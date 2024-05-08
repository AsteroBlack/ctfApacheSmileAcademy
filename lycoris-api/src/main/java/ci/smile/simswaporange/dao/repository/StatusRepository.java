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

import ci.smile.simswaporange.dao.entity.Status;
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
import ci.smile.simswaporange.dao.repository.customize._StatusRepository;

/**
 * Repository : Status.
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Integer>, _StatusRepository {
	/**
	 * Finds Status by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Status whose id is equals to the given id. If
	 *         no Status is found, this method returns null.
	 */
	@Query("select e from Status e where e.id = :id and e.isDeleted = :isDeleted")
	Status findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Status by using code as a search criteria.
	 * 
	 * @param code
	 * @return An Object Status whose code is equals to the given code. If
	 *         no Status is found, this method returns null.
	 */
	@Query("select e from Status e where e.code = :code and e.isDeleted = :isDeleted")
	Status findByCode(@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Status by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Status whose createdAt is equals to the given createdAt. If
	 *         no Status is found, this method returns null.
	 */
	@Query("select e from Status e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Status> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Status by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Status whose createdBy is equals to the given createdBy. If
	 *         no Status is found, this method returns null.
	 */
	@Query("select e from Status e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Status> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Status by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Status whose updatedAt is equals to the given updatedAt. If
	 *         no Status is found, this method returns null.
	 */
	@Query("select e from Status e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Status> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Status by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Status whose updatedBy is equals to the given updatedBy. If
	 *         no Status is found, this method returns null.
	 */
	@Query("select e from Status e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Status> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Status by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Status whose isDeleted is equals to the given isDeleted. If
	 *         no Status is found, this method returns null.
	 */
	@Query("select e from Status e where e.isDeleted = :isDeleted")
	List<Status> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Status by using statusDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Status
	 * @throws DataAccessException,ParseException
	 */
	default List<Status> getByCriteria(Request<StatusDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Status e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Status> query = em.createQuery(req, Status.class);
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
	 * Finds count of Status by using statusDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Status
	 * 
	 */
	default Long count(Request<StatusDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Status e where e IS NOT NULL";
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
	default String getWhereExpression(Request<StatusDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		StatusDto dto = request.getData() != null ? request.getData() : new StatusDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (StatusDto elt : request.getDatas()) {
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
	default String generateCriteria(StatusDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("code", dto.getCode(), "e.code", "String", dto.getCodeParam(), param, index, locale));
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
