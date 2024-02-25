package by.tsarankou.serviceresource.web;

import by.tsarankou.serviceresource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{id}")
    public String getResourceBinaryData(@PathVariable(name = "id") Integer id) {
        return "data";
    }


}
