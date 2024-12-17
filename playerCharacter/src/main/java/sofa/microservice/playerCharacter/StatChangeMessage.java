package sofa.microservice.playerCharacter;

import lombok.NoArgsConstructor;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class StatChangeMessage  {
    private String characterId;
    private String statType;
    private String value;
}