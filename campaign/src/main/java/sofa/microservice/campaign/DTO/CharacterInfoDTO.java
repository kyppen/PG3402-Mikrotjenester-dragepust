package sofa.microservice.campaign.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterInfoDTO {
    private String id;
    private String characterName;
    private String profession;
    private String species;
}
