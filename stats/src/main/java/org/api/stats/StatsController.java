package org.api.stats;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.api.stats.DTO.CharacterStatsDTO;
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
    public ResponseEntity<HttpStatus> updateHp(@RequestBody HpUpdateRequest request) {
        statsService.updateHp(request.getCharacterId(), request.getHpChange());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/magic")
    public ResponseEntity<Void> updateMagic(@RequestBody MagicUpdateRequest request) {
        statsService.updateMagic(request.getCharacterId(), request.getMagicChange());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/willpower")
    public ResponseEntity<Void> updateWillpower(@RequestBody WillpowerUpdateRequest request) {
        statsService.updateWillpower(request.getCharacterId(), request.getWillpowerChange());
        return ResponseEntity.ok().build();
    }

}

