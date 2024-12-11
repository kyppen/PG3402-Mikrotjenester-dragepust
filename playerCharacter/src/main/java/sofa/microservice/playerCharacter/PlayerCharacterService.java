package sofa.microservice.playerCharacter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sofa.microservice.playerCharacter.DTO.ClassDTO;
import sofa.microservice.playerCharacter.DTO.StatsDTO;
import sofa.microservice.playerCharacter.entity.PlayerCharacter;
import sofa.microservice.playerCharacter.util.ClassInfo;

import java.util.List;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "servicenames")
@Slf4j
public class PlayerCharacterService {
    private final PlayerCharacterRepository playerCharacterRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${servicenames.itemservice}")
    String itemservice;

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
        String url = String.format("http://%s:8082/items/set/%s/%s", itemservice, characterId, itemSetId);
        log.info("ItemService url: {}" , url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        System.out.println(response.getBody());
        System.out.println("Added Items to character " + characterId + "itemSet" + itemSetId);
    }
    public void addStats(PlayerCharacter playerCharacter) {
        ClassInfo classInfoService = new ClassInfo();
        ClassDTO classDTO = classInfoService.getClassInfo(playerCharacter.getProfession());
        if (classDTO != null) {
            playerCharacter.setMagic(classDTO.getMagic());
        }
        playerCharacter.setSkills(classDTO.getSkills());
        playerCharacter.setBaseMagic(classDTO.getBaseMagic());
        playerCharacter.setBaseWillpower(classDTO.getBaseWillpower());
        playerCharacter.setBaseHP(classDTO.getBaseHP());
        playerCharacterRepository.save(playerCharacter);
    }
    public void updateStats(Long characterId, StatsDTO updatedStats) {
        PlayerCharacter character = playerCharacterRepository.findById(characterId).orElseThrow(() -> new RuntimeException("Character not found"));
        character.setBaseHP(updatedStats.getHpChange());
        character.setBaseWillpower(updatedStats.getWillpowerChange());
        character.setBaseMagic(updatedStats.getMagicChange());
        playerCharacterRepository.save(character);
    }
}
