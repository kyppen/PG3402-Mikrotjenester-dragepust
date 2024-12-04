package sofa.microservice.campaign.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "MESSAGE")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String campaignId;
    private String playerCharacterId;
    private String message;

    public Message(String campaignId, String playerCharacterId, String message) {
        this.campaignId = campaignId;
        this.playerCharacterId = playerCharacterId;
        this.message = message;
    }
}
