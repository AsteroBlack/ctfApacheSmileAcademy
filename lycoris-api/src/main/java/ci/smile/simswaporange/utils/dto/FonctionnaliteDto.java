
/*
 * Java dto for entity table fonctionnalite 
 * Created on 2022-09-22 ( Time 16:00:42 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.dto.customize._FonctionnaliteDto;

/**
 * DTO for table "fonctionnalite"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class FonctionnaliteDto extends _FonctionnaliteDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private String     code                 ;
    private Integer    idParent             ;
	private String     createdAt            ;
    private Integer    createdBy            ;
    private Boolean    isDeleted            ;
    private String     libelle              ;
	private String     deletedAt            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;
    private Integer    deletedBy            ;
    
    private List<FonctionnaliteDto> datasFonctionaliteChildren;
    

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String fonctionnaliteCode;
	private String fonctionnaliteLibelle;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   codeParam             ;                     
	private SearchParam<Integer>  idParentParam         ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<String>   libelleParam          ;                     
	private SearchParam<String>   deletedAtParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<Integer>  deletedByParam        ;                     
	private SearchParam<String>   fonctionnaliteCodeParam;                     
	private SearchParam<String>   fonctionnaliteLibelleParam;                     
    /**
     * Default constructor
     */
    public FonctionnaliteDto()
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
