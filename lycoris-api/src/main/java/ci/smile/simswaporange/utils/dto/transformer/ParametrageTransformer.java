

/*
 * Java transformer for entity table parametrage 
 * Created on 2023-06-28 ( Time 22:33:01 )
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
 * TRANSFORMER for table "parametrage"
 * 
 * @author Geo
 *
 */
@Mapper
public interface ParametrageTransformer {

	ParametrageTransformer INSTANCE = Mappers.getMapper(ParametrageTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
		@Mapping(source="entity.typeParametrage.id", target="idTypeParametrage"),
		@Mapping(source="entity.typeParametrage.code", target="typeParametrageCode"),
	})
	ParametrageDto toDto(Parametrage entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<ParametrageDto> toDtos(List<Parametrage> entities) throws ParseException;

    default ParametrageDto toLiteDto(Parametrage entity) {
		if (entity == null) {
			return null;
		}
		ParametrageDto dto = new ParametrageDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<ParametrageDto> toLiteDtos(List<Parametrage> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<ParametrageDto> dtos = new ArrayList<ParametrageDto>();
		for (Parametrage entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.delaiAttente", target="delaiAttente"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="typeParametrage", target="typeParametrage"),
	})
    Parametrage toEntity(ParametrageDto dto, TypeParametrage typeParametrage) throws ParseException;

    //List<Parametrage> toEntities(List<ParametrageDto> dtos) throws ParseException;

}
