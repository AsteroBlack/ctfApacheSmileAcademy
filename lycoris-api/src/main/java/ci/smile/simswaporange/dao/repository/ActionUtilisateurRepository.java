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
import ci.smile.simswaporange.dao.repository.customize._ActionUtilisateurRepository;

/**
 * Repository : ActionUtilisateur.
 */
@Repository
public interface ActionUtilisateurRepository extends JpaRepository<ActionUtilisateur, Integer>, _ActionUtilisateurRepository {
	/**
	 * Finds ActionUtilisateur by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object ActionUtilisateur whose id is equals to the given id. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.id = :id and e.isDeleted = :isDeleted")
	ActionUtilisateur findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ActionUtilisateur by using date as a search criteria.
	 * 
	 * @param date
	 * @return An Object ActionUtilisateur whose date is equals to the given date. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.date = :date and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByDate(@Param("date")Date date, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object ActionUtilisateur whose createdAt is equals to the given createdAt. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object ActionUtilisateur whose createdBy is equals to the given createdBy. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object ActionUtilisateur whose updatedAt is equals to the given updatedAt. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object ActionUtilisateur whose updatedBy is equals to the given updatedBy. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object ActionUtilisateur whose isDeleted is equals to the given isDeleted. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using numero as a search criteria.
	 * 
	 * @param numero
	 * @return An Object ActionUtilisateur whose numero is equals to the given numero. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.numero = :numero and e.isDeleted = :isDeleted")
	ActionUtilisateur findByNumero(@Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using motif as a search criteria.
	 * 
	 * @param motif
	 * @return An Object ActionUtilisateur whose motif is equals to the given motif. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.motif = :motif and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByMotif(@Param("motif")String motif, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using empreinte as a search criteria.
	 * 
	 * @param empreinte
	 * @return An Object ActionUtilisateur whose empreinte is equals to the given empreinte. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.empreinte = :empreinte and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByEmpreinte(@Param("empreinte")String empreinte, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using isExcelNumber as a search criteria.
	 * 
	 * @param isExcelNumber
	 * @return An Object ActionUtilisateur whose isExcelNumber is equals to the given isExcelNumber. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.isExcelNumber = :isExcelNumber and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByIsExcelNumber(@Param("isExcelNumber")Boolean isExcelNumber, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using statut as a search criteria.
	 * 
	 * @param statut
	 * @return An Object ActionUtilisateur whose statut is equals to the given statut. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.statut = :statut and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByStatut(@Param("statut")String statut, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using isMachine as a search criteria.
	 * 
	 * @param isMachine
	 * @return An Object ActionUtilisateur whose isMachine is equals to the given isMachine. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.isMachine = :isMachine and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByIsMachine(@Param("isMachine")Boolean isMachine, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using lastMachineDate as a search criteria.
	 * 
	 * @param lastMachineDate
	 * @return An Object ActionUtilisateur whose lastMachineDate is equals to the given lastMachineDate. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.lastMachineDate = :lastMachineDate and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByLastMachineDate(@Param("lastMachineDate")Date lastMachineDate, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using lastDebloquage as a search criteria.
	 * 
	 * @param lastDebloquage
	 * @return An Object ActionUtilisateur whose lastDebloquage is equals to the given lastDebloquage. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.lastDebloquage = :lastDebloquage and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByLastDebloquage(@Param("lastDebloquage")Date lastDebloquage, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using statusBscs as a search criteria.
	 * 
	 * @param statusBscs
	 * @return An Object ActionUtilisateur whose statusBscs is equals to the given statusBscs. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.statusBscs = :statusBscs and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByStatusBscs(@Param("statusBscs")String statusBscs, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using activationDate as a search criteria.
	 * 
	 * @param activationDate
	 * @return An Object ActionUtilisateur whose activationDate is equals to the given activationDate. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.activationDate = :activationDate and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByActivationDate(@Param("activationDate")Date activationDate, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using offerName as a search criteria.
	 * 
	 * @param offerName
	 * @return An Object ActionUtilisateur whose offerName is equals to the given offerName. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.offerName = :offerName and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByOfferName(@Param("offerName")String offerName, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using fromBss as a search criteria.
	 * 
	 * @param fromBss
	 * @return An Object ActionUtilisateur whose fromBss is equals to the given fromBss. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.fromBss = :fromBss and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByFromBss(@Param("fromBss")Boolean fromBss, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using lastBlocageDate as a search criteria.
	 * 
	 * @param lastBlocageDate
	 * @return An Object ActionUtilisateur whose lastBlocageDate is equals to the given lastBlocageDate. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.lastBlocageDate = :lastBlocageDate and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByLastBlocageDate(@Param("lastBlocageDate")Date lastBlocageDate, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using contractId as a search criteria.
	 * 
	 * @param contractId
	 * @return An Object ActionUtilisateur whose contractId is equals to the given contractId. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.contractId = :contractId and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByContractId(@Param("contractId")String contractId, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using portNumber as a search criteria.
	 * 
	 * @param portNumber
	 * @return An Object ActionUtilisateur whose portNumber is equals to the given portNumber. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.portNumber = :portNumber and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByPortNumber(@Param("portNumber")String portNumber, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using serialNumber as a search criteria.
	 * 
	 * @param serialNumber
	 * @return An Object ActionUtilisateur whose serialNumber is equals to the given serialNumber. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.serialNumber = :serialNumber and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findBySerialNumber(@Param("serialNumber")String serialNumber, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ActionUtilisateur by using tmCode as a search criteria.
	 * 
	 * @param tmCode
	 * @return An Object ActionUtilisateur whose tmCode is equals to the given tmCode. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.tmCode = :tmCode and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByTmCode(@Param("tmCode")String tmCode, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ActionUtilisateur by using typeNumeroId as a search criteria.
	 * 
	 * @param typeNumeroId
	 * @return An Object ActionUtilisateur whose typeNumeroId is equals to the given typeNumeroId. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.typeNumero.id = :typeNumeroId and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByTypeNumeroId(@Param("typeNumeroId")Integer typeNumeroId, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ActionUtilisateur by using idStatus as a search criteria.
	 * 
	 * @param idStatus
	 * @return An Object ActionUtilisateur whose idStatus is equals to the given idStatus. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.status.id = :idStatus and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByIdStatus(@Param("idStatus")Integer idStatus, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ActionUtilisateur by using idCategory as a search criteria.
	 * 
	 * @param idCategory
	 * @return An Object ActionUtilisateur whose idCategory is equals to the given idCategory. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.category.id = :idCategory and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByIdCategory(@Param("idCategory")Integer idCategory, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ActionUtilisateur by using idUser as a search criteria.
	 * 
	 * @param idUser
	 * @return An Object ActionUtilisateur whose idUser is equals to the given idUser. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.user.id = :idUser and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByIdUser(@Param("idUser")Integer idUser, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ActionUtilisateur by using idProfil as a search criteria.
	 * 
	 * @param idProfil
	 * @return An Object ActionUtilisateur whose idProfil is equals to the given idProfil. If
	 *         no ActionUtilisateur is found, this method returns null.
	 */
	@Query("select e from ActionUtilisateur e where e.profil.id = :idProfil and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByIdProfil(@Param("idProfil")Integer idProfil, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of ActionUtilisateur by using actionUtilisateurDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of ActionUtilisateur
	 * @throws DataAccessException,ParseException
	 */
	default List<ActionUtilisateur> getByCriteria(Request<ActionUtilisateurDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from ActionUtilisateur e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.updatedAt desc";
		TypedQuery<ActionUtilisateur> query = em.createQuery(req, ActionUtilisateur.class);
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
	 * Finds count of ActionUtilisateur by using actionUtilisateurDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of ActionUtilisateur
	 * 
	 */
	default Long count(Request<ActionUtilisateurDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from ActionUtilisateur e where e IS NOT NULL";
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
	default String getWhereExpression(Request<ActionUtilisateurDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		ActionUtilisateurDto dto = request.getData() != null ? request.getData() : new ActionUtilisateurDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (ActionUtilisateurDto elt : request.getDatas()) {
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
	default String generateCriteria(ActionUtilisateurDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (dto.getDate()!= null) {
//				listOfQuery.add(CriteriaUtils.generateCriteria("date", dto.getDate(), "e.date", "Date", dto.getDateParam(), param, index, locale));
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
			if (Utilities.notBlank(dto.getNumero())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("numero", dto.getNumero(), "e.numero", "String", dto.getNumeroParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getMotif())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("motif", dto.getMotif(), "e.motif", "String", dto.getMotifParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getEmpreinte())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("empreinte", dto.getEmpreinte(), "e.empreinte", "String", dto.getEmpreinteParam(), param, index, locale));
			}
			if (dto.getIsExcelNumber()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isExcelNumber", dto.getIsExcelNumber(), "e.isExcelNumber", "Boolean", dto.getIsExcelNumberParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStatut())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statut", dto.getStatut(), "e.statut", "String", dto.getStatutParam(), param, index, locale));
			}
			if (dto.getIsMachine()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isMachine", dto.getIsMachine(), "e.isMachine", "Boolean", dto.getIsMachineParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLastMachineDate())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("lastMachineDate", dto.getLastMachineDate(), "e.lastMachineDate", "Date", dto.getLastMachineDateParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLastDebloquage())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("lastDebloquage", dto.getLastDebloquage(), "e.lastDebloquage", "Date", dto.getLastDebloquageParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStatusBscs())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statusBscs", dto.getStatusBscs(), "e.statusBscs", "String", dto.getStatusBscsParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActivationDate())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("activationDate", dto.getActivationDate(), "e.activationDate", "Date", dto.getActivationDateParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getOfferName())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("offerName", dto.getOfferName(), "e.offerName", "String", dto.getOfferNameParam(), param, index, locale));
			}
			if (dto.getFromBss()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("fromBss", dto.getFromBss(), "e.fromBss", "Boolean", dto.getFromBssParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLastBlocageDate())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("lastBlocageDate", dto.getLastBlocageDate(), "e.lastBlocageDate", "Date", dto.getLastBlocageDateParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getContractId())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("contractId", dto.getContractId(), "e.contractId", "String", dto.getContractIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getPortNumber())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("portNumber", dto.getPortNumber(), "e.portNumber", "String", dto.getPortNumberParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getSerialNumber())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("serialNumber", dto.getSerialNumber(), "e.serialNumber", "String", dto.getSerialNumberParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getTmCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("tmCode", dto.getTmCode(), "e.tmCode", "String", dto.getTmCodeParam(), param, index, locale));
			}
			if (dto.getTypeNumeroId()!= null && dto.getTypeNumeroId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("typeNumeroId", dto.getTypeNumeroId(), "e.typeNumero.id", "Integer", dto.getTypeNumeroIdParam(), param, index, locale));
			}
			if (dto.getIdStatus()!= null && dto.getIdStatus() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idStatus", dto.getIdStatus(), "e.status.id", "Integer", dto.getIdStatusParam(), param, index, locale));
			}
			if (dto.getIdCategory()!= null && dto.getIdCategory() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idCategory", dto.getIdCategory(), "e.category.id", "Integer", dto.getIdCategoryParam(), param, index, locale));
			}
			if (dto.getIdUser()!= null && dto.getIdUser() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idUser", dto.getIdUser(), "e.user.id", "Integer", dto.getIdUserParam(), param, index, locale));
			}
			if (dto.getIdProfil()!= null && dto.getIdProfil() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idProfil", dto.getIdProfil(), "e.profil.id", "Integer", dto.getIdProfilParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getTypeNumeroLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("typeNumeroLibelle", dto.getTypeNumeroLibelle(), "e.typeNumero.libelle", "String", dto.getTypeNumeroLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStatusCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statusCode", dto.getStatusCode(), "e.status.code", "String", dto.getStatusCodeParam(), param, index, locale));
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
			if (Utilities.notBlank(dto.getProfilLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("profilLibelle", dto.getProfilLibelle(), "e.profil.libelle", "String", dto.getProfilLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getProfilCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("profilCode", dto.getProfilCode(), "e.profil.code", "String", dto.getProfilCodeParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
