
/*
 * Java dto for entity table user 
 * Created on 2022-06-28 ( Time 15:30:59 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.dto.customize;

import java.util.Date;
import java.util.List;
import java.util.Date;

import ci.smile.simswaporange.utils.dto.ProfilFonctionnaliteDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.dto.CiviliteDto;
import ci.smile.simswaporange.utils.dto.FonctionnaliteDto;
import lombok.Data;
import lombok.ToString;

/**
 * DTO customize for table "user"
 * 
 * @author Geo
 *
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class _UserDto {
    private String     password             ;
    private String 	   actionEffectue		;
    private String     recipient;
    private String 		token;
    private String key;
    private String      sender;
    private String      content;
    private List<FonctionnaliteDto> fonctionnaliteData;
    private List<ProfilFonctionnaliteDto> datasFonctionalites;

}
