
/*
 * Java dto for entity table ation_to_notifiable 
 * Created on 2023-06-29 ( Time 14:15:46 )
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
import ci.smile.simswaporange.utils.dto.customize._AtionToNotifiableDto;

/**
 * DTO for table "ation_to_notifiable"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class AtionToNotifiableDto extends _AtionToNotifiableDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private Integer    idStatus             ;
    private Integer    idUserDemand         ;
    private Integer    idUserValid          ;
    private String     numero               ;
    private Boolean    isNotify             ;
    private Boolean    isEnMasse            ;
    private String     statutAction         ;
	private String     createdAt            ;
    private Integer    createdBy            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;
    private Boolean    isDeleted            ;
    private Integer    idAction             ;
	private String 	   message				;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String userNom;
	private String userPrenom;
	private String userLogin;
	private String actionSimswapLibelle;
	private String statusCode;
//	private String userNom;
//	private String userPrenom;
//	private String userLogin;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<Integer>  idStatusParam         ;                     
	private SearchParam<Integer>  idUserDemandParam     ;                     
	private SearchParam<Integer>  idUserValidParam      ;                     
	private SearchParam<String>   numeroParam           ;                     
	private SearchParam<Boolean>  isNotifyParam         ;                     
	private SearchParam<Boolean>  isEnMasseParam        ;                     
	private SearchParam<String>   statutActionParam     ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<Integer>  idActionParam         ;                     
	private SearchParam<String>   userNomParam          ;                     
	private SearchParam<String>   userPrenomParam       ;                     
	private SearchParam<String>   userLoginParam        ;                     
	private SearchParam<String>   messageParam        ;
	private SearchParam<String>   actionSimswapLibelleParam;
	private SearchParam<String>   statusCodeParam       ;                     
//	private SearchParam<String>   userNomParam          ;                     
//	private SearchParam<String>   userPrenomParam       ;                     
//	private SearchParam<String>   userLoginParam        ;                     
    /**
     * Default constructor
     */
    public AtionToNotifiableDto()
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
