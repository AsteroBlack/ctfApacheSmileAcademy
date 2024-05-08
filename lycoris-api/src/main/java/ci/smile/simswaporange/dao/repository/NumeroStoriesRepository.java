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
import ci.smile.simswaporange.dao.repository.customize._NumeroStoriesRepository;

/**
 * Repository : NumeroStories.
 */
@Repository
public interface NumeroStoriesRepository extends JpaRepository<NumeroStories, Integer>, _NumeroStoriesRepository {
	/**
	 * Finds NumeroStories by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object NumeroStories whose id is equals to the given id. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.id = :id and e.isDeleted = :isDeleted")
	NumeroStories findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds NumeroStories by using numero as a search criteria.
	 * 
	 * @param numero
	 * @return An Object NumeroStories whose numero is equals to the given numero. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.numero = :numero and e.isDeleted = :isDeleted")
	NumeroStories findByNumero(@Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds NumeroStories by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object NumeroStories whose createdAt is equals to the given createdAt. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<NumeroStories> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds NumeroStories by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object NumeroStories whose updatedAt is equals to the given updatedAt. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<NumeroStories> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds NumeroStories by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object NumeroStories whose updatedBy is equals to the given updatedBy. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<NumeroStories> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds NumeroStories by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object NumeroStories whose isDeleted is equals to the given isDeleted. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.isDeleted = :isDeleted")
	List<NumeroStories> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds NumeroStories by using isMachine as a search criteria.
	 * 
	 * @param isMachine
	 * @return An Object NumeroStories whose isMachine is equals to the given isMachine. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.isMachine = :isMachine and e.isDeleted = :isDeleted")
	List<NumeroStories> findByIsMachine(@Param("isMachine")Boolean isMachine, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds NumeroStories by using statut as a search criteria.
	 * 
	 * @param statut
	 * @return An Object NumeroStories whose statut is equals to the given statut. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.statut = :statut and e.isDeleted = :isDeleted")
	List<NumeroStories> findByStatut(@Param("statut")String statut, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds NumeroStories by using login as a search criteria.
	 * 
	 * @param login
	 * @return An Object NumeroStories whose login is equals to the given login. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.login = :login and e.isDeleted = :isDeleted")
	NumeroStories findByLogin(@Param("login")String login, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds NumeroStories by using machine as a search criteria.
	 * 
	 * @param machine
	 * @return An Object NumeroStories whose machine is equals to the given machine. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.machine = :machine and e.isDeleted = :isDeleted")
	List<NumeroStories> findByMachine(@Param("machine")String machine, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds NumeroStories by using adresseIp as a search criteria.
	 * 
	 * @param adresseIp
	 * @return An Object NumeroStories whose adresseIp is equals to the given adresseIp. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.adresseIp = :adresseIp and e.isDeleted = :isDeleted")
	List<NumeroStories> findByAdresseIp(@Param("adresseIp")String adresseIp, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds NumeroStories by using idTypeNumero as a search criteria.
	 * 
	 * @param idTypeNumero
	 * @return An Object NumeroStories whose idTypeNumero is equals to the given idTypeNumero. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.typeNumero.id = :idTypeNumero and e.isDeleted = :isDeleted")
	List<NumeroStories> findByIdTypeNumero(@Param("idTypeNumero")Integer idTypeNumero, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds NumeroStories by using idProfil as a search criteria.
	 * 
	 * @param idProfil
	 * @return An Object NumeroStories whose idProfil is equals to the given idProfil. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.profil.id = :idProfil and e.isDeleted = :isDeleted")
	List<NumeroStories> findByIdProfil(@Param("idProfil")Integer idProfil, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds NumeroStories by using idCategory as a search criteria.
	 * 
	 * @param idCategory
	 * @return An Object NumeroStories whose idCategory is equals to the given idCategory. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.category.id = :idCategory and e.isDeleted = :isDeleted")
	List<NumeroStories> findByIdCategory(@Param("idCategory")Integer idCategory, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds NumeroStories by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object NumeroStories whose createdBy is equals to the given createdBy. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.user.id = :createdBy and e.isDeleted = :isDeleted")
	List<NumeroStories> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds NumeroStories by using idStatut as a search criteria.
	 * 
	 * @param idStatut
	 * @return An Object NumeroStories whose idStatut is equals to the given idStatut. If
	 *         no NumeroStories is found, this method returns null.
	 */
	@Query("select e from NumeroStories e where e.status.id = :idStatut and e.isDeleted = :isDeleted")
	List<NumeroStories> findByIdStatut(@Param("idStatut")Integer idStatut, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of NumeroStories by using numeroStoriesDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of NumeroStories
	 * @throws DataAccessException,ParseException
	 */
	default List<NumeroStories> getByCriteria(Request<NumeroStoriesDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from NumeroStories e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<NumeroStories> query = em.createQuery(req, NumeroStories.class);
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
	 * Finds count of NumeroStories by using numeroStoriesDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of NumeroStories
	 * 
	 */
	default Long count(Request<NumeroStoriesDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from NumeroStories e where e IS NOT NULL";
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
	default String getWhereExpression(Request<NumeroStoriesDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		NumeroStoriesDto dto = request.getData() != null ? request.getData() : new NumeroStoriesDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (NumeroStoriesDto elt : request.getDatas()) {
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
	default String generateCriteria(NumeroStoriesDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getNumero())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("numero", dto.getNumero(), "e.numero", "String", dto.getNumeroParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCreatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
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
			if (dto.getIsMachine()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isMachine", dto.getIsMachine(), "e.isMachine", "Boolean", dto.getIsMachineParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStatut())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statut", dto.getStatut(), "e.statut", "String", dto.getStatutParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getOfferName())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("offerName", dto.getOfferName(), "e.offerName", "String", dto.getOfferNameParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getReason())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("reason", dto.getReason(), "e.reason", "String", dto.getReasonParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLogin())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("login", dto.getLogin(), "e.login", "String", dto.getLoginParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getMachine())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("machine", dto.getMachine(), "e.machine", "String", dto.getMachineParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getAdresseIp())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("adresseIp", dto.getAdresseIp(), "e.adresseIp", "String", dto.getAdresseIpParam(), param, index, locale));
			}
			if (dto.getIdTypeNumero()!= null && dto.getIdTypeNumero() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idTypeNumero", dto.getIdTypeNumero(), "e.typeNumero.id", "Integer", dto.getIdTypeNumeroParam(), param, index, locale));
			}
			if (dto.getIdProfil()!= null && dto.getIdProfil() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idProfil", dto.getIdProfil(), "e.profil.id", "Integer", dto.getIdProfilParam(), param, index, locale));
			}
			if (dto.getIdCategory()!= null && dto.getIdCategory() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idCategory", dto.getIdCategory(), "e.category.id", "Integer", dto.getIdCategoryParam(), param, index, locale));
			}
			if (dto.getCreatedBy()!= null && dto.getCreatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.user.id", "Integer", dto.getCreatedByParam(), param, index, locale));
			}
			if (dto.getIdStatut()!= null && dto.getIdStatut() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idStatut", dto.getIdStatut(), "e.status.id", "Integer", dto.getIdStatutParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getTypeNumeroLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("typeNumeroLibelle", dto.getTypeNumeroLibelle(), "e.typeNumero.libelle", "String", dto.getTypeNumeroLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getProfilLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("profilLibelle", dto.getProfilLibelle(), "e.profil.libelle", "String", dto.getProfilLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getProfilCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("profilCode", dto.getProfilCode(), "e.profil.code", "String", dto.getProfilCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCategoryCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("categoryCode", dto.getCategoryCode(), "e.category.code", "String", dto.getCategoryCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCategoryLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("categoryLibelle", dto.getCategoryLibelle(), "e.category.libelle", "String", dto.getCategoryLibelleParam(), param, index, locale));
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
