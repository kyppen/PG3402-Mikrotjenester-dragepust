package sofa.microservice.playerCharacter.DTO;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CampaignInfoDTO {
    private String campaignName;
    private String campaignDescription;
    private String campaignId;
}

