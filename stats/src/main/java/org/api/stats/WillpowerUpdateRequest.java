package org.api.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor

public class WillpowerUpdateRequest {
    private Long characterId;
    private int willpowerChange;

    // Getters and setters
}
