package sofa.example.diceroller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofa.example.diceroller.DTO.MessageDTO;


@RequiredArgsConstructor
@RestController
@RequestMapping("/dice")
public class DiceController {
    private final DiceService diceService;

    @PostMapping("/message")
    public ResponseEntity<Integer> sendMessageToQueue(@RequestBody MessageDTO messageDTO) {
        diceService.sendMessage(messageDTO);
        return new ResponseEntity<>(5, HttpStatus.CREATED);
    }

    @GetMapping("/roll")
    public ResponseEntity<Integer> roll(@RequestBody MessageDTO messageDTO) {
        Integer result = diceService.rolling(2);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
