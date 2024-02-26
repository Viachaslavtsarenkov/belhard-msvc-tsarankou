package by.tsarankou.servicesong.web;

import by.tsarankou.servicesong.dto.MetaDataDTO;
import by.tsarankou.servicesong.service.AudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class AudioController {

    private final AudioService audioService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public int uploadMetaDate(@RequestBody MetaDataDTO metaDataDTO) {
        return audioService.uploadMetaData(metaDataDTO);
    }

    @GetMapping("/{id}")
    public String getMetaData(@PathVariable(value = "id") int id) {
        System.out.println(id);
        System.out.println("\n from another server");
        return "data";
    }
}
