package sofa.microservice.playerCharacter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofa.microservice.playerCharacter.DTO.CampaignInfoDTO;
import sofa.microservice.playerCharacter.DTO.PlayerCharacterDTO;
import sofa.microservice.playerCharacter.DTO.StatsDTO;
import sofa.microservice.playerCharacter.DTO.UserIDDTO;
import sofa.microservice.playerCharacter.entity.PlayerCharacter;
import sofa.microservice.playerCharacter.util.ClassInfo;

import java.util.List;

@Slf4j
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
    public ResponseEntity<HttpStatus> deleteCharacter(@PathVariable String characterId){
        log.info("Delete request on characterId: {}", characterId);
        playerCharacterService.deletePlayerCharacter(characterId);
        return new ResponseEntity<>(HttpStatus.OK);
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
    @GetMapping("/{characterId}")
    public ResponseEntity<PlayerCharacter> getCharacterById(@PathVariable String characterId){
        log.info("Attempted to get character by id");
        PlayerCharacter playerCharacter;
        playerCharacter = playerCharacterService.getById(characterId);
        log.info("Character gotten by ID");
        return ResponseEntity.ok(playerCharacter);
    }
    @GetMapping("/campaigninfo/{characterId}")
    public ResponseEntity<CampaignInfoDTO> getCampaignInfo(@PathVariable String characterId){
        log.info("campaigninfo/{characterId} with id: {}", characterId);
        CampaignInfoDTO campaignInfoDTO = playerCharacterService.getCampaignInfoByCharacterId(characterId);
        if(campaignInfoDTO == null){
            log.info("campaignInfoDTO is NULL");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        log.info("Sending campaignInfoDTO {}", campaignInfoDTO);
        return new ResponseEntity<>(campaignInfoDTO, HttpStatus.OK);
    }
    @PostMapping("/{characterId}/stats")
    public void updateCharacterStats(@PathVariable Long characterId, @RequestBody StatsDTO updatedStats) {
        playerCharacterService.updateStats(characterId, updatedStats);
    }
    @PostMapping("/link-user")
    public ResponseEntity<Void> linkUserToCharacter(@RequestBody UserIDDTO useridDTO) {
        log.info("Linking user to character: {}", useridDTO);

        boolean linked = playerCharacterService.linkUserToCharacter(
                useridDTO.getUserId()
        );

        if (linked) {
            log.info("Successfully linked user {}", useridDTO.getUserId());
            return ResponseEntity.ok().build();
        } else {
            log.warn("Failed to link user {}", useridDTO.getUserId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
