package sofa.microservice.playerCharacter.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
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
}
