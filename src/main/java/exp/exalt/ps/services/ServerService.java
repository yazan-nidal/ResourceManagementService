package exp.exalt.ps.services;


import exp.exalt.ps.models.Server;
import exp.exalt.ps.models.State;
import exp.exalt.ps.repositories.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ServerService {
    @Autowired
    ServerRepository serverRepository;

  public Server findServer(Predicate<? super Server> filter) {
      List<Server> servers = serverRepository.findAll();
     return servers.stream().filter(filter).findFirst().orElse(null);
    }

    public List<Server> findActiveAvailableServer(int space) {
        List<Server> servers = serverRepository.findAllByState(State.ACTIVE.getState());
        return servers.stream().filter( s -> s.getAvailable() >= space).collect(Collectors.toList());
    }

    public List<Server> findCreateAvailableServer(int space) {
        List<Server> servers = serverRepository.findAllByState(State.CREATING.getState());
        return servers.stream().filter( s -> s.getAvailable() >= space).collect(Collectors.toList());
    }

    public synchronized Server addUpdateServer(Server server) {
       return serverRepository.save(server);
    }

}
