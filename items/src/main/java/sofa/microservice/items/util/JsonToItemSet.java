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
                ItemSet itemSet = mapper.readValue(new File("src/main/resources/JegerPakke.json"), ItemSet.class);
                System.out.println("Jeger");
                return itemSet;
            }else if (i == 2){
                System.out.println("Trollman");
                ItemSet itemSet = mapper.readValue(new File("src/main/resources/TrolldomsPakke.json"), ItemSet.class);
                return itemSet;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
