package by.tsarankou.servicesong.web;

import by.tsarankou.servicesong.service.AudioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableRabbit
public class MessageListener {
    private final AudioService audioService;

    @RabbitListener(queues = "${broker.songs.deletingResourceQueue}")
    public void deleteMetaData(String payload) throws IOException {
        log.info("Listen to delete metadata ids: {}", payload);
        List<Integer> idsList = Arrays.stream(payload.split(","))
                .map(Integer::valueOf).toList();
        audioService
                .deleteAudioByIds(idsList);
    }
}
