package sofa.microservice.campaign;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofa.microservice.campaign.entity.Campaign;

@Repository
public interface CampaignRepo extends JpaRepository<Campaign, Long> {
}