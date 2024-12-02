package sofa.microservice.campaign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sofa.microservice.campaign.DTO.CampaignDTO;
import sofa.microservice.campaign.entity.Campaign;

@RestController
@RequestMapping("/campaign")
@RequiredArgsConstructor
@Slf4j
public class CampaignController {
    private final CampaignService campaignService;

    @PostMapping("/create")
    public ResponseEntity<Campaign> createCampaign(@RequestBody CampaignDTO campaignDTO) {
        log.info("Create campaign: {}", campaignDTO);
        Campaign campaign = new Campaign();
        campaign.setCreatorId(campaignDTO.getCreatorId());
        campaign.setDescription(campaignDTO.getDescription());
        campaign.setName(campaignDTO.getName());
        return new ResponseEntity<>(campaign, HttpStatus.CREATED);
    }
}
