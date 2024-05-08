package ci.smile.simswaporange.utils.dto.customize;

import ci.smile.simswaporange.utils.dto.ActionEnMasseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class _DemandeDto {
    private Boolean isTransfert;
    private Boolean isMasse;
    private ActionEnMasseDto actionEnMasseDto;
}
