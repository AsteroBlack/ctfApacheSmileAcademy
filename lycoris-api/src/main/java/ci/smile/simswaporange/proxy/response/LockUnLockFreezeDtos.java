package ci.smile.simswaporange.proxy.response;

import ci.smile.simswaporange.utils.dto.DataLockUnlockFreeze;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LockUnLockFreezeDtos {
	@JsonProperty("status")
	private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("code")
    private String code;
    @JsonProperty("hasError")
    private Boolean hasError;
    @JsonProperty("data")
    private List<DataLockUnlockFreeze> data;

    private List<String> numbers;
    
}
