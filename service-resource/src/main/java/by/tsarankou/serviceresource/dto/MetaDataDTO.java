package by.tsarankou.serviceresource.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetaDataDTO {
    private String name;
    private String artist;
    private String album;
    private String length;
    private int resourceId;
    private int year;
}
