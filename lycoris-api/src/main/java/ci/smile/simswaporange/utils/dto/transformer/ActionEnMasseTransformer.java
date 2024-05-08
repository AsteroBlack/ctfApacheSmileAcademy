

/*
 * Java transformer for entity table action_en_masse 
 * Created on 2023-06-28 ( Time 20:24:52 )
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
 * TRANSFORMER for table "action_en_masse"
 * 
 * @author Geo
 *
 */
@Mapper
public interface ActionEnMasseTransformer {

	ActionEnMasseTransformer INSTANCE = Mappers.getMapper(ActionEnMasseTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
	})
	ActionEnMasseDto toDto(ActionEnMasse entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<ActionEnMasseDto> toDtos(List<ActionEnMasse> entities) throws ParseException;

    default ActionEnMasseDto toLiteDto(ActionEnMasse entity) {
		if (entity == null) {
			return null;
		}
		ActionEnMasseDto dto = new ActionEnMasseDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<ActionEnMasseDto> toLiteDtos(List<ActionEnMasse> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<ActionEnMasseDto> dtos = new ArrayList<ActionEnMasseDto>();
		for (ActionEnMasse entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.code", target="code"),
		@Mapping(source="dto.libelle", target="libelle"),
		@Mapping(source="dto.lienFichier", target="lienFichier"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.identifiant", target="identifiant"),
		@Mapping(source="dto.isOk", target="isOk"),
		@Mapping(source="dto.lienImport", target="lienImport"),
		@Mapping(source="dto.linkDownload", target="linkDownload"),
		@Mapping(source="dto.putOn", target="putOn"),
		@Mapping(source="dto.putOf", target="putOf"),
		@Mapping(source="dto.linkDownloadMasse", target="linkDownloadMasse"),
		@Mapping(source="dto.error", target="error"),
		@Mapping(source="dto.isAutomatically", target="isAutomatically"),
		@Mapping(source="dto.isDebloageEnMasse", target="isDebloageEnMasse"),
	})
    ActionEnMasse toEntity(ActionEnMasseDto dto) throws ParseException;

    //List<ActionEnMasse> toEntities(List<ActionEnMasseDto> dtos) throws ParseException;

}
