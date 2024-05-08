
/*
 * Java business for entity table action_en_masse
 * Created on 2022-10-04 ( Time 11:23:37 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.business;

import ci.smile.simswaporange.dao.constante.Constant;
import ci.smile.simswaporange.dao.entity.Status;

import java.io.*;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import ci.smile.simswaporange.proxy.customizeclass.functions.ObjectUtilities;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDtos;
import ci.smile.simswaporange.utils.dto.customize.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.utils.enums.*;
import ci.smile.simswaporange.utils.contract.IBasicBusiness;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.transformer.*;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
import ci.smile.simswaporange.proxy.service.SimSwapServiceProxyService;

import org.springframework.transaction.annotation.Transactional;

import static org.apache.poi.ss.util.CellUtil.getCell;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/**
 * BUSINESS for table "action_en_masse"
 *
 * @author Geo
 */
@Component
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ActionEnMasseBusiness implements IBasicBusiness <Request <ActionEnMasseDto>, Response <ActionEnMasseDto>>{

    private final UserRepository userRepository;
    private final ActionEnMasseRepository actionEnMasseRepository;
    private final ActionSimswapRepository actionSimswapRepository;
    private final ActionUtilisateurRepository actionUtilisateurRepository;
    private final StatusRepository statusRepository;
    private final ActionParametrableRepository actionParametrableRepository;
    private final AtionToNotifiableRepository ationToNotifiableRepository;
    private final DemandeBusiness demandeBusiness;
    private final ParamsUtils paramsUtils;
    private final ObjectUtilities objectUtilities;
    //	@Autowired
//	private CallApi callApi;
    private final UserBusiness userBusiness;
    private final SimSwapServiceProxyService simSwapServiceProxyService;
    private final CategoryRepository categoryRepository;
    private final FunctionalError functionalError;
    private final NumeroStoriesRepository numeroStoriesRepository;
    private final TypeNumeroRepository typeNumeroRepository;
    private final ProfilFonctionnaliteRepository profilFonctionnaliteRepository;
    private final TypeDemandeRepository typeDemandeRepository;
    private final DemandeRepository demandeRepository;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    @PersistenceContext
    private EntityManager em;

    private Date currentDate;

    /**
     * create ActionEnMasse by using ActionEnMasseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response <ActionEnMasseDto> create(Request <ActionEnMasseDto> request,Locale locale) throws ParseException{
        log.info("----begin create ActionEnMasse-----");
        Response <ActionEnMasseDto> response = new Response <ActionEnMasseDto>();
        List <ActionEnMasse> items = new ArrayList <ActionEnMasse>();
        for(ActionEnMasseDto dto: request.getDatas()){
            // Definir les parametres obligatoires
            Map <String, Object> fieldsToVerify = new HashMap <>();
            fieldsToVerify.put("code",dto.getCode());
            fieldsToVerify.put("libelle",dto.getLibelle());
            fieldsToVerify.put("lienFichier",dto.getLienFichier());
            if(!Validate.RequiredValue(fieldsToVerify).isGood()){
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
                response.setHasError(true);
                return response;
            }
            // Verify if actionEnMasse to insert do not exist
            ActionEnMasse existingEntity = null;
            // verif unique code in db
            existingEntity = actionEnMasseRepository.findByCode(dto.getCode(),false);
            if(existingEntity!=null){
                response.setStatus(functionalError.DATA_EXIST("actionEnMasse code -> "+dto.getCode(),locale));
                response.setHasError(true);
                return response;
            }
            // verif unique code in items to save
            if(items.stream().anyMatch(a->a.getCode().equalsIgnoreCase(dto.getCode()))){
                response.setStatus(functionalError.DATA_DUPLICATE(" code ",locale));
                response.setHasError(true);
                return response;
            }

            // verif unique libelle in db
            existingEntity = actionEnMasseRepository.findByLibelle(dto.getLibelle(),false);
            if(existingEntity!=null){
                response.setStatus(functionalError.DATA_EXIST("actionEnMasse libelle -> "+dto.getLibelle(),locale));
                response.setHasError(true);
                return response;
            }
            // verif unique libelle in items to save
            if(items.stream().anyMatch(a->a.getLibelle().equalsIgnoreCase(dto.getLibelle()))){
                response.setStatus(functionalError.DATA_DUPLICATE(" libelle ",locale));
                response.setHasError(true);
                return response;
            }
            ActionEnMasse entityToSave = null;
            entityToSave = ActionEnMasseTransformer.INSTANCE.toEntity(dto);
            entityToSave.setIdentifiant(dto.getIdentifiant());
            entityToSave.setIsOk(dto.getIsOk());
            entityToSave.setLienImport(dto.getLienImport());
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setIsDeleted(false);
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setIsAutomatically(Boolean.FALSE);

            items.add(entityToSave);
        }

        if(!items.isEmpty()){
            List <ActionEnMasse> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = actionEnMasseRepository.saveAll((Iterable <ActionEnMasse>) items);
            if(itemsSaved==null){
                response.setStatus(functionalError.SAVE_FAIL("actionEnMasse",locale));
                response.setHasError(true);
                return response;
            }
            List <ActionEnMasseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))?ActionEnMasseTransformer.INSTANCE.toLiteDtos(itemsSaved):ActionEnMasseTransformer.INSTANCE.toDtos(itemsSaved);

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

        log.info("----end create ActionEnMasse-----");
        return response;
    }

    /**
     * update ActionEnMasse by using ActionEnMasseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response <ActionEnMasseDto> update(Request <ActionEnMasseDto> request,Locale locale) throws ParseException{
        log.info("----begin update ActionEnMasse-----");
        Response <ActionEnMasseDto> response = new Response <ActionEnMasseDto>();
        List <ActionEnMasse> items = new ArrayList <ActionEnMasse>();
        for(ActionEnMasseDto dto: request.getDatas()){
            // Definir les parametres obligatoires
            Map <String, Object> fieldsToVerify = new HashMap <String, Object>();
            fieldsToVerify.put("id",dto.getId());
            if(!Validate.RequiredValue(fieldsToVerify).isGood()){
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
                response.setHasError(true);
                return response;
            }
            // Verifier si la actionEnMasse existe
            ActionEnMasse entityToSave = null;
            entityToSave = actionEnMasseRepository.findOne(dto.getId(),false);
            if(entityToSave==null){
                response.setStatus(functionalError.DATA_NOT_EXIST("actionEnMasse id -> "+dto.getId(),locale));
                response.setHasError(true);
                return response;
            }

            if(Utilities.notBlank(dto.getCode())){
                entityToSave.setCode(dto.getCode());
            }
            if(Utilities.notBlank(dto.getLibelle())){
                entityToSave.setLibelle(dto.getLibelle());
            }
            if(Utilities.notBlank(dto.getLienFichier())){
                entityToSave.setLienFichier(dto.getLienFichier());
            }
            if(dto.getUpdatedBy()!=null&&dto.getUpdatedBy()>0){
                entityToSave.setUpdatedBy(dto.getUpdatedBy());
            }
            if(dto.getCreatedBy()!=null&&dto.getCreatedBy()>0){
                entityToSave.setCreatedBy(dto.getCreatedBy());
            }
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            entityToSave.setUpdatedBy(request.getUser());
            items.add(entityToSave);
        }

        if(!items.isEmpty()){
            List <ActionEnMasse> itemsSaved = null;
            // maj les donnees en base
            itemsSaved = actionEnMasseRepository.saveAll(items);
            if(itemsSaved==null){
                response.setStatus(functionalError.SAVE_FAIL("actionEnMasse",locale));
                response.setHasError(true);
                return response;
            }
            List <ActionEnMasseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))?ActionEnMasseTransformer.INSTANCE.toLiteDtos(itemsSaved):ActionEnMasseTransformer.INSTANCE.toDtos(itemsSaved);

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

        log.info("----end update ActionEnMasse-----");
        return response;
    }

    /**
     * delete ActionEnMasse by using ActionEnMasseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response <ActionEnMasseDto> delete(Request <ActionEnMasseDto> request,Locale locale){
        log.info("----begin delete ActionEnMasse-----");
        Response <ActionEnMasseDto> response = new Response <ActionEnMasseDto>();
        List <ActionEnMasse> items = new ArrayList <ActionEnMasse>();
        for(ActionEnMasseDto dto: request.getDatas()){
            // Definir les parametres obligatoires
            Map <String, Object> fieldsToVerify = new HashMap <String, Object>();
            fieldsToVerify.put("id",dto.getId());
            if(!Validate.RequiredValue(fieldsToVerify).isGood()){
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
                response.setHasError(true);
                return response;
            }
            // Verifier si la actionEnMasse existe
            ActionEnMasse existingEntity = null;
            existingEntity = actionEnMasseRepository.findOne(dto.getId(),false);
            if(existingEntity==null){
                response.setStatus(functionalError.DATA_NOT_EXIST("actionEnMasse -> "+dto.getId(),locale));
                response.setHasError(true);
                return response;
            }
            existingEntity.setIsDeleted(true);
            items.add(existingEntity);
        }

        if(!items.isEmpty()){
            // supprimer les donnees en base
            actionEnMasseRepository.saveAll(items);
            response.setStatus(functionalError.SUCCESS("",locale));
            response.setHasError(false);
        }

        log.info("----end delete ActionEnMasse-----");
        return response;
    }

    /**
     * get ActionEnMasse by using ActionEnMasseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response <ActionEnMasseDto> getByCriteria(Request <ActionEnMasseDto> request,Locale locale) throws Exception{
        log.info("----begin get ActionEnMasse-----");

        Response <ActionEnMasseDto> response = new Response <ActionEnMasseDto>();
        List <ActionEnMasse> items = actionEnMasseRepository.getByCriteria(request,em,locale);

        if(items!=null&&!items.isEmpty()){
            List <ActionEnMasseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))?ActionEnMasseTransformer.INSTANCE.toLiteDtos(items):ActionEnMasseTransformer.INSTANCE.toDtos(items);

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
            if(Utilities.isNotEmpty(itemsDto)){
                response.setFilePathDoc(writeFileNameActionEnMasse(itemsDto));
            }
            response.setItems(itemsDto);
            response.setCount(actionEnMasseRepository.count(request,em,locale));
            response.setStatus(functionalError.SUCCESS("",locale));
            response.setHasError(false);
        }else{
            response.setStatus(functionalError.DATA_EMPTY("actionEnMasse",locale));
            response.setHasError(false);
            return response;
        }

        log.info("----end get ActionEnMasse-----");
        return response;
    }

    /**
     * get full ActionEnMasseDto by using ActionEnMasse as object.
     *
     * @param dto
     * @param size
     * @param isSimpleLoading
     * @param locale
     * @return
     * @throws Exception
     */
    private ActionEnMasseDto getFullInfos(ActionEnMasseDto dto,Integer size,Boolean isSimpleLoading,Locale locale) throws Exception{
        // put code here
        if(Utilities.isTrue(isSimpleLoading)){
            return dto;
        }
        if(size>1){
            return dto;
        }
        return dto;
    }

    public Response <ActionEnMasseDto> custom(Request <ActionEnMasseDto> request,Locale locale){
        log.info("----begin custom ActionEnMasseDto-----");
        Response <ActionEnMasseDto> response = new Response <ActionEnMasseDto>();

        response.setHasError(false);
        response.setCount(1L);
        response.setItems(Arrays.asList(new ActionEnMasseDto()));
        log.info("----end custom ActionEnMasseDto-----");
        return response;
    }

    @Transactional(rollbackFor={RuntimeException.class,Exception.class})
    public Response <ActionUtilisateurDto> executeOnMasse(Request <ActionUtilisateurDto> request,Locale locale){
        log.info(" ----begin uploadLiteLogementPavillon-----");
        Response <ActionUtilisateurDto> response = new Response <ActionUtilisateurDto>();
        ActionSimswap actionSimswap = actionSimswapRepository.findByLibelle("ACTION EN MASSE",Boolean.FALSE);
        ActionUtilisateurDto dto = request.getData();
        List <String> listToCreate = new ArrayList <>();
        List <String> listToUpdate = new ArrayList <>();
        Status status;
        User findUser;
        ActionEnMasse dataActions;
        String filePathResult = null;

        String ipAddress = null;
        String hostName = null;
        // Definir les parametres obligatoires
        Map <String, Object> fieldsToVerify = new HashMap <>();
        fieldsToVerify.put("identifiant",dto.getIdentifiant());
        fieldsToVerify.put("idStatus",dto.getIdStatus());
        fieldsToVerify.put("typeNumeroId",dto.getTypeNumeroId());
        if(!Validate.RequiredValue(fieldsToVerify).isGood()){
            response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
            response.setHasError(true);
            return response;
        }
        try{
            InetAddress localhost = InetAddress.getLocalHost();
            ipAddress = localhost.getHostAddress();
            hostName = localhost.getHostName();
            System.out.println("Adresse IP : "+ipAddress);
            System.out.println("Nom de la machine : "+hostName);
        }catch(UnknownHostException e){
            e.printStackTrace();
        }

        ActionParametrable actionParametrable = actionParametrableRepository.findByLibelle("OPÉRATION EN MASSE",Boolean.FALSE);
        Validate.EntityNotExiste(actionParametrable);
        status = statusRepository.findOne(dto.getIdStatus(),Boolean.FALSE);
        Validate.EntityNotExiste(status);
        TypeNumero typeNumeroSearch = typeNumeroRepository.findOne(dto.getTypeNumeroId(),Boolean.FALSE);
        Validate.EntityNotExiste(typeNumeroSearch);
        Validate.EntityNotExiste(request.getUser());
        dataActions = actionEnMasseRepository.findByOneIdentifiant(dto.getIdentifiant(),Boolean.FALSE);
        Validate.EntityNotExiste(dataActions);
        if(dto.getIdStatus()!=StatusApiEnum.SERVICE_API_BLOCK_SIM&&dto.getIdStatus()!=StatusApiEnum.SERVICE_API_MISE_EN_MACHINE && ObjectUtils.isEmpty(dataActions.getIsDebloageEnMasse()) &&  dto.getIdStatus()!=StatusApiEnum.DEBLOQUER){
            response.setStatus(functionalError.DISALLOWED_OPERATION("PAS RESERVÉ POUR LE DEBLOCAGE EN MASSE",locale));
            response.setHasError(true);
            return response;
        }
        findUser = userRepository.findOne(request.getUser(),Boolean.FALSE);
        Validate.EntityNotExiste(findUser);
        Validate.EntityNotExiste(findUser.getCategory());
        Validate.EntityNotExiste(findUser.getProfil());
        List <Fonctionnalite> fonctionnalites = profilFonctionnaliteRepository.findFonctionnaliteByProfilId(findUser.getProfil().getId(),Boolean.FALSE);
        boolean bd = false;
        if(Utilities.isNotEmpty(fonctionnalites)){
            bd = fonctionnalites.stream().anyMatch(fonctionnalite->fonctionnalite.getLibelle().equalsIgnoreCase("ADMIN MÉTIER")
                    ||fonctionnalite.getLibelle().equalsIgnoreCase("MANAGER FRAUDE-SÉCURITÉ")||fonctionnalite.getLibelle().equalsIgnoreCase("RESPONSABLE BACK-OFFICE"));
        }
        if((!findUser.getProfil().getLibelle().equalsIgnoreCase("ADMIN MÉTIER")&&!findUser.getProfil().getLibelle().equalsIgnoreCase("RESPONSABLE BACK-OFFICE")&&!findUser.getProfil().getLibelle().equalsIgnoreCase("MANAGER FRAUDE-SÉCURITÉ"))&&bd==Boolean.FALSE){
            response.setStatus(functionalError.DISALLOWED_OPERATION("OPÉRATION INTERDITE",locale));
            response.setHasError(true);
            return response;
        }
        if(ObjectUtils.isNotEmpty(dataActions.getIsDebloageEnMasse()) && dataActions.getIsDebloageEnMasse() == Boolean.TRUE){
            TypeDemande typeDemande = typeDemandeRepository.findByCode(StatusApiEnum.ACCEPTER,Boolean.FALSE);
            Demande demande = demandeRepository.findDemandeByActionEnMasse(dataActions.getId(),Boolean.FALSE);
            if(ObjectUtils.allNotNull(demande, typeDemande)){
                demande.setUpdatedAt(Utilities.getCurrentDate());
                demande.setUpdatedBy(findUser.getId());
                demande.setTypeDemande(typeDemande);
                demande.setUser(findUser);
                demandeRepository.save(demande);
            }
        }

        try{
            ArrayList <String> datasNumero = readFile(dataActions.getLienImport());
            log.info("lien import {}",dataActions.getLienImport());
            List <ActionUtilisateur> dataActionForSave = new ArrayList <>();
            List <Historique> datasHistorique = new ArrayList <>();
            ArrayList <String> newList = Utilities.removeDuplicates(datasNumero);
            log.info("numero trouvé  1 {}",datasNumero);
            List <NumeroStoriesDto> itemsStories = new ArrayList <>();
            if(Utilities.isNotEmpty(newList)){
                log.info("numero trouvé  2 {}",newList);
                LockUnLockFreezeDto numbers = simSwapServiceProxyService.getNumbers(UUID.randomUUID().toString(),newList);
                if(numbers!=null&&Utilities.isNotEmpty(numbers.getData())){
                    numbers.getData().stream().forEach(args->{
                        if(args!=null&&Utilities.notBlank(args.getOfferName())){
                            List <ActionUtilisateur> actionUtilisateurs = new ArrayList <>();
                            if(findUser!=null&&findUser.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.ADMIN_CODE)){
                                Category category = simSwapServiceProxyService.getCategory(args.getOfferName());
                                actionUtilisateurs = actionUtilisateurRepository.findByDepartement(typeNumeroSearch.getId(),category.getId(),AES.encrypt(args.getMsisdn(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                                log.info("numeros trouvé étant Administrateur "+actionUtilisateurs);
                            }
                            if(findUser!=null&&!findUser.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.ADMIN_CODE)){
                                actionUtilisateurs = actionUtilisateurRepository.findByDepartement(typeNumeroSearch.getId(),findUser.getCategory().getId(),AES.encrypt(args.getMsisdn(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                                log.info("numeros trouvé étant Non Administrateur "+actionUtilisateurs);
                            }
                            ActionUtilisateur actionUtilisateur = refreshNumber(actionUtilisateurs);
                            log.info("Trouvé avec un refresh Delete "+actionUtilisateur);

                            boolean b = actionUtilisateur!=null?listToUpdate.add(args.getMsisdn()):listToCreate.add(args.getMsisdn());
                            log.info("valeur de b, {}",b);
                        }
                    });
                }
                Response <List <Result>> result = executeIfActionIsValidated(listToUpdate,listToCreate,findUser,typeNumeroSearch,request,ipAddress,hostName,status,datasHistorique,dataActionForSave,itemsStories,actionSimswap,dto.getIdStatus(),locale, numbers);
                List <Result> resultList = result.getItem();
                log.info("resultTraitementFile {}",resultList);
                filePathResult = extraireFichierExecuteOnMasse(resultList,dataActions,response);
                if(result!=null&&result.isHasError()!=Boolean.TRUE){
                    response.setHasError(Boolean.FALSE);
                    response.setStatus(functionalError.SUCCESS("",locale));
                    response.setNumbers(filePathResult);
                    return response;
                }else{
                    response.setHasError(Boolean.TRUE);
                    response.setStatus(functionalError.DISALLOWED_OPERATION(result.getRaison(),locale));
                    response.setNumbers(filePathResult);
                    response.setRaison(result.getRaison());
                    return response;
                }
            }
            LocalDateTime now = LocalDateTime.now();
            if(status.getId().equals(StatusApiEnum.BLOQUER)){
                String messageAlerteConnexion = Utilities.operationEnMasse(status.getCode(),now,dataActions.getLienFichier(),filePathResult,"EXCEL",findUser.getNom(),findUser.getLogin(),findUser.getProfil().getLibelle(),findUser.getCategory().getLibelle());
                extracted("BLOCAGE EN MASSE",null,findUser,status,findUser.getId(),filePathResult,"SUCCESS",messageAlerteConnexion);
            }
            if(status.getId().equals(StatusApiEnum.MISE_EN_MACHINE)){
                String messageAlerteConnexion = Utilities.operationEnMasse(status.getCode(),now,dataActions.getLienFichier(),filePathResult,"EXCEL",findUser.getNom(),findUser.getLogin(),findUser.getProfil().getLibelle(),findUser.getCategory().getLibelle());
                extracted("MISE_EN_MACHINE EN MASSE",null,findUser,status,findUser.getId(),filePathResult,"SUCCESS",messageAlerteConnexion);
            }

        }catch(Exception e){
            response.setHasError(Boolean.TRUE);
            response.setNumbers(filePathResult);
            response.setStatus(functionalError.DISALLOWED_OPERATION("L'opération a échoué",locale));
            response.setRaison(e.getMessage());
            e.printStackTrace();
            return response;
        }
        response.setNumbers(filePathResult);
        response.setStatus(functionalError.SUCCESS("",locale));
        response.setActionEffectue("Execution en masse");
        return response;
    }

    private String extraireFichierExecuteOnMasse(List <Result> resultList,ActionEnMasse dataActions,Response <ActionUtilisateurDto> response) throws Exception{
        if(Utilities.isNotEmpty(resultList)){
            log.info("la liste est pas vide "+resultList);
            String fileSaved = writeFileName(resultList,null);
            dataActions.setIsOk(Boolean.TRUE);
            dataActions.setLinkDownloadMasse(fileSaved);
            log.info("lien du fichier resultat"+fileSaved);
            actionEnMasseRepository.save(dataActions);
            response.setFilePathDoc(fileSaved);
            return fileSaved;
        }
        return "FICHIER";
    }

    private String extraireFichierExecuteOnMasseCron(List <Result> resultList) throws Exception{
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        if(Utilities.isNotEmpty(resultList)){
            log.info("la liste est pas vide "+resultList);
            String fileSaved = writeFileName(resultList,"UNLOCK");
            ActionEnMasse actionEnMasse = new ActionEnMasse();
            actionEnMasse.setIdentifiant(UUID.randomUUID().toString());
            actionEnMasse.setCreatedAt(Utilities.getCurrentDate());
            actionEnMasse.setIsOk(Boolean.TRUE);
            actionEnMasse.setLienFichier(fileSaved);
            actionEnMasse.setLienImport(fileSaved);
            actionEnMasse.setLinkDownloadMasse(fileSaved);
            actionEnMasse.setCode("BLOQUAGE_DES_NUMEROS_DEBLOQUÉ_APRÈS_24H_"+sdfFileName.format(new Date()));
            actionEnMasse.setLibelle("BLOQUAGE DES NUMEROS DEBLOQUÉ APRÈS 24H");
            actionEnMasse.setIsAutomatically(Boolean.TRUE);
            actionEnMasse.setIsDeleted(Boolean.FALSE);
            actionEnMasse.setLienFichier(fileSaved);
            actionEnMasseRepository.save(actionEnMasse);
            log.info("lien du fichier resultat"+fileSaved);
            return fileSaved;
        }
        return "FICHIER";
    }

    public void lockUnlockNumberAfterTwentyFourHours(List <Result> resultList, Boolean aBoolean, String State) throws Exception{
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        if(Utilities.isNotEmpty(resultList)){
            String fileSaved = writeFileName(resultList,State);
            ActionEnMasse actionEnMasse = new ActionEnMasse();
            actionEnMasse.setIdentifiant(UUID.randomUUID().toString());
            actionEnMasse.setCreatedAt(Utilities.getCurrentDate());
            actionEnMasse.setIsOk(Boolean.TRUE);
            actionEnMasse.setLienFichier(fileSaved);
            actionEnMasse.setLienImport(fileSaved);
            actionEnMasse.setLinkDownloadMasse(fileSaved);
            actionEnMasse.setCode(aBoolean == Boolean.TRUE ? "BLOQUAGE_DES_NUMEROS_DEBLOQUÉ_APRÈS_24H_"+sdfFileName.format(new Date()) : "DETECTION_ET_BLOQUAGE_DES_NUMEROS_ORANGE_MONEY_PDV_"+sdfFileName.format(new Date()));
            actionEnMasse.setLibelle(aBoolean == Boolean.TRUE ? "BLOQUAGE DES NUMEROS DEBLOQUÉ APRÈS 24H  " : "DETECTION ET_BLOQUAGE DES NUMEROS ORANGE_MONEY PDV");
            actionEnMasse.setIsAutomatically(Boolean.TRUE);
            actionEnMasse.setIsDeleted(Boolean.FALSE);
            actionEnMasse.setLienFichier(fileSaved);
            actionEnMasseRepository.save(actionEnMasse);
            log.info("lien du fichier resultat"+fileSaved);
        }
    }

    public void refreshNumberAfterTwentyFourHours(List <Result> resultList) throws Exception{
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        if(Utilities.isNotEmpty(resultList)){
            String fileSaved = writeFileName(resultList,"REFRESH");
            ActionEnMasse actionEnMasse = new ActionEnMasse();
            actionEnMasse.setIdentifiant(UUID.randomUUID().toString());
            actionEnMasse.setCreatedAt(Utilities.getCurrentDate());
            actionEnMasse.setIsOk(Boolean.TRUE);
            actionEnMasse.setLienFichier(fileSaved);
            actionEnMasse.setLienImport(fileSaved);
            actionEnMasse.setLinkDownloadMasse(fileSaved);
            actionEnMasse.setCode("RAFRAICHIR_DES_NUMEROS_APRÈS_24H_"+sdfFileName.format(new Date()));
            actionEnMasse.setLibelle("RAFRAICHIR DES NUMEROS  APRÈS 24H");
            actionEnMasse.setIsAutomatically(Boolean.TRUE);
            actionEnMasse.setIsDeleted(Boolean.FALSE);
            actionEnMasse.setLienFichier(fileSaved);
            actionEnMasseRepository.save(actionEnMasse);
            log.info("lien du fichier resultat"+fileSaved);
        }
    }

    public String writeFileName(List <Result> dataActionLite,String bot) throws Exception{
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        ClassPathResource templateClassPathResource = new ClassPathResource("templates/LIST_NUMERO_ACTION_EN_MASSE.xlsx");
        InputStream inputStreamFile = templateClassPathResource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStreamFile);
        XSSFSheet sheet = workbook.getSheetAt(0); // la 1ere feuille du classeur excel
        Cell cell = null;
        Row row = null;
        int rowIndex = 1;
        for(Result actionUtilisateur: dataActionLite){
            // Renseigner les ids
            log.info("--row--"+rowIndex);
            row = sheet.getRow(rowIndex);
            if(row==null){
                row = sheet.createRow(rowIndex);
            }
            cell = getCell(row,0);
            cell.setCellValue(actionUtilisateur.getNumero());
            if(actionUtilisateur.getStatus()!=null){
                cell = getCell(row,1);
                cell.setCellValue(actionUtilisateur.getLockStatus());
            }
            cell = getCell(row,2);
            cell.setCellValue(actionUtilisateur.getReason());
            cell = getCell(row,3);
            cell.setCellValue(actionUtilisateur.getContractId());
            cell = getCell(row,4);
            cell.setCellValue(actionUtilisateur.getPortNumber());
            cell = getCell(row,5);
            cell.setCellValue(actionUtilisateur.getSerialNumber());
            cell = getCell(row,6);
            cell.setCellValue(actionUtilisateur.getTariffModelCode());
            cell = getCell(row,7);
            cell.setCellValue(actionUtilisateur.getOfferName());
            cell = getCell(row,8);
            cell.setCellValue(actionUtilisateur.getStatus());
            cell = getCell(row,9);
            cell.setCellValue(actionUtilisateur.getActivationDate());
            cell = getCell(row,10);
            cell.setCellValue(actionUtilisateur.isFrozen()==Boolean.TRUE?"OUI":"NON");
            cell = getCell(row,11);
            cell.setCellValue(actionUtilisateur.getLogin());
            cell = getCell(row,12);
            cell.setCellValue(Utilities.getCurrentDateTime());
            rowIndex++;
        }
        // Ajuster les colonnes
        for(int i = 0;i<=Utilities.getColumnIndex("I");i++){
            sheet.autoSizeColumn(i);
        }
        String fileNameTitle = null;
        if(bot!=null&&bot.equalsIgnoreCase("REFRESH")){
            fileNameTitle = "ROUTINE_RAFRAICHISSEMENT_NUMÉROS";
        }
        if(bot!=null&&bot.equalsIgnoreCase("LOCK")){
            fileNameTitle = "ROUTINE_BLOQUÉ_NUMÉROS_DÉBLOQUÉ_APRÈS_24H";
        }
        if(bot!=null&&bot.equalsIgnoreCase("DETECTION")){
            fileNameTitle = "DETECTION_ET_BLOQUAGE_DES_NUMEROS_ORANGE_MONEY_PDV_";
        }
        inputStreamFile.close();
        String fileName = Utilities.saveFile(fileNameTitle==null?"LIST_NUMBER_AFTER_EXECUTE_MASSE_FILE"+sdfFileName.format(new Date()):fileNameTitle+sdfFileName.format(new Date()),"xlsx",workbook,paramsUtils);

        return Utilities.getFileUrlLink(fileName,paramsUtils);
    }

    @Transactional(rollbackFor={RuntimeException.class,Exception.class})
    public Response <ActionEnMasseDto> uploadOneFile(String fileNameFull,String identifiant,Integer userId,Integer idStatus){
        ArrayList <String> newList;
        Locale locale = new Locale("fr");
        Response <ActionEnMasseDto> response = new Response <>();
        User findUser = userRepository.findOne(userId,Boolean.FALSE);
        Validate.EntityNotExiste(findUser);
        Validate.EntityNotExiste(findUser.getProfil());
        Validate.EntityNotExiste(findUser.getCategory());
        TypeNumero typeNumero = typeNumeroRepository.findByLibelle(StatusApiEnum.CORAIL,Boolean.FALSE);
        Validate.EntityNotExiste(typeNumero);
        Status status = statusRepository.findOne(idStatus,Boolean.FALSE);
        Validate.EntityNotExiste(status);
        TypeDemande typeDemande = typeDemandeRepository.findByCode(StatusApiEnum.INITIALISER,Boolean.FALSE);
        identifiant = UUID.randomUUID().toString();
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        String code = "File_"+sdfFileName.format(new Date());
        String libelle = "LIST_NUMBER_SINCE_IMPORT_"+sdfFileName.format(new Date());
        ActionEnMasse searchOne = new ActionEnMasse();
        String filePath = null;
        try{
            FileInputStream fileInputStream = new FileInputStream(fileNameFull);
            Iterator <Row> iterator = null;
            iterator = recupererFichier(fileNameFull,fileInputStream,paramsUtils,sdfFileName,identifiant,userId,searchOne,libelle,code);
            Set<String> datasNumero = new HashSet <>();
            ecrireLeFichierPourAvoirUneListeDeNumero(iterator,datasNumero);
            if(datasNumero!=null&&ObjectUtils.isNotEmpty(datasNumero)){
                XSSFWorkbook workbook = fichierDexportation(datasNumero,fileInputStream,findUser);
                String fileName = Utilities.saveFile("LIST_NUMBER_AFTER_IMPORT_"+sdfFileName.format(new Date()),"xlsx",workbook,paramsUtils);
                filePath = Utilities.getFileUrlLink(fileName,paramsUtils);
                response.setFilePathDoc(filePath);
                searchOne.setLinkDownload(filePath);
                searchOne.setLienFichier(filePath);
                searchOne.setIsAutomatically(Boolean.FALSE);
                searchOne.setLienImport(paramsUtils.getPathDossier()+fileName);
                searchOne.setIsOk(Boolean.FALSE);
                actionEnMasseRepository.save(searchOne);
                LocalDateTime now = LocalDateTime.now();
                String messageAlerteConnexion = Utilities.messageImportationFichier(now,fileName,Utilities.getFileUrlLink(fileName,paramsUtils),"EXCEL",findUser.getNom(),findUser.getLogin(),findUser.getProfil().getLibelle(),findUser.getCategory().getLibelle());
                extracted("IMPORTATION FICHIER",null,findUser,null,userId,Utilities.getFileUrlLink(fileName,paramsUtils),"SUCCESS",messageAlerteConnexion);
                response.setHasError(Boolean.FALSE);
            }else{
                response.setStatus(functionalError.FILE_GENERATION_ERROR("VIDE",locale));
                response.setHasError(Boolean.TRUE);
                response.setActionEffectue("Importation Numero fichier EXCEL");
                return response;
            }
            List <Fonctionnalite> fonctionnalites = profilFonctionnaliteRepository.findFonctionnaliteByProfilId(findUser.getProfil().getId(),Boolean.FALSE);
            boolean bd = false;
            List<FonctionnaliteDto> newFonctionnalities = new ArrayList <>();
            if(Utilities.isNotEmpty(fonctionnalites)){
                userBusiness.traitementPourRemonterDesFonctionnalite(fonctionnalites, newFonctionnalities);
                bd = newFonctionnalities.stream().anyMatch(fonctionnalite->fonctionnalite.getLibelle().equalsIgnoreCase("UTILISATEUR BACK-OFFICE"));
            }
            Validate.validateObject(filePath,Constant.FILEGENERATIONERROR);
            log.info(findUser.getProfil().getLibelle() + " " + bd);
            if((findUser.getProfil().getLibelle().equalsIgnoreCase("UTILISATEUR BACK-OFFICE")||bd==Boolean.TRUE) && status.getCode().equalsIgnoreCase(StatusApiEnum.DEBLOCK)){
                Demande demande = new Demande();
                searchOne.setIsDebloageEnMasse(Boolean.TRUE);
                actionEnMasseRepository.save(searchOne);
                demande.setActionEnMasse(searchOne);
                demande.setUser(findUser);
                demande.setNumero(filePath);
                demande.setCreatedAt(Utilities.getCurrentDate());
                demande.setMotif("ISSUE D'UN TRAITEMENT EN MASSE");
                demande.setStatus(status);
                demande.setIsDeleted(Boolean.FALSE);
                demande.setTypeNumero(typeNumero);
                demande.setTypeDemande(typeDemande);
                demande.setCategory(findUser.getCategory());
                demandeRepository.save(demande);
            }
            log.info("----end exportNumber -----");

        }catch(Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
        }

        //savedLogs(userId, actionSimswap, findUser, searchOne);
        response.setStatus(functionalError.SUCCESS("",locale));
        return response;
    }

    private static XSSFWorkbook fichierDexportation(Set<String> newList,FileInputStream fileInputStream,User user) throws IOException{
        ClassPathResource templateClassPathResource = new ClassPathResource("templates/Liste_Numero_Exporte.xlsx");
        InputStream inputStreamFile = templateClassPathResource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStreamFile);
        XSSFSheet sheet = workbook.getSheetAt(0); // la 1ere feuille du classeur excel
        Cell cell = null;
        Row row = null;
        int rowIndex = 1;
        for(String numero: newList){
            // Renseigner les ids
            log.info("--row--"+rowIndex);
            row = sheet.getRow(rowIndex);
            if(row==null){
                row = sheet.createRow(rowIndex);
            }
            cell = getCell(row,0);
            cell.setCellValue(Utilities.notBlank(numero)?numero:"");

            if(!Utilities.notBlank(numero)){
                cell = getCell(row,1);
                cell.setCellValue(StatusApiEnum.ECHOUER);
            }else{
                cell = getCell(row,1);
                cell.setCellValue(StatusApiEnum.SUCCESS);
            }
            cell = getCell(row,2);
            cell.setCellValue(user.getLogin());
            cell = getCell(row,3);
            cell.setCellValue(user.getCategory().getLibelle());
            cell = getCell(row,4);
            cell.setCellValue(user.getProfil().getLibelle());
            cell = getCell(row,5);
            cell.setCellValue(Utilities.getCurrentDateTime());
            rowIndex++;
        }
        // Ajuster les colonnes
        for(int i = 0;i<=Utilities.getColumnIndex("I");i++){
            sheet.autoSizeColumn(i);
        }
        fileInputStream.close();
        return workbook;
    }

//    private void savedLogs(Integer userId, ActionSimswap actionSimswap, User findUser, ActionEnMasse searchOne) {
//        Historique dataHistorique = new Historique();
//        dataHistorique.setActionSimswap(actionSimswap);
//        dataHistorique.setCreatedAt(Utilities.getCurrentDate());
////				dataHistorique.setIdStatus(StatusApiEnum.SERVICE_API_BLOCK_SIM);
//        dataHistorique.setActionService("Numéro importé");
//        dataHistorique.setIsDeleted(Boolean.FALSE);
//        dataHistorique.setIdUser(userId);
//        dataHistorique.setCreatedBy(userId);
//        dataHistorique.setNom(findUser != null ? findUser.getNom() : "");
//        dataHistorique.setPrenom(findUser != null ? findUser.getPrenom() : "");
//        dataHistorique.setLogin(findUser != null ? findUser.getLogin() : "");
//        historiqueRepository.save(dataHistorique);
//        Tache tache = new Tache();
//        tache.setCreatedBy(userId);
//        tache.setCreatedAt(Utilities.getCurrentDate());
//        tache.setDemande(null);
//        tache.setTypeDemande(null);
//        tache.setUser(findUser);
//        tache.setActionSimswap(actionSimswap);
//        tache.setDateDemande(null);
//        tache.setStatus(null);
//        tache.setIsDeleted(false);
//        tache.setActionEnMasse(searchOne);
//        tacheRepository.save(tache);
//    }

    private void enregistrerLoperationDeTraitement(String file_name_full,String identifiant,Integer userId,ActionEnMasse searchOne,String libelle,String code,String lienImport){
        searchOne.setUpdatedAt(Utilities.getCurrentDate());
        searchOne.setIdentifiant(identifiant);
        searchOne.setIsDeleted(Boolean.FALSE);
        searchOne.setLibelle(libelle);
        searchOne.setCreatedBy(userId);
        searchOne.setLienImport(lienImport);
        searchOne.setCode(code);
        searchOne.setCreatedAt(Utilities.getCurrentDate());
        searchOne.setIsOk(Boolean.FALSE);
        searchOne.setIsAutomatically(Boolean.FALSE);
        actionEnMasseRepository.save(searchOne);
    }

    @SneakyThrows
    private Iterator <Row> recupererFichier(String file_name_full,FileInputStream fileInputStream,ParamsUtils paramsUtils,SimpleDateFormat sdfFileName,String identifiant,Integer userId,ActionEnMasse searchOne,String libelle,String code) throws IOException{
        Iterator <Row> iterator;
        String directoryPath = new File(file_name_full).getParent(); // Obtient le répertoire parent de file_name_full
        String fileName = "LIST_NUMBER_AFTER_IMPORT_"+sdfFileName.format(new Date());
        String fileSaved = "LIST_NUMBER_AFTER_IMPORT_"+sdfFileName.format(new Date())+".xlsx";
        String filePath = Paths.get(directoryPath,fileName).toString();
        String subDirectory = directoryPath+"/texts/";
        if(Utilities.getImageExtension(file_name_full).equalsIgnoreCase("xls")){
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            String fileNameHSX = Utilities.saveFileHssWork(filePath,"xlsx",workbook,paramsUtils);
            String savedFileNameHSX = Paths.get(subDirectory,fileNameHSX).toString();
            iterator = sheet.iterator();
            enregistrerLoperationDeTraitement(file_name_full,identifiant,userId,searchOne,libelle,code,savedFileNameHSX);
        }else{
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            iterator = sheet.iterator();
            String fileNameXLSX = Utilities.saveFile(filePath,"xlsx",workbook,paramsUtils);
            String savedFileNameXSLX = Paths.get(subDirectory,fileNameXLSX).toString();

            enregistrerLoperationDeTraitement(file_name_full,identifiant,userId,searchOne,libelle,code,savedFileNameXSLX);
        }
        return iterator;
    }

    private static void ecrireLeFichierPourAvoirUneListeDeNumero(Iterator <Row> iterator, Set<String> set
){
        while(iterator.hasNext()){
            Cell cell = null;
            final Row currentRow = iterator.next();
            if(currentRow.getRowNum()>=1){
                cell = getCell(currentRow,0);
                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        System.out.println(cell.getStringCellValue());
                        String numero = cell.getStringCellValue().trim();
                        set.add(numero);
                        break;
                }
            }
        }
    }

    // @Scheduled(cron = "${app.update.status.transaction.tagues}")
    @Transactional(rollbackFor={RuntimeException.class,Exception.class})
    public void cronReadFileFromTomcatDirectory() throws IOException{
        log.info("----begin uploadLiteLogementPavillon-----");
        String file_name_full = paramsUtils.getRootFilesPathTomcat();
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MM_yyyy");
        String desiredFileName = "File_"+sdfFileName.format(new Date());
        String directoryPath = "/srv/data/";
        File directory = new File(directoryPath);
        // Vérifiez si le répertoire existe
        try{
            if(directory.exists()&&directory.isDirectory()){
                File[] files = directory.listFiles(new FilenameFilter(){
                    @Override
                    public boolean accept(File dir,String name){
                        return name.equals(desiredFileName);
                    }
                });
                if(files!=null&&files.length>0){
                    Iterator <Row> iterator = null;
                    for(File file: files){
                        FileInputStream fileInputStream = new FileInputStream(new File(file.getName()));
                        if(Utilities.getImageExtension(file_name_full).equalsIgnoreCase("xls")){
                            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
                            HSSFSheet sheet = workbook.getSheetAt(0);
                            iterator = (Iterator <Row>) sheet.iterator();
                        }else{
                            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                            XSSFSheet sheet = workbook.getSheetAt(0);
                            iterator = (Iterator <Row>) sheet.iterator();
                        }
                    }
                    ArrayList <_NumerosBscs> arrayList = new ArrayList <>(lireFichier(iterator));
                    List <_NumerosBscs> bscs = Utilities.removeDuplicates(arrayList);
                    if(Utilities.isNotEmpty(bscs)){
                        savefromBscsFile(bscs);
                    }
                }
            }
        }catch(Exception e){
        }

    }

    public List <_NumerosBscs> lireFichier(Iterator <Row> iterator){
        List <_NumerosBscs> _NumerosBscs = new ArrayList <>();
        while(iterator.hasNext()){
            _NumerosBscs _numerosBscsDto = new _NumerosBscs();
            Cell cell = null;
            final Row currentRow = iterator.next();
            int value = currentRow.getRowNum();
            if(value>=1){
                cell = getCell(currentRow,0);
                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        String numero = cell.getStringCellValue().trim();
                        _numerosBscsDto.setNumero(numero);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        String numeroNumeric = cell.getStringCellValue().trim();
                        _numerosBscsDto.setNumero(numeroNumeric);
                        break;
                }
                cell = getCell(currentRow,1);
                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        String isMachine = cell.getStringCellValue().trim();
                        _numerosBscsDto.setNumeroMachine(isMachine);
                        break;
                }
                cell = getCell(currentRow,2);
                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        String statusBlocage = cell.getStringCellValue().trim();
                        _numerosBscsDto.setStatus(statusBlocage);
                        break;
                }
                cell = getCell(currentRow,3);
                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        String coId = cell.getStringCellValue().trim();
                        _numerosBscsDto.setCoId(coId);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        _numerosBscsDto.setCoId(String.valueOf(cell.getStringCellValue()));
                }
                cell = getCell(currentRow,4);
                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        String portNum = cell.getStringCellValue().trim();
                        _numerosBscsDto.setPortNum(portNum);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        _numerosBscsDto.setPortNum(String.valueOf(cell.getStringCellValue()));
                        break;
                }
                cell = getCell(currentRow,5);
                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        _numerosBscsDto.setSmSerialNum(String.valueOf(cell.getStringCellValue()));
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        String cellValueInString = new BigDecimal(cell.getNumericCellValue()).toPlainString();
                        _numerosBscsDto.setSmSerialNum(cellValueInString);
                        break;
                }
                cell = getCell(currentRow,6);
                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        _numerosBscsDto.setOffre(String.valueOf(cell.getStringCellValue()));
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        String offre = new BigDecimal(cell.getNumericCellValue()).toPlainString();
                        _numerosBscsDto.setOffre(offre);
                        break;
                }
                cell = getCell(currentRow,7);
                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        _numerosBscsDto.setDateActivation(cell.getStringCellValue()!=null?String.valueOf(cell.getStringCellValue()).trim():null);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        Date dateFrom = cell.getDateCellValue();
                        _numerosBscsDto.setDateActivation(dateFrom!=null?dateTimeFormat.format(dateFrom):null);
                        break;
                }
                cell = getCell(currentRow,8);
                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        _numerosBscsDto.setStatusTelco(cell.getStringCellValue()!=null?String.valueOf(cell.getStringCellValue()):null);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        String offre = new BigDecimal(cell.getNumericCellValue()).toPlainString();
                        _numerosBscsDto.setStatusTelco(offre!=null?offre:null);

                        break;
                }

            }
            _NumerosBscs.add(_numerosBscsDto);
        }

        return _NumerosBscs;
    }

    public void savefromBscsFile(List <_NumerosBscs> numerosBscs) throws ParseException{

        for(_NumerosBscs bscsToSave: numerosBscs){
            Status status = statusRepository.findByCode(bscsToSave.getStatus().equalsIgnoreCase("BLOQUE")?StatusEnum.BLOQUER:StatusEnum.DEBLOQUER,Boolean.FALSE);
            String numero = AES.decrypt(bscsToSave.getNumero(),StatusApiEnum.SECRET_KEY);
            String encryptNumber = AES.encrypt(numero,StatusApiEnum.SECRET_KEY);
            TypeNumero typeNumero = typeNumeroRepository.findByLibelle("CORAIL",Boolean.FALSE);
            List <ActionUtilisateur> numeroBscsIfFound = actionUtilisateurRepository.findByNumeros(typeNumero.getId(),encryptNumber,Boolean.FALSE);
            if(!Utilities.isNotEmpty(numeroBscsIfFound)){
                NumeroStories numeroStoriesBscs = new NumeroStories();
                numeroStoriesBscs.setNumero(numero);
                numeroStoriesBscs.setIsMachine(bscsToSave.getNumeroMachine().equalsIgnoreCase("NON")?Boolean.FALSE:Boolean.TRUE);
                numeroStoriesBscs.setStatus(status);
                numeroStoriesBscs.setIsDeleted(Boolean.FALSE);
                numeroStoriesBscs.setCreatedAt(Utilities.getCurrentDate());
                numeroStoriesBscs.setContractId(bscsToSave.getCoId());
                numeroStoriesBscs.setPortNumber(bscsToSave.getPortNum());
                numeroStoriesBscs.setOfferName(bscsToSave.getOffre());
                numeroStoriesBscs.setSerialNumber(bscsToSave.getSmSerialNum());
                numeroStoriesBscs.setTypeNumero(typeNumero);
                numeroStoriesRepository.save(numeroStoriesBscs);
                ActionUtilisateur numeroBscs = new ActionUtilisateur();
                numeroBscs.setOfferName(bscsToSave.getOffre());
                numeroBscs.setStatusBscs(bscsToSave.getStatusTelco()!=null?bscsToSave.getStatusTelco():" ");
                numeroBscs.setNumero(encryptNumber);
                numeroBscs.setIsMachine(bscsToSave.getNumeroMachine()!=null?bscsToSave.getNumeroMachine().equalsIgnoreCase("NON")?Boolean.FALSE:Boolean.TRUE:null);
                numeroBscs.setStatus(status);
                numeroBscs.setCreatedAt(Utilities.getCurrentDate());
                numeroBscs.setFromBss(Boolean.TRUE);
                numeroBscs.setActivationDate(Utilities.notBlank(bscsToSave.getDateActivation())?bscsToSave.getDateActivation().contains(":")?dateTimeFormat.parse(bscsToSave.getDateActivation()):dateFormat.parse(bscsToSave.getDateActivation()):null);
                numeroBscs.setIsDeleted(Boolean.FALSE);
                numeroBscs.setContractId(bscsToSave.getCoId());
                numeroBscs.setPortNumber(bscsToSave.getPortNum());
                numeroBscs.setOfferName(bscsToSave.getOffre());
                numeroBscs.setSerialNumber(bscsToSave.getSmSerialNum());
                numeroBscs.setTypeNumero(typeNumero);
                actionUtilisateurRepository.save(numeroBscs);
            }
            extracted("IMPORTATION NUMERO DEPUIS REPERTOIRE TOMCAT",null,null,null,0,null,"SUCCESS","les numéros ont été importés depuis le répertoire Tomcat avec succès. Tout semble en ordre !");
        }
    }

    public void deletedActionUser(){
        log.info("debut du service");
        List <ActionUtilisateur> byIdCategory = actionUtilisateurRepository.findByIdCategory(3,Boolean.FALSE);
        log.info("la taille de l'objet final , {}",byIdCategory.size());
        if(Utilities.isNotEmpty(byIdCategory)){
            byIdCategory.parallelStream().forEach(arg->{
                arg.setIsDeleted(Boolean.TRUE);
                actionUtilisateurRepository.save(arg);
            });
        }
    }

    private void extracted(String libelle,User userDemande,User userValide,Status status,Integer createdBy,String number,String statusAction,String messageAlerte){
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
        ationToNotifiableRepository.save(ationToNotifiable);
    }

    public Response <ActionEnMasseDto> timeStamp(Request <ActionEnMasseDto> request,Locale locale){
        log.info("----begin custom ActionEnMasseDto-----");
        Response <ActionEnMasseDto> response = new Response <ActionEnMasseDto>();
        ActionEnMasseDto dto = request.getData();
        // Definir les parametres obligatoires
        Map <String, Object> fieldsToVerify = new HashMap <String, Object>();
        fieldsToVerify.put("identifiant",dto.getIdentifiant());
        if(!Validate.RequiredValue(fieldsToVerify).isGood()){
            response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
            response.setHasError(true);
            return response;
        }
        ActionEnMasse findOne = actionEnMasseRepository.findByOneIdentifiant(dto.getIdentifiant(),Boolean.FALSE);
        if(findOne!=null){
            if(findOne.getError()!=null){
                if(findOne.getError()==false){
                    if(findOne.getIsOk()==Boolean.TRUE){
                        ActionEnMasseDto data = new ActionEnMasseDto();
                        data.setLienFichier(findOne.getLienFichier());
                        data.setIdentifiant(dto.getIdentifiant());
                        if(findOne.getPutOf()!=null){
                            data.setPutOf(findOne.getPutOf());
                            data.setLinkDownloadMasse(findOne.getLinkDownloadMasse());
                        }
                        if(findOne.getPutOn()!=null){
                            data.setPutOn(findOne.getPutOn());
                            data.setLinkDownload(findOne.getLinkDownload());
                        }
                        data.setIsOk(Boolean.TRUE);
                        response.setItem(data);
                        response.setCount(1L);
                        response.setHasError(Boolean.FALSE);
                    }else{
                        ActionEnMasseDto data = new ActionEnMasseDto();
                        data.setIdentifiant(dto.getIdentifiant());
                        data.setIsOk(Boolean.FALSE);
                        response.setItem(data);
                        response.setHasError(Boolean.FALSE);
                    }
                }
            }else{
                ActionEnMasseDto data = new ActionEnMasseDto();
                data.setIdentifiant(dto.getIdentifiant());
                data.setIsOk(Boolean.FALSE);
                response.setItem(data);
                response.setHasError(Boolean.TRUE);
            }
        }
        log.info("----end timeStamp ActionEnMasseDto-----");
        return response;
    }

    //@Scheduled(cron = "0 0 5 * * *")
    public void cronLockAllUnclockNumber() throws Exception{
        Long dureeBlocage = paramsUtils.getDureeBlocage();
        Status statusBloque = statusRepository.findByCode(StatusApiEnum.BLOCk,false);
        List <String> collect = new ArrayList <>();
        if(statusBloque==null){
            throw new RuntimeException("Status "+StatusApiEnum.BLOCk+" not exist");
        }
        List <Result> results = new ArrayList <>();
        Status statusDebloque = statusRepository.findByCode(StatusApiEnum.DEBLOCK,false);
        if(statusDebloque==null){
            throw new RuntimeException("Status "+StatusApiEnum.DEBLOCK+" not exist");
        }
        List <ActionUtilisateur> listDebloque = actionUtilisateurRepository.findByIdStatus(statusDebloque.getId(),false);
        if(listDebloque.isEmpty()){
            throw new RuntimeException("Liste vide");
        }
        if(Utilities.isNotEmpty(listDebloque)){
            listDebloque.forEach(arg->{
                currentDate = new Date();
                Date dateDeDeblocage = arg.getUpdatedAt();
                if(dateDeDeblocage==null){
                    throw new RuntimeException("date not exist");
                }
                LocalDateTime localDateTime1 = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime localDateTime2 = dateDeDeblocage.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                Duration duration = Duration.between(localDateTime2,localDateTime1);
                long heure = duration.toHours();
                if(heure>=dureeBlocage){
                    collect.add(AES.decrypt(arg.getNumero(),StatusApiEnum.SECRET_KEY));
                }
            });
        }
        List <String> numbersToCreateOrUpdate = new ArrayList <>();
        if(Utilities.isNotEmpty(listDebloque)&&Utilities.isNotEmpty(collect)){
            numbersToCreateOrUpdate.addAll(collect);
        }
        if(Utilities.isNotEmpty(numbersToCreateOrUpdate)){
            numbersToCreateOrUpdate.stream().forEach(lockNumber->{
                List <String> numberListLock = new ArrayList <>();
                numberListLock.add(lockNumber);
                LockUnLockFreezeDtos lockUnLockFreezeDtos = simSwapServiceProxyService.lockNumber(numberListLock,UUID.randomUUID().toString());
                if(lockUnLockFreezeDtos!=null&&lockUnLockFreezeDtos.getStatus().equalsIgnoreCase("200")){
                    log.info("est 200 {}",lockUnLockFreezeDtos);
                    if(Utilities.isNotEmpty(lockUnLockFreezeDtos.getData())){
                        TypeNumero typeNumero = typeNumeroRepository.findByLibelle(StatusApiEnum.CORAIL,Boolean.FALSE);
                        lockUnLockFreezeDtos.getData().stream().forEach(lockUnLockFreezeDtoslockUnLockFreezeDtos->{
                            List <ActionUtilisateur> actionUtilisateursList = actionUtilisateurRepository.findByNumeroAll(typeNumero.getId(),AES.encrypt(lockNumber,StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                            log.info("resultat des numeros",actionUtilisateursList);
                            if(Utilities.isNotEmpty(actionUtilisateursList)){
                                log.info("a trouvé un numero");
                                actionUtilisateursList.stream().forEach(actionUtilisateur->{
                                    List <Result> result2 = updateCronBatch(actionUtilisateur,lockUnLockFreezeDtos.getMessage());
                                    if(Utilities.isNotEmpty(result2)){
                                        results.addAll(result2);
                                    }
                                });
                            }
                        });
                    }
                }
                listeDesCasdERREUR(null,statusDebloque,lockUnLockFreezeDtos,results,numbersToCreateOrUpdate);
            });
        }
        extraireFichierExecuteOnMasseCron(results);
    }

    public void cronLockAllUnclockNumberService(Integer user) throws Exception{
        Long dureeBlocage = paramsUtils.getDureeBlocage();
        Status statusBloque = statusRepository.findByCode(StatusApiEnum.BLOCk,false);
        User existingUser = userRepository.findOne(user,Boolean.FALSE);
        List <String> collect = new ArrayList <>();
        if(statusBloque==null){
            throw new RuntimeException("Status "+StatusApiEnum.BLOCk+" not exist");
        }
        List <Result> results = new ArrayList <>();
        Status statusDebloque = statusRepository.findByCode(StatusApiEnum.DEBLOCK,false);
        if(statusDebloque==null){
            throw new RuntimeException("Status "+StatusApiEnum.DEBLOCK+" not exist");
        }
        List <ActionUtilisateur> listDebloque = actionUtilisateurRepository.findByIdStatus(statusDebloque.getId(),false);
        if(listDebloque.isEmpty()){
            throw new RuntimeException("Liste vide");
        }
        if(Utilities.isNotEmpty(listDebloque)){
            listDebloque.forEach(arg->{
                currentDate = new Date();
                Date dateDeDeblocage = arg.getUpdatedAt();
                if(dateDeDeblocage==null){
                    throw new RuntimeException("date not exist");
                }
                LocalDateTime localDateTime1 = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime localDateTime2 = dateDeDeblocage.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                Duration duration = Duration.between(localDateTime2,localDateTime1);
                long heure = duration.toHours();
                if(heure>=dureeBlocage){
                    collect.add(AES.decrypt(arg.getNumero(),StatusApiEnum.SECRET_KEY));
                }
            });
        }
        List <String> numbersToCreateOrUpdate = new ArrayList <>();
        if(Utilities.isNotEmpty(listDebloque)&&Utilities.isNotEmpty(collect)){
            numbersToCreateOrUpdate.addAll(collect);
        }
        if(Utilities.isNotEmpty(numbersToCreateOrUpdate)){
            numbersToCreateOrUpdate.stream().forEach(lockNumber->{
                List <String> numberListLock = new ArrayList <>();
                numberListLock.add(lockNumber);
                LockUnLockFreezeDtos lockUnLockFreezeDtos = simSwapServiceProxyService.lockNumber(numberListLock,UUID.randomUUID().toString());
                if(lockUnLockFreezeDtos!=null&&lockUnLockFreezeDtos.getStatus().equalsIgnoreCase("200")){
                    log.info("est 200 {}",lockUnLockFreezeDtos);
                    if(Utilities.isNotEmpty(lockUnLockFreezeDtos.getData())){
                        TypeNumero typeNumero = typeNumeroRepository.findByLibelle(StatusApiEnum.CORAIL,Boolean.FALSE);
                        lockUnLockFreezeDtos.getData().stream().forEach(lockUnLockFreezeDtoslockUnLockFreezeDtos->{
                            List <ActionUtilisateur> actionUtilisateursList = actionUtilisateurRepository.findByNumeroAll(typeNumero.getId(),AES.encrypt(lockNumber,StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                            log.info("resultat des numeros",actionUtilisateursList);
                            if(Utilities.isNotEmpty(actionUtilisateursList)){
                                log.info("a trouvé un numero, ",actionUtilisateursList);
                                actionUtilisateursList.stream().forEach(actionUtilisateur->{
                                    List <Result> result2 = updateCronBatch(actionUtilisateur,lockUnLockFreezeDtos.getMessage());
                                    if(Utilities.isNotEmpty(result2)){
                                        results.addAll(result2);
                                    }
                                });
                            }
                        });
                    }
                }
                listeDesCasdERREUR(existingUser,statusDebloque,lockUnLockFreezeDtos,results,numbersToCreateOrUpdate);
            });
        }
        extraireFichierExecuteOnMasseCron(results);
    }


    public ArrayList <String> readFile(String path) throws IOException{
        ArrayList <String> datasNumero = new ArrayList <String>();
        Iterator <Row> iterator = readFileXls(path);
        while(iterator.hasNext()){
            Cell cell = null;
            final Row currentRow = iterator.next();
            if(currentRow.getRowNum()>=1){
                cell = getCell(currentRow,0);
                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        System.out.println(cell.getStringCellValue());
                        String numero = cell.getStringCellValue().trim();
                        datasNumero.add(numero);
                        break;
                }
            }
        }
        return datasNumero;
    }

    public Iterator <Row> readFileXls(String path) throws IOException{
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        Iterator <Row> iterator = null;
        if(Utilities.getImageExtension(path).equalsIgnoreCase("xls")){
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            iterator = (Iterator <Row>) sheet.iterator();
        }else{
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            iterator = (Iterator <Row>) sheet.iterator();
        }
        fileInputStream.close();
        return iterator;
    }

    @SneakyThrows
    private List <ActionUtilisateur> extractData(Iterator <Row> iterator){
        List <ActionUtilisateur> ToSavedActionUser = new ArrayList <>();
        TypeNumero typeNumero = typeNumeroRepository.findByLibelle("CORAIL",Boolean.FALSE);
        while(iterator.hasNext()){
            final Row currentRow = iterator.next();
            int value = currentRow.getRowNum();
            if(value>=1){
                ActionUtilisateur createSavedData = verifyDonnéeEntranteAvantDePersister(currentRow,typeNumero);
                ToSavedActionUser.add(createSavedData);
                log.info("------------------- <> "+createSavedData.toString());
            }
        }
        return ToSavedActionUser;
    }


    @SneakyThrows
    private List <ActionUtilisateurDto> savedExtractedDate(List <DataFromExelFileDto> data){
        List <ActionUtilisateur> ToSavedActionUser = new ArrayList <>();
        TypeNumero typeNumero = typeNumeroRepository.findByLibelle("BSCS",Boolean.FALSE);
        List <ActionUtilisateurDto> actionReturn = new ArrayList <>();
        if(Utilities.isNotEmpty(data)){
            if(data.size()>200){
                int sizeToIterate = ListUtils.partition(data,200).size();
                if(sizeToIterate>=0){
                    for(int i = 0;i<sizeToIterate;i++){
                        List <DataFromExelFileDto> currentBatch = ListUtils.partition(data,200).get(i);
                        if(Utilities.isNotEmpty(currentBatch)){
                            for(DataFromExelFileDto dataFromExelFileDto: currentBatch){
                                boolean b = Boolean.FALSE;
                                if(Utilities.notBlank(dataFromExelFileDto.getNumeroMachine())){
                                    b = dataFromExelFileDto.getNumeroMachine().equalsIgnoreCase("OUI")?Boolean.TRUE:Boolean.FALSE;
                                }
                                Status status = statusRepository.findByCode(dataFromExelFileDto.getStatutBlocage(),Boolean.FALSE);
                                List <ActionUtilisateur> actionUtilisateur = actionUtilisateurRepository.findByNumeroAll(typeNumero.getId(),AES.encrypt(dataFromExelFileDto.getNumero(),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                                if(!Utilities.isNotEmpty(actionUtilisateur)){
                                    ActionUtilisateur savedData = new ActionUtilisateur();
                                    savedData.setActivationDate(dataFromExelFileDto.getDateActivation());
                                    savedData.setContractId(dataFromExelFileDto.getCoId());
                                    savedData.setCreatedAt(Utilities.getCurrentDate());
                                    savedData.setIsDeleted(Boolean.FALSE);
                                    savedData.setDate(currentDate);
                                    savedData.setFromBss(Boolean.TRUE);
                                    savedData.getEmpreinte();
                                    savedData.setIsExcelNumber(Boolean.TRUE);
                                    savedData.setPortNumber(dataFromExelFileDto.getPortNum());
                                    savedData.setSerialNumber(dataFromExelFileDto.getSmSerialNum());
                                    savedData.setIsMachine(b);
                                    savedData.setStatus(status);
                                    savedData.setOfferName(dataFromExelFileDto.getOffre());
                                    savedData.setStatusBscs(dataFromExelFileDto.getStatutTelco()!=null?dataFromExelFileDto.getStatutTelco():"suspendu");
                                    savedData.setTmCode(dataFromExelFileDto.getTmCode());
                                    ToSavedActionUser.add(savedData);
                                }
                            }
                        }
                    }
                }
            }

            if(Utilities.isNotEmpty(ToSavedActionUser)){
                List <ActionUtilisateur> actionUtilisateurs = actionUtilisateurRepository.saveAll((Iterable <ActionUtilisateur>) ToSavedActionUser);
                actionReturn = ActionUtilisateurTransformer.INSTANCE.toLiteDtos(actionUtilisateurs);
            }
        }
        return actionReturn;

    }

    @SneakyThrows
    private List <ActionUtilisateurDto> extractData(List <ActionUtilisateur> actionUtilisateurs){
        List <ActionUtilisateurDto> actionReturn = new ArrayList <>();
        if(Utilities.isNotEmpty(actionUtilisateurs)){
            List <ActionUtilisateur> actionUtilisateursToDto = actionUtilisateurRepository.saveAll((Iterable <ActionUtilisateur>) actionUtilisateurs);
            if(Utilities.isNotEmpty(actionUtilisateursToDto)){
                actionReturn = ActionUtilisateurTransformer.INSTANCE.toDtos(actionUtilisateurs);

            }
        }
        return actionReturn;
    }

    @SneakyThrows
    public List <ActionUtilisateurDto> exposed(){
        LocalDate dateAujourdhui = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateFormatee = dateAujourdhui.format(formatter);
        String resultat = paramsUtils.getBaseString()+dateFormatee+".xlsx";
        Path cheminFichier = FileSystems.getDefault().getPath(paramsUtils.getExtractData(),resultat);
        if(Files.exists(cheminFichier)){
            Iterator <Row> iterator = readFileXls(cheminFichier.toString());
            List <ActionUtilisateur> dataFromExelFileDtos = extractData(iterator);
            List <ActionUtilisateurDto> actionUtilisateurDtos = extractData(dataFromExelFileDtos);
            return actionUtilisateurDtos;
        }else{
            log.error("une erreur est survenu. n'a pas pu lire le fichier, {}",cheminFichier.toString());
            throw new FileNotFoundException();
        }
    }

    public ActionUtilisateur verifyDonnéeEntranteAvantDePersister(Row currentRow,TypeNumero typeNumero) throws ParseException{
        Cell cell = null;
        cell = getCell(currentRow,1);
        ActionUtilisateur savedData = null;
        String numeroFound = cell.getStringCellValue().trim();
        List <ActionUtilisateur> actionUtilisateurs = actionUtilisateurRepository.findByNumeroAll(typeNumero.getId(),AES.encrypt(numeroFound,StatusApiEnum.SECRET_KEY),Boolean.FALSE);
        if(!Utilities.isNotEmpty(actionUtilisateurs)){
            savedData = new ActionUtilisateur();
        }else{
            savedData = actionUtilisateurs.get(0);
        }
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                if(cell.getStringCellValue()!=null){
                    String numero = cell.getStringCellValue().trim();
                    savedData.setNumero(AES.encrypt(numero.trim(),StatusApiEnum.SECRET_KEY));
                    savedData.setTypeNumero(typeNumero);
                    savedData.setCreatedAt(Utilities.getCurrentDate());
                    savedData.setUpdatedAt(Utilities.getCurrentDate());
                    savedData.setIsDeleted(Boolean.FALSE);
                    savedData.setIsExcelNumber(Boolean.TRUE);
                    savedData.setFromBss(Boolean.TRUE);
                    break;
                }
            case Cell.CELL_TYPE_NUMERIC:
                if(cell.getNumericCellValue()>0){
                    String numeroNumeric = String.valueOf(cell.getNumericCellValue());
                    savedData.setNumero(AES.encrypt(numeroNumeric.trim(),StatusApiEnum.SECRET_KEY));
                    savedData.setTypeNumero(typeNumero);
                    savedData.setCreatedAt(Utilities.getCurrentDate());
                    savedData.setUpdatedAt(Utilities.getCurrentDate());
                    savedData.setIsDeleted(Boolean.FALSE);
                    savedData.setIsExcelNumber(Boolean.TRUE);
                    savedData.setFromBss(Boolean.TRUE);
                    break;
                }
        }
        cell = getCell(currentRow,2);
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                boolean b = Boolean.FALSE;
                String isMachine = cell.getStringCellValue().trim();
                if(Utilities.notBlank(isMachine)){
                    b = isMachine.equalsIgnoreCase("OUI")?Boolean.TRUE:Boolean.FALSE;
                }
                savedData.setIsMachine(b);
                break;
        }
        cell = getCell(currentRow,3);
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                if(cell.getStringCellValue()!=null){
                    String statusBlocage = cell.getStringCellValue().trim();
                    Status status = statusRepository.findByCode(statusBlocage,Boolean.FALSE);
                    savedData.setStatus(status);
                    break;
                }
        }
        cell = getCell(currentRow,4);
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                if(cell.getStringCellValue()!=null){
                    String coId = cell.getStringCellValue().trim();
                    savedData.setContractId(coId);
                    break;
                }
            case Cell.CELL_TYPE_NUMERIC:
                if(cell.getNumericCellValue()>0){
                    String coId = String.valueOf(cell.getNumericCellValue());
                    BigDecimal valeurBigDecimal = new BigDecimal(coId);
                    DecimalFormat decimalFormat = new DecimalFormat("#");
                    String valeurDecimale = decimalFormat.format(valeurBigDecimal);
                    savedData.setContractId(valeurDecimale);
                    break;
                }

        }
        cell = getCell(currentRow,5);
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                if(cell.getStringCellValue()!=null){
                    String portNum = cell.getStringCellValue().trim();
                    savedData.setPortNumber(portNum);
                    break;
                }
            case Cell.CELL_TYPE_NUMERIC:
                if(cell.getNumericCellValue()>0){
                    String portNum = String.valueOf(cell.getNumericCellValue());
                    BigDecimal valeurBigDecimal = new BigDecimal(portNum);
                    DecimalFormat decimalFormat = new DecimalFormat("#");
                    String valeurDecimale = decimalFormat.format(valeurBigDecimal);
                    savedData.setPortNumber(valeurDecimale);
                    break;
                }

        }
        cell = getCell(currentRow,6);
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                if(cell.getStringCellValue()!=null){
                    String serialNum = cell.getStringCellValue().trim();
                    savedData.setSerialNumber(serialNum);
                    break;
                }
            case Cell.CELL_TYPE_NUMERIC:
                if(cell.getNumericCellValue()>0){

                    String serialNum = String.valueOf(cell.getNumericCellValue());
                    BigDecimal valeurBigDecimal = new BigDecimal(serialNum);
                    DecimalFormat decimalFormat = new DecimalFormat("#");
                    String valeurDecimale = decimalFormat.format(valeurBigDecimal);
                    savedData.setSerialNumber(valeurDecimale);
                    break;
                }

        }
        cell = getCell(currentRow,7);
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                if(cell.getStringCellValue()!=null){
                    String tmCode = cell.getStringCellValue().trim();
                    savedData.setTmCode(tmCode);
                    break;
                }
            case Cell.CELL_TYPE_NUMERIC:
                if(cell.getNumericCellValue()>0){
                    String tmCode = String.valueOf(cell.getNumericCellValue());
                    BigDecimal valeurBigDecimal = new BigDecimal(tmCode);
                    DecimalFormat decimalFormat = new DecimalFormat("#");
                    String valeurDecimale = decimalFormat.format(valeurBigDecimal);
                    savedData.setTmCode(valeurDecimale);
                    break;
                }
        }
        cell = getCell(currentRow,8);
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                if(cell.getStringCellValue()!=null){
                    String offre = cell.getStringCellValue().trim();
                    savedData.setOfferName(offre);
                    String[] categorieOmci = paramsUtils.getCategorieOmci();
                    List <String> categorieOmcis = Arrays.asList(categorieOmci).stream().map(String::trim).collect(Collectors.toList());
                    Category category;
                    if(categorieOmcis.contains(offre.trim())){
                        category = categoryRepository.findOne(StatusApiEnum.OMCI_CATEGORY,Boolean.FALSE);
                    }else{
                        category = categoryRepository.findOne(StatusApiEnum.TELCO_CATEGORY,Boolean.FALSE);
                    }
                    savedData.setCategory(category);
                    break;
                }
        }
        cell = getCell(currentRow,9);
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                if(cell.getStringCellValue()!=null){
                    String date = cell.getStringCellValue().trim();
                    savedData.setActivationDate(dateTimeFormat.parse(date));
                    break;
                }
            case Cell.CELL_TYPE_NUMERIC:
                Date dateFrom = cell.getDateCellValue();
                savedData.setActivationDate(dateFrom);
                break;
        }

        cell = getCell(currentRow,10);
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                if(cell.getStringCellValue()!=null){
                    String statusTelco = cell.getStringCellValue().trim();
                    savedData.setStatusBscs(statusTelco!=null?statusTelco:"suspendu");
                    break;
                }
        }
        return savedData;
    }

    @SneakyThrows
    public List <ActionUtilisateurDto> readFileFromHomeRepository(String path){
        Path cheminFichier = FileSystems.getDefault().getPath(path);
        if(Files.exists(cheminFichier)){
            if(path.contains(".csv")){
                readAndRefreshFileCsvAndXlsxFromTomcat(path);
            }
            if(path.contains(".xls")){
                Iterator <Row> iterator = readFileXls(cheminFichier.toString());
                List <ActionUtilisateur> dataFromExelFileDtos = extractData(iterator);
                List <ActionUtilisateurDto> actionUtilisateurDtos = extractData(dataFromExelFileDtos);
                return actionUtilisateurDtos;
            }
            return null;
        }else{
            log.error("une erreur est survenu. n'a pas pu lire le fichier, {}",cheminFichier.toString());
            throw new FileNotFoundException();
        }
    }

    //@Scheduled(cron = "0 0 0 * * *")
    //@Scheduled(cron = "0 30 13 * * *")
    @SneakyThrows
    public void cronReadAndRefreshFileCsvAndXlsxFromTomcat(){
        log.info("debut execution du service import");
        LocalDate dateAujourdhui = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateFormatee = dateAujourdhui.format(formatter);
        String resultat = paramsUtils.getBaseString()+dateFormatee+".xlsx";
        String resultatCsv = paramsUtils.getBaseString()+dateFormatee+".csv";
        Path cheminFichierxlsx = FileSystems.getDefault().getPath(paramsUtils.getExtractData(),resultat);
        Path cheminFichierCsv = FileSystems.getDefault().getPath(paramsUtils.getExtractData(),resultatCsv);
        // Vérifiez si le fichier existe
        verifyEtTraiteLeFichierExistant(cheminFichierxlsx,cheminFichierCsv);
    }

    @SneakyThrows
    public void readAndRefreshFileCsvAndXlsxFromTomcat(String path){
        log.info("debut execution du service import");
        LocalDate dateAujourdhui = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateFormatee = dateAujourdhui.format(formatter);
        String resultat = paramsUtils.getBaseString()+dateFormatee+".xlsx";
        String resultatCsv = paramsUtils.getBaseString()+dateFormatee+".csv";
        Path cheminFichierxlsx = FileSystems.getDefault().getPath(paramsUtils.getExtractData(),resultat);
        Path cheminFichierCsv = FileSystems.getDefault().getPath(paramsUtils.getExtractData(),resultatCsv);
        if(path!=null&&path.contains(".csv")){
            log.info("on est bon");
            cheminFichierCsv = FileSystems.getDefault().getPath(path);
            log.info("chemin {}",cheminFichierCsv);
        }
        // Vérifiez si le fichier existe
        verifyEtTraiteLeFichierExistant(cheminFichierxlsx,cheminFichierCsv);
    }

    private void verifyEtTraiteLeFichierExistant(Path cheminFichierxlsx,Path cheminFichierCsv) throws IOException{
        TypeNumero typeNumero = typeNumeroRepository.findByLibelle(StatusApiEnum.CORAIL,Boolean.FALSE);
        Category categoryOmci = categoryRepository.findByCode(StatusApiEnum.OMCI_CODE,Boolean.FALSE);
        Category categoryTelco = categoryRepository.findByCode(StatusApiEnum.TELCO_CODE,Boolean.FALSE);
        List <ActionUtilisateur> toSaved = new ArrayList <>();
        if(Files.exists(cheminFichierCsv)){
            log.info("le fichier existe");
            try(CSVReader csvReader = new CSVReader(new FileReader(String.valueOf(cheminFichierCsv)))){
                List <String[]> data = csvReader.readAll();
                log.info("la taille{}",data.size());
                log.info("le premier element {}",data.get(0));
                log.info("la taille du premier element {}",data.get(0).length);
                if(data.size()>0&&data.get(0).length>=1){
                    log.info("1 respecté");
                    List <String> tmcodeNumeroOfferList = new ArrayList <>();
                    data.parallelStream().skip(1).forEach(row->{
                        String[] firstElement = row[0].split(";");
                        String processedRow = firstElement[0]+","+firstElement[1]+","+firstElement[2]+","+firstElement[3]+","+firstElement[4]+","+firstElement[5]+","+firstElement[6]+","+firstElement[7]+","+firstElement[8]+","+firstElement[9];
                        String[] values = processedRow.split(",");
                        String numero = values[0];
                        String numeroMachine = values[1];
                        String statutBlocage = values[2];
                        String coId = values[3];
                        String portNum = values[4];
                        String smSerialNum = values[5];
                        String tmCode = values[6];
                        String offername = values[7];
                        String dateActivation = values[8];
                        String statutTelco = values[9];
                        System.out.println("TMCODE: "+tmCode);
                        System.out.println("numeroMachine: "+numeroMachine);
                        System.out.println("statutBlocage: "+statutBlocage);
                        System.out.println("coId: "+coId);
                        System.out.println("portNum: "+portNum);
                        System.out.println("smSerialNum: "+smSerialNum);
                        System.out.println("dateActivation: "+dateActivation);
                        System.out.println("statutTelco: "+statutTelco);
                        System.out.println("--------------------");
                        List <ActionUtilisateur> actionUtilisateurs = actionUtilisateurRepository.findByDepartement(typeNumero.getId(),categoryOmci.getId(),numero,Boolean.FALSE);
                        contructListeToSave(actionUtilisateurs,statutBlocage,statutTelco,offername,smSerialNum,coId,tmCode,portNum,dateActivation,categoryOmci,categoryTelco,numero,numeroMachine,typeNumero,toSaved);
                        tmcodeNumeroOfferList.add(processedRow);
                    });
                }
                if(Utilities.isNotEmpty(toSaved)){
                    log.info("la liste va être persister");
                    actionUtilisateurRepository.saveAll(toSaved);
                }
            }catch(IOException|CsvException e){
                log.info(e.getMessage());
                e.printStackTrace();
            }
        }else{
            log.info("on est dans le else");

            if(Files.exists(cheminFichierxlsx)){
                Iterator <Row> iterator = readFileXls(cheminFichierxlsx.toString());
                List <ActionUtilisateur> dataFromExelFileDtos = extractData(iterator);
                extractData(dataFromExelFileDtos);
            }
        }
        log.info("fin execution du service import");
    }

    private List <ActionUtilisateur> contructListeToSave(List <ActionUtilisateur> actionUtilisateurs,String statutBlocage,String statutTelco,String offername,String smSerialNum,String coId,String tmCode,String portNum,String dateActivation,Category categoryOmci,Category categoryTelco,String numero,String numeroMachine,TypeNumero typeNumero,List <ActionUtilisateur> toSaved){
        log.info("on est dans la fonction");
        if(Utilities.isNotEmpty(actionUtilisateurs)){
            log.info("taille de la liste {}",actionUtilisateurs.size());
            Status status = statusRepository.findByCode(statutBlocage,Boolean.FALSE);
            ActionUtilisateur actionutilisateurupdate = actionUtilisateurs.get(0);
            actionutilisateurupdate.setStatus(status);
            actionutilisateurupdate.setUpdatedAt(Utilities.getCurrentDate());
            actionutilisateurupdate.setStatusBscs(statutTelco);
            actionutilisateurupdate.setOfferName(offername);
            actionutilisateurupdate.setSerialNumber(smSerialNum);
            actionutilisateurupdate.setContractId(coId);
            actionutilisateurupdate.setFromBss(Boolean.TRUE);
            actionutilisateurupdate.setTmCode(tmCode);
            actionutilisateurupdate.setPortNumber(portNum);
            actionutilisateurupdate.setActivationDate(dateActivation!=null?Utilities.formatDateAnyFormat(dateActivation,"yyyy-MM-dd HH:mm:ss"):null);
            if(offername.equalsIgnoreCase("Orange money PDV")||offername.equalsIgnoreCase("ORANGE ZEBRA STK")){
                log.info("EST OMCI {} "+offername,categoryOmci);
                actionutilisateurupdate.setCategory(categoryOmci);
                actionutilisateurupdate.setEmpreinte(Utilities.empreinte(statutBlocage,1,Utilities.getCurrentDateTime(),numero));
            }else{
                actionutilisateurupdate.setEmpreinte(Utilities.empreinte(statutBlocage,1,Utilities.getCurrentDateTime(),numero));
                log.info("EST TELCO {} "+offername,categoryTelco);
                actionutilisateurupdate.setCategory(categoryTelco);
            }
            actionutilisateurupdate.setNumero(AES.encrypt(numero,StatusApiEnum.SECRET_KEY));
            actionutilisateurupdate.setIsMachine(numeroMachine.equalsIgnoreCase("NON")?Boolean.FALSE:Boolean.TRUE);
            actionutilisateurupdate.setTypeNumero(typeNumero);
            actionutilisateurupdate.setIsExcelNumber(Boolean.TRUE);
            if(status.getCode().equalsIgnoreCase(StatusApiEnum.BLOCk)){
                actionutilisateurupdate.setLastBlocageDate(Utilities.getCurrentDate());
            }
            if(status.getCode().equalsIgnoreCase(StatusApiEnum.DEBLOCK)){
                actionutilisateurupdate.setLastDebloquage(Utilities.getCurrentDate());
            }
            if(numeroMachine.equalsIgnoreCase("OUI")){
                actionutilisateurupdate.setLastMachineDate(Utilities.getCurrentDate());
            }
            actionutilisateurupdate.setStatut(StatusApiEnum.EFFECTUER);
            actionutilisateurupdate.setDate(Utilities.getCurrentDate());
            actionutilisateurupdate.setIsDeleted(Boolean.FALSE);
            toSaved.add(actionutilisateurupdate);

            List <ActionUtilisateur> toDeleted = actionUtilisateurs.stream().skip(1).collect(Collectors.toList());
            if(Utilities.isNotEmpty(toDeleted)){
                toDeleted.stream().forEach(args->{
                    args.setIsDeleted(Boolean.TRUE);
                    actionUtilisateurRepository.save(args);
                });
            }
        }else{
            Status status = statusRepository.findByCode(statutBlocage,Boolean.FALSE);
            log.info("on veut créer");
            ActionUtilisateur actionUtilisateur = new ActionUtilisateur();
            actionUtilisateur.setStatus(status);
            actionUtilisateur.setUpdatedAt(Utilities.getCurrentDate());
            actionUtilisateur.setStatusBscs(statutTelco);
            actionUtilisateur.setOfferName(offername);
            actionUtilisateur.setSerialNumber(smSerialNum);
            actionUtilisateur.setContractId(coId);
            actionUtilisateur.setCreatedAt(Utilities.getCurrentDate());
            actionUtilisateur.setFromBss(Boolean.TRUE);
            actionUtilisateur.setTmCode(tmCode);
            actionUtilisateur.setPortNumber(portNum);
            actionUtilisateur.setActivationDate(dateActivation!=null?Utilities.formatDateAnyFormat(dateActivation,"yyyy-MM-dd HH:mm:ss"):null);
            if(offername.equalsIgnoreCase("Orange money PDV")||offername.equalsIgnoreCase("ORANGE ZEBRA STK")){
                log.info("EST OMCI {}"+offername,categoryOmci);
                actionUtilisateur.setCategory(categoryOmci);
            }else{
                log.info("EST TELCO {}"+offername,categoryTelco);
                actionUtilisateur.setCategory(categoryTelco);
            }
            actionUtilisateur.setNumero(AES.encrypt(numero,StatusApiEnum.SECRET_KEY));
            actionUtilisateur.setIsMachine(numeroMachine.equalsIgnoreCase("NON")?Boolean.FALSE:Boolean.TRUE);
            actionUtilisateur.setTypeNumero(typeNumero);
            actionUtilisateur.setIsExcelNumber(Boolean.TRUE);
            if(status.getCode().equalsIgnoreCase(StatusApiEnum.BLOCk)){
                actionUtilisateur.setLastBlocageDate(Utilities.getCurrentDate());
            }
            if(status.getCode().equalsIgnoreCase(StatusApiEnum.DEBLOCK)){
                actionUtilisateur.setLastDebloquage(Utilities.getCurrentDate());
            }
            if(numeroMachine.equalsIgnoreCase("OUI")){
                actionUtilisateur.setLastMachineDate(Utilities.getCurrentDate());
            }
            actionUtilisateur.setStatut(StatusApiEnum.EFFECTUER);
            actionUtilisateur.setEmpreinte(Utilities.empreinte(statutBlocage,0,Utilities.getCurrentDateTime(),numero));
            actionUtilisateur.setDate(Utilities.getCurrentDate());
            actionUtilisateur.setIsDeleted(Boolean.FALSE);
            toSaved.add(actionUtilisateur);

        }

        return toSaved;
    }

    public void getAndRefreshNumber(String numero,String numeroMachine,String statutBlocage,String coId,String portNum,String smSerialNum,String tmCode,String offername,String dateActivation,String statutTelco){
        log.info("commence a raffraichir, {}, {}, {}",numero,offername,tmCode);
        TypeNumero typeNumero = typeNumeroRepository.findByLibelle(StatusApiEnum.CORAIL,Boolean.FALSE);
        List <ActionUtilisateur> actionUtilisateurList = actionUtilisateurRepository.findByNumeroAll(typeNumero.getId(),AES.encrypt(numero,StatusApiEnum.SECRET_KEY),Boolean.FALSE);
        if(Utilities.isNotEmpty(actionUtilisateurList)){
            log.info("les numeros son pas vides");
            actionUtilisateurList.stream().forEach(args->{
                _AppRequestDto appRequestDto = new _AppRequestDto();
                appRequestDto.setMsisdn(AES.decrypt(args.getNumero(),StatusApiEnum.SECRET_KEY));
                raffraichirSelonUnStatus(numero,numeroMachine,statutBlocage,coId,portNum,smSerialNum,tmCode,offername,dateActivation,statutTelco,typeNumero,args);
                Status status;
                if(offername.equalsIgnoreCase("Orange Money PDV")){
                    status = statusRepository.findByCode(StatusApiEnum.BLOCk,Boolean.FALSE);
                    args.setStatus(status);
                    simSwapServiceProxyService.lockPhoneNumber(appRequestDto);
                }
                simSwapServiceProxyService.getContract(appRequestDto,args);
            });
        }else{
            ActionUtilisateur actionUtilisateur = new ActionUtilisateur();
            raffraichirSelonUnStatus(numero,numeroMachine,statutBlocage,coId,portNum,smSerialNum,tmCode,offername,dateActivation,statutTelco,typeNumero,actionUtilisateur);
            Status status;
            _AppRequestDto appRequestDto = new _AppRequestDto();
            appRequestDto.setMsisdn(numero);
            if(offername.equalsIgnoreCase("Orange Money PDV")){
                status = statusRepository.findByCode(StatusApiEnum.BLOCk,Boolean.FALSE);
                actionUtilisateur.setStatus(status);
                simSwapServiceProxyService.lockPhoneNumber(appRequestDto);
            }
            simSwapServiceProxyService.getContract(appRequestDto,actionUtilisateur);

        }
    }

    private void raffraichirSelonUnStatus(String numero,String numeroMachine,String statutBlocage,String coId,String portNum,String smSerialNum,String tmCode,String offername,String dateActivation,String statutTelco,TypeNumero typeNumero,ActionUtilisateur args){
        Status status;
        Category category;
        if((offername.equalsIgnoreCase("Orange money PDV")&&tmCode.equalsIgnoreCase("809"))||(offername.equalsIgnoreCase("ORANGE ZEBRA STK")&&tmCode.equalsIgnoreCase("699"))){
            category = categoryRepository.findByCode(StatusApiEnum.OMCI_CODE,Boolean.FALSE);
        }else{
            category = categoryRepository.findByCode(StatusApiEnum.TELCO_CODE,Boolean.FALSE);
        }
        status = statusRepository.findByCode(statutBlocage,Boolean.FALSE);
        args.setCategory(category);
        args.setNumero(AES.encrypt(numero,StatusApiEnum.SECRET_KEY));
        args.setPortNumber(portNum);
        args.setSerialNumber(smSerialNum);
        args.setContractId(coId);
        args.setFromBss(Boolean.TRUE);
        args.setIsExcelNumber(Boolean.TRUE);
        args.setOfferName(offername);
        args.setIsMachine(numeroMachine.equalsIgnoreCase("NON")?Boolean.FALSE:Boolean.TRUE);
        args.setCreatedAt(Utilities.getCurrentDate());
        args.setFromBss(Boolean.TRUE);
        args.setTypeNumero(typeNumero);
        args.setStatusBscs(statutTelco);
        args.setStatus(status);
        args.setIsDeleted(Boolean.FALSE);
    }

    private List <NumeroStoriesDto> numeroStoriesListDto(Integer createdBy,String numero,Integer idProfil,Integer idStatut,Integer idCategory,Integer IdTypeNumero,String addressIp,String login,String machine,String statut,List <NumeroStoriesDto> itemsStories){
        NumeroStoriesDto dataStories = new NumeroStoriesDto();
        dataStories.setCreatedBy(createdBy);
        dataStories.setCreatedAt(Utilities.getCurrentDateTime());
        dataStories.setNumero(numero);
        dataStories.setIdProfil(idProfil);
        dataStories.setIsDeleted(Boolean.FALSE);
        dataStories.setIdStatut(idStatut);
        dataStories.setIdCategory(idCategory);
        dataStories.setIdTypeNumero(IdTypeNumero);
        dataStories.setAdresseIp(addressIp);
        dataStories.setLogin(login);
        dataStories.setMachine(machine);
        dataStories.setStatut(statut);
        itemsStories.add(dataStories);
        return itemsStories;
    }

    private List <Result> listActionUtilisateurCreate(Status status,Profil profil,TypeNumero typeNumero,Integer updatedBy,User user,String numero,Date lastBlocateDate,Date lastMiseEnMachineDate,Date lastDeblocageDate,Boolean isMachine,List <ActionUtilisateur> actionUtilisateurs,List <String> numbersToCreate,String state,String message,String ipAddress,String hostname,List <NumeroStoriesDto> itemsStories){
        log.info("est 200 ---------------------------- 4");
        LockUnLockFreezeDto unLockFreezeDto = simSwapServiceProxyService.getNumbers(UUID.randomUUID().toString(),numbersToCreate);
        List <Result> mapList = new ArrayList <>();
        if(unLockFreezeDto!=null&&Utilities.isNotEmpty(unLockFreezeDto.getData())){
            log.info("la taille de l'objet a mapper {}",unLockFreezeDto.getData().size());
            unLockFreezeDto.getData().stream().forEach(arg->{
                Result map = new Result();
                log.info("est 200 ---------------------------- 7");
                if(arg.getMessage()==null&&arg.getPortNumber()>0){
                    ActionUtilisateur actionUserToSave = new ActionUtilisateur();
                    Category categoryWithOfferName = simSwapServiceProxyService.getCategory(arg.getOfferName());
                    actionUserToSave.setStatus(status);
                    actionUserToSave.setCreatedAt(Utilities.getCurrentDate());
                    actionUserToSave.setUpdatedAt(Utilities.getCurrentDate());
                    actionUserToSave.setStatusBscs(arg.getStatus());
                    actionUserToSave.setOfferName(arg.getOfferName());
                    actionUserToSave.setSerialNumber(arg.getSerialNumber());
                    actionUserToSave.setContractId(String.valueOf(arg.getContractId()));
                    actionUserToSave.setFromBss(Boolean.TRUE);
                    actionUserToSave.setTmCode(String.valueOf(arg.getTariffModelCode()));
                    actionUserToSave.setPortNumber(String.valueOf(arg.getPortNumber()));
                    actionUserToSave.setActivationDate(arg.getActivationDate()!=null?Utilities.formatDateAnyFormat(arg.getActivationDate(),"yyyy-MM-dd HH:mm:ss"):null);
                    actionUserToSave.setProfil(profil);
                    actionUserToSave.setCategory(categoryWithOfferName);
                    actionUserToSave.setUser(user);
                    actionUserToSave.setNumero(AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY));
                    actionUserToSave.setIsMachine(arg.isFrozen());
                    actionUserToSave.setUpdatedBy(updatedBy);
                    actionUserToSave.setTypeNumero(typeNumero);
                    actionUserToSave.setIsExcelNumber(Boolean.TRUE);
                    if(lastBlocateDate!=null)
                        actionUserToSave.setLastBlocageDate(lastBlocateDate);
                    if(lastMiseEnMachineDate!=null)
                        actionUserToSave.setLastMachineDate(lastMiseEnMachineDate);
                    if(lastDeblocageDate!=null)
                        actionUserToSave.setLastDebloquage(lastDeblocageDate);
                    actionUserToSave.setStatut(StatusApiEnum.EFFECTUER);
                    if(status!=null){
                        actionUserToSave.setEmpreinte(Utilities.empreinte(status.getCode(),updatedBy,Utilities.getCurrentDateTime(),numero));
                    }else{
                        actionUserToSave.setEmpreinte(Utilities.empreinte("MISE EN MACHINE",updatedBy,Utilities.getCurrentDateTime(),numero));
                    }
                    actionUserToSave.setDate(Utilities.getCurrentDate());
                    actionUserToSave.setIsDeleted(Boolean.FALSE);
                    actionUserToSave.setCreatedBy(updatedBy);
                    actionUtilisateurs.add(actionUserToSave);
                    numeroStoriesListDto(user.getId(),arg.getMsisdn(),user.getProfil().getId(),status.getId(),user.getCategory().getId(),typeNumero.getId(),ipAddress,user.getLogin(),hostname,state,itemsStories);

                }
                map.setReason(message+"  _  "+state);
                map.setNumero(arg.getMsisdn());
                map.setOfferName(arg.getOfferName());
                map.setSerialNumber(arg.getSerialNumber());
                map.setContractId(String.valueOf(arg.getContractId()));
                map.setTariffModelCode(String.valueOf(arg.getTariffModelCode()));
                map.setPortNumber(String.valueOf(arg.getPortNumber()));
                map.setLockStatus(status.getCode());
                map.setStatus(arg.getStatus());
                map.setActivationDate(arg.getActivationDate());
                map.setFrozen(arg.isFrozen());
                map.setLogin(user.getLogin());
                mapList.add(map);
            });
        }
        log.info("infos fichier resultat, {}",mapList);
        if(Utilities.isNotEmpty(actionUtilisateurs)){
            log.info("entrain de sauver {}",actionUtilisateurs);
            actionUtilisateurRepository.saveAll(actionUtilisateurs);
        }
        return mapList;
    }

    private List <Result> listDemandeCreate(User user,Status status,TypeNumero typeNumero,List <String> numbersToCreate,String message,String ipAddress,String hostname,Locale locale){
        log.info("est 200 ---------------------------- 4");
        LockUnLockFreezeDto unLockFreezeDto = simSwapServiceProxyService.getNumbers(UUID.randomUUID().toString(),numbersToCreate);
        Request <DemandeDto> demandeDtoRequest = new Request <>();
        List <DemandeDto> demandeDtos = new ArrayList <>();
        demandeDtoRequest.setUser(user.getId());
        demandeDtoRequest.setDatas(demandeDtos);
        demandeDtoRequest.setIpAddress(ipAddress);
        demandeDtoRequest.setHostName(hostname);
        List <Result> mapList = new ArrayList <>();
        if(unLockFreezeDto!=null&&Utilities.isNotEmpty(unLockFreezeDto.getData())){
            log.info("la taille de l'objet a mapper {}",unLockFreezeDto.getData().size());
            unLockFreezeDto.getData().stream().forEach(arg->{
                log.info("est 200 ---------------------------- 7");
                if(arg.getMessage()==null&&arg.getPortNumber()>0){
                    DemandeDto demandeDto = new DemandeDto();
                    demandeDto.setIsTransfert(Boolean.FALSE);
                    demandeDto.setNumero(arg.getMsisdn());
                    demandeDto.setIsMasse(Boolean.TRUE);
                    demandeDto.setOfferName(arg.getOfferName());
                    demandeDto.setMotif("ISSUE D'UN TRAITEMENT EN MASSE");
                    demandeDto.setIdStatus(StatusApiEnum.DEBLOQUER);
                    demandeDto.setTypeNumeroId(typeNumero.getId());
                    demandeDtos.add(demandeDto);
                }
                Result map = new Result();
                map.setReason(message+"  _  "+arg.getMessage());
                map.setNumero(arg.getMsisdn());
                map.setOfferName(arg.getOfferName());
                map.setSerialNumber(arg.getSerialNumber());
                map.setContractId(String.valueOf(arg.getContractId()));
                map.setTariffModelCode(String.valueOf(arg.getTariffModelCode()));
                map.setPortNumber(String.valueOf(arg.getPortNumber()));
                map.setLockStatus(status.getCode());
                map.setStatus(arg.getStatus());
                map.setActivationDate(arg.getActivationDate());
                map.setFrozen(arg.isFrozen());
                map.setLogin(user.getLogin());
                mapList.add(map);
            });
            if(Utilities.isNotEmpty(demandeDtos)){
                try{
                    demandeBusiness.create(demandeDtoRequest,locale);
                }catch(ParseException e){
                    throw new RuntimeException(e);
                }catch(IOException e){
                    throw new RuntimeException(e);
                }
            }
        }
        log.info("infos fichier resultat, {}",mapList);
        return mapList;
    }

    private List <Result> listActionUtilisateurUpdate(Status status,Profil profil,TypeNumero typeNumero,Integer updatedBy,User user,String numero,Date lastBlocateDate,Date lastMiseEnMachineDate,Date lastDeblocageDate,List <ActionUtilisateur> actionUtilisateurs,List <String> numbersToUpdate,String state,String ipAddress,String hostName,List <NumeroStoriesDto> itemsStories){
        List <Result> mapList = new ArrayList <>();
        LockUnLockFreezeDto unLockFreezeDto = simSwapServiceProxyService.findByMsisdn(UUID.randomUUID().toString(),numbersToUpdate,user.getId(),Boolean.FALSE);
        if(unLockFreezeDto!=null&&Utilities.isNotEmpty(unLockFreezeDto.getData())){
            log.info(" unLockFreezeDto est pas vide");
            Result map = new Result();
            unLockFreezeDto.getData().stream().forEach(arg->{
                if(arg.getMessage()==null&&arg.getPortNumber()>0){
                    Category categoryFound = simSwapServiceProxyService.getCategory(arg.getOfferName());
                    ActionUtilisateur actionUserToSave = foundActionUtilisateur(typeNumero,categoryFound,arg.getMsisdn());
                    log.info("la ligne trouvé, {}",actionUserToSave);
                    if(actionUserToSave!=null){
                        log.info(" on est entré dans la condition");
                        actionUserToSave.setStatus(status);
                        actionUserToSave.setUpdatedAt(Utilities.getCurrentDate());
                        actionUserToSave.setStatusBscs(arg.getStatus());
                        actionUserToSave.setOfferName(arg.getOfferName());
                        actionUserToSave.setSerialNumber(arg.getSerialNumber());
                        actionUserToSave.setContractId(String.valueOf(arg.getContractId()));
                        actionUserToSave.setFromBss(Boolean.TRUE);
                        actionUserToSave.setTmCode(String.valueOf(arg.getTariffModelCode()));
                        actionUserToSave.setPortNumber(String.valueOf(arg.getPortNumber()));
                        actionUserToSave.setActivationDate(arg.getActivationDate()!=null?Utilities.formatDateAnyFormat(arg.getActivationDate(),"yyyy-MM-dd HH:mm:ss"):null);
                        actionUserToSave.setProfil(profil);
                        actionUserToSave.setCategory(categoryFound);
                        actionUserToSave.setUser(user);
                        actionUserToSave.setNumero(numero);
                        actionUserToSave.setIsMachine(arg.isFrozen());
                        actionUserToSave.setUpdatedBy(updatedBy);
                        actionUserToSave.setTypeNumero(typeNumero);
                        actionUserToSave.setIsExcelNumber(Boolean.TRUE);
                        if(lastBlocateDate!=null)
                            actionUserToSave.setLastBlocageDate(lastBlocateDate);
                        if(lastMiseEnMachineDate!=null)
                            actionUserToSave.setLastMachineDate(lastMiseEnMachineDate);
                        if(lastDeblocageDate!=null)
                            actionUserToSave.setLastDebloquage(lastDeblocageDate);
                        actionUserToSave.setStatut(StatusApiEnum.EFFECTUER);
                        if(status!=null){
                            actionUserToSave.setEmpreinte(Utilities.empreinte(status.getCode(),updatedBy,Utilities.getCurrentDateTime(),numero));
                        }else{
                            actionUserToSave.setEmpreinte(Utilities.empreinte("MISE EN MACHINE",updatedBy,Utilities.getCurrentDateTime(),numero));
                        }
                        actionUserToSave.setDate(Utilities.getCurrentDate());
                        actionUserToSave.setIsDeleted(Boolean.FALSE);
                        actionUserToSave.setCreatedBy(updatedBy);
                        actionUtilisateurs.add(actionUserToSave);
                        numeroStoriesListDto(user.getId(),arg.getMsisdn(),user.getProfil().getId(),status.getId(),user.getCategory().getId(),typeNumero.getId(),ipAddress,user.getLogin(),hostName,state,itemsStories);
                    }
                }
                map.setReason(state);
                map.setNumero(arg.getMsisdn());
                map.setOfferName(arg.getOfferName());
                map.setSerialNumber(arg.getSerialNumber());
                map.setContractId(String.valueOf(arg.getContractId()));
                map.setTariffModelCode(String.valueOf(arg.getTariffModelCode()));
                map.setPortNumber(String.valueOf(arg.getPortNumber()));
                map.setLockStatus(status.getCode());
                map.setStatus(arg.getStatus());
                map.setActivationDate(arg.getActivationDate());
                map.setFrozen(arg.isFrozen());
                map.setLogin(user.getLogin());
                mapList.add(map);
                log.info("la liste finale{}",mapList);
            });
        }
        if(Utilities.isNotEmpty(actionUtilisateurs)){
            actionUtilisateurRepository.saveAll(actionUtilisateurs);
        }
        return mapList;
    }

    private List <Result> updateCronBatch(ActionUtilisateur actionUserToSave,String state){
        List <String> numbersToUpdate = new ArrayList <>();
        numbersToUpdate.add(AES.decrypt(actionUserToSave.getNumero(),StatusApiEnum.SECRET_KEY));
        List <Result> mapList = new ArrayList <>();
        List <ActionUtilisateur> actionUtilisateurs = new ArrayList <>();
        LockUnLockFreezeDto unLockFreezeDto = simSwapServiceProxyService.findByMsisdn(UUID.randomUUID().toString(),numbersToUpdate,actionUserToSave.getUser()!=null?actionUserToSave.getUser().getId():null,Boolean.FALSE);
        if(unLockFreezeDto!=null&&Utilities.isNotEmpty(unLockFreezeDto.getData())){
            log.info(" unLockFreezeDto est pas vide");
            Result map = new Result();
            unLockFreezeDto.getData().stream().forEach(arg->{
                if(arg.getMessage()==null&&arg.getPortNumber()>0&&unLockFreezeDto.getHasError()!=Boolean.FALSE){
                    log.info(" on est entré dans la condition");
                    Status status = statusRepository.findByCode(arg.getLockStatus(),Boolean.FALSE);
                    actionUserToSave.setStatus(status!=null&&!status.getId().equals(StatusApiEnum.MISE_EN_MACHINE)?status:null);
                    actionUserToSave.setStatusBscs(arg.getStatus());
                    actionUserToSave.setOfferName(arg.getOfferName());
                    actionUserToSave.setSerialNumber(arg.getSerialNumber());
                    actionUserToSave.setContractId(String.valueOf(arg.getContractId()));
                    actionUserToSave.setFromBss(Boolean.TRUE);
                    actionUserToSave.setTmCode(String.valueOf(arg.getTariffModelCode()));
                    actionUserToSave.setPortNumber(String.valueOf(arg.getPortNumber()));
                    actionUserToSave.setActivationDate(arg.getActivationDate()!=null?Utilities.formatDateAnyFormat(arg.getActivationDate(),"yyyy-MM-dd HH:mm:ss"):null);
                    actionUserToSave.setIsMachine(arg.isFrozen());
                    actionUserToSave.setUpdatedAt(Utilities.getCurrentDate());
                    actionUserToSave.setLastBlocageDate(Utilities.getCurrentDate());
                    actionUserToSave.setStatut(StatusApiEnum.EFFECTUER);
                    actionUserToSave.setEmpreinte(Utilities.empreinte(status!=null?status.getCode():"AUCUN STATUS TROUVÉ",null,Utilities.getCurrentDateTime(),AES.decrypt(arg.getMsisdn()!=null?arg.getMsisdn():actionUserToSave.getNumero(),StatusApiEnum.SECRET_KEY)));
                    actionUtilisateurs.add(actionUserToSave);
                }
                map.setReason(state);
                map.setNumero(arg.getMsisdn()!=null?AES.decrypt(actionUserToSave.getNumero(),StatusApiEnum.SECRET_KEY):"AUCUN NUMERO");
                map.setOfferName(arg.getOfferName()!=null?arg.getOfferName():"0");
                map.setSerialNumber(arg.getSerialNumber()!=null?arg.getSerialNumber():"0");
                map.setContractId(arg.getContractId()>0?String.valueOf(arg.getContractId()):"0");
                map.setTariffModelCode(arg.getTariffModelCode()>0?String.valueOf(arg.getTariffModelCode()):"0");
                map.setPortNumber(arg.getPortNumber()>0?String.valueOf(arg.getPortNumber()):"0");
                map.setLockStatus(arg.getLockStatus()!=null?arg.getLockStatus():actionUserToSave.getStatus().getCode());
                map.setStatus(arg.getStatus()!=null?arg.getStatus():actionUserToSave.getStatusBscs());
                map.setActivationDate(arg.getActivationDate()!=null?arg.getActivationDate():actionUserToSave.getActivationDate()!=null?actionUserToSave.getActivationDate().toString():"AUCUNE DATE TROUVÉ");
                map.setFrozen(arg.isFrozen());
                mapList.add(map);
                log.info("la liste finale{}",mapList);
            });
        }
        if(Utilities.isNotEmpty(actionUtilisateurs)){
            actionUtilisateurRepository.saveAll(actionUtilisateurs);
        }
        return mapList;
    }

    private ActionUtilisateur foundActionUtilisateur(TypeNumero typeNumero,Category category,String numero){
        List <ActionUtilisateur> listNumbers = foundNumbers(typeNumero,category,numero);

        if(Utilities.isNotEmpty(listNumbers)){
            List <ActionUtilisateur> collect = listNumbers.stream().skip(1).collect(Collectors.toList());
            if(Utilities.isNotEmpty(collect)) collect.stream().forEach(args->{
                args.setIsDeleted(Boolean.TRUE);
                actionUtilisateurRepository.save(args);
            });
            return listNumbers.get(0);
        }
        return null;
    }

    private ActionUtilisateur refreshNumber(List <ActionUtilisateur> listNumbers){
        if(Utilities.isNotEmpty(listNumbers)){
            List <ActionUtilisateur> collect = listNumbers.stream().skip(1).collect(Collectors.toList());
            if(Utilities.isNotEmpty(collect)) collect.stream().forEach(args->{
                args.setIsDeleted(Boolean.TRUE);
                actionUtilisateurRepository.save(args);
            });
            return listNumbers.get(0);
        }
        return null;
    }

    private List <ActionUtilisateur> foundNumbers(TypeNumero typeNumero,Category category,String numero){
        List <ActionUtilisateur> oneNumbers;
        oneNumbers = actionUtilisateurRepository.findByDepartement(typeNumero.getId(),category.getId(),AES.encrypt(numero,StatusApiEnum.SECRET_KEY),Boolean.FALSE);
        return oneNumbers;
    }

    public String writeFileNameActionEnMasse(List <ActionEnMasseDto> dataActionLite) throws Exception{
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        ClassPathResource templateClassPathResource = new ClassPathResource("templates/list_action_en_masse.xlsx");
        InputStream inputStreamFile = templateClassPathResource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStreamFile);
        XSSFSheet sheet = workbook.getSheetAt(0); // la 1ere feuille du classeur excel
        Cell cell = null;
        Row row = null;
        int rowIndex = 1;
        for(ActionEnMasseDto actionUtilisateur: dataActionLite){
            // Renseigner les ids
            log.info("--row--"+rowIndex);
            row = sheet.getRow(rowIndex);
            if(row==null){
                row = sheet.createRow(rowIndex);
            }
            User user = userRepository.findOne(actionUtilisateur.getCreatedBy(),Boolean.FALSE);
            cell = getCell(row,0);
            cell.setCellValue(user!=null?user.getLogin():"");
            cell = getCell(row,1);
            cell.setCellValue(actionUtilisateur.getCode());
            cell = getCell(row,2);
            cell.setCellValue(actionUtilisateur.getLibelle());
            cell = getCell(row,3);
            cell.setCellValue(actionUtilisateur.getCreatedAt());
            User userUpadated = userRepository.findOne(actionUtilisateur.getUpdatedBy(),Boolean.FALSE);
            cell = getCell(row,4);
            cell.setCellValue(userUpadated!=null?userUpadated.getLogin():"");
            rowIndex++;
        }
        // Ajuster les colonnes
        for(int i = 0;i<=Utilities.getColumnIndex("I");i++){
            sheet.autoSizeColumn(i);
        }
        inputStreamFile.close();
        String fileName = Utilities.saveFile("LIST_NUMBER_AFTER_IMPORT_"+sdfFileName.format(new Date()),"xlsx",workbook,paramsUtils);

        return Utilities.getFileUrlLink(fileName,paramsUtils);
    }

    private Response <List <Result>> executeIfActionIsValidated(List <String> listToUpdate,List <String> listToCreate,User findUser,TypeNumero typeNumeroSearch,Request <ActionUtilisateurDto> request,String ipAddress,String hostName,Status status,List <Historique> datasHistorique,List <ActionUtilisateur> dataActionForSave,List <NumeroStoriesDto> itemsStories,ActionSimswap actionSimswap,Integer idStatus,Locale locale, LockUnLockFreezeDto numbers){
        Response <List <Result>> listResponse = new Response <>();
        Date miseEnMachineDate;
        Date deblocageDate;
        Date blocageDate;
        List <Result> results = new ArrayList <>();
        LockUnLockFreezeDtos lockUnLockFreezeDtos = new LockUnLockFreezeDtos();
        log.info("première etape {},{},{},{},{},{},{},{},{}",listToCreate,listToUpdate,findUser,status,datasHistorique,dataActionForSave,itemsStories,actionSimswap,idStatus);
        if(Utilities.isNotEmpty(listToCreate)){
            if(idStatus==StatusApiEnum.SERVICE_API_BLOCK_SIM){
                log.info("operation de blocage");
                lockUnLockFreezeDtos = simSwapServiceProxyService.lockNumber(listToCreate,UUID.randomUUID().toString());
                log.info("retour call Api {}",lockUnLockFreezeDtos);
                blocageDate = Utilities.getCurrentDate();
            }else{
                blocageDate = null;
            }
            if(idStatus==StatusApiEnum.SERVICE_API_MISE_EN_MACHINE){
                log.info("operation de mise en machine");
                lockUnLockFreezeDtos = simSwapServiceProxyService.freezeNumber(listToCreate,UUID.randomUUID().toString());
                miseEnMachineDate = Utilities.getCurrentDate();
            }else{
                miseEnMachineDate = null;
            }
            if(idStatus==StatusApiEnum.DEBLOQUER){
                log.info("operation de deblocage");
                lockUnLockFreezeDtos = simSwapServiceProxyService.unlockNumber(listToCreate,UUID.randomUUID().toString());
                deblocageDate = Utilities.getCurrentDate();
            }else{
                deblocageDate = null;
            }
            log.info("le retour "+lockUnLockFreezeDtos);
            if(lockUnLockFreezeDtos!=null&&lockUnLockFreezeDtos.getStatus().equalsIgnoreCase("200")){
                log.info("est 200 ---------------------------- 1");
                if(Utilities.isNotEmpty(lockUnLockFreezeDtos.getData())&&idStatus!=StatusApiEnum.DEBLOQUER){
                    log.info("est 200 ---------------------------- 2");
                    String message = lockUnLockFreezeDtos.getMessage();
                    Date finalMiseEnMachineDate = miseEnMachineDate;
                    Date finalBlocageDate = blocageDate;
                    Date finalDeblocageDate = deblocageDate;
                    lockUnLockFreezeDtos.getData().stream().forEach(lockUnLockFreezeDtoslockUnLockFreezeDtos->{
                        log.info("est 200 ---------------------------- 3");
                        List <String> numbersLite = new ArrayList <>();
                        numbersLite.add(lockUnLockFreezeDtoslockUnLockFreezeDtos.getMsisdn());
                        List <Result> result1 = listActionUtilisateurCreate(status,findUser.getProfil(),typeNumeroSearch,request.getUser(),findUser,AES.encrypt(lockUnLockFreezeDtoslockUnLockFreezeDtos.getMsisdn(),StatusApiEnum.SECRET_KEY),finalBlocageDate,finalMiseEnMachineDate,finalDeblocageDate,Boolean.TRUE,dataActionForSave,numbersLite,lockUnLockFreezeDtoslockUnLockFreezeDtos.getState(),message,ipAddress,hostName,itemsStories);
                        if(Utilities.isNotEmpty(result1)){
                            results.addAll(result1);
                        }
                    });
                }
                if(idStatus!=StatusApiEnum.DEBLOQUER){
                    listDemandeCreate(findUser,status,typeNumeroSearch,listToCreate,lockUnLockFreezeDtos.getMessage(),ipAddress,hostName,locale);
                }
            }else{
                listeDesCasdERREUR(findUser,status,lockUnLockFreezeDtos,results,listToCreate);
            }
        }else{
            deblocageDate = null;
        }
        if(Utilities.isNotEmpty(listToUpdate)){
            log.info("liste a mettre a jour pas vide {}",listToUpdate);
            if(idStatus==StatusApiEnum.SERVICE_API_BLOCK_SIM){
                lockUnLockFreezeDtos = simSwapServiceProxyService.lockNumber(listToUpdate,UUID.randomUUID().toString());
                blocageDate = Utilities.getCurrentDate();
            }else{
                blocageDate = null;
            }
            if(idStatus==StatusApiEnum.SERVICE_API_MISE_EN_MACHINE){
                lockUnLockFreezeDtos = simSwapServiceProxyService.freezeNumber(listToUpdate,UUID.randomUUID().toString());
                miseEnMachineDate = Utilities.getCurrentDate();
            }else{
                miseEnMachineDate = null;
            }
            if(lockUnLockFreezeDtos!=null&&lockUnLockFreezeDtos.getStatus().equalsIgnoreCase("200")){
                log.info("est 200 {}",lockUnLockFreezeDtos);
                if(Utilities.isNotEmpty(lockUnLockFreezeDtos.getData())&&idStatus!=StatusApiEnum.DEBLOQUER){
                    Date finalBlocageDate1 = blocageDate;
                    Date finalMiseEnMachineDate1 = miseEnMachineDate;
                    lockUnLockFreezeDtos.getData().stream().forEach(lockUnLockFreezeDtoslockUnLockFreezeDtos->{
                        log.info("a trouvé un numero");
                        List <String> numbersList = new ArrayList <>();
                        numbersList.add(lockUnLockFreezeDtoslockUnLockFreezeDtos.getMsisdn());
                        List <Result> result2 = listActionUtilisateurUpdate(status,findUser.getProfil(),typeNumeroSearch,request.getUser(),findUser,AES.encrypt(lockUnLockFreezeDtoslockUnLockFreezeDtos.getMsisdn(),StatusApiEnum.SECRET_KEY),finalBlocageDate1,finalMiseEnMachineDate1,deblocageDate,dataActionForSave,numbersList,lockUnLockFreezeDtoslockUnLockFreezeDtos.getState(),ipAddress,hostName,itemsStories);
                        if(Utilities.isNotEmpty(result2)){
                            results.addAll(result2);
                        }
                    });
                }
                if(idStatus!=StatusApiEnum.DEBLOQUER){
                    listDemandeCreate(findUser,status,typeNumeroSearch,listToCreate,lockUnLockFreezeDtos.getMessage(),ipAddress,hostName,locale);
                }
            }else{
                listeDesCasdERREUR(findUser,status,lockUnLockFreezeDtos,results,listToUpdate);
            }
        }
        if(ObjectUtils.allNotNull(numbers, numbers.getStatus(), numbers.getData()) && numbers.getStatus().equalsIgnoreCase("200") && ObjectUtils.isNotEmpty(numbers.getData())){
            numbers.getData().stream().forEach(arg->{
                Result result = new Result();
                result.setNumero(arg.getMsisdn());
                result.setActivationDate(arg.getActivationDate());
                result.setOfferName(arg.getOfferName());
                result.setSerialNumber(arg.getSerialNumber());
                result.setContractId(arg.getContractId() > 0 ? String.valueOf(arg.getContractId()) : "0");
                result.setTariffModelCode(arg.getTariffModelCode() > 0 ?String.valueOf(arg.getTariffModelCode()):"0");
                result.setPortNumber(arg.getPortNumber() > 0 ?String.valueOf(arg.getPortNumber()):"0");
                result.setLockStatus(status.getCode());
                result.setStatus(arg.getMessage());
                result.setFrozen(Boolean.FALSE);
                result.setReason(arg.getMessage());
                result.setActivationDate("");
                log.info("user {}",findUser);
                result.setLogin(findUser!=null?findUser.getLogin():"EST UN JOB");
                results.add(result);
            });
        }
        listResponse.setRaison(lockUnLockFreezeDtos!=null?lockUnLockFreezeDtos.getMessage():"");
        listResponse.setHasError(Boolean.FALSE);
        listResponse.setItem(results);
        return listResponse;
    }

    private void listeDesCasdERREUR(User findUser,Status status,LockUnLockFreezeDtos lockUnLockFreezeDtos,List <Result> results,List <String> numeros){
        if(lockUnLockFreezeDtos.getHasError()==Boolean.TRUE){
            numeros.stream().forEach(arg->{
                ActionUtilisateur actionUtilisateur = extracted(arg);
                Result result = new Result();
                result.setNumero(arg);
                result.setActivationDate(actionUtilisateur!=null?actionUtilisateur.getActivationDate()!=null?actionUtilisateur.getActivationDate().toString():"0":"0");
                result.setOfferName(actionUtilisateur!=null?actionUtilisateur.getOfferName():"0");
                result.setSerialNumber(actionUtilisateur!=null?actionUtilisateur.getSerialNumber():"0");
                result.setContractId(actionUtilisateur!=null?actionUtilisateur.getContractId():"0");
                result.setTariffModelCode(actionUtilisateur!=null?actionUtilisateur.getTmCode():"0");
                result.setPortNumber(actionUtilisateur!=null?actionUtilisateur.getPortNumber():"0");
                result.setLockStatus(status.getCode());
                result.setStatus(StatusApiEnum.ECHOUER);
                result.setFrozen(actionUtilisateur!=null?actionUtilisateur.getIsMachine():Boolean.FALSE);
                result.setReason(lockUnLockFreezeDtos.getMessage());
                result.setActivationDate("");
                log.info("user {}",findUser);
                result.setLogin(findUser!=null?findUser.getLogin():"EST UN JOB");
                results.add(result);
            });
        }
    }

    private ActionUtilisateur extracted(String arg){
        List <ActionUtilisateur> actionUtilisateurs = actionUtilisateurRepository.findByNumeroList(AES.encrypt(arg,StatusApiEnum.SECRET_KEY),Boolean.FALSE);
        log.info("cherche le numero courant "+actionUtilisateurs);
        if(Utilities.isNotEmpty(actionUtilisateurs)){
            return actionUtilisateurs.get(0);
        }
        return null;
    }


    public void stockFile(_ActionEnMasseDto actionEnMasseDto){
        try{
            extractTarGz(actionEnMasseDto.getInputFilePath(),actionEnMasseDto.getOutputDirectory());
            System.out.println("Extraction terminée avec succès.");
        }catch(IOException e){
            System.err.println("Erreur lors de l'extraction : "+e.getMessage());
        }
    }

    private static void extractTarGz(String inputFilePath,String outputDirectory) throws IOException{

        try(FileInputStream fis = new FileInputStream(inputFilePath);GzipCompressorInputStream gzIn = new GzipCompressorInputStream(fis);TarArchiveInputStream tarIn = new TarArchiveInputStream(gzIn)){

            ArchiveEntry entry;
            while((entry = tarIn.getNextEntry())!=null){
                if(entry.isDirectory()){
                    continue;
                }

                String entryName = entry.getName();
                String outputFile = outputDirectory+entryName;

                try(FileOutputStream fos = new FileOutputStream(outputFile)){
                    byte[] buffer = new byte[1024];
                    int len;
                    while((len = tarIn.read(buffer))!=-1){
                        fos.write(buffer,0,len);
                    }
                }
            }
        }
    }

    //ok rafraichir csv file avec liste de numero
    private List <Result> createOrUpdateFromCsv(Category category,TypeNumero typeNumero,List <String> numbersToCreateOrUpdate){
        LockUnLockFreezeDto unLockFreezeDto = simSwapServiceProxyService.findByMsisdn(UUID.randomUUID().toString(),numbersToCreateOrUpdate,1,Boolean.FALSE);
        List <Result> mapList = new ArrayList <>();
        List <ActionUtilisateur> actionUtilisateurList = new ArrayList <>();

        if(unLockFreezeDto!=null&&Utilities.isNotEmpty(unLockFreezeDto.getData())){
            unLockFreezeDto.getData().stream().forEach(arg->{
                Result map = new Result();
                Status status = statusRepository.findByCode(arg.getLockStatus(),Boolean.FALSE);
                ;
                if(arg.getMessage()==null&&arg.getPortNumber()>0){
                    List <ActionUtilisateur> actionUtilisateurs = actionUtilisateurRepository.findByDepartement(typeNumero.getId(),category.getId(),arg.getMsisdn(),Boolean.FALSE);
                    if(Utilities.isNotEmpty(actionUtilisateurs)){
                        actionUtilisateurs.stream().forEach(actionutilisateurupdate->{
                            actionutilisateurupdate.setStatus(status);
                            actionutilisateurupdate.setUpdatedAt(Utilities.getCurrentDate());
                            actionutilisateurupdate.setStatusBscs(arg.getStatus());
                            actionutilisateurupdate.setOfferName(arg.getOfferName());
                            actionutilisateurupdate.setSerialNumber(arg.getSerialNumber());
                            actionutilisateurupdate.setContractId(String.valueOf(arg.getContractId()));
                            actionutilisateurupdate.setFromBss(Boolean.TRUE);
                            actionutilisateurupdate.setTmCode(String.valueOf(arg.getTariffModelCode()));
                            actionutilisateurupdate.setPortNumber(String.valueOf(arg.getPortNumber()));
                            actionutilisateurupdate.setActivationDate(arg.getActivationDate()!=null?Utilities.formatDateAnyFormat(arg.getActivationDate(),"yyyy-MM-dd HH:mm:ss"):null);
                            actionutilisateurupdate.setCategory(category);
                            actionutilisateurupdate.setNumero(AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY));
                            actionutilisateurupdate.setIsMachine(arg.isFrozen());
                            actionutilisateurupdate.setTypeNumero(typeNumero);
                            actionutilisateurupdate.setIsExcelNumber(Boolean.TRUE);
                            if(status.getCode().equalsIgnoreCase(StatusApiEnum.BLOCk)){
                                actionutilisateurupdate.setLastBlocageDate(Utilities.getCurrentDate());
                            }
                            if(status.getCode().equalsIgnoreCase(StatusApiEnum.DEBLOCK)){
                                actionutilisateurupdate.setLastDebloquage(Utilities.getCurrentDate());
                            }
                            if(arg.isFrozen()==Boolean.TRUE){
                                actionutilisateurupdate.setLastMachineDate(Utilities.getCurrentDate());

                            }
                            actionutilisateurupdate.setStatut(StatusApiEnum.EFFECTUER);
                            if(status!=null){
                                actionutilisateurupdate.setEmpreinte(Utilities.empreinte(status.getCode(),0,Utilities.getCurrentDateTime(),arg.getMsisdn()));
                            }else{
                                actionutilisateurupdate.setEmpreinte(Utilities.empreinte("MISE EN MACHINE",1,Utilities.getCurrentDateTime(),arg.getMsisdn()));
                            }
                            actionutilisateurupdate.setDate(Utilities.getCurrentDate());
                            actionutilisateurupdate.setIsDeleted(Boolean.FALSE);
                            actionUtilisateurList.add(actionutilisateurupdate);
                        });
                    }else{
                        ActionUtilisateur actionUtilisateur = new ActionUtilisateur();
                        actionUtilisateur.setStatus(status);
                        actionUtilisateur.setCreatedAt(Utilities.getCurrentDate());
                        actionUtilisateur.setStatusBscs(arg.getStatus());
                        actionUtilisateur.setOfferName(arg.getOfferName());
                        actionUtilisateur.setSerialNumber(arg.getSerialNumber());
                        actionUtilisateur.setContractId(String.valueOf(arg.getContractId()));
                        actionUtilisateur.setFromBss(Boolean.TRUE);
                        actionUtilisateur.setTmCode(String.valueOf(arg.getTariffModelCode()));
                        actionUtilisateur.setPortNumber(String.valueOf(arg.getPortNumber()));
                        actionUtilisateur.setActivationDate(arg.getActivationDate()!=null?Utilities.formatDateAnyFormat(arg.getActivationDate(),"yyyy-MM-dd HH:mm:ss"):null);
                        actionUtilisateur.setCategory(category);
                        actionUtilisateur.setNumero(AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY));
                        actionUtilisateur.setIsMachine(arg.isFrozen());
                        actionUtilisateur.setTypeNumero(typeNumero);
                        actionUtilisateur.setIsExcelNumber(Boolean.TRUE);
                        if(status.getCode().equalsIgnoreCase(StatusApiEnum.BLOCk)){
                            actionUtilisateur.setLastBlocageDate(Utilities.getCurrentDate());
                        }
                        if(status.getCode().equalsIgnoreCase(StatusApiEnum.DEBLOCK)){
                            actionUtilisateur.setLastDebloquage(Utilities.getCurrentDate());
                        }
                        if(arg.isFrozen()==Boolean.TRUE){
                            actionUtilisateur.setLastMachineDate(Utilities.getCurrentDate());
                        }
                        actionUtilisateur.setStatut(StatusApiEnum.EFFECTUER);
                        if(status!=null){
                            actionUtilisateur.setEmpreinte(Utilities.empreinte(status.getCode(),0,Utilities.getCurrentDateTime(),arg.getMsisdn()));
                        }else{
                            actionUtilisateur.setEmpreinte(Utilities.empreinte("MISE EN MACHINE",1,Utilities.getCurrentDateTime(),arg.getMsisdn()));
                        }
                        actionUtilisateur.setDate(Utilities.getCurrentDate());
                        actionUtilisateur.setIsDeleted(Boolean.FALSE);

                        actionUtilisateurList.add(actionUtilisateur);
                    }
                    map.setNumero(arg.getMsisdn());
                    map.setOfferName(arg.getOfferName());
                    map.setSerialNumber(arg.getSerialNumber());
                    map.setContractId(String.valueOf(arg.getContractId()));
                    map.setTariffModelCode(String.valueOf(arg.getTariffModelCode()));
                    map.setPortNumber(String.valueOf(arg.getPortNumber()));
                    map.setLockStatus(status.getCode());
                    map.setStatus(arg.getStatus());
                    map.setActivationDate(arg.getActivationDate());
                    map.setFrozen(arg.isFrozen());
                    mapList.add(map);
                }
            });
        }

        if(Utilities.isNotEmpty(actionUtilisateurList)){
            log.info("liste a enregistré {}",actionUtilisateurList);
            actionUtilisateurRepository.saveAll(actionUtilisateurList);
        }
        return mapList;
    }

    private List <Result> createOrUpdateFromCsvTry(Category category,TypeNumero typeNumero,List <String> numbersToCreateOrUpdate){
        int batchSize = 10;
        List <Result> mapList = new ArrayList <>();
        List <List <String>> lists = new ArrayList <>();
        List <ActionUtilisateur> actionUtilisateurList = new ArrayList <>();
        IntStream.range(0,(numbersToCreateOrUpdate.size()+batchSize-1) / batchSize).mapToObj(i->numbersToCreateOrUpdate.subList(i * batchSize,Math.min((i+1) * batchSize,numbersToCreateOrUpdate.size()))).forEach(batch->{
            lists.add(batch);
        });
        if(Utilities.isNotEmpty(lists)){
            for(List <String> stringList: lists){
                log.info("liste numero a prendre en compte{}",stringList.stream().collect(Collectors.joining(",")));
                LockUnLockFreezeDto unLockFreezeDto = simSwapServiceProxyService.findByMsisdn(UUID.randomUUID().toString(),stringList,1,Boolean.FALSE);
                if(unLockFreezeDto!=null&&Utilities.isNotEmpty(unLockFreezeDto.getData())){
                    unLockFreezeDto.getData().forEach(arg->{
                        Result map = new Result();
                        Status status = statusRepository.findByCode(arg.getLockStatus(),Boolean.FALSE);
                        if(arg.getMessage()==null&&arg.getPortNumber()>0){
                            List <ActionUtilisateur> actionUtilisateurs = actionUtilisateurRepository.findByDepartement(typeNumero.getId(),category.getId(),arg.getMsisdn(),Boolean.FALSE);
                            if(Utilities.isNotEmpty(actionUtilisateurs)){
                                actionUtilisateurs.stream().forEach(actionutilisateurupdate->{
                                    actionutilisateurupdate.setStatus(status);
                                    actionutilisateurupdate.setUpdatedAt(Utilities.getCurrentDate());
                                    actionutilisateurupdate.setStatusBscs(arg.getStatus());
                                    actionutilisateurupdate.setOfferName(arg.getOfferName());
                                    actionutilisateurupdate.setSerialNumber(arg.getSerialNumber());
                                    actionutilisateurupdate.setContractId(String.valueOf(arg.getContractId()));
                                    actionutilisateurupdate.setFromBss(Boolean.TRUE);
                                    actionutilisateurupdate.setTmCode(String.valueOf(arg.getTariffModelCode()));
                                    actionutilisateurupdate.setPortNumber(String.valueOf(arg.getPortNumber()));
                                    actionutilisateurupdate.setActivationDate(Utilities.formatDateAnyFormat(arg.getActivationDate(),"yyyy-MM-dd HH:mm:ss"));
                                    actionutilisateurupdate.setCategory(category);
                                    actionutilisateurupdate.setNumero(AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY));
                                    actionutilisateurupdate.setIsMachine(arg.isFrozen());
                                    actionutilisateurupdate.setTypeNumero(typeNumero);
                                    actionutilisateurupdate.setIsExcelNumber(Boolean.TRUE);
                                    if(status.getCode().equalsIgnoreCase(StatusApiEnum.BLOCk)){
                                        actionutilisateurupdate.setLastBlocageDate(Utilities.getCurrentDate());
                                    }
                                    if(status.getCode().equalsIgnoreCase(StatusApiEnum.DEBLOCK)){
                                        actionutilisateurupdate.setLastDebloquage(Utilities.getCurrentDate());
                                    }
                                    if(arg.isFrozen()==Boolean.TRUE){
                                        actionutilisateurupdate.setLastMachineDate(Utilities.getCurrentDate());

                                    }
                                    actionutilisateurupdate.setStatut(StatusApiEnum.EFFECTUER);
                                    if(status!=null){
                                        actionutilisateurupdate.setEmpreinte(Utilities.empreinte(status.getCode(),0,Utilities.getCurrentDateTime(),arg.getMsisdn()));
                                    }else{
                                        actionutilisateurupdate.setEmpreinte(Utilities.empreinte("MISE EN MACHINE",1,Utilities.getCurrentDateTime(),arg.getMsisdn()));
                                    }
                                    actionutilisateurupdate.setDate(Utilities.getCurrentDate());
                                    actionutilisateurupdate.setIsDeleted(Boolean.FALSE);
                                    actionUtilisateurList.add(actionutilisateurupdate);
                                });


                            }else{
                                ActionUtilisateur actionUtilisateur = new ActionUtilisateur();
                                actionUtilisateur.setStatus(status);
                                actionUtilisateur.setCreatedAt(Utilities.getCurrentDate());
                                actionUtilisateur.setStatusBscs(arg.getStatus());
                                actionUtilisateur.setOfferName(arg.getOfferName());
                                actionUtilisateur.setSerialNumber(arg.getSerialNumber());
                                actionUtilisateur.setContractId(String.valueOf(arg.getContractId()));
                                actionUtilisateur.setFromBss(Boolean.TRUE);
                                actionUtilisateur.setTmCode(String.valueOf(arg.getTariffModelCode()));
                                actionUtilisateur.setPortNumber(String.valueOf(arg.getPortNumber()));
                                actionUtilisateur.setActivationDate(Utilities.formatDateAnyFormat(arg.getActivationDate(),"yyyy-MM-dd HH:mm:ss"));
                                actionUtilisateur.setCategory(category);
                                actionUtilisateur.setNumero(AES.encrypt(arg.getMsisdn(),StatusApiEnum.SECRET_KEY));
                                actionUtilisateur.setIsMachine(arg.isFrozen());
                                actionUtilisateur.setTypeNumero(typeNumero);
                                actionUtilisateur.setIsExcelNumber(Boolean.TRUE);
                                if(status.getCode().equalsIgnoreCase(StatusApiEnum.BLOCk)){
                                    actionUtilisateur.setLastBlocageDate(Utilities.getCurrentDate());
                                }
                                if(status.getCode().equalsIgnoreCase(StatusApiEnum.DEBLOCK)){
                                    actionUtilisateur.setLastDebloquage(Utilities.getCurrentDate());
                                }
                                if(arg.isFrozen()==Boolean.TRUE){
                                    actionUtilisateur.setLastMachineDate(Utilities.getCurrentDate());

                                }
                                actionUtilisateur.setStatut(StatusApiEnum.EFFECTUER);
                                if(status!=null){
                                    actionUtilisateur.setEmpreinte(Utilities.empreinte(status.getCode(),0,Utilities.getCurrentDateTime(),arg.getMsisdn()));
                                }else{
                                    actionUtilisateur.setEmpreinte(Utilities.empreinte("MISE EN MACHINE",1,Utilities.getCurrentDateTime(),arg.getMsisdn()));
                                }
                                actionUtilisateur.setDate(Utilities.getCurrentDate());
                                actionUtilisateur.setIsDeleted(Boolean.FALSE);

                                actionUtilisateurList.add(actionUtilisateur);
                            }
                            map.setNumero(arg.getMsisdn());
                            map.setOfferName(arg.getOfferName());
                            map.setSerialNumber(arg.getSerialNumber());
                            map.setContractId(String.valueOf(arg.getContractId()));
                            map.setTariffModelCode(String.valueOf(arg.getTariffModelCode()));
                            map.setPortNumber(String.valueOf(arg.getPortNumber()));
                            map.setLockStatus(status.getCode());
                            map.setStatus(arg.getStatus());
                            map.setActivationDate(arg.getActivationDate());
                            map.setFrozen(arg.isFrozen());
                            mapList.add(map);
                        }
                    });
                }
            }
        }

        if(Utilities.isNotEmpty(actionUtilisateurList)){
            log.info("liste a enregistré {}",actionUtilisateurList);
            actionUtilisateurRepository.saveAll(actionUtilisateurList);
        }

        return mapList;
    }


}
