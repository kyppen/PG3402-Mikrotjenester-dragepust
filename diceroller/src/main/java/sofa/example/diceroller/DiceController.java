package sofa.example.diceroller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/dice")
public class DiceController {
    private final DiceService diceService;

    @GetMapping
    public ResponseEntity<Integer> roll() {
        diceService.sendMessage("wtf");
        return new ResponseEntity<>(5, HttpStatus.CREATED);
    }

    @GetMapping("/{amount}")
    public ResponseEntity<Integer> roll(@PathVariable int amount) {
        Integer result = diceService.rolling(amount);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
