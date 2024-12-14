package sofa.example.diceroller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofa.example.diceroller.DTO.ConsoleMessageDTO;
import sofa.example.diceroller.DTO.MessageDTO;


@Slf4j
@RequiredArgsConstructor
@RestController
//@CrossOrigin (origins = "http://localhost:5173")
@RequestMapping("/dice")
public class DiceController {
    private final DiceService diceService;

    @GetMapping("/roll/{DiceAmount}")
    public ResponseEntity<Integer> sendMessageToQueue(@PathVariable String DiceAmount) {
        log.info("Requested to roll dice");
        Integer result = diceService.rollDice(Integer.parseInt(DiceAmount));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
