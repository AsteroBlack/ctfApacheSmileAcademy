package ci.smile.simswaporange.dao.config.configBatch;

import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.dao.repository.ActionUtilisateurRepository;
import ci.smile.simswaporange.dao.repository.StatusRepository;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDtos;
import ci.smile.simswaporange.proxy.service.SimSwapServiceProxyService;
import ci.smile.simswaporange.utils.AES;
import ci.smile.simswaporange.utils.Utilities;
import ci.smile.simswaporange.utils.dto.customize.Result;
import ci.smile.simswaporange.utils.enums.StatusApiEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialiserCorporateNumbersBatch  implements ItemProcessor<ActionUtilisateur,ActionUtilisateur> {
    private final StatusRepository statusRepository;
    private final SimSwapServiceProxyService simSwapServiceProxyService;
    private final ActionUtilisateurRepository actionUtilisateurRepository;
    private final RedisTemplate <String, Result> stringResultRedisTemplate;
    @Override
    public ActionUtilisateur process(ActionUtilisateur item) throws Exception {
        Status status = statusRepository.findOne(StatusApiEnum.BLOQUER, Boolean.FALSE);
        List<ActionUtilisateur> actionUtilisateurList = actionUtilisateurRepository.findByDepartementAndOfferName(item.getNumero(), item.getOfferName(), item.getSerialNumber(), Boolean.FALSE);
        if (Utilities.isNotEmpty(actionUtilisateurList)){
            log.info("le numero {} existe déja", AES.decrypt(item.getNumero(), StatusApiEnum.SECRET_KEY));
            return null;
        }
        if(item.getOfferName().equalsIgnoreCase("Orange money PDV")){
            LockUnLockFreezeDtos numbersBatch = simSwapServiceProxyService.lockNumber(Arrays.asList(AES.decrypt(item.getNumero(), StatusApiEnum.SECRET_KEY)), UUID.randomUUID().toString());
            if(ObjectUtils.isNotEmpty(numbersBatch) && numbersBatch.getStatus().equalsIgnoreCase("200") && ObjectUtils.allNotNull(numbersBatch.getData()) && ObjectUtils.isNotEmpty(numbersBatch.getData())){
                String state = numbersBatch.getData().get(0).getState();
                item.setStatus(status);
                    Result result = new Result();
                    result.setReason(state);
                    result.setNumero(numbersBatch.getData().get(0).getMsisdn());
                    result.setOfferName(item.getOfferName() != null ? item.getOfferName() : "0");
                    result.setSerialNumber(item.getSerialNumber() != null ? item.getSerialNumber() : "0");
                    result.setContractId(ObjectUtils.isNotEmpty(item.getContractId()) ? item.getContractId() : "0");
                    result.setTariffModelCode(ObjectUtils.isNotEmpty(item.getTmCode()) ? item.getTmCode() : "0");
                    result.setPortNumber(ObjectUtils.isNotEmpty(item.getPortNumber())? item.getPortNumber() : "0");
                    result.setLockStatus(status.getCode());
                    result.setStatus(numbersBatch.getStatus());
                    result.setActivationDate(ObjectUtils.isNotEmpty(item.getActivationDate()) ? Utilities.getDateToString(item.getActivationDate()) : "");
                    result.setFrozen(item.getIsMachine());
                    result.setLogin("EST UN TRAITEMENT AUTOMATIQUE");
                    stringResultRedisTemplate.opsForValue().set("numbersBatch_OrangeMoneyPDV_" +UUID.randomUUID(), result);
                return item;
            }
            Result result = new Result();
            result.setReason(numbersBatch.getMessage());
            result.setNumero(AES.decrypt(item.getNumero(), StatusApiEnum.SECRET_KEY));
            result.setLogin("EST UN TRAITEMENT AUTOMATIQUE");
            stringResultRedisTemplate.opsForValue().set("numbersBatch_OrangeMoneyPDV_" +UUID.randomUUID(), result);
            return null;
        }
        return item;
    }
}
