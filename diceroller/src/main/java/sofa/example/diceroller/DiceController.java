package sofa.example.diceroller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofa.example.diceroller.DTO.ConsoleMessageDTO;
import sofa.example.diceroller.DTO.MessageDTO;


@RequiredArgsConstructor
@RestController
@RequestMapping("/dice")
public class DiceController {
    private final DiceService diceService;

    @PostMapping("/roll")
    public ResponseEntity<Integer> sendMessageToQueue(@RequestBody MessageDTO messageDTO) {
        diceService.sendMessageRoll(messageDTO);
        return new ResponseEntity<>(5, HttpStatus.CREATED);
    }

    @PostMapping("/message")
    public ResponseEntity<Integer> sendConsoleMessage(@RequestBody ConsoleMessageDTO consoleMessageDTO) {
        diceService.sendConsoleMessage(consoleMessageDTO);
        return new ResponseEntity<>(5, HttpStatus.CREATED);
    }

}
