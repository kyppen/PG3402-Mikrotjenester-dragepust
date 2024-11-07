package sofa.microservice.playerCharacter.DTO;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class PlayerCharacterDTO {

    private String userId;
    private String characterName;
    private String profession;
    private String species;
    private String itemSetId;

}
