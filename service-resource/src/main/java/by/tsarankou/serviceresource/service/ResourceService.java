package by.tsarankou.serviceresource.service;

import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {
    int uploadDataFile(MultipartFile audioFile);
}
