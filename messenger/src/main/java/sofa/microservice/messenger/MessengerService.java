package sofa.microservice.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sofa.microservice.messenger.DTO.CharacterNameDTO;
import sofa.microservice.messenger.DTO.ConsoleMessageDTO;
import sofa.microservice.messenger.DTO.MessageDTO;

import javax.print.DocFlavor;

@Service
@Slf4j
//@ConfigurationProperties(prefix = "servicenames")
public class MessengerService {

    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${servicenames.dicerollerservice}")
    String diceRollerServiceName;
    @Value("${servicenames.playercharacterservice}")
    String playerCharacterServiceName;



    public MessengerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    private String queueName = "campaign_messages";

    public void sendMessageRoll(MessageDTO messageDTO) {
        String characterName = "DEFAULT";
        Integer result = 69;
        try{
            characterName = GetCharacterInfo(messageDTO.getCharacterId());
        }catch (Exception e){
            log.error("Failed to get character name for roll");
        }
        try{
            result = RollDice();
        }catch (Exception e){
            log.error("Failed to roll dice");
        }

        messageDTO.setMessage(String.format("%s has rolled an %d", characterName, result));
        try{
            String StringDTO = new ObjectMapper().writeValueAsString(messageDTO);
            rabbitTemplate.convertAndSend(queueName, StringDTO);
            log.info("object sent to queue, {}", StringDTO);
        }catch (Exception e){
            log.error("Failed to convert DTO to String");
            log.error("CharacterService Might be down");
        }
    }
    public void sendConsoleMessage(MessageDTO messageDTO) {
        try{
            String Message = String.format("Console: %s", messageDTO.getMessage());
            messageDTO.setMessage(Message);
            String StringDTO = new ObjectMapper().writeValueAsString(messageDTO);
            rabbitTemplate.convertAndSend(queueName, StringDTO);
            System.out.println("Message sent: " + StringDTO);

        }catch (Exception e){
            e.printStackTrace();
            log.error("Failed to convert DTO to String");
        }
    }
    public String GetCharacterInfo(String characterId){
        String url = String.format("http://%s:8081/character/%s", playerCharacterServiceName, characterId);
        log.info("url to PlayercharacterService: {}" , url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);
        String response = restTemplate.getForObject(url, String.class);
        try{
            CharacterNameDTO characterNameDTO = new ObjectMapper().readValue(response, CharacterNameDTO.class);
            log.info("CharacterName {}" , characterNameDTO.getCharacterName());
            return characterNameDTO.getCharacterName();
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public Integer RollDice() throws Exception {
        log.info("RollDice()");
        try{
            log.info("LOCALHOST OR dicerollerservice: {}", diceRollerServiceName);
            //"http://localhost:8086/dice/roll/2"
            String url = String.format("http://%s:8086/dice/roll/2", diceRollerServiceName);
            log.info("URL TO CONTACT diceroller : {}", url);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<String> request = new HttpEntity<>(headers);
            String response = restTemplate.getForObject(url, String.class);
            log.info("Response: {}", response);
            if(response == null){
                return 0;
            }
            return Integer.parseInt(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
