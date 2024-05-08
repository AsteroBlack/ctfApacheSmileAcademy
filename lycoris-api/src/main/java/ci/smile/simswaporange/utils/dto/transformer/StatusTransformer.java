

/*
 * Java transformer for entity table status 
 * Created on 2022-10-04 ( Time 11:23:37 )
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
 * TRANSFORMER for table "status"
 * 
 * @author Geo
 *
 */
@Mapper
public interface StatusTransformer {

	StatusTransformer INSTANCE = Mappers.getMapper(StatusTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:MM:SS",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:MM:SS",target="updatedAt"),
	})
	StatusDto toDto(Status entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<StatusDto> toDtos(List<Status> entities) throws ParseException;

    default StatusDto toLiteDto(Status entity) {
		if (entity == null) {
			return null;
		}
		StatusDto dto = new StatusDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<StatusDto> toLiteDtos(List<Status> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<StatusDto> dtos = new ArrayList<StatusDto>();
		for (Status entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.code", target="code"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
	})
    Status toEntity(StatusDto dto) throws ParseException;

    //List<Status> toEntities(List<StatusDto> dtos) throws ParseException;

}
