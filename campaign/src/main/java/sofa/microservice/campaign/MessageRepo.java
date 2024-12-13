package sofa.microservice.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofa.microservice.campaign.entity.Message;
import sofa.microservice.campaign.entity.PlayerCharacter;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findAllByCampaignId(String campaignId);
    List<Message> findAllByCharacterId(String characterId);
    void deleteAllByCampaignId(String campaignId);
}
