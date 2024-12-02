package sofa.microservice.campaign.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignDTO {
    private Long creatorId;
    private String name;
    private String description;
}
