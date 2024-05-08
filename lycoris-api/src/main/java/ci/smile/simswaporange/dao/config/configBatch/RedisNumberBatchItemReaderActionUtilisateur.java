package ci.smile.simswaporange.dao.config.configBatch;

import ci.smile.simswaporange.business.ActionEnMasseBusiness;
import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
import ci.smile.simswaporange.utils.Utilities;
import ci.smile.simswaporange.utils.dto.customize.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisNumberBatchItemReaderActionUtilisateur implements ItemReader<ActionUtilisateur>{
    private int index = 0;
    private Set <String> keys;
    private final ActionEnMasseBusiness actionEnMasseBusiness;
    private final RedisTemplate <String, Result> redisTemplate;

    @Override
    public ActionUtilisateur read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException{
        List <Result> resultList = new ArrayList <>();
        if (keys == null) {
            keys = redisTemplate.keys("numbersBatch_OrangeMoneyPDV_*");
            if(!keys.isEmpty()){
                List <Result> resultLists =  extracted(resultList);
                if (Utilities.isNotEmpty(resultLists)){
                    log.info("va ecrire dans le fichier resultat");
                    actionEnMasseBusiness.lockUnlockNumberAfterTwentyFourHours(resultLists, false, "DETECTION");
                }
            }
        }
        return null;
    }

    private List <Result>  extracted(List <Result> resultList){
        log.info("taille de numbersBatch_OrangeMoneyPDV_ dans redis {}", keys.size());
        do {
            String key = (String) keys.toArray()[index];
            Result value = redisTemplate.opsForValue().get(key);
            log.info("code numbersBatch_OrangeMoneyPDV_ {}", value);
            resultList.add(value);
            index ++;
        }while(index < keys.size());
        keys.add("0");
        Set<String> key = redisTemplate.keys("numbersBatch_OrangeMoneyPDV_*");
        if(key.size() > 0){
            redisTemplate.delete(key);
        }
        return resultList;
    }
}
