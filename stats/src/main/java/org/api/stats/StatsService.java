package org.api.stats;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.api.stats.DTO.CharacterStatsDTO;
import org.api.stats.DTO.HpUpdateDTO;
import org.api.stats.DTO.StatsDTO;
import org.api.stats.idk.CharacterStatsMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsService {

    private RestTemplate restTemplate = new RestTemplate(); // No need?
    private final RabbitTemplate rabbitTemplate;



    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("stat-queue", message);
        System.out.println("Message sent to queue: " + message);
    }


    //Updates all stats (maybe?)
    public void updateStats(Long characterId, CharacterStatsDTO stats) {
        CharacterStatsMessage message = new CharacterStatsMessage(characterId.toString(), stats);
        rabbitTemplate.convertAndSend("stats-exchange", "stats-routing-key", message);
        System.out.println("Stat update message sent to RabbitMQ: " + message);
    }

    //producer
    public void sendRabbitMessage(StatsDTO statsDTO){
        try{
            String stringDTO = new ObjectMapper().writeValueAsString(statsDTO);
            rabbitTemplate.convertAndSend("stats-queue", stringDTO);
            log.info("statsDTO added to stats-queue");
        }catch (Exception e){
            log.error("Failed to convert StatsDTO to String");
        }

    }

    //funker
    public void updateHp(HpUpdateDTO hpUpdateDTO) {
        CharacterStatsDTO characterStatsDTO = new CharacterStatsDTO();
        characterStatsDTO.setHp(hpUpdateDTO.getValue());
        StatsDTO statsDTO = new StatsDTO("hp", hpUpdateDTO.getValue(), hpUpdateDTO.getCharacterId());
        sendRabbitMessage(statsDTO);
    }


    public void updateMagic(Long characterId, int magicChange) {
        CharacterStatsDTO stats = new CharacterStatsDTO();
        stats.setMagic(magicChange);
        updateStats(characterId, stats);
    }


    public void updateWillpower(Long characterId, int willpowerChange) {
        CharacterStatsDTO stats = new CharacterStatsDTO();
        stats.setWillpower(willpowerChange);
        updateStats(characterId, stats);
    }




}
