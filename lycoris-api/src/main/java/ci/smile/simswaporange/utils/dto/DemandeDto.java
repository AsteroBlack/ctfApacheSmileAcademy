
/*
 * Java dto for entity table demande 
 * Created on 2023-08-08 ( Time 13:26:59 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.dto;

import java.util.Date;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.dto.customize._DemandeDto;

/**
 * DTO for table "demande"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class DemandeDto extends _DemandeDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private Integer    idUser               ;
    private Integer    idTypeDemande        ;
    private Integer    idStatus             ;
    private String     numero               ;
	private String     createdAt            ;
	private String     updatedAt            ;
    private Boolean    isDeleted            ;
    private Boolean    twoBool	            ;

    private Integer    createdBy            ;
    private Integer    updatedByTelco       ;
    private Integer    updatedBy			;
    private String     motif                ;
    private String     offerName            ;
	private String     activationDate       ;
    private String     statusBscs           ;
    private Boolean    isValidated          ;
    private Integer    updatedByOmci        ;
    private Integer    typeNumeroId         ;
    private Integer    idTypeCategory       ;
	private Integer    idActionEnMasse       ;
	private String     portNumber           ;
    private String     contractId           ;
    private String     serialNumber         ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String categoryCode;
	private String categoryLibelle;
	private String statusCode;
	private String userNom;
	private String userPrenom;
	private String userLogin;
	private String typeNumeroLibelle;
	private String typeDemandeCode;
	private String typeDemandeLibelle;

	private String actionEnMasseLibelle;
	private String actionEnMasseCode;
	private String actionEnMasseIdentifiant;
	private String actionEnMasseLienFichier;
	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<Integer>  idUserParam           ;                     
	private SearchParam<Integer>  idTypeDemandeParam    ;                     
	private SearchParam<Integer>  idStatusParam         ;                     
	private SearchParam<String>   numeroParam           ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<Integer>  updatedByTelcoParam   ;                     
	private SearchParam<String>   motifParam            ;                     
	private SearchParam<String>   offerNameParam        ;                     
	private SearchParam<String>   activationDateParam   ;                     
	private SearchParam<String>   statusBscsParam       ;                     
	private SearchParam<Boolean>  isValidatedParam      ;
	private SearchParam<Boolean>  isTransfertParam      ;
	private SearchParam<Integer>  updatedByOmciParam    ;                     
	private SearchParam<Integer>  typeNumeroIdParam     ;                     
	private SearchParam<Integer>  idTypeCategoryParam   ;                     
	private SearchParam<String>   categoryCodeParam     ;                     
	private SearchParam<String>   categoryLibelleParam  ;                     
	private SearchParam<String>   statusCodeParam       ;                     
	private SearchParam<String>   userNomParam          ;                     
	private SearchParam<String>   userPrenomParam       ;                     
	private SearchParam<String>   userLoginParam        ;                     
	private SearchParam<String>   typeNumeroLibelleParam;                     
	private SearchParam<String>   typeDemandeCodeParam  ;                     
	private SearchParam<String>   typeDemandeLibelleParam;
	private SearchParam<String>   portNumberParam        ;                     
	private SearchParam<String>   contractIdParam    	  ;
	private SearchParam<String>   serialNumberParam       ; 
	private SearchParam<Boolean>  twoBoolParam      	  ;
	private SearchParam<Integer>  idActionEnMasseParam    ;
	private SearchParam<Boolean>  actionEnMasseLibelleParam;
	private SearchParam<Boolean>  actionEnMasseCodeParam	  ;
	private SearchParam<Boolean>  actionEnMasseIdentifiantParam;
	private SearchParam<Boolean>  actionEnMasseLienFichierParam;

    /**
     * Default constructor
     */
    public DemandeDto()
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
