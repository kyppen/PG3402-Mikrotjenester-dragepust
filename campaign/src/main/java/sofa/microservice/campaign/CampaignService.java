package sofa.microservice.campaign;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sofa.microservice.campaign.DTO.MessageDTO;
import sofa.microservice.campaign.entity.Campaign;
import sofa.microservice.campaign.entity.Message;
import sofa.microservice.campaign.entity.PlayerCharacter;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CampaignService {
    @Autowired
    private final CampaignRepo campaignRepo;
    private final PlayerCharacterRepo playerCharacterRepo;
    private final MessageRepo messageRepo;

    public void createCampaign(Campaign campaign){
        campaignRepo.save(campaign);
    }
    public List<Campaign> getAllCampaigns(){
        return campaignRepo.findAll();
    }
    public List<Campaign> getCampaignByUserId(String userId){
        return campaignRepo.findCampaignByUserId(userId);
    }

    public void addCharacter(PlayerCharacter playerCharacter){
        playerCharacterRepo.save(playerCharacter);
    }
    public void addMessage(Message message){
        log.info("saved message");
        messageRepo.save(message);
    }
    public List<PlayerCharacter> GetAllCharacters(){
        return playerCharacterRepo.findAll();
    }
    public List<PlayerCharacter> GetAllCharactersInCampaign(String campaignId){
        //return playerCharacterRepo.findAllBycampaignId();
        return playerCharacterRepo.findAllByCampaignId(campaignId);
    }
    public List<Message> GetAllMessages(String campaignId){
        return messageRepo.findAllByCampaignId(campaignId);
    }
    @RabbitListener(queues = "campaign_messages")
    public void receiveMessage(String stringMessage){
        try{
            System.out.println("String message: "+ stringMessage);
            //SOMETING FUCKY HERE
            MessageDTO messageDTO = new ObjectMapper().readValue(stringMessage, MessageDTO.class);
            System.out.println(messageDTO);
            Message message = new Message(messageDTO.getCampaignId(), messageDTO.getPlayerCharacterId(), messageDTO.getMessage());
            messageRepo.save(message);
            log.info("Message saved to repository");
        }catch (Exception e){
            log.error("FAILED CONVERTING STRINGDTO TO MESSAGEDTO");
        }
    }




}
