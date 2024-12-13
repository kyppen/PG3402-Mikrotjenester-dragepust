package org.api.stats.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class StatsDTO {
    private String type;
    private int value;
    private Long characterId;
}
