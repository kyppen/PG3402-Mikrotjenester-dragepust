package sofa.microservice.playerCharacter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofa.microservice.playerCharacter.DTO.PlayerCharacterDTO;
import sofa.microservice.playerCharacter.entity.PlayerCharacter;

import java.util.List;

@Slf4j
@CrossOrigin (origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
public class PlayerCharacterController {
    private final PlayerCharacterService playerCharacterService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createCharacter(@RequestBody PlayerCharacterDTO playerCharacterDTO){
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setUserId(playerCharacterDTO.getUserId());
        playerCharacter.setCharacterName(playerCharacterDTO.getCharacterName());
        playerCharacter.setProfession(playerCharacterDTO.getProfession());
        playerCharacter.setSpecies(playerCharacterDTO.getSpecies());
        playerCharacter.setItemSetId(playerCharacterDTO.getItemSetId());
        Long id = playerCharacterService.createPlayerCharacter(playerCharacter);
        playerCharacterService.sendPostRequest(Long.toString(id), playerCharacter.getItemSetId());
        log.info("Character created and itemset added");
        return new ResponseEntity<>(true, HttpStatus.CREATED);
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
        PlayerCharacter playerCharacter;
        playerCharacter = playerCharacterService.getById(id);
        log.info("Character gotten by ID");
        return ResponseEntity.ok(playerCharacter);
    }
}
