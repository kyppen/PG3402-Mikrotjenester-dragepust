package sofa.example.diceroller;


import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;


import java.util.Random;

@Slf4j
@Service
public class DiceService {

    private final RestTemplate restTemplate = new RestTemplate();

    //Casts a dice
    public Integer rollDice(Integer nr){
        int diceResults = 0;
        for(int i = 0; i < nr; i++){
            long seed = System.currentTimeMillis();
            Random random = new Random(seed);

            int roll = random.nextInt(6) + 1;
            diceResults += roll;
        }
        log.info("Dice cast: {} outcome: {}", nr, diceResults);
        return diceResults;
    }

}
