

/*
 * Java transformer for entity table action_simswap 
 * Created on 2023-07-04 ( Time 12:38:30 )
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
 * TRANSFORMER for table "action_simswap"
 * 
 * @author Geo
 *
 */
@Mapper
public interface ActionSimswapTransformer {

	ActionSimswapTransformer INSTANCE = Mappers.getMapper(ActionSimswapTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
	})
	ActionSimswapDto toDto(ActionSimswap entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<ActionSimswapDto> toDtos(List<ActionSimswap> entities) throws ParseException;

    default ActionSimswapDto toLiteDto(ActionSimswap entity) {
		if (entity == null) {
			return null;
		}
		ActionSimswapDto dto = new ActionSimswapDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<ActionSimswapDto> toLiteDtos(List<ActionSimswap> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<ActionSimswapDto> dtos = new ArrayList<ActionSimswapDto>();
		for (ActionSimswap entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.libelle", target="libelle"),
		@Mapping(source="dto.uri", target="uri"),
	})
    ActionSimswap toEntity(ActionSimswapDto dto) throws ParseException;

    //List<ActionSimswap> toEntities(List<ActionSimswapDto> dtos) throws ParseException;

}
