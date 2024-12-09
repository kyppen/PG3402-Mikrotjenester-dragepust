package sofa.microservice.campaign;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import sofa.microservice.campaign.DTO.CharacterInfoDTO;
import sofa.microservice.campaign.DTO.MessageDTO;
import sofa.microservice.campaign.entity.Campaign;
import sofa.microservice.campaign.entity.Message;
import sofa.microservice.campaign.entity.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@CrossOrigin (origins = "http://localhost:5173")
public class CampaignService {
    @Autowired
    private final CampaignRepo campaignRepo;
    private final PlayerCharacterRepo playerCharacterRepo;
    private final MessageRepo messageRepo;
    private final RestTemplate restTemplate;

    public void createCampaign(Campaign campaign){
        log.info("Saving campaign {}", campaign);
        campaignRepo.save(campaign);
    }
    public List<Campaign> getAllCampaigns(){
        return campaignRepo.findAll();
    }
    public Campaign getCampaign(String id){
        log.info("Fetching campaign with id {}", id);
        return campaignRepo.findById(Long.parseLong(id)).orElse(null);
    }
    public List<Campaign> getCampaignByUserId(String userId){
        return campaignRepo.findCampaignByUserId(userId);
    }

    //This function either creates a character
    //or updates which campaign a character belongs too
    public void addCharacter(PlayerCharacter playerCharacterToAdd){
        if(playerCharacterRepo.existsByCharacterId(playerCharacterToAdd.getCharacterId())){
            log.info("Character with id {} already exists, we should update instead", playerCharacterToAdd.getCharacterId());
            PlayerCharacter playerCharacter = playerCharacterRepo.findAllByCharacterId(playerCharacterToAdd.getCharacterId());
            playerCharacter.setCharacterId(playerCharacterToAdd.getCharacterId());
            playerCharacter.setCampaignId(playerCharacterToAdd.getCampaignId());
            playerCharacterRepo.save(playerCharacter);
        }else{
            log.info("Adding character {}", playerCharacterToAdd);
            playerCharacterRepo.save(playerCharacterToAdd);
        }
    }
    public void addMessage(Message message){
        log.info("saved message");
        messageRepo.save(message);
    }
    public List<PlayerCharacter> GetAllCharacters(){
        log.info("GetAllCharacters");
        return playerCharacterRepo.findAll();
    }
    public List<PlayerCharacter> GetAllCharactersInCampaign(String campaignId){
        //return playerCharacterRepo.findAllBycampaignId();
        return playerCharacterRepo.findAllByCampaignId(campaignId);
    }
    public List<CharacterInfoDTO> GetAllCharactersInCampaignWithInfo(String campaignId){
        List<PlayerCharacter> ListOfCharacterIds = GetAllCharactersInCampaign(campaignId);
        List<CharacterInfoDTO> characterInfoList = new ArrayList<>();

        for(PlayerCharacter playerCharacter : ListOfCharacterIds){
            log.info("Loop {}", playerCharacter.getCharacterId());
            String url = "http://localhost:8081/character/" + playerCharacter.getCharacterId();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            CharacterInfoDTO response = restTemplate.getForObject(url, CharacterInfoDTO.class);
            characterInfoList.add(response);
            response.toString();
        }
        log.info("Got all chracters returning");
        return characterInfoList;
    }
    public List<Message> GetAllMessages(String campaignId){
        return messageRepo.findAllByCampaignId(campaignId);
    }
    @RabbitListener(queues = "campaign_messages")
    public void receiveMessage(String stringMessage){
        try{
            System.out.println("String message: "+ stringMessage);
            MessageDTO messageDTO = new ObjectMapper().readValue(stringMessage, MessageDTO.class);
            System.out.println(messageDTO);
            Message message = new Message(messageDTO.getCampaignId(), messageDTO.getCharacterId(), messageDTO.getMessage());
            messageRepo.save(message);
            log.info("Message saved to repository");
        }catch (Exception e){
            log.error("FAILED CONVERTING STRINGDTO TO MESSAGEDTO");
        }
    }




}
