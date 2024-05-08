package ci.smile.simswaporange.utils.dto.customize;

import ci.smile.simswaporange.dao.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class _NumerosBscs {
	private String numero;
	private String numeroMachine;
	private String status;
	private String coId;
	private String portNum;
	private String smSerialNum;
	private String offre;
	private String dateActivation;
	private String statusTelco;
}
