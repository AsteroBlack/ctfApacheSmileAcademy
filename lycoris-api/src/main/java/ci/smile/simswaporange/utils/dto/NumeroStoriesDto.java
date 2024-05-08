
/*
 * Java dto for entity table numero_stories 
 * Created on 2023-07-04 ( Time 13:03:13 )
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
import ci.smile.simswaporange.utils.dto.customize._NumeroStoriesDto;

/**
 * DTO for table "numero_stories"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class NumeroStoriesDto extends _NumeroStoriesDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private String     numero               ;
    private Integer    idProfil             ;
	private String     createdAt            ;
    private Integer    createdBy            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;
    private Boolean    isDeleted            ;
    private Integer    idStatut             ;
    private Integer    idCategory           ;
    private Boolean    isMachine            ;
    private Integer    idTypeNumero         ;
    private String     statut               ;
    private String     login                ;
    private String     machine              ;
    private String     adresseIp            ;
    private String     portNumber           ;
    private String     contractId           ;
    private String     serialNumber         ;
    private String     offerName         ;
	private String     reason         		;
    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String typeNumeroLibelle;
	private String profilLibelle;
	private String profilCode;
	private String categoryCode;
	private String categoryLibelle;
	private String userNom;
	private String userPrenom;
	private String userLogin;
	private String statusCode;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   numeroParam           ;
	private SearchParam<String>   reasonParam           ;
	private SearchParam<Integer>  idProfilParam         ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<Integer>  idStatutParam         ;                     
	private SearchParam<Integer>  idCategoryParam       ;                     
	private SearchParam<Boolean>  isMachineParam        ;                     
	private SearchParam<Integer>  idTypeNumeroParam     ;                     
	private SearchParam<String>   statutParam           ;                     
	private SearchParam<String>   loginParam            ;  
	private SearchParam<String>   offerNameParam            ;                     

	private SearchParam<String>   machineParam          ;                     
	private SearchParam<String>   adresseIpParam        ;                     
	private SearchParam<String>   typeNumeroLibelleParam;                     
	private SearchParam<String>   profilLibelleParam    ;                     
	private SearchParam<String>   profilCodeParam       ;                     
	private SearchParam<String>   categoryCodeParam     ;                     
	private SearchParam<String>   categoryLibelleParam  ;                     
	private SearchParam<String>   userNomParam          ;                     
	private SearchParam<String>   userPrenomParam       ;                     
	private SearchParam<String>   userLoginParam        ;                     
	private SearchParam<String>   statusCodeParam       ;      
	private SearchParam<String>   portNumberParam        ;                     
	private SearchParam<String>   contractIdParam    ;                     
	private SearchParam<String>   serialNumberParam       ; 
    /**
     * Default constructor
     */
    public NumeroStoriesDto()
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
