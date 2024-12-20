package sofa.microservice.campaign.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class AddCharacterToCampaignDTO {
    private String userId;
    private String characterId;
    private String campaignId;
}
