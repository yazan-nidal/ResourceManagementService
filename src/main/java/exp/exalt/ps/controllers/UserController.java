package exp.exalt.ps.controllers;

import exp.exalt.ps.dto.ServerDtoResponse;
import exp.exalt.ps.dto.UserDtoRequest;
import exp.exalt.ps.dto.UserDtoResponse;
import exp.exalt.ps.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserUtil userUtil;

    @PostMapping(value = {"/",""})
    public ResponseEntity<Object> addUser(@RequestBody UserDtoRequest userDtoRequest) {
        UserDtoResponse user = userUtil.addUser(userDtoRequest);
        if(user == null) new ResponseEntity<>("This  user  name  is  used", HttpStatus.CONFLICT);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping(value = {"/{username}/allocate/server/"})
    public ResponseEntity<Object> allocateServer(@PathVariable(value = "username") String username,@RequestParam(name = "space") int space) {
        ServerDtoResponse response = userUtil.allocateServer(username,space);
        if(response == null) return new ResponseEntity<>("user  not found",HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(userUtil.allocateServer(username,space),HttpStatus.OK);
    }

}
