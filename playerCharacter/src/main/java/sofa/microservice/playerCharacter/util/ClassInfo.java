package sofa.microservice.playerCharacter.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Objects;

import sofa.microservice.playerCharacter.DTO.ClassDTO;


public class ClassInfo {


    public static ClassDTO getClassInfo(String profession){
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Profession name: " + profession);
        try{
            if("Jeger".equalsIgnoreCase(profession)){
                System.out.println(ClassDTO.class);
                return mapper.readValue(new File("src/main/resources/JegerStats.json"), ClassDTO.class);
            }else if ("Shaman".equalsIgnoreCase(profession)) {
                System.out.println(profession);
                return mapper.readValue(new File("src/main/resources/ShamanStats.json"), ClassDTO.class);
            }else if ("Skald".equalsIgnoreCase(profession)) {
                System.out.println(profession);
                return mapper.readValue(new File("src/main/resources/SkaldStats.json"), ClassDTO.class);
            } else {
                System.out.println("No class found");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
