package ci.smile.simswaporange.proxy.customizeclass;

import ci.smile.simswaporange.dao.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesPair {
    private Category categorieOmci;
    private Category categorieTelco;
    public CategoriesPair(Category categorieOmci, Category categorieTelco){
        this.categorieOmci = categorieOmci;
        this.categorieTelco = categorieTelco;
    }
}
