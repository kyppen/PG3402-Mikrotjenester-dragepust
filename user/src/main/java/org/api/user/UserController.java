package org.api.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        log.info("Creating user {}", user);
        return userService.saveUser(user);

    }
}

