package sofa.microservice.playerCharacter.DTO;

import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ClassDTO {
    private String profession;
    private int baseHP;
    private int baseMagic;
    private int baseWillpower;
    @ElementCollection
    private List<String> skills;
    @ElementCollection
    private List<String> magic;
}
