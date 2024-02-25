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

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
@NoArgsConstructor
public class MetaDataService {
    private List<String> fileDescription = List.of("xmpDM:artist", "title", "xmpDM:duration", "xmpDM:releaseDate", "xmpDM:album");

    public MetaDataDTO getMetaDataFromFile(MultipartFile audioFile) throws IOException, TikaException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();

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

        for(String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));
        }

        fileDescription  = Arrays.stream(metadataNames).filter(fileDescription::contains).toList();

        return null;
//                MetaDataDTO.builder()
//                .album(metadata.get(fileDescription"xmpDM:album"))
//                .year(Integer.parseInt(metadata.get("xmpDM:releaseDate")))
//                .artist(metadata.get("xmpDM:artist"))
//                .name("title")
//                .length(metadata.get("xmpDM:duration"))
//                .build();
    }
}
