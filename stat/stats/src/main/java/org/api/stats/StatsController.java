package org.api.stats;

import lombok.RequiredArgsConstructor;
import org.api.stats.entity.ClassStats;
import org.api.stats.entity.StatsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService classStatsService) {
        this.statsService = classStatsService;
    }

    @GetMapping("/{classId}")
    public ResponseEntity<StatsSet> getClassStats(@PathVariable Integer classId) {
        try {
            StatsSet statsSet = statsService.getStatsForClass(classId);
            if (statsSet != null) {
                return ResponseEntity.ok(statsSet);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}


