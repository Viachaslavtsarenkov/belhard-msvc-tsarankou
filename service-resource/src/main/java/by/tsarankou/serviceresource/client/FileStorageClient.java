package by.tsarankou.serviceresource.client;

import java.io.IOException;
import java.util.List;

public interface FileStorageClient {
    void uploadResource(String key, byte[] content);

    byte[] downloadResource(String key) throws IOException;

    void deleteResource(List<String> keys);
    List<byte[]> findAllResources() throws IOException;
}

