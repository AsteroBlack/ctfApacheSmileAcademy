
/*
 * Java business for entity table action_utilisateur
 * Created on 2022-06-13 ( Time 13:07:16 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.business;

import ci.smile.simswaporange.dao.constante.Constant;
import ci.smile.simswaporange.dao.entity.Status;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;
import ci.smile.simswaporange.proxy.customizeclass.functions.ObjectUtilities;
import ci.smile.simswaporange.proxy.response.ApiResponse;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDtos;
import ci.smile.simswaporange.proxy.service.SimSwapServiceProxyService;
import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.contract.IBasicBusiness;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.ActionUtilisateurDto;
import ci.smile.simswaporange.utils.dto.HistoriqueDto;
import ci.smile.simswaporange.utils.dto.NumeroStoriesDto;
import ci.smile.simswaporange.utils.dto.customize.*;
import ci.smile.simswaporange.utils.dto.transformer.ActionUtilisateurTransformer;
import ci.smile.simswaporange.utils.enums.CategoryEnum;
import ci.smile.simswaporange.utils.enums.StatusApiEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import lombok.extern.java.Log;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder.Case;

import static org.apache.poi.ss.util.CellUtil.getCell;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.utils.URIBuilder;
import scala.runtime.RichFloat$;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.IntStream;

/**
 * BUSINESS for table "action_utilisateur"
 *
 * @author Geo
 */
