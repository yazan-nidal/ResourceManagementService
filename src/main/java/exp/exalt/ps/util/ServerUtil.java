package exp.exalt.ps.util;

import exp.exalt.ps.config.Mapper;
import exp.exalt.ps.dto.ServerDtoResponse;
import exp.exalt.ps.models.Server;
import exp.exalt.ps.models.State;
import exp.exalt.ps.services.STimeServices;
import exp.exalt.ps.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServerUtil {
    long MAX_WAIT = 20;
    @Autowired
    Mapper mapper;
    @Autowired
    ServerService serverService;
    @Autowired
    STimeServices sTimeServices;

    public ServerDtoResponse getServerState(long id) {
        Server server = serverService.findServer(server1 -> server1.getId() == id);

        // server  not  found
        if (server == null) return null;


        // active  state  server  no  calculate remain time
        if(server.getState() == State.ACTIVE.getState()) {
            return new ServerDtoResponse(server.getId(),State.ACTIVE.getStatus(),null);
        }

        // creating state calculate remain time
        Long end = System.currentTimeMillis();
        Long start = sTimeServices.getSTime(server.getId()).getTime();
        Long timestamp = MAX_WAIT -  ((end - start) / 1000);
        String status = State.CREATING.getStatus();
        return new ServerDtoResponse(server.getId(),status,timestamp);
    }

}
