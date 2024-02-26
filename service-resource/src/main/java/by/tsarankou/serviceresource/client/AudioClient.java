package by.tsarankou.serviceresource.client;

import by.tsarankou.serviceresource.dto.IdDTO;
import by.tsarankou.serviceresource.dto.MetaDataDTO;

public interface AudioClient {
    IdDTO ping(MetaDataDTO metaDataDTO);
}
