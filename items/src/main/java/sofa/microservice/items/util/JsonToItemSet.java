package sofa.microservice.items.util;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sofa.microservice.items.ItemSetDTO.ItemSet;

import java.io.File;
import java.io.IOException;

public class JsonToItemSet {

    public ItemSet getItemSet(Integer i){
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Itemset id " + i);
        try{
            if(i == 1){
                return mapper.readValue(new File("src/main/resources/JegerPakke.json"), ItemSet.class);
            }else if (i == 2){
                System.out.println("Trollman");
                return mapper.readValue(new File("src/main/resources/TrolldomsPakke.json"), ItemSet.class);
            }else if (i == 3) {
                System.out.println("Skald");
                return mapper.readValue(new File("src/main/resources/SkaldPakke.json"), ItemSet.class);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
