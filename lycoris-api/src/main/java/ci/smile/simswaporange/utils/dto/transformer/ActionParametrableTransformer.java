

/*
 * Java transformer for entity table action_parametrable 
 * Created on 2023-06-29 ( Time 14:01:11 )
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
 * TRANSFORMER for table "action_parametrable"
 * 
 * @author Geo
 *
 */
@Mapper
public interface ActionParametrableTransformer {

	ActionParametrableTransformer INSTANCE = Mappers.getMapper(ActionParametrableTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
	})
	ActionParametrableDto toDto(ActionParametrable entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<ActionParametrableDto> toDtos(List<ActionParametrable> entities) throws ParseException;

    default ActionParametrableDto toLiteDto(ActionParametrable entity) {
		if (entity == null) {
			return null;
		}
		ActionParametrableDto dto = new ActionParametrableDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<ActionParametrableDto> toLiteDtos(List<ActionParametrable> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<ActionParametrableDto> dtos = new ArrayList<ActionParametrableDto>();
		for (ActionParametrable entity : entities) {
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
    ActionParametrable toEntity(ActionParametrableDto dto) throws ParseException;

    //List<ActionParametrable> toEntities(List<ActionParametrableDto> dtos) throws ParseException;

}
