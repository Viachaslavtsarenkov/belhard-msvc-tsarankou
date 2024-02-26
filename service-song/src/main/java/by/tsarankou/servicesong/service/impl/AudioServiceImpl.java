package by.tsarankou.servicesong.service.impl;

import by.tsarankou.servicesong.data.Audio;
import by.tsarankou.servicesong.data.repository.AudioRepository;
import by.tsarankou.servicesong.dto.MetaDataDTO;
import by.tsarankou.servicesong.service.AudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AudioServiceImpl implements AudioService {

    private final AudioRepository audioRepository;

    @Override
    public int uploadMetaData(MetaDataDTO metaDataDTO) {

        Audio audio = Audio.builder()
                .name(metaDataDTO.getName())
                .artist(metaDataDTO.getArtist())
                .album(metaDataDTO.getAlbum())
                .length(metaDataDTO.getLength())
                .resourceId(metaDataDTO.getResourceId())
                .year(metaDataDTO.getYear())
                .build();

        audioRepository.save(audio);

        return audio.getId();
    }
}
