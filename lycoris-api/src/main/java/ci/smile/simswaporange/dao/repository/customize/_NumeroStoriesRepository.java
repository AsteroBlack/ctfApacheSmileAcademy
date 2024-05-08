package ci.smile.simswaporange.dao.repository.customize;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.dao.entity.*;

/**
 * Repository customize : NumeroStories.
 */
@Repository
public interface _NumeroStoriesRepository {
	default List<String> _generateCriteria(NumeroStoriesDto dto, HashMap<String, java.lang.Object> param, Integer index, Locale locale) throws Exception {
		List<String> listOfQuery = new ArrayList<String>();

		// PUT YOUR RIGHT CUSTOM CRITERIA HERE

		return listOfQuery;
	}
	@Query("select e from NumeroStories e where e.numero = :numero and e.isDeleted = :isDeleted")
	List<NumeroStories>  findByNumeros(@Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);
	
	@Query("select e from NumeroStories e where e.createdAt = :date and e.status.id = :idStatus and e.isDeleted = :isDeleted")
	List<NumeroStories> findByNumberByStatusAndDate(@Param("date")Date date, @Param("isDeleted")Boolean isDeleted, @Param("idStatus") int id);
	
}
