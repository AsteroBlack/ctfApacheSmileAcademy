

/*
 * Java transformer for entity table profil 
 * Created on 2022-09-22 ( Time 16:00:42 )
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
 * TRANSFORMER for table "profil"
 * 
 * @author Geo
 *
 */
@Mapper
public interface ProfilTransformer {

	ProfilTransformer INSTANCE = Mappers.getMapper(ProfilTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
		@Mapping(source="entity.deletedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="deletedAt"),
	})
	ProfilDto toDto(Profil entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<ProfilDto> toDtos(List<Profil> entities) throws ParseException;

    default ProfilDto toLiteDto(Profil entity) {
		if (entity == null) {
			return null;
		}
		ProfilDto dto = new ProfilDto();
		dto.setId( entity.getId() );
		dto.setLibelle( entity.getLibelle() );
		return dto;
    }

	default List<ProfilDto> toLiteDtos(List<Profil> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<ProfilDto> dtos = new ArrayList<ProfilDto>();
		for (Profil entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.code", target="code"),
		@Mapping(source="dto.corail", target="corail"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.libelle", target="libelle"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
		@Mapping(source="dto.deletedBy", target="deletedBy"),
	})
    Profil toEntity(ProfilDto dto) throws ParseException;

    //List<Profil> toEntities(List<ProfilDto> dtos) throws ParseException;

}
