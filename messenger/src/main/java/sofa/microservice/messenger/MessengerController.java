package sofa.microservice.messenger;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofa.microservice.messenger.DTO.ConsoleMessageDTO;
import sofa.microservice.messenger.DTO.MessageDTO;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/messages")
public class MessengerController {

    private final MessengerService messengerService;

    public MessengerController(MessengerService messengerService) {
        this.messengerService = messengerService;
    }

    @PostMapping("/roll")
    public ResponseEntity<Integer> sendMessageToQueue(@RequestBody MessageDTO messageDTO) {
        messengerService.sendMessageRoll(messageDTO);
        return new ResponseEntity<>(5, HttpStatus.CREATED);
    }
    @PostMapping("/message")
    public ResponseEntity<Integer> sendConsoleMessage(@RequestBody ConsoleMessageDTO consoleMessageDTO) {
        log.info(consoleMessageDTO.toString());
        messengerService.sendConsoleMessage(consoleMessageDTO);
        return new ResponseEntity<>(5, HttpStatus.CREATED);
    }
}