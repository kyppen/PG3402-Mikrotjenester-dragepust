package org.api.stats.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor

public class StatUpdateRequestDTO {
    private Long characterId;
    private int value;
}
