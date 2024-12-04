package sofa.example.diceroller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sofa.example.diceroller.DTO.CharacterNameDTO;
import sofa.example.diceroller.DTO.MessageDTO;

import java.util.Random;

@Slf4j
@Service
public class DiceService {

    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    public DiceService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    //@Value("${spring.rabbitmq.queue}") // Inject the queue name from properties
    private String queueName = "campaign_messages";

    public void sendMessage(MessageDTO messageDTO) {
        try{
            String characterName = GetCharacterInfo("1");
            String Message = String.format("%s has rolled an %d", characterName, rolling(2));
            messageDTO.setMessage(Message);
            String StringDTO = new ObjectMapper().writeValueAsString(messageDTO);
            rabbitTemplate.convertAndSend(queueName, StringDTO);
            System.out.println("Message sent: " + StringDTO);

        }catch (Exception e){
            e.printStackTrace();
            log.error("Failed to convert DTO to String");
        }
    }
    public Integer rolling(int amount){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        Integer rolled = 0;
        for(int i = 0; i < amount; i++){
            rolled += random.nextInt(6) + 1;
        }
        return rolled;
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
}
