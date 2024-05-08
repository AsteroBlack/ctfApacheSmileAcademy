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
import ci.smile.simswaporange.dao.repository.customize._ParametrageProfilRepository;

/**
 * Repository : ParametrageProfil.
 */
@Repository
public interface ParametrageProfilRepository extends JpaRepository<ParametrageProfil, Integer>, _ParametrageProfilRepository {
	/**
	 * Finds ParametrageProfil by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object ParametrageProfil whose id is equals to the given id. If
	 *         no ParametrageProfil is found, this method returns null.
	 */
	@Query("select e from ParametrageProfil e where e.id = :id and e.isDeleted = :isDeleted")
	ParametrageProfil findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ParametrageProfil by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object ParametrageProfil whose createdAt is equals to the given createdAt. If
	 *         no ParametrageProfil is found, this method returns null.
	 */
	@Query("select e from ParametrageProfil e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<ParametrageProfil> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ParametrageProfil by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object ParametrageProfil whose createdBy is equals to the given createdBy. If
	 *         no ParametrageProfil is found, this method returns null.
	 */
	@Query("select e from ParametrageProfil e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<ParametrageProfil> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ParametrageProfil by using email as a search criteria.
	 * 
	 * @param email
	 * @return An Object ParametrageProfil whose email is equals to the given email. If
	 *         no ParametrageProfil is found, this method returns null.
	 */
	@Query("select e from ParametrageProfil e where e.email = :email and e.isDeleted = :isDeleted")
	ParametrageProfil findByEmail(@Param("email")String email, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ParametrageProfil by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object ParametrageProfil whose isDeleted is equals to the given isDeleted. If
	 *         no ParametrageProfil is found, this method returns null.
	 */
	@Query("select e from ParametrageProfil e where e.isDeleted = :isDeleted")
	List<ParametrageProfil> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ParametrageProfil by using nomPrenom as a search criteria.
	 * 
	 * @param nomPrenom
	 * @return An Object ParametrageProfil whose nomPrenom is equals to the given nomPrenom. If
	 *         no ParametrageProfil is found, this method returns null.
	 */
	@Query("select e from ParametrageProfil e where e.nomPrenom = :nomPrenom and e.isDeleted = :isDeleted")
	List<ParametrageProfil> findByNomPrenom(@Param("nomPrenom")String nomPrenom, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ParametrageProfil by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object ParametrageProfil whose updatedAt is equals to the given updatedAt. If
	 *         no ParametrageProfil is found, this method returns null.
	 */
	@Query("select e from ParametrageProfil e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<ParametrageProfil> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ParametrageProfil by using idParametrage as a search criteria.
	 * 
	 * @param idParametrage
	 * @return An Object ParametrageProfil whose idParametrage is equals to the given idParametrage. If
	 *         no ParametrageProfil is found, this method returns null.
	 */
	@Query("select e from ParametrageProfil e where e.idParametrage = :idParametrage and e.isDeleted = :isDeleted")
	List<ParametrageProfil> findByIdParametrage(@Param("idParametrage")Integer idParametrage, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ParametrageProfil by using numero as a search criteria.
	 * 
	 * @param numero
	 * @return An Object ParametrageProfil whose numero is equals to the given numero. If
	 *         no ParametrageProfil is found, this method returns null.
	 */
	@Query("select e from ParametrageProfil e where e.numero = :numero and e.isDeleted = :isDeleted")
	ParametrageProfil findByNumero(@Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of ParametrageProfil by using parametrageProfilDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of ParametrageProfil
	 * @throws DataAccessException,ParseException
	 */
	default List<ParametrageProfil> getByCriteria(Request<ParametrageProfilDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from ParametrageProfil e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<ParametrageProfil> query = em.createQuery(req, ParametrageProfil.class);
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
	 * Finds count of ParametrageProfil by using parametrageProfilDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of ParametrageProfil
	 * 
	 */
	default Long count(Request<ParametrageProfilDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from ParametrageProfil e where e IS NOT NULL";
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
	default String getWhereExpression(Request<ParametrageProfilDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		ParametrageProfilDto dto = request.getData() != null ? request.getData() : new ParametrageProfilDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (ParametrageProfilDto elt : request.getDatas()) {
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
	default String generateCriteria(ParametrageProfilDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
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
			if (Utilities.notBlank(dto.getEmail())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("email", dto.getEmail(), "e.email", "String", dto.getEmailParam(), param, index, locale));
			}
			if (dto.getIsDeleted()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getNomPrenom())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("nomPrenom", dto.getNomPrenom(), "e.nomPrenom", "String", dto.getNomPrenomParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUpdatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
			}
			if (dto.getIdParametrage()!= null && dto.getIdParametrage() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idParametrage", dto.getIdParametrage(), "e.idParametrage", "Integer", dto.getIdParametrageParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getNumero())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("numero", dto.getNumero(), "e.numero", "String", dto.getNumeroParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
