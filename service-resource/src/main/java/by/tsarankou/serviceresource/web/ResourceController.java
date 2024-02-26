package by.tsarankou.serviceresource.web;

import by.tsarankou.serviceresource.data.Resource;
import by.tsarankou.serviceresource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping()
    public ResponseEntity<Integer> uploadNewResource(@RequestParam("audioFile") MultipartFile audioFile) {
        int id = resourceService.uploadDataFile(audioFile);
        return ResponseEntity.ok(id);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Resource> getResourceBinaryData(@PathVariable(name = "id") Integer id) {
        return ResponseEntity
                .ok(resourceService.findResourceById(id));
    }


}
