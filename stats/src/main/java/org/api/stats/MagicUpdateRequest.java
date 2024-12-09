package org.api.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class MagicUpdateRequest {
    private Long characterId;
    private int magicChange;

    // Getters and setters
}