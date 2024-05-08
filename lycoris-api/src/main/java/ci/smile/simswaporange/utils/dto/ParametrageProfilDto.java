
/*
 * Java dto for entity table parametrage_profil 
 * Created on 2023-06-21 ( Time 19:39:01 )
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
import ci.smile.simswaporange.utils.dto.customize._ParametrageProfilDto;

/**
 * DTO for table "parametrage_profil"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class ParametrageProfilDto extends _ParametrageProfilDto implements Cloneable{

    private Integer    id                   ; // Primary Key

	private String     createdAt            ;
    private Integer    createdBy            ;
    private String     email                ;
    private Boolean    isDeleted            ;
    private String     nomPrenom            ;
	private String     updatedAt            ;
    private Integer    idParametrage        ;
    private String     numero               ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<String>   emailParam            ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<String>   nomPrenomParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  idParametrageParam    ;                     
	private SearchParam<String>   numeroParam           ;                     
    /**
     * Default constructor
     */
    public ParametrageProfilDto()
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
