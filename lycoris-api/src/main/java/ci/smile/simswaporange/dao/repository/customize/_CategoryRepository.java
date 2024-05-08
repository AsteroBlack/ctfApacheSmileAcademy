package ci.smile.simswaporange.dao.repository.customize;

import ci.smile.simswaporange.utils.dto.ActionUtilisateurDto;
import ci.smile.simswaporange.utils.dto.CategoryDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
@Repository
public interface _CategoryRepository {
    default List<String> _generateCriteria(CategoryDto dto, HashMap<String, java.lang.Object> param, Integer index, Locale locale) throws Exception {
        List<String> listOfQuery = new ArrayList<String>();

        // PUT YOUR RIGHT CUSTOM CRITERIA HERE

        return listOfQuery;
    }
}
