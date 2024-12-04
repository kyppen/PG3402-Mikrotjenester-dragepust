package sofa.microservice.campaign;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofa.microservice.campaign.entity.Campaign;

import java.util.List;

@Repository
public interface CampaignRepo extends JpaRepository<Campaign, Long> {
    List<Campaign> findCampaignByUserId(String userId);
}
