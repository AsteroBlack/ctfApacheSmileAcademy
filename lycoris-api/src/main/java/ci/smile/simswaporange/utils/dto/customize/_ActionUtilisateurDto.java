
/*
 * Java dto for entity table action_utilisateur 
 * Created on 2022-06-13 ( Time 14:55:31 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.dto.customize;

import java.util.Date;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.dto.ActionUtilisateurDto;
import lombok.Data;
import lombok.ToString;

/**
 * DTO customize for table "action_utilisateur"
 * 
 * @author Geo
 *
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class _ActionUtilisateurDto {
	private List<String> datasNumero;
	private String 		dataNumero;
	private String 	   	actionEffectue		;
	private String 		pageNumber;
	private String 		pageSize;
	private String 		sensitiveNumberPack;
	private Boolean     isFreezed;
//	private Long        contractId;
//	private Long        portNumber;
//	private String      serialNumber;
	private Integer     tariffModelCode;
	private String      profilLibelle        ;
	private Boolean     isValidated		     ;
	private String      profilCode           ;
	private String      status_act          ;
	private List<ActionUtilisateurDto> actionUtilisateurDtos;
	private Integer idDemande;
	private String identifiant;

	private Boolean isUnlocked;
	private Boolean	isTransferred;
	private Integer idTypeDemande;
	//
	
	private String filePath;
//	private List<ParamDto> datasParameter;
}
