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
import ci.smile.simswaporange.dao.repository.customize._ActionEnMasseRepository;

/**
 * Repository : ActionEnMasse.
 */
@Repository
public interface ActionEnMasseRepository extends JpaRepository<ActionEnMasse, Integer>, _ActionEnMasseRepository {
	/**
	 * Finds ActionEnMasse by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object ActionEnMasse whose id is equals to the given id. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.id = :id and e.isDeleted = :isDeleted")
	ActionEnMasse findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ActionEnMasse by using code as a search criteria.
	 * 
	 * @param code
	 * @return An Object ActionEnMasse whose code is equals to the given code. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.code = :code and e.isDeleted = :isDeleted")
	ActionEnMasse findByCode(@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using libelle as a search criteria.
	 * 
	 * @param libelle
	 * @return An Object ActionEnMasse whose libelle is equals to the given libelle. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.libelle = :libelle and e.isDeleted = :isDeleted")
	ActionEnMasse findByLibelle(@Param("libelle")String libelle, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using lienFichier as a search criteria.
	 * 
	 * @param lienFichier
	 * @return An Object ActionEnMasse whose lienFichier is equals to the given lienFichier. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.lienFichier = :lienFichier and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByLienFichier(@Param("lienFichier")String lienFichier, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object ActionEnMasse whose updatedAt is equals to the given updatedAt. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object ActionEnMasse whose createdAt is equals to the given createdAt. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object ActionEnMasse whose isDeleted is equals to the given isDeleted. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object ActionEnMasse whose updatedBy is equals to the given updatedBy. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object ActionEnMasse whose createdBy is equals to the given createdBy. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using identifiant as a search criteria.
	 * 
	 * @param identifiant
	 * @return An Object ActionEnMasse whose identifiant is equals to the given identifiant. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.identifiant = :identifiant and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByIdentifiant(@Param("identifiant")String identifiant, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using isOk as a search criteria.
	 * 
	 * @param isOk
	 * @return An Object ActionEnMasse whose isOk is equals to the given isOk. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.isOk = :isOk and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByIsOk(@Param("isOk")Boolean isOk, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using lienImport as a search criteria.
	 * 
	 * @param lienImport
	 * @return An Object ActionEnMasse whose lienImport is equals to the given lienImport. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.lienImport = :lienImport and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByLienImport(@Param("lienImport")String lienImport, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using linkDownload as a search criteria.
	 * 
	 * @param linkDownload
	 * @return An Object ActionEnMasse whose linkDownload is equals to the given linkDownload. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.linkDownload = :linkDownload and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByLinkDownload(@Param("linkDownload")String linkDownload, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using putOn as a search criteria.
	 * 
	 * @param putOn
	 * @return An Object ActionEnMasse whose putOn is equals to the given putOn. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.putOn = :putOn and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByPutOn(@Param("putOn")Boolean putOn, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using putOf as a search criteria.
	 * 
	 * @param putOf
	 * @return An Object ActionEnMasse whose putOf is equals to the given putOf. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.putOf = :putOf and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByPutOf(@Param("putOf")Boolean putOf, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using linkDownloadMasse as a search criteria.
	 * 
	 * @param linkDownloadMasse
	 * @return An Object ActionEnMasse whose linkDownloadMasse is equals to the given linkDownloadMasse. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.linkDownloadMasse = :linkDownloadMasse and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByLinkDownloadMasse(@Param("linkDownloadMasse")String linkDownloadMasse, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using error as a search criteria.
	 * 
	 * @param error
	 * @return An Object ActionEnMasse whose error is equals to the given error. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.error = :error and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByError(@Param("error")Boolean error, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionEnMasse by using isAutomatically as a search criteria.
	 * 
	 * @param isAutomatically
	 * @return An Object ActionEnMasse whose isAutomatically is equals to the given isAutomatically. If
	 *         no ActionEnMasse is found, this method returns null.
	 */
	@Query("select e from ActionEnMasse e where e.isAutomatically = :isAutomatically and e.isDeleted = :isDeleted")
	List<ActionEnMasse> findByIsAutomatically(@Param("isAutomatically")Boolean isAutomatically, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of ActionEnMasse by using actionEnMasseDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of ActionEnMasse
	 * @throws DataAccessException,ParseException
	 */
	default List<ActionEnMasse> getByCriteria(Request<ActionEnMasseDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from ActionEnMasse e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<ActionEnMasse> query = em.createQuery(req, ActionEnMasse.class);
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
	 * Finds count of ActionEnMasse by using actionEnMasseDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of ActionEnMasse
	 * 
	 */
	default Long count(Request<ActionEnMasseDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from ActionEnMasse e where e IS NOT NULL";
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
	default String getWhereExpression(Request<ActionEnMasseDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		ActionEnMasseDto dto = request.getData() != null ? request.getData() : new ActionEnMasseDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (ActionEnMasseDto elt : request.getDatas()) {
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
	default String generateCriteria(ActionEnMasseDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("code", dto.getCode(), "e.code", "String", dto.getCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("libelle", dto.getLibelle(), "e.libelle", "String", dto.getLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLienFichier())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("lienFichier", dto.getLienFichier(), "e.lienFichier", "String", dto.getLienFichierParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUpdatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCreatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
			}
			if (dto.getIsDeleted()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
			}
			if (dto.getUpdatedBy()!= null && dto.getUpdatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedBy", dto.getUpdatedBy(), "e.updatedBy", "Integer", dto.getUpdatedByParam(), param, index, locale));
			}
			if (dto.getCreatedBy()!= null && dto.getCreatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getIdentifiant())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("identifiant", dto.getIdentifiant(), "e.identifiant", "String", dto.getIdentifiantParam(), param, index, locale));
			}
			if (dto.getIsOk()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isOk", dto.getIsOk(), "e.isOk", "Boolean", dto.getIsOkParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLienImport())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("lienImport", dto.getLienImport(), "e.lienImport", "String", dto.getLienImportParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLinkDownload())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("linkDownload", dto.getLinkDownload(), "e.linkDownload", "String", dto.getLinkDownloadParam(), param, index, locale));
			}
			if (dto.getPutOn()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("putOn", dto.getPutOn(), "e.putOn", "Boolean", dto.getPutOnParam(), param, index, locale));
			}
			if (dto.getPutOf()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("putOf", dto.getPutOf(), "e.putOf", "Boolean", dto.getPutOfParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLinkDownloadMasse())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("linkDownloadMasse", dto.getLinkDownloadMasse(), "e.linkDownloadMasse", "String", dto.getLinkDownloadMasseParam(), param, index, locale));
			}
			if (dto.getError()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("error", dto.getError(), "e.error", "Boolean", dto.getErrorParam(), param, index, locale));
			}
			if (dto.getIsAutomatically()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isAutomatically", dto.getIsAutomatically(), "e.isAutomatically", "Boolean", dto.getIsAutomaticallyParam(), param, index, locale));
			}
			if (dto.getIsDebloageEnMasse()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isDebloageEnMasse", dto.getIsDebloageEnMasse(), "e.isDebloageEnMasse", "Boolean", dto.getIsDebloageEnMasseParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
