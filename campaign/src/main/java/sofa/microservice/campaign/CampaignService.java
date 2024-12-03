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
    @RabbitListener(queues = "campaign_messages")
    public void receiveMessage(String message){
        try{
            System.out.println(message);
            //SOMETING FUCKY HERE
            MessageDTO messageDTO = new ObjectMapper().readValue(message, MessageDTO.class);
            System.out.println(messageDTO);
        }catch (Exception e){
            log.error("FAILED CONVERTING STRINGDTO TO MESSAGEDTO");
        }
    }




}
