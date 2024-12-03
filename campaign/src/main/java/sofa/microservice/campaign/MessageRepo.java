package sofa.microservice.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofa.microservice.campaign.entity.Message;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
}
