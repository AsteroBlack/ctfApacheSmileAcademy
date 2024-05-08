package ci.smile.simswaporange.dao.repository.customize;

import ci.smile.simswaporange.dao.entity.ActionEnMasse;
import ci.smile.simswaporange.utils.dto.ActionEnMasseDto;
import ci.smile.simswaporange.utils.dto.ActionUtilisateurDto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Repository
public interface _ActionEnMasseRepository {
	
	
	@Query("select e from ActionEnMasse e where e.identifiant = :identifiant and e.isDeleted = :isDeleted")
	ActionEnMasse findByOneIdentifiant(@Param("identifiant")String identifiant, @Param("isDeleted")Boolean isDeleted);
    default List<String> _generateCriteria(ActionEnMasseDto dto, HashMap<String, Object> param, Integer index, Locale locale) throws Exception {
        List<String> listOfQuery = new ArrayList<String>();

        // PUT YOUR RIGHT CUSTOM CRITERIA HERE

        return listOfQuery;
    }
}
