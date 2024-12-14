package org.api.stats;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.api.stats.DTO.CharacterStatsDTO;
import org.api.stats.DTO.StatUpdateRequestDTO;
import org.api.stats.idk.MagicUpdateRequest;
import org.api.stats.idk.WillpowerUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin (origins = "http://localhost:5173")
@RestController
@RequestMapping("/stats")
@Slf4j
@RequiredArgsConstructor
public class StatsController {
    @Autowired
    private final StatsService statsService;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        statsService.sendMessage(message);
        return "Message sent: " + message;
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateStats(@RequestBody CharacterStatsDTO stats) {
        statsService.updateStats(stats.getCharacterId(), stats);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/hp")
    public ResponseEntity<HttpStatus> updateHp(@RequestBody StatUpdateRequestDTO statUpdateRequestDTO) {
        log.info("updateHp: {}",statUpdateRequestDTO);
        statsService.updateHp(statUpdateRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/magic")
    public ResponseEntity<Void> updateMagic(@RequestBody StatUpdateRequestDTO statUpdateRequestDTO) {
        log.info("updateMagic: {}",statUpdateRequestDTO);
        statsService.updateMagic(statUpdateRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/willpower")
    public ResponseEntity<Void> updateWillpower(@RequestBody StatUpdateRequestDTO statUpdateRequestDTO) {
        log.info("updateWillpower: {}",statUpdateRequestDTO);
        statsService.updateWillpower(statUpdateRequestDTO);
        return ResponseEntity.ok().build();
    }

}

