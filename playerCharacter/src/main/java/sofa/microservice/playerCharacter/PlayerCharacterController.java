package sofa.microservice.playerCharacter;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofa.microservice.playerCharacter.DTO.PlayerCharacterDTO;
import sofa.microservice.playerCharacter.DTO.UserIDDTO;
import sofa.microservice.playerCharacter.entity.PlayerCharacter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
public class PlayerCharacterController {
    private final PlayerCharacterService playerCharacterService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createCharacter(@RequestBody PlayerCharacterDTO playerCharacterDTO){
        System.out.println("Post Controller");
        PlayerCharacter playerCharacter = new PlayerCharacter();
        System.out.println(playerCharacterDTO.getUserId() + " playercharacterDTO");
        playerCharacter.setUserId(playerCharacterDTO.getUserId());
        playerCharacter.setCharacterName(playerCharacterDTO.getCharacterName());
        playerCharacter.setJob(playerCharacterDTO.getJob());
        playerCharacter.setRace(playerCharacterDTO.getRace());
        System.out.println(playerCharacter.getId() + " playercharacter");
        playerCharacterService.createPlayerCharacter(playerCharacter);


        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
    @GetMapping("/getall")
    public ResponseEntity<List<PlayerCharacter>> getCharacters(){
        System.out.println("Get Controller");
        List<PlayerCharacter> AllCharacters;

        AllCharacters = playerCharacterService.getAllCharacters();
        return ResponseEntity.ok(AllCharacters);
    }
    @GetMapping("/getall/{userId}")
    public ResponseEntity<List<PlayerCharacter>> getCharactersByUserId(@PathVariable String userId){
        System.out.println("/getall " + userId);
        List<PlayerCharacter> UserCharacters;
        UserCharacters = playerCharacterService.getByUserId(userId);

        return ResponseEntity.ok(UserCharacters);
    }
}
