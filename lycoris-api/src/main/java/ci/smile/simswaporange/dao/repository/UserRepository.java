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
import ci.smile.simswaporange.dao.repository.customize._UserRepository;

/**
 * Repository : User.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, _UserRepository {
	/**
	 * Finds User by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object User whose id is equals to the given id. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.id = :id and e.isDeleted = :isDeleted")
	User findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds User by using nom as a search criteria.
	 * 
	 * @param nom
	 * @return An Object User whose nom is equals to the given nom. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.nom = :nom and e.isDeleted = :isDeleted")
	List<User> findByNom(@Param("nom")String nom, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using prenom as a search criteria.
	 * 
	 * @param prenom
	 * @return An Object User whose prenom is equals to the given prenom. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.prenom = :prenom and e.isDeleted = :isDeleted")
	List<User> findByPrenom(@Param("prenom")String prenom, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using isValidated as a search criteria.
	 * 
	 * @param isValidated
	 * @return An Object User whose isValidated is equals to the given isValidated. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.isValidated = :isValidated and e.isDeleted = :isDeleted")
	List<User> findByIsValidated(@Param("isValidated")Boolean isValidated, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object User whose createdAt is equals to the given createdAt. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<User> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object User whose createdBy is equals to the given createdBy. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<User> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object User whose updatedAt is equals to the given updatedAt. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<User> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object User whose updatedBy is equals to the given updatedBy. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<User> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object User whose isDeleted is equals to the given isDeleted. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.isDeleted = :isDeleted")
	List<User> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using isLocked as a search criteria.
	 * 
	 * @param isLocked
	 * @return An Object User whose isLocked is equals to the given isLocked. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.isLocked = :isLocked and e.isDeleted = :isDeleted")
	List<User> findByIsLocked(@Param("isLocked")Boolean isLocked, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using lockedAt as a search criteria.
	 * 
	 * @param lockedAt
	 * @return An Object User whose lockedAt is equals to the given lockedAt. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.lockedAt = :lockedAt and e.isDeleted = :isDeleted")
	List<User> findByLockedAt(@Param("lockedAt")Date lockedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using lockedBy as a search criteria.
	 * 
	 * @param lockedBy
	 * @return An Object User whose lockedBy is equals to the given lockedBy. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.lockedBy = :lockedBy and e.isDeleted = :isDeleted")
	List<User> findByLockedBy(@Param("lockedBy")Integer lockedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using login as a search criteria.
	 * 
	 * @param login
	 * @return An Object User whose login is equals to the given login. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.login = :login and e.isDeleted = :isDeleted")
	User findByLogin(@Param("login")String login, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using isSuperAdmin as a search criteria.
	 * 
	 * @param isSuperAdmin
	 * @return An Object User whose isSuperAdmin is equals to the given isSuperAdmin. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.isSuperAdmin = :isSuperAdmin and e.isDeleted = :isDeleted")
	List<User> findByIsSuperAdmin(@Param("isSuperAdmin")Boolean isSuperAdmin, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using locked as a search criteria.
	 * 
	 * @param locked
	 * @return An Object User whose locked is equals to the given locked. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.locked = :locked and e.isDeleted = :isDeleted")
	List<User> findByLocked(@Param("locked")Boolean locked, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using contact as a search criteria.
	 * 
	 * @param contact
	 * @return An Object User whose contact is equals to the given contact. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.contact = :contact and e.isDeleted = :isDeleted")
	List<User> findByContact(@Param("contact")String contact, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using isConnected as a search criteria.
	 * 
	 * @param isConnected
	 * @return An Object User whose isConnected is equals to the given isConnected. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.isConnected = :isConnected and e.isDeleted = :isDeleted")
	List<User> findByIsConnected(@Param("isConnected")Boolean isConnected, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using emailAdresse as a search criteria.
	 * 
	 * @param emailAdresse
	 * @return An Object User whose emailAdresse is equals to the given emailAdresse. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.emailAdresse = :emailAdresse and e.isDeleted = :isDeleted")
	List<User> findByEmailAdresse(@Param("emailAdresse")String emailAdresse, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using firstConnection as a search criteria.
	 * 
	 * @param firstConnection
	 * @return An Object User whose firstConnection is equals to the given firstConnection. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.firstConnection = :firstConnection and e.isDeleted = :isDeleted")
	List<User> findByFirstConnection(@Param("firstConnection")Date firstConnection, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using lastConnection as a search criteria.
	 * 
	 * @param lastConnection
	 * @return An Object User whose lastConnection is equals to the given lastConnection. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.lastConnection = :lastConnection and e.isDeleted = :isDeleted")
	List<User> findByLastConnection(@Param("lastConnection")Date lastConnection, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds User by using idProfil as a search criteria.
	 * 
	 * @param idProfil
	 * @return An Object User whose idProfil is equals to the given idProfil. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.profil.id = :idProfil and e.isDeleted = :isDeleted")
	List<User> findByIdProfil(@Param("idProfil")Integer idProfil, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds User by using idCivilite as a search criteria.
	 * 
	 * @param idCivilite
	 * @return An Object User whose idCivilite is equals to the given idCivilite. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.civilite.id = :idCivilite and e.isDeleted = :isDeleted")
	List<User> findByIdCivilite(@Param("idCivilite")Integer idCivilite, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds User by using idCategory as a search criteria.
	 * 
	 * @param idCategory
	 * @return An Object User whose idCategory is equals to the given idCategory. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.category.id = :idCategory and e.isDeleted = :isDeleted")
	List<User> findByIdCategory(@Param("idCategory")Integer idCategory, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of User by using userDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of User
	 * @throws DataAccessException,ParseException
	 */
	default List<User> getByCriteria(Request<UserDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from User e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<User> query = em.createQuery(req, User.class);
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
	 * Finds count of User by using userDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of User
	 * 
	 */
	default Long count(Request<UserDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from User e where e IS NOT NULL";
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
	default String getWhereExpression(Request<UserDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		UserDto dto = request.getData() != null ? request.getData() : new UserDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (UserDto elt : request.getDatas()) {
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
	default String generateCriteria(UserDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getNom())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("nom", dto.getNom(), "e.nom", "String", dto.getNomParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getPrenom())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("prenom", dto.getPrenom(), "e.prenom", "String", dto.getPrenomParam(), param, index, locale));
			}
			if (dto.getIsValidated()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isValidated", dto.getIsValidated(), "e.isValidated", "Boolean", dto.getIsValidatedParam(), param, index, locale));
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
			if (dto.getIsLocked()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isLocked", dto.getIsLocked(), "e.isLocked", "Boolean", dto.getIsLockedParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLockedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("lockedAt", dto.getLockedAt(), "e.lockedAt", "Date", dto.getLockedAtParam(), param, index, locale));
			}
			if (dto.getLockedBy()!= null && dto.getLockedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("lockedBy", dto.getLockedBy(), "e.lockedBy", "Integer", dto.getLockedByParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLogin())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("login", dto.getLogin(), "e.login", "String", dto.getLoginParam(), param, index, locale));
			}
			if (dto.getIsSuperAdmin()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isSuperAdmin", dto.getIsSuperAdmin(), "e.isSuperAdmin", "Boolean", dto.getIsSuperAdminParam(), param, index, locale));
			}
			if (dto.getLocked()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("locked", dto.getLocked(), "e.locked", "Boolean", dto.getLockedParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getContact())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("contact", dto.getContact(), "e.contact", "String", dto.getContactParam(), param, index, locale));
			}
			if (dto.getIsConnected()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isConnected", dto.getIsConnected(), "e.isConnected", "Boolean", dto.getIsConnectedParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getEmailAdresse())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("emailAdresse", dto.getEmailAdresse(), "e.emailAdresse", "String", dto.getEmailAdresseParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getFirstConnection())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("firstConnection", dto.getFirstConnection(), "e.firstConnection", "Date", dto.getFirstConnectionParam(), param, index, locale));
			}

			if (Utilities.notBlank(dto.getIpadress())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("ipadress", dto.getIpadress(), "e.ipadress", "String", dto.getIpadressParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getMachine())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("machine", dto.getMachine(), "e.machine", "String", dto.getMachineParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStatus())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("status", dto.getStatus(), "e.status", "String", dto.getStatusParam(), param, index, locale));
			}


			if (Utilities.notBlank(dto.getLastConnection())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("lastConnection", dto.getLastConnection(), "e.lastConnection", "Date", dto.getLastConnectionParam(), param, index, locale));
			}
			if (dto.getIdProfil()!= null && dto.getIdProfil() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idProfil", dto.getIdProfil(), "e.profil.id", "Integer", dto.getIdProfilParam(), param, index, locale));
			}
			if (dto.getIdCivilite()!= null && dto.getIdCivilite() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idCivilite", dto.getIdCivilite(), "e.civilite.id", "Integer", dto.getIdCiviliteParam(), param, index, locale));
			}
			if (dto.getIdCategory()!= null && dto.getIdCategory() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idCategory", dto.getIdCategory(), "e.category.id", "Integer", dto.getIdCategoryParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getProfilLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("profilLibelle", dto.getProfilLibelle(), "e.profil.libelle", "String", dto.getProfilLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getProfilCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("profilCode", dto.getProfilCode(), "e.profil.code", "String", dto.getProfilCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCiviliteLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("civiliteLibelle", dto.getCiviliteLibelle(), "e.civilite.libelle", "String", dto.getCiviliteLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCategoryCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("categoryCode", dto.getCategoryCode(), "e.category.code", "String", dto.getCategoryCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCategoryLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("categoryLibelle", dto.getCategoryLibelle(), "e.category.libelle", "String", dto.getCategoryLibelleParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
