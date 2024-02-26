package by.tsarankou.servicesong.service;

import by.tsarankou.servicesong.data.Audio;
import by.tsarankou.servicesong.dto.MetaDataDTO;

import java.util.List;

public interface AudioService {
    int uploadMetaData(MetaDataDTO metaDataDTO);
    Audio findAudioById(Integer id);
    Integer[] deleteAudioByIds(Integer[] ids);
}
