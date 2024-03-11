package by.tsarankou.serviceresource.service;

import by.tsarankou.serviceresource.dto.IdDTO;
import by.tsarankou.serviceresource.dto.IdsDTO;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ResourceService {

    IdDTO uploadDataFile(File audioFile) throws IOException, TikaException, SAXException;
    byte[] findResourceById(Integer id) throws IOException;
    IdsDTO deleteAllResourcesByIds(List<Integer> ids);
    List<byte[]> findAllResources();
}
