package sofa.microservice.playerCharacter.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import sofa.microservice.playerCharacter.DTO.ClassDTO;


@Slf4j
public class ClassInfo {

    public ClassDTO getClassInfo(String profession){
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Profession name: " + profession);
        try{
            if("Jeger".equalsIgnoreCase(profession)){
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stats/JegerStats.json");
                if (inputStream == null) {
                    throw new FileNotFoundException("File not found: sets/JegerStats.json");
                }
                log.info("Returning inputstream class JegerStats");
                return mapper.readValue(inputStream, ClassDTO.class);
            }else if ("Shaman".equalsIgnoreCase(profession)) {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stats/ShamanStats.json");
                if (inputStream == null) {
                    throw new FileNotFoundException("File not found: stats/ShamanStats.json");
                }
                log.info("Returning inputstream class ShamanStats");
                return mapper.readValue(inputStream, ClassDTO.class);
            }else if ("Skald".equalsIgnoreCase(profession)) {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stats/SkaldStats.json");
                if (inputStream == null) {
                    throw new FileNotFoundException("File not found: stats/SkaldStats.json");
                }
                log.info("Returning inputstream class SkaldStats");
                return mapper.readValue(inputStream, ClassDTO.class);
            } else {
                System.out.println("No class found");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
