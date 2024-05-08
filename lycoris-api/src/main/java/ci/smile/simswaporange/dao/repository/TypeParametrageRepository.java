package ci.smile.simswaporange.dao.repository;

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
import ci.smile.simswaporange.dao.repository.customize._TypeParametrageRepository;

/**
 * Repository : TypeParametrage.
 */
@Repository
public interface TypeParametrageRepository extends JpaRepository<TypeParametrage, Integer>, _TypeParametrageRepository {
	/**
	 * Finds TypeParametrage by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object TypeParametrage whose id is equals to the given id. If
	 *         no TypeParametrage is found, this method returns null.
	 */
	@Query("select e from TypeParametrage e where e.id = :id and e.isDeleted = :isDeleted")
	TypeParametrage findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds TypeParametrage by using code as a search criteria.
	 * 
	 * @param code
	 * @return An Object TypeParametrage whose code is equals to the given code. If
	 *         no TypeParametrage is found, this method returns null.
	 */
	@Query("select e from TypeParametrage e where e.code = :code and e.isDeleted = :isDeleted")
	TypeParametrage findByCode(@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds TypeParametrage by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object TypeParametrage whose createdAt is equals to the given createdAt. If
	 *         no TypeParametrage is found, this method returns null.
	 */
	@Query("select e from TypeParametrage e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<TypeParametrage> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds TypeParametrage by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object TypeParametrage whose updatedAt is equals to the given updatedAt. If
	 *         no TypeParametrage is found, this method returns null.
	 */
	@Query("select e from TypeParametrage e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<TypeParametrage> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds TypeParametrage by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object TypeParametrage whose isDeleted is equals to the given isDeleted. If
	 *         no TypeParametrage is found, this method returns null.
	 */
	@Query("select e from TypeParametrage e where e.isDeleted = :isDeleted")
	List<TypeParametrage> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds TypeParametrage by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object TypeParametrage whose createdBy is equals to the given createdBy. If
	 *         no TypeParametrage is found, this method returns null.
	 */
	@Query("select e from TypeParametrage e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<TypeParametrage> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of TypeParametrage by using typeParametrageDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of TypeParametrage
	 * @throws DataAccessException,ParseException
	 */
	default List<TypeParametrage> getByCriteria(Request<TypeParametrageDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from TypeParametrage e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<TypeParametrage> query = em.createQuery(req, TypeParametrage.class);
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
	 * Finds count of TypeParametrage by using typeParametrageDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of TypeParametrage
	 * 
	 */
	default Long count(Request<TypeParametrageDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from TypeParametrage e where e IS NOT NULL";
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
	default String getWhereExpression(Request<TypeParametrageDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		TypeParametrageDto dto = request.getData() != null ? request.getData() : new TypeParametrageDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (TypeParametrageDto elt : request.getDatas()) {
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
	default String generateCriteria(TypeParametrageDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
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
			if (Utilities.notBlank(dto.getUpdatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
			}
			if (dto.getIsDeleted()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
			}
			if (dto.getCreatedBy()!= null && dto.getCreatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
