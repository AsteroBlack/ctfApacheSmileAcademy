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
import ci.smile.simswaporange.dao.repository.customize._AtionToNotifiableRepository;

/**
 * Repository : AtionToNotifiable.
 */
@Repository
public interface AtionToNotifiableRepository extends JpaRepository<AtionToNotifiable, Integer>, _AtionToNotifiableRepository {
	/**
	 * Finds AtionToNotifiable by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object AtionToNotifiable whose id is equals to the given id. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.id = :id and e.isDeleted = :isDeleted")
	AtionToNotifiable findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds AtionToNotifiable by using numero as a search criteria.
	 * 
	 * @param numero
	 * @return An Object AtionToNotifiable whose numero is equals to the given numero. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.numero = :numero and e.isDeleted = :isDeleted")
	AtionToNotifiable findByNumero(@Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds AtionToNotifiable by using isNotify as a search criteria.
	 * 
	 * @param isNotify
	 * @return An Object AtionToNotifiable whose isNotify is equals to the given isNotify. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.isNotify = :isNotify and e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByIsNotify(@Param("isNotify")Boolean isNotify, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds AtionToNotifiable by using isEnMasse as a search criteria.
	 * 
	 * @param isEnMasse
	 * @return An Object AtionToNotifiable whose isEnMasse is equals to the given isEnMasse. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.isEnMasse = :isEnMasse and e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByIsEnMasse(@Param("isEnMasse")Boolean isEnMasse, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds AtionToNotifiable by using statutAction as a search criteria.
	 * 
	 * @param statutAction
	 * @return An Object AtionToNotifiable whose statutAction is equals to the given statutAction. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.statutAction = :statutAction and e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByStatutAction(@Param("statutAction")String statutAction, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds AtionToNotifiable by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object AtionToNotifiable whose createdAt is equals to the given createdAt. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds AtionToNotifiable by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object AtionToNotifiable whose createdBy is equals to the given createdBy. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds AtionToNotifiable by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object AtionToNotifiable whose updatedAt is equals to the given updatedAt. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds AtionToNotifiable by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object AtionToNotifiable whose updatedBy is equals to the given updatedBy. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds AtionToNotifiable by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object AtionToNotifiable whose isDeleted is equals to the given isDeleted. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds AtionToNotifiable by using idUserDemand as a search criteria.
	 * 
	 * @param idUserDemand
	 * @return An Object AtionToNotifiable whose idUserDemand is equals to the given idUserDemand. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.user.id = :idUserDemand and e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByIdUserDemand(@Param("idUserDemand")Integer idUserDemand, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds AtionToNotifiable by using idAction as a search criteria.
	 * 
	 * @param idAction
	 * @return An Object AtionToNotifiable whose idAction is equals to the given idAction. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.actionSimswap.id = :idAction and e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByIdAction(@Param("idAction")Integer idAction, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds AtionToNotifiable by using idStatus as a search criteria.
	 * 
	 * @param idStatus
	 * @return An Object AtionToNotifiable whose idStatus is equals to the given idStatus. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.status.id = :idStatus and e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByIdStatus(@Param("idStatus")Integer idStatus, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds AtionToNotifiable by using idUserValid as a search criteria.
	 * 
	 * @param idUserValid
	 * @return An Object AtionToNotifiable whose idUserValid is equals to the given idUserValid. If
	 *         no AtionToNotifiable is found, this method returns null.
	 */
	@Query("select e from AtionToNotifiable e where e.user2.id = :idUserValid and e.isDeleted = :isDeleted")
	List<AtionToNotifiable> findByIdUserValid(@Param("idUserValid")Integer idUserValid, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of AtionToNotifiable by using ationToNotifiableDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of AtionToNotifiable
	 * @throws DataAccessException,ParseException
	 */
	default List<AtionToNotifiable> getByCriteria(Request<AtionToNotifiableDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from AtionToNotifiable e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<AtionToNotifiable> query = em.createQuery(req, AtionToNotifiable.class);
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
	 * Finds count of AtionToNotifiable by using ationToNotifiableDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of AtionToNotifiable
	 * 
	 */
	default Long count(Request<AtionToNotifiableDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from AtionToNotifiable e where e IS NOT NULL";
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
	default String getWhereExpression(Request<AtionToNotifiableDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		AtionToNotifiableDto dto = request.getData() != null ? request.getData() : new AtionToNotifiableDto();
		dto.setIsDeleted(true);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (AtionToNotifiableDto elt : request.getDatas()) {
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
	default String generateCriteria(AtionToNotifiableDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getNumero())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("numero", dto.getNumero(), "e.numero", "String", dto.getNumeroParam(), param, index, locale));
			}
			if (dto.getIsNotify()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isNotify", dto.getIsNotify(), "e.isNotify", "Boolean", dto.getIsNotifyParam(), param, index, locale));
			}
			if (dto.getIsEnMasse()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isEnMasse", dto.getIsEnMasse(), "e.isEnMasse", "Boolean", dto.getIsEnMasseParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStatutAction())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statutAction", dto.getStatutAction(), "e.statutAction", "String", dto.getStatutActionParam(), param, index, locale));
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
			if (dto.getIdUserDemand()!= null && dto.getIdUserDemand() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idUserDemand", dto.getIdUserDemand(), "e.user.id", "Integer", dto.getIdUserDemandParam(), param, index, locale));
			}
			if (dto.getIdAction()!= null && dto.getIdAction() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idAction", dto.getIdAction(), "e.actionParametrable.id", "Integer", dto.getIdActionParam(), param, index, locale));
			}
			if (dto.getIdStatus()!= null && dto.getIdStatus() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idStatus", dto.getIdStatus(), "e.status.id", "Integer", dto.getIdStatusParam(), param, index, locale));
			}
			if (dto.getIdUserValid()!= null && dto.getIdUserValid() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idUserValid", dto.getIdUserValid(), "e.user2.id", "Integer", dto.getIdUserValidParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUserNom())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("userNom", dto.getUserNom(), "e.user.nom", "String", dto.getUserNomParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUserPrenom())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("userPrenom", dto.getUserPrenom(), "e.user.prenom", "String", dto.getUserPrenomParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUserLogin())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("userLogin", dto.getUserLogin(), "e.user.login", "String", dto.getUserLoginParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActionSimswapLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("actionSimswapLibelle", dto.getActionSimswapLibelle(), "e.actionSimswap.libelle", "String", dto.getActionSimswapLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStatusCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statusCode", dto.getStatusCode(), "e.status.code", "String", dto.getStatusCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUserNom())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("userNom", dto.getUserNom(), "e.user2.nom", "String", dto.getUserNomParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUserPrenom())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("userPrenom", dto.getUserPrenom(), "e.user2.prenom", "String", dto.getUserPrenomParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUserLogin())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("userLogin", dto.getUserLogin(), "e.user2.login", "String", dto.getUserLoginParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
