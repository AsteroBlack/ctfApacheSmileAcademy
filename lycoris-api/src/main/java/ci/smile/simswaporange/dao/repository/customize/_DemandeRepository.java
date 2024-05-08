package ci.smile.simswaporange.dao.repository.customize;

import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
import ci.smile.simswaporange.dao.entity.Demande;
import ci.smile.simswaporange.utils.dto.DemandeDto;
import ci.smile.simswaporange.utils.dto.HistoriqueDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Repository
public interface _DemandeRepository {
    default List<String> _generateCriteria(DemandeDto dto, HashMap<String, Object> param, Integer index, Locale locale) throws Exception {
        List<String> listOfQuery = new ArrayList<String>();

        // PUT YOUR RIGHT CUSTOM CRITERIA HERE

        return listOfQuery;
    }
    @Query("select e from Demande e where e.typeNumero.id = :typeNumeroId and e.numero = :numero and e.typeDemande.id = :typeDemandeCode and e.category.id = :idTypeCategory and e.isTransfert = :isTransfert and e.isDeleted = :isDeleted")
    Demande findDemandeByStatusAndType(@Param("typeNumeroId")Integer typeNumeroId,@Param("numero")String numero ,@Param("typeDemandeCode")Integer typeDemandeCode,@Param("idTypeCategory")Integer idTypeCategory, @Param("isTransfert")Boolean isTransfert, @Param("isDeleted")Boolean isDeleted);

    @Query("select e from Demande e where e.numero = :numero and e.status.id = :idStatus and e.typeDemande.code = :typeDemandeCode and e.isDeleted = :isDeleted")
    List<Demande> findDemandeByStatusAndTypes(@Param("numero")String numero, @Param("idStatus")Integer idStatus, @Param("isDeleted")Boolean isDeleted,@Param("typeDemandeCode")String typeDemandeCode);

    @Query("select e from Demande e where e.typeNumero.id = :typeNumeroId and e.numero = :numero and  e.typeDemande.code = :typeDemandeCode and e.isDeleted = :isDeleted")
    Demande findDemandeByStatut(@Param("typeNumeroId")Integer typeNumeroId,@Param("numero")String numero,@Param("typeDemandeCode")String typeDemandeCode, @Param("isDeleted")Boolean isDeleted);

    @Query("select e from Demande e where e.actionEnMasse.id = :actionEnMasseId and e.isDeleted = :isDeleted")
    Demande findDemandeByActionEnMasse(@Param("actionEnMasseId")Integer actionEnMasseId, @Param("isDeleted")Boolean isDeleted);
}
