package sofa.microservice.campaign.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CampaignDTO {
    private String userId;
    private String campaignName;
    private String campaignDescription;
}
