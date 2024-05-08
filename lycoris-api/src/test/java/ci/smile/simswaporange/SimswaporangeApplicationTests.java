/*
package ci.smile.simswaporange;

import ci.smile.simswaporange.business.CiviliteBusiness;
import ci.smile.simswaporange.business.StatusBusiness;
import ci.smile.simswaporange.dao.repository.ActionUtilisateurRepository;
import ci.smile.simswaporange.dao.repository.StatusRepository;
import ci.smile.simswaporange.utils.ExceptionUtils;
import ci.smile.simswaporange.utils.FunctionalError;
import ci.smile.simswaporange.utils.TechnicalError;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.CiviliteDto;
import ci.smile.simswaporange.utils.dto.StatusDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;

@SpringBootTest
class SimswaporangeApplicationTests {
	@InjectMocks
	private StatusBusiness statusBusiness;
	@Mock
	private  StatusRepository statusRepository;
	@Mock
	private  ActionUtilisateurRepository actionUtilisateurRepository;
	@Mock
	private  FunctionalError functionalError;
	@Mock
	private  TechnicalError technicalError;
	@Mock
	private  ExceptionUtils exceptionUtils;
	@Test
	@Order(0)
	void contextLoads(){
		Request <StatusDto> request = new Request <>();
		StatusDto dto = new StatusDto();
		dto.setCode("FR-STAT");
		request.setDatas(Arrays.asList(dto));
		Locale locale = new Locale("fr", "");
		try{
			Response<StatusDto> response =  statusBusiness.create(request, locale);
		}catch(ParseException e){
			e.printStackTrace();
		}
	}

}
*/
