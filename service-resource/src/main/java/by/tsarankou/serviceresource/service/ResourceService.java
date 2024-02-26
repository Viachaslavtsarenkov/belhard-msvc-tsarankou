package by.tsarankou.serviceresource.service;

import by.tsarankou.serviceresource.data.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ResourceService {
    int uploadDataFile(byte[] audioFile);
    Resource findResourceById(Integer id);
    Integer[] deleteAllResourcesByIds(Integer[] ids);
}
