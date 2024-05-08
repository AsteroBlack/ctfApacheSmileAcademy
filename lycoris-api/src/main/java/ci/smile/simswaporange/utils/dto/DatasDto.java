package ci.smile.simswaporange.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatasDto {
	@JsonProperty("msisdn")
	private String msisdn;

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

	@JsonProperty("message")
	private String message;
}
