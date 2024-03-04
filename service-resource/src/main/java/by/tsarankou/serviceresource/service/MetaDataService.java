package by.tsarankou.serviceresource.service;

import by.tsarankou.serviceresource.dto.MetaDataDTO;
import lombok.NoArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    public MetaDataDTO getMetaDataFromFile(File audioFile) throws IOException, TikaException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();

        FileInputStream inputstream = new FileInputStream(audioFile);
        ParseContext pcontext = new ParseContext();

        Mp3Parser Mp3Parser = new  Mp3Parser();
        Mp3Parser.parse(inputstream, handler, metadata, pcontext);
        String[] metadataNames = metadata.names();

        Map<String, String> audioDescription = Arrays.stream(metadataNames)
                .filter(fileDescription::contains)
                .collect(Collectors.toMap(c -> c, metadata::get));
        System.out.println(audioDescription.get(fileDescription.get(3)).isBlank() ? 0 : audioDescription.get(fileDescription.get(3)));

        return  MetaDataDTO.builder()
                  .artist(audioDescription.getOrDefault(fileDescription.get(0), ""))
                .name(audioDescription.getOrDefault(fileDescription.get(1), ""))
                .length(audioDescription.getOrDefault(fileDescription.get(2), ""))
                .year(Integer.parseInt(audioDescription.get(fileDescription.get(3)).isBlank()
                        ? "0" : audioDescription.get(fileDescription.get(3))))
                .album(audioDescription.getOrDefault(fileDescription.get(4), ""))
                .build();
    }
}
