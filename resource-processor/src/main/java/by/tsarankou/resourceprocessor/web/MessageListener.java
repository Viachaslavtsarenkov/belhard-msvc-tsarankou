package by.tsarankou.resourceprocessor.web;

import by.tsarankou.resourceprocessor.client.ResourceClient;
import by.tsarankou.resourceprocessor.client.SongClient;
import by.tsarankou.resourceprocessor.dto.MetaDataDTO;
import by.tsarankou.resourceprocessor.service.MetaDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableRabbit
public class MessageListener {

    private final ResourceClient resourceClient;
    private final SongClient songClient;
    private final MetaDataService metaDataService;

    @RabbitListener(queues = "${broker.resource.create.Queue}")
    public void ping(String payload) throws IOException, TikaException, SAXException {
        int id = Integer.parseInt(payload.split(":")[1]);
        byte[] resource = resourceClient.getResource(id);
        File convFile = new File("music.mp3");
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write(resource);
        fos.close();
        MetaDataDTO metaData =  metaDataService.getMetaDataFromFile(convFile);
        metaData.setId(id);
        songClient.sentMetadataToSongsService(metaData);
        log.info("Received message about creating resource with payload: {}", payload + id);
    }
}
