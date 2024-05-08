
/*
 * Java business for entity table user
 * Created on 2022-10-04 ( Time 11:23:37 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.business;

import ci.smile.simswaporange.dao.constante.Constant;
import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.proxy.service.SimSwapServiceProxyService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.utils.enums.*;
import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.contract.IBasicBusiness;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.transformer.*;
import ci.smile.simswaporange.dao.entity.User;
import ci.smile.simswaporange.dao.entity.Category;
import ci.smile.simswaporange.dao.entity.Profil;
import ci.smile.simswaporange.dao.entity.Civilite;
import ci.smile.simswaporange.dao.entity.Fonctionnalite;
import ci.smile.simswaporange.dao.entity.ProfilFonctionnalite;
import ci.smile.simswaporange.utils.Utilities;
import ci.smile.simswaporange.dao.repository.FonctionnaliteRepository;
import ci.smile.simswaporange.security.Rsa;
import ci.smile.simswaporange.dao.config.service.RedisService;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;

/**
 * BUSINESS for table "user"
 *
 * @author Geo
 */
@Log
@Component
public class UserBusiness implements IBasicBusiness<Request<UserDto>, Response<UserDto>> {

    private Response<UserDto> response;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FonctionnaliteRepository fonctionnaliteRepository;
    @Autowired
    private ActionUtilisateurRepository actionUtilisateurRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NumeroStoriesRepository numeroStoriesRepository;
    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private ProfilFonctionnaliteRepository profilFonctionnaliteRepository;
    @Autowired
    private CiviliteRepository civiliteRepository;
    @Autowired
    private TacheRepository tacheRepository;
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private ActionSimswapRepository actionSimswapRepository;
    @Autowired
    private AtionToNotifiableRepository ationToNotifiableRepository;
    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private TechnicalError technicalError;

    private Logger slf4jLogger;

    @Autowired
    private ParamsUtils paramsUtils;

    @Autowired
    private RedisService redisService;
    @Autowired
    private ExceptionUtils exceptionUtils;

    @Autowired
    private SimSwapServiceProxyService simSwapServiceProxyService;
    @PersistenceContext
    private EntityManager em;

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dateTimeFormat;

