package by.tsarankou.serviceresource.service;

import by.tsarankou.serviceresource.dto.MetaDataDTO;
import com.google.common.io.Files;
import lombok.NoArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class MetaDataService {
    private final List<String> fileDescription = List.of("xmpDM:artist",
            "title",
            "xmpDM:duration",
            "xmpDM:releaseDate",
            "xmpDM:album");

    public MetaDataDTO getMetaDataFromFile(MultipartFile audioFile) throws IOException, TikaException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        //todo
        File tempDir = Files.createTempDir();
        File localFile = Paths.get(tempDir.getAbsolutePath(), "audio.mp3").toFile();

        try {
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            audioFile.transferTo(localFile);
        } catch (Exception e) {
            //todo
        }

        FileInputStream inputstream = new FileInputStream(localFile);
        ParseContext pcontext = new ParseContext();
        Mp3Parser Mp3Parser = new  Mp3Parser();
        Mp3Parser.parse(inputstream, handler, metadata, pcontext);
        String[] metadataNames = metadata.names();

        Map<String, String> audioDescription = Arrays.stream(metadataNames)
                .filter(fileDescription::contains)
                .collect(Collectors.toMap(c -> c, metadata::get));

        return  MetaDataDTO.builder()
                  .artist(audioDescription.getOrDefault(fileDescription.get(0), ""))
                .name(audioDescription.getOrDefault(fileDescription.get(1), ""))
                .length(audioDescription.getOrDefault(fileDescription.get(2), ""))
                //todo
               // .year(Integer.parseInt(audioDescription.getOrDefault(fileDescription.get(3),"0")))
                .album(audioDescription.getOrDefault(fileDescription.get(4), ""))
                .build();
    }
}
