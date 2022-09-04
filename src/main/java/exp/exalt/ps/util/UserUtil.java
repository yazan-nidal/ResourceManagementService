package exp.exalt.ps.util;

import exp.exalt.ps.config.Mapper;
import exp.exalt.ps.dto.*;
import exp.exalt.ps.models.STime;
import exp.exalt.ps.models.Server;
import exp.exalt.ps.models.State;
import exp.exalt.ps.models.User;
import exp.exalt.ps.services.STimeServices;
import exp.exalt.ps.services.SequenceGeneratorService;
import exp.exalt.ps.services.ServerService;
import exp.exalt.ps.services.UserService;

import jdk.internal.net.http.common.Pair;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class UserUtil {
    long Max_CAP = 100;
    long MAX_WAIT = 20;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    Mapper mapper;
    @Autowired
    UserService userService;
    @Autowired
    ServerService serverService;
    @Autowired
    STimeServices sTimeServices;

    public UserDtoResponse addUser(UserDtoRequest userDtoRequest) {
        User user= mapper.convertForm(userDtoRequest,User.class);

        // user conflict
        if(userService.getUserByUserName(userDtoRequest.getUsername()) != null) {
            return null;
        }

        long id  = sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME);
        user.setId(id);
        user.setServers(new ArrayList<>());
        userService.addUpdateUser(user);

        return mapper.convertForm(userDtoRequest,UserDtoResponse.class);
    }

    @SneakyThrows
    public ServerDtoResponse allocateServer(String  username, int space){
        User user = userService.getUserByUserName(username);

        // user not found
        if(user == null) return  null;

        Server server = null;

        //Find  Available Active Servers
        List<Server> servers = serverService.findActiveAvailableServer(space);

        if(servers == null
                || servers.isEmpty()) {


            //Find  Available Servers in create state
            servers = serverService.findCreateAvailableServer(space);

            // create  new  server  when  no server Available
            if(servers == null
                    || servers.isEmpty()) {

                // create  server
                server =  createNewServer();

                // trigger async jop to  active  then  spin  server
                Server finalServer = server;
                Thread newThread = new Thread(() -> {


                    // save  start  time  to  calculate  remain  time  when  check  status
                    Long timestamp = System.currentTimeMillis();
                    sTimeServices.setSTime(new STime(finalServer.getId(),timestamp));

                    try {
                        Thread.sleep(MAX_WAIT*1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    CompletableFuture<Server> completableFuture = CompletableFuture.supplyAsync( () -> activeServer(finalServer));
                    try {
                        //schedule time to complete job then  remove time  counter
                        completableFuture.get(MAX_WAIT,TimeUnit.SECONDS);
                        completableFuture.thenRun(() -> sTimeServices.unSetSTime(finalServer.getId()));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (TimeoutException e) {
                        throw new RuntimeException(e);
                    }

                });
                newThread.start();

                // spin 'allocate' server immediately after creating
                Server server1 = spinServer(server,user,space);
                return new ServerDtoResponse(server1.getId(),
                        State.CREATING.getStatus(),
                        MAX_WAIT);
            }
        }

        // find best server with minimum space enough to allocate it
        server = servers.stream().min(Comparator.comparingLong(s -> s.getAvailable())).get();
        server = spinServer(server, user, space);

        // response  when  active  status  without time
        if(server.getState() == State.ACTIVE.getState()) {
            return new ServerDtoResponse(server.getId(),State.ACTIVE.getStatus(),null);
        }

        // response  when  create  status  wit calculate remain time
        Long end = System.currentTimeMillis();
        Long start = sTimeServices.getSTime(server.getId()).getTime();
        Long timestamp = MAX_WAIT -  ((end - start) / 1000);
        String status = State.CREATING.getStatus();
        return new ServerDtoResponse(server.getId(),status,timestamp);
    }
    private Server spinServer(Server server,User user,int space){
        long id = server.getId();
        server = serverService.findServer(server1 -> server1.getId() == id);

        user= userService.getUserByUserName(user.getUsername());

        server.getUsers().add(user);
        server.setAvailable(server.getAvailable()-space);
        synchronized(this){
            server = serverService.addUpdateServer(server);
        }

        user.getServers().add(server);
        userService.addUpdateUser(user);
        return server;
    }

    private Server createNewServer(){
        long id  = sequenceGeneratorService.generateSequence(Server.SEQUENCE_NAME);
        Server server = new Server(id,State.CREATING.getState(),Max_CAP,Max_CAP,new ArrayList<>());
        synchronized(this){
            server = serverService.addUpdateServer(server);
        }
        return server;
    }

    private Server activeServer(Server server) {
        long id = server.getId();
        server = serverService.findServer(server1 -> server1.getId() == id);
        server.setState(State.ACTIVE.getState());
        synchronized(this){
            server = serverService.addUpdateServer(server);
        }
        return server;
    }

}
