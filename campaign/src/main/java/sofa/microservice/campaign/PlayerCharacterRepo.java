package sofa.microservice.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofa.microservice.campaign.entity.PlayerCharacter;

import java.util.List;

@Repository
public interface PlayerCharacterRepo extends JpaRepository<PlayerCharacter, Long> {
    List<PlayerCharacter> findAllByCampaignId(String campaignId);
    PlayerCharacter findByCharacterId(String characterId);
    boolean existsByCharacterId(String characterId);
}
