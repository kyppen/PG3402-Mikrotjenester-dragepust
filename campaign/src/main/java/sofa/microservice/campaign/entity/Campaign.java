package sofa.microservice.campaign.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sofa.microservice.campaign.DTO.CampaignDTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "CAMPAIGN")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private String name;
    private String description;
    public Campaign(CampaignDTO campaignDTO) {
        this.setName(campaignDTO.getName());
        this.setDescription(campaignDTO.getDescription());
        this.setUserId(campaignDTO.getUserId());
    }
}
