package ci.smile.simswaporange.dao.config.configBatch;

import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.dao.repository.ActionUtilisateurRepository;
import ci.smile.simswaporange.dao.repository.StatusRepository;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDtos;
import ci.smile.simswaporange.utils.AES;
import ci.smile.simswaporange.utils.Utilities;
import ci.smile.simswaporange.utils.enums.StatusApiEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisRefrehNumberItemProcessor implements ItemProcessor<LockUnLockFreezeDto, LockUnLockFreezeDto> {
    private final StatusRepository statusRepository;
    private final ActionUtilisateurRepository actionUtilisateurRepository;
    private final RedisTemplate<String, LockUnLockFreezeDto> redisTemplate;
    @Override
    public LockUnLockFreezeDto process(LockUnLockFreezeDto lockUnLockFreezeDtos) throws Exception {
        log.info("on est dans le process de la second etape");
        log.info("l'objet stocké dans redis {}", lockUnLockFreezeDtos);
        if (lockUnLockFreezeDtos != null && lockUnLockFreezeDtos.getStatus().equalsIgnoreCase("200")){
            lockUnLockFreezeDtos.getData().stream().forEach(arg -> {
                List<ActionUtilisateur> actionUtilisateurList = actionUtilisateurRepository.findByNumeroList(AES.encrypt(arg.getMsisdn(), StatusApiEnum.SECRET_KEY), Boolean.FALSE);
                if (Utilities.isNotEmpty(actionUtilisateurList)){
                    actionUtilisateurList.stream().forEach(actionUtilisateur -> {
                        Status status = statusRepository.findByCode(arg.getLockStatus(), Boolean.FALSE);
                        actionUtilisateur.setStatus(status != null && !status.getId().equals(StatusApiEnum.MISE_EN_MACHINE) ? status : null);
                        log.info(" on est entré dans la condition");
                        actionUtilisateur.setSerialNumber(arg.getSerialNumber());
                        actionUtilisateur.setContractId(String.valueOf(arg.getContractId()));
                        actionUtilisateur.setFromBss(Boolean.TRUE);
                        actionUtilisateur.setTmCode(String.valueOf(arg.getTariffModelCode()));
                        actionUtilisateur.setPortNumber(String.valueOf(arg.getPortNumber()));
                        actionUtilisateur.setActivationDate(arg.getActivationDate() != null ? Utilities.formatDateAnyFormat(arg.getActivationDate(), "yyyy-MM-dd HH:mm:ss") : null);
                        actionUtilisateur.setIsMachine(arg.isFrozen());
                        actionUtilisateur.setUpdatedAt(Utilities.getCurrentDate());
                        actionUtilisateur.setLastBlocageDate(Utilities.getCurrentDate());
                        actionUtilisateur.setStatut(StatusApiEnum.EFFECTUER);
                        actionUtilisateur.setStatusBscs(arg.getStatus());
                        actionUtilisateur.setOfferName(arg.getOfferName());
                        actionUtilisateur.setEmpreinte(Utilities.empreinte(status != null ? status.getCode() : "AUCUN STATUS TROUVÉ", null, Utilities.getCurrentDateTime(), AES.decrypt(arg.getMsisdn() != null ? arg.getMsisdn() : actionUtilisateur.getNumero(), StatusApiEnum.SECRET_KEY)));
                        actionUtilisateurRepository.save(actionUtilisateur);
                    });
                }
            });

        }
        Set<String> key = redisTemplate.keys("numbersRefreshNumberBatch_*");
        if(key.size() > 0){
            redisTemplate.delete(key);
        }
        return null;
    }
}
