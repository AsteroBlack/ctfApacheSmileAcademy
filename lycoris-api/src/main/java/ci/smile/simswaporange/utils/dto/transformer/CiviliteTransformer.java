

/*
 * Java transformer for entity table civilite 
 * Created on 2022-07-01 ( Time 17:34:13 )
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
 * TRANSFORMER for table "civilite"
 * 
 * @author Geo
 *
 */
@Mapper
public interface CiviliteTransformer {

	CiviliteTransformer INSTANCE = Mappers.getMapper(CiviliteTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:MM:SS",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:MM:SS",target="updatedAt"),
	})
	CiviliteDto toDto(Civilite entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<CiviliteDto> toDtos(List<Civilite> entities) throws ParseException;

    default CiviliteDto toLiteDto(Civilite entity) {
		if (entity == null) {
			return null;
		}
		CiviliteDto dto = new CiviliteDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<CiviliteDto> toLiteDtos(List<Civilite> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<CiviliteDto> dtos = new ArrayList<CiviliteDto>();
		for (Civilite entity : entities) {
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
    Civilite toEntity(CiviliteDto dto) throws ParseException;

    //List<Civilite> toEntities(List<CiviliteDto> dtos) throws ParseException;

}
