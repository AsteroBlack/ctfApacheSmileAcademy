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
import ci.smile.simswaporange.dao.repository.customize._ProfilRepository;

/**
 * Repository : Profil.
 */
@Repository
public interface ProfilRepository extends JpaRepository<Profil, Integer>, _ProfilRepository {
	/**
	 * Finds Profil by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Profil whose id is equals to the given id. If
	 *         no Profil is found, this method returns null.
	 */
	@Query("select e from Profil e where e.id = :id and e.isDeleted = :isDeleted")
	Profil findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Profil by using code as a search criteria.
	 * 
	 * @param code
	 * @return An Object Profil whose code is equals to the given code. If
	 *         no Profil is found, this method returns null.
	 */
	@Query("select e from Profil e where e.code = :code and e.isDeleted = :isDeleted")
	Profil findByCode(@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Profil by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Profil whose createdAt is equals to the given createdAt. If
	 *         no Profil is found, this method returns null.
	 */
	@Query("select e from Profil e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Profil> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Profil by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Profil whose createdBy is equals to the given createdBy. If
	 *         no Profil is found, this method returns null.
	 */
	@Query("select e from Profil e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Profil> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Profil by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Profil whose isDeleted is equals to the given isDeleted. If
	 *         no Profil is found, this method returns null.
	 */
	@Query("select e from Profil e where e.isDeleted = :isDeleted")
	List<Profil> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Profil by using libelle as a search criteria.
	 * 
	 * @param libelle
	 * @return An Object Profil whose libelle is equals to the given libelle. If
	 *         no Profil is found, this method returns null.
	 */
	@Query("select e from Profil e where e.libelle = :libelle and e.isDeleted = :isDeleted")
	Profil findByLibelle(@Param("libelle")String libelle, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Profil by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Profil whose updatedAt is equals to the given updatedAt. If
	 *         no Profil is found, this method returns null.
	 */
	@Query("select e from Profil e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Profil> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Profil by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Profil whose updatedBy is equals to the given updatedBy. If
	 *         no Profil is found, this method returns null.
	 */
	@Query("select e from Profil e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Profil> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Profil by using deletedAt as a search criteria.
	 * 
	 * @param deletedAt
	 * @return An Object Profil whose deletedAt is equals to the given deletedAt. If
	 *         no Profil is found, this method returns null.
	 */
	@Query("select e from Profil e where e.deletedAt = :deletedAt and e.isDeleted = :isDeleted")
	List<Profil> findByDeletedAt(@Param("deletedAt")Date deletedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Profil by using deletedBy as a search criteria.
	 * 
	 * @param deletedBy
	 * @return An Object Profil whose deletedBy is equals to the given deletedBy. If
	 *         no Profil is found, this method returns null.
	 */
	@Query("select e from Profil e where e.deletedBy = :deletedBy and e.isDeleted = :isDeleted")
	List<Profil> findByDeletedBy(@Param("deletedBy")Integer deletedBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Profil by using profilDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Profil
	 * @throws DataAccessException,ParseException
	 */
	default List<Profil> getByCriteria(Request<ProfilDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Profil e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Profil> query = em.createQuery(req, Profil.class);
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
	 * Finds count of Profil by using profilDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Profil
	 * 
	 */
	default Long count(Request<ProfilDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Profil e where e IS NOT NULL";
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
	default String getWhereExpression(Request<ProfilDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		ProfilDto dto = request.getData() != null ? request.getData() : new ProfilDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (ProfilDto elt : request.getDatas()) {
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
	default String generateCriteria(ProfilDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("code", dto.getCode(), "e.code", "String", dto.getCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCorail())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("corail", dto.getCorail(), "e.corail", "String", dto.getCorailParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCreatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
			}
			if (dto.getCreatedBy()!= null && dto.getCreatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
			}
			if (dto.getIsDeleted()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("libelle", dto.getLibelle(), "e.libelle", "String", dto.getLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUpdatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
			}
			if (dto.getUpdatedBy()!= null && dto.getUpdatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedBy", dto.getUpdatedBy(), "e.updatedBy", "Integer", dto.getUpdatedByParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getDeletedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("deletedAt", dto.getDeletedAt(), "e.deletedAt", "Date", dto.getDeletedAtParam(), param, index, locale));
			}
			if (dto.getDeletedBy()!= null && dto.getDeletedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("deletedBy", dto.getDeletedBy(), "e.deletedBy", "Integer", dto.getDeletedByParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
