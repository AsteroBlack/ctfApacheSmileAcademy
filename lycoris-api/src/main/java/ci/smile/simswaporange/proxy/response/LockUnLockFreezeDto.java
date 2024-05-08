package ci.smile.simswaporange.proxy.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ci.smile.simswaporange.utils.dto.DataLockUnlockFreeze;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LockUnLockFreezeDto {
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("code")
    private String code;
    @JsonProperty("hasError")
    private Boolean hasError;
    @JsonProperty("data")
    private List<SensitiveNumberDto> data;

    private List<String> numbers;
    
}
