package ci.smile.simswaporange.proxy.customizeclass.functions;

import ci.smile.simswaporange.dao.entity.Category;
import ci.smile.simswaporange.dao.repository.CategoryRepository;
import ci.smile.simswaporange.proxy.customizeclass.CategoriesPair;
import ci.smile.simswaporange.utils.enums.StatusApiEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectUtilities {
    private final CategoryRepository categoryRepository;
    public  CategoriesPair categoriePair(){
        Category categoryOmci = categoryRepository.findByCode(StatusApiEnum.OMCI_CODE, Boolean.FALSE);
        Category categoryTelco= categoryRepository.findByCode(StatusApiEnum.TELCO_CODE, Boolean.FALSE);
        return new CategoriesPair(categoryOmci, categoryTelco);
    }
}
