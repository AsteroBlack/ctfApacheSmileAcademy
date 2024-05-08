package ci.smile.simswaporange.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataLockUnlockFreeze {
	@JsonProperty("state")
    private String state;

    @JsonProperty("msisdn")
    private String msisdn;

}
