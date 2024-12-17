package sofa.microservice.campaign;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sofa.microservice.campaign.DTO.CampaignInfoDTO;
import sofa.microservice.campaign.DTO.CharacterInfoDTO;
import sofa.microservice.campaign.DTO.MessageDTO;
import sofa.microservice.campaign.entity.Campaign;
import sofa.microservice.campaign.entity.Message;
import sofa.microservice.campaign.entity.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignService {

    @Value("${servicenames.playercharacterservice}")
    private String playercharacterservice;

    private final CampaignRepo campaignRepo;
    private final PlayerCharacterRepo playerCharacterRepo;
    private final MessageRepo messageRepo;
    private final RestTemplate restTemplate;

    public void createCampaign(Campaign campaign){
        log.info("Saving campaign {}", campaign);
        campaignRepo.save(campaign);
    }
    @Transactional
    public void deleteCampaign(String campaignId){
        if(campaignRepo.existsById(Long.parseLong(campaignId))){
            log.info("campaign exists by id {}", campaignId);
            List<Message> messages = messageRepo.findAllByCampaignId(campaignId);
            List<PlayerCharacter> characters = playerCharacterRepo.findAllByCampaignId(campaignId);
            log.info("Deleting {} Characters" , characters.size());
            log.info("Deleting {} Messages" , messages.size());
            messageRepo.deleteAllByCampaignId(campaignId);
            campaignRepo.deleteById(Long.parseLong(campaignId));
            playerCharacterRepo.deleteByCampaignId(campaignId);
            log.info("Campaign Deleted");
        }
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
            PlayerCharacter playerCharacter = playerCharacterRepo.findByCharacterId(playerCharacterToAdd.getCharacterId());
            playerCharacter.setCharacterId(playerCharacterToAdd.getCharacterId());
            playerCharacter.setCampaignId(playerCharacterToAdd.getCampaignId());
            playerCharacterRepo.save(playerCharacter);
        }else{
            log.info("Adding character {}", playerCharacterToAdd);
            playerCharacterRepo.save(playerCharacterToAdd);
        }
    }
    public void removeCharacter(String characterId){
        if(playerCharacterRepo.existsByCharacterId(characterId)){
            log.info("Delete: character exists attempting to delete: characterId: {}", characterId);
            PlayerCharacter playerCharacter = playerCharacterRepo.findByCharacterId(characterId);
            playerCharacterRepo.deleteById(playerCharacter.getId());
        }
    }
    public CampaignInfoDTO getCampaignIdOfCharacter(String characterId){
        log.info("GetCampaignIdOfCharacter {}", characterId);
        if(playerCharacterRepo.existsByCharacterId(characterId)){
            log.info("Character exists in a campaign");
            PlayerCharacter playerCharacter = playerCharacterRepo.findByCharacterId(characterId);
            if(campaignRepo.existsById(Long.parseLong(playerCharacter.getCampaignId()))){
                Campaign campaign = campaignRepo.findById(Long.parseLong(playerCharacter.getCampaignId())).orElseThrow();
                CampaignInfoDTO campaignInfoDTO = new CampaignInfoDTO();
                campaignInfoDTO.setCampaignName(campaign.getCampaignName());
                campaignInfoDTO.setCampaignDescription(campaign.getCampaignDescription());
                campaignInfoDTO.setCampaignId(String.format("%d",campaign.getId()));
                return campaignInfoDTO;
            }
        }
        log.info("character with characterId {} has no campaign", characterId);
        return null;
    }
    public void addMessage(Message message){
        log.info("saved message");
        messageRepo.save(message);
    }
    public void removeMessages(String characterId){
        if(playerCharacterRepo.existsByCharacterId(characterId)){
            log.info("MESSAGE DELETE: Character exists in the campaign {}", characterId);
            List<Message> Messages = messageRepo.findAllByCharacterId(characterId);
            for(Message message : Messages){
                log.info("PLACEHOLDER TESTING IF MORE A CHARACTER CAN BE in multiple campaigns");
            }
        }
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
            log.info("amount of character in campaign {}", ListOfCharacterIds.size());
            log.info("service: {}", playercharacterservice);
            String url = String.format("http://%s:8081/character/%s", playercharacterservice, playerCharacter.getCharacterId());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            CharacterInfoDTO response = restTemplate.getForObject(url, CharacterInfoDTO.class);
            characterInfoList.add(response);
            response.toString();
        }
        log.info("Got all chracters returning");
        return characterInfoList;
    }
    public List<Message> getAllMessages(String campaignId){
        return messageRepo.findAllByCampaignId(campaignId);
    }

    @RabbitListener(queues = "campaign_messages")
    public void receiveMessage(String stringMessage) throws InterruptedException {
        log.info("processing saving message");
        Thread.sleep(2000);
        log.info("Saving message");
        try{
            System.out.println("String message: "+ stringMessage);
            MessageDTO messageDTO = new ObjectMapper().readValue(stringMessage, MessageDTO.class);
            System.out.println(messageDTO);
            if(playerCharacterRepo.existsByCharacterId(messageDTO.getCharacterId())){
                Message message = new Message(messageDTO.getCampaignId(), messageDTO.getCharacterId(), messageDTO.getMessage());
                messageRepo.save(message);
                log.info("Message saved to repository");
            }else if (messageDTO.getCharacterId().equals("CONSOLE")){
                Message message = new Message(messageDTO.getCampaignId(), messageDTO.getCharacterId(), messageDTO.getMessage());
                log.info("MESSAGE CONTAINS CONSOLE");
                messageRepo.save(message);
                log.info("CONSOLE MESSAGE SAVED");
            }
            else{
                log.info("Messaged not saved; UserId not in repo characterId: {}" , messageDTO.getCharacterId());
            }
        }catch (Exception e){
            log.error("FAILED CONVERTING STRINGDTO TO MESSAGEDTO");
        }
    }




}
