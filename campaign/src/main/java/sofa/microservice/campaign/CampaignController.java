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
@CrossOrigin (origins = "http://localhost:5173")
@Slf4j
public class CampaignController {
    private final CampaignService campaignService;
    @PostMapping("/create")
    public ResponseEntity<Campaign> createCampaign(@RequestBody CampaignDTO campaignDTO) {
        log.info("Create campaign: {}", campaignDTO.toString());
        Campaign campaign = new Campaign(campaignDTO);
        campaignService.createCampaign(campaign);
        return new ResponseEntity<>(campaign, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Campaign>> allCampaigns(){
        return new ResponseEntity<>(campaignService.getAllCampaigns(), HttpStatus.OK);
    }

    @GetMapping("/campaign/{campaignId}")
    public Campaign getCampaign(@PathVariable String campaignId){
        Campaign campaign = campaignService.getCampaign(campaignId);
        log.info("campaignId: {}", campaign);
        log.info("Campaign get by id: {}", campaign.toString());
        return campaign;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<Campaign>> getCampaigns(@PathVariable String userId){
        log.info("Get campaigns by UserId: {}", userId);
        return new ResponseEntity<>(campaignService.getCampaignByUserId(userId), HttpStatus.OK);
    }
    @PutMapping("/character/add")
    public ResponseEntity<HttpStatus> addCharacter(@RequestBody AddCharacterToCampaignDTO addCharacterToCampaignDTO){
        log.info("Add Character: {}", addCharacterToCampaignDTO);
        PlayerCharacter playerCharacter = new PlayerCharacter(addCharacterToCampaignDTO);
        campaignService.addCharacter(playerCharacter);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/character/all")
    public ResponseEntity<List<PlayerCharacter>> AllCharacters(){
        return new ResponseEntity<>(campaignService.GetAllCharacters(), HttpStatus.OK);
    }
    @GetMapping("/characters/{campaignID}")
    public ResponseEntity<List<PlayerCharacter>> CharacterInACampaign(@RequestBody CampaignIdDTO campaignIdDTO){
        return new ResponseEntity<>(campaignService.GetAllCharactersInCampaign(campaignIdDTO.getCampaignId()), HttpStatus.OK);
    }
    @GetMapping("/characterinfo/{campaignId}")
    public List<CharacterInfoDTO> CharacterInCampaignWithInfo(@PathVariable String campaignId){
        return campaignService.GetAllCharactersInCampaignWithInfo(campaignId);
    }
    @GetMapping("/chat/{campaignId}")
    public ResponseEntity<List<Message>> CampaignMessageLog(@PathVariable String campaignId){
        //log.info("SOMETHING ");
        return new ResponseEntity<>(campaignService.GetAllMessages(campaignId), HttpStatus.OK);
    }

    //NEED TO ADD CHECK IF CAMPAIGN EXISTS BEFORE ADDING, FINE FOR TESTING
    //THIS SHOULD READ FROM RABBITMQ QUEUE FROM DICEROLLER SERVICE
    //FRONTEND -> DICEROLLER -> CAMPAIGN.
    //this will be depricated once rabbitMQ queue is up
    @PostMapping("/message")
    public void addMessage(@RequestBody MessageDTO messageDTO){
        log.info("message {}", messageDTO);
        Message message = new Message(messageDTO.getCampaignId(),messageDTO.getCharacterId(),messageDTO.getMessage());
        campaignService.addMessage(message);
    }

}
