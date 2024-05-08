package ci.smile.simswaporange.proxy.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import ci.smile.simswaporange.utils.dto.DatasDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensitiveNumberPackDto {

    @JsonProperty("status")
    private int status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private DatasDto data;
}
