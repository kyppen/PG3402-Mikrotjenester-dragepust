package sofa.microservice.messenger.DTO;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MessageDTO {
    private String campaignId;
    private String characterId;
    //This string doesn't get saved or displayed
    // It may later be used to specify what type of roll a character is doing
    private String message;
}
