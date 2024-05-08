

/*
 * Java transformer for entity table parametrage_profil 
 * Created on 2023-06-21 ( Time 19:39:01 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.dto.transformer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.dao.entity.*;


/**
 * TRANSFORMER for table "parametrage_profil"
 * 
 * @author Geo
 *
 */
@Mapper
public interface ParametrageProfilTransformer {

	ParametrageProfilTransformer INSTANCE = Mappers.getMapper(ParametrageProfilTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
	})
	ParametrageProfilDto toDto(ParametrageProfil entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<ParametrageProfilDto> toDtos(List<ParametrageProfil> entities) throws ParseException;

    default ParametrageProfilDto toLiteDto(ParametrageProfil entity) {
		if (entity == null) {
			return null;
		}
		ParametrageProfilDto dto = new ParametrageProfilDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<ParametrageProfilDto> toLiteDtos(List<ParametrageProfil> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<ParametrageProfilDto> dtos = new ArrayList<ParametrageProfilDto>();
		for (ParametrageProfil entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.email", target="email"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.nomPrenom", target="nomPrenom"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.idParametrage", target="idParametrage"),
		@Mapping(source="dto.numero", target="numero"),
	})
    ParametrageProfil toEntity(ParametrageProfilDto dto) throws ParseException;

    //List<ParametrageProfil> toEntities(List<ParametrageProfilDto> dtos) throws ParseException;

}
