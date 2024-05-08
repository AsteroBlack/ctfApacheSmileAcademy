package ci.smile.simswaporange.dao.config.configBatch;

import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.dao.entity.TypeNumero;
import ci.smile.simswaporange.dao.repository.ActionUtilisateurRepository;
import ci.smile.simswaporange.dao.repository.StatusRepository;
import ci.smile.simswaporange.dao.repository.TypeNumeroRepository;
import ci.smile.simswaporange.proxy.customizeclass.functions.ObjectUtilities;
import ci.smile.simswaporange.proxy.service.SimSwapServiceProxyService;
import ci.smile.simswaporange.utils.AES;
import ci.smile.simswaporange.utils.Utilities;
import ci.smile.simswaporange.utils.enums.StatusApiEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class InitializerBatchMapping implements FieldSetMapper<ActionUtilisateur> {
    private final ObjectUtilities objectUtilities;
    private final StatusRepository statusRepository;
    private final TypeNumeroRepository typeNumeroRepository;
    @Override
    public ActionUtilisateur mapFieldSet(FieldSet fieldSet) {
        log.info("fieldSetValue {} ", fieldSet);
        Status status = statusRepository.findByCode(fieldSet.readString("STATUT_BLOCAGE"), Boolean.FALSE);
        log.info("STATUT_BLOCAGE {} ", fieldSet.readString("STATUT_BLOCAGE"));
        log.info("status trouvé {} ", status);
        if(ObjectUtils.isNotEmpty(status)){
            TypeNumero typeNumero = typeNumeroRepository.findByLibelle(StatusApiEnum.CORAIL, Boolean.FALSE);
            ActionUtilisateur actionUtilisateur = new ActionUtilisateur();
            actionUtilisateur.setStatus(status);
            actionUtilisateur.setUpdatedAt(Utilities.getCurrentDate());
            actionUtilisateur.setStatusBscs(fieldSet.readString("STATUT_TELCO"));
            actionUtilisateur.setOfferName(fieldSet.readString("OFFRE"));
            actionUtilisateur.setSerialNumber(fieldSet.readString("SM_SERIALNUM"));
            actionUtilisateur.setContractId(fieldSet.readString("CO_ID"));
            actionUtilisateur.setFromBss(Boolean.TRUE);
            actionUtilisateur.setTmCode(fieldSet.readString("TMCODE"));
            actionUtilisateur.setPortNumber(fieldSet.readString("PORT_NUM"));
            actionUtilisateur.setActivationDate(Utilities.notBlank(fieldSet.readString("DATE_ACTIVATION")) ? Utilities.formatDateAnyFormat(fieldSet.readString("DATE_ACTIVATION"), "yyyy-MM-dd HH:mm:ss") : null);
            if (fieldSet.readString("OFFRE").equalsIgnoreCase("Orange money PDV") || fieldSet.readString("OFFRE").equalsIgnoreCase("ORANGE ZEBRA STK")) {
                actionUtilisateur.setCategory(objectUtilities.categoriePair().getCategorieOmci());
                actionUtilisateur.setEmpreinte(
                        Utilities.empreinte(status != null ? status.getCode() : "AUCUN_STATUS", 1, Utilities.getCurrentDateTime(), fieldSet.readString("NUMERO")));
            }else {
                actionUtilisateur.setEmpreinte(
                        Utilities.empreinte(status != null ? status.getCode() : "AUCUN_STATUS", 1, Utilities.getCurrentDateTime(), fieldSet.readString("NUMERO")));
                actionUtilisateur.setCategory(objectUtilities.categoriePair().getCategorieTelco());
            }
            actionUtilisateur.setNumero(AES.encrypt(fieldSet.readString("NUMERO"), StatusApiEnum.SECRET_KEY));
            actionUtilisateur.setIsMachine(fieldSet.readString("NUMERO_MACHINE").equalsIgnoreCase("NON") ? Boolean.FALSE : Boolean.TRUE);
            actionUtilisateur.setTypeNumero(typeNumero);
            actionUtilisateur.setIsExcelNumber(Boolean.TRUE);
            if (status != null && status.getCode().equalsIgnoreCase(StatusApiEnum.BLOCk)) {
                actionUtilisateur.setLastBlocageDate(Utilities.getCurrentDate());
            }
            if (status != null && status.getCode().equalsIgnoreCase(StatusApiEnum.DEBLOCK)) {
                actionUtilisateur.setLastDebloquage(Utilities.getCurrentDate());
            }
            if (fieldSet.readString("NUMERO_MACHINE").equalsIgnoreCase("OUI")) {
                actionUtilisateur.setLastMachineDate(Utilities.getCurrentDate());
            }
            actionUtilisateur.setStatut(StatusApiEnum.EFFECTUER);
            actionUtilisateur.setDate(Utilities.getCurrentDate());
            actionUtilisateur.setIsDeleted(Boolean.FALSE);
            actionUtilisateur.setStatut(StatusApiEnum.EFFECTUER);
            actionUtilisateur.setDate(Utilities.getCurrentDate());
            actionUtilisateur.setIsDeleted(Boolean.FALSE);
            return  actionUtilisateur;
        }
       return null;
    }
}
