

/*
 * Java transformer for entity table historique
 * Created on 2023-07-05 ( Time 13:10:58 )
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
 * TRANSFORMER for table "historique"
 *
 * @author Geo
 */
@Mapper
public interface HistoriqueTransformer {

    HistoriqueTransformer INSTANCE = Mappers.getMapper(HistoriqueTransformer.class);

    @FullTransformerQualifier
    @Mappings({
            @Mapping(source = "entity.createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss", target = "createdAt"),
            @Mapping(source = "entity.updatedAt", dateFormat = "dd/MM/yyyy HH:mm:ss", target = "updatedAt"),
            @Mapping(source = "entity.date", dateFormat = "dd/MM/yyyy HH:mm:ss", target = "date"),
            @Mapping(source = "entity.actionSimswap.id", target = "idActionUser"),
            @Mapping(source = "entity.actionSimswap.libelle", target = "actionSimswapLibelle"),
            @Mapping(source = "entity.user.id", target = "idUser"),
            @Mapping(source = "entity.user.nom", target = "userNom"),
            @Mapping(source = "entity.user.prenom", target = "userPrenom"),
            @Mapping(source = "entity.user.login", target = "userLogin"),
            @Mapping(source = "entity.status.id", target = "idStatus"),
            @Mapping(source = "entity.status.code", target = "statusCode"),

    })
    HistoriqueDto toDto(Historique entity) throws ParseException;

    @IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<HistoriqueDto> toDtos(List<Historique> entities) throws ParseException;

    default HistoriqueDto toLiteDto(Historique entity) {
        if (entity == null) {
            return null;
        }
        HistoriqueDto dto = new HistoriqueDto();
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        return dto;
    }

    default List<HistoriqueDto> toLiteDtos(List<Historique> entities) {
        if (entities == null || entities.stream().allMatch(o -> o == null)) {
            return null;
        }
        List<HistoriqueDto> dtos = new ArrayList<HistoriqueDto>();
        for (Historique entity : entities) {
            dtos.add(toLiteDto(entity));
        }
        return dtos;
    }

    @Mappings({
            @Mapping(source = "dto.id", target = "id"),
            @Mapping(source = "dto.request", target = "request"),
            @Mapping(source = "dto.uri", target = "uri"),
            @Mapping(source = "dto.response", target = "response"),
            @Mapping(source = "dto.createdAt", dateFormat = "dd/MM/yyyy", target = "createdAt"),
            @Mapping(source = "dto.createdBy", target = "createdBy"),
            @Mapping(source = "dto.updatedAt", dateFormat = "dd/MM/yyyy", target = "updatedAt"),
            @Mapping(source = "dto.updatedBy", target = "updatedBy"),
            @Mapping(source = "dto.isDeleted", target = "isDeleted"),
            @Mapping(source = "dto.date", dateFormat = "dd/MM/yyyy", target = "date"),
            @Mapping(source = "dto.actionService", target = "actionService"),
            @Mapping(source = "dto.login", target = "login"),
            @Mapping(source = "dto.raison", target = "raison"),
            @Mapping(source = "dto.numero", target = "numero"),
            @Mapping(source = "dto.nom", target = "nom"),
            @Mapping(source = "dto.username", target = "username"),
            @Mapping(source = "dto.firstname", target = "firstname"),
            @Mapping(source = "dto.name", target = "name"),
            @Mapping(source = "dto.prenom", target = "prenom"),
            @Mapping(source = "dto.isConnexion", target = "isConnexion"),
            @Mapping(source = "dto.statusConnection", target = "statusConnection"),
            @Mapping(source = "dto.machine", target = "machine"),
            @Mapping(source = "dto.ipadress", target = "ipadress"),
            @Mapping(source = "actionSimswap", target = "actionSimswap"),
            @Mapping(source = "user", target = "user"),
            @Mapping(source = "status", target = "status"),

    })
    Historique toEntity(HistoriqueDto dto, ActionSimswap actionSimswap, User user, Status status) throws ParseException;

    //List<Historique> toEntities(List<HistoriqueDto> dtos) throws ParseException;

}
