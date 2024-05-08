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
import ci.smile.simswaporange.dao.repository.customize._ProfilFonctionnaliteRepository;

/**
 * Repository : ProfilFonctionnalite.
 */
@Repository
public interface ProfilFonctionnaliteRepository extends JpaRepository<ProfilFonctionnalite, Integer>, _ProfilFonctionnaliteRepository {
	/**
	 * Finds ProfilFonctionnalite by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object ProfilFonctionnalite whose id is equals to the given id. If
	 *         no ProfilFonctionnalite is found, this method returns null.
	 */
	@Query("select e from ProfilFonctionnalite e where e.id = :id and e.isDeleted = :isDeleted")
	ProfilFonctionnalite findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ProfilFonctionnalite by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object ProfilFonctionnalite whose createdAt is equals to the given createdAt. If
	 *         no ProfilFonctionnalite is found, this method returns null.
	 */
	@Query("select e from ProfilFonctionnalite e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<ProfilFonctionnalite> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ProfilFonctionnalite by using code as a search criteria.
	 * 
	 * @param code
	 * @return An Object ProfilFonctionnalite whose code is equals to the given code. If
	 *         no ProfilFonctionnalite is found, this method returns null.
	 */
	@Query("select e from ProfilFonctionnalite e where e.code = :code and e.isDeleted = :isDeleted")
	ProfilFonctionnalite findByCode(@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ProfilFonctionnalite by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object ProfilFonctionnalite whose createdBy is equals to the given createdBy. If
	 *         no ProfilFonctionnalite is found, this method returns null.
	 */
	@Query("select e from ProfilFonctionnalite e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<ProfilFonctionnalite> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ProfilFonctionnalite by using libelle as a search criteria.
	 * 
	 * @param libelle
	 * @return An Object ProfilFonctionnalite whose libelle is equals to the given libelle. If
	 *         no ProfilFonctionnalite is found, this method returns null.
	 */
	@Query("select e from ProfilFonctionnalite e where e.libelle = :libelle and e.isDeleted = :isDeleted")
	ProfilFonctionnalite findByLibelle(@Param("libelle")String libelle, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ProfilFonctionnalite by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object ProfilFonctionnalite whose isDeleted is equals to the given isDeleted. If
	 *         no ProfilFonctionnalite is found, this method returns null.
	 */
	@Query("select e from ProfilFonctionnalite e where e.isDeleted = :isDeleted")
	List<ProfilFonctionnalite> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ProfilFonctionnalite by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object ProfilFonctionnalite whose updatedAt is equals to the given updatedAt. If
	 *         no ProfilFonctionnalite is found, this method returns null.
	 */
	@Query("select e from ProfilFonctionnalite e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<ProfilFonctionnalite> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ProfilFonctionnalite by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object ProfilFonctionnalite whose updatedBy is equals to the given updatedBy. If
	 *         no ProfilFonctionnalite is found, this method returns null.
	 */
	@Query("select e from ProfilFonctionnalite e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<ProfilFonctionnalite> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ProfilFonctionnalite by using deletedAt as a search criteria.
	 * 
	 * @param deletedAt
	 * @return An Object ProfilFonctionnalite whose deletedAt is equals to the given deletedAt. If
	 *         no ProfilFonctionnalite is found, this method returns null.
	 */
	@Query("select e from ProfilFonctionnalite e where e.deletedAt = :deletedAt and e.isDeleted = :isDeleted")
	List<ProfilFonctionnalite> findByDeletedAt(@Param("deletedAt")Date deletedAt, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ProfilFonctionnalite by using fonctionnaliteId as a search criteria.
	 * 
	 * @param fonctionnaliteId
	 * @return An Object ProfilFonctionnalite whose fonctionnaliteId is equals to the given fonctionnaliteId. If
	 *         no ProfilFonctionnalite is found, this method returns null.
	 */
	@Query("select e from ProfilFonctionnalite e where e.fonctionnalite.id = :fonctionnaliteId and e.isDeleted = :isDeleted")
	List<ProfilFonctionnalite> findByFonctionnaliteId(@Param("fonctionnaliteId")Integer fonctionnaliteId, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ProfilFonctionnalite by using profilId as a search criteria.
	 * 
	 * @param profilId
	 * @return An Object ProfilFonctionnalite whose profilId is equals to the given profilId. If
	 *         no ProfilFonctionnalite is found, this method returns null.
	 */
	@Query("select e from ProfilFonctionnalite e where e.profil.id = :profilId and e.isDeleted = :isDeleted")
	List<ProfilFonctionnalite> findByProfilId(@Param("profilId")Integer profilId, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of ProfilFonctionnalite by using profilFonctionnaliteDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of ProfilFonctionnalite
	 * @throws DataAccessException,ParseException
	 */
	default List<ProfilFonctionnalite> getByCriteria(Request<ProfilFonctionnaliteDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from ProfilFonctionnalite e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<ProfilFonctionnalite> query = em.createQuery(req, ProfilFonctionnalite.class);
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
	 * Finds count of ProfilFonctionnalite by using profilFonctionnaliteDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of ProfilFonctionnalite
	 * 
	 */
	default Long count(Request<ProfilFonctionnaliteDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from ProfilFonctionnalite e where e IS NOT NULL";
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
	default String getWhereExpression(Request<ProfilFonctionnaliteDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		ProfilFonctionnaliteDto dto = request.getData() != null ? request.getData() : new ProfilFonctionnaliteDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (ProfilFonctionnaliteDto elt : request.getDatas()) {
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
	default String generateCriteria(ProfilFonctionnaliteDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCreatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("code", dto.getCode(), "e.code", "String", dto.getCodeParam(), param, index, locale));
			}
			if (dto.getCreatedBy()!= null && dto.getCreatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("libelle", dto.getLibelle(), "e.libelle", "String", dto.getLibelleParam(), param, index, locale));
			}
			if (dto.getIsDeleted()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
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
			if (dto.getFonctionnaliteId()!= null && dto.getFonctionnaliteId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("fonctionnaliteId", dto.getFonctionnaliteId(), "e.fonctionnalite.id", "Integer", dto.getFonctionnaliteIdParam(), param, index, locale));
			}
			if (dto.getProfilId()!= null && dto.getProfilId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("profilId", dto.getProfilId(), "e.profil.id", "Integer", dto.getProfilIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getFonctionnaliteCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("fonctionnaliteCode", dto.getFonctionnaliteCode(), "e.fonctionnalite.code", "String", dto.getFonctionnaliteCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getFonctionnaliteLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("fonctionnaliteLibelle", dto.getFonctionnaliteLibelle(), "e.fonctionnalite.libelle", "String", dto.getFonctionnaliteLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getProfilCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("profilCode", dto.getProfilCode(), "e.profil.code", "String", dto.getProfilCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getProfilLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("profilLibelle", dto.getProfilLibelle(), "e.profil.libelle", "String", dto.getProfilLibelleParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
