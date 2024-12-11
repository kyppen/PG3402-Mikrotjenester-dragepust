package org.api.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User saveUser(User user) {
    System.out.println(user);
        return userRepo.save(user);
    }
}
