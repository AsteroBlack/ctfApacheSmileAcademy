package ci.smile.simswaporange.dao.repository.customize;

import ci.smile.simswaporange.utils.dto.HistoriqueDto;
import ci.smile.simswaporange.utils.dto.TypeDemandeDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public interface _TypeDemandeRepository {
    default List<String> _generateCriteria(TypeDemandeDto dto, HashMap<String, Object> param, Integer index, Locale locale) throws Exception {
        List<String> listOfQuery = new ArrayList<String>();

        // PUT YOUR RIGHT CUSTOM CRITERIA HERE

        return listOfQuery;
    }
}
