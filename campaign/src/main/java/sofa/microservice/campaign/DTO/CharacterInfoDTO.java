package sofa.microservice.campaign.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterInfoDTO {
    private String userId;
    private String characterName;
    private String profession;
    private String species;
    private String itemSetId;
}
