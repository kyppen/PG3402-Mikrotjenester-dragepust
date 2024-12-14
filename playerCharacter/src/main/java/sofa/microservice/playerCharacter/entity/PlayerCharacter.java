package sofa.microservice.playerCharacter.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "CHARACTER")
public class PlayerCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String characterName;
    private String profession;
    private String species;
    private String itemSetId;
    private int baseWillpower;
    private int baseHP;
    private int baseMagic;

    @ElementCollection
    private List<String> skills;

    @ElementCollection
    private List<String> magic;

    public String characterStatsString(){
        return "magic: " + this.baseMagic +" hp: " + this.baseHP + " willpower: " + this.baseWillpower;
    }
}
