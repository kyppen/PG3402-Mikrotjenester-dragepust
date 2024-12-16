package org.api.user;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    private UserRepo userRepo;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        log.info("Creating user {}", userDTO);
        User user = userService.saveUser(userDTO);
        log.info("UserId: {}", user.getId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<User> findByUsername(@RequestParam String username){
        System.out.println("/Logged in " + username);
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }
}

