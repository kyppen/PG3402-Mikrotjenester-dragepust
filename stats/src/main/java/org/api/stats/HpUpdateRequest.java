package org.api.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor

public class HpUpdateRequest {
    private Long characterId;
    private int hpChange;
}
