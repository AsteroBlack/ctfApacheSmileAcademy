package ci.smile.simswaporange.proxy.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class SensitiveNumberDto {
	@JsonProperty("msisdn")
	private String msisdn;

	@JsonProperty("message")
	private String message;

	@JsonProperty("lockStatus")
	private String lockStatus;

	@JsonProperty("contractId")
	private int contractId;

	@JsonProperty("portNumber")
	private long portNumber;

	@JsonProperty("serialNumber")
	private String serialNumber;

	@JsonProperty("tariffModelCode")
	private int tariffModelCode;

	@JsonProperty("offerName")
	private String offerName;
	
	@JsonProperty("status")
	private String status;

	@JsonProperty("activationDate")
	private String activationDate;

	@JsonProperty("isFrozen")
	private boolean isFrozen;
	
	
}
