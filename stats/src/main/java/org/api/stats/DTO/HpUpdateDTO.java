package org.api.stats.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor

public class HpUpdateDTO {
    private Long characterId;
    private int value;
    private String type;
}
