package org.api.user;

import lombok.*;

@Getter
@Setter
@ToString

public class UserDTO {
    private String username;
    private String password;

    public UserDTO(String userId) {
    }
}
