package by.tsarankou.serviceresource.web;

import by.tsarankou.serviceresource.data.Resource;
import by.tsarankou.serviceresource.dto.IdDTO;
import by.tsarankou.serviceresource.dto.IdsDTO;
import by.tsarankou.serviceresource.service.ResourceService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping()
    public ResponseEntity<IdDTO> uploadNewResource(@RequestBody(required = true) byte[] audioFile) throws IOException {
        int id = resourceService.uploadDataFile(audioFile);
        IdDTO idDTO = new IdDTO();
        idDTO.setId(id);
        return ResponseEntity.ok(idDTO);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<byte[]> getResourceBinaryData(@PathVariable(name = "id") Integer id) {
        return ResponseEntity
                .ok(resourceService.findResourceById(id).getAudioFile());
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdsDTO> deleteMetaDataByIds(@PathParam(value = "ids") Integer[] ids) {
        IdsDTO idsDTO = new IdsDTO();
        Integer[] idsList = resourceService.deleteAllResourcesByIds(ids);
        idsDTO.setIds(idsList);
        return ResponseEntity.ok(idsDTO);
    }
}
