
/*
 * Java dto for entity table tache 
 * Created on 2023-07-04 ( Time 13:18:47 )
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
import ci.smile.simswaporange.utils.dto.customize._TacheDto;

/**
 * DTO for table "tache"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class TacheDto extends _TacheDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private Integer    idUser               ;
    private Integer    idTypeDemande        ;
    private Integer    idDemande            ;
	private String     updatedAt            ;
	private String     createdAt            ;
    private Boolean    isDeleted            ;
    private Integer    updatedBy            ;
    private Integer    createdBy            ;
	private String     dateDemande          ;
    private Integer    idActionEnMasse      ;
    private Integer    idStatus             ;
    private Integer    idActionSimswap      ;
    
    private DemandeDto demandeDto			;
    private UserDto    userOmciDto			;
    private UserDto	   userTelcoDto			;
    private ActionEnMasseDto actionEnMasseDto;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String actionEnMasseCode;
	private String actionEnMasseLibelle;
	private String actionSimswapLibelle;
	private String statusCode;
	private String typeDemandeCode;
	private String typeDemandeLibelle;
	private String userNom;
	private String userPrenom;
	private String userLogin;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<Integer>  idUserParam           ;                     
	private SearchParam<Integer>  idTypeDemandeParam    ;                     
	private SearchParam<Integer>  idDemandeParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<String>   dateDemandeParam      ;                     
	private SearchParam<Integer>  idActionEnMasseParam  ;                     
	private SearchParam<Integer>  idStatusParam         ;                     
	private SearchParam<Integer>  idActionSimswapParam  ;                     
	private SearchParam<String>   actionEnMasseCodeParam;                     
	private SearchParam<String>   actionEnMasseLibelleParam;                     
	private SearchParam<String>   actionSimswapLibelleParam;                     
	private SearchParam<String>   statusCodeParam       ;                     
	private SearchParam<String>   typeDemandeCodeParam  ;                     
	private SearchParam<String>   typeDemandeLibelleParam;                     
	private SearchParam<String>   userNomParam          ;                     
	private SearchParam<String>   userPrenomParam       ;                     
	private SearchParam<String>   userLoginParam        ;                     
    /**
     * Default constructor
     */
    public TacheDto()
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
