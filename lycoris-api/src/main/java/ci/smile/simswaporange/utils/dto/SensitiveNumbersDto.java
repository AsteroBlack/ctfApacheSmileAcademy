package ci.smile.simswaporange.utils.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class SensitiveNumbersDto {
    private String msisdn ;
    private String lockStatus;
    private Boolean isFreezed;
    private Integer contractId;
    private Integer portNumber;
    private String serialNumber;
    private Integer tariffModelCode;
    private String offerName;
    private String activationDate;
    private String status;
}
