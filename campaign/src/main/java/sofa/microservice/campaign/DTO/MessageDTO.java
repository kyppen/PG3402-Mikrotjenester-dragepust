package sofa.microservice.campaign.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDTO {
    private String type;
    private String campaignId;
    private String characterId;
    private String message;
}
