package sofa.microservice.campaign;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CampaignService {
    @Autowired
    private final CampaignRepo campaignRepo;

}
