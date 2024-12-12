package sofa.microservice.playerCharacter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofa.microservice.playerCharacter.DTO.PlayerCharacterDTO;
import sofa.microservice.playerCharacter.DTO.StatsDTO;
import sofa.microservice.playerCharacter.entity.PlayerCharacter;
import sofa.microservice.playerCharacter.util.ClassInfo;

import java.util.List;

@Slf4j
//@CrossOrigin (origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
public class PlayerCharacterController {
    private final PlayerCharacterService playerCharacterService;


    @GetMapping("/class/{profession}")
    public void getClassInfo(@PathVariable String profession) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.getClassInfo(profession);

    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createCharacter(@RequestBody PlayerCharacterDTO playerCharacterDTO){
        System.out.println(playerCharacterDTO.toString());
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setUserId(playerCharacterDTO.getUserId());
        playerCharacter.setCharacterName(playerCharacterDTO.getCharacterName());
        playerCharacter.setProfession(playerCharacterDTO.getProfession());
        playerCharacter.setSpecies(playerCharacterDTO.getSpecies());
        playerCharacter.setItemSetId(playerCharacterDTO.getItemSetId());
        Long id = playerCharacterService.createPlayerCharacter(playerCharacter);
        playerCharacterService.sendPostRequest(Long.toString(id), playerCharacter.getItemSetId());
        playerCharacterService.addStats(playerCharacter);
        log.info("Character created and itemset added");
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{characterId}")
    public ResponseEntity<Boolean> deleteCharacter(@PathVariable String characterId){
        log.info("Delete request on characterId: {}", characterId);
        playerCharacterService.deletePlayerCharacter(characterId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<PlayerCharacter>> getCharacters(){
        System.out.println("Get Controller");
        List<PlayerCharacter> AllCharacters;
        AllCharacters = playerCharacterService.getAllCharacters();
        return ResponseEntity.ok(AllCharacters);
    }
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<PlayerCharacter>> getCharactersByUserId(@PathVariable String userId){
        System.out.println("/getall " + userId);
        List<PlayerCharacter> UserCharacters;
        UserCharacters = playerCharacterService.getByUserId(userId);
        return ResponseEntity.ok(UserCharacters);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PlayerCharacter> getCharacterById(@PathVariable String id){
        log.info("Attempted to get character by id");
        PlayerCharacter playerCharacter;
        playerCharacter = playerCharacterService.getById(id);
        log.info("Character gotten by ID");
        return ResponseEntity.ok(playerCharacter);
    }
    @PostMapping("/{characterId}/stats")
    public void updateCharacterStats(@PathVariable Long characterId, @RequestBody StatsDTO updatedStats) {
        playerCharacterService.updateStats(characterId, updatedStats);
    }
}
