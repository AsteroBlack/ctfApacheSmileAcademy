
/*
 * Java dto for entity table product 
 * Created on 2023-12-28 ( Time 14:27:12 )
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
import ci.smile.simswaporange.utils.dto.customize._ProductDto;

/**
 * DTO for table "product"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class ProductDto extends _ProductDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private String     code                 ;
    private String     libelle              ;
	private String     updatedAt            ;
	private String     createdAt            ;
    private Boolean    isDeleted            ;
    private Integer    createdBy            ;
    private Integer    updatedBy            ;
    private String     idTransaction        ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String transactionCode;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   codeParam             ;                     
	private SearchParam<String>   libelleParam          ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<String>   idTransactionParam    ;                     
	private SearchParam<String>   transactionCodeParam  ;                     
    /**
     * Default constructor
     */
    public ProductDto()
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
