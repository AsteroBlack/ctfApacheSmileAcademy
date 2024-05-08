package ci.smile.simswaporange.proxy.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import ci.smile.simswaporange.utils.dto.DataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)

public class SensitiveNumberData {

	@JsonProperty("status")
	private String status;
	@JsonProperty("message")
	private String message;
	@JsonProperty("data")
	private DataDto data;

}
