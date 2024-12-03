package org.api.stats.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.stats.entity.StatsSet;
import org.springframework.stereotype.Component;

import java.io.File;
@Component
public class JsonToStatsSet {

    public StatsSet getStatsSet(Integer i){
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Statsset id " + i);
        try{
            if(i == 1){
                return mapper.readValue(new File("src/main/resources/JegerStats.json"), StatsSet.class);
            }else if (i == 2){
                System.out.println("Shaman");
                return mapper.readValue(new File("src/main/resources/ShamanStats.json"), StatsSet.class);
            }else if (i == 3) {
                System.out.println("Skald");
                return mapper.readValue(new File("src/main/resources/SkaldStats.json"), StatsSet.class);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}