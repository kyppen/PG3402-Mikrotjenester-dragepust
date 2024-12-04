package sofa.microservice.campaign.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageDTO {
    private String campaignId;
    private String playerCharacterId;
    private String message;
}
