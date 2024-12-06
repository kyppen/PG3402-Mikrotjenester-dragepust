package org.api.stats;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin (origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/stats")
@Slf4j
public class StatsController {
    @Autowired
    private StatsService statsService;

    @PostMapping("/{characterId}")
    public void updateStats(@PathVariable Long characterId, @RequestBody CharacterStats updatedStats) {
        log.info("Updating stats for character {}", characterId);
        log.info("Updated stats: {}", updatedStats.toString());
        statsService.updateStats(characterId, updatedStats);
    }
}

