package sofa.microservice.campaign;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofa.microservice.campaign.DTO.*;
import sofa.microservice.campaign.entity.Campaign;
import sofa.microservice.campaign.entity.Message;
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
        campaign.setUserId(campaignDTO.getUserId());
        campaign.setDescription(campaignDTO.getDescription());
        campaign.setName(campaignDTO.getName());
        campaignService.createCampaign(campaign);
        return new ResponseEntity<>(campaign, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public List<Campaign> allCampaigns(){
        return campaignService.getAllCampaigns();
    }
    @GetMapping("/campaign")
    public List<Campaign> getCampaign(@RequestBody UserIdDTO userIdDTO){
        return campaignService.getCampaignByUserId(userIdDTO.getUserId());
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
    @GetMapping("/messagelog")
    public List<Message> CampaignMessageLog(@RequestBody CampaignIdDTO campaignIdDTO){
        return campaignService.GetAllMessages(campaignIdDTO.getCampaignId());
    }

    //NEED TO ADD CHECK IF CAMPAIGN EXISTS BEFORE ADDING, FINE FOR TESTING
    //THIS SHOULD READ FROM RABBITMQ QUEUE FROM DICEROLLER SERVICE
    //FRONTEND -> DICEROLLER -> CAMPAIGN.
    //this will be depricated once rabbitMQ queue is up
    @PostMapping("/message")
    public void addMessage(@RequestBody MessageDTO messageDTO){
        log.info("message {}", messageDTO);
        Message message = new Message(messageDTO.getCampaignId(),messageDTO.getPlayerCharacterId(),messageDTO.getMessage());
        campaignService.addMessage(message);
    }

}
