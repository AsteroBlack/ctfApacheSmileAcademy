package ci.smile.simswaporange.dao.config.configBatch;

import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class InitialiserCorporateResultsNumbersBatch implements ItemProcessor<ActionUtilisateur, ActionUtilisateur>{
    @Override
    public ActionUtilisateur process(ActionUtilisateur item) throws Exception{
        return null;
    }
}
