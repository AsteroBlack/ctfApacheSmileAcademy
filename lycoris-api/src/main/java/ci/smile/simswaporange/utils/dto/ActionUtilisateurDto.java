
/*
 * Java dto for entity table action_utilisateur 
 * Created on 2023-06-27 ( Time 13:58:37 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.dto;

import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.dto.customize._ActionUtilisateurDto;

/**
 * DTO for table "action_utilisateur"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class ActionUtilisateurDto extends _ActionUtilisateurDto implements Cloneable{

    private Integer    id                   ; // Primary Key

	private String     date                 ;
	private String     createdAt            ;
    private Integer    createdBy            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;
    private Boolean    isDeleted            ;
    private Integer    idStatus             ;
    private Integer    idUser               ;
    private String     numero               ;
    private String     motif                ;
    private String     empreinte            ;
    private Integer    idProfil             ;
    private Boolean    isExcelNumber        ;
    private String     statut               ;
    private Integer    idCategory           ;
    private Boolean    isMachine            ;
	private String     lastMachineDate      ;
	private String     lastDebloquage       ;
	private String     lastBlocageDate      ;
    private String     statusBscs           ;
	private String     activationDate       ;
    private String     offerName            ;
    private Boolean    fromBss              ;
    private Integer    typeNumeroId         ;
    private String     portNumber           ;
    private String     contractId           ;
    private String     serialNumber         ;
    private String 	   tmCode				;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String typeNumeroLibelle;
	private String statusCode;
	private String categoryCode;
	private String categoryLibelle;
	private String userNom;
	private String userPrenom;
	private String userLogin;
	private String profilLibelle;
	private String profilCode;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   dateParam             ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<Integer>  idStatusParam         ;                     
	private SearchParam<Integer>  idUserParam           ;                     
	private SearchParam<String>   numeroParam           ;                     
	private SearchParam<String>   motifParam            ;                     
	private SearchParam<String>   empreinteParam        ;                     
	private SearchParam<Integer>  idProfilParam         ;                     
	private SearchParam<Boolean>  isExcelNumberParam    ;                     
	private SearchParam<String>   statutParam           ; 
	private SearchParam<String>   tmCodeParam           ;                     
	private SearchParam<Integer>  idCategoryParam       ;                     
	private SearchParam<Boolean>  isMachineParam        ;                     
	private SearchParam<String>   lastMachineDateParam  ;                     
	private SearchParam<String>   lastDebloquageParam   ;    
	private SearchParam<String>   lastBlocageDateParam  ;                     
	private SearchParam<String>   statusBscsParam       ;                     
	private SearchParam<String>   activationDateParam   ;                     
	private SearchParam<String>   offerNameParam        ;                     
	private SearchParam<Boolean>  fromBssParam          ;                     
	private SearchParam<Integer>  typeNumeroIdParam     ;                     
	private SearchParam<String>   typeNumeroLibelleParam;                     
	private SearchParam<String>   statusCodeParam       ;                     
	private SearchParam<String>   categoryCodeParam     ;                     
	private SearchParam<String>   categoryLibelleParam  ;                     
	private SearchParam<String>   userNomParam          ;                     
	private SearchParam<String>   userPrenomParam       ;                     
	private SearchParam<String>   userLoginParam        ;                     
	private SearchParam<String>   profilLibelleParam    ;                     
	private SearchParam<String>   profilCodeParam       ;
	private SearchParam<String>   portNumberParam        ;                     
	private SearchParam<String>   contractIdParam    ;                     
	private SearchParam<String>   serialNumberParam       ; 
    /**
     * Default constructor
     */
    public ActionUtilisateurDto()
    {
        super();
    }
    
	//----------------------------------------------------------------------
    // clone METHOD
    //----------------------------------------------------------------------
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
