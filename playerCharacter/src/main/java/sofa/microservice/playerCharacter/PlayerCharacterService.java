package sofa.microservice.playerCharacter;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sofa.microservice.playerCharacter.entity.PlayerCharacter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerCharacterService {
    private final PlayerCharacterRepository playerCharacterRepository;

    public void createPlayerCharacter(PlayerCharacter character) {
        System.out.println("Service");
        System.out.println(character.getId()+ "Service");
        playerCharacterRepository.save(character);
    }
    public List<PlayerCharacter> getAllCharacters() {
        return playerCharacterRepository.findAll();
    }
    public List<PlayerCharacter> getByUserId(String userId) {
        return playerCharacterRepository.findAllByUserId(userId);
    }
}