    public UserBusiness() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        slf4jLogger = LoggerFactory.getLogger(getClass());
    }

    /**
     * create User by using UserDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    @SneakyThrows
    public Response<UserDto> create(Request<UserDto> request, Locale locale) throws ParseException {
        log.info("----begin create User-----");

        Response<UserDto> response = new Response<UserDto>();
        List<User> items = new ArrayList<>();

        for (UserDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<>();
            fieldsToVerify.put("nom", dto.getNom());
            fieldsToVerify.put("prenom", dto.getPrenom());
            fieldsToVerify.put("login", dto.getLogin());
            fieldsToVerify.put("idProfil", dto.getIdProfil());
            fieldsToVerify.put("contact", dto.getContact());
            fieldsToVerify.put("emailAdresse", dto.getEmailAdresse());
//			fieldsToVerify.put("idCivilite", dto.getIdCivilite());
            fieldsToVerify.put("idCategory", dto.getIdCategory());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }
            User requester = null;
            // verif unique login in db
            requester = userRepository.findOne(request.getUser(), false);
            Validate.EntityNotExiste(requester);
            // Verify if user to insert do not exist
            User existingEntity = null;
            // verif unique login in db
            existingEntity = userRepository.findByLogin(dto.getLogin(), false);
            if (existingEntity != null) {
                response.setStatus(functionalError.DATA_EXIST("user login -> " + dto.getLogin(), locale));
                response.setHasError(true);
                return response;
            }
            // verif unique login in items to save
            if (items.stream().anyMatch(a -> a.getLogin().equalsIgnoreCase(dto.getLogin()))) {
                response.setStatus(functionalError.DATA_DUPLICATE(" login ", locale));
                response.setHasError(true);
                return response;
            }
            // Verify if category exist
            Category existingCategory = null;
            if (dto.getIdCategory() != null && dto.getIdCategory() > 0) {
                existingCategory = categoryRepository.findOne(dto.getIdCategory(), false);
                if (existingCategory == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("category idCategory -> " + dto.getIdCategory(), locale));
                    response.setHasError(true);
                    return response;
                }
            }
            // Verify if profil exist
            Profil existingProfil = null;
            if (dto.getIdProfil() != null && dto.getIdProfil() > 0) {
                existingProfil = profilRepository.findOne(dto.getIdProfil(), false);
                if (existingProfil == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("profil idProfil -> " + dto.getIdProfil(), locale));
                    response.setHasError(true);
                    return response;
                }
            }
            // Verify if civilite exist
            Civilite existingCivilite = null;
            if (dto.getIdCivilite() != null && dto.getIdCivilite() > 0) {
                existingCivilite = civiliteRepository.findOne(dto.getIdCivilite(), false);
                if (existingCivilite == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("civilite idCivilite -> " + dto.getIdCivilite(), locale));
                    response.setHasError(true);
                    return response;
                }
            }
            User entityToSave = null;
            entityToSave = UserTransformer.INSTANCE.toEntity(dto, existingProfil, existingCivilite, existingCategory);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setIsDeleted(Boolean.FALSE);
            entityToSave.setLocked(Boolean.FALSE);
            entityToSave.setIsValidated(Boolean.TRUE);
            items.add(entityToSave);
            LocalDateTime now = LocalDateTime.now();
            String messageAlerteConnexion = Utilities.messageCreationUtilisateur(now, entityToSave.getNom() + " " + entityToSave.getPrenom(), entityToSave.getLogin(), entityToSave.getProfil().getLibelle(), entityToSave.getCategory().getLibelle(), requester.getNom() + " " + requester.getPrenom(), requester.getLogin(), requester.getProfil().getLibelle(), requester.getCategory().getLibelle());
            AtionToNotifiable ationToNotifiable = extracted("AJOUTER UN UTILISATEUR",null, requester,null,request.getUser(),null, "SUCCESS",
                    messageAlerteConnexion);
            try {
                simSwapServiceProxyService.sendEmail(messageAlerteConnexion, ationToNotifiable);
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }

        if (!items.isEmpty()) {
            List<User> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = userRepository.saveAll((Iterable<User>) items);
            if (itemsSaved == null) {
                response.setStatus(functionalError.SAVE_FAIL("user", locale));
                response.setHasError(true);
                return response;
            }
            List<UserDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UserTransformer.INSTANCE.toLiteDtos(itemsSaved) : UserTransformer.INSTANCE.toDtos(itemsSaved);

            final int size = itemsSaved.size();
            List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
            itemsDto.parallelStream().forEach(dto -> {
                try {
                    dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
                } catch (Exception e) {
                    listOfError.add(e.getMessage());
                    e.printStackTrace();
                }
            });
            if (Utilities.isNotEmpty(listOfError)) {
                Object[] objArray = listOfError.stream().distinct().toArray();
                throw new RuntimeException(StringUtils.join(objArray, ", "));
            }
            response.setStatus(functionalError.SUCCESS("", locale));
            response.setItems(itemsDto);
            response.setHasError(false);
        }
        response.setStatus(functionalError.SUCCESS("operation effectué", locale));
        response.setActionEffectue("Création d'utilisateur");
        log.info("----end create User-----");
        return response;
    }

    /**
     * update User by using UserDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<UserDto> update(Request<UserDto> request, Locale locale) throws ParseException {
        log.info("----begin update User-----");

        Response<UserDto> response = new Response<UserDto>();
        List<User> items = new ArrayList<User>();

        for (UserDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }

            // Verifier si la user existe
            User entityToSave = null;
            entityToSave = userRepository.findOne(dto.getId(), false);
            if (entityToSave == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("user id -> " + dto.getId(), locale));
                response.setHasError(true);
                return response;
            }

            User existingUser = null;
            existingUser = userRepository.findOne(request.getUser(), false);
            if (existingUser == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("user id -> " + dto.getId(), locale));
                response.setHasError(true);
                return response;
            }

            // Verify if category exist
            if (dto.getIdCategory() != null && dto.getIdCategory() > 0) {
                Category existingCategory = categoryRepository.findOne(dto.getIdCategory(), false);
                if (existingCategory == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("category idCategory -> " + dto.getIdCategory(), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setCategory(existingCategory);
            }
            // Verify if profil exist
            if (dto.getIdProfil() != null && dto.getIdProfil() > 0) {
                Profil existingProfil = profilRepository.findOne(dto.getIdProfil(), false);
                if (existingProfil == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("profil idProfil -> " + dto.getIdProfil(), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setProfil(existingProfil);
            }
            // Verify if civilite exist
            if (dto.getIdCivilite() != null && dto.getIdCivilite() > 0) {
                Civilite existingCivilite = civiliteRepository.findOne(dto.getIdCivilite(), false);
                if (existingCivilite == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("civilite idCivilite -> " + dto.getIdCivilite(), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setCivilite(existingCivilite);
            }
            if (Utilities.notBlank(dto.getNom())) {
                entityToSave.setNom(dto.getNom());
            }
            if (Utilities.notBlank(dto.getEmailAdresse())) {
                entityToSave.setEmailAdresse(dto.getEmailAdresse());
            }
            if (Utilities.notBlank(dto.getPrenom())) {
                entityToSave.setPrenom(dto.getPrenom());
            }
            if (dto.getIsValidated() != null) {
                entityToSave.setIsValidated(dto.getIsValidated());
            }
            if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
                entityToSave.setCreatedBy(dto.getCreatedBy());
            }
            if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
                entityToSave.setUpdatedBy(dto.getUpdatedBy());
            }
            if (dto.getIsLocked() != null) {
                entityToSave.setIsLocked(dto.getIsLocked());
            }
            if (Utilities.notBlank(dto.getLockedAt())) {
                entityToSave.setLockedAt(dateFormat.parse(dto.getLockedAt()));
            }
            if (dto.getLockedBy() != null && dto.getLockedBy() > 0) {
                entityToSave.setLockedBy(dto.getLockedBy());
            }
            if (Utilities.notBlank(dto.getLogin())) {
                entityToSave.setLogin(dto.getLogin());
            }
            if (dto.getIsSuperAdmin() != null) {
                entityToSave.setIsSuperAdmin(dto.getIsSuperAdmin());
            }
            if (dto.getLocked() != null) {
                entityToSave.setLocked(dto.getLocked());
            }
            if (Utilities.notBlank(dto.getContact())) {
                entityToSave.setContact(dto.getContact());
            }
            if (dto.getIsConnected() != null) {
                entityToSave.setIsConnected(dto.getIsConnected());
            }
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            entityToSave.setUpdatedBy(request.getUser());
            LocalDateTime now = LocalDateTime.now();
            String messageAlerteConnexion = Utilities.messageModificationUtilisateur(now, entityToSave.getNom() + " " + entityToSave.getPrenom(), entityToSave.getLogin(), entityToSave.getProfil() != null ? entityToSave.getProfil().getLibelle() : "PAS D2FINI", entityToSave.getCategory() != null ? entityToSave.getCategory().getLibelle() : "PAS DEFINI", existingUser.getNom() + " " + existingUser.getPrenom(), existingUser.getLogin(), existingUser.getProfil() != null ? existingUser.getProfil().getLibelle() : "PAS DEFINI", existingUser.getCategory() != null ? existingUser.getCategory().getLibelle() : "PAS DEFINI");
            AtionToNotifiable ationToNotifiable = extracted("MODIFIER UN UTILISATEUR",null, existingUser,null,request.getUser(),null, "SUCCESS",
                    messageAlerteConnexion);
            try {
                simSwapServiceProxyService.sendEmail(messageAlerteConnexion, ationToNotifiable);
            } catch (IOException e) {
                log.info(e.getMessage());
            }
            items.add(entityToSave);
        }

        if (!items.isEmpty()) {

            List<User> itemsSaved = null;
            // maj les donnees en base
            itemsSaved = userRepository.saveAll((Iterable<User>) items);
            if (itemsSaved == null) {
                response.setStatus(functionalError.SAVE_FAIL("user", locale));
                response.setHasError(true);
                return response;
            }
            List<UserDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UserTransformer.INSTANCE.toLiteDtos(itemsSaved) : UserTransformer.INSTANCE.toDtos(itemsSaved);

            final int size = itemsSaved.size();
            List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
            itemsDto.parallelStream().forEach(dto -> {
                try {
                    dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
                } catch (Exception e) {
                    listOfError.add(e.getMessage());
                    e.printStackTrace();
                }
            });
            if (Utilities.isNotEmpty(listOfError)) {
                Object[] objArray = listOfError.stream().distinct().toArray();
                throw new RuntimeException(StringUtils.join(objArray, ", "));
            }
            response.setStatus(functionalError.SUCCESS("", locale));
            response.setItems(itemsDto);
            response.setHasError(false);
        }

        log.info("----end update User-----");
        return response;
    }

    /**
     * delete User by using UserDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<UserDto> delete(Request<UserDto> request, Locale locale) {
        log.info("----begin delete User-----");

        Response<UserDto> response = new Response<UserDto>();
        List<User> items = new ArrayList<User>();

        for (UserDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }

            // Verifier si la user existe
            User existingEntity = null;
            existingEntity = userRepository.findOne(dto.getId(), false);
            if (existingEntity == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("user -> " + dto.getId(), locale));
                response.setHasError(true);
                return response;
            }

            // actionUtilisateur
            List<ActionUtilisateur> listOfActionUtilisateur = actionUtilisateurRepository.findByIdUser(existingEntity.getId(), false);
            if (listOfActionUtilisateur != null && !listOfActionUtilisateur.isEmpty()) {
                response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfActionUtilisateur.size() + ")", locale));
                response.setHasError(true);
                return response;
            }
            // numeroStories
            List<NumeroStories> listOfNumeroStories = numeroStoriesRepository.findByCreatedBy(existingEntity.getId(), false);
            if (listOfNumeroStories != null && !listOfNumeroStories.isEmpty()) {
                response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfNumeroStories.size() + ")", locale));
                response.setHasError(true);
                return response;
            }
            // tache
            List<Tache> listOfTache = tacheRepository.findByIdUser(existingEntity.getId(), false);
            if (listOfTache != null && !listOfTache.isEmpty()) {
                response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfTache.size() + ")", locale));
                response.setHasError(true);
                return response;
            }
            // demande
            List<Demande> listOfDemande = demandeRepository.findByIdUser(existingEntity.getId(), false);
            if (listOfDemande != null && !listOfDemande.isEmpty()) {
                response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfDemande.size() + ")", locale));
                response.setHasError(true);
                return response;
            }

            existingEntity.setIsDeleted(true);
            items.add(existingEntity);
        }

        if (!items.isEmpty()) {
            // supprimer les donnees en base
            userRepository.saveAll((Iterable<User>) items);
            response.setStatus(functionalError.SUCCESS("", locale));
            response.setHasError(false);
        }

        log.info("----end delete User-----");
        return response;
    }

    /**
     * get User by using UserDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<UserDto> getByCriteria(Request<UserDto> request, Locale locale) throws Exception {
        log.info("----begin get User-----");

        Response<UserDto> response = new Response<UserDto>();
        List<User> items = userRepository.getByCriteria(request, em, locale);

        if (items != null && !items.isEmpty()) {
            List<UserDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UserTransformer.INSTANCE.toLiteDtos(items) : UserTransformer.INSTANCE.toDtos(items);

            final int size = items.size();
            List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
            itemsDto.parallelStream().forEach(dto -> {
                try {
                    dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
                } catch (Exception e) {
                    listOfError.add(e.getMessage());
                    e.printStackTrace();
                }
            });
            if (Utilities.isNotEmpty(listOfError)) {
                Object[] objArray = listOfError.stream().distinct().toArray();
                throw new RuntimeException(StringUtils.join(objArray, ", "));
            }
            if (Utilities.isNotEmpty(itemsDto)) {
                response.setFilePathDoc(writeFileName(itemsDto));
            }
            response.setItems(itemsDto);
            response.setCount(userRepository.count(request, em, locale));
            response.setHasError(false);
            response.setStatus(functionalError.SUCCESS("", locale));
        } else {
            response.setStatus(functionalError.DATA_EMPTY("user", locale));
            response.setHasError(false);
            return response;
        }

        log.info("----end get User-----");
        return response;
    }

    /**
     * get full UserDto by using User as object.
     *
     * @param dto
     * @param size
     * @param isSimpleLoading
     * @param locale
     * @return
     * @throws Exception
     */
    private UserDto getFullInfos(UserDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
        // put code here
        // put code here
        if (dto.getIdProfil() != null) {
            List<Fonctionnalite> datasFonctionnalite = profilFonctionnaliteRepository.findFonctionnaliteByProfilId(dto.getIdProfil(), false);
            List<FonctionnaliteDto> rootFonctionnalites = new ArrayList<>();
            traitementPourRemonterDesFonctionnalite(datasFonctionnalite, rootFonctionnalites);
            Constant.containsProfil().stream().forEach(arg ->{
                datasFonctionnalite.stream().forEach(fonctionnalite -> {
                    if (fonctionnalite.getLibelle().equalsIgnoreCase(arg)){
                        Profil profil = profilRepository.findByLibelle(arg, Boolean.FALSE);
                        if (profil != null){
                            List<Fonctionnalite> moreDatasFonctionality = profilFonctionnaliteRepository.findFonctionnaliteByProfilId(profil.getId(), false);
                            if (Utilities.isNotEmpty(moreDatasFonctionality))
                                try {
                                    traitementPourRemonterDesFonctionnalite(moreDatasFonctionality, rootFonctionnalites);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }

                        }
                    }
                });
            });
            if (rootFonctionnalites != null && !rootFonctionnalites.isEmpty()) {
                dto.setFonctionnaliteData(rootFonctionnalites);
            }
        }

        if (Utilities.isTrue(isSimpleLoading)) {
            return dto;
        }
        if (size > 1) {
            return dto;
        }

        return dto;
    }

    public List<FonctionnaliteDto>  traitementPourRemonterDesFonctionnalite(List<Fonctionnalite> datasFonctionnalite, List<FonctionnaliteDto> rootFonctionnalites) throws ParseException {
        if (Utilities.isNotEmpty(datasFonctionnalite)) {
            List<FonctionnaliteDto> datasFonctionnaliteDto = FonctionnaliteTransformer.INSTANCE.toDtos(datasFonctionnalite);
            for (FonctionnaliteDto foncDto : datasFonctionnaliteDto) {
                rootFonctionnalites.add(foncDto);
                if (foncDto.getIdParent() != null) {
                    Fonctionnalite fonctionnalite = fonctionnaliteRepository.findOne(foncDto.getIdParent(), Boolean.FALSE);
                    if (fonctionnalite != null) {
                        FonctionnaliteDto parentDto = FonctionnaliteTransformer.INSTANCE.toDto(fonctionnalite);
                        if (!containsFonctionnaliteWithLibelle(rootFonctionnalites, parentDto.getLibelle())) {
                            rootFonctionnalites.add(parentDto);
                        }
                    }
                }
            }
        }

        return rootFonctionnalites;
    }

    public Response<UserDto> custom(Request<UserDto> request, Locale locale) {
        log.info("----begin custom UserDto-----");
        Response<UserDto> response = new Response<UserDto>();

        response.setHasError(false);
        response.setCount(1L);
        response.setItems(Arrays.asList(new UserDto()));
        log.info("----end custom UserDto-----");
        return response;
    }

    @SuppressWarnings("unused")
    public Response<UserDto> connexion(Request<UserDto> request, Locale locale) {
        log.info("----begin login User-----");
        response = new Response<UserDto>();
        UserDto useritemDto = new UserDto();
        try {
            String tenancyCode = null;
            UserDto data = request.getData();
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("login", data.getLogin());
            fieldsToVerify.put("password", data.getPassword());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }

            User item = userRepository.findByLogin(data.getLogin(), Boolean.FALSE);
            if (item == null || Utilities.isTrue(item.getLocked())) {
                response.setStatus(functionalError.DISALLOWED_OPERATION(String.format("%1$s %2$s est vérouillé(e)", item.getLogin(), null), locale));
                response.setHasError(true);
                return response;
            }
            String messageAlerteConnexion = Utilities.alerteConnexion(item.getNom() + " " + item.getPrenom(), item.getLogin(), item.getProfil().getLibelle(), item.getCategory().getLibelle());
            if (messageAlerteConnexion != null){
                AtionToNotifiable ationToNotifiable = extracted("TENTATIVE DE CONNEXION",null, null,null,request.getUser(),null, "SUCCESS",
                        messageAlerteConnexion);
                try {
                    simSwapServiceProxyService.sendEmail(messageAlerteConnexion, ationToNotifiable);
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }

            //simSwapServiceProxyService.sendSms(messageAlerteConnexion);

            List<User> items = Arrays.asList(item);
            if (items != null && !items.isEmpty()) {
                List<UserDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UserTransformer.INSTANCE.toLiteDtos(items) : UserTransformer.INSTANCE.toDtos(items);
                // final String tenantID = CurrentTenantCode.get();
                final String tenantID = tenancyCode;
                final int size = items.size();
                List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
                itemsDto.parallelStream().forEach(dto -> {
                    try {
                        dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
                    } catch (Exception e) {
                        listOfError.add(e.getMessage());
                        e.printStackTrace();
                    }
                });
                if (Utilities.isNotEmpty(listOfError)) {
                    Object[] objArray = listOfError.stream().distinct().toArray();
                    throw new RuntimeException(StringUtils.join(objArray, ", "));
                }
                if (Utilities.isNotEmpty(itemsDto)) {
                    response.setItems(itemsDto);
                }
                UserDto userDto = itemsDto.get(0);
                String token = Utilities.generateCodeOld();
                userDto.setToken(token);
                userDto.setKey(request.getKey());
                userDto.setPassword(null);
                redisService.saveUser(data.getLogin(), itemsDto.get(0), true);
                response.setHasError(false);
                response.setStatus(functionalError.SUCCESS("", locale));
                response.setActionEffectue("TENTATIVE DE CONNEXION");
            } else {
                response.setActionEffectue("TENTATIVE DE CONNEXION");
                response.setStatus(functionalError.DISALLOWED_OPERATION("user", locale));
                response.setHasError(false);
                return response;
            }
            log.info("----end get User-----");
        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
        } catch (Exception e) {
            exceptionUtils.EXCEPTION(response, locale, e);
        } finally {
            if (response.isHasError() && response.getStatus() != null) {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
            }
        }
        response.setStatus(functionalError.SUCCESS("", locale));
        response.setActionEffectue("Connexion utilisateur");
        return response;
    }

    public Response<UserDto> validateInscription(Request<UserDto> request, Locale locale) throws ParseException {
        log.info("----begin create User-----");

        Response<UserDto> response = new Response<UserDto>();
        List<User> items = new ArrayList<User>();
        for (UserDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }

            User existUser = userRepository.findOne(dto.getId(), Boolean.FALSE);
            if (existUser == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("role roleId -> " + dto.getIdProfil(), locale));
                response.setHasError(true);
                return response;
            }

            existUser.setIsValidated(Boolean.TRUE);
            existUser.setLocked(Boolean.FALSE);

            items.add(existUser);
        }

        if (!items.isEmpty()) {
            userRepository.saveAll(items);
            response.setStatus(functionalError.SUCCESS("", locale));
            response.setHasError(Boolean.FALSE);
        }

