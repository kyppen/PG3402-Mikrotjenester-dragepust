package org.api.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
@Slf4j
@ConfigurationProperties(prefix = "servicenames")
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Value("${servicenames.userservice}")
    String userServiceName;

    public User saveUser(User user) {
    System.out.println(user);
        return userRepo.save(user);
    }
}
