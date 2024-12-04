package sofa.microservice.campaign;




import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue myQueue(){
        return new Queue("campaign_messages", true);
    }
}
