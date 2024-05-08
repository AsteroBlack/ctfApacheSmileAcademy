
/*
 * Java dto for entity table historique 
 * Created on 2023-07-05 ( Time 13:10:58 )
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
import ci.smile.simswaporange.utils.dto.customize._HistoriqueDto;

import javax.persistence.Column;

/**
 * DTO for table "historique"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class HistoriqueDto extends _HistoriqueDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private String     request              ;
    private String     response             ;

	private String     createdAt            ;
    private Integer    createdBy            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;
    private Boolean    isDeleted            ;
    private Integer    idStatus             ;
	private Integer    idUser            	;
	private String     numero				;
	private String     date                 ;
    private String     actionService        ;
    private String     login                ;
    private String     nom                  ;
    private String     prenom               ;
	private String     username        ;
	private String     name          ;
	private String     firstname       ;
	private String     uri               	;
	private Integer    idActionUser         ;
    private Boolean    isConnexion          ;
    private String     statusConnection     ;
    private String     machine              ;
    private String     ipadress             ;
    
	private String     firstConnection      ;
	private String     lastConnection       ;
    private Boolean	   forLogConnexion      ;
	private String raison;


	//----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String actionSimswapLibelle;
	private String userNom;
	private String userPrenom;
	private String userLogin;
	private String statusCode;
	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   requestParam          ;                     
	private SearchParam<String>   responseParam         ;                     
	private SearchParam<Integer>  idUserParam           ;                     
	private SearchParam<String>   createdAtParam        ;
	private SearchParam<String>   uriParam        		;
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<Integer>  idStatusParam         ;
	private SearchParam<String>    numeroParam			;

	private SearchParam<String>   dateParam             ;                     
	private SearchParam<String>   actionServiceParam    ;                     
	private SearchParam<String>   loginParam            ;                     
	private SearchParam<String>   nomParam              ;                     
	private SearchParam<String>   prenomParam           ;                     
	private SearchParam<Integer>  idActionUserParam     ;                     
	private SearchParam<Boolean>  isConnexionParam      ;                     
	private SearchParam<String>   statusConnectionParam ;                     
	private SearchParam<String>   machineParam          ;                     
	private SearchParam<String>   ipadressParam         ;                     
	private SearchParam<String>   actionSimswapLibelleParam;

	private SearchParam<String>   userNomParam ;
	private SearchParam<String>   userPrenomParam          ;
	private SearchParam<String>   userLoginParam         ;
	private SearchParam<String>   statusCodeParam;
	private SearchParam<String>   raisonParam;

	private SearchParam<String>   usernameParam         ;
	private SearchParam<String>   firstnameParam;
	private SearchParam<String>   nameParam;

	/**
     * Default constructor
     */
    public HistoriqueDto()
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
