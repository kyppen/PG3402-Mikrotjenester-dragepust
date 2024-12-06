package sofa.microservice.playerCharacter.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StatsDTO {
    private int willpowerChange;
    private int hpChange;
    private int magicChange;
}
