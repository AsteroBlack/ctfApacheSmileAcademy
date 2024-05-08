
/*
 * Java dto for entity table profil_fonctionnalite 
 * Created on 2022-09-22 ( Time 16:00:42 )
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
import ci.smile.simswaporange.utils.dto.customize._ProfilFonctionnaliteDto;

/**
 * DTO for table "profil_fonctionnalite"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class ProfilFonctionnaliteDto extends _ProfilFonctionnaliteDto implements Cloneable{

    private Integer    id                   ; // Primary Key

	private String     createdAt            ;
    private String     code                 ;
    private Integer    createdBy            ;
    private String     libelle              ;
    private Boolean    isDeleted            ;
    private Integer    profilId             ;
    private Integer    fonctionnaliteId     ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;
	private String     deletedAt            ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String fonctionnaliteCode;
	private String fonctionnaliteLibelle;
	private String profilCode;
	private String profilLibelle;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<String>   codeParam             ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<String>   libelleParam          ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<Integer>  profilIdParam         ;                     
	private SearchParam<Integer>  fonctionnaliteIdParam ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<String>   deletedAtParam        ;                     
	private SearchParam<String>   fonctionnaliteCodeParam;                     
	private SearchParam<String>   fonctionnaliteLibelleParam;                     
	private SearchParam<String>   profilCodeParam       ;                     
	private SearchParam<String>   profilLibelleParam    ;                     
    /**
     * Default constructor
     */
    public ProfilFonctionnaliteDto()
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
