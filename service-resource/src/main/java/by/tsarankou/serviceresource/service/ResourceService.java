package by.tsarankou.serviceresource.service;

import by.tsarankou.serviceresource.data.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {
    int uploadDataFile(MultipartFile audioFile);
    Resource findResourceById(Integer id);
}
