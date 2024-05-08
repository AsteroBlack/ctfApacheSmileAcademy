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
import ci.smile.simswaporange.dao.repository.customize._ParametrageRepository;

/**
 * Repository : Parametrage.
 */
@Repository
public interface ParametrageRepository extends JpaRepository<Parametrage, Integer>, _ParametrageRepository {
	/**
	 * Finds Parametrage by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Parametrage whose id is equals to the given id. If
	 *         no Parametrage is found, this method returns null.
	 */
	@Query("select e from Parametrage e where e.id = :id and e.isDeleted = :isDeleted")
	Parametrage findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Parametrage by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Parametrage whose createdAt is equals to the given createdAt. If
	 *         no Parametrage is found, this method returns null.
	 */
	@Query("select e from Parametrage e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Parametrage> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Parametrage by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Parametrage whose createdBy is equals to the given createdBy. If
	 *         no Parametrage is found, this method returns null.
	 */
	@Query("select e from Parametrage e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Parametrage> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Parametrage by using delaiAttente as a search criteria.
	 * 
	 * @param delaiAttente
	 * @return An Object Parametrage whose delaiAttente is equals to the given delaiAttente. If
	 *         no Parametrage is found, this method returns null.
	 */
	@Query("select e from Parametrage e where e.delaiAttente = :delaiAttente and e.isDeleted = :isDeleted")
	List<Parametrage> findByDelaiAttente(@Param("delaiAttente")String delaiAttente, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Parametrage by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Parametrage whose isDeleted is equals to the given isDeleted. If
	 *         no Parametrage is found, this method returns null.
	 */
	@Query("select e from Parametrage e where e.isDeleted = :isDeleted")
	List<Parametrage> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Parametrage by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Parametrage whose updatedAt is equals to the given updatedAt. If
	 *         no Parametrage is found, this method returns null.
	 */
	@Query("select e from Parametrage e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Parametrage> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Parametrage by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Parametrage whose updatedBy is equals to the given updatedBy. If
	 *         no Parametrage is found, this method returns null.
	 */
	@Query("select e from Parametrage e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Parametrage> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Parametrage by using idTypeParametrage as a search criteria.
	 * 
	 * @param idTypeParametrage
	 * @return An Object Parametrage whose idTypeParametrage is equals to the given idTypeParametrage. If
	 *         no Parametrage is found, this method returns null.
	 */
	@Query("select e from Parametrage e where e.typeParametrage.id = :idTypeParametrage and e.isDeleted = :isDeleted")
	List<Parametrage> findByIdTypeParametrage(@Param("idTypeParametrage")Integer idTypeParametrage, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Parametrage by using parametrageDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Parametrage
	 * @throws DataAccessException,ParseException
	 */
	default List<Parametrage> getByCriteria(Request<ParametrageDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Parametrage e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Parametrage> query = em.createQuery(req, Parametrage.class);
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
	 * Finds count of Parametrage by using parametrageDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Parametrage
	 * 
	 */
	default Long count(Request<ParametrageDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Parametrage e where e IS NOT NULL";
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
	default String getWhereExpression(Request<ParametrageDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		ParametrageDto dto = request.getData() != null ? request.getData() : new ParametrageDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (ParametrageDto elt : request.getDatas()) {
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
	default String generateCriteria(ParametrageDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCreatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
			}
			if (dto.getCreatedBy()!= null && dto.getCreatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
			}
//			if (Utilities.notBlank(dto.getDelaiAttente())) {
//				listOfQuery.add(CriteriaUtils.generateCriteria("delaiAttente", dto.getDelaiAttente(), "e.delaiAttente", "Integer", dto.getDelaiAttenteParam(), param, index, locale));
//			}
			if (dto.getIsDeleted()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUpdatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
			}
			if (dto.getUpdatedBy()!= null && dto.getUpdatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedBy", dto.getUpdatedBy(), "e.updatedBy", "Integer", dto.getUpdatedByParam(), param, index, locale));
			}
			if (dto.getIdTypeParametrage()!= null && dto.getIdTypeParametrage() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idTypeParametrage", dto.getIdTypeParametrage(), "e.typeParametrage.id", "Integer", dto.getIdTypeParametrageParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getTypeParametrageCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("typeParametrageCode", dto.getTypeParametrageCode(), "e.typeParametrage.code", "String", dto.getTypeParametrageCodeParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
