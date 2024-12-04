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
        Campaign campaign = new Campaign(campaignDTO);
        campaignService.createCampaign(campaign);
        return new ResponseEntity<>(campaign, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Campaign>> allCampaigns(){
        return new ResponseEntity<>(campaignService.getAllCampaigns(), HttpStatus.OK);
    }

    @GetMapping("/campaign")
    public ResponseEntity<List<Campaign>> getCampaign(@RequestBody UserIdDTO userIdDTO){
        return new ResponseEntity<>(campaignService.getCampaignByUserId(userIdDTO.getUserId()), HttpStatus.OK);
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
    @GetMapping("/characters")
    public ResponseEntity<List<PlayerCharacter>> CharacterInACampaign(@RequestBody CampaignIdDTO campaignIdDTO){
        return new ResponseEntity<>(campaignService.GetAllCharactersInCampaign(campaignIdDTO.getCampaignId()), HttpStatus.OK);
    }
    @GetMapping("/messagelog")
    public ResponseEntity<List<Message>> CampaignMessageLog(@RequestBody CampaignIdDTO campaignIdDTO){
        return new ResponseEntity<>(campaignService.GetAllMessages(campaignIdDTO.getCampaignId()), HttpStatus.OK);
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
