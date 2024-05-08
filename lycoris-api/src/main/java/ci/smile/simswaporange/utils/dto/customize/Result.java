package ci.smile.simswaporange.utils.dto.customize;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Result {
    private String numero;
    private String reason;
    private String lockStatus;
    private String contractId;
    private String portNumber;
    private String serialNumber;
    private String tariffModelCode;
    private String offerName;
    private String status;
    private String activationDate;
    private boolean isFrozen;
    private String login;

}
