package by.tsarankou.serviceresource.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @PostMapping()
    public ResponseEntity<Integer> uploadNewResource() {
        return ResponseEntity.ok(0);
    }

    @GetMapping("/{id}")
    public String getResourceBinaryData(@PathVariable(name = "id") Integer id) {
        return "data";
    }


}
