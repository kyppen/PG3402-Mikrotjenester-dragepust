package sofa.microservice.campaign.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sofa.microservice.campaign.DTO.AddCharacterToCampaignDTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "PLAYERCHARACTER")
public class PlayerCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String campaignId;
    private String characterId;
    public PlayerCharacter(AddCharacterToCampaignDTO playerCharacter){
        this.campaignId = playerCharacter.getCampaignId();
        this.characterId = playerCharacter.getCharacterId();
    }
}
