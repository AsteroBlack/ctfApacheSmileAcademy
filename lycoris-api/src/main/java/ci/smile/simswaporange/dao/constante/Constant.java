package ci.smile.simswaporange.dao.constante;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static List<String> containsUris(){
        List<String> strings = new ArrayList<>();
        strings.add("/simswap/actionUtilisateur/getByCriteria");
        strings.add("/simswap/actionUtilisateur/findByMsisdn");
        strings.add("/simswap/demande/getByCriteria");
        strings.add("/simswap/user/getByCriteria");
        strings.add("/simswap/fonctionnalite/getByCriteria");
        strings.add("/simswap/profil/getByCriteria");
        strings.add("/simswap/parametrageProfil/getByCriteria");
        strings.add("/simswap/parametrage/getByCriteria");
        strings.add("/simswap/historique/getByCriteria");
        strings.add("/simswap/numeroStories/exportNumeroStorieFile");
        strings.add("/simswap/numeroStories/getByCriteria");
        strings.add("/simswap/historique/exportLogsUser");
        strings.add("/simswap/actionUtilisateur/exportActionUtilisateur");
        strings.add("/simswap/actionUtilisateur/dashboard");
        strings.add("/simswap/actionEnMasse/getByCriteria");
        strings.add("/simswap/parametrage/update");
        strings.add("/simswap/parametrageProfil/create");
        strings.add("/simswap/user/unBlockUser");
        strings.add("/simswap/user/blockUser");
        strings.add("/simswap/user/update");//
        strings.add("/simswap/user/delete");//
        strings.add("/simswap/user/create");//
        strings.add("/simswap/profil/update");//
        strings.add("/simswap/parametrageProfil/delete");
        strings.add("/simswap/parametrageProfil/delete");
        strings.add("/simswap/actionUtilisateur/uploadOneFile");
        strings.add("/simswap/demande/actionOnNumber");
        strings.add("/simswap/actionEnMasse/executeOnMasse");
        strings.add("/simswap/actionUtilisateur/blocageNumero");
        strings.add("/simswap/demande/validerRefuser");
        return strings;
    }

    public static List<String> containsProfil(){
        List<String> strings = new ArrayList<>();
        strings.add("ADMIN TECHNIQUE");
        strings.add("ADMIN MÉTIER");
        strings.add("MANAGER FRAUDE-SÉCURITÉ");
        strings.add("RESPONSABLE BACK-OFFICE");
        strings.add("UTILISATEUR BACK-OFFICE");
        return strings;
    }

    public static List<String> containsUrisSimswap(){
        List<String> strings = new ArrayList<>();
        strings.add("/simswap/demande/actionOnNumber");
        strings.add("/simswap/actionEnMasse/executeOnMasse");
        strings.add("/simswap/actionUtilisateur/blocageNumero");
        strings.add("/simswap/demande/validerRefuser");
        return strings;
    }

    public static List<String> loginUri(){
        List<String> strings = new ArrayList<>();
        strings.add("/simswap/user/create");
        strings.add("/simswap/user/delete");
        strings.add("/simswap/user/update");
        strings.add("/simswap/user/blockUser");
        strings.add("/simswap/user/unBlockUser");

        return strings;
    }


    public static List<String> containsUriDuo(){
        List<String> strings = new ArrayList<>();
        strings.add("DUO_CORAIL_BO");
        strings.add("DUO_CORAIL_RESPBO");
        strings.add("DUO_CORAIL_MFRA");
        strings.add("DUO_CORAIL_SEC");
        strings.add("DUO_CORAIL_RESPFRA");
        strings.add("DUO_CORAIL_AT");
        return strings;
    }
    public static final String FILEGENERATIONERROR = "LE SYSTÈME N'A PAS PU GÉNÉRER LE FICHIER";
}
