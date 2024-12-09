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
    private String message;
}
