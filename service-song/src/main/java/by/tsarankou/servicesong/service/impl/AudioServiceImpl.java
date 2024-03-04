package by.tsarankou.servicesong.service.impl;

import by.tsarankou.servicesong.data.Audio;
import by.tsarankou.servicesong.data.repository.AudioRepository;
import by.tsarankou.servicesong.dto.IdsDTO;
import by.tsarankou.servicesong.dto.MetaDataDTO;
import by.tsarankou.servicesong.service.AudioService;
import by.tsarankou.servicesong.service.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AudioServiceImpl implements AudioService {

    private final AudioRepository audioRepository;

    @Override
    @Transactional
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
        log.info("Created audio metadata with id: {}",audio.getId());
        return audio.getId();
    }

    @Override
    @Transactional
    public Audio findAudioById(Integer id) {
        Audio audio =  audioRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("Found audio metadata with id: {}",audio.getId());
        return audio;
    }

    @Override
    @Transactional
    public IdsDTO deleteAudioByIds(List<Integer>ids) {
        List<Audio> audioList = audioRepository.findAllByIdIn(ids);

        Integer[] deletedMetaDataIds = audioList
                .stream()
                .map(Audio::getId).toArray(Integer[]::new);
        Integer[] idsResources = audioList.stream()
                .map(Audio::getResourceId)
                .toArray(Integer[]::new);

        audioRepository.deleteAllByIdIn(List.of(deletedMetaDataIds));
        log.info("Deleted audio metadata with id: {}", Arrays.stream(deletedMetaDataIds)
                .map(String::valueOf).collect(Collectors.joining(",")));

        return IdsDTO.builder()
                .ids(idsResources)
                .build();
    }
}
