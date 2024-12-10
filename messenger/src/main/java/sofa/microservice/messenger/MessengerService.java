package sofa.microservice.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
public class MessengerService {

    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    public MessengerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    private String queueName = "campaign_messages";

    public void sendMessageRoll(MessageDTO messageDTO) {
        try{
            String characterName = GetCharacterInfo("1");
            log.info("ROLLING WITH NEW SERVICE");
            // 5 should be replaced with a call to dice service
            log.info("Calling Roll Dice");
            Integer result = RollDice();
            log.info("After Calling rolldice {}", result);
            String Message = String.format("%s has rolled an %d", characterName, result);
            messageDTO.setMessage(Message);

            String StringDTO = new ObjectMapper().writeValueAsString(messageDTO);
            rabbitTemplate.convertAndSend(queueName, StringDTO);
            log.info("object sent to queue, {}", StringDTO);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Failed to convert DTO to String");
        }
    }
    public void sendConsoleMessage(ConsoleMessageDTO consoleMessageDTO) {
        try{
            String Message = String.format("Console: %s", consoleMessageDTO.getMessage());
            consoleMessageDTO.setMessage(Message);
            String StringDTO = new ObjectMapper().writeValueAsString(consoleMessageDTO);
            rabbitTemplate.convertAndSend(queueName, StringDTO);
            System.out.println("Message sent: " + StringDTO);

        }catch (Exception e){
            e.printStackTrace();
            log.error("Failed to convert DTO to String");
        }
    }
    public String GetCharacterInfo(String characterId){
        String url = "http://localhost:8081/character/" + characterId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);
        String response = restTemplate.getForObject(url, String.class);
        try{
            CharacterNameDTO characterNameDTO = new ObjectMapper().readValue(response, CharacterNameDTO.class);
            System.out.println("Character name is: " + characterNameDTO.getCharacterName());
            return characterNameDTO.getCharacterName();
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public Integer RollDice(){
        log.info("RollDice()");
        try{
            String url = "http://localhost:8086/dice/roll/2";
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
