package org.api.stats;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CharacterStats {
    private int hpChange;
    private int magicChange;
    private int willpowerChange;

    // Default constructor
    public CharacterStats() {}
}