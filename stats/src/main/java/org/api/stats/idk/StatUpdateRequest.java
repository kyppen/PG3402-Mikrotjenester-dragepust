package org.api.stats.idk;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatUpdateRequest {
    private int hpChange;
    private int magicChange;
    private int willpowerChange;


    // Constructor
    public StatUpdateRequest() {}

}

