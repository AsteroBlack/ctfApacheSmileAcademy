
/*
 * Java dto for entity table action_en_masse 
 * Created on 2023-06-28 ( Time 20:24:52 )
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
import ci.smile.simswaporange.utils.dto.customize._ActionEnMasseDto;

/**
 * DTO for table "action_en_masse"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class ActionEnMasseDto extends _ActionEnMasseDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private String     code                 ;
    private String     libelle              ;
    private String     lienFichier          ;
	private String     updatedAt            ;
	private String     createdAt            ;
    private Boolean    isDeleted            ;
    private Integer    updatedBy            ;
    private Integer    createdBy            ;
    private String     identifiant          ;
    private Boolean    isOk                 ;
    private String     lienImport           ;
    private String     linkDownload         ;
    private Boolean    putOn                ;
    private Boolean    putOf                ;
    private String     linkDownloadMasse    ;
    private Boolean    error                ;
    private Boolean    isAutomatically      ;
	private Boolean    isDebloageEnMasse	;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   codeParam             ;                     
	private SearchParam<String>   libelleParam          ;                     
	private SearchParam<String>   lienFichierParam      ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<String>   identifiantParam      ;                     
	private SearchParam<Boolean>  isOkParam             ;                     
	private SearchParam<String>   lienImportParam       ;                     
	private SearchParam<String>   linkDownloadParam     ;                     
	private SearchParam<Boolean>  putOnParam            ;                     
	private SearchParam<Boolean>  putOfParam            ;                     
	private SearchParam<String>   linkDownloadMasseParam;                     
	private SearchParam<Boolean>  errorParam            ;                     
	private SearchParam<Boolean>  isAutomaticallyParam  ;                     
	private SearchParam<Boolean>  isDebloageEnMasseParam  ;
    /**
     * Default constructor
     */
    public ActionEnMasseDto()
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
