package by.tsarankou.serviceresource.service.impl;

import by.tsarankou.serviceresource.data.Resource;
import by.tsarankou.serviceresource.data.repository.ResourceRepository;
import by.tsarankou.serviceresource.service.MediaDataService;
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
    final MediaDataService mediaDataService;
    @Override
    public int uploadDataFile(MultipartFile audioFile) {
        try {
            Blob blob= new SerialBlob(audioFile.getResource().getContentAsByteArray());
            Resource audioResourceFile = Resource
                    .builder()
                    .audioFile(blob)
                    .build();

            resourceRepository.save(audioResourceFile);
            mediaDataService.getMetaDataFromFile(audioFile);

            System.out.println(mediaDataService.getMetaDataFromFile(audioFile));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //todo
        }

        return 0;
    }
}
