package sofa.microservice.playerCharacter.DTO;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class PlayerCharacterDTO {

    private String userId;
    private String characterName;
    private String race;
    private String job;

}
