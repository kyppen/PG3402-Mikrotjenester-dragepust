package org.api.stats;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.api.stats.DTO.CharacterStatsDTO;
import org.api.stats.DTO.StatUpdateRequestDTO;
import org.api.stats.DTO.StatsDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsService {

    private RestTemplate restTemplate = new RestTemplate(); // No need?
    private final RabbitTemplate rabbitTemplate;


    //producer
    public void sendRabbitMessage(StatsDTO statsDTO){
        try{
            String stringDTO = new ObjectMapper().writeValueAsString(statsDTO);
            rabbitTemplate.convertAndSend("stats-queue", stringDTO);
            log.info("RabbitMQ StatsDTO string sent to  stats-queue");
        }catch (Exception e){
            log.error("Failed to convert StatsDTO to String");
        }

    }

    //funker
    public void updateHp(StatUpdateRequestDTO statUpdateRequestDTO) {
        CharacterStatsDTO characterStatsDTO = new CharacterStatsDTO();
        characterStatsDTO.setHp(statUpdateRequestDTO.getValue());
        StatsDTO statsDTO = new StatsDTO("hp", statUpdateRequestDTO.getValue(), statUpdateRequestDTO.getCharacterId());
        sendRabbitMessage(statsDTO);
    }


    public void updateMagic(StatUpdateRequestDTO statUpdateRequestDTO) {
        CharacterStatsDTO characterStatsDTO = new CharacterStatsDTO();
        characterStatsDTO.setMagic(statUpdateRequestDTO.getValue());
        StatsDTO statsDTO = new StatsDTO("magic", statUpdateRequestDTO.getValue(), statUpdateRequestDTO.getCharacterId());
        sendRabbitMessage(statsDTO);
    }


    public void updateWillpower(StatUpdateRequestDTO statUpdateRequestDTO) {
        CharacterStatsDTO characterStatsDTO = new CharacterStatsDTO();
        characterStatsDTO.setWillpower(statUpdateRequestDTO.getValue());
        StatsDTO statsDTO = new StatsDTO("will", statUpdateRequestDTO.getValue(), statUpdateRequestDTO.getCharacterId());
        sendRabbitMessage(statsDTO);
    }




}
