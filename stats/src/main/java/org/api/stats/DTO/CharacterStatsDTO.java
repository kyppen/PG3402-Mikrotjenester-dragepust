package org.api.stats.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CharacterStatsDTO {
    private Long characterId;
    private int hp;
    private int magic;
    private int willpower;
}
