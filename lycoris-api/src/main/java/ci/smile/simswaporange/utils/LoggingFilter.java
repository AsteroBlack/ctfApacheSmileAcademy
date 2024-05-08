package ci.smile.simswaporange.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ci.smile.simswaporange.dao.constante.Constant;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.dao.repository.*;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.google.gson.Gson;

import ci.smile.simswaporange.business.HistoriqueBusiness;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.dto.HistoriqueDto;
import ci.smile.simswaporange.utils.enums.StatusApiEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {
    @Autowired
    private HistoriqueBusiness historiqueBusiness;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActionSimswapRepository actionSimswapRepository;
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private ParametrageRepository parametrageRepository;
    @Autowired
    private StatusRepository statusRepository;
    private Locale locale;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("info -------------------------------------------------------------------------------------");
        log.info("host {}, adressIp {}, remoteUser {}", request.getRemoteHost(), request.getRemoteAddr(), request.getRemoteUser());
        log.info("end -------------------------------------------------------------------------------------");
        List<String> constantUri = Constant.containsUris();
        ///simswap
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        String requestID = UUID.randomUUID().toString();
        requestWrapper.setAttribute("requestID", requestID);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
        responseWrapper.copyBodyToResponse();
        String ipAddress = request.getRemoteAddr();
        String hostName = request.getRemoteHost();
        String remoteUri = request.getRequestURI();
        Gson gson = new Gson();
        Map<Object, Object> attribute = gson.fromJson(requestBody, Map.class);
        if (attribute != null) {
            hostName = attribute.get("hostName") != null ? attribute.get("hostName").toString() : request.getRemoteHost();
            ipAddress = attribute.get("ipAddress") != null ? attribute.get("ipAddress").toString() : request.getRemoteAddr();
        }
        switch (request.getRequestURI()) {
            case "/simswap/user/connexionLdap":
            case "/simswap/user/connexion":
                try {
                    logsConnexionUtilisateur( hostName, ipAddress, requestBody, responseBody, request.getRequestURI(), null);
                } catch (IOException | ServletException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "/simswap/actionUtilisateur/uploadOneFile":
                try {
                    logsUploadOneFile(request, requestID, hostName, ipAddress, requestBody, responseBody, request.getRequestURI());
                } catch (IOException | ServletException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/simswap/demande/create":

            case "/simswap/demande/validerRefuser":
                try {
                    logsPourLesUriNonDefini(request, requestID, hostName, ipAddress, request.getRequestURI(), requestBody, responseBody);
                } catch (IOException | ServletException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "/simswap/parametrage/create":

            case "/simswap/parametrage/update":
                try {
                    logsParametrageCreateAndUpdateextracted(request, requestID, hostName, ipAddress, requestBody, responseBody, request.getRequestURI());
                } catch (IOException | ServletException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                String finalHostName = hostName;
                String finalIpAddress = ipAddress;
                String requestUri = remoteUri;
                constantUri.stream().forEach(args -> {
                    try {
                        if (request.getRequestURI().equalsIgnoreCase(args)) {
                            logsPourLesUriNonDefini(request, requestID, finalHostName, finalIpAddress, requestUri, requestBody, responseBody);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                });
        }
    }

    private void logsPourLesUriNonDefini(HttpServletRequest request, String requestID, String hostName, String ipAddress, String uri, String requestBody, String responseBody) throws IOException, ServletException {
        log.info("uri action simswap {}", uri);
        System.out.println("log uri for logs --------------" + uri);
        Gson gson = new Gson();
        Status status = getStatus(requestBody);
        Map<Object, Object> attributes = gson.fromJson(responseBody, Map.class);
        String actionEffectue = attributes.containsKey("actionEffectue") ? attributes.get("actionEffectue") != null ? attributes.get("actionEffectue").toString() : "" : null;
        User findLogin = getUser(requestBody);
        String raison = recupererRaison(attributes, actionEffectue, uri);
        Boolean isTransfert = getIsTransfertBoolean(requestBody);
        Map<String, Object> infosUser = getInfosUser(requestBody);
        switch (uri) {
            case "/simswap/demande/validerRefuser":
                String numeroValiderRefuserList = recupererNumbersValiderRefuserDemande(attributes, actionEffectue);
                ActionSimswap actionSimswapValidationTransfert;
                if (isTransfert == Boolean.TRUE) {
                    actionSimswapValidationTransfert = actionSimswapRepository.findByLibelle("VALIDATION TRANSFERT DE CATEGORIE", Boolean.FALSE);
                } else {
                    actionSimswapValidationTransfert = resolveActionSimswap(requestBody);
                }
                createHistoriqueBody(actionEffectue, requestBody, findLogin, responseBody, hostName, ipAddress, actionSimswapValidationTransfert, gson, request.getRequestURI(), status != null ? status.getId() : null, raison, numeroValiderRefuserList, null, null);
                break;
            case "/simswap/demande/create":
                String numeroDemande = getNumbers(requestBody, uri, responseBody);
                ActionSimswap actionSimswapDemandeTransfert;
                if (isTransfert == Boolean.TRUE) {
                    actionSimswapDemandeTransfert = actionSimswapRepository.findByLibelle("DEMANDE TRANSFERT DE CORBEILLE", Boolean.FALSE);
                } else {
                    actionSimswapDemandeTransfert = resolveDemandeActionSimswap(requestBody);
                }
                createHistoriqueBody(actionEffectue, requestBody, findLogin, responseBody, hostName, ipAddress, actionSimswapDemandeTransfert, gson, request.getRequestURI(), status != null ? status.getId() : null, raison, numeroDemande, null, null);
                break;
            case "/simswap/actionUtilisateur/findByMsisdn":

            case "/simswap/demande/actionOnNumber":
                String numeroDemandeMsisdn = getNumbers(requestBody, uri,responseBody);
                ActionSimswap actionSimswapNumbers = actionSimswapRepository.findByUri(uri, Boolean.FALSE);
                createHistoriqueBody(actionEffectue, requestBody, findLogin, responseBody, hostName, ipAddress, actionSimswapNumbers, gson, request.getRequestURI(), status != null ? status.getId() : null, raison, numeroDemandeMsisdn, null, null);
                break;
            case "/simswap/actionEnMasse/executeOnMasse":
                String fichierOperation = getNumbers(requestBody, uri,responseBody);
                Status statusOperation = getStatusData(requestBody);
                ActionSimswap actionSimswapstatusOperation = null;
                if (statusOperation!= null && statusOperation.getId().equals(StatusApiEnum.MISE_EN_MACHINE)){
                    actionSimswapstatusOperation = actionSimswapRepository.findByLibelle("MISE_EN_MACHINE EN MASSE",Boolean.FALSE);
                } else if (statusOperation!= null && statusOperation.getId().equals(StatusApiEnum.BLOQUER)) {
                    actionSimswapstatusOperation = actionSimswapRepository.findByLibelle("BLOCAGE EN MASSE",Boolean.FALSE);
                }
                createHistoriqueBody(actionEffectue, requestBody, findLogin, responseBody, hostName, ipAddress, actionSimswapstatusOperation, gson, request.getRequestURI(), statusOperation != null ? statusOperation.getId() : null, raison, fichierOperation, null, null);
                break;
            case "/simswap/user/delete":
            case "/simswap/user/update":
            case "/simswap/user/blockUser":
            case "/simswap/user/unBlockUser":
                User userAction = getIdUser(requestBody);
                ActionSimswap actionSimswapUsers = actionSimswapRepository.findByUri(uri, Boolean.FALSE);
                createHistoriqueBody(actionEffectue, requestBody, findLogin, responseBody, hostName, ipAddress, actionSimswapUsers, gson, request.getRequestURI(), status != null ? status.getId() : null, raison, "PAS DE NUMÉRO", userAction, null);
                break;
            case "/simswap/user/create":
                ActionSimswap actionSimswapUsersCreate = actionSimswapRepository.findByUri(uri, Boolean.FALSE);
                createHistoriqueBody(actionEffectue, requestBody, findLogin, responseBody, hostName, ipAddress, actionSimswapUsersCreate, gson, request.getRequestURI(), status != null ? status.getId() : null, raison, "PAS DE NUMÉRO", null, infosUser);
                break;
            default:
                ActionSimswap actionSimswap = actionSimswapRepository.findByUri(uri, Boolean.FALSE);
                createHistoriqueBody(actionEffectue, requestBody, findLogin, responseBody, hostName, ipAddress, actionSimswap, gson, request.getRequestURI(), status != null ? status.getId() : null, raison, "PAS CONCERNÉ", null, null);

        }
    }

    private static String recupererRaison(Map<Object, Object> attributes, String actionEffectue, String uri) {
        String raison;
        if (attributes.containsKey("raison")) {
            raison = attributes.get("raison") != null ? attributes.get("raison").toString() : actionEffectue;
        } else {
            if (uri.equalsIgnoreCase("/simswap/actionUtilisateur/findByMsisdn")) {
                    raison = attributes.containsKey("message") ? attributes.get("message").toString() : null;
                return raison;
            } else {
                Map<Object, Object> raison1 = attributes.containsKey("status") ? (Map<Object, Object>) attributes.get("status") : null;
                raison = raison1 != null ? raison1.get("message") != null ? raison1.get("message").toString() : actionEffectue : "";
                return raison;
            }
        }
        return raison;
    }

    private static String recupererNumbersValiderRefuserDemande(Map<Object, Object> attributes, String actionEffectue) {
        String raison;
        if (attributes.containsKey("numbers")) {
            raison = attributes.get("numbers") != null ? attributes.get("numbers").toString() : actionEffectue;
            return raison;
        }
        return "Aucun numéro  trouvé";
    }

    private void logsParametrageCreateAndUpdateextracted(HttpServletRequest request, String requestID, String hostName, String ipAddress, String requestBody, String responseBody, String uri) throws IOException, ServletException {
        ActionSimswap actionSimswap = actionSimswapRepository.findByUri(uri, Boolean.FALSE);
        Integer integer = null;
        String actionEffectue = null;
        Gson gson = new Gson();
        Map<Object, Object> attributes = gson.fromJson(responseBody, Map.class);
        System.out.println(attributes);
        if (attributes != null) {
            if (attributes.containsKey("items")) {
                List<Map<Object, Object>> maps = (List<Map<Object, Object>>) attributes.get("items");
                if (Utilities.isNotEmpty(maps)) {
                    Map<Object, Object> map = maps.get(0);
                    double d = (double) map.get("id");
                    integer = (int) d;
                    System.out.println(integer);
                }
            }
            if (attributes.containsKey("actionEffectue")) {
                actionEffectue = attributes.get("actionEffectue").toString();
            }
        }
        String raison;
        Map<Object, Object> raison1 = attributes.containsKey("status") ? (Map<Object, Object>) attributes.get("status") : null;
        raison = raison1 != null ? raison1.get("message") != null ? raison1.get("message").toString() : actionEffectue : "";

        if (integer != null) {
            Parametrage parametrage = parametrageRepository.findOne(integer, Boolean.FALSE);
            if (parametrage != null) {
                parametrage.setDelaiAttente(requestBody);
                parametrageRepository.save(parametrage);
            }
        }
        User findLogin = null;
        Status statusId = null;
        if (Utilities.notBlank(requestBody)) {
            findLogin = getUser(requestBody);
            statusId = getStatus(requestBody);
        }
        createHistoriqueBody(actionEffectue, requestBody, findLogin, responseBody, hostName, ipAddress, actionSimswap, gson, request.getRequestURI(), statusId != null ? statusId.getId() : null, raison, "PAS DE NUMÉRO CONCERNÉ", null, null);
    }

    private void logsUploadOneFile(HttpServletRequest request, String requestID, String hostName, String ipAddress, String requestBody, String responseBody, String uri) throws IOException, ServletException {
        ActionSimswap actionSimswap = actionSimswapRepository.findByUri(uri, Boolean.FALSE);
        System.out.println("---------------------------REQUEST BODY----------------------------" + requestBody);
        String actionEffectue = null;
        Gson gson = new Gson();
        Map<Object, Object> attributes = gson.fromJson(responseBody, Map.class);
        if (attributes != null) {
            if (attributes.containsKey("actionEffectue")) {
               // System.out.println("Ici Attribute .................." + attributes);
                actionEffectue = attributes.get("actionEffectue").toString();
            }
        }
        Integer idData = null;
        Enumeration<String> dataReq = request.getHeaderNames();
        if (dataReq != null) {
            while (dataReq.hasMoreElements()) {
                String head = dataReq.nextElement();
//					System.out.println(head + " :" + request.getHeader(head));
                if (head.equalsIgnoreCase("user")) {
                    idData = Integer.parseInt(request.getHeader(head));
                }
            }
        }

        String raison;
        Map<Object, Object> raison1 = attributes.containsKey("status") ? (Map<Object, Object>) attributes.get("status") : null;
        raison = raison1 != null ? raison1.get("message") != null ? raison1.get("message").toString() : actionEffectue : "";
        User findLogin = userRepository.findOne(idData, Boolean.FALSE);
        createHistoriqueBody(actionEffectue, requestBody, findLogin, responseBody, hostName, ipAddress, actionSimswap, gson, request.getRequestURI(), null, raison, "NUMERO DANS LE FICHIER RESULTAT", null, null);
        System.out.println(request.getRequestURI());
    }

    @SneakyThrows
    private User getUser(String requestBody) {
        Gson gson = new Gson();
        // Convertir le corps de la requête en JSON
        Map<String, Object> map = gson.fromJson(requestBody, Map.class);
        try {
            double nombreEnDouble = Double.parseDouble((map.get("user") != null ? map.get("user").toString() : "1"));
            int partieEntiere = (int) nombreEnDouble;
            User findLogin = userRepository.findOne(partieEntiere, Boolean.FALSE);
            Validate.EntityNotExiste(findLogin);
            if (findLogin.getIsLocked() == Boolean.TRUE) {
                throw new CustomEntityNotFoundException("401", "vous êtes bloqué contacté un administrateur", Boolean.TRUE);
            }
            return findLogin;
        } catch (NumberFormatException e) {
            // La conversion a échoué en raison d'un format incorrect
            User findLogin = userRepository.findOne(Integer.parseInt(map.get("user").toString()), Boolean.FALSE);
            Validate.EntityNotExiste(findLogin);
            if (findLogin.getIsLocked() == Boolean.TRUE) {
                throw new CustomEntityNotFoundException("401", "vous êtes bloqué contacté un administrateur", Boolean.TRUE);
            }
            System.out.println("Erreur de format : La chaîne n'est pas un nombre à virgule flottante valide.");
            return findLogin;
        }

    }

    private User getIdUser(String requestBody) {
        Gson gson = new Gson();
        // Convertir le corps de la requête en JSON
        Map<String, Object> map = gson.fromJson(requestBody, Map.class);
        List<Map<String, Object>> objectMap = (List<Map<String, Object>>) map.get("datas");
        if (Utilities.isNotEmpty(objectMap)){
            Map<String, Object> stringObjectMap = objectMap.get(0);
            try {
                double nombreEnDouble = Double.parseDouble((stringObjectMap.get("id") != null ? stringObjectMap.get("id").toString() : null));
                int partieEntiere = (int) nombreEnDouble;
                User findLogin = userRepository.findOne(partieEntiere, Boolean.FALSE);
                Validate.EntityNotExiste(findLogin);
                return findLogin;
            } catch (NumberFormatException e) {
                // La conversion a échoué en raison d'un format incorrect
                User findLogin = userRepository.findOne(stringObjectMap.get("id") != null ? Integer.parseInt(stringObjectMap.get("id").toString()) : null, Boolean.FALSE);
                Validate.EntityNotExiste(findLogin);
                return findLogin;
            }
        }
       return null;
    }

    private Map<String, Object> getInfosUser(String requestBody) {
        Gson gson = new Gson();
        // Convertir le corps de la requête en JSON
        Map<String, Object> map = gson.fromJson(requestBody, Map.class);
        List<Map<String, Object>> objectMap = (List<Map<String, Object>>) map.get("datas");
        if (Utilities.isNotEmpty(objectMap)){
            Map<String, Object> stringObjectMap = objectMap.get(0);
            Map<String, Object> stringObjectMap1 = new HashMap<>();
            stringObjectMap1.put("nom", stringObjectMap.get("nom") != null ? stringObjectMap.get("nom").toString() : "PAS DÉFINI");
            stringObjectMap1.put("prenom", stringObjectMap.get("prenom") != null ? stringObjectMap.get("prenom").toString() : "PAS DÉFINI");
            stringObjectMap1.put("login", stringObjectMap.get("login") != null ? stringObjectMap.get("login").toString() : "PAS DÉFINI");
            return stringObjectMap1;
        }
        return null;
    }



    private ActionSimswap resolveActionSimswap(String requestBody) {
        Status status = getStatus(requestBody);
        if (status != null) {
            switch (status.getId()) {
                case 2:
                    return actionSimswapRepository.findByLibelle("VALIDATION DEBLOCAGE NUMERO SIMSWAP", Boolean.FALSE);
                case 4:
                    return actionSimswapRepository.findByLibelle("VALIDATION SUPPRIMER NUMERO", Boolean.FALSE);
                default:
                    break;
            }
        }
        return null;
    }

    private ActionSimswap resolveDemandeActionSimswap(String requestBody) {
        Status status = getStatus(requestBody);
        if (status != null) {
            switch (status.getId()) {
                case 2:
                    return actionSimswapRepository.findByLibelle("DEMANDE DEBLOCAGE NUMERO SIMSWAP", Boolean.FALSE);
                case 4:
                    return actionSimswapRepository.findByLibelle("DEMANDE SUPPRESSION NUMERO SIMSWAP", Boolean.FALSE);
                default:
                    break;
            }
        }
        return null;
    }

    @SneakyThrows
    private Status getStatus(String requestBody) {
        Gson gson = new Gson();
        // Convertir le corps de la requête en JSON
        Map<String, Object> map = gson.fromJson(requestBody, Map.class);
        List<Map<String, Object>> objectMap = (List<Map<String, Object>>) map.get("datas");
        try {
            if (Utilities.isNotEmpty(objectMap)) {
                Map<String, Object> map1 = objectMap.get(0);
                double nombreEnDouble = Double.parseDouble((map1.get("idStatus") != null ? map1.get("idStatus").toString() : ""));
                int partieEntiere = (int) nombreEnDouble;
                Status findStatus = statusRepository.findOne(partieEntiere, Boolean.FALSE);
                Validate.EntityNotExiste(findStatus);
                return findStatus;
            }
        } catch (NumberFormatException e) {
            if (Utilities.isNotEmpty(objectMap)) {
                Map<String, Object> map1 = objectMap.get(0);
                log.info("status-------------{}", map1);
                // La conversion a échoué en raison d'un format incorrect
                Status findStatus = statusRepository.findOne(map1.get("idStatus") != null ? Integer.parseInt(map1.get("idStatus").toString()) : null, Boolean.FALSE);
                System.out.println("Erreur de format : La chaîne n'est pas un nombre à virgule flottante valide.");
                return findStatus;
            }
        }
        return null;
    }

    @SneakyThrows
    private Status getStatusData(String requestBody) {
        Gson gson = new Gson();
        // Convertir le corps de la requête en JSON
        Map<String, Object> map = gson.fromJson(requestBody, Map.class);
        Map<String, Object> objectMap = (Map<String, Object>) map.get("data");
        try {
            if (objectMap != null) {
                double nombreEnDouble = Double.parseDouble((objectMap.get("idStatus") != null ? objectMap.get("idStatus").toString() : ""));
                int partieEntiere = (int) nombreEnDouble;
                Status findStatus = statusRepository.findOne(partieEntiere, Boolean.FALSE);
                Validate.EntityNotExiste(findStatus);
                return findStatus;
            }
        } catch (NumberFormatException e) {
                log.info("status-------------{}", objectMap);
                // La conversion a échoué en raison d'un format incorrect
                Status findStatus = statusRepository.findOne(objectMap.get("idStatus") != null ? Integer.parseInt(objectMap.get("idStatus").toString()) : null, Boolean.FALSE);
                System.out.println("Erreur de format : La chaîne n'est pas un nombre à virgule flottante valide.");
                return findStatus;

        }
        return null;
    }

    private String getNumbers(String requestBody, String uri, String responseBody) {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(requestBody, Map.class);
        try {
            if (map.containsKey("datas")){
                List<Map<String, Object>> objectMap = (List<Map<String, Object>>) map.get("datas");
                if (Utilities.isNotEmpty(objectMap)) {
                    String numeroRequest =  objectMap.stream().map(arg->{
                        String numero = arg.get("numero") != null ? arg.get("numero").toString() : "AUCUN NUMERO";
                        return numero;
                    }).collect(Collectors.joining(","));
                    return numeroRequest;
                }
            } else if (uri.equalsIgnoreCase("/simswap/actionUtilisateur/findByMsisdn")) {
                if (map.containsKey("numbers")){
                    List<String> objectMapNumbers = (List<String>) map.get("numbers");
                    if (Utilities.isNotEmpty(objectMapNumbers)){
                        String numbers = objectMapNumbers.stream().collect(Collectors.joining(","));
                        return  numbers;
                    }
                }
            } else if (uri.equalsIgnoreCase("/simswap/actionEnMasse/executeOnMasse")){
                Map<String, Object> responseBodyOperation = gson.fromJson(responseBody, Map.class);
                if (responseBodyOperation != null){
                    return responseBodyOperation.containsKey("numbers") ? responseBodyOperation.get("numbers").toString() : "FICHIER";
                }
            }

        } catch (NumberFormatException e) {
            log.info(e.getMessage());
            return e.getMessage();
        }
        return null;
    }

    private Boolean getIsTransfertBoolean(String requestBody) {
        Gson gson = new Gson();
        // Convertir le corps de la requête en JSON
        Map<String, Object> map = gson.fromJson(requestBody, Map.class);
        List<Map<String, Object>> objectMap = (List<Map<String, Object>>) map.get("datas");
        try {
            if (Utilities.isNotEmpty(objectMap)) {
                Map<String, Object> map1 = objectMap.get(0);
                Boolean isTransfert = (Boolean) map1.get("isTransfert");
                return isTransfert;
            }
        } catch (NumberFormatException e) {
            log.info(e.getMessage());
        }
        return false;
    }

    private String getNumber(String requestBody) {
        Gson gson = new Gson();
        // Convertir le corps de la requête en JSON
        Map<String, Object> map = gson.fromJson(requestBody, Map.class);
        List<Map<String, Object>> objectMap = (List<Map<String, Object>>) map.get("datas");
        try {
            if (Utilities.isNotEmpty(objectMap)) {
                Map<String, Object> map1 = objectMap.get(0);
                String isTransfert = (String) map1.get("numero");
                return isTransfert;
            }
        } catch (NumberFormatException e) {
            log.info(e.getMessage());
        }
        return "PAS DE NUMÉRO";
    }



    private void logsConnexionUtilisateur(String hostName, String ipAddress, String requestBody, String responseBody, String requestUri, Integer idStatus) throws IOException, ServletException {
        String login = null;
        Gson gson = new Gson();
        ActionSimswap actionSimswap = actionSimswapRepository.findByUri(requestUri, Boolean.FALSE);
        Map<Object, Object> attribute = gson.fromJson(requestBody, Map.class);
        if (attribute != null) {
            if (attribute.containsKey("data")) {
                Map<Object, Object> dataKey = gson.fromJson(attribute.get("data").toString(), Map.class);
               // System.out.println("Ici Attribute .................." + attribute);
                if (dataKey.containsKey("login")) {
                    login = dataKey.get("login") != null ? dataKey.get("login").toString() : null;
                }
            }
        }
        Map<Object, Object> attributes = gson.fromJson(responseBody, Map.class);
        String raison;
        Map<Object, Object> raison1 = attributes.containsKey("status") ? (Map<Object, Object>) attributes.get("status") : null;
        raison = raison1 != null ? raison1.get("message") != null ? raison1.get("message").toString() : "" : "";
        String actionEffectue = null;
        User findLogin = userRepository.findByLogin(login, Boolean.FALSE);
        if (findLogin != null) {
            if (findLogin.getFirstConnection() == null) {
                findLogin.setFirstConnection(Utilities.getCurrentDate());
            } else {
                findLogin.setLastConnection(Utilities.getCurrentDate());
            }
            findLogin.setUpdatedAt(Utilities.getCurrentDate());
            findLogin.setIpadress(ipAddress);
            findLogin.setMachine(hostName);
            if (responseBody != null) {
                Map<Object, Object> responseAttribute = gson.fromJson(responseBody, Map.class);
                if (responseAttribute != null && responseAttribute.get("hasError").equals(true)) {
                    findLogin.setStatus(StatusApiEnum.ECHOUER);
                } else {
                    findLogin.setStatus(StatusApiEnum.SUCCESS);
                }
            }
            userRepository.save(findLogin);
        }
        createHistoriqueBody(actionEffectue, requestBody, findLogin, responseBody, hostName, ipAddress, actionSimswap, gson, requestUri, idStatus, raison, "PAS DE NUM2RO CONCERNÉ", null, null);
    }

    private void createHistoriqueBody(String actionEffectue, String requestBody, User findLogin, String responseBody, String hostName, String ipAddress, ActionSimswap actionSimswap, Gson gson, String requestUri, Integer isStatus, String raison, String numero, User userForAdmin, Map<String, Object> infosUser) {
        HistoriqueDto datasHist = new HistoriqueDto();
        datasHist.setActionService(actionEffectue != null ? actionEffectue : requestUri);
        datasHist.setRequest(AES.encrypt(requestBody, StatusApiEnum.SECRET_KEY));
        datasHist.setNom(findLogin != null ? findLogin.getNom() : "");
        datasHist.setPrenom(findLogin != null ? findLogin.getPrenom() : "");
        datasHist.setResponse(AES.encrypt(responseBody, StatusApiEnum.SECRET_KEY));
        datasHist.setIdStatus(isStatus);
        datasHist.setLogin(findLogin != null ? findLogin.getLogin() : "");
        datasHist.setIdUser(findLogin != null ? findLogin.getId() : null);
        datasHist.setCreatedBy(findLogin != null ? findLogin.getId() : null);
        datasHist.setDate(Utilities.getCurrentDateTime());
        datasHist.setUri(requestUri);
        datasHist.setRaison(raison);
        datasHist.setUsername(userForAdmin != null ? userForAdmin.getLogin() : infosUser != null ? infosUser.get("login").toString() : "PAS DÉFINI");
        datasHist.setName(userForAdmin != null ? userForAdmin.getNom() : infosUser != null ? infosUser.get("nom").toString() : "PAS DÉFINI");
        datasHist.setFirstname(userForAdmin != null ? userForAdmin.getPrenom() : infosUser != null ? infosUser.get("prenom").toString() : "PAS DÉFINI");
        if (actionSimswap != null && actionSimswap.getUri().equalsIgnoreCase("/simswap/user/connexionLdap")) {
            datasHist.setIsConnexion(Boolean.TRUE);
        } else {
            datasHist.setIsConnexion(Boolean.FALSE);
        }
        datasHist.setNumero(numero);
        datasHist.setMachine(hostName);
        datasHist.setIpadress(ipAddress);
        datasHist.setIdActionUser(actionSimswap != null ? actionSimswap.getId() : null);
        if (responseBody != null) {
            Map<Object, Object> responseAttribute = gson.fromJson(responseBody, Map.class);
            if (responseAttribute != null && responseAttribute.get("hasError").equals(true)) {
                datasHist.setStatusConnection(StatusApiEnum.ECHOUER);
            } else {
                datasHist.setStatusConnection(StatusApiEnum.SUCCESS);
            }
        }
        Request<HistoriqueDto> datasHistR = new Request<>();
        datasHistR.setDatas(Arrays.asList(datasHist));
        try {
            historiqueBusiness.create(datasHistR, locale);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        if (!Utilities.notBlank(characterEncoding)) {
            characterEncoding = "UTF-8";
        }
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


}
