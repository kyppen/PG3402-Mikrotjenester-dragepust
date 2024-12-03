package org.api.stats;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.api.stats.entity.ClassStats;
import org.api.stats.entity.StatsSet;
import org.api.stats.util.JsonToStatsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Service
public class StatsService {

    private final JsonToStatsSet jsonToStatsSet;

    @Autowired
    public StatsService(JsonToStatsSet jsonToStatsSet) {
        this.jsonToStatsSet = jsonToStatsSet;
    }

    public StatsSet getStatsForClass(int classId) {
        return jsonToStatsSet.getStatsSet(classId);
    }
}



