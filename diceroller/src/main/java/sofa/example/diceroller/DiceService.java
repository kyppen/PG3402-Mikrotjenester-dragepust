package sofa.example.diceroller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import sofa.example.diceroller.DTO.MessageDTO;

import java.util.Random;

@Slf4j
@Service
public class DiceService {

    private final RabbitTemplate rabbitTemplate;

    public DiceService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    //@Value("${spring.rabbitmq.queue}") // Inject the queue name from properties
    private String queueName = "campaign_messages";

    public void sendMessage(MessageDTO messageDTO) {
        try{
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
}
