
/*
 * Java business for entity table demande
 * Created on 2022-10-04 ( Time 11:23:37 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.business;

import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;
import ci.smile.simswaporange.proxy.customizeclass.functions.ObjectUtilities;
import ci.smile.simswaporange.proxy.response.ApiResponse;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDtos;
import ci.smile.simswaporange.proxy.response.SensitiveNumberDto;
import ci.smile.simswaporange.proxy.service.SimSwapServiceProxyService;
import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.contract.IBasicBusiness;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.ActionUtilisateurDto;
import ci.smile.simswaporange.utils.dto.DemandeDto;
import ci.smile.simswaporange.utils.dto.customize._AppRequestDto;
import ci.smile.simswaporange.utils.dto.transformer.ActionEnMasseTransformer;
import ci.smile.simswaporange.utils.dto.transformer.DemandeTransformer;
import ci.smile.simswaporange.utils.enums.StatusApiEnum;
import ci.smile.simswaporange.utils.enums.TypeDemandeEnum;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.stream.Collectors;
import java.time.LocalTime;

/**
 * BUSINESS for table "demande"
 *
 * @author Geo
 */
@Log
@Component
@EnableScheduling
@Transactional
public class DemandeBusiness implements IBasicBusiness <Request <DemandeDto>, Response <DemandeDto>>{
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ParamsUtils paramsUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectUtilities objectUtilities;
    @Autowired
    private TacheRepository tacheRepository;
    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TypeNumeroRepository typeNumeroRepository;
    @Autowired
    private TypeDemandeRepository typeDemandeRepository;
    @Autowired
    private ParametrageRepository parametrageRepository;
    @Autowired
    private ActionSimswapRepository actionSimswapRepository;
    @Autowired
    private NumeroStoriesRepository numeroStoriesRepository;
    @Autowired
    private ActionEnMasseRepository actionEnMasseRepository;
    @Autowired
    private TypeParametrageRepository typeParametrageRepository;
    @Autowired
    private ActionUtilisateurBusiness actionUtilisateurBusiness;
    @Autowired
    private SimSwapServiceProxyService simSwapServiceProxyService;
    @Autowired
    private AtionToNotifiableRepository ationToNotifiableRepository;
    @Autowired
    private ActionUtilisateurRepository actionUtilisateurRepository;
    @Autowired
    private ActionParametrableRepository actionParametrableRepository;


    //	@Autowired
//	private NumeroBscsRepository numeroBscsRepository;


    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dateTimeFormat;
    private SimpleDateFormat dateTimeFormatAbsolute;
    private Context context;
    private final String SENDER = "SERVICE - SIMSWAP NOTIFICATION";
    private final String ENTETE = "SIMSWAP - NOTIFICATION";
    private final String TITRE = "NOTIFICATION SIMSWAP";

