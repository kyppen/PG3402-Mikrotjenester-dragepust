package sofa.microservice.items.util;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import sofa.microservice.items.ItemSetDTO.ItemSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class JsonToItemSet {

    public ItemSet getItemSet(Integer i){
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Itemset id " + i);
        try{
            if(i == 1){
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sets/JegerPakke.json");
                if (inputStream == null) {
                    throw new FileNotFoundException("File not found: sets/JegerPakke.json");
                }
                log.info("Returning inputstream class JegerPakke");
                return mapper.readValue(inputStream, ItemSet.class);
            }else if (i == 2){
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sets/TrolldomsPakke.json");
                if (inputStream == null) {
                    throw new FileNotFoundException("File not found: sets/JegerPakke.json");
                }
                log.info("Returning inputstream class Trolldom");
                return mapper.readValue(inputStream, ItemSet.class);
            }else if (i == 3) {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sets/SkaldPakke.json");
                if (inputStream == null) {
                    throw new FileNotFoundException("File not found: sets/JegerPakke.json");
                }
                log.info("Returning inputstream class Skald");
                return mapper.readValue(inputStream, ItemSet.class);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
