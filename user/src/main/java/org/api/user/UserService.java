package org.api.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@ConfigurationProperties(prefix = "servicenames")
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Value("${servicenames.userservice}")
    String userServiceName;

    public User saveUser(UserDTO userDTO) {
        log.info("UserDTO {}", userDTO);
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return userRepo.save(user);
    }
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

}
