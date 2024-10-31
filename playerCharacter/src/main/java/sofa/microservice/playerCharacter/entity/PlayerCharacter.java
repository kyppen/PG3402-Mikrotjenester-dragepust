package sofa.microservice.playerCharacter.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sofa.microservice.playerCharacter.DTO.PlayerCharacterDTO;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class PlayerCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userId;
    private String characterName;
    private String job;
    private String race;


}
