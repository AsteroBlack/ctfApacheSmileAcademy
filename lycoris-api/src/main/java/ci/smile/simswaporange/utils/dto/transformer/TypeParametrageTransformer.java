

/*
 * Java transformer for entity table type_parametrage 
 * Created on 2023-06-21 ( Time 19:28:14 )
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
 * TRANSFORMER for table "type_parametrage"
 * 
 * @author Geo
 *
 */
@Mapper
public interface TypeParametrageTransformer {

	TypeParametrageTransformer INSTANCE = Mappers.getMapper(TypeParametrageTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
	})
	TypeParametrageDto toDto(TypeParametrage entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<TypeParametrageDto> toDtos(List<TypeParametrage> entities) throws ParseException;

    default TypeParametrageDto toLiteDto(TypeParametrage entity) {
		if (entity == null) {
			return null;
		}
		TypeParametrageDto dto = new TypeParametrageDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<TypeParametrageDto> toLiteDtos(List<TypeParametrage> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<TypeParametrageDto> dtos = new ArrayList<TypeParametrageDto>();
		for (TypeParametrage entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.code", target="code"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.createdBy", target="createdBy"),
	})
    TypeParametrage toEntity(TypeParametrageDto dto) throws ParseException;

    //List<TypeParametrage> toEntities(List<TypeParametrageDto> dtos) throws ParseException;

}
