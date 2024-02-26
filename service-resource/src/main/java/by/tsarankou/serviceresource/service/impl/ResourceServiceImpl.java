package by.tsarankou.serviceresource.service.impl;

import by.tsarankou.serviceresource.client.AudioClient;
import by.tsarankou.serviceresource.data.Resource;
import by.tsarankou.serviceresource.data.repository.ResourceRepository;
import by.tsarankou.serviceresource.dto.MetaDataDTO;
import by.tsarankou.serviceresource.service.MetaDataService;
import by.tsarankou.serviceresource.service.ResourceService;
import by.tsarankou.serviceresource.service.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    final ResourceRepository resourceRepository;
    final MetaDataService metaDataService;
    final AudioClient audioClient;
    @Override
    public int uploadDataFile(byte[] audioFile) {
        Resource audioResourceFile = Resource.builder()
                .build();

        audioResourceFile
                .setAudioFile(audioFile);
        resourceRepository.save(audioResourceFile);
        log.info("Created resource with id: {}", audioResourceFile.getId());
        try {
        MetaDataDTO audioMetaData = metaDataService
                   .getMetaDataFromFile(createTemporaryAudioFile(audioFile));
        audioMetaData.setResourceId(audioResourceFile.getId());
        audioClient.ping(audioMetaData);
        } catch (Exception e) {
            //todo
        }
        return audioResourceFile.getId();
    }

    @Override
    @Transactional
    public Resource findResourceById(Integer id) {
        Resource resource = resourceRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("Found resource with id: {}", resource.getId());
        return resource;
    }

    @Override
    @Transactional
    public Integer[] deleteAllResourcesByIds(Integer[] ids) {
        List<Integer> audioList = resourceRepository.findExistingIds(List.of(ids));

        ids = audioList.toArray(Integer[]::new);

        resourceRepository.deleteAllByIdIn(List.of(ids));
        log.info("Deleted resource with id: {}",
                Arrays.stream(ids).map(String::valueOf).collect(Collectors.joining(",")));

        return ids;
    }

    private File createTemporaryAudioFile(byte[] audioFile) throws IOException {
        File tempAudioFile = File.createTempFile("audio", ".mp3");
        FileOutputStream fos = new FileOutputStream(tempAudioFile);
        fos.write(audioFile);
        return tempAudioFile;
    }
}