    public DemandeBusiness(){
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateTimeFormatAbsolute = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public Response <DemandeDto> create(Request <DemandeDto> request,Locale locale) throws ParseException, IOException{
        log.info("----begin create Demande-----");
        Response <DemandeDto> response = new Response <>();
        List <Demande> items = new ArrayList <>();
        List <Tache> itemsTache = new ArrayList <>();
        Status existingStatus = null;
        List <String> numeros = new ArrayList <>();
        for(DemandeDto dto: request.getDatas()){
            // Definir les parametres obligatoires
            Map <String, java.lang.Object> fieldsToVerify = new HashMap <>();
            //fieldsToVerify.put("idStatus", dto.getIdStatus());
            fieldsToVerify.put("numero",dto.getNumero());
            fieldsToVerify.put("offerName",dto.getOfferName());
            fieldsToVerify.put("motif",dto.getMotif());
            fieldsToVerify.put("isTransfert",dto.getIsTransfert());
            fieldsToVerify.put("idTypeCategory",dto.getIdTypeCategory());
            fieldsToVerify.put("typeNumeroId",dto.getTypeNumeroId());
            if(!Validate.RequiredValue(fieldsToVerify).isGood()){
                response.setStatus(functionalError.FIELD_EMPTY("Veuillez renseigner le "+Validate.getValidate().getField(),locale));
                response.setHasError(true);
                return response;
            }
            numeros.add(dto.getNumero());
            User existingUser = null;
            if(request.getUser()!=null&&request.getUser()>0){
                existingUser = userRepository.findOne(request.getUser(),false);
                Validate.EntityNotExiste(existingUser);
                Validate.EntityNotExiste(existingUser.getCategory());
            }
            Demande existingEntity = null;
            existingEntity = demandeRepository.findOne(dto.getId(),false);
            Validate.EntityExiste(existingEntity);
            TypeNumero typeNumero = typeNumeroRepository.findOne(dto.getTypeNumeroId(),Boolean.FALSE);
            Validate.EntityNotExiste(typeNumero);
            if(dto.getIdStatus()!=null&&dto.getIdStatus()>0){
                existingStatus = statusRepository.findOne(dto.getIdStatus(),false);
                Validate.EntityNotExiste(existingStatus);
            }
            Category category = categoryRepository.findOne(dto.getIdTypeCategory(),Boolean.FALSE);
            Validate.EntityNotExiste(category);
            TypeDemande existingTypeDemande = typeDemandeRepository.findByCode(TypeDemandeEnum.INITIALISER_CODE,false);
            Demande demandeBySatus = demandeRepository.findDemandeByStatusAndType(typeNumero.getId(),dto.getNumero(),existingTypeDemande.getId(),category.getId(),dto.getIsTransfert(),Boolean.FALSE);
            if(demandeBySatus!=null&&dto.getIsMasse()==null){
                response.setStatus(functionalError.DATA_EXIST("demande deja effectué pour ->"+demandeBySatus.getNumero(),locale));
                response.setHasError(true);
                return response;
            }
            dto.setOfferName(dto.getOfferName());
            dto.setStatusBscs(dto.getStatusBscs());
            Demande entityToSave;
            entityToSave = DemandeTransformer.INSTANCE.toEntity(dto,category,existingStatus,existingUser,typeNumero,existingTypeDemande,null);
            entityToSave.setTypeDemande(existingTypeDemande);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setMotif(dto.getMotif());
            entityToSave.setIsValidated(Boolean.FALSE);
            entityToSave.setIsDeleted(false);
            entityToSave.setCreatedBy(request.getUser());
            if(category.getId()==StatusApiEnum.TELCO_CATEGORY){
                List <ActionUtilisateur> categorieOmci = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(),StatusApiEnum.OMCI_CATEGORY,AES.encrypt(dto.getNumero(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                if(Utilities.isNotEmpty(categorieOmci)){
                    entityToSave.setIsValidated(Boolean.TRUE);
                }
                if(dto.getIsTransfert()==Boolean.TRUE){
                    entityToSave.setCategory(objectUtilities.categoriePair().getCategorieOmci());
                }
            }
            if(category.getId()==StatusApiEnum.OMCI_CATEGORY){
                List <ActionUtilisateur> categorieTelco = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(),StatusApiEnum.TELCO_CATEGORY,AES.encrypt(dto.getNumero(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                if(Utilities.isNotEmpty(categorieTelco)){
                    entityToSave.setIsValidated(Boolean.TRUE);
                }
                if(dto.getIsTransfert()==Boolean.TRUE){
                    entityToSave.setCategory(objectUtilities.categoriePair().getCategorieTelco());
                }
            }
            /*if (existingUser.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.ADMIN_CODE)) {
                if (dto.getOfferName().equalsIgnoreCase("Orange money PDV") || dto.getOfferName().equalsIgnoreCase("ORANGE ZEBRA STK")) {
                    entityToSave.setCategory(categoryOmci);
                    List<ActionUtilisateur> categorieTelco = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(), StatusApiEnum.TELCO_CATEGORY, AES.encrypt(dto.getNumero(), StatusApiEnum.SECRET_KEY), Boolean.FALSE);
                    if (Utilities.isNotEmpty(categorieTelco)) {
                        entityToSave.setIsValidated(Boolean.TRUE);
                    }
                } else {
                    entityToSave.setCategory(categoryTelco);
                    List<ActionUtilisateur> categorieOmci = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(), StatusApiEnum.OMCI_CATEGORY, AES.encrypt(dto.getNumero(), StatusApiEnum.SECRET_KEY), Boolean.FALSE);
                    if (Utilities.isNotEmpty(categorieOmci)) {
                        entityToSave.setIsValidated(Boolean.TRUE);
                    }
                }

            }*/


            String messagedemandeGenerateSimswapAlert = null;
            if(dto.getIsTransfert()==Boolean.FALSE){
                LocalDateTime now = LocalDateTime.now();
                messagedemandeGenerateSimswapAlert = Utilities.demandeGenerateSimswapAlert(now,existingUser.getNom()+" "+existingUser.getPrenom(),dto.getNumero(),existingStatus.getCode(),"SUCCESS");
                AtionToNotifiable ationToNotifiable = extracted("CRÉATION DEMANDE",existingUser,null,existingStatus,request.getUser(),dto.getNumero(),"SUCCESS",
                        messagedemandeGenerateSimswapAlert);
                try{
                    simSwapServiceProxyService.sendEmail(messagedemandeGenerateSimswapAlert,ationToNotifiable);
                }catch(IOException e){
                    log.info(e.getMessage());
                }
            }else{
                List <ActionUtilisateur> foundStatus = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(),existingUser.getCategory().getId(),AES.encrypt(dto.getNumero(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                if(Utilities.isNotEmpty(foundStatus)){
                    ActionUtilisateur actionUtilisateur = foundStatus.get(0);
                    entityToSave.setStatus(actionUtilisateur.getStatus());
                }
                AtionToNotifiable ationToNotifiable = extracted("CRÉATION DEMANDE",existingUser,null,existingStatus,request.getUser(),dto.getNumero(),"SUCCESS",
                        messagedemandeGenerateSimswapAlert);
                LocalDateTime now = LocalDateTime.now();
                messagedemandeGenerateSimswapAlert = Utilities.messageTransfertCategorie(now,existingUser.getNom()+" "+existingUser.getPrenom(),existingUser.getLogin(),existingUser.getProfil().getLibelle(),dto.getNumero(),category.getCode(),entityToSave.getStatus().getCode());
                try{
                    simSwapServiceProxyService.sendEmail(messagedemandeGenerateSimswapAlert,ationToNotifiable);
                }catch(IOException e){
                    log.info(e.getMessage());
                }

            }
            items.add(entityToSave);
        }
        if(!items.isEmpty()){
            List <Demande> itemsSaved;
            // inserer les donnees en base de donnees
            itemsSaved = demandeRepository.saveAll(items);
            if(itemsSaved==null){
                response.setStatus(functionalError.SAVE_FAIL("demande",locale));
                response.setHasError(true);
                return response;
            }
            List <Tache> itemsTacheSaved = null;
            itemsTacheSaved = tacheRepository.saveAll(itemsTache);
            if(itemsTacheSaved==null){
                response.setStatus(functionalError.SAVE_FAIL("Tache",locale));
                response.setHasError(true);
                return response;
            }
            List <DemandeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))?DemandeTransformer.INSTANCE.toLiteDtos(itemsSaved):DemandeTransformer.INSTANCE.toDtos(itemsSaved);
            final int size = itemsSaved.size();
            List <String> listOfError = Collections.synchronizedList(new ArrayList <>());
            itemsDto.parallelStream().forEach(dto->{
                try{
                    dto = getFullInfos(dto,size,request.getIsSimpleLoading(),locale);
                }catch(Exception e){
                    listOfError.add(e.getMessage());
                    e.printStackTrace();
                }
            });
            if(Utilities.isNotEmpty(listOfError)){
                Object[] objArray = listOfError.stream().distinct().toArray();
                throw new RuntimeException(StringUtils.join(objArray,", "));
            }
            response.setStatus(functionalError.SUCCESS(""+numeros,locale));
            response.setItems(itemsDto);
            response.setHasError(false);
            response.setActionEffectue("Creer une demande");
        }
        log.info("----end create Demande-----");
        return response;
    }

    /**
     * update Demande by using DemandeDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response <DemandeDto> update(Request <DemandeDto> request,Locale locale) throws ParseException{
        log.info("----begin update Demande-----");

        Response <DemandeDto> response = new Response <DemandeDto>();
        List <Demande> items = new ArrayList <Demande>();

        for(DemandeDto dto: request.getDatas()){
            // Definir les parametres obligatoires
            Map <String, java.lang.Object> fieldsToVerify = new HashMap <String, java.lang.Object>();
            fieldsToVerify.put("id",dto.getId());
            if(!Validate.RequiredValue(fieldsToVerify).isGood()){
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
                response.setHasError(true);
                return response;
            }
            // Verifier si la demande existe
            Demande entityToSave = null;
            entityToSave = demandeRepository.findOne(dto.getId(),false);
            if(entityToSave==null){
                response.setStatus(functionalError.DATA_NOT_EXIST("demande id -> "+dto.getId(),locale));
                response.setHasError(true);
                return response;
            }
            // Verify if status exist
            if(dto.getIdStatus()!=null&&dto.getIdStatus()>0){
                Status existingStatus = statusRepository.findOne(dto.getIdStatus(),false);
                if(existingStatus==null){
                    response.setStatus(functionalError.DATA_NOT_EXIST("status idStatus -> "+dto.getIdStatus(),locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setStatus(existingStatus);
            }
            // Verify if user exist
            if(dto.getIdUser()!=null&&dto.getIdUser()>0){
                User existingUser = userRepository.findOne(dto.getIdUser(),false);
                if(existingUser==null){
                    response.setStatus(functionalError.DATA_NOT_EXIST("user idUser -> "+dto.getIdUser(),locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setUser(existingUser);
            }
            // Verify if typeDemande exist
            if(dto.getIdTypeDemande()!=null&&dto.getIdTypeDemande()>0){
                TypeDemande existingTypeDemande = typeDemandeRepository.findOne(dto.getIdTypeDemande(),false);
                if(existingTypeDemande==null){
                    response.setStatus(functionalError.DATA_NOT_EXIST("typeDemande idTypeDemande -> "+dto.getIdTypeDemande(),locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setTypeDemande(existingTypeDemande);
            }
            if(dto.getTypeNumeroId()!=null&&dto.getTypeNumeroId()>0){
                TypeNumero existingTypeNumero = typeNumeroRepository.findOne(dto.getTypeNumeroId(),false);
                if(existingTypeNumero==null){
                    response.setStatus(functionalError.DATA_NOT_EXIST("typeNumero idtypeNumero -> "+dto.getTypeNumeroId(),locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setTypeNumero(existingTypeNumero);
            }
            if(Utilities.notBlank(dto.getNumero())){
                entityToSave.setNumero(dto.getNumero());
            }
            if(dto.getCreatedBy()!=null&&dto.getCreatedBy()>0){
                entityToSave.setCreatedBy(dto.getCreatedBy());
            }
            if(dto.getUpdatedByOmci()!=null&&dto.getUpdatedByOmci()>0){
                entityToSave.setUpdatedByOmci(dto.getUpdatedByOmci());
            }
            if(dto.getUpdatedByTelco()!=null&&dto.getUpdatedByTelco()>0){
                entityToSave.setUpdatedByTelco(dto.getUpdatedByTelco());
            }
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            items.add(entityToSave);
        }

        if(!items.isEmpty()){
            List <Demande> itemsSaved = null;
            // maj les donnees en base
            itemsSaved = demandeRepository.saveAll((Iterable <Demande>) items);
            if(itemsSaved==null){
                response.setStatus(functionalError.SAVE_FAIL("demande",locale));
                response.setHasError(true);
                return response;
            }
            List <DemandeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))?DemandeTransformer.INSTANCE.toLiteDtos(itemsSaved):DemandeTransformer.INSTANCE.toDtos(itemsSaved);
            final int size = itemsSaved.size();
            List <String> listOfError = Collections.synchronizedList(new ArrayList <String>());
            itemsDto.parallelStream().forEach(dto->{
                try{
                    dto = getFullInfos(dto,size,request.getIsSimpleLoading(),locale);
                }catch(Exception e){
                    listOfError.add(e.getMessage());
                    e.printStackTrace();
                }
            });
            if(Utilities.isNotEmpty(listOfError)){
                Object[] objArray = listOfError.stream().distinct().toArray();
                throw new RuntimeException(StringUtils.join(objArray,", "));
            }
            response.setStatus(functionalError.SUCCESS("",locale));
            response.setItems(itemsDto);
            response.setHasError(false);
        }

        log.info("----end update Demande-----");
        return response;
    }

    /**
     * delete Demande by using DemandeDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response <DemandeDto> delete(Request <DemandeDto> request,Locale locale){
        log.info("----begin delete Demande-----");
        Response <DemandeDto> response = new Response <DemandeDto>();
        List <Demande> items = new ArrayList <Demande>();
        for(DemandeDto dto: request.getDatas()){
            // Definir les parametres obligatoires
            Map <String, java.lang.Object> fieldsToVerify = new HashMap <String, java.lang.Object>();
            fieldsToVerify.put("id",dto.getId());
            if(!Validate.RequiredValue(fieldsToVerify).isGood()){
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
                response.setHasError(true);
                return response;
            }
            // Verifier si la demande existe
            Demande existingEntity = null;
            existingEntity = demandeRepository.findOne(dto.getId(),false);
            if(existingEntity==null){
                response.setStatus(functionalError.DATA_NOT_EXIST("demande -> "+dto.getId(),locale));
                response.setHasError(true);
                return response;
            }
            List <Tache> listOfTache = tacheRepository.findByIdDemande(existingEntity.getId(),false);
            if(listOfTache!=null&&!listOfTache.isEmpty()){
                response.setStatus(functionalError.DATA_NOT_DELETABLE("("+listOfTache.size()+")",locale));
                response.setHasError(true);
                return response;
            }
            existingEntity.setIsDeleted(true);
            items.add(existingEntity);
        }
        if(!items.isEmpty()){
            // supprimer les donnees en base
            demandeRepository.saveAll((Iterable <Demande>) items);
            response.setStatus(functionalError.SUCCESS("",locale));
            response.setHasError(false);
        }

        log.info("----end delete Demande-----");
        return response;
    }

    /**
     * get Demande by using DemandeDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response <DemandeDto> getByCriteria(Request <DemandeDto> request,Locale locale) throws Exception{
        log.info("----begin get Demande-----");
        Response <DemandeDto> response = new Response <DemandeDto>();
        List <Demande> items = demandeRepository.getByCriteria(request,em,locale);
        User user = userRepository.findOne(request.getUser(),Boolean.FALSE);
        if(items!=null&&!items.isEmpty()){
            List <DemandeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))?DemandeTransformer.INSTANCE.toLiteDtos(items):DemandeTransformer.INSTANCE.toDtos(items);
            final int size = items.size();
            List <String> listOfError = Collections.synchronizedList(new ArrayList <String>());
            itemsDto.parallelStream().forEach(dto->{
                try{
                    dto = getFullInfos(dto,size,request.getIsSimpleLoading(),locale);
                }catch(Exception e){
                    listOfError.add(e.getMessage());
                    e.printStackTrace();
                }
            });
            if(Utilities.isNotEmpty(listOfError)){
                Object[] objArray = listOfError.stream().distinct().toArray();
                throw new RuntimeException(StringUtils.join(objArray,", "));
            }
            if(user!=null&&user.getCategory().getId()==StatusApiEnum.OMCI_CATEGORY||user.getCategory().getId()==StatusApiEnum.TELCO_CATEGORY){
                if(user.getCategory()!=null){
                    List <DemandeDto> toReturn = itemsDto.stream().filter(dto->dto.getIdTypeCategory()==user.getCategory().getId()||dto.getIsValidated()==Boolean.TRUE||dto.getTwoBool()==Boolean.TRUE).collect(Collectors.toList());
                    if(Utilities.isNotEmpty(toReturn)){
                        response.setFilePathDoc(writeFileName(toReturn));
                    }
                    response.setItems(toReturn);
                    response.setCount(Long.valueOf(toReturn.size()));
                    response.setHasError(false);
                    return response;
                }
            }

            if(Utilities.isNotEmpty(itemsDto)){
                response.setFilePathDoc(writeFileName(itemsDto));
            }
            response.setItems(itemsDto);
            response.setCount(demandeRepository.count(request,em,locale));
            response.setHasError(false);
            response.setStatus(functionalError.SUCCESS("",locale));
        }else{
            response.setStatus(functionalError.DATA_EMPTY("demande",locale));
            response.setHasError(false);
            return response;
        }

        log.info("----end get Demande-----");
        return response;
    }

    /**
     * get full DemandeDto by using Demande as object.
     *
     * @param dto
     * @param size
     * @param isSimpleLoading
     * @param locale
     * @return
     * @throws Exception
     */
    private DemandeDto getFullInfos(DemandeDto dto,Integer size,Boolean isSimpleLoading,Locale locale) throws Exception{
        if(dto.getIdActionEnMasse()!=null){
            ActionEnMasse actionEnMasse = actionEnMasseRepository.findOne(dto.getIdActionEnMasse(),Boolean.FALSE);
            if(actionEnMasse!=null){
                dto.setActionEnMasseDto(ActionEnMasseTransformer.INSTANCE.toDto(actionEnMasse));
            }
        }
        // put code here
        if(Utilities.isTrue(isSimpleLoading)){
            return dto;
        }
        if(size>1){
            return dto;
        }
        return dto;
    }

    public Response <DemandeDto> custom(Request <DemandeDto> request,Locale locale){
        log.info("----begin custom DemandeDto-----");
        Response <DemandeDto> response = new Response <DemandeDto>();

        response.setHasError(false);
        response.setCount(1L);
        response.setItems(Arrays.asList(new DemandeDto()));
        log.info("----end custom DemandeDto-----");
        return response;
    }

    @org.springframework.transaction.annotation.Transactional(rollbackFor={RuntimeException.class,Exception.class})
    public Response <DemandeDto> valideRefuser(Request <DemandeDto> request,Locale locale) throws Exception{
        log.info("----begin ValiderRefuser  Demande-----");
        Response <DemandeDto> response = new Response <DemandeDto>();
        List <Demande> items = new ArrayList <Demande>();
        Request <ActionUtilisateurDto> requestDemande = new Request <>();
        List <ActionUtilisateurDto> requestDemandeList = new ArrayList <>();
        Status existingStatus = null;
        TypeNumero typeNumero = null;
        Demande entityToSave = null;
        User existingUser = null;
        Boolean aBoolean = Boolean.FALSE;
        TypeDemande existingTypeDemande = null;
        List <String> numeros = new ArrayList <>();
        ActionUtilisateur one_action = null;
        List <Map <String, String>> toRecipients = new ArrayList <>();
        ActionSimswap actionSimswap = actionSimswapRepository.findByLibelle("ACTION SIMSWAP",Boolean.FALSE);
        for(DemandeDto dto: request.getDatas()){
            // Definir les parametres obligatoires
            Map <String, java.lang.Object> fieldsToVerify = new HashMap <>();
            fieldsToVerify.put("idTypeDemande",dto.getIdTypeDemande());
            fieldsToVerify.put("id",dto.getId());
            fieldsToVerify.put("typeNumeroId",dto.getTypeNumeroId());
            fieldsToVerify.put("isTransfert",dto.getIsTransfert());
            fieldsToVerify.put("idCategory",dto.getIdTypeCategory());
            if(!Validate.RequiredValue(fieldsToVerify).isGood()){
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
                response.setHasError(true);
                return response;
            }
            aBoolean = dto.getIsTransfert();
            entityToSave = demandeRepository.findOne(dto.getId(),false);
            Validate.EntityNotExiste(entityToSave);
            Validate.EntityNotExiste(entityToSave.getUser());
            Validate.EntityNotExiste(entityToSave.getCategory());
            Integer idStatus = entityToSave.getStatus().getId();
            existingStatus = statusRepository.findOne(idStatus,false);
            Validate.EntityNotExiste(existingStatus);
            if(entityToSave.getTypeDemande()!=null){
                if(entityToSave.getTypeDemande().getId().equals(TypeDemandeEnum.ACCEPTER)){
                    response.setStatus(functionalError.DATA_EXIST("DÉJA ACCEPTÉ-> "+dto.getId(),locale));
                    response.setHasError(true);
                    return response;
                }
            }
            existingTypeDemande = typeDemandeRepository.findOne(dto.getIdTypeDemande(),false);
            Validate.EntityNotExiste(existingTypeDemande);
            typeNumero = typeNumeroRepository.findOne(dto.getTypeNumeroId(),Boolean.FALSE);
            Validate.EntityNotExiste(typeNumero);
            // Verifier si la demande existe
            if(entityToSave.getTypeDemande()!=null){
                if(entityToSave.getTypeDemande().getId().equals(TypeDemandeEnum.ACCEPTER)){
                    if(dto.getIdTypeDemande().equals(TypeDemandeEnum.REFUSER)){
                        response.setStatus(functionalError.DATA_EXIST("demande id -> "+dto.getId(),locale));
                        response.setHasError(true);
                        return response;
                    }
                }
            }
            numeros.add(entityToSave.getNumero());
            existingUser = null;
            if(request.getUser()!=null&&request.getUser()>0){
                existingUser = userRepository.findOne(request.getUser(),false);
                Validate.EntityNotExiste(existingUser);
                User userSendRequest = userRepository.findOne(entityToSave.getUser().getId(),Boolean.FALSE);
                Validate.EntityNotExiste(userSendRequest);
                Response <DemandeDto> workFlowTraitementPourLaValidationDuneDemande = workFlowTraitementPourLaValidationDuneDemande(request,locale,dto,entityToSave,existingUser,response,existingTypeDemande,existingStatus,items,actionSimswap);
                if(workFlowTraitementPourLaValidationDuneDemande!=null)
                    return workFlowTraitementPourLaValidationDuneDemande;
                List <ActionUtilisateur> oneActionList = null;
                if(entityToSave.getIsTransfert()==Boolean.TRUE){
                    Category category = getCategory(entityToSave.getCategory());
                    oneActionList = actionUtilisateurRepository.findByNumber(typeNumero.getId(),category.getId(),AES.encrypt(entityToSave.getNumero(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                }else{
                    oneActionList = actionUtilisateurRepository.findByNumber(typeNumero.getId(),entityToSave.getCategory().getId(),AES.encrypt(entityToSave.getNumero(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                }
                if(Utilities.isEmpty(oneActionList)){
                    response.setHasError(Boolean.TRUE);
                    response.setStatus(functionalError.DATA_EMPTY("le numero existe pas dans cette categorie",locale));
                    return response;
                }
                one_action = oneActionList.get(0);
                List <ActionUtilisateur> listeToDeleted = oneActionList.stream().skip(1).collect(Collectors.toList());
                if(Utilities.isNotEmpty(listeToDeleted)){
                    listeToDeleted.stream().forEach(argsToDeleted->{
                        argsToDeleted.setIsDeleted(Boolean.TRUE);
                        actionUtilisateurRepository.save(argsToDeleted);
                    });
                }
                if(dto.getIdTypeDemande()==TypeDemandeEnum.ACCEPTER&&dto.getIsTransfert()==Boolean.FALSE){
                    Response <DemandeDto> response1 = traitementFonctionnaliteDeNonTransfert(request,locale,dto,one_action,response,entityToSave,idStatus,userSendRequest,existingStatus,existingUser,typeNumero,existingTypeDemande,actionSimswap,numeros);
                    if(response1!=null) return response1;
                }
                if(aBoolean==Boolean.TRUE){
                    ActionUtilisateurDto actionUtilisateurDto = new ActionUtilisateurDto();
                    actionUtilisateurDto.setNumero(entityToSave.getNumero());
                    actionUtilisateurDto.setMotif(entityToSave.getMotif());
                    actionUtilisateurDto.setIdCategory(dto.getIdTypeCategory());
                    actionUtilisateurDto.setTypeNumeroId(typeNumero.getId());
                    requestDemandeList.add(actionUtilisateurDto);
                }
                // Change nouveau status de la demande
                entityToSave.setTypeDemande(existingTypeDemande);
                entityToSave.setUpdatedAt(Utilities.getCurrentDate());
                entityToSave.setUpdatedBy(request.getUser());
                entityToSave.setStatus(existingStatus);
                items.add(entityToSave);
            }
        }
        if(aBoolean==Boolean.TRUE){
            requestDemande.setUser(existingUser.getId());
            requestDemande.setDatas(requestDemandeList);
            Response <DemandeDto> demandeDtoResponse = traitementPourLaFonctionnaliteDeTransfert(requestDemande,locale,entityToSave,existingTypeDemande,existingUser,actionSimswap,response);
            if(demandeDtoResponse!=null&&demandeDtoResponse.isHasError()==Boolean.TRUE){
                response.setRaison(demandeDtoResponse.getRaison());
                response.setHasError(Boolean.TRUE);
                response.setStatus(demandeDtoResponse.getStatus());
                return response;
            }
//            Response<DemandeDto> response1 = traiteLeCasDesTransfert(locale, numeros, existingUser, typeNumero, response, entityToSave);
//            if (response1 != null) return response1;
        }else{
            Response <DemandeDto> response1 = traiteLesCasDeNonTransfert(locale,existingStatus,numeros,existingUser,typeNumero,response,entityToSave,request);
            if(response1!=null) return response1;
            if(!existingStatus.getId().equals(StatusApiEnum.SUPPRIMER)){
                simSwapServiceProxyService.msisdnForRefraiche(UUID.randomUUID().toString(),numeros,existingUser);
            }
        }
        if(!items.isEmpty()){
            Response <DemandeDto> response1 = getDemandeDtoResponse(request,locale,items,response,toRecipients);
            if(response1!=null) return response1;
        }
        response.setNumbers(Utilities.isNotEmpty(numeros)?numeros.toString():"AUCUN NUMEROS");
        response.setStatus(functionalError.SUCCESS("",locale));
        response.setNumbers(Utilities.isNotEmpty(numeros)?numeros.get(0):"AUCUN NUMERO");
        response.setHasError(false);
        log.info("----end ValiderRefuser Demande-----");
        return response;

    }

    private Response <DemandeDto> traiteLesCasDeNonTransfert(Locale locale,Status existingStatus,List <String> numeros,User existingUser,TypeNumero typeNumero,Response <DemandeDto> response,Demande entityToSave,Request <DemandeDto> request){

        Category category;
        if(entityToSave.getTwoBool()==Boolean.TRUE){
            if(entityToSave.getOfferName().equalsIgnoreCase("Orange money PDV")||entityToSave.getOfferName().equalsIgnoreCase("ORANGE ZEBRA STK")){
                category = objectUtilities.categoriePair().getCategorieOmci();
            }else{
                category = objectUtilities.categoriePair().getCategorieTelco();
            }
        }else{
            category = entityToSave.getCategory();
        }

        LockUnLockFreezeDtos lockUnLockFreezeDtos = actionEnMasse(existingStatus,numeros,existingUser,entityToSave.getCategory(),existingUser.getProfil(),typeNumero);
        if(lockUnLockFreezeDtos!=null&&lockUnLockFreezeDtos.getHasError()==Boolean.TRUE){
            response.setRaison(lockUnLockFreezeDtos.getMessage()+" sur "+entityToSave.getNumero());
            response.setStatus(functionalError.DISALLOWED_OPERATION(lockUnLockFreezeDtos.getMessage(),locale));
            response.setHasError(Boolean.TRUE);
            return response;
        }
        Category finalCategory = category;
        numeros.stream().forEach(args->{
            numeroStoriesSaveLite(args,existingUser.getProfil(),existingStatus,typeNumero,existingUser,finalCategory,StatusApiEnum.EFFECTUER,Boolean.FALSE,request);
        });
        Demande finalEntityToSave = entityToSave;
        Status finalExistingStatus = existingStatus;
        User finalExistingUser = existingUser;
        numeros.stream().forEach(args->{
            LocalDateTime now = LocalDateTime.now();
            String messageValidateGenerateSimswapAlert = Utilities.validateGenerateDemandeSimswapAlert(now,finalEntityToSave.getUser().getNom()+" "+finalEntityToSave.getUser().getNom(),args,finalExistingStatus.getCode(),finalEntityToSave.getUser().getLogin(),finalExistingUser.getNom()+" "+finalExistingUser.getPrenom(),finalExistingUser.getLogin(),"SUCCESS");
            AtionToNotifiable ationToNotifiable = extracted(existingStatus.getId().equals(StatusApiEnum.SUPPRIMER)?"VALIDATION SUPPRIMER NUMERO":"VALIDATION DEBLOCAGE NUMERO SIMSWAP",null,existingUser,existingStatus,existingUser!=null?existingUser.getId():0,entityToSave.getNumero(),"SUCCESS",messageValidateGenerateSimswapAlert);
            try{
                simSwapServiceProxyService.sendEmail(messageValidateGenerateSimswapAlert,ationToNotifiable);
            }catch(IOException e){
                log.info(e.getMessage());
            }
        });
        return null;
    }

    private Response <DemandeDto> traiteLeCasDesTransfert(Locale locale,List <String> numeros,User existingUser,TypeNumero typeNumero,Response <DemandeDto> response,Demande entityToSave){
        Category category = null;
        Status existingStatus;
        existingStatus = statusRepository.findOne(StatusApiEnum.BLOQUER,Boolean.FALSE);
        if(entityToSave.getTwoBool()==Boolean.TRUE){
            if(entityToSave.getOfferName().equalsIgnoreCase("Orange money PDV")||entityToSave.getOfferName().equalsIgnoreCase("ORANGE ZEBRA STK")){
                category = objectUtilities.categoriePair().getCategorieOmci();
            }else{
                category = objectUtilities.categoriePair().getCategorieTelco();
            }
        }else{
            category = entityToSave.getCategory();
        }
        LockUnLockFreezeDtos lockUnLockFreezeDtos = actionEnMasse(existingStatus,numeros,existingUser,category,existingUser.getProfil(),typeNumero);
        if(lockUnLockFreezeDtos!=null&&lockUnLockFreezeDtos.getHasError()==Boolean.TRUE){
            response.setRaison(lockUnLockFreezeDtos.getMessage());
            response.setStatus(functionalError.DISALLOWED_OPERATION(lockUnLockFreezeDtos.getMessage(),locale));
            response.setHasError(Boolean.TRUE);
            return response;
        }
        Demande finalEntityToSave = entityToSave;
        Demande finalEntityToSave1 = entityToSave;
        numeros.stream().forEach(args->{
            LocalDateTime now = LocalDateTime.now();
            String messagedemandeGenerateSimswapAlert = Utilities.messageValidationTransfertCategorie(now,finalEntityToSave.getUser().getNom()+" "+finalEntityToSave.getUser().getPrenom(),finalEntityToSave.getUser().getLogin(),finalEntityToSave.getUser().getProfil().getLibelle(),args,finalEntityToSave1.getUser()!=null?finalEntityToSave1.getUser().getCategory()!=null?finalEntityToSave1.getUser().getCategory().getCode():"PAS DÉFINI":"PAS DEFINI",finalEntityToSave1.getCategory().getCode());
            AtionToNotifiable ationToNotifiable = extracted("VALIDATION TRANSFERT DE CORBEILLE",null,existingUser,existingStatus,existingUser!=null?existingUser.getId():0,entityToSave.getNumero(),"SUCCESS",messagedemandeGenerateSimswapAlert);
            try{
                simSwapServiceProxyService.sendEmail(messagedemandeGenerateSimswapAlert,ationToNotifiable);
            }catch(IOException e){
                log.info(e.getMessage());
            }
        });
        return null;
    }

    private Response <DemandeDto> traitementFonctionnaliteDeNonTransfert(Request <DemandeDto> request,Locale locale,DemandeDto dto,ActionUtilisateur one_action,Response <DemandeDto> response,Demande entityToSave,Integer idStatus,User userSendRequest,Status existingStatus,User existingUser,TypeNumero typeNumero,TypeDemande existingTypeDemande,ActionSimswap actionSimswap,List <String> numeros){
        if(one_action!=null&&!entityToSave.getStatus().getId().equals(StatusApiEnum.SUPPRIMER)&&one_action.getIsMachine()==Boolean.TRUE){
            response.setStatus(functionalError.DISALLOWED_OPERATION(String.format("pas permis {}, est en machine",entityToSave.getNumero()),locale));
            response.setHasError(true);
            return response;
        }
        Response <DemandeDto> validationDemandeNumeroExistant = validationDemandeNumeroExistant(request,dto,one_action,entityToSave,existingStatus,userSendRequest);
        if(validationDemandeNumeroExistant!=null) return validationDemandeNumeroExistant;

        numeros.add(entityToSave.getNumero());
        return null;
    }

    private Category getCategory(Category category){
        if(category.getId().equals(StatusApiEnum.OMCI_CATEGORY)){
            return objectUtilities.categoriePair().getCategorieTelco();
        }else{
            return objectUtilities.categoriePair().getCategorieOmci();
        }
    }

    private Response <DemandeDto> getDemandeDtoResponse(Request <DemandeDto> request,Locale locale,Iterable <Demande> items,Response <DemandeDto> response,List <Map <String, String>> toRecipients) throws ParseException{
        List <Demande> itemsSaved = null;
        // inserer les donnees en base de donnees
        itemsSaved = demandeRepository.saveAll(items);
        if(itemsSaved==null){
            response.setStatus(functionalError.SAVE_FAIL("demande",locale));
            response.setHasError(true);
            return response;
        }
        List <DemandeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))?DemandeTransformer.INSTANCE.toLiteDtos(itemsSaved):DemandeTransformer.INSTANCE.toDtos(itemsSaved);
        final int size = itemsSaved.size();
        List <String> listOfError = Collections.synchronizedList(new ArrayList <String>());
        itemsDto.parallelStream().forEach(dto->{
            try{
                dto = getFullInfos(dto,size,request.getIsSimpleLoading(),locale);
            }catch(Exception e){
                listOfError.add(e.getMessage());
                e.printStackTrace();
            }
        });
        if(Utilities.isNotEmpty(listOfError)){
            Object[] objArray = listOfError.stream().distinct().toArray();
            throw new RuntimeException(StringUtils.join(objArray,", "));
        }
        response.setStatus(functionalError.SUCCESS("",locale));
        response.setItems(itemsDto);
        response.setHasError(false);
        return null;
    }

    private Response <DemandeDto> validationDemandeNumeroExistant(Request <DemandeDto> request,DemandeDto dto,ActionUtilisateur one_action,Demande entityToSave,Status existingStatus,User existingUser){
        one_action.setMotif(entityToSave.getMotif()!=null?entityToSave.getMotif():"");
        one_action.setCategory(entityToSave.getCategory());
        one_action.setUpdatedAt(Utilities.getCurrentDate());
        demandeRepository.save(entityToSave);
        actionUtilisateurRepository.save(one_action);
        return null;
    }

    private Response <DemandeDto> workFlowTraitementPourLaValidationDuneDemande(Request <DemandeDto> request,Locale locale,DemandeDto dto,Demande entityToSave,User existingUser,Response <DemandeDto> response,TypeDemande existingTypeDemande,Status existingStatus,List <Demande> items,ActionSimswap actionSimswap){
        if(entityToSave.getIsValidated()==Boolean.TRUE){
            if(existingUser.getCategory().getCode().equalsIgnoreCase("TELCO")){
                entityToSave.setIsValidated(Boolean.FALSE);
                entityToSave.setTwoBool(Boolean.TRUE);
                demandeRepository.save(entityToSave);
                response.setStatus(functionalError.SUCCESS_VALIDATED("UN RESPONSABLE OMCI DOIT VALIDER",locale));
            }
            if(existingUser.getCategory().getCode().equalsIgnoreCase("OMCI")){
                entityToSave.setTwoBool(Boolean.TRUE);
                entityToSave.setIsValidated(Boolean.FALSE);
                demandeRepository.save(entityToSave);
                response.setStatus(functionalError.SUCCESS_VALIDATED("UN RESPONSABLE TELCO DOIT VALIDER",locale));
            }
            response.setHasError(false);
            return response;
        }
        if(existingTypeDemande.getCode().equals(TypeDemandeEnum.REFUSER_CODE)){
            entityToSave.setTypeDemande(existingTypeDemande);
            entityToSave.setStatus(existingStatus);
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            entityToSave.setUpdatedBy(request.getUser());
            items.add(entityToSave);
            forTache(request.getUser(),entityToSave,existingTypeDemande,existingUser,entityToSave.getUpdatedAt(),entityToSave.getStatus(),actionSimswap);
            response.setActionEffectue("REFUSER UNE DEMANDE");
            response.setHasError(false);
            demandeRepository.save(entityToSave);
            return response;
        }
        return null;
    }

    private Response <DemandeDto> traitementPourLaFonctionnaliteDeTransfert(Request<ActionUtilisateurDto> request,Locale locale,Demande entityToSave,TypeDemande existingTypeDemande,User existingUser,ActionSimswap actionSimswap,Response <DemandeDto> response) throws Exception{
        Response <ActionUtilisateurDto> actionUtilisateurDtoResponse = actionUtilisateurBusiness.create(request,locale);
        if(actionUtilisateurDtoResponse!=null&&actionUtilisateurDtoResponse.isHasError()==Boolean.TRUE){
            response.setRaison(actionUtilisateurDtoResponse.getRaison());
            response.setHasError(Boolean.TRUE);
            response.setNumbers(entityToSave.getNumero());
            response.setStatus(actionUtilisateurDtoResponse.getStatus());
            return response;
        }
        forTache(request.getUser(),entityToSave,existingTypeDemande,existingUser,entityToSave.getUpdatedAt(),entityToSave.getStatus(),actionSimswap);
        response.setActionEffectue("ACCEPTER UNE DEMANDE");
        return response;
    }

    public Response <DemandeDto> actionOnNumber(Request <DemandeDto> request,Locale locale){
        log.info("----begin actionOnNumber  Demande-----");
        Response <DemandeDto> response = new Response <DemandeDto>();
        List <String> numbersToCreateOrUpdate = new ArrayList <>();
        User existingUser = null;
        Status existingStatus = null;
        TypeNumero typeNumero = null;
        boolean b = Boolean.FALSE;
        ActionSimswap actionSimswap = actionSimswapRepository.findByLibelle("ACTION SIMSWAP",Boolean.FALSE);
        for(DemandeDto dto: request.getDatas()){
            Map <String, java.lang.Object> fieldsToVerify = new HashMap <String, java.lang.Object>();
            fieldsToVerify.put("numero",dto.getNumero());
            fieldsToVerify.put("typeNumeroId",dto.getTypeNumeroId());
            fieldsToVerify.put("idStatus",dto.getIdStatus());
            if(!Validate.RequiredValue(fieldsToVerify).isGood()){
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
                response.setHasError(true);
                return response;
            }
            existingStatus = statusRepository.findOne(dto.getIdStatus(),false);
            Validate.EntityNotExiste(existingStatus);
            typeNumero = typeNumeroRepository.findOne(dto.getTypeNumeroId(),Boolean.FALSE);
            Validate.EntityNotExiste(typeNumero);
            if(existingStatus.getId()==StatusApiEnum.DEBLOQUER||existingStatus.getId()==StatusApiEnum.SUPPRIMER){
                response.setStatus(functionalError.DISALLOWED_OPERATION("Non permis",locale));
                response.setHasError(false);
                return response;
            }
            if(request.getUser()!=null&&request.getUser()>0){
                existingUser = userRepository.findOne(request.getUser(),Boolean.FALSE);
                Validate.EntityNotExiste(existingUser);
                Validate.EntityNotExiste(existingUser.getCategory());
                Validate.EntityNotExiste(existingUser.getProfil());
                if(dto.getIdStatus().equals(StatusApiEnum.DEBLOQUER)||dto.getIdStatus().equals(StatusApiEnum.SUPPRIMER)){
                    Response <DemandeDto> response1 = verificationDesCasDeWorflowPourLesStatusDeDeBlocage(locale,dto,existingUser,typeNumero,response);
                    if(response1!=null) return response1;
                }
                numbersToCreateOrUpdate.add(dto.getNumero());
            }
            forTache(request.getUser(),null,null,existingUser,Utilities.getCurrentDate(),existingStatus,actionSimswap);
        }
        log.info("status "+existingStatus+" liste des numeros "+numbersToCreateOrUpdate+" existingUSer "+existingUser+" existingUserCategory "+existingUser.getCategory()+" existingProfil "+existingUser.getProfil()+" typeNumero "+typeNumero);
        System.out.println("status "+existingStatus+" liste des numeros "+numbersToCreateOrUpdate+" existingUSer "+existingUser+" existingUserCategory "+existingUser.getCategory()+" existingProfil "+existingUser.getProfil()+" typeNumero "+typeNumero);
        LockUnLockFreezeDtos lockUnLockFreezeDtos = actionEnMasse(existingStatus,numbersToCreateOrUpdate,existingUser,existingUser.getCategory(),existingUser.getProfil(),typeNumero);
        if(lockUnLockFreezeDtos!=null&&lockUnLockFreezeDtos.getHasError()==Boolean.TRUE){
            User finalExistingUser1 = existingUser;
            User finalExistingUser2 = existingUser;
            Status finalExistingStatus1 = existingStatus;
            numbersToCreateOrUpdate.stream().forEach(arg->{
                LocalDateTime now = LocalDateTime.now();
                String messageAlerteSimswap = Utilities.generateSimswapAlert(now,finalExistingUser2.getNom()+" "+finalExistingUser2.getPrenom(),arg,finalExistingStatus1.getCode(),finalExistingUser2.getLogin(),"FAILED");
                AtionToNotifiable ationToNotifiable = extracted(finalExistingStatus1.getId().equals(StatusApiEnum.MISE_EN_MACHINE)?"MISE_EN_MACHINE NUMERO SIMSWAP":"BLOCAGE NUMERO SIMSWAP",null,finalExistingUser2,finalExistingStatus1,finalExistingUser1!=null?finalExistingUser1.getId():0,arg,"FAILED",messageAlerteSimswap);
                try{
                    simSwapServiceProxyService.sendEmail(messageAlerteSimswap,ationToNotifiable);
                }catch(IOException e){
                    log.info(e.getMessage());
                }
            });
            simSwapServiceProxyService.msisdnForRefraiche(UUID.randomUUID().toString(),numbersToCreateOrUpdate,existingUser);
            response.setStatus(functionalError.DISALLOWED_OPERATION(lockUnLockFreezeDtos.getMessage(),locale));
            response.setHasError(Boolean.TRUE);
            response.setRaison(lockUnLockFreezeDtos.getMessage());
            return response;
        }
        if(Utilities.isNotEmpty(numbersToCreateOrUpdate)){
            User finalExistingUser = existingUser;
            Status finalExistingStatus = existingStatus;
            TypeNumero finalTypeNumero = typeNumero;
            numbersToCreateOrUpdate.stream().forEach(args->{
                numeroStoriesSaveLite(args,finalExistingUser.getProfil(),finalExistingStatus,finalTypeNumero,finalExistingUser,finalExistingUser.getCategory(),StatusApiEnum.EFFECTUER,finalExistingStatus.equals(StatusApiEnum.MISE_EN_MACHINE)?Boolean.TRUE:Boolean.FALSE,request);
            });
            User finalExistingUser1 = existingUser;
            User finalExistingUser2 = existingUser;
            Status finalExistingStatus1 = existingStatus;
            numbersToCreateOrUpdate.stream().forEach(arg->{
                LocalDateTime now = LocalDateTime.now();
                String messageAlerteSimswap = Utilities.generateSimswapAlert(now,finalExistingUser2.getNom()+" "+finalExistingUser2.getPrenom(),arg,finalExistingStatus1.getCode(),finalExistingUser2.getLogin(),"SUCCESS");
                AtionToNotifiable ationToNotifiable = extracted(finalExistingStatus1.getId().equals(StatusApiEnum.MISE_EN_MACHINE)?"MISE_EN_MACHINE NUMERO SIMSWAP":"BLOCAGE NUMERO SIMSWAP",null,finalExistingUser2,finalExistingStatus1,finalExistingUser1!=null?finalExistingUser1.getId():0,arg,"SUCCESS",messageAlerteSimswap);
                try{
                    simSwapServiceProxyService.sendEmail(messageAlerteSimswap,ationToNotifiable);
                }catch(IOException e){
                    log.info(e.getMessage());
                }
            });
        }
        simSwapServiceProxyService.msisdnForRefraiche(UUID.randomUUID().toString(),numbersToCreateOrUpdate,existingUser);
        response.setStatus(functionalError.SUCCESS("",locale));
        response.setHasError(false);
        log.info("----end ValiderRefuser Demande-----");
        return response;
    }

    private Response <DemandeDto> verificationDesCasDeWorflowPourLesStatusDeDeBlocage(Locale locale,DemandeDto dto,User existingUser,TypeNumero typeNumero,Response <DemandeDto> response){
        boolean b;
        if(existingUser.getCategory().getId()==StatusApiEnum.TELCO_CATEGORY){
            List <ActionUtilisateur> categorieOmci = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(),StatusApiEnum.OMCI_CATEGORY,AES.encrypt(dto.getNumero(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);

            if(Utilities.isNotEmpty(categorieOmci)){
                ActionUtilisateur actionUtilisateurs = categorieOmci.get(0);
                actionUtilisateurs.setFromBss(Boolean.FALSE);
                actionUtilisateurRepository.save(actionUtilisateurs);
                b = Boolean.TRUE;
                response.setStatus(functionalError.SUCCESS_VALIDATED("UN RESPONSABLE OMCI DOIT VALIDER",locale));
                response.setHasError(false);
                return response;
            }

        }
        if(existingUser.getCategory().getId()==StatusApiEnum.OMCI_CATEGORY){
            List <ActionUtilisateur> categorieTelco = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(),StatusApiEnum.TELCO_CATEGORY,AES.encrypt(dto.getNumero(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
            if(Utilities.isNotEmpty(categorieTelco)){
                ActionUtilisateur actionUtilisateurs = categorieTelco.get(0);
                actionUtilisateurs.setFromBss(Boolean.FALSE);
                actionUtilisateurRepository.save(actionUtilisateurs);
                b = Boolean.TRUE;
                response.setStatus(functionalError.SUCCESS_VALIDATED("UN RESPONSABLE TELCO DOIT VALIDER",locale));
                response.setHasError(false);
                return response;
            }
        }
        return null;
    }

    private List <String> traitementOperationEnMasse(LockUnLockFreezeDtos LockDtos){
        List <String> numeroToCreate = new ArrayList <>();
        if(LockDtos.getData()!=null&&Utilities.isNotEmpty(LockDtos.getData())&&LockDtos.getStatus().equalsIgnoreCase("200")){
            LockDtos.getData().stream().forEach(arg->{
                numeroToCreate.add(arg.getMsisdn());
//                if (!arg.getState().equalsIgnoreCase("Opération déja effectuée ")) {
//                    numeroToCreate.add(arg.getMsisdn());
//                }
            });
        }
        return numeroToCreate;
    }

    private LockUnLockFreezeDtos actionEnMasse(Status status,List <String> numeros,User user,Category category,Profil profil,TypeNumero typeNumero){
        LockUnLockFreezeDtos FreezeDtos = null;
        Date lasBlocageDate = null;
        Date lasMachineDate = null;
        Date lasDeBlocageDate = null;
        if(status.getId().equals(StatusApiEnum.MISE_EN_MACHINE)){
            log.info("mise en machine");
            FreezeDtos = simSwapServiceProxyService.freezeNumber(numeros,UUID.randomUUID().toString());
//            numeros.stream().forEach(arg -> {
//                List<ActionUtilisateur> actionUtilisateurList = actionUtilisateurRepository.findByNumeroAll(typeNumero.getId(), AES.encrypt(arg, StatusApiEnum.SECRET_KEY), Boolean.FALSE);
//                if (Utilities.isNotEmpty(actionUtilisateurList)) {
//                    actionUtilisateurList.stream().forEach(actionUtilisateur -> {
//                        actionUtilisateur.setIsMachine(Boolean.TRUE);
//                        actionUtilisateur.setLastMachineDate(Utilities.getCurrentDate());
//                        actionUtilisateur.setUser(user);
//                        actionUtilisateur.setUpdatedAt(Utilities.getCurrentDate());
//                        actionUtilisateurRepository.save(actionUtilisateur);
//                    });
//                }
//            });
            lasMachineDate = Utilities.getCurrentDate();
        }
        if(status.getId().equals(StatusApiEnum.BLOQUER)){
            log.info("bloquage");
            FreezeDtos = simSwapServiceProxyService.lockNumber(numeros,UUID.randomUUID().toString());
            lasBlocageDate = Utilities.getCurrentDate();
//            numeros.stream().forEach(arg -> {
//                List<ActionUtilisateur> actionUtilisateurList = actionUtilisateurRepository.findByNumeroAll(typeNumero.getId(), AES.encrypt(arg, StatusApiEnum.SECRET_KEY), Boolean.FALSE);
//                if (Utilities.isNotEmpty(actionUtilisateurList)) {
//                    actionUtilisateurList.stream().forEach(actionUtilisateur -> {
//                        actionUtilisateur.setStatus(status);
//                        actionUtilisateur.setUser(user);
//                        actionUtilisateur.setLastBlocageDate(Utilities.getCurrentDate());
//                        actionUtilisateur.setUpdatedAt(Utilities.getCurrentDate());
//                        actionUtilisateurRepository.save(actionUtilisateur);
//                    });
//                }
//            });
        }
        if(status.getId().equals(StatusApiEnum.DEBLOQUER)){
            log.info("deblocage");
            FreezeDtos = simSwapServiceProxyService.unlockNumber(numeros,UUID.randomUUID().toString());
            lasDeBlocageDate = Utilities.getCurrentDate();
//            numeros.stream().forEach(arg -> {
//                List<ActionUtilisateur> actionUtilisateurList = actionUtilisateurRepository.findByNumeroAll(typeNumero.getId(), AES.encrypt(arg, StatusApiEnum.SECRET_KEY), Boolean.FALSE);
//                if (Utilities.isNotEmpty(actionUtilisateurList)) {
//                    actionUtilisateurList.stream().forEach(actionUtilisateur -> {
//                        actionUtilisateur.setStatus(status);
//                        actionUtilisateur.setUser(user);
//                        actionUtilisateur.setUpdatedAt(Utilities.getCurrentDate());
//                        actionUtilisateurRepository.save(actionUtilisateur);
//                    });
//                }
//            });

        }
        if(status.getId().equals(StatusApiEnum.SUPPRIMER)){
            log.info("est entrain de supprimer");
            numeros.stream().forEach(arg->{
                List <ActionUtilisateur> actionUtilisateurList = actionUtilisateurRepository.findByDepartement(typeNumero.getId(),category.getId(),AES.encrypt(arg,StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                log.info("la liste qui dois etre supprimer "+actionUtilisateurList);
                if(Utilities.isNotEmpty(actionUtilisateurList)){
                    actionUtilisateurList.stream().forEach(actionUtilisateur->{
                        actionUtilisateur.setIsDeleted(Boolean.TRUE);
                        actionUtilisateur.setUser(user);
                        actionUtilisateurRepository.save(actionUtilisateur);
                    });
                }
            });
        }
        if(FreezeDtos!=null&&Utilities.isNotEmpty(FreezeDtos.getData())&&FreezeDtos.getHasError()!=Boolean.FALSE&&!status.getId().equals(StatusApiEnum.SUPPRIMER)){
            simSwapServiceProxyService.msisdnForRefraiche(UUID.randomUUID().toString(),numeros,user);
            List <String> numberThatStateCompleted = traitementOperationEnMasse(FreezeDtos);
            if(Utilities.isNotEmpty(numberThatStateCompleted)){
                log.info("numero traité");
                creerNumbersHasNotExist(status,user,category,profil,typeNumero,numberThatStateCompleted,lasBlocageDate,lasMachineDate,lasDeBlocageDate);
                return FreezeDtos;
            }
        }
        return FreezeDtos;
    }

    private void creerNumbersHasNotExist(Status status,User user,Category category,Profil profil,TypeNumero typeNumero,List <String> numeroToCreate,Date lasBlocageDate,Date lasMachineDate,Date lasDeBlocageDate){
        log.info("entrain de créer les numéros ou mettre a jour");
        LockUnLockFreezeDto byMsisdn = simSwapServiceProxyService.findByMsisdn(UUID.randomUUID().toString(),numeroToCreate,user.getId(),Boolean.FALSE);
        if(byMsisdn!=null&&Utilities.isNotEmpty(byMsisdn.getData())){
            log.info("1 respecté");
            byMsisdn.getData().stream().forEach(arg->{
                if(arg.getMessage()==null&&arg.getPortNumber()>0){
                    log.info("2 respecté");
                    List <ActionUtilisateur> actionUtilisateur = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(),category.getId(),AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                    if(Utilities.isNotEmpty(actionUtilisateur)){
                        log.info("3 respecté");
                        actionUtilisateur.stream().forEach(argSaved->{
                            argSaved.setOfferName(arg.getOfferName());
                            argSaved.setSerialNumber(arg.getSerialNumber());
                            argSaved.setContractId(String.valueOf(arg.getContractId()));
                            argSaved.setFromBss(Boolean.TRUE);
                            argSaved.setTmCode(String.valueOf(arg.getTariffModelCode()));
                            argSaved.setPortNumber(String.valueOf(arg.getPortNumber()));
                            argSaved.setActivationDate(arg.getActivationDate()!=null?Utilities.formatDateAnyFormat(arg.getActivationDate(),"yyyy-MM-dd HH:mm:ss"):null);
                            argSaved.setProfil(profil);
                            argSaved.setCategory(category);
                            argSaved.setUser(user);
                            argSaved.setNumero(AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY));
                            argSaved.setIsMachine(arg.isFrozen());
                            argSaved.setTypeNumero(typeNumero);
                            if(lasMachineDate!=null){
                                argSaved.setLastMachineDate(lasMachineDate);
                                argSaved.setIsMachine(arg.isFrozen());
                            }
                            if(lasBlocageDate!=null){
                                argSaved.setStatus(status);
                                argSaved.setLastBlocageDate(lasBlocageDate);
                            }
                            if(lasDeBlocageDate!=null){
                                argSaved.setStatus(status);
                                argSaved.setLastDebloquage(lasDeBlocageDate);
                            }
                            argSaved.setStatusBscs(arg.getStatus());

                            actionUtilisateurRepository.save(argSaved);
                        });
                    }else{
                        if(user.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.ADMIN_CODE)){
                            mettreAjourPourLesActionMeneParDesUtilisateurDifferentDeOmciEtTellco(status,user,profil,typeNumero,lasBlocageDate,lasMachineDate,lasDeBlocageDate,arg);
                        }else{
                            ActionUtilisateur argSaved = new ActionUtilisateur();
                            argSaved.setOfferName(arg.getOfferName());
                            argSaved.setSerialNumber(arg.getSerialNumber());
                            argSaved.setContractId(String.valueOf(arg.getContractId()));
                            argSaved.setFromBss(Boolean.TRUE);
                            argSaved.setTmCode(String.valueOf(arg.getTariffModelCode()));
                            argSaved.setPortNumber(String.valueOf(arg.getPortNumber()));
                            argSaved.setActivationDate(arg.getActivationDate()!=null?Utilities.formatDateAnyFormat(arg.getActivationDate(),"yyyy-MM-dd HH:mm:ss"):null);
                            argSaved.setProfil(profil);
                            argSaved.setCategory(category);
                            argSaved.setCreatedAt(Utilities.getCurrentDate());
                            argSaved.setNumero(AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY));
                            argSaved.setIsMachine(arg.isFrozen());
                            argSaved.setTypeNumero(typeNumero);
                            if(lasMachineDate!=null){
                                argSaved.setLastMachineDate(lasMachineDate);
                                argSaved.setIsMachine(arg.isFrozen());
                            }
                            if(lasBlocageDate!=null){
                                argSaved.setStatus(status);
                                argSaved.setLastBlocageDate(lasBlocageDate);
                            }
                            if(lasDeBlocageDate!=null){
                                argSaved.setStatus(status);
                                argSaved.setLastDebloquage(lasDeBlocageDate);
                            }
                            argSaved.setStatusBscs(arg.getStatus());
                            argSaved.setUser(user);
                            argSaved.setUpdatedAt(Utilities.getCurrentDate());
                            actionUtilisateurRepository.save(argSaved);
                        }
                    }
                }
            });
        }
    }

    private void mettreAjourPourLesActionMeneParDesUtilisateurDifferentDeOmciEtTellco(Status status,User user,Profil profil,TypeNumero typeNumero,Date lasBlocageDate,Date lasMachineDate,Date lasDeBlocageDate,SensitiveNumberDto arg){
        List <ActionUtilisateur> actionUtilisateurOmci = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(),StatusApiEnum.OMCI_CATEGORY,AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
        Category categoryOmci = categoryRepository.findOne(StatusApiEnum.OMCI_CATEGORY,Boolean.FALSE);
        if(Utilities.isNotEmpty(actionUtilisateurOmci)){
            actionUtilisateurOmci.stream().forEach(argSaved->{
                argSaved.setOfferName(arg.getOfferName());
                argSaved.setSerialNumber(arg.getSerialNumber());
                argSaved.setContractId(String.valueOf(arg.getContractId()));
                argSaved.setFromBss(Boolean.TRUE);
                argSaved.setTmCode(String.valueOf(arg.getTariffModelCode()));
                argSaved.setPortNumber(String.valueOf(arg.getPortNumber()));
                argSaved.setActivationDate(arg.getActivationDate()!=null?Utilities.formatDateAnyFormat(arg.getActivationDate(),"yyyy-MM-dd HH:mm:ss"):null);
                argSaved.setProfil(profil);
                argSaved.setCategory(categoryOmci);
                argSaved.setUser(user);
                argSaved.setNumero(AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY));
                argSaved.setIsMachine(arg.isFrozen());
                argSaved.setTypeNumero(typeNumero);
                if(lasMachineDate!=null){
                    argSaved.setLastMachineDate(lasMachineDate);
                    argSaved.setIsMachine(arg.isFrozen());
                }
                if(lasBlocageDate!=null){
                    argSaved.setStatus(status);
                    argSaved.setLastBlocageDate(lasBlocageDate);
                }
                if(lasDeBlocageDate!=null){
                    argSaved.setStatus(status);
                    argSaved.setLastDebloquage(lasDeBlocageDate);
                }
                argSaved.setStatusBscs(arg.getStatus());

                actionUtilisateurRepository.save(argSaved);
            });
        }
        List <ActionUtilisateur> actionUtilisateurTelco = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(),StatusApiEnum.TELCO_CATEGORY,AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
        Category categoryTelco = categoryRepository.findOne(StatusApiEnum.OMCI_CATEGORY,Boolean.FALSE);
        if(Utilities.isNotEmpty(actionUtilisateurTelco)){
            actionUtilisateurTelco.stream().forEach(argSaved->{
                argSaved.setOfferName(arg.getOfferName());
                argSaved.setSerialNumber(arg.getSerialNumber());
                argSaved.setContractId(String.valueOf(arg.getContractId()));
                argSaved.setFromBss(Boolean.TRUE);
                argSaved.setTmCode(String.valueOf(arg.getTariffModelCode()));
                argSaved.setPortNumber(String.valueOf(arg.getPortNumber()));
                argSaved.setActivationDate(arg.getActivationDate()!=null?Utilities.formatDateAnyFormat(arg.getActivationDate(),"yyyy-MM-dd HH:mm:ss"):null);
                argSaved.setProfil(profil);
                argSaved.setCategory(categoryTelco);
                argSaved.setUser(user);
                argSaved.setNumero(AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY));
                argSaved.setIsMachine(arg.isFrozen());
                argSaved.setTypeNumero(typeNumero);
                if(lasMachineDate!=null){
                    argSaved.setLastMachineDate(lasMachineDate);
                    argSaved.setIsMachine(arg.isFrozen());
                }
                if(lasBlocageDate!=null){
                    argSaved.setStatus(status);
                    argSaved.setLastBlocageDate(lasBlocageDate);
                }
                if(lasDeBlocageDate!=null){
                    argSaved.setStatus(status);
                    argSaved.setLastDebloquage(lasDeBlocageDate);
                }
                argSaved.setStatusBscs(arg.getStatus());

                actionUtilisateurRepository.save(argSaved);
            });
        }
    }

    @Scheduled(cron="0 */5 * * * *")
    public void alerteSMS() throws IOException{
        System.out.println("---------------DEBUT------------------");
        TypeParametrage typeParametrage = typeParametrageRepository.findByCode("NOTIFICATION ALERTE SIMSWAP",Boolean.FALSE);
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        if(typeParametrage!=null){
            List <Parametrage> parametrageList = parametrageRepository.findByIdTypeParametrage(typeParametrage.getId(),Boolean.FALSE);
            if(Utilities.isNotEmpty(parametrageList)){
                Parametrage parametrage = parametrageList.get(0);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(parametrage.getDelaiAttente());
                Map <String, Object> resultMap = objectMapper.convertValue(jsonNode,HashMap.class);
                if(!resultMap.isEmpty()){
                    List <Map <String, Object>> list = (List <Map <String, Object>>) resultMap.get("datas");
                    if(Utilities.isNotEmpty(list)&&!list.get(0).isEmpty()){
                        Map <String, Object> map = list.get(0);
                        List <Map <String, Object>> hours = (List <Map <String, Object>>) map.get("hours");
                        if(Utilities.isNotEmpty(hours)){
                            for(Map <String, Object> current: hours){
                                String day = current.get("key").toString();
                                if(day!=null){
                                    LocalDate today = LocalDate.now();
                                    TextStyle textStyle = TextStyle.FULL;
                                    Locale localeDate = Locale.FRANCE;
                                    String dayOfWeek = today.getDayOfWeek().getDisplayName(textStyle,localeDate);
                                    if(day.equalsIgnoreCase(dayOfWeek)){
                                        List <Map <String, Object>> plages = (List <Map <String, Object>>) current.get("plages");
                                        if(Utilities.isNotEmpty(plages)){
                                            for(Map <String, Object> plage: plages){
                                                List <Integer> integers = (List <Integer>) plage.get("values");
                                                if(Utilities.isNotEmpty(integers)&&integers.size()==2){
                                                    log.info("9");
                                                    log.info("10");
                                                    System.out.println("la liste entrante est -> "+integers);
                                                    Integer firstValue = integers.get(0);
                                                    Integer secondValue = integers.get(1);
                                                    if(hour>=firstValue&&hour<=secondValue){
                                                        operationDalerteCourante(integers,hour);
                                                        break;
                                                    }

                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public void alerteSMSService() throws IOException{
        System.out.println("---------------DEBUT------------------");
        TypeParametrage typeParametrage = typeParametrageRepository.findByCode("NOTIFICATION ALERTE SIMSWAP",Boolean.FALSE);
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        if(typeParametrage!=null){
            List <Parametrage> parametrageList = parametrageRepository.findByIdTypeParametrage(typeParametrage.getId(),Boolean.FALSE);
            if(Utilities.isNotEmpty(parametrageList)){
                Parametrage parametrage = parametrageList.get(0);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(parametrage.getDelaiAttente());
                Map <String, Object> resultMap = objectMapper.convertValue(jsonNode,HashMap.class);
                if(!resultMap.isEmpty()){
                    List <Map <String, Object>> list = (List <Map <String, Object>>) resultMap.get("datas");
                    if(Utilities.isNotEmpty(list)&&!list.get(0).isEmpty()){
                        Map <String, Object> map = list.get(0);
                        List <Map <String, Object>> hours = (List <Map <String, Object>>) map.get("hours");
                        if(Utilities.isNotEmpty(hours)){
                            for(Map <String, Object> current: hours){
                                String day = current.get("key").toString();
                                if(day!=null){
                                    LocalDate today = LocalDate.now();
                                    TextStyle textStyle = TextStyle.FULL;
                                    Locale localeDate = Locale.FRANCE;
                                    String dayOfWeek = today.getDayOfWeek().getDisplayName(textStyle,localeDate);
                                    if(day.equalsIgnoreCase(dayOfWeek)){
                                        List <Map <String, Object>> plages = (List <Map <String, Object>>) current.get("plages");
                                        if(Utilities.isNotEmpty(plages)){
                                            for(Map <String, Object> plage: plages){
                                                List <Integer> integers = (List <Integer>) plage.get("values");
                                                if(Utilities.isNotEmpty(integers)&&integers.size()==2){
                                                    log.info("9");
                                                    log.info("10");
                                                    System.out.println("la liste entrante est -> "+integers);
                                                    Integer firstValue = integers.get(0);
                                                    Integer secondValue = integers.get(1);
                                                    if(hour>=firstValue&&hour<=secondValue){
                                                        operationDalerteCourante(integers,hour);
                                                        break;
                                                    }

                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }


    private void operationDalerteCourante(List <Integer> integers,int hour){
        log.info("11");
        List <AtionToNotifiable> ationToNotifiables = ationToNotifiableRepository.findByIsDeleted(Boolean.FALSE);
        Validate.EntityNotExiste(ationToNotifiables);
        ationToNotifiables.stream().forEach(arg->{
            log.info(" va lancer l'alerte");
            arg.setIsDeleted(Boolean.TRUE);
            arg.setUpdatedAt(Utilities.getCurrentDate());
            ationToNotifiableRepository.save(arg);
            log.info(" a l'alerte supprimer");
            simSwapServiceProxyService.sendSms(arg.getMessage(),arg);
        });
    }

    public String formatMessage(Status status,User initier,User valider,Date createdAt,Date updatedAt,String numero,Boolean isEnMasse,String statut,ActionParametrable actionParametrable,List <Map <String, String>> toRecipients){
        StringBuilder message = new StringBuilder();

        message.append("ALERTE : Une demande de SIMSWAP a été initiée");

        if(actionParametrable!=null){
            message.append(" avec l'action : '").append(actionParametrable.getLibelle()).append("'");
        }
        if(initier!=null){
            Map <String, String> recipient = new HashMap <>();
            recipient.put("email",initier.getEmailAdresse());
            recipient.put("user",initier.getNom());
            toRecipients.add(recipient);
            message.append(" par ").append(initier.getProfil().getLibelle()).append(" ").append(initier.getNom()).append(" ").append(initier.getPrenom());
            if(status!=null){
                message.append(", pour une opération de SIMSWAP de type '").append(status.getCode()).append("'");
            }else{
                message.append(", pour une opération de SIMSWAP de type 'MISE EN MACHINE'");
            }
        }
        if(valider!=null){
            Map <String, String> recipient = new HashMap <>();
            recipient.put("email",valider.getEmailAdresse());
            recipient.put("user",valider.getNom());
            toRecipients.add(recipient);

            message.append(". La validation a été effectuée par ").append(valider.getNom()).append(" ").append(valider.getPrenom()).append(" ayant le profil ").append(valider.getProfil().getLibelle());

            message.append(" avec la décision : '").append(statut).append("', le ").append(updatedAt!=null?updatedAt:Utilities.getCurrentDateTime());
        }

        if(numero!=null){
            message.append(". Le(s) numéro(s) concerné(s): '").append(numero).append("'");
        }

        if(createdAt!=null){
            message.append(". La demande a été initiée le ").append(createdAt);
        }

        if(isEnMasse!=null){
            message.append(". Provenance : ").append(isEnMasse.equals(Boolean.TRUE)?"'FICHIER D'IMPORTATION'":"'BSCS'");
        }
        message.insert(0,getSalutation());

        // Ajoutez d'autres informations selon vos besoins

        return message.toString();
    }

    public List <Map <String, String>> sendEmailList(List <ParametrageProfil> parametrageProfils,List <Map <String, String>> toRecipients){
        for(ParametrageProfil toSend: parametrageProfils){
            Map <String, String> recipient = new HashMap <String, String>();
            recipient = new HashMap <String, String>();
            recipient.put("email",toSend.getEmail());
            recipient.put("user",toSend.getNomPrenom());
            toRecipients.add(recipient);
        }
        return toRecipients;
    }


    private AtionToNotifiable extracted(String libelle,User userDemande,User userValide,Status status,Integer createdBy,String number,String statusAction,String messageAlerte){
        ActionSimswap actionParametrable = actionSimswapRepository.findByLibelle(libelle,Boolean.FALSE);
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
        return ationToNotifiableRepository.save(ationToNotifiable);

    }

    public void forTache(Integer requestUser,Demande demande,TypeDemande typeDemande,User user,Date date,Status status,ActionSimswap actionSimswap){
        Tache tache = new Tache();
        tache.setCreatedBy(requestUser);
        tache.setCreatedAt(Utilities.getCurrentDate());
        tache.setDemande(demande);
        tache.setTypeDemande(typeDemande);
        tache.setUser(user);
        tache.setDateDemande(date);
        tache.setIsDeleted(false);
        tache.setStatus(status);
        tache.setActionSimswap(actionSimswap);

        tacheRepository.save(tache);

    }

    public ApiResponse <LockUnLockFreezeDto> changestatusOnbscs(Integer statusId,String number,Status status,ActionUtilisateur actionUtilisateur,Category category,Profil profil,Demande demande,Request <DemandeDto> request){
        ApiResponse <LockUnLockFreezeDto> apiResponse = new ApiResponse <>();
        if(statusId==StatusApiEnum.SERVICE_API_BLOCK_SIM){
            _AppRequestDto _AppRequest = _AppRequestDto.builder().phoneNumber(number).build();
            apiResponse = simSwapServiceProxyService.lockPhoneNumber(_AppRequest);
            if(apiResponse.getStatus()==200){
                actionUtilisateur.setStatus(status);
                actionUtilisateur.setProfil(profil);
                actionUtilisateur.setCategory(category);
                actionUtilisateur.setStatut(StatusApiEnum.EFFECTUER);
                actionUtilisateur.setLastBlocageDate(Utilities.getCurrentDate());
                actionUtilisateurRepository.save(actionUtilisateur);
                numeroStoriesSave(number,profil,status,actionUtilisateur,request,demande,category,StatusApiEnum.EFFECTUER,Boolean.FALSE);
            }else{
                numeroStoriesSave(number,profil,status,actionUtilisateur,request,demande,category,StatusApiEnum.ECHOUER,Boolean.FALSE);
                return apiResponse;
            }
        }
        if(statusId==StatusApiEnum.SERVICE_API_DEBLOCK_SIM){
            _AppRequestDto _AppRequest = _AppRequestDto.builder().phoneNumber(number).build();
            actionUtilisateur.setLastDebloquage(Utilities.getCurrentDate());
            apiResponse = simSwapServiceProxyService.unlockPhoneNumber(_AppRequest);
            System.out.println("--------------+++++++++++++++-----------------"+apiResponse.getStatus());
            if(apiResponse.getStatus()==200){
                actionUtilisateur.setStatut(StatusApiEnum.EFFECTUER);
                actionUtilisateur.setStatus(status);
                actionUtilisateur.setProfil(profil);
                actionUtilisateur.setCategory(category);
                actionUtilisateur.setLastDebloquage(Utilities.getCurrentDate());
                actionUtilisateurRepository.save(actionUtilisateur);
                numeroStoriesSave(number,profil,status,actionUtilisateur,request,demande,category,StatusApiEnum.EFFECTUER,Boolean.FALSE);
            }else{
                numeroStoriesSave(number,profil,status,actionUtilisateur,request,demande,category,StatusApiEnum.ECHOUER,Boolean.FALSE);
                return apiResponse;
            }
        }
        if(statusId==StatusApiEnum.SERVICE_API_MISE_EN_MACHINE){
            _AppRequestDto _AppRequest = _AppRequestDto.builder().phoneNumber(number).build();
            apiResponse = simSwapServiceProxyService.freezePhoneNumber(_AppRequest);
            if(apiResponse.getStatus()==200){
                actionUtilisateur.setStatut(StatusApiEnum.EFFECTUER);
                actionUtilisateur.setIsMachine(Boolean.TRUE);
                actionUtilisateur.setProfil(profil);
                actionUtilisateur.setCategory(category);
                actionUtilisateur.setLastMachineDate(Utilities.getCurrentDate());
                actionUtilisateurRepository.save(actionUtilisateur);
                numeroStoriesSave(number,profil,status,actionUtilisateur,request,demande,category,StatusApiEnum.EFFECTUER,Boolean.TRUE);
            }else{
                numeroStoriesSave(number,profil,status,actionUtilisateur,request,demande,category,StatusApiEnum.ECHOUER,Boolean.TRUE);
                return apiResponse;
            }
        }
        return apiResponse;
    }

    public void numeroStoriesSave(String number,Profil profil,Status status,ActionUtilisateur actionUtilisateur,Request <DemandeDto> request,Demande demande,Category category,String statut,Boolean booleaan){
        NumeroStories dataStories = new NumeroStories();
        dataStories.setCreatedAt(Utilities.getCurrentDate());
        dataStories.setNumero(number);
        dataStories.setProfil(profil);
        dataStories.setIsDeleted(Boolean.FALSE);
        dataStories.setStatus(status);
        dataStories.setStatut(statut);
        dataStories.setTypeNumero(actionUtilisateur.getTypeNumero());
        dataStories.setAdresseIp(request.getIpAddress());
        dataStories.setUser(demande!=null?demande.getUser():null);
        dataStories.setLogin(demande!=null?demande.getUser().getLogin():null);
        dataStories.setMachine(request.getHostName());
        dataStories.setIsMachine(booleaan);
        dataStories.setCategory(category);
        numeroStoriesRepository.save(dataStories);
    }

    public void numeroStoriesSaveLite(String number,Profil profil,Status status,TypeNumero typeNumero,User user,Category category,String statut,Boolean booleaan,Request <DemandeDto> request){
        NumeroStories dataStories = new NumeroStories();
        dataStories.setCreatedAt(Utilities.getCurrentDate());
        dataStories.setNumero(number);
        dataStories.setProfil(profil);
        dataStories.setIsDeleted(Boolean.FALSE);
        dataStories.setStatus(status);
        dataStories.setStatut(statut);
        dataStories.setTypeNumero(typeNumero);
        dataStories.setAdresseIp(request.getIpAddress());
        dataStories.setUser(user);
        dataStories.setLogin(user!=null?user.getLogin():null);
        dataStories.setMachine(request.getHostName());
        dataStories.setIsMachine(booleaan);
        dataStories.setCategory(category);
        numeroStoriesRepository.save(dataStories);
    }


    private String getSalutation(){
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // Déterminez la salutation en fonction de l'heure actuelle
        if(currentTime.isBefore(LocalTime.NOON)){
            return "Bonjour, ";
        }else{
            return "Bonsoir, ";
        }
    }

    public String writeFileName(List <DemandeDto> dataActionLite) throws Exception{
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        ClassPathResource templateClassPathResource = new ClassPathResource("templates/list_of_demande.xlsx");
        InputStream inputStreamFile = templateClassPathResource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStreamFile);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Cell cell = null;
        Row row = null;
        int rowIndex = 1;
        for(DemandeDto demandeDto: dataActionLite){
            // Renseigner les ids
            log.info("--row--"+rowIndex);
            row = sheet.getRow(rowIndex);
            if(row==null){
                row = sheet.createRow(rowIndex);
            }
            cell = getCell(row,0);
            cell.setCellValue(demandeDto.getUserLogin());
            cell = getCell(row,1);
            cell.setCellValue(demandeDto.getMotif());
            cell = getCell(row,2);
            cell.setCellValue(demandeDto.getNumero());
            cell = getCell(row,3);
            cell.setCellValue(demandeDto.getStatusBscs());
            cell = getCell(row,4);
            cell.setCellValue(demandeDto.getStatusCode());
            cell = getCell(row,5);
            cell.setCellValue(demandeDto.getCreatedAt());
            cell = getCell(row,6);
            cell.setCellValue(demandeDto.getTypeDemandeCode());
            rowIndex++;
        }
        // Ajuster les colonnes
        for(int i = 0;i<=Utilities.getColumnIndex("I");i++){
            sheet.autoSizeColumn(i);
        }
        inputStreamFile.close();
        String fileName = Utilities.saveFile("LIST_DEMANDE_NUMBER_"+sdfFileName.format(new Date()),"xlsx",workbook,paramsUtils);
        return Utilities.getFileUrlLink(fileName,paramsUtils);
    }

    public static Cell getCell(Row row,Integer index){
        return (row.getCell(index)==null)?row.createCell(index):row.getCell(index);
    }
}
