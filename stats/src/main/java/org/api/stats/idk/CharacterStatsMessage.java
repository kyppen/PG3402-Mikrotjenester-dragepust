package org.api.stats.idk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.api.stats.DTO.CharacterStatsDTO;

@Getter
@Setter
@AllArgsConstructor
public class CharacterStatsMessage {
    private String characterId;
    private CharacterStatsDTO stats;
}