@Log
@Component
@EnableScheduling
public class ActionUtilisateurBusiness
        implements IBasicBusiness <Request <ActionUtilisateurDto>, Response <ActionUtilisateurDto>>{

    private Response <ActionUtilisateurDto> response;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ParamsUtils paramsUtils;

    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlTemplate;
    @Autowired
    private ExceptionUtils exceptionUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectUtilities objectUtilities;
    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private HistoriqueBusiness historiqueBusiness;
    @Autowired
    private TypeNumeroRepository typeNumeroRepository;
    @Autowired
    private HistoriqueRepository historiqueRepository;
    @Autowired
    @Lazy
    private ActionEnMasseBusiness actionEnMasseBusiness;
    @Autowired
    private NumeroStoriesBusiness numeroStoriesBusiness;
    @Autowired
    private TypeDemandeRepository typeDemandeRepository;
    @Autowired
    private ActionEnMasseRepository actionEnMasseRepository;
    @Autowired
    private NumeroStoriesRepository numeroStoriesRepository;
    @Autowired
    private SimSwapServiceProxyService simSwapServiceProxyService;
    @Autowired
    private ActionUtilisateurRepository actionUtilisateurRepository;
    @Autowired
    private ProfilFonctionnaliteRepository profilFonctionnaliteRepository;

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dateTimeFormat;
    private Date currentDate;

    public ActionUtilisateurBusiness(){
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    /**
     * create ActionUtilisateur by using ActionUtilisateurDto as object.
     *
     * @param request
     * @return response
     * @throws Exception
     */

    @Transactional
    @Override
    public Response <ActionUtilisateurDto> create(Request <ActionUtilisateurDto> request,Locale locale)
            throws Exception{
        log.info("----begin create ActionUtilisateur-----");
        Response <ActionUtilisateurDto> response = new Response <>();
        List <ActionUtilisateur> items = new ArrayList <>();
        for(ActionUtilisateurDto dto: request.getDatas()){
            Map <String, Object> fieldsToVerify = new HashMap <>();
            fieldsToVerify.put("numero",dto.getNumero());
            fieldsToVerify.put("motif",dto.getMotif());
            fieldsToVerify.put("typeNumeroId",dto.getTypeNumeroId());
            fieldsToVerify.put("idCategory",dto.getIdCategory());
            if(!Validate.RequiredValue(fieldsToVerify).isGood()){
                response.setStatus(functionalError.FIELD_EMPTY("veuillez renseigner ce champ " + Validate.getValidate().getField(),locale));
                response.setHasError(true);
                return response;
            }
            User existingUser = null;
            List <String> numeroAbloque = new ArrayList <>();
            if(request.getUser()!=null&&request.getUser()>0){
                existingUser = userRepository.findOne(request.getUser(),false);
                Validate.EntityNotExiste(existingUser);
                Validate.EntityNotExiste(existingUser.getCategory());
            }
            TypeNumero typeNumero = null;
            if(dto.getTypeNumeroId()!=null&&dto.getTypeNumeroId()>0){
                typeNumero = typeNumeroRepository.findOne(dto.getTypeNumeroId(),false);
                Validate.EntityNotExiste(typeNumero);
                if(typeNumero.getLibelle().equalsIgnoreCase("BSCS")){
                    response.setStatus(functionalError.DISALLOWED_OPERATION("permis pour corail",locale));
                    response.setHasError(Boolean.TRUE);
                    return response;
                }
            }
            Category category = categoryRepository.findOne(dto.getIdCategory(), Boolean.FALSE);
            Validate.EntityNotExiste(category);
            List <ActionUtilisateur> actionUtilisateurs = actionUtilisateurRepository.findByDepartement(typeNumero.getId(),category.getId(),AES.encrypt(dto.getNumero().replaceAll("\\s",""),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
            if(Utilities.isNotEmpty(actionUtilisateurs)){
                ActionUtilisateur actionUtilisateur = actionUtilisateurs.get(0);
                switch(existingUser.getCategory().getCode()){
                    case "TELCO":
                        Category categoryOmci = categoryRepository.findByCode(StatusApiEnum.OMCI_CODE,Boolean.FALSE);
                        List <ActionUtilisateur> omciCategory = actionUtilisateurRepository.findByDepartement(typeNumero.getId(),categoryOmci.getId(),AES.encrypt(dto.getNumero().replaceAll("\\s",""),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                        if(Utilities.isNotEmpty(omciCategory)){
                            Validate.EntityExiste(omciCategory);
                        }
                        actionUtilisateur.setCategory(categoryOmci);
                        numeroAbloque.add(dto.getNumero());
                        LockUnLockFreezeDtos lockUnLockFreezeDto = simSwapServiceProxyService.lockNumber(numeroAbloque,UUID.randomUUID().toString());
                        if(lockUnLockFreezeDto.getHasError()==Boolean.TRUE){
                            response.setRaison(lockUnLockFreezeDto.getMessage()!=null?lockUnLockFreezeDto.getMessage():"une erreur est survenu lors de l'appel réseau");
                            response.setHasError(Boolean.TRUE);
                            response.setStatus(functionalError.DISALLOWED_OPERATION(lockUnLockFreezeDto.getMessage(),locale));
                            return response;
                        }
                        if(Utilities.isNotEmpty(lockUnLockFreezeDto.getData())){
                            simSwapServiceProxyService.findByMsisdn(UUID.randomUUID().toString(),numeroAbloque,existingUser.getId(),Boolean.TRUE);
                        }
                        break;
                    case "OMCI":
                        Category categoryTelco = categoryRepository.findByCode(StatusApiEnum.TELCO_CODE,Boolean.FALSE);
                        List <ActionUtilisateur> telcoCategory = actionUtilisateurRepository.findByDepartement(typeNumero.getId(),categoryTelco.getId(),AES.encrypt(dto.getNumero().replaceAll("\\s",""),StatusApiEnum.SECRET_KEY),Boolean.FALSE);
                        if(Utilities.isNotEmpty(telcoCategory)){
                            Validate.EntityExiste(telcoCategory);
                        }
                        actionUtilisateur.setCategory(categoryTelco);
                        numeroAbloque.add(dto.getNumero());
                        LockUnLockFreezeDtos lockUnLockFreezeDtos = simSwapServiceProxyService.lockNumber(numeroAbloque,UUID.randomUUID().toString());
                        if(lockUnLockFreezeDtos.getHasError()==Boolean.TRUE){
                            response.setRaison(lockUnLockFreezeDtos.getMessage()!=null?lockUnLockFreezeDtos.getMessage():"une erreur est survenu lors de l'appel réseau");
                            response.setHasError(Boolean.TRUE);
                            response.setStatus(functionalError.DISALLOWED_OPERATION(lockUnLockFreezeDtos.getMessage(),locale));
                            return response;
                        }
                        if(Utilities.isNotEmpty(lockUnLockFreezeDtos.getData())){
                            simSwapServiceProxyService.findByMsisdn(UUID.randomUUID().toString(),numeroAbloque,existingUser.getId(),Boolean.TRUE);
                        }
                        break;
                }
                actionUtilisateur.setMotif(dto.getMotif());
                actionUtilisateur.setUser(existingUser);
                actionUtilisateur.setUpdatedBy(existingUser.getId());
                actionUtilisateur.setUpdatedAt(Utilities.getCurrentDate());
                items.add(actionUtilisateur);
                List <ActionUtilisateur> toDeleted = actionUtilisateurs.stream().skip(1).collect(Collectors.toList());
                if(Utilities.isNotEmpty(toDeleted)){
                    toDeleted.stream().forEach(arg->{
                        arg.setIsDeleted(Boolean.TRUE);
                        actionUtilisateurRepository.save(arg);
                    });
                }
            }else{
                response.setNumbers(dto.getNumero());
                response.setHasError(Boolean.TRUE);
                response.setStatus(functionalError.DISALLOWED_OPERATION("Aucun numéro concerné par le transfert",locale));
                response.setRaison("le numéro existe pas dans cette catégorie");
                return response;
            }

        }
        if(!items.isEmpty()){
            List <ActionUtilisateur> itemsSaved = actionUtilisateurRepository.saveAll(items);
            List <ActionUtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                    ?ActionUtilisateurTransformer.INSTANCE.toLiteDtos(itemsSaved)
                    :ActionUtilisateurTransformer.INSTANCE.toDtos(itemsSaved);
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
        log.info("----end create ActionUtilisateur-----");
        return response;
    }

    /**
     * update ActionUtilisateur by using ActionUtilisateurDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response <ActionUtilisateurDto> update(Request <ActionUtilisateurDto> request,Locale locale)
            throws ParseException{
        log.info("----begin update ActionUtilisateur-----");
        Response <ActionUtilisateurDto> response = new Response <ActionUtilisateurDto>();
        List <ActionUtilisateur> items = new ArrayList <ActionUtilisateur>();
        String data = "";
        for(ActionUtilisateurDto dto: request.getDatas()){
            // Definir les parametres obligatoires
            Map <String, Object> fieldsToVerify = new HashMap <String, Object>();
            fieldsToVerify.put("id",dto.getId());
            if(!Validate.RequiredValue(fieldsToVerify).isGood()){
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
                response.setHasError(true);
                return response;
            }

            // Verifier si la actionUtilisateur existe
            ActionUtilisateur entityToSave = null;
            entityToSave = actionUtilisateurRepository.findOne(dto.getId(),false);
            if(entityToSave==null){
                response.setStatus(functionalError.DATA_NOT_EXIST("actionUtilisateur id -> "+dto.getId(),locale));
                response.setHasError(true);
                return response;
            }

            // Verify if status exist
            if(dto.getIdStatus()!=null&&dto.getIdStatus()>0){
                Status existingStatus = statusRepository.findOne(dto.getIdStatus(),false);
                if(existingStatus==null){
                    response.setStatus(
                            functionalError.DATA_NOT_EXIST("status idStatus -> "+dto.getIdStatus(),locale));
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
            if(dto.getCreatedBy()!=null&&dto.getCreatedBy()>0){
                entityToSave.setUpdatedBy(dto.getCreatedBy());
            }
            if(dto.getIsMachine()!=null&&dto.getIsMachine()!=Boolean.FALSE){
                entityToSave.setIsMachine(Boolean.TRUE);
            }
            if(dto.getUpdatedBy()!=null&&dto.getUpdatedBy()>0){
                entityToSave.setUpdatedBy(dto.getUpdatedBy());
            }
            if(Utilities.notBlank(dto.getLastDebloquage())){
                entityToSave.setLastDebloquage(Utilities.getCurrentDate());
            }
            if(Utilities.notBlank(dto.getLastBlocageDate())){
                entityToSave.setLastBlocageDate(Utilities.getCurrentDate());
            }
            if(Utilities.notBlank(dto.getLastMachineDate())){
                entityToSave.setLastMachineDate(Utilities.getCurrentDate());
            }
            if(Utilities.notBlank(dto.getNumero())){
                entityToSave.setNumero(dto.getNumero());
            }
            if(Utilities.notBlank(dto.getEmpreinte())){
                entityToSave.setEmpreinte(dto.getEmpreinte());
            }
            if(dto.getIdProfil()!=null&&dto.getIdProfil()>0){
                Profil dataProfil = profilRepository.findOne(dto.getIdProfil(),Boolean.FALSE);
                entityToSave.setProfil(dataProfil);
            }
            if(dto.getTypeNumeroId()!=null&&dto.getTypeNumeroId()>0){
                TypeNumero typeNumero = typeNumeroRepository.findOne(dto.getTypeNumeroId(),Boolean.FALSE);
                entityToSave.setTypeNumero(typeNumero);
            }
            if(Utilities.notBlank(dto.getMotif())){
                entityToSave.setMotif(dto.getMotif());
            }
            if(dto.getFromBss()!=null){
                entityToSave.setFromBss(dto.getFromBss());
            }
            if(Utilities.notBlank(dto.getStatut())){
                entityToSave.setStatut(dto.getStatut());
            }
            if(Utilities.notBlank(dto.getStatusBscs())){
                entityToSave.setStatusBscs(dto.getStatusBscs());
            }
            if(Utilities.notBlank(dto.getActivationDate())){
                entityToSave.setActivationDate(dateTimeFormat.parse(dto.getActivationDate()));
            }
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            items.add(entityToSave);
            System.out.println(items);
        }

        if(!items.isEmpty()){
            List <ActionUtilisateur> itemsSaved = null;
            itemsSaved = actionUtilisateurRepository.saveAll(items);
            if(itemsSaved==null){
                response.setStatus(functionalError.SAVE_FAIL("actionUtilisateur",locale));
                response.setHasError(true);
                return response;
            }
            List <ActionUtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                    ?ActionUtilisateurTransformer.INSTANCE.toLiteDtos(itemsSaved)
                    :ActionUtilisateurTransformer.INSTANCE.toDtos(itemsSaved);

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
        response.setStatus(functionalError.SUCCESS("",locale));
        log.info("----end update ActionUtilisateur-----");
        return response;
    }

    /**
     * delete ActionUtilisateur by using ActionUtilisateurDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response <ActionUtilisateurDto> delete(Request <ActionUtilisateurDto> request,Locale locale){
        log.info("----begin delete ActionUtilisateur-----");

        Response <ActionUtilisateurDto> response = new Response <ActionUtilisateurDto>();
        List <ActionUtilisateur> items = new ArrayList <ActionUtilisateur>();

        for(ActionUtilisateurDto dto: request.getDatas()){
            // Definir les parametres obligatoires
            Map <String, Object> fieldsToVerify = new HashMap <String, Object>();
            fieldsToVerify.put("id",dto.getId());
            if(!Validate.RequiredValue(fieldsToVerify).isGood()){
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
                response.setHasError(true);
                return response;
            }

            // Verifier si la actionUtilisateur existe
            ActionUtilisateur existingEntity = null;
            existingEntity = actionUtilisateurRepository.findOne(dto.getId(),false);
            if(existingEntity==null){
                response.setStatus(functionalError.DATA_NOT_EXIST("actionUtilisateur -> "+dto.getId(),locale));
                response.setHasError(true);
                return response;
            }
            // -----------------------------------------------------------------------
            // ----------- CHECK IF DATA IS USED
            // -----------------------------------------------------------------------

            existingEntity.setIsDeleted(true);
            items.add(existingEntity);
        }

        if(!items.isEmpty()){
            // supprimer les donnees en base
            actionUtilisateurRepository.saveAll(items);
            response.setStatus(functionalError.SUCCESS("",locale));
            response.setHasError(false);
        }

        log.info("----end delete ActionUtilisateur-----");
        response.setStatus(functionalError.SUCCESS("",locale));
        return response;
    }

    /**
     * get ActionUtilisateur by using ActionUtilisateurDto as object.
     *
     * @param request
     * @return response
     */

    @Override
    public Response <ActionUtilisateurDto> getByCriteria(Request <ActionUtilisateurDto> request,Locale locale)
            throws Exception{
        log.info("----begin get ActionUtilisateur-----");
        List <ActionUtilisateurDto> actionUtilisateurDtos;
        Response <ActionUtilisateurDto> response = new Response <>();
        ActionUtilisateurDto req = request.getData();
        if(Utilities.notBlank(request.getData().getNumero())){
            String numero = AES.encrypt(request.getData().getNumero().trim().replaceAll("\\s",""),StatusApiEnum.SECRET_KEY);
            req.setNumero(numero);
        }
        User user = userRepository.findOne(request.getUser(),false);
        if(user==null){
            response.setStatus(functionalError.DATA_EMPTY("ID USER",locale));
            response.setHasError(true);
            return response;
        }
        Profil profilFound = profilRepository.findOne(user.getProfil()!=null?user.getProfil().getId():null,
                Boolean.FALSE);
        Validate.EntityNotExiste(profilFound);
        String codeCategory = user.getCategory().getCode();
        if(codeCategory==null){
            response.setStatus(functionalError.DATA_EMPTY("CODE CATEGORY",locale));
            response.setHasError(true);
            return response;
        }
        if(!codeCategory.equals(CategoryEnum.ADMIN_CODE)){
            if(req!=null){
                req.setIdCategory(user.getCategory().getId());
                request.setData(req);
            }
        }
        List <Fonctionnalite> fonctionnalites = profilFonctionnaliteRepository.findFonctionnaliteByProfilId(profilFound.getId(),Boolean.FALSE);
        boolean b = false;
        boolean another = false;
        if(Utilities.isNotEmpty(fonctionnalites)){
            for(Fonctionnalite fonctionnalite: fonctionnalites){
                if(fonctionnalite.getLibelle().equalsIgnoreCase("RESPONSABLE BACK-OFFICE")
                        ||fonctionnalite.getLibelle().equalsIgnoreCase("UTILISATEUR BACK-OFFICE")){
                    b = Boolean.TRUE;
                    break;
                }
            }
            b = fonctionnalites.stream()
                    .anyMatch(fonctionnalite->
                            fonctionnalite.getLibelle().equalsIgnoreCase("RESPONSABLE BACK-OFFICE")
                                    ||fonctionnalite.getLibelle().equalsIgnoreCase("UTILISATEUR BACK-OFFICE")
                    );
            another = fonctionnalites.stream()
                    .anyMatch(fonctionnalite->
                            fonctionnalite.getLibelle().equalsIgnoreCase("MANAGER FRAUDE-SÉCURITÉ")
                                    ||fonctionnalite.getLibelle().equalsIgnoreCase("ADMIN TECHNIQUE")
                                    ||fonctionnalite.getLibelle().equalsIgnoreCase("ADMIN MÉTIER")
                    );
        }
        if((profilFound.getLibelle().equalsIgnoreCase("RESPONSABLE BACK-OFFICE")||profilFound.getLibelle().equalsIgnoreCase("UTILISATEUR BACK-OFFICE")||b==Boolean.TRUE)&&another==Boolean.FALSE){
            req.setIsMachine(Boolean.FALSE);
            request.setData(req);
        }
        List <ActionUtilisateur> items = actionUtilisateurRepository.getByCriteria(request,em,locale);
        List <ActionUtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                ?ActionUtilisateurTransformer.INSTANCE.toLiteDtos(items)
                :ActionUtilisateurTransformer.INSTANCE.toDtos(items);
        final int size = items.size();
        List <String> listOfError = Collections.synchronizedList(new ArrayList <>());
        itemsDto.parallelStream().forEach(dto->{
            try{
                dto = getFullInfos(dto,size,request.getIsSimpleLoading(),locale);
            }catch(Exception e){
                listOfError.add(e.getMessage());
                e.printStackTrace();
            }
        });
        response.setItems(itemsDto);
        response.setCount(actionUtilisateurRepository.count(request,em,locale));
        if(Utilities.isNotEmpty(itemsDto) && request.getIsSearchNumbers() == null){
            response.setFilePathDoc(writeFileName(itemsDto));
            response.setItems(itemsDto);
        }
        response.setHasError(false);
        response.setActionEffectue("Vue Numero Sensible");
        response.setStatus(functionalError.SUCCESS("",locale));
        log.info("----end get ActionUtilisateur-----");

        return response;
    }

    @Scheduled(cron="0 0 1 * * *")
    public void cronRefreshAllNumber() throws Exception{
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        List <String> numbersToCreateOrUpdate = new ArrayList <>();
        List <Result> resultList = new ArrayList <>();
        List <ActionUtilisateur> actionUtilisateurList = actionUtilisateurRepository.findAll();
        if(Utilities.isNotEmpty(actionUtilisateurList)){
            List <String> collect = actionUtilisateurList.stream().map(arg->AES.decrypt(arg.getNumero(),StatusApiEnum.SECRET_KEY)).distinct().collect(Collectors.toList());
            if(Utilities.isNotEmpty(collect)){
                numbersToCreateOrUpdate.addAll(collect);
            }
        }
//        log.info("commence a raffraichir");
//        int batchSize = 10;
//        List<List<String>> lists = new ArrayList<>();
//        IntStream.range(0, (numbersToCreateOrUpdate.size() + batchSize - 1) / batchSize)
//                .mapToObj(i -> numbersToCreateOrUpdate.subList(i * batchSize, Math.min((i + 1) * batchSize, numbersToCreateOrUpdate.size())))
//                .forEach(batch -> lists.add(batch));
//        if (Utilities.isNotEmpty(lists)) {
//            log.info("les numeros sont pas vides");
//            lists.stream().forEach(data -> {
//                List<Result> resultat = simSwapServiceProxyService.msisdnForRefraiche(UUID.randomUUID().toString(), data);
//                resultList.addAll(resultat);
//            });
//        }

        log.info("commence a raffraichir");
        numbersToCreateOrUpdate.stream().forEach(arg->{
            List <String> listToRefraihe = new ArrayList <>();
            listToRefraihe.add(arg);
            List <Result> resultat = simSwapServiceProxyService.msisdnForRefraiche(UUID.randomUUID().toString(),listToRefraihe,null);
            resultList.addAll(resultat);
        });

        String fileName = actionEnMasseBusiness.writeFileName(resultList,"REFRESH");
        ActionEnMasse searchOne = new ActionEnMasse();
        searchOne.setLinkDownload(fileName);
        searchOne.setLienFichier(fileName);
        searchOne.setIsOk(Boolean.TRUE);
        searchOne.setIdentifiant(UUID.randomUUID().toString());
        searchOne.setLinkDownloadMasse(fileName);
        searchOne.setCode("RAFFRAICHISSEMENT_DES_NUMEROS_APRÈS_24H_"+sdfFileName.format(new Date()));
        searchOne.setIsAutomatically(Boolean.TRUE);
        searchOne.setIsDeleted(Boolean.FALSE);
        searchOne.setLibelle("RAFFRAICHISSEMENT DES NUMEROS APRÈS 24H");
        searchOne.setCreatedAt(Utilities.getCurrentDate());
        searchOne.setCode("RAFFRAICHISSEMENT DES NUMEROS APRÈS 24H");
        actionEnMasseRepository.save(searchOne);
    }

    public void cronRefreshAllNumberForActionenMasse(Integer user) throws Exception{
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        List <String> numbersToCreateOrUpdate = new ArrayList <>();
        List <Result> resultList = new ArrayList <>();
        User existingUser = userRepository.findOne(user,Boolean.FALSE);
        List <ActionUtilisateur> actionUtilisateurList = actionUtilisateurRepository.findAll();
        if(Utilities.isNotEmpty(actionUtilisateurList)){
            List <String> collect = actionUtilisateurList.stream().map(arg->AES.decrypt(arg.getNumero(),StatusApiEnum.SECRET_KEY)).distinct().collect(Collectors.toList());
            if(Utilities.isNotEmpty(collect)){
                numbersToCreateOrUpdate.addAll(collect);
            }
        }
        log.info("commence a raffraichir");
        numbersToCreateOrUpdate.stream().forEach(arg->{
            List <String> listToRefraihe = new ArrayList <>();
            listToRefraihe.add(arg);
            List <Result> resultat = simSwapServiceProxyService.msisdnForRefraiche(UUID.randomUUID().toString(),listToRefraihe,existingUser);
            resultList.addAll(resultat);
        });
        String fileName = actionEnMasseBusiness.writeFileName(resultList,"REFRESH");
        ActionEnMasse searchOne = new ActionEnMasse();
        searchOne.setLinkDownload(fileName);
        searchOne.setLienFichier(fileName);
        searchOne.setIdentifiant(UUID.randomUUID().toString());
        searchOne.setLinkDownloadMasse(fileName);
        searchOne.setIsOk(Boolean.TRUE);
        searchOne.setCode("RAFFRAICHISSEMENT_DES_NUMEROS_APRÈS_24H_"+sdfFileName.format(new Date()));
        searchOne.setIsAutomatically(Boolean.TRUE);
        searchOne.setIsDeleted(Boolean.FALSE);
        searchOne.setLibelle("RAFFRAICHISSEMENT DES NUMEROS APRÈS 24H");
        searchOne.setCreatedAt(Utilities.getCurrentDate());
        searchOne.setCode("RAFFRAICHISSEMENT DES NUMEROS APRÈS 24H");
        actionEnMasseRepository.save(searchOne);
    }


    public String writeFileName(List <ActionUtilisateurDto> dataActionLite) throws Exception{
        SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        ClassPathResource templateClassPathResource = new ClassPathResource("templates/list_of_numbers_by_tab.xlsx");
        InputStream inputStreamFile = templateClassPathResource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStreamFile);
        XSSFSheet sheet = workbook.getSheetAt(0); // la 1ere feuille du classeur excel
        Cell cell = null;
        Row row = null;
        int rowIndex = 1;
        for(ActionUtilisateurDto actionUtilisateur: dataActionLite){
            // Renseigner les ids
            log.info("--row--"+rowIndex);
            row = sheet.getRow(rowIndex);
            if(row==null){
                row = sheet.createRow(rowIndex);
            }
            cell = getCell(row,0);
            cell.setCellValue(actionUtilisateur.getUserLogin());
            cell = getCell(row,1);
            cell.setCellValue(actionUtilisateur.getMotif());
            cell = getCell(row,2);
            cell.setCellValue(actionUtilisateur.getNumero());
            cell = getCell(row,3);
            cell.setCellValue(actionUtilisateur.getStatusBscs());
            cell = getCell(row,4);
            cell.setCellValue(actionUtilisateur.getStatusCode());
            cell = getCell(row,5);
            cell.setCellValue(actionUtilisateur.getLastDebloquage());
            cell = getCell(row,6);
            cell.setCellValue(actionUtilisateur.getIsMachine()==Boolean.TRUE?"OUI":"NON");
            cell = getCell(row,7);
            cell.setCellValue(actionUtilisateur.getLastMachineDate());

            cell = getCell(row,8);
            cell.setCellValue(actionUtilisateur.getCategoryCode());
            cell = getCell(row,9);
            cell.setCellValue(actionUtilisateur.getPortNumber());
            cell = getCell(row,10);
            cell.setCellValue(actionUtilisateur.getSerialNumber());
            cell = getCell(row,11);
            cell.setCellValue(actionUtilisateur.getOfferName());
            rowIndex++;
        }
        // Ajuster les colonnes
        for(int i = 0;i<=Utilities.getColumnIndex("I");i++){
            sheet.autoSizeColumn(i);
        }
        inputStreamFile.close();
        String fileName = Utilities.saveFile("LIST_SENSIBLE_NUMBER_"+sdfFileName.format(new Date()),"xlsx",workbook,
                paramsUtils);

        return Utilities.getFileUrlLink(fileName,paramsUtils);
    }


    public Response <ActionUtilisateurDto> getByCriteriaLite(Request <ActionUtilisateurDto> request,Locale locale)
            throws Exception{
        log.info("----begin get -----");

        Response <ActionUtilisateurDto> response = new Response <ActionUtilisateurDto>();
        List <ActionUtilisateur> items = actionUtilisateurRepository.getByCriteria(request,em,locale);

        if(items!=null&&!items.isEmpty()){
            List <ActionUtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                    ?ActionUtilisateurTransformer.INSTANCE.toLiteDtos(items)
                    :ActionUtilisateurTransformer.INSTANCE.toDtos(items);

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
            response.setItems(itemsDto);
            response.setCount(actionUtilisateurRepository.count(request,em,locale));
            response.setStatus(functionalError.SUCCESS("",locale));
            response.setHasError(false);
        }else{
            response.setStatus(functionalError.DATA_EMPTY("ActionUtilisateur",locale));
            response.setHasError(false);
            return response;
        }

        log.info("----end get ActionUtilisateur-----");
        return response;
    }

    /**
     * get full ActionUtilisateurDto by using ActionUtilisateur as object.
     *
     * @param dto
     * @param size
     * @param isSimpleLoading
     * @param locale
     * @return
     * @throws Exception
     */
    private ActionUtilisateurDto getFullInfos(ActionUtilisateurDto dto,Integer size,Boolean isSimpleLoading,
                                              Locale locale) throws Exception{
        Category category = null;
        System.out.println(dto.getNumero());
        TypeNumero typeNumero = typeNumeroRepository.findByLibelle(StatusApiEnum.CORAIL, Boolean.FALSE);
        TypeDemande typeDemande = typeDemandeRepository.findByCode(StatusApiEnum.INITIALISER, Boolean.FALSE);
        if(Utilities.notBlank(dto.getNumero())){
            dto.setNumero(AES.decrypt(dto.getNumero(),StatusApiEnum.SECRET_KEY));
        }
        ActionUtilisateur actionUtilisateur = actionUtilisateurRepository.findOne(dto.getId(), Boolean.FALSE);
        if(actionUtilisateur != null){
            if(actionUtilisateur.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.OMCI_CODE)){
                category = objectUtilities.categoriePair().getCategorieTelco();
            }else {
                category = objectUtilities.categoriePair().getCategorieOmci();
            }
            Demande demandeTransferred = actionUtilisateurRepository.findByTransfertDemande(typeNumero.getId(),AES.decrypt(actionUtilisateur.getNumero(),StatusApiEnum.SECRET_KEY),typeDemande.getId(),category.getId(), Boolean.TRUE, Boolean.FALSE );
            dto.setIsTransferred(Boolean.TRUE);
            if(demandeTransferred == null){
                dto.setIsTransferred(Boolean.FALSE);
            }
            dto.setIsUnlocked(Boolean.TRUE);
            Demande demandeUnlocked = actionUtilisateurRepository.findByUnlockDemande(typeNumero.getId(),AES.decrypt(actionUtilisateur.getNumero(),StatusApiEnum.SECRET_KEY),typeDemande.getId(),actionUtilisateur.getCategory().getId(), Boolean.FALSE );
            if(demandeUnlocked == null){
                dto.setIsUnlocked(Boolean.FALSE);
            }
        }
        if(Utilities.isTrue(isSimpleLoading)){
            return dto;
        }
        if(size>1){
            return dto;
        }
        return dto;
    }


    public static Cell getCell(Row row,Integer index){
        return (row.getCell(index)==null)?row.createCell(index):row.getCell(index);
    }

    @SuppressWarnings("unused")
    //@Scheduled(cron = "${app.update.status.transaction.second}")
    public void cronEXcelFile() throws ParseException{
        log.info(dateTimeFormat.format(Utilities.getCurrentDate())+":.......... ");
//		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale("","");
        List <ActionUtilisateur> datasFile = new ArrayList <>();
        datasFile = actionUtilisateurRepository.findExcelFile(Boolean.TRUE,Boolean.FALSE);
        if(datasFile!=null){
            for(ActionUtilisateur actionUtilisateur: datasFile){
                if(actionUtilisateur.getIsExcelNumber()==Boolean.TRUE){
                    System.out.println("....................ON EST ENTRE.........................");
                    System.out.println(actionUtilisateur);
                    actionUtilisateur.setIsExcelNumber(Boolean.FALSE);
                    actionUtilisateurRepository.save(actionUtilisateur);
                }
            }
        }
//		update(datas, locale);

        log.info("Fin Cron pour les fichiers importé excel :.....................");
        System.out.println("...................................................................................... ");
    }

    @SuppressWarnings("unused")
    @Transactional(rollbackFor={RuntimeException.class,Exception.class})
    public Response <ActionUtilisateurDto> uploadFileCsv(String file_name_full,Integer userId) throws IOException{
        log.info("----begin uploadLiteLogementPavillon-----");
        Locale locale = new Locale("fr");
        Response <ActionUtilisateurDto> response = new Response <ActionUtilisateurDto>();
        if(userId==null){
            response.setStatus(functionalError.FIELD_EMPTY("Champs vide",locale));
            response.setHasError(true);
            return response;
        }

        User findUser = userRepository.findOne(userId,Boolean.FALSE);
        try{
            System.out.println("--pathFile--"+file_name_full);
            if(Utilities.getImageExtension(file_name_full).equalsIgnoreCase("csv")){
                ArrayList <String> datasNumero = new ArrayList <String>();
                try(Reader reader = Files.newBufferedReader(Paths.get(file_name_full));
                    CSVParser csvParser = new CSVParser(reader,
                            CSVFormat.DEFAULT.withHeader("numero").withIgnoreHeaderCase().withTrim());){
                    for(CSVRecord csvRecord: csvParser){
                        String numero = csvRecord.get("numero");
                        if(csvRecord.getRecordNumber()>1){
                            System.out.println("Numero : "+numero);
                            datasNumero.add(numero);
                        }
                    }
                }
                List <ActionUtilisateur> dataAction = new ArrayList <ActionUtilisateur>();
                List <Historique> datasHistorique = new ArrayList <Historique>();
                ArrayList <String> newList = Utilities.removeDuplicates(datasNumero);
                List <NumeroStoriesDto> itemsStories = new ArrayList <NumeroStoriesDto>();
                System.out.println("--------->"+newList);
                if(Utilities.isNotEmpty(newList)){
                    for(String numero: newList){
                        List <ActionUtilisateur> dataActionUser = actionUtilisateurRepository.findByOneNumero(userId,
                                findUser.getCategory().getId(),AES.encrypt(numero,StatusApiEnum.SECRET_KEY),
                                Boolean.FALSE);
                        if(Utilities.isNotEmpty(dataActionUser)){
                            Status oneStatus = new Status();
                            oneStatus = statusRepository.findOne(StatusApiEnum.SERVICE_API_BLOCK_SIM,Boolean.FALSE);
                            ActionUtilisateurDto actionUser = new ActionUtilisateurDto();
                            actionUser.setNumero(AES.encrypt(numero,StatusApiEnum.SECRET_KEY));
//								actionUser.setNumero(Utilities.encryptAES(numero));
//								actionUser.setEmpreinte(Utilities.empreinte(oneStatus.getCode(),userId,Utilities.getCurrentDateTime(), numero));
                            actionUser.setIsDeleted(Boolean.FALSE);
                            actionUser.setIsExcelNumber(Boolean.TRUE);
                            actionUser.setIdUser(userId);
                            actionUser.setCreatedBy(userId);
                            ActionUtilisateur actionUserToSave = new ActionUtilisateur();
                            User user = userRepository.findOne(userId,Boolean.FALSE);
//							actionUserToSave = ActionUtilisateurTransformer.INSTANCE.toEntity(actionUser, null,
//									user.getCategory() != null ? user.getCategory() : null, user,
//									user.getProfil() != null ? user.getProfil() : null, null);

                            actionUserToSave.setCreatedAt(Utilities.getCurrentDate());
                            actionUserToSave.setUpdatedAt(Utilities.getCurrentDate());
                            dataAction.add(actionUserToSave);
                            NumeroStoriesDto dataStories = new NumeroStoriesDto();
                            dataStories.setCreatedBy(userId);
                            dataStories.setCreatedAt(Utilities.getCurrentDateTime());
                            dataStories.setNumero(numero);
                            dataStories.setIdProfil(user!=null?user.getProfil().getId():null);
                            dataStories.setIsDeleted(Boolean.FALSE);
                            dataStories.setIdStatut(StatusApiEnum.SERVICE_API_BLOCK_SIM);
                            itemsStories.add(dataStories);
                            if(Utilities.isNotEmpty(itemsStories)){
                                Request <NumeroStoriesDto> requestStories = new Request <>();
                                requestStories.setDatas(itemsStories);
                                Response <NumeroStoriesDto> datasSaved = numeroStoriesBusiness.create(requestStories,
                                        locale);
                            }
                        }
                    }
                }
                if(Utilities.isNotEmpty(dataAction)){
                    List <ActionUtilisateur> dataActionLite = actionUtilisateurRepository.saveAll(dataAction);
                    System.out.println("Item"+dataAction);
//						response.setItems(itemsDto);
                    response.setCount(Long.parseLong(String.valueOf(dataActionLite.size())));
                    response.setItems(ActionUtilisateurTransformer.INSTANCE.toDtos(dataAction));
                    response.setStatus(functionalError.SUCCESS("",locale));
                    response.setHasError(Boolean.FALSE);
                    response.setActionEffectue("Bloquage de numero en masse depuis fichier CSV");
                }
                if(!Utilities.isNotEmpty(dataAction)){
                    response.setStatus(functionalError.FILE_GENERATION_ERROR("",locale));
                    response.setHasError(Boolean.TRUE);
                    response.setActionEffectue("Bloquage de numero en masse depuis fichier CSV");
                }

                historiqueRepository.saveAll(datasHistorique);
            }else{
                response.setStatus(functionalError.FILE_GENERATION_ERROR("",locale));
                response.setHasError(Boolean.TRUE);
                response.setActionEffectue("Bloquage de numero en masse depuis fichier CSV");
            }

        }catch(Exception e){
            response.setStatus(functionalError.FILE_GENERATION_ERROR("",locale));
            response.setHasError(Boolean.TRUE);
            response.setActionEffectue("Bloquage de numero en masse depuis fichier CSV");
        }

        return response;
    }

    public Response <ActionUtilisateurDto> getNumberFromExcelFile(Request <ActionUtilisateurDto> request,Locale locale)
            throws Exception{
        log.info("----begin number from excel File-----");

        Response <ActionUtilisateurDto> response = new Response <ActionUtilisateurDto>();
        ActionUtilisateurDto dataAction = new ActionUtilisateurDto();
        ActionUtilisateurDto dto = request.getData();
        dto.setIsExcelNumber(Boolean.TRUE);
        Response <ActionUtilisateurDto> actionGetted = getByCriteria(request,locale);

//		Request<ActionUtilisateurDto> datasAction = new Request<ActionUtilisateurDto>();
//		datasAction.setData(dataAction);

        log.info("----end number from excel file-----");
        response.setActionEffectue("Numero ");
        response.setHasError(false);
        response.setCount(actionGetted.getCount());
        response.setItems(actionGetted.getItems());
        response.setStatus(functionalError.SUCCESS("",locale));
        return response;
    }

    public Response <HistoriqueDto> getConnexionUser(Request <HistoriqueDto> request,Locale locale) throws Exception{
        log.info("----begin number from connexion File-----");
        Response <HistoriqueDto> response = new Response <HistoriqueDto>();
        HistoriqueDto dataAction = new HistoriqueDto();
        HistoriqueDto dto = request.getData();
        dto.setIsConnexion(Boolean.TRUE);
        Response <HistoriqueDto> actionGetted = historiqueBusiness.getByCriteria(request,locale);
//		Request<ActionUtilisateurDto> datasAction = new Request<ActionUtilisateurDto>();
//		datasAction.setData(dataAction);
        log.info("----end number from connexion file-----");
        response.setActionEffectue("Connexion utilisateur");
        response.setHasError(false);
        response.setCount(actionGetted.getCount());
        response.setItems(actionGetted.getItems());
        response.setStatus(functionalError.SUCCESS("",locale));
        return response;
    }

    public Response <List <DataKeyValues>> OptimizeDashboard(Request <ActionUtilisateurDto> request,Locale locale)
            throws Exception{
        log.info("----begin getDashboard-----");
        Response <List <DataKeyValues>> response = new Response <>();
        List <Map <String, Object>> listReponseMap = new ArrayList <>();
        int totalOmci = 0;
        int totalTelco = 0;
        int totalOmciSimswap = 0;
        int totalTelcoSimswap = 0;
        List <DataKeyValues> dataKeyValues = new ArrayList <>();
        // Verification de l'existence du USER connecté
        User user = userRepository.findOne(request.getUser(),false);
        Validate.EntityNotExiste(user);
        Validate.EntityNotExiste(user.getCategory());
        ActionUtilisateurDto dto = request.getData();
        String startDB = null;
        String endDB = null;
        Date start = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dto.getCreatedAtParam().getStart());
        Date end = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dto.getCreatedAtParam().getEnd());
        startDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start);
        endDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end);
        if(!Utilities.notBlank(startDB)||!Utilities.notBlank(endDB)){
            throw new CustomEntityNotFoundException("400","renseigné une période",Boolean.TRUE);
        }
        DataKeyValues dataValuesNSIMSWAP = new DataKeyValues();
        DataKeyValues dataValuesSIMSWAP = new DataKeyValues();
        TypeNumero typeNumero = typeNumeroRepository.findOne(dto.getTypeNumeroId(),Boolean.FALSE);
        Validate.EntityNotExiste(typeNumero);
        List <DataKey> NSMSWAP = getStringObjectMap(typeNumero,startDB,endDB,totalOmci,user,totalTelco);
        dataValuesNSIMSWAP.setKey("NSIMSWAP");
        dataValuesNSIMSWAP.setValues(NSMSWAP);
        //SIMSWAP
        List <DataKey> SMSWAP = extractSimswap(typeNumero,startDB,endDB,totalOmciSimswap,totalTelcoSimswap,user);
        dataValuesSIMSWAP.setKey("SIMSWAP");
        dataValuesSIMSWAP.setValues(SMSWAP);

        dataKeyValues.add(dataValuesNSIMSWAP);
        dataKeyValues.add(dataValuesSIMSWAP);

        log.info("----end number from connexion file-----");
        response.setActionEffectue("Vue Dashboard");
        response.setHasError(false);
        response.setItem(dataKeyValues);
        response.setStatus(functionalError.SUCCESS("",locale));

        return response;
    }

    private List <DataKey> extractSimswap(TypeNumero typeNumero,String startDB,String endDB,int totalOmciSimswap,int totalTelcoSimswap,User user){
        List <DataValues> valuesOmci = new ArrayList <>();
        List <DataValues> valuesTelco = new ArrayList <>();
        List <DataKey> dataKeys = new ArrayList <>();

        String queryTotalBloqueSimswap = getStringSimswap(typeNumero,StatusApiEnum.OMCI_CATEGORY,startDB,endDB,StatusApiEnum.BLOQUER,Boolean.FALSE);
        Map <String, Object> totalNumberBoquerSimswap;
        totalNumberBoquerSimswap = mysqlTemplate.queryForMap(queryTotalBloqueSimswap);
        if(totalNumberBoquerSimswap!=null){
            totalOmciSimswap += Integer.valueOf(totalNumberBoquerSimswap.get("total_elements").toString());
        }
        String queryTotalDebloqueSimswap = getStringSimswap(typeNumero,StatusApiEnum.OMCI_CATEGORY,startDB,endDB,StatusApiEnum.DEBLOQUER,Boolean.FALSE);
        Map <String, Object> totalNumberDebloqueSimswap;
        totalNumberDebloqueSimswap = mysqlTemplate.queryForMap(queryTotalDebloqueSimswap);
        if(totalNumberDebloqueSimswap!=null){
            totalOmciSimswap += Integer.valueOf(totalNumberDebloqueSimswap.get("total_elements").toString());
        }
        String queryTotalMiseEnMachineSimswap = getStringSimswap(typeNumero,StatusApiEnum.OMCI_CATEGORY,startDB,endDB,StatusApiEnum.DEBLOQUER,Boolean.TRUE);
        Map <String, Object> totalMiseEnMachineSimswap;
        totalMiseEnMachineSimswap = mysqlTemplate.queryForMap(queryTotalMiseEnMachineSimswap);
        if(totalNumberDebloqueSimswap!=null){
            totalOmciSimswap += Integer.valueOf(totalMiseEnMachineSimswap.get("total_elements").toString());
        }

        String queryTotalBloqueTelcoSimswap = getStringSimswap(typeNumero,StatusApiEnum.TELCO_CATEGORY,startDB,endDB,StatusApiEnum.BLOQUER,Boolean.FALSE);
        Map <String, Object> totalNumberBoquerTelcoSimswap;
        totalNumberBoquerTelcoSimswap = mysqlTemplate.queryForMap(queryTotalBloqueTelcoSimswap);
        if(totalNumberBoquerTelcoSimswap!=null){
            totalTelcoSimswap += Integer.valueOf(totalNumberBoquerTelcoSimswap.get("total_elements").toString());
        }
        String queryTotalDebloqueTelcoSimswap = getStringSimswap(typeNumero,StatusApiEnum.TELCO_CATEGORY,startDB,endDB,StatusApiEnum.DEBLOQUER,Boolean.FALSE);
        Map <String, Object> totalNumberDebloqueTelcoSimswap;
        totalNumberDebloqueTelcoSimswap = mysqlTemplate.queryForMap(queryTotalDebloqueTelcoSimswap);
        if(totalNumberDebloqueTelcoSimswap!=null){
            totalTelcoSimswap += Integer.valueOf(totalNumberDebloqueTelcoSimswap.get("total_elements").toString());
        }
        String queryTotalMiseEnMachineTelcoSimswap = getStringSimswap(typeNumero,StatusApiEnum.TELCO_CATEGORY,startDB,endDB,StatusApiEnum.DEBLOQUER,Boolean.TRUE);
        Map <String, Object> totalMiseEnMachineTelcoSimswap;
        totalMiseEnMachineTelcoSimswap = mysqlTemplate.queryForMap(queryTotalMiseEnMachineTelcoSimswap);
        if(totalMiseEnMachineTelcoSimswap!=null){
            totalTelcoSimswap += Integer.valueOf(totalMiseEnMachineTelcoSimswap.get("total_elements").toString());
        }
        if(user.getProfil().getLibelle().equalsIgnoreCase("MANAGER FRAUDE-SÉCURITÉ")||user.getProfil().getLibelle().equalsIgnoreCase("ADMIN MÉTIER")){
            //OMCI
            DataValues omciValues_BLOQUER = new DataValues();
            omciValues_BLOQUER.setKey("BLOQUER");
            omciValues_BLOQUER.setValue(totalNumberBoquerSimswap.get("total_elements")!=null?totalNumberBoquerSimswap.get("total_elements").toString():"0");
            valuesOmci.add(omciValues_BLOQUER);
            DataValues omciValues_DEBLOQUER = new DataValues();
            omciValues_DEBLOQUER.setKey("DEBLOQUER");
            omciValues_DEBLOQUER.setValue(totalNumberDebloqueSimswap.get("total_elements")!=null?totalNumberDebloqueSimswap.get("total_elements").toString():"0");
            valuesOmci.add(omciValues_DEBLOQUER);
            DataValues omciMiseEnMachine = new DataValues();
            omciMiseEnMachine.setKey("MISE_EN_MACHINE");
            omciMiseEnMachine.setValue(totalMiseEnMachineSimswap.get("total_elements")!=null?totalMiseEnMachineSimswap.get("total_elements").toString():"0");
            valuesOmci.add(omciMiseEnMachine);
            DataValues omciTotal = new DataValues();
            omciTotal.setKey("TOTAL");
            omciTotal.setValue(totalOmciSimswap>0?String.valueOf(totalOmciSimswap):"0");
            valuesOmci.add(omciTotal);
            DataKey dataKeyOmci = new DataKey();
            dataKeyOmci.setKey("OMCI");
            dataKeyOmci.setValues(valuesOmci);
            dataKeys.add(dataKeyOmci);

            //TELCO
            DataValues telcoValues_DEBLOQUER = new DataValues();
            telcoValues_DEBLOQUER.setKey("DEBLOQUER");
            telcoValues_DEBLOQUER.setValue(totalNumberDebloqueTelcoSimswap.get("total_elements")!=null?totalNumberDebloqueTelcoSimswap.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_DEBLOQUER);
            DataValues telcoValues_BLOQUER = new DataValues();
            telcoValues_BLOQUER.setKey("BLOQUER");
            telcoValues_BLOQUER.setValue(totalNumberBoquerTelcoSimswap.get("total_elements")!=null?totalNumberBoquerTelcoSimswap.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_BLOQUER);
            DataValues telcoValues_MISE_EN_MACHINE = new DataValues();
            telcoValues_MISE_EN_MACHINE.setKey("MISE_EN_MACHINE");
            telcoValues_MISE_EN_MACHINE.setValue(totalMiseEnMachineTelcoSimswap.get("total_elements")!=null?totalMiseEnMachineTelcoSimswap.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_MISE_EN_MACHINE);
            DataValues telcoTotal = new DataValues();
            telcoTotal.setKey("TOTAL");
            telcoTotal.setValue(totalTelcoSimswap>0?String.valueOf(totalTelcoSimswap):"0");
            valuesTelco.add(telcoTotal);
            DataKey dataKeyTelco = new DataKey();
            dataKeyTelco.setKey("TELCO");
            dataKeyTelco.setValues(valuesTelco);
            dataKeys.add(dataKeyTelco);

        }else if(user.getCategory().getId().equals(StatusApiEnum.TELCO_CATEGORY)){
            //TELCO
            DataValues telcoValues_DEBLOQUER = new DataValues();
            telcoValues_DEBLOQUER.setKey("DEBLOQUER");
            telcoValues_DEBLOQUER.setValue(totalNumberDebloqueTelcoSimswap.get("total_elements")!=null?totalNumberDebloqueTelcoSimswap.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_DEBLOQUER);
            DataValues telcoValues_BLOQUER = new DataValues();
            telcoValues_BLOQUER.setKey("BLOQUER");
            telcoValues_BLOQUER.setValue(totalNumberBoquerTelcoSimswap.get("total_elements")!=null?totalNumberBoquerTelcoSimswap.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_BLOQUER);
            DataValues telcoValues_MISE_EN_MACHINE = new DataValues();
            telcoValues_MISE_EN_MACHINE.setKey("MISE_EN_MACHINE");
            telcoValues_MISE_EN_MACHINE.setValue(totalMiseEnMachineTelcoSimswap.get("total_elements")!=null?totalMiseEnMachineTelcoSimswap.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_MISE_EN_MACHINE);
            DataValues telcoTotal = new DataValues();
            telcoTotal.setKey("TOTAL");
            telcoTotal.setValue(totalTelcoSimswap>0?String.valueOf(totalTelcoSimswap):"0");
            valuesTelco.add(telcoTotal);
            DataKey dataKeyTelco = new DataKey();
            dataKeyTelco.setKey("TELCO");
            dataKeyTelco.setValues(valuesTelco);
            dataKeys.add(dataKeyTelco);
        }else if(user.getCategory().getId().equals(StatusApiEnum.OMCI_CATEGORY)){
            //OMCI
            DataValues omciValues_BLOQUER = new DataValues();
            omciValues_BLOQUER.setKey("BLOQUER");
            omciValues_BLOQUER.setValue(totalNumberBoquerSimswap.get("total_elements")!=null?totalNumberBoquerSimswap.get("total_elements").toString():"0");
            valuesOmci.add(omciValues_BLOQUER);
            DataValues omciValues_DEBLOQUER = new DataValues();
            omciValues_DEBLOQUER.setKey("DEBLOQUER");
            omciValues_DEBLOQUER.setValue(totalNumberDebloqueSimswap.get("total_elements")!=null?totalNumberDebloqueSimswap.get("total_elements").toString():"0");
            valuesOmci.add(omciValues_DEBLOQUER);
            DataValues omciMiseEnMachine = new DataValues();
            omciMiseEnMachine.setKey("MISE_EN_MACHINE");
            omciMiseEnMachine.setValue(totalMiseEnMachineSimswap.get("total_elements")!=null?totalMiseEnMachineSimswap.get("total_elements").toString():"0");
            valuesOmci.add(omciMiseEnMachine);
            DataValues omciTotal = new DataValues();
            omciTotal.setKey("TOTAL");
            omciTotal.setValue(totalOmciSimswap>0?String.valueOf(totalOmciSimswap):"0");
            valuesOmci.add(omciTotal);
            DataKey dataKeyOmci = new DataKey();
            dataKeyOmci.setKey("OMCI");
            dataKeyOmci.setValues(valuesOmci);
            dataKeys.add(dataKeyOmci);
        }
        return dataKeys;
    }

    private List <DataKey> getStringObjectMap(TypeNumero typeNumero,String startDB,String endDB,int totalOmci,User user,int totalTelco){
        List <DataValues> valuesOmci = new ArrayList <>();
        List <DataValues> valuesTelco = new ArrayList <>();
        List <DataKey> dataKeys = new ArrayList <>();

        //OMCI REQUEST
        String queryTotalBloque = getString(typeNumero,StatusApiEnum.OMCI_CATEGORY,startDB,endDB,StatusApiEnum.BLOQUER,Boolean.FALSE);
        Map <String, Object> totalNumberBoquer;
        totalNumberBoquer = mysqlTemplate.queryForMap(queryTotalBloque);
        if(totalNumberBoquer!=null){
            totalOmci += Integer.valueOf(totalNumberBoquer.get("total_elements").toString());
        }
        String queryTotalDebloque = getString(typeNumero,StatusApiEnum.OMCI_CATEGORY,startDB,endDB,StatusApiEnum.DEBLOQUER,Boolean.FALSE);
        Map <String, Object> totalNumberDebloque;
        totalNumberDebloque = mysqlTemplate.queryForMap(queryTotalDebloque);
        if(totalNumberDebloque!=null){
            totalOmci += Integer.valueOf(totalNumberDebloque.get("total_elements").toString());
        }
        String queryTotalMiseEnMachine = getString(typeNumero,StatusApiEnum.OMCI_CATEGORY,startDB,endDB,StatusApiEnum.DEBLOQUER,Boolean.TRUE);
        Map <String, Object> totalMiseEnMachine;
        totalMiseEnMachine = mysqlTemplate.queryForMap(queryTotalMiseEnMachine);
        if(totalNumberDebloque!=null){
            totalOmci += Integer.valueOf(totalMiseEnMachine.get("total_elements").toString());
        }

        // TELCO REQUEST
        String queryTotalBloqueTelco = getString(typeNumero,StatusApiEnum.TELCO_CATEGORY,startDB,endDB,StatusApiEnum.BLOQUER,Boolean.FALSE);
        Map <String, Object> totalNumberBoquerTelco;
        totalNumberBoquerTelco = mysqlTemplate.queryForMap(queryTotalBloqueTelco);
        if(totalNumberBoquerTelco!=null){
            totalTelco += Integer.valueOf(totalNumberBoquerTelco.get("total_elements").toString());
        }
        String queryTotalDebloqueTelco = getString(typeNumero,StatusApiEnum.TELCO_CATEGORY,startDB,endDB,StatusApiEnum.DEBLOQUER,Boolean.FALSE);
        Map <String, Object> totalNumberDebloqueTelco;
        totalNumberDebloqueTelco = mysqlTemplate.queryForMap(queryTotalDebloqueTelco);
        if(totalNumberDebloqueTelco!=null){
            totalTelco += Integer.valueOf(totalNumberDebloqueTelco.get("total_elements").toString());
        }
        String queryTotalMiseEnMachineTelco = getString(typeNumero,StatusApiEnum.TELCO_CATEGORY,startDB,endDB,StatusApiEnum.DEBLOQUER,Boolean.TRUE);
        Map <String, Object> totalMiseEnMachineTelco;
        totalMiseEnMachineTelco = mysqlTemplate.queryForMap(queryTotalMiseEnMachineTelco);
        if(totalMiseEnMachineTelco!=null){
            totalTelco += Integer.valueOf(totalMiseEnMachineTelco.get("total_elements").toString());
        }

        if(user.getProfil().getLibelle().equalsIgnoreCase("MANAGER FRAUDE-SÉCURITÉ")||user.getProfil().getLibelle().equalsIgnoreCase("ADMIN MÉTIER")){
            //OMCI
            DataValues omciValues_BLOQUER = new DataValues();
            omciValues_BLOQUER.setKey("BLOQUER");
            omciValues_BLOQUER.setValue(totalNumberBoquer.get("total_elements")!=null?totalNumberBoquer.get("total_elements").toString():"0");
            valuesOmci.add(omciValues_BLOQUER);
            DataValues omciValues_DEBLOQUER = new DataValues();
            omciValues_DEBLOQUER.setKey("DEBLOQUER");
            omciValues_DEBLOQUER.setValue(totalNumberDebloque.get("total_elements")!=null?totalNumberDebloque.get("total_elements").toString():"0");
            valuesOmci.add(omciValues_DEBLOQUER);
            DataValues omciMiseEnMachine = new DataValues();
            omciMiseEnMachine.setKey("MISE_EN_MACHINE");
            omciMiseEnMachine.setValue(totalMiseEnMachine.get("total_elements")!=null?totalMiseEnMachine.get("total_elements").toString():"0");
            valuesOmci.add(omciMiseEnMachine);
            DataValues omciTotal = new DataValues();
            omciTotal.setKey("TOTAL");
            omciTotal.setValue(totalOmci>0?String.valueOf(totalOmci):"0");
            valuesOmci.add(omciTotal);
            DataKey dataKeyOmci = new DataKey();
            dataKeyOmci.setKey("OMCI");
            dataKeyOmci.setValues(valuesOmci);
            dataKeys.add(dataKeyOmci);

            //TELCO
            DataValues telcoValues_DEBLOQUER = new DataValues();
            telcoValues_DEBLOQUER.setKey("DEBLOQUER");
            telcoValues_DEBLOQUER.setValue(totalNumberDebloqueTelco.get("total_elements")!=null?totalNumberDebloqueTelco.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_DEBLOQUER);
            DataValues telcoValues_BLOQUER = new DataValues();
            telcoValues_BLOQUER.setKey("BLOQUER");
            telcoValues_BLOQUER.setValue(totalNumberBoquerTelco.get("total_elements")!=null?totalNumberBoquerTelco.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_BLOQUER);
            DataValues telcoValues_MISE_EN_MACHINE = new DataValues();
            telcoValues_MISE_EN_MACHINE.setKey("MISE_EN_MACHINE");
            telcoValues_MISE_EN_MACHINE.setValue(totalMiseEnMachineTelco.get("total_elements")!=null?totalMiseEnMachineTelco.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_MISE_EN_MACHINE);
            DataValues telcoTotal = new DataValues();
            telcoTotal.setKey("TOTAL");
            telcoTotal.setValue(totalTelco>0?String.valueOf(totalTelco):"0");
            valuesTelco.add(telcoTotal);
            DataKey dataKeyTelco = new DataKey();
            dataKeyTelco.setKey("TELCO");
            dataKeyTelco.setValues(valuesTelco);
            dataKeys.add(dataKeyTelco);

        }else if(user.getCategory().getId().equals(StatusApiEnum.OMCI_CATEGORY)){
            DataValues omciValues_BLOQUER = new DataValues();
            omciValues_BLOQUER.setKey("BLOQUER");
            omciValues_BLOQUER.setValue(totalNumberBoquer.get("total_elements")!=null?totalNumberBoquer.get("total_elements").toString():"0");
            valuesOmci.add(omciValues_BLOQUER);
            DataValues omciValues_DEBLOQUER = new DataValues();
            omciValues_DEBLOQUER.setKey("DEBLOQUER");
            omciValues_DEBLOQUER.setValue(totalNumberDebloque.get("total_elements")!=null?totalNumberDebloque.get("total_elements").toString():"0");
            valuesOmci.add(omciValues_DEBLOQUER);
            DataValues omciMiseEnMachine = new DataValues();
            omciMiseEnMachine.setKey("MISE_EN_MACHINE");
            omciMiseEnMachine.setValue(totalMiseEnMachine.get("total_elements")!=null?totalMiseEnMachine.get("total_elements").toString():"0");
            valuesOmci.add(omciMiseEnMachine);
            DataValues omciTotal = new DataValues();
            omciTotal.setKey("TOTAL");
            omciTotal.setValue(totalOmci>0?String.valueOf(totalOmci):"0");
            valuesOmci.add(omciTotal);
            DataKey dataKeyOmci = new DataKey();
            dataKeyOmci.setKey("OMCI");
            dataKeyOmci.setValues(valuesOmci);
            dataKeys.add(dataKeyOmci);

        }else if(user.getCategory().getId().equals(StatusApiEnum.TELCO_CATEGORY)){
            //TELCO
            DataValues telcoValues_DEBLOQUER = new DataValues();
            telcoValues_DEBLOQUER.setKey("DEBLOQUER");
            telcoValues_DEBLOQUER.setValue(totalNumberDebloqueTelco.get("total_elements")!=null?totalNumberDebloqueTelco.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_DEBLOQUER);
            DataValues telcoValues_BLOQUER = new DataValues();
            telcoValues_BLOQUER.setKey("BLOQUER");
            telcoValues_BLOQUER.setValue(totalNumberBoquerTelco.get("total_elements")!=null?totalNumberBoquerTelco.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_BLOQUER);
            DataValues telcoValues_MISE_EN_MACHINE = new DataValues();
            telcoValues_MISE_EN_MACHINE.setKey("MISE_EN_MACHINE");
            telcoValues_MISE_EN_MACHINE.setValue(totalMiseEnMachineTelco.get("total_elements")!=null?totalMiseEnMachineTelco.get("total_elements").toString():"0");
            valuesTelco.add(telcoValues_MISE_EN_MACHINE);
            DataValues telcoTotal = new DataValues();
            telcoTotal.setKey("TOTAL");
            telcoTotal.setValue(totalTelco>0?String.valueOf(totalTelco):"0");
            valuesTelco.add(telcoTotal);
            DataKey dataKeyTelco = new DataKey();
            dataKeyTelco.setKey("TELCO");
            dataKeyTelco.setValues(valuesTelco);
            dataKeys.add(dataKeyTelco);
        }

        return dataKeys;
    }

    private static String getString(TypeNumero typeNumero,Integer idCategory,String startDB,String endDB,Integer statusId,Boolean isMachine){
        if(isMachine==Boolean.FALSE){
            String queryData = "SELECT COUNT(*) as total_elements FROM numero_stories WHERE id_statut = "+statusId+" AND is_deleted = FALSE AND id_category = "
                    +idCategory+" AND created_at BETWEEN '"+startDB+"' AND '"+endDB+"'";
            return queryData;
        }
        String queryData = "SELECT COUNT(*) as total_elements FROM numero_stories WHERE is_machine = TRUE AND is_deleted = FALSE AND id_category = "
                +idCategory+" AND created_at BETWEEN '"+startDB+"' AND '"+endDB+"'";
        return queryData;
    }

    private static String getStringSimswap(TypeNumero typeNumero,Integer idCategory,String startDB,String endDB,Integer statusId,Boolean isMachine){
        if(isMachine==Boolean.FALSE){
            String queryData = "SELECT COUNT(*) as total_elements FROM action_utilisateur WHERE id_status = "+statusId+" AND type_numero_id = "+typeNumero.getId()+" and is_deleted = FALSE AND id_category = "
                    +idCategory+" AND updated_at BETWEEN '"+startDB+"' AND '"+endDB+"'";
            return queryData;
        }
        String queryData = "SELECT COUNT(*) as total_elements FROM action_utilisateur WHERE  type_numero_id = "+typeNumero.getId()+" AND is_machine = TRUE AND  is_deleted = FALSE AND id_category = "
                +idCategory+" AND updated_at BETWEEN '"+startDB+"' AND '"+endDB+"'";
        return queryData;
    }

    public Response <List <Map <String, String>>> getDashboardMiseEnMachine(Request <ActionUtilisateurDto> request,
                                                                            Locale locale,String startDB,String endDB,Integer typeNumero) throws Exception{
        log.info("----begin getDashboard-----");
        Response <List <Map <String, String>>> response = new Response <>();
        List <Map <String, String>> listReponseMap = new ArrayList <Map <String, String>>();

        ActionUtilisateurDto req = request.getData();
        // Verification de l'existence du USER connecté
        User user = userRepository.findOne(request.getUser(),false);
        if(user==null){
            response.setStatus(functionalError.DATA_EMPTY("ID USER",locale));
            response.setHasError(true);
            return response;
        }
        // Verification de l'existence de l'id de la categorie connecté
        String codeCategory = user.getCategory().getCode();
        if(codeCategory==null){
            response.setStatus(functionalError.DATA_EMPTY("CODE CATEGORY",locale));
            response.setHasError(true);
            return response;
        }
        Integer idCategory = user.getCategory().getId();
        ActionUtilisateurDto dto = request.getData();

        if(!codeCategory.equals(CategoryEnum.ADMIN_CODE)){
            Map <String, String> reponseMap = new HashMap <String, String>();
            reponseMap.put("category",codeCategory);
            if(Utilities.notBlank(dto.getCreatedAt())){
                Map <String, Object> mapObject = new HashMap <>();
                List <Map <String, Object>> datasTo = new ArrayList <>();
                String queryData = "SELECT COUNT(*) as nbre_id, is_machine FROM action_utilisateur\r\n"
                        +"WHERE type_numero_id = "+typeNumero
                        +"  and is_deleted = FALSE and action_utilisateur.id_category = "+idCategory
                        +" and action_utilisateur.is_machine = TRUE and created_at BETWEEN  '"+startDB+"' and '"
                        +endDB+"'\r\n"+"GROUP BY is_machine";
                datasTo = mysqlTemplate.queryForList(queryData);
                if(Utilities.isNotEmpty(datasTo)){
                    mapObject = datasTo.get(0);
                    String dataCode = null;
                    if(mapObject!=null){
                        reponseMap.put("createdAt",dateTimeFormat.format(Utilities.getCurrentDate()));
                        dataCode = "MISE_EN_MACHINE";
                        reponseMap.put(dataCode,mapObject.get("nbre_id").toString());
                        listReponseMap.add(reponseMap);
                    }
                }
            }

        }else{
            Map <String, String> reponseMap = new HashMap <String, String>();
            reponseMap.put("category",CategoryEnum.OMCI_CODE);
            idCategory = 1;
            if(Utilities.notBlank(dto.getCreatedAt())){
                Map <String, Object> mapObject = new HashMap <>();
                String queryData = "SELECT COUNT(*) as nbre_id, id_status FROM action_utilisateur\r\n"
                        +"WHERE type_numero_id = "+typeNumero
                        +" and is_deleted = FALSE and action_utilisateur.id_category = "+idCategory
                        +" and action_utilisateur.is_machine = TRUE and  created_at BETWEEN  '"+startDB+"' and '"
                        +endDB+"'\r\n"+"GROUP BY is_machine";
                List <Map <String, Object>> datasTo = new ArrayList <>();
                datasTo = mysqlTemplate.queryForList(queryData);
                String dataCode = null;
                if(Utilities.isNotEmpty(datasTo)){
                    mapObject = datasTo.get(0);
                    if(mapObject!=null){
                        reponseMap.put("createdAt",dateTimeFormat.format(Utilities.getCurrentDate()));
                        dataCode = "MISE_EN_MACHINE";
                        reponseMap.put(dataCode,
                                mapObject.get("nbre_id")!=null?mapObject.get("nbre_id").toString():"");
                        listReponseMap.add(reponseMap);
                    }
                }
                reponseMap = new HashMap <String, String>();
                reponseMap.put("category",CategoryEnum.TELCO_CODE);
                idCategory = 2;
                if(Utilities.notBlank(dto.getCreatedAt())){
                    mapObject = new HashMap <>();

                    String queryDatas = "SELECT COUNT(*) as nbre_id, id_status FROM action_utilisateur\r\n"
                            +"WHERE type_numero_id = "+typeNumero
                            +" and is_deleted = FALSE and action_utilisateur.id_category = "+idCategory
                            +" and action_utilisateur.is_machine = TRUE and  created_at BETWEEN  '"+startDB
                            +"' and '"+endDB+"'\r\n"+"GROUP BY is_machine";
                    List <Map <String, Object>> datasTos = new ArrayList <>();
                    datasTos = mysqlTemplate.queryForList(queryDatas);
                    if(Utilities.isNotEmpty(datasTos)){
                        Map <String, Object> mapObjects = datasTos.get(0);
                        dataCode = null;
                        if(mapObjects!=null){
                            reponseMap.put("createdAt",dateTimeFormat.format(Utilities.getCurrentDate()));
                            dataCode = "MISE_EN_MACHINE";
                            reponseMap.put(dataCode,
                                    mapObjects.get("nbre_id")!=null?mapObjects.get("nbre_id").toString():"");

                        }
                        listReponseMap.add(reponseMap);
                    }
                }
            }
        }
        log.info("----end number from connexion file-----");
        response.setActionEffectue("Vue Dashboard");
        response.setHasError(false);
        response.setItem(listReponseMap);
        response.setStatus(functionalError.SUCCESS("",locale));

        return response;
    }

    public Response <List <Map <String, String>>> getDashboardMiseEnMachineStories(Request <ActionUtilisateurDto> request,
                                                                                   Locale locale) throws Exception{
        log.info("----begin getDashboard-----");
        Response <List <Map <String, String>>> response = new Response <>();
        List <Map <String, String>> listReponseMap = new ArrayList <Map <String, String>>();
        ActionUtilisateurDto req = request.getData();
        // Verification de l'existence du USER connecté
        User user = userRepository.findOne(request.getUser(),false);
        if(user==null){
            response.setStatus(functionalError.DATA_EMPTY("ID USER",locale));
            response.setHasError(true);
            return response;
        }
        String codeCategory = user.getCategory().getCode();
        if(codeCategory==null){
            response.setStatus(functionalError.DATA_EMPTY("CODE CATEGORY",locale));
            response.setHasError(true);
            return response;
        }
        Integer idCategory = user.getCategory().getId();
        ActionUtilisateurDto dto = request.getData();
        Date starDate = null;
        Date endDate = null;
        String startDB = null;
        String endDB = null;
        if(!codeCategory.equals(CategoryEnum.ADMIN_CODE)){
            Map <String, String> reponseMap = new HashMap <String, String>();
//			reponseMap.put("category", codeCategory);
            if(Utilities.notBlank(dto.getCreatedAt())){
                Date start = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dto.getCreatedAtParam().getStart());
                Date end = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dto.getCreatedAtParam().getEnd());
                startDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start);
                starDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDB);
                System.out.println("starDate ==> "+startDB);
                endDB = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(end);
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").parse(endDB);
                Map <String, Object> mapObject = new HashMap <>();
                String queryData = "SELECT COUNT(*) as nbre_id, is_machine FROM numero_stories\r\n"
                        +"WHERE id_type_numero = "+request.getData().getTypeNumeroId()
                        +" and is_deleted = FALSE and numero_stories.is_machine = TRUE and created_at BETWEEN  '"
                        +startDB+"' and '"+endDB+"'\r\n"+"GROUP BY is_machine";
                List <Map <String, Object>> datasTos = new ArrayList <>();
                datasTos = mysqlTemplate.queryForList(queryData);
                if(Utilities.isNotEmpty(datasTos)){
                    String dataCode = null;
                    mapObject = datasTos.get(0);
                    if(mapObject!=null){
                        reponseMap.put("createdAt",dateTimeFormat.format(Utilities.getCurrentDate()));
                        dataCode = "MISE_EN_MACHINE";
                        reponseMap.put(dataCode,mapObject.get("nbre_id").toString());
                        reponseMap.put("category",user.getCategory().getCode());
                    }
                }
            }
            listReponseMap.add(reponseMap);
        }else{
            Map <String, String> reponseMap = new HashMap <String, String>();
            idCategory = 1;
            if(Utilities.notBlank(dto.getCreatedAt())){
                Date start = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dto.getCreatedAtParam().getStart());
                Date end = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dto.getCreatedAtParam().getEnd());
                startDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start);
                starDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDB);
                System.out.println("starDate ==> "+startDB);
                endDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end);
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDB);
                Map <String, Object> mapObject = new HashMap <>();
                String queryData = "SELECT COUNT(*) as nbre_id, is_machine FROM numero_stories\r\n"
                        +"WHERE id_type_numero = "+request.getData().getTypeNumeroId()
                        +" and is_deleted = FALSE  and numero_stories.is_machine = TRUE and  created_at BETWEEN  '"
                        +startDB+"' and '"+endDB+"'\r\n"+"GROUP BY is_machine";
                List <Map <String, Object>> datasTos = new ArrayList <>();
                Category categoryOp = categoryRepository.findOne(idCategory,Boolean.FALSE);
                datasTos = mysqlTemplate.queryForList(queryData);
                if(Utilities.isNotEmpty(datasTos)){
                    mapObject = datasTos.get(0);
                    String dataCode = null;
                    if(mapObject!=null){
                        reponseMap.put("createdAt",dateTimeFormat.format(Utilities.getCurrentDate()));
                        dataCode = "MISE_EN_MACHINE";
                        reponseMap.put(dataCode,mapObject.get("nbre_id").toString());
                        reponseMap.put("category",categoryOp.getCode());
                    }
                    listReponseMap.add(reponseMap);
                }
            }
        }
        log.info("----end number from connexion file-----");
        response.setActionEffectue("Vue Dashboard");
        response.setHasError(false);
        response.setItem(listReponseMap);
        response.setStatus(functionalError.SUCCESS("",locale));

        return response;
    }

    public Response <List <Map <String, String>>> getDashboardLite(Request <ActionUtilisateurDto> request,Locale locale)
            throws Exception{
        log.info("----begin getDashboardLite-----");
        Response <List <Map <String, String>>> response = new Response <>();
        List <Map <String, String>> listReponseMap = new ArrayList <Map <String, String>>();
        Map <String, String> reponseMap = new HashMap <String, String>();

        User user = userRepository.findOne(request.getUser(),false);
        if(user==null){
            response.setStatus(functionalError.DATA_EMPTY("ID USER",locale));
            response.setHasError(true);
            return response;
        }
        // Verification de l'existence de l'id de la categorie connecté
        String codeCategory = user.getCategory().getCode();
        if(codeCategory==null){
            response.setStatus(functionalError.DATA_EMPTY("CODE CATEGORY",locale));
            response.setHasError(true);
            return response;
        }
        Integer idCategory = user.getCategory().getId();
        ActionUtilisateurDto dto = request.getData();
        if(dto.getTypeNumeroId()==null){
            response.setStatus(functionalError.DATA_EMPTY("ID Type Numero",locale));
            response.setHasError(true);
            return response;
        }
        TypeNumero typeNumero = typeNumeroRepository.findOne(dto.getTypeNumeroId(),Boolean.FALSE);
        if(typeNumero==null){
            response.setStatus(functionalError.DATA_EMPTY("ID Type Numero "+dto.getTypeNumeroId(),locale));
            response.setHasError(true);
            return response;
        }

        Date start = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dto.getCreatedAtParam().getStart());
        String startDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start);

        Date end = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dto.getCreatedAtParam().getEnd());
        String endDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end);

        String queryDataDataByDay = "SELECT COUNT(*) AS nbre_id FROM ( SELECT id FROM numero_stories WHERE id_type_numero = "
                +typeNumero.getId()+" AND is_deleted = FALSE AND id_category = "+idCategory
                +" AND created_at BETWEEN '"+startDB+"' and '"+endDB+"' GROUP BY id ) AS subquery";
        System.out.println(queryDataDataByDay);
        List <Map <String, Object>> count = new ArrayList <Map <String, Object>>();
        count = mysqlTemplate.queryForList(queryDataDataByDay);
        System.out.println("--------------------------------------"+count);
        if(Utilities.isNotEmpty(count)){
            Map <String, Object> ones = count.get(0);
            System.out.println("----------------------------------"+count);
            reponseMap.put("total",ones.get("nbre_id").toString());
        }
        if(!codeCategory.equals(CategoryEnum.ADMIN_CODE)){
            reponseMap.put("category",codeCategory);
            if(Utilities.notBlank(dto.getCreatedAt())){
                List <Map <String, Object>> map1Liste = new ArrayList <Map <String, Object>>();
                String queryData = "SELECT COUNT(*) as nbre_id, id_statut FROM numero_stories\r\n"
                        +"WHERE id_type_numero = "+typeNumero.getId()+" and is_deleted = FALSE and id_category = "
                        +idCategory+" and created_at BETWEEN  '"+startDB+"' and '"+endDB+"'\r\n"
                        +"GROUP BY id_statut";
                System.out.println(queryData);
                map1Liste = mysqlTemplate.queryForList(queryData);
                for(Map <String, Object> map: map1Liste){
                    String dataCode = null;
                    if(map!=null){
                        dataCode = "MISE_EN_MACHINE";
                        Response <List <Map <String, String>>> responsMiseEnMAchine = getDashboardMiseEnMachineStories(
                                request,locale);
                        if(Utilities.isNotEmpty(responsMiseEnMAchine.getItem())){
                            Map <String, String> works = responsMiseEnMAchine.getItem().get(0);
                            reponseMap.put(dataCode,
                                    works.get("MISE_EN_MACHINE")!=null?works.get("MISE_EN_MACHINE").toString()
                                            :null);
//							if (works.get("category").toString().equalsIgnoreCase("OMCI")) {
//								reponseMap.put(dataCode, works.get("MISE_EN_MACHINE").toString());
//							}
                        }
                        System.out.println(map);
                        Status data = statusRepository.findOne(
                                map.get("id_statut")!=null?Integer.parseInt(map.get("id_statut").toString()):null,
                                Boolean.FALSE);
                        reponseMap.put("createdAt",dateTimeFormat.format(Utilities.getCurrentDate()));
                        if(data!=null){
                            if(data.getId()==StatusApiEnum.BLOQUER){
                                dataCode = "BLOQUER";
                                reponseMap.put(dataCode,
                                        map.get("nbre_id")!=null?map.get("nbre_id").toString():null);
                            }
                            if(data.getId()==StatusApiEnum.DEBLOQUER){
                                dataCode = "DEBLOQUER";
                                reponseMap.put(dataCode,
                                        map.get("nbre_id")!=null?map.get("nbre_id").toString():null);
                            }
                            if(data.getId()==StatusApiEnum.MISE_EN_MACHINE){

                            }
                            if(data.getId()==StatusApiEnum.SUPPRIMER){
                                dataCode = "SUPPRIMER";
                                reponseMap.put(dataCode,
                                        map.get("nbre_id")!=null?map.get("nbre_id").toString():null);
                            }
                        }

                    }
                }
                listReponseMap.add(reponseMap);
            }
        }else{
            reponseMap.put("category",CategoryEnum.OMCI_CODE);
            idCategory = 1;
            if(Utilities.notBlank(dto.getCreatedAt())){
                List <Map <String, Object>> map1Liste = new ArrayList <Map <String, Object>>();
                String queryData = "SELECT COUNT(*) as nbre_id, id_statut FROM numero_stories\r\n"
                        +"WHERE id_type_numero = "+typeNumero.getId()+" and is_deleted = FALSE and id_category = "
                        +idCategory+" and created_at BETWEEN  '"+startDB+"' and '"+endDB+"'\r\n"
                        +"GROUP BY id_statut";
                map1Liste = mysqlTemplate.queryForList(queryData);
                for(Map <String, Object> map: map1Liste){
                    String dataCode = null;
                    if(map!=null){
                        dataCode = "MISE_EN_MACHINE";
                        Response <List <Map <String, String>>> responsMiseEnMAchine = getDashboardMiseEnMachineStories(
                                request,locale);
                        if(Utilities.isNotEmpty(responsMiseEnMAchine.getItem())){
                            Map <String, String> works = responsMiseEnMAchine.getItem().get(0);
                            if(works.get("category").toString().equalsIgnoreCase("OMCI")){
                                reponseMap.put(dataCode,
                                        works.get("MISE_EN_MACHINE")!=null?works.get("MISE_EN_MACHINE").toString()
                                                :null);
                            }
                        }
                        System.out.println(map);
                        Status data = statusRepository.findOne(
                                map.get("id_statut")!=null?Integer.parseInt(map.get("id_statut").toString()):null,
                                Boolean.FALSE);
                        reponseMap.put("createdAt",dateTimeFormat.format(Utilities.getCurrentDate()));
                        if(data!=null){
                            if(data.getId()==StatusApiEnum.BLOQUER){
                                dataCode = "BLOQUER";
                                reponseMap.put(dataCode,
                                        map.get("nbre_id")!=null?map.get("nbre_id").toString():null);
                            }
                            if(data.getId()==StatusApiEnum.DEBLOQUER){
                                dataCode = "DEBLOQUER";
                                reponseMap.put(dataCode,
                                        map.get("nbre_id")!=null?map.get("nbre_id").toString():null);
                            }

                            if(data.getId()==StatusApiEnum.SUPPRIMER){
                                dataCode = "SUPPRIMER";
                                reponseMap.put(dataCode,
                                        map.get("nbre_id")!=null?map.get("nbre_id").toString():null);
                            }
                        }

                    }
                }
                listReponseMap.add(reponseMap);
                reponseMap = new HashMap <String, String>();
                reponseMap.put("category",CategoryEnum.TELCO_CODE);
                idCategory = 2;
                if(Utilities.notBlank(dto.getCreatedAt())){
                    map1Liste = new ArrayList <Map <String, Object>>();
                    queryData = "SELECT COUNT(*) as nbre_id, id_statut FROM numero_stories\r\n"
                            +"WHERE id_type_numero = "+typeNumero.getId()
                            +" and is_deleted = FALSE and id_category = "+idCategory+" and created_at BETWEEN  '"
                            +startDB+"' and '"+endDB+"'\r\n"+"GROUP BY id_statut";
                    map1Liste = mysqlTemplate.queryForList(queryData);
                    for(Map <String, Object> map: map1Liste){
                        String dataCode = null;
                        if(map!=null){
                            System.out.println(map);
                            Response <List <Map <String, String>>> responsMiseEnMAchine = getDashboardMiseEnMachineStories(
                                    request,locale);
                            dataCode = "MISE_EN_MACHINE";
                            if(Utilities.isNotEmpty(responsMiseEnMAchine.getItem())){
                                Map <String, String> works = responsMiseEnMAchine.getItem().get(0);
                                if(works.get("category").toString().equalsIgnoreCase("TELCO")){
                                    reponseMap.put(dataCode,works.get("MISE_EN_MACHINE").toString());
                                }
                            }
                            Status data = statusRepository.findOne(Integer.parseInt(map.get("id_statut").toString()),
                                    Boolean.FALSE);
                            reponseMap.put("createdAt",dateTimeFormat.format(Utilities.getCurrentDate()));
                            if(data!=null){
                                if(data.getId()==StatusApiEnum.BLOQUER){
                                    dataCode = "BLOQUER";
                                    reponseMap.put(dataCode,map.get("nbre_id").toString());
                                }
                                if(data.getId()==StatusApiEnum.DEBLOQUER){
                                    dataCode = "DEBLOQUER";
                                    reponseMap.put(dataCode,map.get("nbre_id").toString());
                                }
                                if(data.getId()==StatusApiEnum.MISE_EN_MACHINE){

                                }
                                if(data.getId()==StatusApiEnum.SUPPRIMER){
                                    dataCode = "SUPPRIMER";
                                    reponseMap.put(dataCode,map.get("nbre_id").toString());
                                }
                            }
                        }
                    }
                    listReponseMap.add(reponseMap);
                }
            }
        }
        log.info("----end getDashboardLite-----");
        response.setActionEffectue("Vue Dashboard");
        response.setHasError(false);
        response.setItem(listReponseMap);
        response.setStatus(functionalError.SUCCESS("",locale));
        return response;
    }

    public Response <Map <String, Object>> getNumeroByCategory(Request <ActionUtilisateurDto> request,Locale locale)
            throws Exception{
        log.info("----begin number from connexion File-----");
        Response <Map <String, Object>> response = new Response <Map <String, Object>>();
        Map <String, Object> mapForAdmin = new HashMap <>();
        Map <String, Object> forTelco = new HashMap <>();
        Map <String, Object> forOm = new HashMap <>();
        User oneUser = userRepository.findOne(request.getUser(),Boolean.FALSE);
        String queryLite = "";

        String telcoRequest = "";
        String omRequest = "";

        Map <String, Object> reponseMap = new HashMap <String, Object>();
        ActionUtilisateurDto dto = request.getData();

        Date starDate = null;

        Date endDate = null;

        String startDB = null;

        String endDB = null;

        if(Utilities.notBlank(dto.getCreatedAt())){
            Date start = new SimpleDateFormat("dd/MM/yyyy").parse(dto.getCreatedAtParam().getStart());
            Date end = new SimpleDateFormat("dd/MM/yyyy").parse(dto.getCreatedAtParam().getEnd());

            startDB = new SimpleDateFormat("yyyy-MM-dd").format(start);
            endDB = new SimpleDateFormat("yyyy-MM-dd").format(end);
            if(oneUser!=null){
                if(oneUser.getIsSuperAdmin()==Boolean.TRUE){
                    telcoRequest = "SELECT COUNT(*) as nbre_id "
                            +"FROM db_simswap.action_utilisateur WHERE created_at BETWEEN '"+startDB+"' AND '"
                            +endDB+"' AND id_category = 2;";
                    omRequest = "SELECT COUNT(*) as nbre_id "
                            +"FROM db_simswap.action_utilisateur WHERE created_at BETWEEN  '"+startDB+"' and '"
                            +endDB+"' AND id_category = 1;";
                    forTelco = mysqlTemplate.queryForMap(telcoRequest);
                    forOm = mysqlTemplate.queryForMap(omRequest);
                    mapForAdmin.put("TELCO",forTelco.get("nbre_id"));
                    mapForAdmin.put("OMCI",forOm.get("nbre_id"));
                    mapForAdmin.put("createdAt",dateTimeFormat.format(Utilities.getCurrentDate()));
                    response.setItem(mapForAdmin);
                }else{
                    if(oneUser.getCategory()!=null){
                        queryLite = "SELECT COUNT(*) as nbre_id "
                                +"FROM db_simswap.action_utilisateur WHERE created_at BETWEEN  '"+startDB+"' and '"
                                +endDB+"' AND id_category = '"+oneUser.getCategory().getId()+"'"+"";
                        System.out.println(oneUser.getCategory().getId());
                        Map <String, Object> map1Liste = new HashMap <>();
//                      String queryData = "SELECT COUNT(*) as nbre_id, id_statut FROM numero_stories\r\n" + "WHERE is_deleted = FALSE and created_at BETWEEN  '" + startDB + "' and '" + endDB + "'\r\n"+ "GROUP BY id_statut";
                        map1Liste = mysqlTemplate.queryForMap(queryLite);
                        map1Liste.put("createdAt",dateTimeFormat.format(Utilities.getCurrentDate()));
                        System.out.println(map1Liste);
                        response.setItem(map1Liste);
                    }
                }
            }else{
                response.setHasError(Boolean.TRUE);
                response.setStatus(functionalError.DATA_NOT_EXIST(request.getUser().toString(),locale));
                return response;
            }

        }
        System.out.println(reponseMap);
        log.info("----end number from connexion file-----");
        response.setActionEffectue("Vue Dashboard Categorie");
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("",locale));

        return response;

    }

    public Response <ActionUtilisateurDto> findByMsisdn(Request <ActionUtilisateurDto> request,String requestId,
                                                        Locale locale) throws Exception{
        log.info("----begin number from connexion File-----");
        Response <ActionUtilisateurDto> response = new Response <>();
        List <ActionUtilisateurDto> itemDto = new ArrayList <>();
        ObjectReader mapper = new ObjectMapper().readerFor(Map.class);
        ;
        ActionUtilisateurDto dto = request.getData();
        Map <String, Object> fieldsToVerify = new HashMap <String, Object>();
        fieldsToVerify.put("numero ",dto.getNumero());
        if(!Validate.RequiredValue(fieldsToVerify).isGood()){
            response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
            response.setHasError(true);
            return response;
        }
        String appId = ParamsUtils.simSwapAppId;
        String msisdn = dto.getNumero();
        String baseUrl = ParamsUtils.simSwapAddress+"/find-by-misdn";
        HttpClient httpClient = HttpClients.createDefault();
        try{
            URI uri = new URIBuilder(baseUrl).addParameter("appId",appId).addParameter("requestId",requestId)
                    .addParameter("msisdn",msisdn).build();
            HttpGet httpGet = new HttpGet(uri);
            HttpResponse responseHttp = httpClient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseHttp.getEntity().getContent()));
            StringBuilder responseBody = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                responseBody.append(line);
            }
            User user = userRepository.findOne(request.getUser(),false);
            String category = user.getCategory().getCode();
            List <Map <String, Object>> items = mapper.readValue(String.valueOf(responseBody));
            // Filtrage en fonction du numero et du profile
            List <Map <String, Object>> listOfApiFilter = null;
            if(category.contains("OMCI")){
                listOfApiFilter = items.stream().filter(
                                map->map.get("msisdn")!=null&&map.get("msisdn").toString().startsWith(dto.getNumero()))
                        .filter(map->map.get("offerName")!=null&&map.get("offerName").equals("Orange Money PDV"))
                        .collect(Collectors.toList());
            }else if(category.contains("TELCO")){
                listOfApiFilter = items.stream().filter(
                                map->map.get("msisdn")!=null&&map.get("msisdn").toString().startsWith(dto.getNumero()))
                        .filter(map->map.get("offerName")!=null&&map.get("offerName").equals("ORANGE ENTREPRISE"))
                        .collect(Collectors.toList());
            }else{
                listOfApiFilter = items.stream().filter(
                                map->map.get("msisdn")!=null&&map.get("msisdn").toString().startsWith(dto.getNumero()))
                        .collect(Collectors.toList());
            }
            if(listOfApiFilter!=null){
                for(Map <String, Object> el: listOfApiFilter){
                    ActionUtilisateurDto actionUtilisateurDto = new ActionUtilisateurDto();
                    System.out.println(el);
                    actionUtilisateurDto.setNumero(el.get("msisdn")!=null?el.get("msisdn").toString():null);
                    actionUtilisateurDto.setIsExcelNumber(false);
                    actionUtilisateurDto
                            .setProfilLibelle(el.get("offerName")!=null?el.get("offerName").toString():null);
                    actionUtilisateurDto.setCreatedAt(
                            el.get("activationDate")!=null?el.get("activationDate").toString():null);
                    actionUtilisateurDto.setIsDeleted(false);
                    actionUtilisateurDto.setMotif(dto.getMotif());
                    actionUtilisateurDto
                            .setStatusCode(el.get("lockStatus")!=null?el.get("lockStatus").toString():null);
                    actionUtilisateurDto
                            .setSerialNumber(el.get("serialNumber")!=null?el.get("serialNumber").toString():null);
                    actionUtilisateurDto
                            .setContractId(el.get("contractId")!=null?el.get("contractId").toString():null);
                    actionUtilisateurDto.setIsFreezed(Boolean.parseBoolean(el.get("isFreezed").toString()));
                    actionUtilisateurDto
                            .setPortNumber(el.get("portNumber")!=null?el.get("portNumber").toString():null);
                    actionUtilisateurDto.setTariffModelCode(
                            el.get("tariffModelCode")!=null?Integer.parseInt(el.get("tariffModelCode").toString())
                                    :null);
                    actionUtilisateurDto.setActivationDate(
                            el.get("activationDate")!=null?el.get("activationDate").toString():null);
                    actionUtilisateurDto.setStatusBscs(el.get("status")!=null?el.get("status").toString():null);
                    actionUtilisateurDto
                            .setOfferName(el.get("offerName")!=null?el.get("offerName").toString():null);
                    itemDto.add(actionUtilisateurDto);
                }
            }
            System.out.println("Code de statut : "+responseHttp.getStatusLine().getStatusCode());
            System.out.println("Réponse : "+responseBody.toString());
        }catch(IOException|URISyntaxException e){
            e.printStackTrace();
        }
        log.info("----vue details numéro-----");
        response.setItems(itemDto);
        response.setActionEffectue("vue findByMsisdn");
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("",locale));
        return response;

    }

    public Response <Map <String, Object>> getSensitiveNumbers(Request <ActionUtilisateurDto> request,String requestId,
                                                               Locale locale) throws Exception{
        log.info("----begin number from connexion File-----");
        Response <Map <String, Object>> response = new Response <Map <String, Object>>();
        ActionUtilisateurDto dto = request.getData();
        Map <String, Object> fieldsToVerify = new HashMap <String, Object>();
        fieldsToVerify.put("numero",dto.getNumero());
        fieldsToVerify.put("sensitiveNumberPack",dto.getSensitiveNumberPack());
        if(!Validate.RequiredValue(fieldsToVerify).isGood()){
            response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
            response.setHasError(true);
            return response;
        }
        String appId = ParamsUtils.simSwapAppId;
        String msisdn = dto.getNumero();
        String baseUrl = ParamsUtils.simSwapAddress;
        HttpClient httpClient = HttpClients.createDefault();
        try{
            URI uri = new URIBuilder(baseUrl).addParameter("appId",appId).addParameter("requestId",requestId)
                    .addParameter("msisdn",msisdn)
                    .addParameter("pageNumber",dto.getPageNumber()!=null?dto.getPageNumber():"0")
                    .addParameter("pageSize",dto.getPageSize()!=null?dto.getPageSize():"25").build();
            HttpGet httpGet = new HttpGet(uri);
            HttpResponse responseHttp = httpClient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseHttp.getEntity().getContent()));
            StringBuilder responseBody = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                responseBody.append(line);
            }
            // Affichez la réponse
            System.out.println("Code de statut : "+responseHttp.getStatusLine().getStatusCode());
            System.out.println("Réponse : "+responseBody.toString());

        }catch(IOException|URISyntaxException e){
            e.printStackTrace();
        }
        log.info("----end number from connexion file-----");
        response.setActionEffectue("Vue getSensitiveNumbers");
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("",locale));
        return response;
    }

    public Response <Map <String, Object>> freezeSensitiveNumber(Request <ActionUtilisateurDto> request,String requestId,
                                                                 Locale locale) throws Exception{
        log.info("----begin number from connexion File-----");
        Response <Map <String, Object>> response = new Response <Map <String, Object>>();
        ActionUtilisateurDto dto = request.getData();
        Map <String, Object> fieldsToVerify = new HashMap <String, Object>();
        fieldsToVerify.put("numero",dto.getNumero());
        if(!Validate.RequiredValue(fieldsToVerify).isGood()){
            response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
            response.setHasError(true);
            return response;
        }
        String appId = ParamsUtils.simSwapAppId;
        String msisdn = dto.getNumero();
        String baseUrl = ParamsUtils.simSwapAddress+"/";
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl+msisdn+"/freeze");
        httpPost.addHeader("appId",appId);
        httpPost.addHeader("requestId",requestId);
        try{
            HttpResponse responseHttp = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseHttp.getEntity().getContent()));
            StringBuilder responseBody = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                responseBody.append(line);
            }
            System.out.println("Code de statut : "+responseHttp.getStatusLine().getStatusCode());
            System.out.println("Réponse : "+responseBody.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
        log.info("----end number from connexion file-----");
        response.setActionEffectue("Vue freezeSensitiveNumber");
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("",locale));
        return response;

    }

    public Response <ActionUtilisateurDto> getNumbersList(Request <ActionUtilisateurDto> request,
                                                          Locale locale) throws Exception{
        log.info("----begin number from connexion File-----");
        Response <ActionUtilisateurDto> response = new Response <>();
        List <ActionUtilisateurDto> actionUtilisateurList = new ArrayList <>();
        ActionUtilisateurDto dto = request.getData();
        Map <String, Object> fieldsToVerify = new HashMap <>();
        fieldsToVerify.put("datasNumero",dto.getDatasNumero());
        if(!Validate.RequiredValue(fieldsToVerify).isGood()){
            response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
            response.setHasError(true);
            return response;
        }
         dto.getDatasNumero().stream().forEach(arg ->{
                Request<ActionUtilisateurDto> actionUtilisateurDtoRequest = new Request <>();
             actionUtilisateurDtoRequest.setHostName(request.getHostName());
             actionUtilisateurDtoRequest.setIpAddress(request.getIpAddress());
             actionUtilisateurDtoRequest.setUser(request.getUser());
             actionUtilisateurDtoRequest.setIsSearchNumbers(Boolean.TRUE);
             ActionUtilisateurDto actionUtilisateurDto = new ActionUtilisateurDto();
             actionUtilisateurDto.setNumero(arg);
             actionUtilisateurDtoRequest.setData(actionUtilisateurDto);
             try{
                 Response<ActionUtilisateurDto> actionUtilisateurDtoResponse =  getByCriteria(actionUtilisateurDtoRequest,locale);
                 if(actionUtilisateurDtoResponse != null && Utilities.isNotEmpty(actionUtilisateurDtoResponse.getItems())){
                     actionUtilisateurList.addAll(actionUtilisateurDtoResponse.getItems());
                 }
             }catch(Exception e){
                 throw new RuntimeException(e);
             }
         });
        log.info("----end number from connexion file-----");
        if(Utilities.isNotEmpty(actionUtilisateurList)){
            response.setFilePathDoc(writeFileName(actionUtilisateurList));
        }
        response.setActionEffectue("Vue lockSensitiveNumber");
        response.setItems(actionUtilisateurList);
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("",locale));
        return response;

    }

    public Response <Map <String, Object>> unlockSensitiveNumber(Request <ActionUtilisateurDto> request,String requestId,
                                                                 Locale locale) throws Exception{
        log.info("----begin number from connexion File-----");
        Response <Map <String, Object>> response = new Response <Map <String, Object>>();
        ActionUtilisateurDto dto = request.getData();
        Map <String, Object> fieldsToVerify = new HashMap <String, Object>();
        fieldsToVerify.put("numero",dto.getNumero());
        if(!Validate.RequiredValue(fieldsToVerify).isGood()){
            response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
            response.setHasError(true);
            return response;
        }
        String appId = ParamsUtils.simSwapAppId;
        String msisdn = dto.getNumero();
        String baseUrl = ParamsUtils.simSwapAddress+"/";
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl+msisdn+"/unlock");
        httpPost.addHeader("appId",appId);
        httpPost.addHeader("requestId",requestId);
        try{
            HttpResponse responseHttp = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseHttp.getEntity().getContent()));
            StringBuilder responseBody = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                responseBody.append(line);
            }
            System.out.println("Code de statut : "+responseHttp.getStatusLine().getStatusCode());
            System.out.println("Réponse : "+responseBody.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
        log.info("----end number from connexion file-----");
        response.setActionEffectue("Vue unlockSensitiveNumber");
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("",locale));
        return response;

    }

    public Response <ActionUtilisateurDto> exportActionUtilisateur(Request <ActionUtilisateurDto> request,
                                                                   Locale locale){
        response = new Response <>();
        try{
            request.getData();
            response = getByCriteria(request,locale);
            if(response!=null&&Utilities.isNotEmpty(response.getItems())){
                SimpleDateFormat sdfFileName = new SimpleDateFormat(
                        "dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
                ClassPathResource templateClassPathResource = new ClassPathResource("templates/reporting_outil.xlsx");
                InputStream fileInputStream = templateClassPathResource.getInputStream();
                XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                XSSFSheet sheet = workbook.getSheetAt(0); // la 1ere feuille du classeur excel
                Cell cell = null;
                Row row = null;
                int rowIndex = 1;
                for(ActionUtilisateurDto dto: response.getItems()){
                    // Renseigner les ids
                    log.info("--row--"+rowIndex);
                    row = sheet.getRow(rowIndex);
                    if(row==null){
                        row = sheet.createRow(rowIndex);
                    }
                    cell = getCell(row,0);
                    cell.setCellValue(rowIndex);
                    cell = getCell(row,1);
                    System.out.println("ICI LE NUMERO DE TELEPHONE ===========> "+dto.getNumero());
                    cell.setCellValue(Utilities.notBlank(dto.getNumero())?dto.getNumero().contains("=")?AES.decrypt(dto.getNumero(),StatusApiEnum.SECRET_KEY):dto.getNumero():"");
                    cell = getCell(row,2);
                    cell.setCellValue(Utilities.notBlank(dto.getStatusBscs())?dto.getStatusBscs():"");
                    cell = getCell(row,3);
                    cell.setCellValue(dto.getIdStatus()==StatusApiEnum.BLOQUER?"OUI":"NON");
                    cell = getCell(row,4);
                    cell.setCellValue(dto.getLastBlocageDate());
                    cell = getCell(row,5);
                    cell.setCellValue(dto.getUpdatedAt());
                    cell = getCell(row,6);
                    cell.setCellValue(dto.getIsMachine()==Boolean.TRUE?"OUI":"NON");
                    cell = getCell(row,7);
                    cell.setCellValue(dto.getLastMachineDate());
                    rowIndex++;
                }
                // Ajuster les colonnes
                for(int i = 0;i<=Utilities.getColumnIndex("I");i++){
                    sheet.autoSizeColumn(i);
                }
                fileInputStream.close();
                String fileName = Utilities.saveFile("LIST_OF_ACTION_UTILISATEUR"+sdfFileName.format(new Date()),
                        "xlsx",workbook,paramsUtils);
                response.setFilePathDoc(Utilities.getFileUrlLink(fileName,paramsUtils));
                response.setHasError(Boolean.FALSE);

                response.setItems(null);
            }else{
                response.setItems(new ArrayList <>());
                response.setStatus(functionalError.DATA_EMPTY("Liste vide",locale));
                response.setHasError(Boolean.FALSE);
            }
            log.info("----end exportOlt -----");
        }catch(PermissionDeniedDataAccessException e){
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response,locale,e);
        }catch(DataAccessResourceFailureException e){
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response,locale,e);
        }catch(DataAccessException e){
            exceptionUtils.DATA_ACCESS_EXCEPTION(response,locale,e);
        }catch(RuntimeException e){
            exceptionUtils.RUNTIME_EXCEPTION(response,locale,e);
        }catch(Exception e){
            exceptionUtils.EXCEPTION(response,locale,e);
        }finally{
            if(response.isHasError()&&response.getStatus()!=null){
                throw new RuntimeException(response.getStatus().getCode()+";"+response.getStatus().getMessage());
            }
        }
        return response;
    }

    public Response <ActionUtilisateurDto> actionOnNumber(Request <ActionUtilisateurDto> request,Locale locale,
                                                          Boolean isBoolean) throws Exception{
        log.info("---- begin create ActionUtilisateur -----");
        Response <ActionUtilisateurDto> response = new Response <>();
        List <ActionUtilisateur> items = new ArrayList <>();
        List <ActionUtilisateur> itemsToSaved = new ArrayList <>();
        List <String> numStringList = new ArrayList <>();
        String ipAddress = null;
        String hostName = null;
        User existingUser;

        for(ActionUtilisateurDto dto: request.getDatas()){
            // Definir les parametres obligatoires
            Map <String, Object> fieldsToVerify = new HashMap <String, Object>();
            fieldsToVerify.put("numero",dto.getNumero());
            fieldsToVerify.put("idStatus",dto.getIdStatus());
            fieldsToVerify.put("idTypeNumero",dto.getTypeNumeroId());
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
            Status existingStatus;
            if(dto.getIdStatus()!=null&&dto.getIdStatus()>0){
                existingStatus = statusRepository.findOne(dto.getIdStatus(),false);
                if(existingStatus==null){
                    response.setStatus(functionalError.DATA_NOT_EXIST("status -> "+dto.getIdStatus(),locale));
                    response.setHasError(true);
                    return response;
                }
            }else{
                existingStatus = null;
            }
            if(request.getUser()!=null&&request.getUser()>0){
                existingUser = userRepository.findOne(request.getUser(),false);
                if(existingUser==null){
                    response.setStatus(functionalError.DATA_NOT_EXIST("user idUser -> "+request.getUser(),locale));
                    response.setHasError(true);
                    return response;
                }
            }else{
                existingUser = null;
            }
            if(existingUser.getCategory()==null){
                response.setStatus(functionalError.DISALLOWED_OPERATION("PAS AUTORISÉ",locale));
                response.setHasError(Boolean.TRUE);
                return response;
            }
            TypeNumero typeNumero = typeNumeroRepository.findOne(dto.getTypeNumeroId(),Boolean.FALSE);
            if(typeNumero==null){
                response.setStatus(functionalError.DATA_NOT_EXIST("idTypeNumero  -> "+dto.getTypeNumeroId(),locale));
                response.setHasError(true);
                return response;
            }
            Profil profilFound = profilRepository.findOne(existingUser.getProfil()!=null?existingUser.getProfil().getId():null,Boolean.FALSE);
            if(profilFound!=null){
                if(profilFound.getLibelle().equalsIgnoreCase("ADMIN MÉTIER")){
                    if(existingStatus.getId()==StatusApiEnum.DEBLOQUER){
                        response.setStatus(functionalError.DISALLOWED_OPERATION(
                                "Peut pas Léver une mise en machine   -> "+profilFound.getLibelle(),locale));
                        response.setHasError(true);
                        return response;
                    }
                }
            }
            List <ActionUtilisateur> dataActionUser = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(),
                    existingUser.getCategory().getId(),AES.encrypt(dto.getNumero(),StatusApiEnum.SECRET_KEY),
                    Boolean.FALSE);
            if(isBoolean==Boolean.TRUE){
                if(!Utilities.isNotEmpty(dataActionUser)){
                    response.setStatus(functionalError.DATA_EMPTY(" NUMERO PAS CONCERNÉ",locale));
                    response.setHasError(Boolean.TRUE);
                    return response;
                }
            }
            if(Utilities.isNotEmpty(dataActionUser)){
                ActionUtilisateur arg = dataActionUser.get(0);
                List <ActionUtilisateur> actionUsersToDelete = dataActionUser.stream().skip(1).collect(Collectors.toList());
                if(Utilities.isNotEmpty(actionUsersToDelete)){
                    actionUsersToDelete.stream().forEach(toDelete->{
                        toDelete.setIsDeleted(Boolean.TRUE);
                        actionUtilisateurRepository.save(toDelete);
                    });
                }
                arg.setUser(existingUser);
                arg.setDate(Utilities.getCurrentDate());
                arg.setCreatedBy(request.getUser());
                arg.setUpdatedAt(Utilities.getCurrentDate());
                arg.setEmpreinte(Utilities.empreinte(existingStatus.getCode(),request.getUser(),
                        Utilities.getCurrentDateTime(),dto.getNumero()));
                arg.setNumero(dto.getNumero());
                arg.setFromBss(Boolean.TRUE);
                arg.setIsDeleted(Boolean.FALSE);
                items.add(arg);
            }else{
                ActionUtilisateur actionUtilisateur = new ActionUtilisateur();
                actionUtilisateur.setNumero(AES.encrypt(dto.getNumero(),StatusApiEnum.SECRET_KEY));
                actionUtilisateur.setUser(existingUser);
                actionUtilisateur.setIsExcelNumber(Boolean.FALSE);
                actionUtilisateur.setProfil(existingUser.getProfil());
                actionUtilisateur.setStatus(existingStatus);
                actionUtilisateur.setIsDeleted(Boolean.FALSE);
                actionUtilisateur.setContractId(dto.getContractId());
                actionUtilisateur.setDate(Utilities.getCurrentDate());
                actionUtilisateur.setOfferName(dto.getOfferName());
                actionUtilisateur.setTypeNumero(typeNumero);
                actionUtilisateur.setCreatedBy(request.getUser());
                actionUtilisateur.setEmpreinte(Utilities.empreinte(existingStatus.getCode(),request.getUser(),
                        Utilities.getCurrentDateTime(),dto.getNumero()));
                actionUtilisateur.setStatusBscs(dto.getStatusBscs());
                actionUtilisateur.setActivationDate(Utilities.parseDate(dto.getActivationDate()));
                actionUtilisateur.setPortNumber(dto.getPortNumber());
                actionUtilisateur.setSerialNumber(dto.getSerialNumber());
                actionUtilisateur.setCreatedAt(Utilities.getCurrentDate());
                if(existingUser.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.OMCI_CODE)||existingUser.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.TELCO_CODE)){
                    actionUtilisateur.setCategory(existingUser.getCategory());
                }else{
                    extracted(dto,actionUtilisateur);
                }
                itemsToSaved.add(actionUtilisateur);
            }
            if(!itemsToSaved.isEmpty()){
                if(Utilities.isNotEmpty(itemsToSaved)){
                    String finalHostName = hostName;
                    String finalIpAddress = ipAddress;
                    User finalExistingUser = existingUser;
                    itemsToSaved.forEach(arg->{
                        NumeroStories dataStories = new NumeroStories();
                        _AppRequestDto _AppRequest = _AppRequestDto.builder().phoneNumber(dto.getNumero()).build();
                        ApiResponse <LockUnLockFreezeDto> apiResponse = null;
                        switch(existingStatus.getCode()){
                            case StatusApiEnum.BLOCk:
                                apiResponse = simSwapServiceProxyService.lockPhoneNumber(_AppRequest);
                                if(apiResponse.getStatus()==200){
                                    arg.setStatus(existingStatus);
                                    arg.setStatut(StatusApiEnum.EFFECTUER);
                                    arg.setLastBlocageDate(Utilities.getCurrentDate());
                                }else{
                                    arg.setStatut(StatusApiEnum.ECHOUER);
                                }
                                break;
                            case StatusApiEnum.DEBLOCK:
                                apiResponse = simSwapServiceProxyService.unlockPhoneNumber(_AppRequest);
                                if(apiResponse.getStatus()==200){
                                    arg.setStatus(existingStatus);
                                    arg.setStatut(StatusApiEnum.EFFECTUER);
                                    arg.setLastDebloquage(Utilities.getCurrentDate());
                                }else{
                                    arg.setStatut(StatusApiEnum.ECHOUER);
                                }
                                break;
                            case StatusApiEnum.FREEZE:
                                apiResponse = simSwapServiceProxyService.freezePhoneNumber(_AppRequest);
                                dataStories.setIsMachine(Boolean.TRUE);
                                if(apiResponse.getStatus()==200){
                                    arg.setStatus(existingStatus);
                                    arg.setStatut(StatusApiEnum.EFFECTUER);
                                    arg.setLastMachineDate(Utilities.getCurrentDate());
                                    arg.setIsMachine(Boolean.TRUE);
                                }else{
                                    arg.setStatut(StatusApiEnum.ECHOUER);
                                }
                                break;
                        }
                        items.add(actionUtilisateurRepository.save(arg));
                        dataStories.setUser(finalExistingUser);
                        dataStories.setCreatedAt(Utilities.getCurrentDate());
                        dataStories.setNumero(AES.decrypt(arg.getNumero(),StatusApiEnum.SECRET_KEY));
                        dataStories.setProfil(finalExistingUser!=null?finalExistingUser.getProfil():null);
                        dataStories.setIsDeleted(Boolean.FALSE);
                        dataStories.setStatut(apiResponse.getStatus()==200?"SUCCESS":"FAILED");
                        dataStories.setTypeNumero(typeNumero);
                        dataStories.setAdresseIp(finalIpAddress);
                        dataStories.setLogin(finalExistingUser.getLogin());
                        dataStories.setMachine(finalHostName);
                        dataStories.setContractId(arg.getContractId());
                        dataStories.setPortNumber(arg.getPortNumber());
                        dataStories.setSerialNumber(arg.getSerialNumber());
                        dataStories.setOfferName(arg.getOfferName());
                        dataStories.setStatut(apiResponse.getStatus()==200?"SUCCESS":"FAILED");
                        dataStories.setReason(apiResponse.getMessage());
                        dataStories.setCategory(finalExistingUser.getCategory());
                        numeroStoriesRepository.save(dataStories);
                    });
                    numStringList.add(dto.getNumero());
                }
                simSwapServiceProxyService.msisdnForRefraiche(UUID.randomUUID().toString(),numStringList,existingUser);
                List <ActionUtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                        ?ActionUtilisateurTransformer.INSTANCE.toLiteDtos(items)
                        :ActionUtilisateurTransformer.INSTANCE.toDtos(items);
                final int size = items.size();
                List <String> listOfError = Collections.synchronizedList(new ArrayList <>());
                itemsDto.parallelStream().forEach(dtos->{
                    try{
                        dtos = getFullInfos(dtos,size,request.getIsSimpleLoading(),locale);
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
            log.info("----end actionOnNumber  ActionUtilisateur-- ---");
        }
        return response;
    }

    private void extracted(ActionUtilisateurDto dto,ActionUtilisateur actionUtilisateur){
        Category categoryTelco = categoryRepository.findByCode(StatusApiEnum.TELCO_CODE,Boolean.FALSE);
        Category categoryOmci = categoryRepository.findByCode(StatusApiEnum.OMCI_CODE,Boolean.FALSE);
        if(dto.getOfferName().equalsIgnoreCase("Orange money PDV")||dto.getOfferName().equalsIgnoreCase("ORANGE ZEBRA STK")){
            actionUtilisateur.setCategory(categoryOmci);
        }else{
            actionUtilisateur.setCategory(categoryTelco);
        }
    }
}
