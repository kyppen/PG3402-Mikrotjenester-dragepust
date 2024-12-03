package sofa.example.diceroller;


import lombok.Value;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Service
public class DiceService {

    private final RabbitTemplate rabbitTemplate;

    public DiceService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    //@Value("${spring.rabbitmq.queue}") // Inject the queue name from properties
    private String queueName = "diceQueue";

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(queueName, message, msg -> {
            msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return msg;
        });
        System.out.println("Message sent: " + message);
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
