package ci.smile.simswaporange.dao.repository.customize;

import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
import ci.smile.simswaporange.dao.entity.Demande;
import ci.smile.simswaporange.dao.entity.User;
import ci.smile.simswaporange.utils.dto.ActionUtilisateurDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Repository customize : ActionUtilisateur.
 */
@Repository
public interface _ActionUtilisateurRepository {
	@Query("select e from ActionUtilisateur e where e.typeNumero.id = :typeNumeroId and e.numero = :numero and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByNumeroAll(@Param("typeNumeroId")Integer typeNumeroId,@Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);
	
	default List<String> _generateCriteria(ActionUtilisateurDto dto, HashMap<String, java.lang.Object> param, Integer index, Locale locale) throws Exception {
		List<String> listOfQuery = new ArrayList<String>();

		// PUT YOUR RIGHT CUSTOM CRITERIA HERE

		return listOfQuery;
	}

	@Query("select e from ActionUtilisateur e where e.numero = :numero and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByNumeroList(@Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);

	@Query("select e from ActionUtilisateur e where e.numero = :numero and e.status.id = :idStatus and e.isDeleted = :isDeleted")
	ActionUtilisateur findByNumeroStatut(@Param("numero")String numero, @Param("idStatus")Integer idStatus, @Param("isDeleted")Boolean isDeleted);
	@Query("select e from ActionUtilisateur e where e.isExcelNumber = :isExcelNumber and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findExcelFile(@Param("isExcelNumber")Boolean isExcelNumber, @Param("isDeleted")Boolean isDeleted);

	@Query("select e from Demande e where e.typeNumero.id = :typeNumeroId and e.numero = :numero and e.typeDemande.id = :typeDemandeCode and e.category.id = :idTypeCategory and e.isTransfert = :isTransfert and e.isDeleted = :isDeleted")
	Demande findByTransfertDemande(@Param("typeNumeroId")Integer typeNumeroId,@Param("numero")String numero ,@Param("typeDemandeCode")Integer typeDemandeCode,@Param("idTypeCategory")Integer idTypeCategory,@Param("isTransfert")Boolean isTransfert,@Param("isDeleted")Boolean isDeleted);

	@Query("select e from Demande e where e.typeNumero.id = :typeNumeroId and e.numero = :numero and e.typeDemande.id = :typeDemandeCode and e.category.id = :idTypeCategory and e.isDeleted = :isDeleted")
	Demande findByUnlockDemande(@Param("typeNumeroId")Integer typeNumeroId,@Param("numero")String numero ,@Param("typeDemandeCode")Integer typeDemandeCode,@Param("idTypeCategory")Integer idTypeCategory,@Param("isDeleted")Boolean isDeleted);

	@Query(value="SELECT COUNT(id_status) FROM db_simswap.action_utilisateur where id_status = idStatus and e.isDeleted = :isDeleted",nativeQuery=true)
	  List<User> getUsers(@Param("isDeleted") Boolean isDeleted);

	@Query("select e from ActionUtilisateur e where e.numero = :numero and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByNumeroListe(@Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);

	@Query("select e from ActionUtilisateur e where e.typeNumero.id = :typeNumeroId and e.category.id = :idCategory and  e.numero = :numero and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByOneNumero(@Param("typeNumeroId")Integer typeNumeroId, @Param("idCategory")Integer idCategory, @Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);

	@Query("select e from ActionUtilisateur e where e.typeNumero.id = :typeNumeroId   and e.numero = :numero and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByNumeros(@Param("typeNumeroId")Integer typeNumeroId, @Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);
	
	@Query("select e from ActionUtilisateur e where e.fromBss = :fromBss and e.category.id = :idCategory and  e.numero = :numero and e.isDeleted = :isDeleted")
	ActionUtilisateur findByNumeroUnique(@Param("fromBss")Boolean fromBss,@Param("idCategory")Integer idCategory, @Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);


	@Query("select e from ActionUtilisateur e where e.typeNumero.id = :typeNumeroId and e.category.id = :idCategory and  e.numero = :numero and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByNumber(@Param("typeNumeroId")Integer typeNumeroId, @Param("idCategory")Integer idCategory, @Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);

	@Query("select e from ActionUtilisateur e where e.typeNumero.id = :typeNumeroId and e.category.id = :idCategory and  e.numero = :numero and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByDepartement(@Param("typeNumeroId")Integer typeNumeroId, @Param("idCategory")Integer idCategory, @Param("numero")String numero, @Param("isDeleted")Boolean isDeleted);

	@Query("select e from ActionUtilisateur e where e.numero = :numero and  e.offerName = :offerName and  e.serialNumber = :serialNumber and e.isDeleted = :isDeleted")
	List<ActionUtilisateur> findByDepartementAndOfferName(@Param("numero")String numero, @Param("offerName")String offerName, @Param("serialNumber")String serialNumber, @Param("isDeleted")Boolean isDeleted);

}
