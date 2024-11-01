package sofa.example.diceroller;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Service
public class DiceService {
    public Integer rolling(int amount){

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        Integer rolled = 0;
        for(int i = 0; i < amount; i++){
            rolled += random.nextInt(6) + 1;
        }
        return rolled;
    }
}
