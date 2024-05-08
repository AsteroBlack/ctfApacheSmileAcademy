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
import ci.smile.simswaporange.dao.repository.customize._HistoriqueRepository;

/**
 * Repository : Historique.
 */
@Repository
public interface HistoriqueRepository extends JpaRepository<Historique, Integer>, _HistoriqueRepository {
	/**
	 * Finds Historique by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Historique whose id is equals to the given id. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.id = :id and e.isDeleted = :isDeleted")
	Historique findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Historique by using request as a search criteria.
	 * 
	 * @param request
	 * @return An Object Historique whose request is equals to the given request. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.request = :request and e.isDeleted = :isDeleted")
	List<Historique> findByRequest(@Param("request")String request, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using response as a search criteria.
	 * 
	 * @param response
	 * @return An Object Historique whose response is equals to the given response. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.response = :response and e.isDeleted = :isDeleted")
	List<Historique> findByResponse(@Param("response")String response, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using idUser as a search criteria.
	 * 
	 * @param idUser
	 * @return An Object Historique whose idUser is equals to the given idUser. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.user.id = :idUser and e.isDeleted = :isDeleted")
	List<Historique> findByIdUser(@Param("idUser")Integer idUser, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Historique whose createdAt is equals to the given createdAt. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Historique> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Historique whose createdBy is equals to the given createdBy. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Historique> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Historique whose updatedAt is equals to the given updatedAt. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Historique> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Historique whose updatedBy is equals to the given updatedBy. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Historique> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Historique whose isDeleted is equals to the given isDeleted. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.isDeleted = :isDeleted")
	List<Historique> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using idStatus as a search criteria.
	 * 
	 * @param idStatus
	 * @return An Object Historique whose idStatus is equals to the given idStatus. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.status.id = :idStatus and e.isDeleted = :isDeleted")
	List<Historique> findByIdStatus(@Param("idStatus")Integer idStatus, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using date as a search criteria.
	 * 
	 * @param date
	 * @return An Object Historique whose date is equals to the given date. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.date = :date and e.isDeleted = :isDeleted")
	List<Historique> findByDate(@Param("date")Date date, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using actionService as a search criteria.
	 * 
	 * @param actionService
	 * @return An Object Historique whose actionService is equals to the given actionService. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.actionService = :actionService and e.isDeleted = :isDeleted")
	List<Historique> findByActionService(@Param("actionService")String actionService, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using login as a search criteria.
	 * 
	 * @param login
	 * @return An Object Historique whose login is equals to the given login. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.login = :login and e.isDeleted = :isDeleted")
	Historique findByLogin(@Param("login")String login, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using nom as a search criteria.
	 * 
	 * @param nom
	 * @return An Object Historique whose nom is equals to the given nom. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.nom = :nom and e.isDeleted = :isDeleted")
	List<Historique> findByNom(@Param("nom")String nom, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using prenom as a search criteria.
	 * 
	 * @param prenom
	 * @return An Object Historique whose prenom is equals to the given prenom. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.prenom = :prenom and e.isDeleted = :isDeleted")
	List<Historique> findByPrenom(@Param("prenom")String prenom, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using isConnexion as a search criteria.
	 * 
	 * @param isConnexion
	 * @return An Object Historique whose isConnexion is equals to the given isConnexion. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.isConnexion = :isConnexion and e.isDeleted = :isDeleted")
	List<Historique> findByIsConnexion(@Param("isConnexion")Boolean isConnexion, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using statusConnection as a search criteria.
	 * 
	 * @param statusConnection
	 * @return An Object Historique whose statusConnection is equals to the given statusConnection. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.statusConnection = :statusConnection and e.isDeleted = :isDeleted")
	List<Historique> findByStatusConnection(@Param("statusConnection")String statusConnection, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using machine as a search criteria.
	 * 
	 * @param machine
	 * @return An Object Historique whose machine is equals to the given machine. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.machine = :machine and e.isDeleted = :isDeleted")
	List<Historique> findByMachine(@Param("machine")String machine, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Historique by using ipadress as a search criteria.
	 * 
	 * @param ipadress
	 * @return An Object Historique whose ipadress is equals to the given ipadress. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.ipadress = :ipadress and e.isDeleted = :isDeleted")
	List<Historique> findByIpadress(@Param("ipadress")String ipadress, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Historique by using idActionUser as a search criteria.
	 * 
	 * @param idActionUser
	 * @return An Object Historique whose idActionUser is equals to the given idActionUser. If
	 *         no Historique is found, this method returns null.
	 */
	@Query("select e from Historique e where e.actionSimswap.id = :idActionUser and e.isDeleted = :isDeleted")
	List<Historique> findByIdActionUser(@Param("idActionUser")Integer idActionUser, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Historique by using historiqueDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Historique
	 * @throws DataAccessException,ParseException
	 */
	default List<Historique> getByCriteria(Request<HistoriqueDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Historique e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Historique> query = em.createQuery(req, Historique.class);
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
	 * Finds count of Historique by using historiqueDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Historique
	 * 
	 */
	default Long count(Request<HistoriqueDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Historique e where e IS NOT NULL";
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
	default String getWhereExpression(Request<HistoriqueDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		HistoriqueDto dto = request.getData() != null ? request.getData() : new HistoriqueDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (HistoriqueDto elt : request.getDatas()) {
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
	default String generateCriteria(HistoriqueDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getRequest())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("request", dto.getRequest(), "e.request", "String", dto.getRequestParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getResponse())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("response", dto.getResponse(), "e.response", "String", dto.getResponseParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCreatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUri())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("uri", dto.getUri(), "e.uri", "String", dto.getUriParam(), param, index, locale));
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
			if (dto.getIdStatus()!= null && dto.getIdStatus() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idStatus", dto.getIdStatus(), "e.idStatus", "Integer", dto.getIdStatusParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getDate())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("date", dto.getDate(), "e.date", "Date", dto.getDateParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActionService())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("actionService", dto.getActionService(), "e.actionService", "String", dto.getActionServiceParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLogin())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("login", dto.getLogin(), "e.login", "String", dto.getLoginParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getNom())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("nom", dto.getNom(), "e.nom", "String", dto.getNomParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getNumero())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("numero", dto.getNumero(), "e.numero", "String", dto.getNumeroParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getPrenom())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("prenom", dto.getPrenom(), "e.prenom", "String", dto.getPrenomParam(), param, index, locale));
			}
			if (dto.getIsConnexion()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isConnexion", dto.getIsConnexion(), "e.isConnexion", "Boolean", dto.getIsConnexionParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStatusConnection())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statusConnection", dto.getStatusConnection(), "e.statusConnection", "String", dto.getStatusConnectionParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getMachine())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("machine", dto.getMachine(), "e.machine", "String", dto.getMachineParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getIpadress())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("ipadress", dto.getIpadress(), "e.ipadress", "String", dto.getIpadressParam(), param, index, locale));
			}
			if (dto.getIdActionUser()!= null && dto.getIdActionUser() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idActionUser", dto.getIdActionUser(), "e.actionSimswap.id", "Integer", dto.getIdActionUserParam(), param, index, locale));
			}
			if (dto.getIdUser()!= null && dto.getIdUser() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idUser", dto.getIdUser(), "e.user.id", "Integer", dto.getIdUserParam(), param, index, locale));
			}
			if (dto.getIdStatus()!= null && dto.getIdStatus() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idStatus", dto.getIdStatus(), "e.status.id", "Integer", dto.getIdStatusParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActionSimswapLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("actionSimswapLibelle", dto.getActionSimswapLibelle(), "e.actionSimswap.libelle", "String", dto.getActionSimswapLibelleParam(), param, index, locale));
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
			if (Utilities.notBlank(dto.getStatusCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statusCode", dto.getStatusCode(), "e.status.code", "String", dto.getStatusCodeParam(), param, index, locale));
			}

			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