//		hitoric(request, response);
        response.setStatus(functionalError.SUCCESS("operation effectué", locale));
        response.setActionEffectue("Valider Compte");
        return response;
    }

    public Response<UserDto> isGranted(Integer userId, String functionalityCode, Locale locale) {
        log.info("----begin get isGranted-----");

        response = new Response<UserDto>();

        try {
            User currentUser = userRepository.findOne(userId, false);
            if (currentUser == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("Utilisateur -> " + userId, locale));
                response.setHasError(true);
                return response;
            }
            response.setHasError(false);
            log.info("----end get isGranted-----");

        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
        } catch (Exception e) {
            exceptionUtils.EXCEPTION(response, locale, e);
        } finally {
            if (response.isHasError() && response.getStatus() != null) {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
            }
        }

        return response;
    }

    @SuppressWarnings("unused")
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Response<UserDto> lock(Request<UserDto> request, Locale locale) {
        log.info("----begin lock User-----");
        response = new Response<UserDto>();
        try {
            List<User> items = new ArrayList<User>();
            for (UserDto dto : request.getDatas()) {
                // Definir les parametres obligatoires
                Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
                fieldsToVerify.put("id", dto.getId());
                if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                    response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                    response.setHasError(true);
                    response.setActionEffectue("Bloquer Utilisateur");
                    return response;
                }
                User requester = userRepository.findOne(request.getUser(), false);
                if (requester == null) {
                    response.setStatus(functionalError.DATA_EXIST("User [" + request.getUser() + "] inexistant", locale));
                    response.setHasError(true);
                    response.setActionEffectue("Bloquer Utilisateur");
                    return response;
                }
                if (requester.getLocked() != null && requester.getLocked()) {
                    response.setStatus(functionalError.USER_IS_LOCKED(String.format("%1$b %2$s vérouillé(e)", requester.getNom(), requester.getPrenom()), locale));
                    response.setHasError(true);
                    response.setActionEffectue("Bloquer Utilisateur");
                    return response;
                }
                // Verify if utilisateur to insert do not exist
                User entityToLock = null;
                entityToLock = userRepository.findOne(dto.getId(), false);
                if (entityToLock == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("User -> " + dto.getId(), locale));
                    response.setHasError(true);
                    response.setActionEffectue("Bloquer Utilisateur");
                    return response;
                }
                if (entityToLock.getLocked() != null && entityToLock.getLocked()) {
                    response.setStatus(functionalError.DISALLOWED_OPERATION(String.format("%1$b %2$s est déjà vérouillé(e)", entityToLock.getNom(), entityToLock.getPrenom()), locale));
                    response.setHasError(true);
                    response.setActionEffectue("Bloquer Utilisateur");
                    return response;
                }
                LocalDateTime now = LocalDateTime.now();
                String messageAlerteConnexion = Utilities.messageVerrouillageUtilisateur(now, entityToLock.getNom() + " " + entityToLock.getPrenom(), entityToLock.getLogin(), entityToLock.getProfil().getLibelle(), entityToLock.getCategory().getLibelle(), requester.getNom() + " " + requester.getPrenom(), requester.getLogin(), requester.getProfil().getLibelle(), requester.getCategory().getLibelle());

                AtionToNotifiable ationToNotifiable = extracted("BLOQUER UN UTILISATEUR",null, requester,null,request.getUser(),null, "SUCCESS",
                        messageAlerteConnexion);
                try {
                    simSwapServiceProxyService.sendEmail(messageAlerteConnexion, ationToNotifiable);
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
                entityToLock.setLocked(true);
                items.add(entityToLock);
            }

            if (!items.isEmpty()) {
                List<User> itemsSaved = null;
                // maj les donnees en base
                itemsSaved = userRepository.saveAll((Iterable<User>) items);
                if (itemsSaved == null) {
                    response.setStatus(functionalError.SAVE_FAIL("user", locale));
                    response.setHasError(true);
                    return response;
                }
                List<UserDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UserTransformer.INSTANCE.toLiteDtos(itemsSaved) : UserTransformer.INSTANCE.toDtos(itemsSaved);
                final int size = itemsSaved.size();
                List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
                itemsDto.parallelStream().forEach(dto -> {
                    try {
                        dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
                    } catch (Exception e) {
                        listOfError.add(e.getMessage());
                        e.printStackTrace();
                    }
                });
                if (Utilities.isNotEmpty(listOfError)) {
                    Object[] objArray = listOfError.stream().distinct().toArray();
                    throw new RuntimeException(StringUtils.join(objArray, ", "));
                }
                response.setActionEffectue("Bloquer Utilisateur");
                response.setItems(itemsDto);
                response.setStatus(functionalError.SUCCESS("compte utilisateur vérrouillé", locale));
                response.setHasError(false);
            }

//			hitoric(request, response);

            log.info("----end lock User-----");
        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
        } catch (Exception e) {
            exceptionUtils.EXCEPTION(response, locale, e);
        } finally {
            if (response.isHasError() && response.getStatus() != null) {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
            }
        }
        response.setStatus(functionalError.SUCCESS("", locale));
        response.setActionEffectue("Bloquer utilisateur");
        return response;
    }

    /**
     * unlock User by using UserDto as object.
     *
     * @param request
     * @return response
     */
    @SuppressWarnings("unused")
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Response<UserDto> unlock(Request<UserDto> request, Locale locale) {
        log.info("----begin unlock User-----");
        response = new Response<>();
        try {
            List<User> items = new ArrayList<User>();
            for (UserDto dto : request.getDatas()) {
                // Definir les parametres obligatoires
                Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
                fieldsToVerify.put("id", dto.getId());
                if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                    response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                    response.setHasError(true);
                    return response;
                }
                User requester = userRepository.findOne(request.getUser(), false);
                if (requester == null) {
                    response.setStatus(functionalError.DATA_EXIST("utilisateur [" + request.getUser() + "] inexistant", locale));
                    response.setHasError(true);
                    return response;
                }
                if (requester.getLocked() != null && requester.getLocked()) {
                    response.setStatus(functionalError.USER_IS_LOCKED(String.format("%1$b %2$s vérouillé(e)", requester.getNom(), requester.getPrenom()), locale));
                    response.setHasError(true);
                    return response;
                }
                // Verify if utilisateur to insert do not exist
                User entityToLock = null;
                entityToLock = userRepository.findOne(dto.getId(), false);
                if (entityToLock == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("utilisateur -> " + dto.getId(), locale));
                    response.setHasError(true);
                    return response;
                }
                if (entityToLock.getLocked() != null && !entityToLock.getLocked()) {
                    response.setStatus(functionalError.USER_IS_LOCKED(String.format("%1$b %2$s est déjà dévérouillé(e)", entityToLock.getNom(), entityToLock.getPrenom()), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToLock.setLocked(false);
                LocalDateTime now = LocalDateTime.now();
                String messageAlerteConnexion = Utilities.messageDeverrouillageUtilisateur(now, entityToLock.getNom() + " " + entityToLock.getPrenom(), entityToLock.getLogin(), entityToLock.getProfil().getLibelle(), entityToLock.getCategory().getLibelle(), requester.getNom() + " " + requester.getPrenom(), requester.getLogin(), requester.getProfil().getLibelle(), requester.getCategory().getLibelle());
                AtionToNotifiable ationToNotifiable = extracted("DÉBLOQUER UN UTILISATEUR",null, requester,null,request.getUser(),null, "SUCCESS",
                        messageAlerteConnexion);
                try {
                    simSwapServiceProxyService.sendEmail(messageAlerteConnexion, ationToNotifiable);
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
                items.add(entityToLock);
            }
            if (!items.isEmpty()) {
                List<User> itemsSaved = null;
                // maj les donnees en base
                itemsSaved = userRepository.saveAll((Iterable<User>) items);
                if (itemsSaved == null) {
                    response.setStatus(functionalError.SAVE_FAIL("user", locale));
                    response.setHasError(true);
                    return response;
                }
                List<UserDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UserTransformer.INSTANCE.toLiteDtos(itemsSaved) : UserTransformer.INSTANCE.toDtos(itemsSaved);

                final int size = itemsSaved.size();
                List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
                itemsDto.parallelStream().forEach(dto -> {
                    try {
                        dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
                    } catch (Exception e) {
                        listOfError.add(e.getMessage());
                        e.printStackTrace();
                    }
                });
                if (Utilities.isNotEmpty(listOfError)) {
                    Object[] objArray = listOfError.stream().distinct().toArray();
                    throw new RuntimeException(StringUtils.join(objArray, ", "));
                }
                response.setActionEffectue("Débloquer Utilisateur");
                response.setItems(itemsDto);
                response.setStatus(functionalError.SUCCESS("compte utilisateur déverrouillé", locale));
                response.setHasError(false);
            }
//			hitoric(request, response);
            log.info("----end unlock User-----");
        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
        } catch (Exception e) {
            exceptionUtils.EXCEPTION(response, locale, e);
        } finally {
            if (response.isHasError() && response.getStatus() != null) {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
            }
        }
        response.setActionEffectue("Débloquer Utilisateur");
        response.setStatus(functionalError.SUCCESS("", locale));
        response.setActionEffectue("Debloquer utilisateur");
        return response;
    }

    public Response<KokolaDto> smsOrange(Request<UserDto> request, Locale locale) throws IOException {
        Response<KokolaDto> response = new Response<KokolaDto>();
        KokolaDto kokolaDtoToSend = new KokolaDto();
        UserDto kokolaDto = request.getData();
        Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
        fieldsToVerify.put("recipient", request.getData().getRecipient());
        fieldsToVerify.put("content", request.getData().getContent());
        if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
            response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            response.setHasError(Boolean.TRUE);
            return response;
        }
        URL url = new URL(paramsUtils.getUrlSendSms() + kokolaDto.getRecipient() + "&SOA=SIM SWAP&Flags=0&Content=" + kokolaDto.getContent());
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream() : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String responseSendSms = s.hasNext() ? s.next() : "";
        System.out.println(responseSendSms);
        Map<String, Object> map = new Gson().fromJson(responseSendSms, new TypeToken<Map<String, Object>>() {
        }.getType());
        kokolaDtoToSend.setSendSmsResponse(map);
        response.setItem(kokolaDtoToSend);
        return response;
    }


    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Response<UserDto> connexionLdap(Request<UserDto> request, Locale locale) {
        Response<UserDto> response = new Response<>();
        try {
            UserDto userDto = new UserDto();
            log.info("----begin connexionLdap Utilisateur-----");
            UserDto dto = request.getData();
            // champs obligatoires
            HashMap<String, Object> fieldsToVerify = new HashMap<>();
            fieldsToVerify.put("login", dto.getLogin());
            fieldsToVerify.put("password", dto.getPassword());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }
            Map<String, Object> req = new HashMap<>();
            req.put("username", dto.getLogin());
            req.put("password", dto.getPassword());
            Map<String, Object> map = CallAPi(req, paramsUtils.getUrlAuthenticationLdapTest() + "/authenticate?app-id=" + paramsUtils.getAppId() + "&request-id=" + UUID.randomUUID());
            if (map == null || map.get("data") == null || map.get("status").equals(401)) {
                response.setStatus(functionalError.LOGIN_FAIL(locale));
                response.setHasError(Boolean.TRUE);
                return response;
            }
            User userToConnect;
            userToConnect = userRepository.findByLogin(dto.getLogin(), false);
            Validate.EntityNotExiste(userToConnect);
            Validate.EntityNotExiste(userToConnect.getProfil());
            System.out.println("retour api user ------------------------------->" + map);
            Map<String, Object> orci;
            orci = (Map<String, Object>) map.get("data");
            List<String> groups = (List<String>) orci.get("groups");
            Utilities.validateList(groups);
            String profilLibelle = String.valueOf(
                    groups.stream()
                            .filter(arg -> arg.equalsIgnoreCase(userToConnect.getProfil().getCorail()))
                            .findFirst() // Récupère le premier élément correspondant à la condition
                            .orElse(null) // Si aucun élément ne correspond, retourne null
            );
            log.info("corail data " +  profilLibelle);
            if (!Utilities.notBlank(profilLibelle) || profilLibelle.equalsIgnoreCase("null") || profilLibelle == null){
                response.setRaison("pas autorisé, veuillez contacter un admin");
                response.setStatus(functionalError.DISALLOWED_OPERATION("", locale));
                response.setHasError(true);
                return response;
            }
            if (userDto.getLocked() != null && userDto.getLocked().equals(Boolean.TRUE)) {
                response.setStatus(functionalError.USER_IS_LOCKED("compte a été verrouillé", locale));
                response.setHasError(Boolean.TRUE);
                return response;
            }
            userDto = getFullInfos(UserTransformer.INSTANCE.toDto(userToConnect), 1, Boolean.FALSE, locale);
            response.setItems(Arrays.asList(userDto));
            String token = Utilities.generateCodeOld();
            userDto.setToken(token);
            userDto.setKey(request.getKey());
            userDto.setPassword(null);
            redisService.saveUser(dto.getLogin(), userDto, true);
            response.setStatus(functionalError.SUCCESS("UTILISATEUR CONNECTÉ", locale));
            response.setHasError(false);
            log.info("----end connexionLdap Utilisateur-----");
        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response.isHasError() && response.getStatus() != null) {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
            }
        }
        return response;
    }

    public Map<String, Object> CallAPi(Map<String, Object> data, String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String queryString = new Gson().toJson(data);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), queryString);
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("POST", body).addHeader("Content-Type", "application/json").build();
        okhttp3.Response resp = client.newCall(request).execute();
        String outputString = resp.body().string();
        Map<String, Object> map = new Gson().fromJson(outputString, new TypeToken<Map<String, Object>>() {
        }.getType());
        resp.close();
        return map;
    }

    private boolean containsFonctionnaliteWithLibelle(List<FonctionnaliteDto> fonctionnalites, String libelle) {
        for (FonctionnaliteDto foncDto : fonctionnalites) {
            if (foncDto.getLibelle() != null && foncDto.getLibelle().equals(libelle)) {
                return true;
            }
        }
        return false;
    }

    public Response<UserDto> getUserSessionTTL(Request<UserDto> request, Locale locale) {
        response = new Response<>();
        String TokenUser = request.getSessionUser();
        try {
            log.info("----begin  getUserSessionTTL-----");
            if (!Utilities.notBlank(TokenUser)) {
                response.setStatus(functionalError.NO_USER("", locale));
                response.setHasError(true);
                return response;
            }
            Long ccf = redisService.getTTL(TokenUser);
            if (ccf < 0) {
                ccf = (long) 0;
            }
            if (ccf == null || ccf == 0) {
                response.setStatus(functionalError.SESSION_EXPIRED("", locale));
                response.setHasError(true);
                return response;
            }
            response.setSessionUserExpire(ccf);
            response.setStatus(functionalError.SUCCESS("", locale));
            log.info("----end getUserSessionTTL-----");
        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response.isHasError() && response.getStatus() != null) {
                log.info(String.format("Erreur | code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
            }
        }

        return response;
    }

    public Response<Map<String, Object>> deleteKeys(Locale locale) {
        Response<Map<String, Object>> response = new Response<>();
        try {
            Set<String> keys = redisService.getKeys("*node-");
            if (keys != null && !keys.isEmpty()) {
                keys.stream().forEach(item -> {
                    redisService.deleteUser(item);
                    log.info("delete key : " + item);
                });
                response.setStatus(functionalError.SUCCESS("", locale));
                response.setHasError(true);
            } else {
                response.setStatus(functionalError.DATA_EMPTY("", locale));
                response.setHasError(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            slf4jLogger.warn("getValue : " + e.getCause(), e.getMessage());
        }
        return response;
    }

    public Response<UserDto> getSessionUser(Request<UserDto> request, Locale locale) {
        response = new Response<UserDto>();
        try {
            UserDto dto = request.getData();
            // champs obligatoires
            HashMap<String, Object> fieldsToVerify = new HashMap<>();
            fieldsToVerify.put("login", dto.getLogin());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }
            UserDto userDto = redisService.getUser(dto.getLogin());
            if (userDto != null) {
                response.setItems(Arrays.asList(userDto));
                response.setStatus(functionalError.SUCCESS("Utilisateur connecté", locale));
                response.setHasError(false);
            } else {
                response.setStatus(functionalError.DISALLOWED_OPERATION("Token indisponible", locale));
                response.setHasError(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            slf4jLogger.warn("getValue : " + e.getCause(), e.getMessage());
        }
        return response;
    }

    @SuppressWarnings({"unused", "null"})
    public Response<UserDto> getActiveSession(Locale locale) {
        response = new Response<UserDto>();
        try {
            String regex = "*";
            Set<String> getAllKey = redisService.getKeys(regex);
            // List<String> getAllKey = redisUser.getKeys();
            List<String> keys = new ArrayList<String>();
            List<UserDto> datas = new ArrayList<UserDto>();
            List<User> datasx = new ArrayList<User>();
            if (getAllKey != null && !getAllKey.isEmpty()) {
                getAllKey.stream().forEach(k -> {
                    if (Utilities.notBlank(k) && k.length() == 8) {
                        keys.add(k);
                    }
                });
                if (keys != null && !keys.isEmpty()) {
                    keys.stream().forEach(item -> {
                        UserDto dto = this.redisService.getUser(item);
                        if (dto != null) {
                            datas.add(dto);
                        }
                    });
                }
                if (datas != null && !datas.isEmpty()) {
                    response.setItems(datas);
                    response.setHasError(Boolean.FALSE);
                    response.setStatus(functionalError.SUCCESS("", locale));
                    response.setCount((long) datas.size());
                } else {
                    response.setItems(new ArrayList<>());
                    response.setHasError(Boolean.FALSE);
                    response.setStatus(functionalError.SUCCESS("Liste vide", locale));
                }
            } else {
                response.setItems(new ArrayList<>());
                response.setHasError(Boolean.FALSE);
                response.setStatus(functionalError.SUCCESS("Liste vide", locale));
            }

        } catch (Exception e) {
            e.printStackTrace();
            slf4jLogger.warn("getValue : " + e.getCause(), e.getMessage());

        }
        return response;
    }

    public Response<UserDto> getPublicKey(Locale locale) {
        slf4jLogger.info("----begin getPublicKey-----");
        response = new Response<>();
        Rsa rsa = new Rsa();
        try {
            RSAPublicKey publicKey = null;
            publicKey = (RSAPublicKey) rsa.getPublicKey();
            if (publicKey != null) {
                response.setHasError(false);
                response.setModulus(publicKey.getModulus().toString(16));
                response.setExponent(publicKey.getPublicExponent().toString(16));
                response.setHasError(Boolean.FALSE);
            } else {
                response.setStatus(functionalError.DATA_EMPTY("", locale));
                response.setHasError(true);
                return response;
            }

            slf4jLogger.info("----end getPublicKey-----");
        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
        } catch (Exception e) {
            exceptionUtils.EXCEPTION(response, locale, e);
        } finally {
            if (response.isHasError() && response.getStatus() != null) {
                slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
                throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
            }
        }
        return response;
    }

    public String writeFileName(List<UserDto> dataActionLite) throws Exception {
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        ClassPathResource templateClassPathResource = new ClassPathResource("templates/Liste_KPI_USER_Exporte.xlsx");
        InputStream inputStreamFile = templateClassPathResource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStreamFile);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Cell cell = null;
        Row row = null;
        int rowIndex = 1;
        for (UserDto user : dataActionLite) {
            // Renseigner les ids
            log.info("--row--" + rowIndex);
            row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
            cell = getCell(row, 0);
            cell.setCellValue(user.getCiviliteLibelle());
            cell = getCell(row, 1);
            cell.setCellValue(user.getNom());
            cell = getCell(row, 2);
            cell.setCellValue(user.getPrenom());
            cell = getCell(row, 3);
            cell.setCellValue(user.getContact() + "/" + user.getEmailAdresse());
            cell = getCell(row, 4);
            cell.setCellValue(user.getProfilLibelle());
            cell = getCell(row, 5);
            cell.setCellValue(user.getLogin());
            cell = getCell(row, 6);
            cell.setCellValue(user.getIsLocked() == Boolean.FALSE ? "ACTIF" : "BLOQUÉ");
            cell = getCell(row, 7);
            cell.setCellValue(user.getLogin());
            cell = getCell(row, 7);
            cell.setCellValue(user.getCreatedAt());
            cell = getCell(row, 8);
            cell.setCellValue(user.getCategoryCode());
            cell = getCell(row, 9);
            cell.setCellValue(user.getFirstConnection());
            cell = getCell(row, 10);
            cell.setCellValue(user.getLastConnection());

            rowIndex++;
        }
        // Ajuster les colonnes
        for (int i = 0; i <= Utilities.getColumnIndex("I"); i++) {
            sheet.autoSizeColumn(i);
        }
        inputStreamFile.close();
        String fileName = Utilities.saveFile("LIST_USERS_" + sdfFileName.format(new Date()), "xlsx", workbook, paramsUtils);

        return Utilities.getFileUrlLink(fileName, paramsUtils);
    }

    public static Cell getCell(Row row, Integer index) {
        return (row.getCell(index) == null) ? row.createCell(index) : row.getCell(index);
    }

    private AtionToNotifiable extracted(String libelle, User userDemande, User userValide, Status status, Integer createdBy, String number, String statusAction, String messageAlerte) {
        ActionSimswap actionParametrable = actionSimswapRepository.findByLibelle(libelle,
                Boolean.FALSE);
        AtionToNotifiable ationToNotifiable = new AtionToNotifiable();
        ationToNotifiable.setUser(userDemande);
        ationToNotifiable.setUser2(userValide);
        ationToNotifiable.setStatus(status);
        ationToNotifiable.setCreatedBy(createdBy);
        ationToNotifiable.setIsDeleted(Boolean.FALSE);
        ationToNotifiable.setCreatedAt(Utilities.getCurrentDate());
        ationToNotifiable.setIsEnMasse(Boolean.TRUE);
        ationToNotifiable.setIsNotify(Boolean.FALSE);
        ationToNotifiable.setNumero(number);
        ationToNotifiable.setStatutAction(statusAction);
        ationToNotifiable.setActionSimswap(actionParametrable);
        ationToNotifiable.setMessage(messageAlerte);
        ationToNotifiable.setStatus(status);
       return  ationToNotifiableRepository.save(ationToNotifiable);
    }
}
