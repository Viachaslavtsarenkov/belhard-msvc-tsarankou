package by.tsarankou.resourceprocessor.client;

import by.tsarankou.resourceprocessor.dto.MetaDataDTO;


public interface ResourceClient {
    byte[] getResource(Integer id);
    void sentMetadataToSongsService(MetaDataDTO metaDataDTO);
}
