package by.tsarankou.servicesong.web;

import by.tsarankou.servicesong.data.Audio;
import by.tsarankou.servicesong.dto.IdDTO;
import by.tsarankou.servicesong.dto.IdsDTO;
import by.tsarankou.servicesong.dto.MetaDataDTO;
import by.tsarankou.servicesong.service.AudioService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class AudioController {

    private final AudioService audioService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdDTO> uploadMetaDate(@RequestBody MetaDataDTO metaDataDTO) {
        IdDTO idDTO = new IdDTO();
        idDTO.setId(audioService
                .uploadMetaData(metaDataDTO));
        return ResponseEntity.ok(idDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Audio> findMetaDataById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity
                .ok(audioService.findAudioById(id));
    }
}
