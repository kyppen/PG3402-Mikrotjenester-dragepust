package sofa.microservice.playerCharacter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sofa.microservice.playerCharacter.DTO.CampaignInfoDTO;
import sofa.microservice.playerCharacter.DTO.ClassDTO;
import sofa.microservice.playerCharacter.DTO.StatsDTO;
import sofa.microservice.playerCharacter.entity.PlayerCharacter;
import sofa.microservice.playerCharacter.util.ClassInfo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "servicenames")
@Slf4j
public class PlayerCharacterService {
    private final PlayerCharacterRepository playerCharacterRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${servicenames.itemservice}")
    String itemservice;
    @Value("${servicenames.campaignservice}")
    String campaignservice;

    //Creates a character and returns the character ID to add itemset
    public Long createPlayerCharacter(PlayerCharacter character) {
        System.out.println("Service");
        playerCharacterRepository.save(character);
        Long id = character.getId();
        System.out.println("CHARACTER ID MAYBE? " + id);
        return id;
    }
    public boolean deletePlayerCharacter(String characterId){
        log.info("Service Delete");
        if(playerCharacterRepository.existsById(Long.parseLong(characterId))){
            log.info("Character to delete exists");
            log.info("Deleting character Items");
            if(sendDeleteCharacterItemsRequest(characterId)){
                log.info("Items have been removed");
            }else{
                log.warn("There was an issue with the response from itemservice");
            }
            if(sendDeleteCharacterCampaignRequest(characterId)){
                log.info("campaignCharacter has been removed");
            }else{
                log.warn("There was an issue with the response from campaignservice");
            }

        }else{
            log.info("Character does not exist");
        }
        return true;
    }
    public CampaignInfoDTO getCampaignInfoByCharacterId(String characterId){
        return sendCampaignIdRequest(characterId);
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
    public boolean sendDeleteCharacterItemsRequest(String characterId) {
        String url = String.format("http://%s:8082/items/character/delete/%s", itemservice, characterId);
        log.info("ItemService url: {}", url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);
        // hvofor er det ikke noe deleteForEntity her som i alle de andre methodene? restTemplate.delete(url);
        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Boolean.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Items has been removed from character" + response.getBody());
            return true;
        } else {
            log.info("Error in response " + response.getStatusCode());
            return false;
        }
    }
    public CampaignInfoDTO sendCampaignIdRequest(String characterId){
        String url = String.format("http://%s:8085/campaign/character/%s", campaignservice, characterId);
        log.info("campaignservice id url: {}", url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        ResponseEntity<CampaignInfoDTO> response = restTemplate.getForEntity(url, CampaignInfoDTO.class);
        // Print the response status code
        if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
            log.info("character is not in a campaign");
            return null;
        }
        if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(202))){
            log.info("character is in a campaign");
            return response.getBody();
        }
        return null;
    }
    public boolean sendDeleteCharacterCampaignRequest(String characterId){
        String url = String.format("http://%s:8085/campaign/character/delete/%s", campaignservice, characterId);
        log.info("CampagignService url: {}" , url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);
        // hvofor er det ikke noe deleteForEntity her som i alle de andre methodene? restTemplate.delete(url);
        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Boolean.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Character removed from campaign " + response.getBody());
            return true;
        } else {
            log.info("Something when wrong in campaign response: " + response.getStatusCode());
            return false;
        }
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
    public boolean linkUserToCharacter(String characterId) {
        Optional<PlayerCharacter> characterOpt = playerCharacterRepository.findById(Long.valueOf(characterId));
        if (characterOpt.isPresent()) {
            PlayerCharacter character = characterOpt.get();
            character.setUserId("1");
            playerCharacterRepository.save(character);
            return true;
        } else {
            log.error("Character with ID {} not found", characterId);
            return false;
        }
    }
}
