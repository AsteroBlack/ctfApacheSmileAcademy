
/*
 * Java dto for entity table user 
 * Created on 2023-07-05 ( Time 13:47:04 )
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
import ci.smile.simswaporange.utils.dto.customize._UserDto;

import javax.persistence.Column;

/**
 * DTO for table "user"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class UserDto extends _UserDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private String     nom                  ;
    private String     prenom               ;
    private Boolean    isValidated          ;
	private String     createdAt            ;
    private Integer    createdBy            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;
    private Boolean    isDeleted            ;
    private Boolean    isLocked             ;
	private String     lockedAt             ;
    private Integer    lockedBy             ;
    private String     login                ;
    private Boolean    isSuperAdmin         ;
    private Boolean    locked               ;
    private Integer    idProfil             ;
    private Integer    idCivilite           ;
    private String     contact              ;
    private Boolean    isConnected          ;
    private Integer    idCategory           ;
    private String     emailAdresse         ;
	private String     firstConnection      ;
	private String     lastConnection       ;

	private String     machine       ;
	private String     ipadress       ;
	private String     status       ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String profilLibelle;
	private String profilCode;
	private String civiliteLibelle;
	private String categoryCode;
	private String categoryLibelle;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   nomParam              ;                     
	private SearchParam<String>   prenomParam           ;
	private SearchParam<String>   machineParam        	;
	private SearchParam<String>   ipadressParam         ;
	private SearchParam<String>   statusParam         	;
	private SearchParam<Boolean>  isValidatedParam      ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<Boolean>  isLockedParam         ;                     
	private SearchParam<String>   lockedAtParam         ;                     
	private SearchParam<Integer>  lockedByParam         ;                     
	private SearchParam<String>   loginParam            ;                     
	private SearchParam<Boolean>  isSuperAdminParam     ;                     
	private SearchParam<Boolean>  lockedParam           ;                     
	private SearchParam<Integer>  idProfilParam         ;                     
	private SearchParam<Integer>  idCiviliteParam       ;                     
	private SearchParam<String>   contactParam          ;                     
	private SearchParam<Boolean>  isConnectedParam      ;                     
	private SearchParam<Integer>  idCategoryParam       ;                     
	private SearchParam<String>   emailAdresseParam     ;                     
	private SearchParam<String>   firstConnectionParam  ;                     
	private SearchParam<String>   lastConnectionParam   ;                     
	private SearchParam<String>   profilLibelleParam    ;                     
	private SearchParam<String>   profilCodeParam       ;                     
	private SearchParam<String>   civiliteLibelleParam  ;                     
	private SearchParam<String>   categoryCodeParam     ;                     
	private SearchParam<String>   categoryLibelleParam  ;                     
    /**
     * Default constructor
     */
    public UserDto()
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
