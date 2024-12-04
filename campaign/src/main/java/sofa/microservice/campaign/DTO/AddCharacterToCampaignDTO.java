package sofa.microservice.campaign.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCharacterToCampaignDTO {
    private String userId;
    private String characterId;
    private String campaignId;
}
