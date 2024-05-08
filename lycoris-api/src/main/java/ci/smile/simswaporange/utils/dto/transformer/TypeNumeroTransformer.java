

/*
 * Java transformer for entity table type_numero 
 * Created on 2023-06-27 ( Time 13:52:14 )
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
 * TRANSFORMER for table "type_numero"
 * 
 * @author Geo
 *
 */
@Mapper
public interface TypeNumeroTransformer {

	TypeNumeroTransformer INSTANCE = Mappers.getMapper(TypeNumeroTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
	})
	TypeNumeroDto toDto(TypeNumero entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<TypeNumeroDto> toDtos(List<TypeNumero> entities) throws ParseException;

    default TypeNumeroDto toLiteDto(TypeNumero entity) {
		if (entity == null) {
			return null;
		}
		TypeNumeroDto dto = new TypeNumeroDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<TypeNumeroDto> toLiteDtos(List<TypeNumero> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<TypeNumeroDto> dtos = new ArrayList<TypeNumeroDto>();
		for (TypeNumero entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.libelle", target="libelle"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
	})
    TypeNumero toEntity(TypeNumeroDto dto) throws ParseException;

    //List<TypeNumero> toEntities(List<TypeNumeroDto> dtos) throws ParseException;

}
