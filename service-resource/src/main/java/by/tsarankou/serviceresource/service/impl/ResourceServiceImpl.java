package by.tsarankou.serviceresource.service.impl;

import by.tsarankou.serviceresource.client.AudioClient;
import by.tsarankou.serviceresource.data.Resource;
import by.tsarankou.serviceresource.data.repository.ResourceRepository;
import by.tsarankou.serviceresource.dto.MetaDataDTO;
import by.tsarankou.serviceresource.service.MetaDataService;
import by.tsarankou.serviceresource.service.ResourceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;

@Service
@AllArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    final ResourceRepository resourceRepository;
    final MetaDataService metaDataService;
    final AudioClient audioClient;
    @Override
    public int uploadDataFile(MultipartFile audioFile) {
        try {
            Blob blob = new SerialBlob(audioFile.getResource().getContentAsByteArray());
            Resource audioResourceFile = Resource
                    .builder()
                    .audioFile(blob)
                    .build();

            resourceRepository.save(audioResourceFile);
            MetaDataDTO audioMetaData = metaDataService.getMetaDataFromFile(audioFile);
            audioMetaData.setResourceId(audioResourceFile.getId());
            return audioResourceFile.getId();
        } catch (Exception e) {
            //todo
        }
        return 0;
    }

    @Override
    public Resource findResourceById(Integer id) {
        return resourceRepository.findById(id).get();
    }
}
