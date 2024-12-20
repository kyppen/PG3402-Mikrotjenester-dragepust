package sofa.microservice.playerCharacter.DTO;


import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class PlayerCharacterDTO {
    private String userId;
    private String characterName;
    private String profession;
    private String species;
    private String itemSetId;

    private int baseHP;
    private int baseMagic;
    private int willpower;

    @ElementCollection
    private List<String> skills;

    @ElementCollection
    private List<String> magic;

}
