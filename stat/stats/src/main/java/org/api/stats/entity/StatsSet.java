package org.api.stats.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatsSet {
    private String setName;
    private int baseHP;               // Corresponds to "baseHP" in JSON
    private int baseWillpower;        // Corresponds to "baseWillpower" in JSON
    private int baseMagic;            // Corresponds to "baseMagic" in JSON
    private List<String> skills;      // Corresponds to "skills" in JSON
    private List<String> magic;       // Corresponds to "magic" in JSON

    @Override
    public String toString() {
        return "StatsSet{" +
                "setName='" + setName +
                "baseHP=" + baseHP +
                ", baseWillpower=" + baseWillpower +
                ", baseMagic=" + baseMagic +
                ", skills=" + skills +
                ", magic=" + magic +
                '}';
    }
}
