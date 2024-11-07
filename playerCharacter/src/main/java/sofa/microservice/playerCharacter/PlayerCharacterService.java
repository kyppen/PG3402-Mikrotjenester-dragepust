package sofa.microservice.playerCharacter;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sofa.microservice.playerCharacter.entity.PlayerCharacter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerCharacterService {
    private final PlayerCharacterRepository playerCharacterRepository;
    private final RestTemplate restTemplate = new RestTemplate();


    //Creates a character and returns the character ID to add itemset
    public Long createPlayerCharacter(PlayerCharacter character) {
        System.out.println("Service");
        playerCharacterRepository.save(character);
        Long id = character.getId();
        System.out.println("CHARACTER ID MAYBE? " + id);
        return id;
    }
    public List<PlayerCharacter> getAllCharacters() {
        return playerCharacterRepository.findAll();
    }
    public List<PlayerCharacter> getByUserId(String userId) {
        return playerCharacterRepository.findAllByUserId(userId);
    }
    public PlayerCharacter getById(String id) {
        return playerCharacterRepository.findPlayerCharacterById(Long.parseLong(id));
    }
    public void sendPostRequest(String characterId, String itemSetId){
        String url = "http://localhost:8082/items/set/" + characterId + "/" + itemSetId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        System.out.println(response.getBody());
        System.out.println("Added Items to character " + characterId + "itemSet" + itemSetId);
    }
}
