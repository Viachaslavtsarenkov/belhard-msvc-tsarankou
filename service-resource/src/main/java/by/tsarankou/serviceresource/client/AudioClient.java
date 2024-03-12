package by.tsarankou.serviceresource.client;

import by.tsarankou.serviceresource.dto.IdDTO;
import by.tsarankou.serviceresource.dto.IdsDTO;

public interface AudioClient {
    IdsDTO deleteMetaData(String ids);
}
