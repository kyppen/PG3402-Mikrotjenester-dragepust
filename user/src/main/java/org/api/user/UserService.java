package org.api.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public void sendUserIdToCharacterService(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        String characterServiceUrl = "http://localhost:8087/character/link-user";

        UserDTO dto = new UserDTO(userId);
        restTemplate.postForEntity(characterServiceUrl, dto, Void.class);
    }
}
