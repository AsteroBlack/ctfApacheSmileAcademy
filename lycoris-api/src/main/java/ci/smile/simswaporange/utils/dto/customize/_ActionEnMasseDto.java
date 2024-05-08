package ci.smile.simswaporange.utils.dto.customize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ci.smile.simswaporange.utils.contract.SearchParam;
import ci.smile.simswaporange.utils.dto.ActionEnMasseDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class _ActionEnMasseDto {
	
	private String inputFilePath;
    private String outputDirectory;
    private Integer user;
}
