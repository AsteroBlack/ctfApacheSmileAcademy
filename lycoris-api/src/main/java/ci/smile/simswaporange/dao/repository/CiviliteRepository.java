package ci.smile.simswaporange.dao.repository;

import java.util.Date;
import java.util.List;
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
import ci.smile.simswaporange.dao.repository.customize._CiviliteRepository;

/**
 * Repository : Civilite.
 */
@Repository
public interface CiviliteRepository extends JpaRepository<Civilite, Integer>, _CiviliteRepository {
	/**
	 * Finds Civilite by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Civilite whose id is equals to the given id. If
	 *         no Civilite is found, this method returns null.
	 */
	@Query("select e from Civilite e where e.id = :id and e.isDeleted = :isDeleted")
	Civilite findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Civilite by using libelle as a search criteria.
	 * 
	 * @param libelle
	 * @return An Object Civilite whose libelle is equals to the given libelle. If
	 *         no Civilite is found, this method returns null.
	 */
	@Query("select e from Civilite e where e.libelle = :libelle and e.isDeleted = :isDeleted")
	Civilite findByLibelle(@Param("libelle")String libelle, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Civilite by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Civilite whose createdAt is equals to the given createdAt. If
	 *         no Civilite is found, this method returns null.
	 */
	@Query("select e from Civilite e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Civilite> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Civilite by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Civilite whose createdBy is equals to the given createdBy. If
	 *         no Civilite is found, this method returns null.
	 */
	@Query("select e from Civilite e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Civilite> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Civilite by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Civilite whose updatedAt is equals to the given updatedAt. If
	 *         no Civilite is found, this method returns null.
	 */
	@Query("select e from Civilite e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Civilite> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Civilite by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Civilite whose updatedBy is equals to the given updatedBy. If
	 *         no Civilite is found, this method returns null.
	 */
	@Query("select e from Civilite e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Civilite> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Civilite by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Civilite whose isDeleted is equals to the given isDeleted. If
	 *         no Civilite is found, this method returns null.
	 */
	@Query("select e from Civilite e where e.isDeleted = :isDeleted")
	List<Civilite> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Civilite by using civiliteDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Civilite
	 * @throws DataAccessException,ParseException
	 */
	default List<Civilite> getByCriteria(Request<CiviliteDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Civilite e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Civilite> query = em.createQuery(req, Civilite.class);
		for (Map.Entry<String, java.lang.Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		if (request.getIndex() != null && request.getSize() != null) {
			query.setFirstResult(request.getIndex() * request.getSize());
			query.setMaxResults(request.getSize());
		}
		return query.getResultList();
	}

	/**
	 * Finds count of Civilite by using civiliteDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Civilite
	 * 
	 */
	default Long count(Request<CiviliteDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Civilite e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by  e.id desc";
		javax.persistence.Query query = em.createQuery(req);
		for (Map.Entry<String, java.lang.Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		Long count = (Long) query.getResultList().get(0);
		return count;
	}

	/**
	 * get where expression
	 * @param request
	 * @param param
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	default String getWhereExpression(Request<CiviliteDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		CiviliteDto dto = request.getData() != null ? request.getData() : new CiviliteDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (CiviliteDto elt : request.getDatas()) {
				elt.setIsDeleted(false);
				String eltReq = generateCriteria(elt, param, index, locale);
				if (request.getIsAnd() != null && request.getIsAnd()) {
					othersReq += "and (" + eltReq + ") ";
				} else {
					othersReq += "or (" + eltReq + ") ";
				}
				index++;
			}
		}
		String req = "";
		if (!mainReq.isEmpty()) {
			req += " and (" + mainReq + ") ";
		}
		req += othersReq;
		return req;
	}

	/**
	 * generate sql query for dto
	 * @param dto
	 * @param param
	 * @param index
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	default String generateCriteria(CiviliteDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("libelle", dto.getLibelle(), "e.libelle", "String", dto.getLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCreatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
			}
			if (dto.getCreatedBy()!= null && dto.getCreatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUpdatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
			}
			if (dto.getUpdatedBy()!= null && dto.getUpdatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedBy", dto.getUpdatedBy(), "e.updatedBy", "Integer", dto.getUpdatedByParam(), param, index, locale));
			}
			if (dto.getIsDeleted()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
