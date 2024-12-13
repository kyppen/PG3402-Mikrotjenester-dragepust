package org.api.user;

import lombok.*;

@Getter
@Setter
@ToString

public class UserDTO {
    private String userId;

    public UserDTO(String userId) {
    }
}
