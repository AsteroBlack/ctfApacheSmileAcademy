package ci.smile.simswaporange.dao.config.configBatch;

import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NullItemPrcessor<T> implements ItemProcessor <ActionUtilisateur, ActionUtilisateur>{

    @Override
    public ActionUtilisateur process(ActionUtilisateur item) throws Exception{
        return null;
    }
}
