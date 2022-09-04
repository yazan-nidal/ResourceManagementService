package exp.exalt.ps.controllers;

import exp.exalt.ps.dto.ServerDtoResponse;
import exp.exalt.ps.models.Server;
import exp.exalt.ps.util.ServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/servers")
public class ServerController {
    @Autowired
    ServerUtil serverUtil;

    @GetMapping(value = {"state/{id}"})
    public ResponseEntity<Object> getServerState(@PathVariable(value = "id") long id) {
        ServerDtoResponse serverStatus = serverUtil.getServerState(id);
        if(serverStatus == null) return new ResponseEntity<>("Server not found",HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(serverStatus,HttpStatus.OK);
    }

}
