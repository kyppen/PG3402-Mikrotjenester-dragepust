package sofa.microservice.messenger.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsoleMessageDTO {
    private String campaignId;
    private String message;
}

