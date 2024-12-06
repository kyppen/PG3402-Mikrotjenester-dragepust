package org.api.stats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class StatsService {
    @Autowired
    private RestTemplate restTemplate;

    // Update stats for a character
    public void updateStats(Long characterId, CharacterStats updatedStats) {
        // Forward the updated stats to CharacterService
        saveUpdatedStatsToCharacterService(characterId, updatedStats);
        log.info("Updated stats for character {}", characterId);
    }

    // Save updated stats to CharacterService
    private void saveUpdatedStatsToCharacterService(Long characterId, CharacterStats updatedStats) {
        String characterServiceUrl = "http://localhost:8081/character/" + characterId + "/stats";
        log.info("Saving updated stats to CharacterService: {}", characterServiceUrl);
        log.info("Updated stats: {}", updatedStats.toString());
        restTemplate.postForEntity(characterServiceUrl, updatedStats, Void.class);
    }
}
