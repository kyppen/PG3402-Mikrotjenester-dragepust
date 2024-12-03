package sofa.microservice.campaign;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sofa.microservice.campaign.entity.Campaign;
import sofa.microservice.campaign.entity.PlayerCharacter;

import java.util.List;

@Service
@AllArgsConstructor
public class CampaignService {
    @Autowired
    private final CampaignRepo campaignRepo;
    private final PlayerCharacterRepo playerCharacterRepo;

    public void createCampaign(Campaign campaign){
        campaignRepo.save(campaign);
    }
    public List<Campaign> getAllCampaigns(){
        return campaignRepo.findAll();
    }
    public void addCharacter(PlayerCharacter playerCharacter){
        playerCharacterRepo.save(playerCharacter);
    }
    public List<PlayerCharacter> GetAllCharacters(){
        return playerCharacterRepo.findAll();
    }
    public List<PlayerCharacter> GetAllCharactersInCampaign(String campaignId){
        //return playerCharacterRepo.findAllBycampaignId();
        return playerCharacterRepo.findAllByCampaignId(campaignId);
    }


}
