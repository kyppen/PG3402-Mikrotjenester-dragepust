package sofa.microservice.campaign.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import sofa.microservice.campaign.DTO.CampaignDTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "CAMPAIGN")
@ToString
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private String campaignName;
    private String campaignDescription;
    public Campaign(CampaignDTO campaignDTO) {
        this.setCampaignName(campaignDTO.getCampaignName());
        this.setCampaignDescription(campaignDTO.getCampaignDescription());
        this.setUserId(campaignDTO.getUserId());
    }
}
