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
import sofa.example.diceroller.DTO.ConsoleMessageDTO;
import sofa.example.diceroller.DTO.MessageDTO;

import java.util.Random;

@Slf4j
@Service
public class DiceService {

    private final RestTemplate restTemplate = new RestTemplate();

    //Casts a dice
    public Integer rollDice(Integer nr){
        int diceResults = 0;
        for(int i = 0; i < nr; i++){
            long seed = System.currentTimeMillis();
            Random random = new Random(seed);

            int roll = random.nextInt(6) + 1;
            diceResults += roll;
            log.info("dice rolled an {}" , roll);
        }
        log.info("Dice cast: {} outcome: {}", nr, diceResults);
        return diceResults;
    }

}
