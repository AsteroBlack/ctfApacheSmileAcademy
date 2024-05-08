package ci.smile.simswaporange.dao.config.configBatch;

import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
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
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CollectUnlockNumbersProcessor implements ItemProcessor<ActionUtilisateur, ActionUtilisateur> {
    private final ParamsUtils paramsUtils;
    private static final int BATCH_SIZE = 500;
    private List<String> numbers = new ArrayList<>();
    private final RedisTemplate<String, Long> stringLongRedisTemplate;
    private final SimSwapServiceProxyService simSwapServiceProxyService;
    private final RedisTemplate<String, LockUnLockFreezeDtos> redisSimSwapServiceProxyServiceTemplate;

    @Override
    public ActionUtilisateur process(ActionUtilisateur item) throws Exception {
        log.info("numero trouvé {} ", item);
        Object object = stringLongRedisTemplate.opsForValue().get("totalItemsUnlockNumbers");
        long l = 0;
        if (object != null){
             l = Long.valueOf(object.toString());
        }
     /*   Date currentDate = new Date();
        Long dureeBlocage = paramsUtils.getDureeBlocage();
        Date dateDeDeblocage = item.getUpdatedAt();
        LocalDateTime localDateTime1 = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime localDateTime2 = dateDeDeblocage.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(localDateTime2, localDateTime1);
        long heure = duration.toHours();*/
        if (l > 0 && numbers.size() < BATCH_SIZE) {
            numbers.add(AES.decrypt(item.getNumero(), StatusApiEnum.SECRET_KEY));
        }
        if (numbers.size() == BATCH_SIZE){
            log.info("prêt a enregistrer dans redis {} ", BATCH_SIZE);
            LockUnLockFreezeDtos numbersBatch = simSwapServiceProxyService.lockNumber(numbers, UUID.randomUUID().toString());
            log.info("le retour de lockUnLockFreezeDto {} ", numbersBatch);
            redisSimSwapServiceProxyServiceTemplate.opsForValue().set("numbersBatchLockNumbers_" + UUID.randomUUID(), numbersBatch);
            numbers.clear();
            l = l - 500;
            log.info("Dans le lock il reste  {} a traiter", l);

            stringLongRedisTemplate.opsForValue().set("totalItemsUnlockNumbers",l);
        }
        if (l > 0 && numbers.size() == l){
            log.info("prêt a enregistrer dans redis {} ", l);
            LockUnLockFreezeDtos numbersBatch = simSwapServiceProxyService.lockNumber(numbers, UUID.randomUUID().toString());
            log.info("le retour de lockUnLockFreezeDto {} ", numbersBatch);
            redisSimSwapServiceProxyServiceTemplate.opsForValue().set("numbersBatchLockNumbers_" + UUID.randomUUID(), numbersBatch);
            numbers.clear();
        }
        return null;
    }
}
