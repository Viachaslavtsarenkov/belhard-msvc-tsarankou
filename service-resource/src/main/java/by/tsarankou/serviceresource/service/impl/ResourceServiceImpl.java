package by.tsarankou.serviceresource.service.impl;

import by.tsarankou.serviceresource.client.AudioClient;
import by.tsarankou.serviceresource.client.FileStorageClient;
import by.tsarankou.serviceresource.data.Resource;
import by.tsarankou.serviceresource.data.repository.ResourceRepository;
import by.tsarankou.serviceresource.dto.IdDTO;
import by.tsarankou.serviceresource.dto.IdsDTO;
import by.tsarankou.serviceresource.dto.MetaDataDTO;
import by.tsarankou.serviceresource.service.MetaDataService;
import by.tsarankou.serviceresource.service.ResourceService;
import by.tsarankou.serviceresource.service.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final MetaDataService metaDataService;
    private final AudioClient audioClient;
    private final FileStorageClient fileStorageClient;
    @Override
    @Transactional
    public IdDTO uploadDataFile(File audioFile) throws IOException, TikaException, SAXException {
        Resource audioResourceFile = new Resource();

        String idSavedAudioFile = uploadToStorage(Files
                .readAllBytes(Path.of(audioFile.getPath())));
        audioResourceFile.setAudioFile(idSavedAudioFile);
        resourceRepository.save(audioResourceFile);
        log.info("Created resource with id: {}, AWS link {}", audioResourceFile.getId(), idSavedAudioFile);
        MetaDataDTO audioMetaData = metaDataService
                   .getMetaDataFromFile(audioFile);
        audioMetaData.setResourceId(audioResourceFile.getId());
        audioClient.ping(audioMetaData);
        return new IdDTO(audioResourceFile.getId());
    }

    @Override
    @Transactional
    public byte[] findResourceById(Integer id) throws IOException {
        Resource resource = resourceRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
        log.info("Found resource with id: {}", resource.getId());
        return  fileStorageClient.downloadResource(resource.getAudioFile());
    }

    @Override
    @Transactional
    public IdsDTO deleteAllResourcesByIds(List<Integer> ids) {
        List<Integer> audioList = resourceRepository
                .findExistingIds(ids);

        List<String>  bucketsIds = resourceRepository.findAllByIdIn(audioList)
                .stream()
                .map(Resource::getAudioFile)
                .toList();

        resourceRepository.deleteAllByIdIn(audioList);
        log.info("Deleted resource with id: {}",
                audioList.stream().map(String::valueOf)
                        .collect(Collectors.joining(",")));
        fileStorageClient.deleteResource(bucketsIds);

        return audioClient.deleteMetaData(audioList.stream().map(String::valueOf)
                .collect(Collectors.joining(",")));
    }

    @Override
    public List<byte[]> findAllResources() {
        //fileStorageClient.findAllResources();
        return null;
    }

    private String uploadToStorage(byte[] audioFile) {
        String randomKey = UUID.randomUUID().toString();
        fileStorageClient.uploadResource(randomKey, audioFile);
        return randomKey;
    }
}
