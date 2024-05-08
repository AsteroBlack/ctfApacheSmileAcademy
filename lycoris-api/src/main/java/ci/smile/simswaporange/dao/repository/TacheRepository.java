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
import ci.smile.simswaporange.dao.repository.customize._TacheRepository;

/**
 * Repository : Tache.
 */
@Repository
public interface TacheRepository extends JpaRepository<Tache, Integer>, _TacheRepository {
	/**
	 * Finds Tache by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Tache whose id is equals to the given id. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.id = :id and e.isDeleted = :isDeleted")
	Tache findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Tache by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Tache whose updatedAt is equals to the given updatedAt. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Tache> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Tache by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Tache whose createdAt is equals to the given createdAt. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Tache> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Tache by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Tache whose isDeleted is equals to the given isDeleted. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.isDeleted = :isDeleted")
	List<Tache> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Tache by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Tache whose updatedBy is equals to the given updatedBy. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Tache> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Tache by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Tache whose createdBy is equals to the given createdBy. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Tache> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Tache by using dateDemande as a search criteria.
	 * 
	 * @param dateDemande
	 * @return An Object Tache whose dateDemande is equals to the given dateDemande. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.dateDemande = :dateDemande and e.isDeleted = :isDeleted")
	List<Tache> findByDateDemande(@Param("dateDemande")Date dateDemande, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Tache by using idActionEnMasse as a search criteria.
	 * 
	 * @param idActionEnMasse
	 * @return An Object Tache whose idActionEnMasse is equals to the given idActionEnMasse. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.actionEnMasse.id = :idActionEnMasse and e.isDeleted = :isDeleted")
	List<Tache> findByIdActionEnMasse(@Param("idActionEnMasse")Integer idActionEnMasse, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Tache by using idActionSimswap as a search criteria.
	 * 
	 * @param idActionSimswap
	 * @return An Object Tache whose idActionSimswap is equals to the given idActionSimswap. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.actionSimswap.id = :idActionSimswap and e.isDeleted = :isDeleted")
	List<Tache> findByIdActionSimswap(@Param("idActionSimswap")Integer idActionSimswap, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Tache by using idDemande as a search criteria.
	 * 
	 * @param idDemande
	 * @return An Object Tache whose idDemande is equals to the given idDemande. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.demande.id = :idDemande and e.isDeleted = :isDeleted")
	List<Tache> findByIdDemande(@Param("idDemande")Integer idDemande, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Tache by using idStatus as a search criteria.
	 * 
	 * @param idStatus
	 * @return An Object Tache whose idStatus is equals to the given idStatus. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.status.id = :idStatus and e.isDeleted = :isDeleted")
	List<Tache> findByIdStatus(@Param("idStatus")Integer idStatus, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Tache by using idTypeDemande as a search criteria.
	 * 
	 * @param idTypeDemande
	 * @return An Object Tache whose idTypeDemande is equals to the given idTypeDemande. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.typeDemande.id = :idTypeDemande and e.isDeleted = :isDeleted")
	List<Tache> findByIdTypeDemande(@Param("idTypeDemande")Integer idTypeDemande, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Tache by using idUser as a search criteria.
	 * 
	 * @param idUser
	 * @return An Object Tache whose idUser is equals to the given idUser. If
	 *         no Tache is found, this method returns null.
	 */
	@Query("select e from Tache e where e.user.id = :idUser and e.isDeleted = :isDeleted")
	List<Tache> findByIdUser(@Param("idUser")Integer idUser, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Tache by using tacheDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Tache
	 * @throws DataAccessException,ParseException
	 */
	default List<Tache> getByCriteria(Request<TacheDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Tache e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Tache> query = em.createQuery(req, Tache.class);
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
	 * Finds count of Tache by using tacheDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Tache
	 * 
	 */
	default Long count(Request<TacheDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Tache e where e IS NOT NULL";
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
	default String getWhereExpression(Request<TacheDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		TacheDto dto = request.getData() != null ? request.getData() : new TacheDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (TacheDto elt : request.getDatas()) {
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
	default String generateCriteria(TacheDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
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
			if (Utilities.notBlank(dto.getDateDemande())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("dateDemande", dto.getDateDemande(), "e.dateDemande", "Date", dto.getDateDemandeParam(), param, index, locale));
			}
			if (dto.getIdActionEnMasse()!= null && dto.getIdActionEnMasse() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idActionEnMasse", dto.getIdActionEnMasse(), "e.actionEnMasse.id", "Integer", dto.getIdActionEnMasseParam(), param, index, locale));
			}
			if (dto.getIdActionSimswap()!= null && dto.getIdActionSimswap() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idActionSimswap", dto.getIdActionSimswap(), "e.actionSimswap.id", "Integer", dto.getIdActionSimswapParam(), param, index, locale));
			}
			if (dto.getIdDemande()!= null && dto.getIdDemande() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idDemande", dto.getIdDemande(), "e.demande.id", "Integer", dto.getIdDemandeParam(), param, index, locale));
			}
			if (dto.getIdStatus()!= null && dto.getIdStatus() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idStatus", dto.getIdStatus(), "e.status.id", "Integer", dto.getIdStatusParam(), param, index, locale));
			}
			if (dto.getIdTypeDemande()!= null && dto.getIdTypeDemande() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idTypeDemande", dto.getIdTypeDemande(), "e.typeDemande.id", "Integer", dto.getIdTypeDemandeParam(), param, index, locale));
			}
			if (dto.getIdUser()!= null && dto.getIdUser() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idUser", dto.getIdUser(), "e.user.id", "Integer", dto.getIdUserParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActionEnMasseCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("actionEnMasseCode", dto.getActionEnMasseCode(), "e.actionEnMasse.code", "String", dto.getActionEnMasseCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActionEnMasseLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("actionEnMasseLibelle", dto.getActionEnMasseLibelle(), "e.actionEnMasse.libelle", "String", dto.getActionEnMasseLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActionSimswapLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("actionSimswapLibelle", dto.getActionSimswapLibelle(), "e.actionSimswap.libelle", "String", dto.getActionSimswapLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStatusCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statusCode", dto.getStatusCode(), "e.status.code", "String", dto.getStatusCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getTypeDemandeCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("typeDemandeCode", dto.getTypeDemandeCode(), "e.typeDemande.code", "String", dto.getTypeDemandeCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getTypeDemandeLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("typeDemandeLibelle", dto.getTypeDemandeLibelle(), "e.typeDemande.libelle", "String", dto.getTypeDemandeLibelleParam(), param, index, locale));
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
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
