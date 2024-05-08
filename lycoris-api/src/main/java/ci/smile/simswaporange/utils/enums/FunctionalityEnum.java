/*
 * Created on 2018-04-14 ( Time 21:52:32 )
 * Generator tool : Telosys Tools Generator ( version 3.0.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.enums;

/**
 * 
 * @author Geo
 *
 */
 public enum FunctionalityEnum {
 	DEFAULT("DEFAULT"),

	// ACTION_EN_MASSE
	VIEW_ACTION_EN_MASSE("VIEW_ACTION_EN_MASSE"),	
	CREATE_ACTION_EN_MASSE("CREATE_ACTION_EN_MASSE"),
	UPDATE_ACTION_EN_MASSE("UPDATE_ACTION_EN_MASSE"),
	DELETE_ACTION_EN_MASSE("DELETE_ACTION_EN_MASSE"),
	// ACTION_PARAMETRABLE
	VIEW_ACTION_PARAMETRABLE("VIEW_ACTION_PARAMETRABLE"),	
	CREATE_ACTION_PARAMETRABLE("CREATE_ACTION_PARAMETRABLE"),
	UPDATE_ACTION_PARAMETRABLE("UPDATE_ACTION_PARAMETRABLE"),
	DELETE_ACTION_PARAMETRABLE("DELETE_ACTION_PARAMETRABLE"),
	// ACTION_SIMSWAP
	VIEW_ACTION_SIMSWAP("VIEW_ACTION_SIMSWAP"),	
	CREATE_ACTION_SIMSWAP("CREATE_ACTION_SIMSWAP"),
	UPDATE_ACTION_SIMSWAP("UPDATE_ACTION_SIMSWAP"),
	DELETE_ACTION_SIMSWAP("DELETE_ACTION_SIMSWAP"),
	// ACTION_UTILISATEUR
	VIEW_ACTION_UTILISATEUR("VIEW_ACTION_UTILISATEUR"),	
	CREATE_ACTION_UTILISATEUR("CREATE_ACTION_UTILISATEUR"),
	UPDATE_ACTION_UTILISATEUR("UPDATE_ACTION_UTILISATEUR"),
	DELETE_ACTION_UTILISATEUR("DELETE_ACTION_UTILISATEUR"),
	// ATION_TO_NOTIFIABLE
	VIEW_ATION_TO_NOTIFIABLE("VIEW_ATION_TO_NOTIFIABLE"),	
	CREATE_ATION_TO_NOTIFIABLE("CREATE_ATION_TO_NOTIFIABLE"),
	UPDATE_ATION_TO_NOTIFIABLE("UPDATE_ATION_TO_NOTIFIABLE"),
	DELETE_ATION_TO_NOTIFIABLE("DELETE_ATION_TO_NOTIFIABLE"),
	// CATEGORY
	VIEW_CATEGORY("VIEW_CATEGORY"),	
	CREATE_CATEGORY("CREATE_CATEGORY"),
	UPDATE_CATEGORY("UPDATE_CATEGORY"),
	DELETE_CATEGORY("DELETE_CATEGORY"),
	// CIVILITE
	VIEW_CIVILITE("VIEW_CIVILITE"),	
	CREATE_CIVILITE("CREATE_CIVILITE"),
	UPDATE_CIVILITE("UPDATE_CIVILITE"),
	DELETE_CIVILITE("DELETE_CIVILITE"),
	// DEMANDE
	VIEW_DEMANDE("VIEW_DEMANDE"),	
	CREATE_DEMANDE("CREATE_DEMANDE"),
	UPDATE_DEMANDE("UPDATE_DEMANDE"),
	DELETE_DEMANDE("DELETE_DEMANDE"),
	// FONCTIONNALITE
	VIEW_FONCTIONNALITE("VIEW_FONCTIONNALITE"),	
	CREATE_FONCTIONNALITE("CREATE_FONCTIONNALITE"),
	UPDATE_FONCTIONNALITE("UPDATE_FONCTIONNALITE"),
	DELETE_FONCTIONNALITE("DELETE_FONCTIONNALITE"),
	// HISTORIQUE
	VIEW_HISTORIQUE("VIEW_HISTORIQUE"),	
	CREATE_HISTORIQUE("CREATE_HISTORIQUE"),
	UPDATE_HISTORIQUE("UPDATE_HISTORIQUE"),
	DELETE_HISTORIQUE("DELETE_HISTORIQUE"),
	// LISTE_DIFFUSION
	VIEW_LISTE_DIFFUSION("VIEW_LISTE_DIFFUSION"),	
	CREATE_LISTE_DIFFUSION("CREATE_LISTE_DIFFUSION"),
	UPDATE_LISTE_DIFFUSION("UPDATE_LISTE_DIFFUSION"),
	DELETE_LISTE_DIFFUSION("DELETE_LISTE_DIFFUSION"),
	// NUMERO_STORIES
	VIEW_NUMERO_STORIES("VIEW_NUMERO_STORIES"),	
	CREATE_NUMERO_STORIES("CREATE_NUMERO_STORIES"),
	UPDATE_NUMERO_STORIES("UPDATE_NUMERO_STORIES"),
	DELETE_NUMERO_STORIES("DELETE_NUMERO_STORIES"),
	// PARAMETRAGE
	VIEW_PARAMETRAGE("VIEW_PARAMETRAGE"),	
	CREATE_PARAMETRAGE("CREATE_PARAMETRAGE"),
	UPDATE_PARAMETRAGE("UPDATE_PARAMETRAGE"),
	DELETE_PARAMETRAGE("DELETE_PARAMETRAGE"),
	// PARAMETRAGE_PROFIL
	VIEW_PARAMETRAGE_PROFIL("VIEW_PARAMETRAGE_PROFIL"),	
	CREATE_PARAMETRAGE_PROFIL("CREATE_PARAMETRAGE_PROFIL"),
	UPDATE_PARAMETRAGE_PROFIL("UPDATE_PARAMETRAGE_PROFIL"),
	DELETE_PARAMETRAGE_PROFIL("DELETE_PARAMETRAGE_PROFIL"),
	// PROFIL
	VIEW_PROFIL("VIEW_PROFIL"),	
	CREATE_PROFIL("CREATE_PROFIL"),
	UPDATE_PROFIL("UPDATE_PROFIL"),
	DELETE_PROFIL("DELETE_PROFIL"),
	// PROFIL_FONCTIONNALITE
	VIEW_PROFIL_FONCTIONNALITE("VIEW_PROFIL_FONCTIONNALITE"),	
	CREATE_PROFIL_FONCTIONNALITE("CREATE_PROFIL_FONCTIONNALITE"),
	UPDATE_PROFIL_FONCTIONNALITE("UPDATE_PROFIL_FONCTIONNALITE"),
	DELETE_PROFIL_FONCTIONNALITE("DELETE_PROFIL_FONCTIONNALITE"),
	// STATUS
	VIEW_STATUS("VIEW_STATUS"),	
	CREATE_STATUS("CREATE_STATUS"),
	UPDATE_STATUS("UPDATE_STATUS"),
	DELETE_STATUS("DELETE_STATUS"),
	// TACHE
	VIEW_TACHE("VIEW_TACHE"),	
	CREATE_TACHE("CREATE_TACHE"),
	UPDATE_TACHE("UPDATE_TACHE"),
	DELETE_TACHE("DELETE_TACHE"),
	// TYPE_DEMANDE
	VIEW_TYPE_DEMANDE("VIEW_TYPE_DEMANDE"),	
	CREATE_TYPE_DEMANDE("CREATE_TYPE_DEMANDE"),
	UPDATE_TYPE_DEMANDE("UPDATE_TYPE_DEMANDE"),
	DELETE_TYPE_DEMANDE("DELETE_TYPE_DEMANDE"),
	// TYPE_NUMERO
	VIEW_TYPE_NUMERO("VIEW_TYPE_NUMERO"),	
	CREATE_TYPE_NUMERO("CREATE_TYPE_NUMERO"),
	UPDATE_TYPE_NUMERO("UPDATE_TYPE_NUMERO"),
	DELETE_TYPE_NUMERO("DELETE_TYPE_NUMERO"),
	// TYPE_PARAMETRAGE
	VIEW_TYPE_PARAMETRAGE("VIEW_TYPE_PARAMETRAGE"),	
	CREATE_TYPE_PARAMETRAGE("CREATE_TYPE_PARAMETRAGE"),
	UPDATE_TYPE_PARAMETRAGE("UPDATE_TYPE_PARAMETRAGE"),
	DELETE_TYPE_PARAMETRAGE("DELETE_TYPE_PARAMETRAGE"),
	
	// TYPE_PARAMETRAGE
	CREATE_PRODUCT("VIEW_CREATE_PRODUCT"),	
	UPDATE_PRODUCT("CREATE_UPDATE_PRODUCT"),
	DELETE_PRODUCT("UPDATE_DELETE_PRODUCT"),
	VIEW_PRODUCT("DELETE_VIEW_PRODUCT"),
	
	CREATE_TRANSACTION("VIEW_CREATE_TRANSACTION"),	
	UPDATE_TRANSACTION("CREATE_UPDATE_TRANSACTION"),
	DELETE_TRANSACTION("UPDATE_DELETE_TRANSACTION"),
	VIEW_TRANSACTION("DELETE_VIEW_TRANSACTION"),
	// USER
	VIEW_USER("VIEW_USER"),	
	CREATE_USER("CREATE_USER"),
	UPDATE_USER("UPDATE_USER"),
	DELETE_USER("DELETE_USER");

	private final String value;
 	public String getValue() {
 		return value;
 	}
 	private FunctionalityEnum(String value) {
 		this.value = value;
 	}
}