

/*
 * Java transformer for entity table category 
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
 * TRANSFORMER for table "category"
 * 
 * @author Geo
 *
 */
@Mapper
public interface CategoryTransformer {

	CategoryTransformer INSTANCE = Mappers.getMapper(CategoryTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:MM:SS",target="updatedAt"),
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:MM:SS",target="createdAt"),
	})
	CategoryDto toDto(Category entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<CategoryDto> toDtos(List<Category> entities) throws ParseException;

    default CategoryDto toLiteDto(Category entity) {
		if (entity == null) {
			return null;
		}
		CategoryDto dto = new CategoryDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<CategoryDto> toLiteDtos(List<Category> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<CategoryDto> dtos = new ArrayList<CategoryDto>();
		for (Category entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.code", target="code"),
		@Mapping(source="dto.libelle", target="libelle"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
	})
    Category toEntity(CategoryDto dto) throws ParseException;

    //List<Category> toEntities(List<CategoryDto> dtos) throws ParseException;

}
