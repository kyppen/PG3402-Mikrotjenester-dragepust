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
@RequestMapping("/messages")
public class MessengerController {

    private final MessengerService messengerService;

    public MessengerController(MessengerService messengerService) {
        this.messengerService = messengerService;
    }

    @PostMapping("/roll")
    public ResponseEntity<Integer> sendMessageToQueue(@RequestBody MessageDTO messageDTO) {
        log.info("WTF");
        messengerService.sendMessageRoll(messageDTO);
        return new ResponseEntity<>(2, HttpStatus.CREATED);
    }
    @PostMapping("/message")
    public ResponseEntity<Integer> sendConsoleMessage(@RequestBody ConsoleMessageDTO consoleMessageDTO) {
        log.info(consoleMessageDTO.toString());
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(consoleMessageDTO.getMessage());
        messageDTO.setCampaignId(consoleMessageDTO.getCampaignId());
        //very janky solution
        //This allows console to type in chat
        messageDTO.setCharacterId("CONSOLE");
        messageDTO.setType("CONSOLE");
        messengerService.sendConsoleMessage(messageDTO);
        return new ResponseEntity<>(2, HttpStatus.CREATED);
    }
}