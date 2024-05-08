package ci.smile.simswaporange.utils.dto.customize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class _AppRequestDto {
	private String phoneNumber;
	private String msisdn;
	@Builder.Default
	private Integer pageNumber = 0;
	@Builder.Default
	private Integer pageSize = 25;
	@Builder.Default
	private String sensitiveNumberPack = "LockedNumbers";
	private String message;
	
	
	private Integer user;

	private List<String> numbers;

}
