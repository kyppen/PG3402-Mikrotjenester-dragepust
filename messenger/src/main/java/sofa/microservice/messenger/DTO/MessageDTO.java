package sofa.microservice.messenger.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDTO {
    private String campaignId;
    private String characterId;
    //This string doesn't get saved or displayed
    // It may later be used to specify what type of roll a character is doing
    private String type;
    private String message;
}
