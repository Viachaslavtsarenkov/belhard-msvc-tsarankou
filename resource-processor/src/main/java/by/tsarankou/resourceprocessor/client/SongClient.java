package by.tsarankou.resourceprocessor.client;

import by.tsarankou.resourceprocessor.dto.MetaDataDTO;

public interface SongClient {
    void sentMetadataToSongsService(MetaDataDTO metaDataDTO);
}
