package sofa.microservice.campaign;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofa.microservice.campaign.DTO.AddCharacterToCampaignDTO;
import sofa.microservice.campaign.DTO.CampaignDTO;
import sofa.microservice.campaign.DTO.CampaignIdDTO;
import sofa.microservice.campaign.entity.Campaign;
import sofa.microservice.campaign.entity.PlayerCharacter;

import java.util.List;

@RestController
@RequestMapping("/campaign")
@RequiredArgsConstructor
@Slf4j
public class CampaignController {
    private final CampaignService campaignService;

    @PostMapping("/create")
    public ResponseEntity<Campaign> createCampaign(@RequestBody CampaignDTO campaignDTO) {
        log.info("Create campaign: {}", campaignDTO);
        Campaign campaign = new Campaign();
        campaign.setCreatorId(campaignDTO.getCreatorId());
        campaign.setDescription(campaignDTO.getDescription());
        campaign.setName(campaignDTO.getName());
        campaignService.createCampaign(campaign);
        return new ResponseEntity<>(campaign, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public List<Campaign> allCampaigns(){
        return campaignService.getAllCampaigns();
    }
    @PutMapping("/playercharacter/add")
    public void addCharacter(@RequestBody AddCharacterToCampaignDTO addCharacterToCampaignDTO){
        log.info("Add Character: {}", addCharacterToCampaignDTO);
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setCampaignId(addCharacterToCampaignDTO.getCampaignId());
        playerCharacter.setCharacterId(addCharacterToCampaignDTO.getCharacterId());
        campaignService.addCharacter(playerCharacter);
    }
    @GetMapping("/playercharacter/all")
    public List<PlayerCharacter> AllCharacters(){
        return campaignService.GetAllCharacters();
    }
    @GetMapping("/characters")
    public List<PlayerCharacter> CharacterInACampaign(@RequestBody CampaignIdDTO campaignIdDTO){
        return campaignService.GetAllCharactersInCampaign(campaignIdDTO.getCampaignId());
    }

}
