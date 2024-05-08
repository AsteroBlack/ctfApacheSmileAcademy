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
import ci.smile.simswaporange.dao.repository.customize._DemandeRepository;

/**
 * Repository : Demande.
 */
@Repository
public interface DemandeRepository extends JpaRepository<Demande, Integer>, _DemandeRepository {
	/**
	 * Finds Demande by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Demande whose id is equals to the given id. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.id = :id and e.isDeleted = :isDeleted")
	Demande findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Demande by using numero as a search criteria.
	 * 
	 * @param numero
	 * @return An Object Demande whose numero is equals to the given numero. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.numero = :numero and e.isDeleted = :isDeleted")
	Demande findByNumero(@Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Demande whose createdAt is equals to the given createdAt. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Demande> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Demande whose updatedAt is equals to the given updatedAt. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Demande> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Demande whose isDeleted is equals to the given isDeleted. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.isDeleted = :isDeleted")
	List<Demande> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Demande whose createdBy is equals to the given createdBy. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Demande> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using updatedByTelco as a search criteria.
	 * 
	 * @param updatedByTelco
	 * @return An Object Demande whose updatedByTelco is equals to the given updatedByTelco. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.updatedByTelco = :updatedByTelco and e.isDeleted = :isDeleted")
	List<Demande> findByUpdatedByTelco(@Param("updatedByTelco")Integer updatedByTelco, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using motif as a search criteria.
	 * 
	 * @param motif
	 * @return An Object Demande whose motif is equals to the given motif. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.motif = :motif and e.isDeleted = :isDeleted")
	List<Demande> findByMotif(@Param("motif")String motif, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using offerName as a search criteria.
	 * 
	 * @param offerName
	 * @return An Object Demande whose offerName is equals to the given offerName. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.offerName = :offerName and e.isDeleted = :isDeleted")
	List<Demande> findByOfferName(@Param("offerName")String offerName, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using activationDate as a search criteria.
	 * 
	 * @param activationDate
	 * @return An Object Demande whose activationDate is equals to the given activationDate. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.activationDate = :activationDate and e.isDeleted = :isDeleted")
	List<Demande> findByActivationDate(@Param("activationDate")Date activationDate, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using statusBscs as a search criteria.
	 * 
	 * @param statusBscs
	 * @return An Object Demande whose statusBscs is equals to the given statusBscs. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.statusBscs = :statusBscs and e.isDeleted = :isDeleted")
	List<Demande> findByStatusBscs(@Param("statusBscs")String statusBscs, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using isValidated as a search criteria.
	 * 
	 * @param isValidated
	 * @return An Object Demande whose isValidated is equals to the given isValidated. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.isValidated = :isValidated and e.isDeleted = :isDeleted")
	List<Demande> findByIsValidated(@Param("isValidated")Boolean isValidated, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using updatedByOmci as a search criteria.
	 * 
	 * @param updatedByOmci
	 * @return An Object Demande whose updatedByOmci is equals to the given updatedByOmci. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.updatedByOmci = :updatedByOmci and e.isDeleted = :isDeleted")
	List<Demande> findByUpdatedByOmci(@Param("updatedByOmci")Integer updatedByOmci, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Demande by using idTypeCategory as a search criteria.
	 * 
	 * @param idTypeCategory
	 * @return An Object Demande whose idTypeCategory is equals to the given idTypeCategory. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.category.id = :idTypeCategory and e.isDeleted = :isDeleted")
	List<Demande> findByIdTypeCategory(@Param("idTypeCategory")Integer idTypeCategory, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Demande by using idStatus as a search criteria.
	 * 
	 * @param idStatus
	 * @return An Object Demande whose idStatus is equals to the given idStatus. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.status.id = :idStatus and e.isDeleted = :isDeleted")
	List<Demande> findByIdStatus(@Param("idStatus")Integer idStatus, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Demande by using idUser as a search criteria.
	 * 
	 * @param idUser
	 * @return An Object Demande whose idUser is equals to the given idUser. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.user.id = :idUser and e.isDeleted = :isDeleted")
	List<Demande> findByIdUser(@Param("idUser")Integer idUser, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Demande by using typeNumeroId as a search criteria.
	 * 
	 * @param typeNumeroId
	 * @return An Object Demande whose typeNumeroId is equals to the given typeNumeroId. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.typeNumero.id = :typeNumeroId and e.isDeleted = :isDeleted")
	List<Demande> findByTypeNumeroId(@Param("typeNumeroId")Integer typeNumeroId, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Demande by using idTypeDemande as a search criteria.
	 * 
	 * @param idTypeDemande
	 * @return An Object Demande whose idTypeDemande is equals to the given idTypeDemande. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.typeDemande.id = :idTypeDemande and e.isDeleted = :isDeleted")
	List<Demande> findByIdTypeDemande(@Param("idTypeDemande")Integer idTypeDemande, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Demande by using demandeDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Demande
	 * @throws DataAccessException,ParseException
	 */
	default List<Demande> getByCriteria(Request<DemandeDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Demande e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Demande> query = em.createQuery(req, Demande.class);
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
	 * Finds count of Demande by using demandeDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Demande
	 * 
	 */
	default Long count(Request<DemandeDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Demande e where e IS NOT NULL";
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
	default String getWhereExpression(Request<DemandeDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		DemandeDto dto = request.getData() != null ? request.getData() : new DemandeDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (DemandeDto elt : request.getDatas()) {
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
	default String generateCriteria(DemandeDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
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
			if (dto.getTwoBool()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("twoBool", dto.getIsDeleted(), "e.twoBool", "Boolean", dto.getTwoBoolParam(), param, index, locale));
			}
			if (dto.getIsDeleted()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
			}
			if (dto.getCreatedBy()!= null && dto.getCreatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
			}
			if (dto.getUpdatedByTelco()!= null && dto.getUpdatedByTelco() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedByTelco", dto.getUpdatedByTelco(), "e.updatedByTelco", "Integer", dto.getUpdatedByTelcoParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getMotif())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("motif", dto.getMotif(), "e.motif", "String", dto.getMotifParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getOfferName())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("offerName", dto.getOfferName(), "e.offerName", "String", dto.getOfferNameParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActivationDate())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("activationDate", dto.getActivationDate(), "e.activationDate", "Date", dto.getActivationDateParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStatusBscs())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statusBscs", dto.getStatusBscs(), "e.statusBscs", "String", dto.getStatusBscsParam(), param, index, locale));
			}
			if (dto.getIsValidated()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isValidated", dto.getIsValidated(), "e.isValidated", "Boolean", dto.getIsValidatedParam(), param, index, locale));
			}if (dto.getIsTransfert()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isTransfert", dto.getIsTransfert(), "e.isTransfert", "Boolean", dto.getIsTransfertParam(), param, index, locale));
			}
			if (dto.getUpdatedByOmci()!= null && dto.getUpdatedByOmci() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedByOmci", dto.getUpdatedByOmci(), "e.updatedByOmci", "Integer", dto.getUpdatedByOmciParam(), param, index, locale));
			}
			if (dto.getIdTypeCategory()!= null && dto.getIdTypeCategory() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idTypeCategory", dto.getIdTypeCategory(), "e.category.id", "Integer", dto.getIdTypeCategoryParam(), param, index, locale));
			}
			if (dto.getIdStatus()!= null && dto.getIdStatus() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idStatus", dto.getIdStatus(), "e.status.id", "Integer", dto.getIdStatusParam(), param, index, locale));
			}
			if (dto.getIdUser()!= null && dto.getIdUser() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idUser", dto.getIdUser(), "e.user.id", "Integer", dto.getIdUserParam(), param, index, locale));
			}
			if (dto.getTypeNumeroId()!= null && dto.getTypeNumeroId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("typeNumeroId", dto.getTypeNumeroId(), "e.typeNumero.id", "Integer", dto.getTypeNumeroIdParam(), param, index, locale));
			}
			if (dto.getIdTypeDemande()!= null && dto.getIdTypeDemande() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idTypeDemande", dto.getIdTypeDemande(), "e.typeDemande.id", "Integer", dto.getIdTypeDemandeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCategoryCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("categoryCode", dto.getCategoryCode(), "e.category.code", "String", dto.getCategoryCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCategoryLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("categoryLibelle", dto.getCategoryLibelle(), "e.category.libelle", "String", dto.getCategoryLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStatusCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statusCode", dto.getStatusCode(), "e.status.code", "String", dto.getStatusCodeParam(), param, index, locale));
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
			if (Utilities.notBlank(dto.getTypeNumeroLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("typeNumeroLibelle", dto.getTypeNumeroLibelle(), "e.typeNumero.libelle", "String", dto.getTypeNumeroLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getTypeDemandeCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("typeDemandeCode", dto.getTypeDemandeCode(), "e.typeDemande.code", "String", dto.getTypeDemandeCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getTypeDemandeLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("typeDemandeLibelle", dto.getTypeDemandeLibelle(), "e.typeDemande.libelle", "String", dto.getTypeDemandeLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActionEnMasseCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("actionEnMasseCode", dto.getActionEnMasseCode(), "e.actionEnMasse.code", "String", dto.getActionEnMasseCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActionEnMasseLibelle())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("actionEnMasseLibelle", dto.getActionEnMasseLibelle(), "e.actionEnMasse.libelle", "String", dto.getActionEnMasseLibelleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActionEnMasseIdentifiant())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("actionEnMasseIdentifiant", dto.getActionEnMasseIdentifiant(), "e.actionEnMasse.identifiant", "String", dto.getActionEnMasseIdentifiantParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getActionEnMasseLienFichier())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("actionEnMasseLienFichier", dto.getActionEnMasseLienFichier(), "e.actionEnMasse.lienFichier", "String", dto.getActionEnMasseLienFichierParam(), param, index, locale));
			}


			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
