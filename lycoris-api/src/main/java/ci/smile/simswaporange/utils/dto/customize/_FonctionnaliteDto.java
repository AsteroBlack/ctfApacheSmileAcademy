
/*
 * Java dto for entity table fonctionnalite 
 * Created on 2022-09-22 ( Time 16:00:42 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.dto.customize;

import java.util.Date;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ci.smile.simswaporange.utils.dto.FonctionnaliteDto;
import lombok.Data;
import lombok.ToString;
import ci.smile.simswaporange.utils.contract.*;

/**
 * DTO customize for table "fonctionnalite"
 * 
 * @author Geo
 *
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class _FonctionnaliteDto {
	protected String parentCode;
	protected List<FonctionnaliteDto> datasChildren;
}
