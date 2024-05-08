package ci.smile.simswaporange.utils.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DataFromExelFileDto {
    private String numero;
    private String numeroMachine;
    private String statutBlocage;
    private String coId;
    private String portNum;
    private String smSerialNum;
    private String tmCode;
    private String offre;
    private Date dateActivation;
    private String statutTelco;

}
