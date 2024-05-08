package ci.smile.simswaporange.dao.config.configBatch;


import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDtos;
import ci.smile.simswaporange.proxy.service.SimSwapServiceProxyService;
import ci.smile.simswaporange.utils.AES;
import ci.smile.simswaporange.utils.ParamsUtils;
import ci.smile.simswaporange.utils.enums.StatusApiEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CollectRefreshNumbersProcessor implements ItemProcessor<ActionUtilisateur, ActionUtilisateur> {
    private static final int BATCH_SIZE = 500;
    private List<String> numbers = new ArrayList<>();
    private final RedisTemplate<String, Long> stringLongRedisTemplate;
    private final SimSwapServiceProxyService simSwapServiceProxyService;
    private final RedisTemplate<String, LockUnLockFreezeDto> redisTemplateRefreshNumber;

    @Override
    public ActionUtilisateur process(ActionUtilisateur item) throws Exception {
        log.info("numero trouvé {} ", item);
        Object object = stringLongRedisTemplate.opsForValue().get("totalItemsRefreshNumbers");
        long l = 0;
        if (object != null){
            l = Long.valueOf(object.toString());
        }
        if (numbers.size() < BATCH_SIZE) {
            numbers.add(AES.decrypt(item.getNumero(), StatusApiEnum.SECRET_KEY));
        }
        if (numbers.size() == BATCH_SIZE){
            log.info("prêt a enregistrer dans redis {} ", BATCH_SIZE);
            LockUnLockFreezeDto numbersBatch = simSwapServiceProxyService.getNumbers(UUID.randomUUID().toString(),numbers);
            log.info("le retour de lockUnLockFreezeDto {} ", numbersBatch);
            l = l - 500;
            log.info("Dans le refresh il reste  {} a traiter", l);
            stringLongRedisTemplate.opsForValue().set("totalItemsRefreshNumbers",l);
            redisTemplateRefreshNumber.opsForValue().set("numbersRefreshNumberBatch_" + UUID.randomUUID(), numbersBatch);
            numbers.clear();
        }
        if (l > 0 && numbers.size() == l){
            log.info("prêt a enregistrer dans redis {} ", l);
            LockUnLockFreezeDto numbersBatch = simSwapServiceProxyService.getNumbers(UUID.randomUUID().toString(),numbers);
            log.info("le retour de lockUnLockFreezeDto {} ", numbersBatch);
            redisTemplateRefreshNumber.opsForValue().set("numbersRefreshNumberBatch_" + UUID.randomUUID(), numbersBatch);
            numbers.clear();
        }
        return null;    }
}
