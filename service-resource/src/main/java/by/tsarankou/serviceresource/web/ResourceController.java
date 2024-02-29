package by.tsarankou.serviceresource.web;

import by.tsarankou.serviceresource.dto.IdDTO;
import by.tsarankou.serviceresource.dto.IdsDTO;
import by.tsarankou.serviceresource.service.ResourceService;
import com.uwyn.jhighlight.tools.FileUtils;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping()
    public ResponseEntity<IdDTO> uploadNewResource(@RequestParam(name = "audioFile")MultipartFile audioFile) throws IOException {

        File convFile = new File( audioFile.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write(audioFile.getBytes());
        fos.close();

        if (!FileUtils.getExtension(convFile.getPath()).equals("mp3") ||
                !(audioFile.getContentType().equals("audio/mpeg"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        };
        return ResponseEntity.ok()
                .body(resourceService.uploadDataFile(convFile));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<byte[]> getResourceBinaryData(@PathVariable(name = "id") Integer id) {
        return ResponseEntity
                .ok(resourceService.findResourceById(id).getAudioFile());
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdsDTO> deleteMetaDataByIds(@PathParam(value = "ids") String ids) {
        List<Integer> idsList = Arrays.stream(ids.split(","))
                .map(Integer::valueOf).toList();
        return ResponseEntity.ok(resourceService
                .deleteAllResourcesByIds(idsList));
    }

}
