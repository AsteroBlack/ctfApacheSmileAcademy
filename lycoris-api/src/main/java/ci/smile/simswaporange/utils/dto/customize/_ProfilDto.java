
/*
 * Java dto for entity table profil 
 * Created on 2022-09-22 ( Time 16:00:42 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.dto.customize;

import java.util.Date;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ci.smile.simswaporange.utils.dto.FonctionnaliteDto;
import ci.smile.simswaporange.utils.dto.customize._ProfilDto;
import ci.smile.simswaporange.utils.contract.*;
import lombok.Data;

/**
 * DTO customize for table "profil"
 * 
 * @author Geo
 *
 */
@JsonInclude(Include.NON_NULL)
@Data
public class _ProfilDto {
	private List<FonctionnaliteDto> datasFonctionalites;

}