package by.tsarankou.serviceresource.service.impl;

import by.tsarankou.serviceresource.client.AudioClient;
import by.tsarankou.serviceresource.data.Resource;
import by.tsarankou.serviceresource.data.repository.ResourceRepository;
import by.tsarankou.serviceresource.dto.IdDTO;
import by.tsarankou.serviceresource.dto.IdsDTO;
import by.tsarankou.serviceresource.dto.MetaDataDTO;
import by.tsarankou.serviceresource.service.MetaDataService;
import by.tsarankou.serviceresource.service.ResourceService;
import by.tsarankou.serviceresource.service.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    @Transactional
    public IdDTO uploadDataFile(File audioFile) throws IOException {
        Resource audioResourceFile = new Resource();
        audioResourceFile.setAudioFile(Files
                        .readAllBytes(Path.of(audioFile.getPath())));
        resourceRepository.save(audioResourceFile);

        log.info("Created resource with id: {}", audioResourceFile.getId());
        try {
        MetaDataDTO audioMetaData = metaDataService
                   .getMetaDataFromFile(audioFile);
        audioMetaData.setResourceId(audioResourceFile.getId());
        audioClient.ping(audioMetaData);
        } catch (Exception e) {
            System.out.println(e);
            //todo
        }
        return IdDTO.builder()
                .id(audioResourceFile.getId())
                .build();
    }

    @Override
    @Transactional
    public Resource findResourceById(Integer id) {
        Resource resource = resourceRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
        log.info("Found resource with id: {}", resource.getId());
        return resource;
    }

    @Override
    @Transactional
    public IdsDTO deleteAllResourcesByIds(List<Integer> ids) {
        List<Integer> audioList = resourceRepository
                .findExistingIds(ids);
        resourceRepository.deleteAllByIdIn(audioList);

        log.info("Deleted resource with id: {}",
                audioList.stream().map(String::valueOf)
                        .collect(Collectors.joining(",")));
        //IdsDTO idsResources = audioClient.deleteMetaData(ids.stream().map(String::valueOf)
//                .collect(Collectors.joining(",")));
        return IdsDTO.builder().ids(audioList.toArray(Integer[]::new)).build();
    }
}
