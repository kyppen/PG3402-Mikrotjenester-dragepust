package sofa.example.diceroller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RequiredArgsConstructor
@RestController

@RequestMapping("/dice")
public class DiceController {
    private final DiceService diceService;

    @GetMapping("/roll/{DiceAmount}")
    public ResponseEntity<Integer> sendMessageToQueue(@PathVariable String DiceAmount) throws InterruptedException {
        log.info("Requested to roll dice");
        Integer result = diceService.rollDice(Integer.parseInt(DiceAmount));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
