package org.api.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.api.stats.DTO.CharacterStatsDTO;
import org.api.stats.DTO.StatsDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsService {
    @Autowired
    private RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("stat-queue", message);
        System.out.println("Message sent to queue: " + message);
    }

    public void updateStats(Long characterId, CharacterStatsDTO stats) {
        CharacterStatsMessage message = new CharacterStatsMessage(characterId.toString(), stats);
        rabbitTemplate.convertAndSend("stats-exchange", "stats-routing-key", message);
        System.out.println("Stat update message sent to RabbitMQ: " + message);
    }


    public void updateHp(Long characterId, int hpChange) {
        CharacterStatsDTO stats = new CharacterStatsDTO();
        stats.setHp(hpChange);
        updateStats(characterId, stats);
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
