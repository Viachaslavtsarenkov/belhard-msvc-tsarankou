package by.tsarankou.servicesong.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdsDTO {
    private Integer[] ids;
}
