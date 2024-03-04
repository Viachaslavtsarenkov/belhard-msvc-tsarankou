package by.tsarankou.serviceresource.service;

import by.tsarankou.serviceresource.data.Resource;
import by.tsarankou.serviceresource.dto.IdDTO;
import by.tsarankou.serviceresource.dto.IdsDTO;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ResourceService {

    IdDTO uploadDataFile(File audioFile) throws IOException;
    Resource findResourceById(Integer id);
    IdsDTO deleteAllResourcesByIds(List<Integer> ids);
}
