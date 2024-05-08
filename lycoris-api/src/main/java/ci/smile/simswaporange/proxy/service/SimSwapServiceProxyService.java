package ci.smile.simswaporange.proxy.service;

import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.dao.repository.*;
//import ci.smile.simswaporange.proxy.ProxyService;
import ci.smile.simswaporange.proxy.customizeclass.CategoriesPair;
import ci.smile.simswaporange.proxy.customizeclass.functions.ObjectUtilities;
import ci.smile.simswaporange.proxy.response.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.customize.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.smile.simswaporange.utils.dto.customize._AppRequestDto;
import ci.smile.simswaporange.utils.enums.StatusApiEnum;
import javassist.tools.web.BadHttpRequest;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class SimSwapServiceProxyService {
    private  Context context;
    private final ParamsUtils paramsUtils;
    private final HostingUtils hostingUtils;
    private final UserRepository userRepository;
    private final HttpServletRequest requestBasic;
    private final ObjectUtilities objectUtilities;
    private final StatusRepository statusRepository;
    private final TypeNumeroRepository typeNumeroRepository;
    private final ParametrageRepository parametrageRepository;
    private final TypeParametrageRepository typeParametrageRepository;
    private final ActionUtilisateurRepository actionUtilisateurRepository;
    private final ParametrageProfilRepository parametrageProfilRepository;
    private final String appId = ParamsUtils.simSwapAppId;
    private final String ENTETE = "SIMSWAP - NOTIFICATION";
    private final String TITRE = "ALERTE SÉCURISATION SIMSWAP";
    private static final ObjectMapper mapper = new ObjectMapper();
    private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

    public void sendSMS(_AppRequestDto _AppRequestDto) throws BadHttpRequest {
        if (!Utilities.notBlank(_AppRequestDto.getMessage()) || !Utilities.notBlank(_AppRequestDto.getPhoneNumber())) {
            throw new BadHttpRequest();
        }
        // Paramètres dynamiques et requis
        String soa = "Orange";
//        String da = "0759346278";
        String userName = "application_orange";
        String password = "orange";
//        String content = "TEST SEND MESSAGE";
        String flags = "1";
        String senderAppId = "168";

        String apiUrl = "http://192.168.31.78/routeur_kannel/interface.php/application_orange";
        String urlWithParams = String.format("%s?SOA=%s&DA=%s&UserName=%s&Password=%s&Content=%s&Flags=%s&SenderAppId=%s",
                apiUrl, soa, _AppRequestDto.getPhoneNumber(), userName, password, _AppRequestDto.getMessage(), flags, senderAppId);

        // Créer un client OkHttp
        OkHttpClient client = new OkHttpClient();

        // Construire la requête
        Request request = new Request.Builder()
                .url(urlWithParams)
                .header("Cookie", "BIGipServerPool_NGINX01-SVA-80=1055126026.20480.0000; PHPSESSID=h93hogoh13tgiu5jcjh6q1rnj7")
                .post(RequestBody.create(null, new byte[0]))
                .build();

        // Envoyer la requête de manière asynchrone
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                slf4jLogger.info(e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Traiter la réponse (si nécessaire)
                int statusCode = response.code();
                String responseBody = Objects.requireNonNull(response.body()).string();

                System.out.println("Status Code: " + statusCode);
                System.out.println("Response Body: " + responseBody);
            }
        });

        // Votre application peut continuer ici sans attendre la réponse
    }

//		Builder builder = Feign.builder().decoder(new GsonDecoder());
//		ProxyService myApiService = builder.target(ProxyService.class, "http://192.168.31.78");
//		myApiService.sendSMS(_AppRequestDto.getPhoneNumber(), _AppRequestDto.getMessage(), appId);


    public TokenDto myToken() {
        slf4jLogger.info("start method callAPI");
        String rep = "KO";
        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
        myBuilder = ignoreCertificateSSL(myBuilder);
        OkHttpClient client = myBuilder.build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");
        Request request = new Request.Builder().url("https://192.168.31.195/gwbooster2/token").method("POST", body)
                .addHeader("Authorization",
                        "Basic " + paramsUtils.getBasicAuth())
                .addHeader("Content-Type", "application/x-www-form-urlencoded").build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null) {
                ResponseBody db = response.body();
                if (db != null) {
                    rep = db.string();
                    JsonNode jsonNode = mapper.readTree(rep);
                    // Convertir JsonNode en TokenDto
                    TokenDto token = mapper.treeToValue(jsonNode, TokenDto.class);
                    slf4jLogger.info("----------------------------------------------------------------------------- " + token);

                    return token;
                }
            }
        } catch (IOException e) {
            slf4jLogger.info("message : " + e.getMessage());
            slf4jLogger.info("cause : " + e.getCause());
            e.printStackTrace();
        }

        slf4jLogger.info("end method callAPI");
        return new TokenDto();
    }

    public ApiResponse<LockUnLockFreezeDto> unlockPhoneNumber(_AppRequestDto _AppRequestDto) {
        slf4jLogger.info("start method unlockNumber");
        ApiResponse<LockUnLockFreezeDto> responseUnlock = new ApiResponse<>();
        LockUnLockFreezeDto unLockFreezeDto = null;
        TokenDto tokenDto = myToken();
        if (tokenDto != null) {
            OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
            myBuilder = ignoreCertificateSSL(myBuilder);
            OkHttpClient client = myBuilder.build();

            Request request = new Request.Builder()
                    .url("https://192.168.31.195/gwbooster2/sim-swap-backend/v1/sensitive-numbers/"
                            + _AppRequestDto.getPhoneNumber() + "/unlock?appId=" + appId + "&requestId="
                            + UUID.randomUUID().toString())
                    .post(RequestBody.create(null, new byte[0]))
                    .addHeader("Authorization", "Bearer " + tokenDto.getAccess_token()).build();
            try {
                Response response = client.newCall(request).execute();
                if (response != null) {
                    slf4jLogger.info("Response code: " + response.code());
                    slf4jLogger.info("Response body: " + response.body());
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String responseData = responseBody.string();
                        JsonNode jsonNode = mapper.readTree(responseData);

                        unLockFreezeDto = mapper.treeToValue(jsonNode, LockUnLockFreezeDto.class);
                        slf4jLogger.info("Response data: " + responseData);
                        responseUnlock.setStatus(Utilities.notBlank(unLockFreezeDto.getStatus()) ? Integer.valueOf(unLockFreezeDto.getStatus()) : 0);
                        responseUnlock.setData(unLockFreezeDto);
                        return responseUnlock;
                    }
                }
            } catch (IOException e) {
                slf4jLogger.error("Error during API call: " + e.getMessage());
            }
        }
        slf4jLogger.info("end method unlockNumber");
        return responseUnlock;
    }

    public ApiResponse<LockUnLockFreezeDto> freezePhoneNumber(_AppRequestDto _AppRequestDto) {
        slf4jLogger.info("start method freezeNumber");
        ApiResponse<LockUnLockFreezeDto> responseLock = new ApiResponse<>();
        LockUnLockFreezeDto unLockFreezeDto = null;
        TokenDto tokenDto = myToken();
        if (tokenDto != null) {
            OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
            myBuilder = ignoreCertificateSSL(myBuilder);
            OkHttpClient client = myBuilder.build();

            Request request = new Request.Builder()
                    .url("https://192.168.31.195/gwbooster2/sim-swap-backend/v1/sensitive-numbers/"
                            + _AppRequestDto.getPhoneNumber() + "/freeze?appId=" + appId + "&requestId="
                            + UUID.randomUUID().toString())
                    .post(RequestBody.create(null, new byte[0]))
                    .addHeader("Authorization", "Bearer " + tokenDto.getAccess_token()).build();
            try {
                Response response = client.newCall(request).execute();
                if (response != null) {
                    slf4jLogger.info("Response code: " + response.code());
                    slf4jLogger.info("Response body: " + response.body());

                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String responseData = responseBody.string();
                        JsonNode jsonNode = mapper.readTree(responseData);
                        unLockFreezeDto = mapper.treeToValue(jsonNode, LockUnLockFreezeDto.class);
                        responseLock.setStatus(Utilities.notBlank(unLockFreezeDto.getStatus()) ? Integer.valueOf(unLockFreezeDto.getStatus()) : 0);
                        responseLock.setData(unLockFreezeDto);
                        slf4jLogger.info("Response data: " + responseData);
                        return responseLock;
                    }
                }
            } catch (IOException e) {
                slf4jLogger.error("Error during API call: " + e.getMessage());
            }
        }
        slf4jLogger.info("end method freezeNumber");
        return responseLock;
    }

    public ApiResponse<LockUnLockFreezeDto> lockPhoneNumber(_AppRequestDto _AppRequestDto) {
        slf4jLogger.info("start method lockNumber");
        ApiResponse<LockUnLockFreezeDto> responseLock = new ApiResponse<>();
        LockUnLockFreezeDto unLockFreezeDto = null;
        TokenDto tokenDto = myToken();
        if (tokenDto != null) {
            OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
            myBuilder = ignoreCertificateSSL(myBuilder);
            OkHttpClient client = myBuilder.build();

            Request request = new Request.Builder()
                    .url("https://192.168.31.195/gwbooster2/sim-swap-backend/v1/sensitive-numbers/"
                            + _AppRequestDto.getPhoneNumber() + "/lock?appId=" + appId + "&requestId="
                            + UUID.randomUUID().toString())
                    .post(RequestBody.create(null, new byte[0]))
                    .addHeader("Authorization", "Bearer " + tokenDto.getAccess_token()).build();
            try {
                Response response = client.newCall(request).execute();
                if (response != null) {
                    slf4jLogger.info("Response code: " + response.code());
                    slf4jLogger.info("Response body: " + response.body());
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String responseData = responseBody.string();
                        slf4jLogger.info("----------------------------------------------------------------------------- " + responseData);

                        JsonNode jsonNode = mapper.readTree(responseData);
                        unLockFreezeDto = mapper.treeToValue(jsonNode, LockUnLockFreezeDto.class);
                        responseLock.setStatus(Utilities.notBlank(unLockFreezeDto.getStatus()) ? Integer.valueOf(unLockFreezeDto.getStatus()) : 0);
                        responseLock.setData(unLockFreezeDto);
                        slf4jLogger.info("Response data: " + responseData);
                        return responseLock;
                    }
                }
            } catch (IOException e) {
                slf4jLogger.error("Error during API call: " + e.getMessage());
            }
        }
        slf4jLogger.info("end method lockNumber");
        return responseLock;
    }

    public ApiResponse<SensitiveNumberData> getContract(_AppRequestDto _AppRequestDto, ActionUtilisateur actionUtilisateur) {
        slf4jLogger.info("start method contract");
        ApiResponse<SensitiveNumberData> apiResponse = new ApiResponse<>();
        String requestId = UUID.randomUUID().toString();
        TokenDto map = myToken();
        String token = map.getAccess_token() != null ? map.getAccess_token() : null;
        SensitiveNumberData unLockFreezeDto = null;
        TokenDto tokenDto = myToken();
        if (tokenDto != null) {
            OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
            myBuilder = ignoreCertificateSSL(myBuilder);
            OkHttpClient client = myBuilder.build();

            Request request = new Request.Builder()
                    .url("https://192.168.31.195/gwbooster2/sim-swap-backend/v1/sensitive-numbers/find-by-msisdn?appId=" + appId + "&requestId=" + requestId + "&msisdn=" + _AppRequestDto.getMsisdn())
                    .method("GET", null).addHeader("Authorization", "Bearer " + token).build();
            try {
                Response response = client.newCall(request).execute();
                if (response != null) {
                    slf4jLogger.info("Response code: " + response.code());
                    slf4jLogger.info("Response body: " + response.body());
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String responseData = responseBody.string();
                        slf4jLogger.info("----------------------------------------------------------------------------- " + responseData);
                        JsonNode jsonNode = mapper.readTree(responseData);
                        unLockFreezeDto = mapper.treeToValue(jsonNode, SensitiveNumberData.class);
                        apiResponse.setStatus(Utilities.notBlank(unLockFreezeDto.getStatus()) ? Integer.valueOf(unLockFreezeDto.getStatus()) : 0);
                        apiResponse.setData(unLockFreezeDto);
                        slf4jLogger.info("Response data: " + responseData);
                        if (apiResponse.getStatus() == 200) {
                            slf4jLogger.info("Prêt a raffraichir la requête: {}", _AppRequestDto);
                            slf4jLogger.info("Prêt a raffraichir la donnée: {}", unLockFreezeDto);
                            if (_AppRequestDto.getUser() != null) {
                                refreshNumberOnCorail(_AppRequestDto, unLockFreezeDto);
                            } else {
                                if (actionUtilisateur != null) {
                                    refresh(_AppRequestDto, unLockFreezeDto, actionUtilisateur);

                                } else {
                                    slf4jLogger.info("commence a raffraichir {}", unLockFreezeDto.getData().getMsisdn());
                                    refresh(_AppRequestDto, unLockFreezeDto, null);
                                }
                            }
                        }
                        return apiResponse;
                    }
                }
            } catch (IOException e) {
                if (actionUtilisateur != null) {
                    actionUtilisateurRepository.save(actionUtilisateur);
                }
                slf4jLogger.error("Error during API call: " + e.getMessage());
            }
            apiResponse.setData(unLockFreezeDto);

        }
        slf4jLogger.info("end method contract");
        return apiResponse;
    }

    public LockUnLockFreezeDto findByMsisdn(String requestId, List<String> strings, Integer userID, Boolean isTransfert) {
        LockUnLockFreezeDto lockUnLockFreezeDtos = new LockUnLockFreezeDto();
        if (!Utilities.isNotEmpty(strings)) {
            lockUnLockFreezeDtos.setCode("400");
            lockUnLockFreezeDtos.setMessage("aucun numero renseigné");
            lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
            return lockUnLockFreezeDtos;
        }
        User user = userRepository.findOne(userID, Boolean.FALSE);
        Validate.EntityNotExiste(user);
        String numbers = extractedNumbers(strings);
        AtomicReference<String> message = new AtomicReference<>();
        List<Result> resultList = new ArrayList<>();
        slf4jLogger.info("-----------------------------------Numbers------------------------------------------ " + numbers);
        slf4jLogger.info("----------------URI-------------" + paramsUtils.getSimswapBaseUriv2() + "find-by-msisdn?" + numbers + "&appId=" + paramsUtils.getAppId() + "&requestId=" + requestId);
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(paramsUtils.getSimswapBaseUriv2() + "find-by-msisdn?" + numbers + "&appId=" + paramsUtils.getAppId() + "&requestId=" + requestId)
                    .method("GET", null)
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseData = responseBody.string();
                    slf4jLogger.info("-----------------------------------STRING------------------------------------------ " + responseData);
                    JsonNode jsonNode = mapper.readTree(responseData);
                    slf4jLogger.info("---------------------------------JSONNODE-------------------------------------------- " + jsonNode);
                    if (jsonNode != null && jsonNode.get("status").asInt() == 200) {
                        lockUnLockFreezeDtos = mapper.treeToValue(jsonNode, LockUnLockFreezeDto.class);
                        slf4jLogger.info("---------------------------------DESERIALISATION-------------------------------------------- " + lockUnLockFreezeDtos);
                        if (lockUnLockFreezeDtos != null && Utilities.isNotEmpty(lockUnLockFreezeDtos.getData())) {
                            lockUnLockFreezeDtos.getData().stream().forEach(arg -> {
                                    if (arg.getMessage() == null && arg.getPortNumber() > 0) {
                                        List<ActionUtilisateur> actionUtilisateurs = actionUtilisateurRepository.findByNumeroListe(AES.encrypt(arg.getMsisdn(), StatusApiEnum.SECRET_KEY), Boolean.FALSE);
                                        Status status = statusRepository.findByCode(arg.getLockStatus(), Boolean.FALSE);
                                        traitementPourLaMiseAJourDesNumerosTrouve(arg, actionUtilisateurs, status, jsonNode, resultList, user, isTransfert);
                                    }
									message.set(arg.getMessage());
                            });
                            if (user != null && user.getProfil().getLibelle().equalsIgnoreCase("RESPONSABLE BACK-OFFICE")
                                    || user.getProfil().getLibelle().equalsIgnoreCase("UTILISATEUR BACK-OFFICE")){
                                List<SensitiveNumberDto> collect = lockUnLockFreezeDtos.getData().stream().filter(args -> {
                                    if (args.isFrozen() != Boolean.TRUE) {
                                        return true;
                                    }
                                    return false;
                                }).collect(Collectors.toList());
                                lockUnLockFreezeDtos.setData(collect);
                            }
                        }
                        lockUnLockFreezeDtos.setStatus(jsonNode.get("status") != null ? jsonNode.get("status").asText() : "408 sur " + strings);
                        lockUnLockFreezeDtos.setMessage(jsonNode.get("message") != null ? jsonNode.get("message").asText() : "Erreur lors du call api sur " + numbers);
                        lockUnLockFreezeDtos.setCode(jsonNode.get("code") != null ? jsonNode.get("code").asText() : "Erreur lors du call api");
                        lockUnLockFreezeDtos.setHasError(Boolean.FALSE);
                        return lockUnLockFreezeDtos;
                    }
                    lockUnLockFreezeDtos.setStatus(jsonNode.get("status") != null ? jsonNode.get("status").asText() : "408 sur " + strings);
                    lockUnLockFreezeDtos.setMessage(jsonNode.get("message") != null ? jsonNode.get("message").asText() : "Erreur lors du call api sur " + numbers);
                    lockUnLockFreezeDtos.setCode(jsonNode.get("code") != null ? jsonNode.get("code").asText() : "Erreur lors du call api");
                    lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
                    return lockUnLockFreezeDtos;
                }
            }
        } catch (IOException e) {
            slf4jLogger.info(e.getMessage());
            lockUnLockFreezeDtos.setCode("500");
            lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
            lockUnLockFreezeDtos.setMessage("une erreur est survenue lors du traitement " + e.getMessage());
            lockUnLockFreezeDtos.setStatus(e.getMessage());
            return lockUnLockFreezeDtos;
        }
        return lockUnLockFreezeDtos;
    }

    public LockUnLockFreezeDto getNumbers(String requestId, List<String> strings) {
        LockUnLockFreezeDto lockUnLockFreezeDtos = new LockUnLockFreezeDto();
        if (!Utilities.isNotEmpty(strings)) {
            lockUnLockFreezeDtos.setCode("500");
            lockUnLockFreezeDtos.setMessage("aucun numero renseigné");
            lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
            return lockUnLockFreezeDtos;
        }
        String numbers = extractedNumbers(strings);
        slf4jLogger.info("-----------------------------------Numbers------------------------------------------ " + numbers);
        slf4jLogger.info("----------------URI-------------" + paramsUtils.getSimswapBaseUriv2() + "find-by-msisdn?" + numbers + "&appId=" + paramsUtils.getAppId() + "&requestId=" + requestId);
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(paramsUtils.getSimswapBaseUriv2() + "find-by-msisdn?" + numbers + "&appId=" + paramsUtils.getAppId() + "&requestId=" + requestId)
                    .method("GET", null)
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseData = responseBody.string();
                    slf4jLogger.info("-----------------------------------STRING------------------------------------------ " + responseData);
                    JsonNode jsonNode = mapper.readTree(responseData);
                    slf4jLogger.info("---------------------------------JSONNODE-------------------------------------------- " + jsonNode);
                    if (jsonNode != null && jsonNode.get("status").asInt() == 200) {
                        lockUnLockFreezeDtos = mapper.treeToValue(jsonNode, LockUnLockFreezeDto.class);
                        slf4jLogger.info("---------------------------------DESERIALISATION-------------------------------------------- " + lockUnLockFreezeDtos);
                        lockUnLockFreezeDtos.setStatus(jsonNode.get("status") != null ? jsonNode.get("status").asText() : "408 sur " + strings);
                        lockUnLockFreezeDtos.setMessage(jsonNode.get("message") != null ? jsonNode.get("message").asText() : "Erreur lors du call api sur " + numbers);
                        lockUnLockFreezeDtos.setCode(jsonNode.get("code") != null ? jsonNode.get("code").asText() : "Erreur lors du call api");
                        lockUnLockFreezeDtos.setHasError(Boolean.FALSE);
                        lockUnLockFreezeDtos.setNumbers(strings);
                        return lockUnLockFreezeDtos;
                    }
                    lockUnLockFreezeDtos.setStatus(jsonNode.get("status") != null ? jsonNode.get("status").asText() : "408 sur " + strings);
                    lockUnLockFreezeDtos.setMessage(jsonNode.get("message") != null ? jsonNode.get("message").asText() : "Erreur lors du call api sur " + numbers);
                    lockUnLockFreezeDtos.setCode(jsonNode.get("code") != null ? jsonNode.get("code").asText() : "Erreur lors du call api");
                    lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
                    lockUnLockFreezeDtos.setNumbers(strings);
                    return lockUnLockFreezeDtos;
                }
            }
        } catch (IOException e) {
            slf4jLogger.info(e.getMessage());
            lockUnLockFreezeDtos.setCode("500");
            lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
            lockUnLockFreezeDtos.setNumbers(strings);
            lockUnLockFreezeDtos.setMessage("une erreur est survenue lors du traitement ");
            lockUnLockFreezeDtos.setStatus(e.getMessage());
            return lockUnLockFreezeDtos;
        }
        return lockUnLockFreezeDtos;
    }


    @SneakyThrows
    public List<Result> msisdnForRefraiche(String requestId, List<String> strings, User user) {
        if (!Utilities.isNotEmpty(strings)) {
            throw new BadHttpRequest();
        }
        List<Result> resultList = new ArrayList<>();
        LockUnLockFreezeDto lockUnLockFreezeDtos = new LockUnLockFreezeDto();
        TypeNumero typeNumero = typeNumeroRepository.findByLibelle(StatusApiEnum.CORAIL, Boolean.FALSE);
        Validate.EntityNotExiste(typeNumero);
        String numbers = extractedNumbers(strings);
        slf4jLogger.info("-----------------------------------Numbers------------------------------------------ " + numbers);
        slf4jLogger.info("----------------URI-------------" + paramsUtils.getSimswapBaseUriv2() + "find-by-msisdn?" + numbers + "&appId=" + paramsUtils.getAppId() + "&requestId=" + requestId);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(paramsUtils.getSimswapBaseUriv2() + "find-by-msisdn?" + numbers + "&appId=" + paramsUtils.getAppId() + "&requestId=" + requestId)
                .method("GET", null)
                .addHeader("Accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        if (response != null) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseData = responseBody.string();
                slf4jLogger.info("-----------------------------------STRING------------------------------------------ " + responseData);
                JsonNode jsonNode = mapper.readTree(responseData);
                slf4jLogger.info("---------------------------------JSONNODE-------------------------------------------- " + jsonNode);
                if (jsonNode != null && jsonNode.get("status").asInt() == 200) {
                    lockUnLockFreezeDtos = mapper.treeToValue(jsonNode, LockUnLockFreezeDto.class);
                    slf4jLogger.info("---------------------------------DESERIALISATION-------------------------------------------- " + lockUnLockFreezeDtos);
                    if (lockUnLockFreezeDtos != null && Utilities.isNotEmpty(lockUnLockFreezeDtos.getData())) {
                        lockUnLockFreezeDtos.getData().stream().forEach(arg -> {
                            if (arg.getMessage() == null && arg.getPortNumber() > 0) {
                                List<ActionUtilisateur> actionUtilisateurs = new ArrayList<>();
                                if(user != null && user.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.ADMIN_CODE)){
                                    Category category = getCategory(arg.getOfferName());
                                    actionUtilisateurs = actionUtilisateurRepository.findByDepartement(typeNumero.getId(),category.getId(), AES.encrypt(arg.getMsisdn(), StatusApiEnum.SECRET_KEY), Boolean.FALSE);
                                }
                                if (user != null && !user.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.ADMIN_CODE)){
                                    actionUtilisateurs = actionUtilisateurRepository.findByDepartement(typeNumero.getId(),user.getCategory().getId(), AES.encrypt(arg.getMsisdn(), StatusApiEnum.SECRET_KEY), Boolean.FALSE);
                                }
                                Status status = statusRepository.findByCode(arg.getLockStatus(), Boolean.FALSE);
                                traitementPourLaMiseAJourDesNumerosTrouve(arg, actionUtilisateurs, status, jsonNode, resultList, user, Boolean.FALSE);
                            }
                        });
                    }
                    return resultList;
                }
                lockUnLockFreezeDtos.setStatus(jsonNode.get("status") != null ? jsonNode.get("status").asText() : "408");
                lockUnLockFreezeDtos.setMessage(jsonNode.get("message") != null ? jsonNode.get("message").asText() : "Erreur lors du call api");
                lockUnLockFreezeDtos.setCode(jsonNode.get("code") != null ? jsonNode.get("code").asText() : "Erreur lors du call api");
            }
        }
        return resultList;
    }

    private void traitementPourLaMiseAJourDesNumerosTrouve(SensitiveNumberDto arg, List<ActionUtilisateur> actionUtilisateurs, Status status, JsonNode jsonNode, List<Result> resultList, User user, Boolean isTransfert) {
        slf4jLogger.info("data from API {}", arg);
        List<Result> resultList1 = new ArrayList<>();
        if (Utilities.isNotEmpty(actionUtilisateurs)) {
            actionUtilisateurs.stream().skip(1).collect(Collectors.toList()).stream().forEach(args->{
                args.setIsDeleted(Boolean.TRUE);
                actionUtilisateurRepository.save(args);
            });
            ActionUtilisateur actionUserToSave = actionUtilisateurs.get(0);
            slf4jLogger.info("va status, {}", status);
            actionUserToSave.setStatusBscs(arg.getStatus());
            actionUserToSave.setOfferName(arg.getOfferName());
            actionUserToSave.setSerialNumber(arg.getSerialNumber());
            actionUserToSave.setContractId(String.valueOf(arg.getContractId()));
            actionUserToSave.setFromBss(Boolean.TRUE);
            actionUserToSave.setTmCode(String.valueOf(arg.getTariffModelCode()));
            actionUserToSave.setPortNumber(String.valueOf(arg.getPortNumber()));
            actionUserToSave.setActivationDate(arg.getActivationDate() != null ? Utilities.formatDateAnyFormat(arg.getActivationDate(), "yyyy-MM-dd HH:mm:ss") : null);
            actionUserToSave.setStatus(status);
            actionUserToSave.setIsMachine(arg.isFrozen());
            actionUserToSave.setIsDeleted(Boolean.FALSE);
            actionUserToSave.setUpdatedAt(Utilities.getCurrentDate());
            actionUserToSave.setUser(user);
            if (user.getCategory() != null && user.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.ADMIN_CODE) && isTransfert == Boolean.FALSE){
                if (arg.getOfferName().equalsIgnoreCase("Orange money PDV") || arg.getOfferName().equalsIgnoreCase("ORANGE ZEBRA STK")) {
                    slf4jLogger.info("EST OMCI {} " + arg.getOfferName());
                    actionUserToSave.setCategory(objectUtilities.categoriePair().getCategorieOmci());
                    actionUserToSave.setEmpreinte(
                            Utilities.empreinte(objectUtilities.categoriePair().getCategorieOmci().getCode(), user != null ? user.getId() : null, Utilities.getCurrentDateTime(), arg.getMsisdn()));
                } else {
                    actionUserToSave.setEmpreinte(
                            Utilities.empreinte(objectUtilities.categoriePair().getCategorieTelco().getCode(), user != null ? user.getId() : null, Utilities.getCurrentDateTime(), arg.getMsisdn()));
                    slf4jLogger.info("EST TELCO {} " + arg.getOfferName(), objectUtilities.categoriePair().getCategorieTelco());
                    actionUserToSave.setCategory(objectUtilities.categoriePair().getCategorieTelco());
                }
            }
            if (isTransfert == Boolean.TRUE && actionUserToSave.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.OMCI_CODE)){
                actionUserToSave.setCategory(objectUtilities.categoriePair().getCategorieTelco());
                actionUserToSave.setEmpreinte(
                        Utilities.empreinte(objectUtilities.categoriePair().getCategorieOmci().getCode(), user != null ? user.getId() : null, Utilities.getCurrentDateTime(), arg.getMsisdn()));
            }
            if (isTransfert == Boolean.TRUE && actionUserToSave.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.TELCO_CODE)){
                actionUserToSave.setCategory(objectUtilities.categoriePair().getCategorieOmci());
                actionUserToSave.setEmpreinte(
                        Utilities.empreinte(objectUtilities.categoriePair().getCategorieTelco().getCode(), user != null ? user.getId() : null, Utilities.getCurrentDateTime(), arg.getMsisdn()));
                slf4jLogger.info("EST TELCO {} " + arg.getOfferName(), objectUtilities.categoriePair().getCategorieTelco());
            }
            slf4jLogger.info("va raffraichir, {}", actionUtilisateurs);
            actionUtilisateurRepository.save(actionUserToSave);
            Result map = new Result();
            map.setReason(jsonNode.get("message") != null ? jsonNode.get("message").asText() : "Erreur lors du call api");
            map.setNumero(arg.getMsisdn());
            map.setOfferName(arg.getOfferName());
            map.setSerialNumber(arg.getSerialNumber());
            map.setContractId(String.valueOf(arg.getContractId()));
            map.setTariffModelCode(String.valueOf(arg.getTariffModelCode()));
            map.setPortNumber(String.valueOf(arg.getPortNumber()));
            map.setLockStatus(arg.getLockStatus());
            map.setStatus(arg.getStatus());
            map.setActivationDate(arg.getActivationDate());
            map.setFrozen(arg.isFrozen());
            map.setLogin(user != null ? user.getLogin() : "AUCUN LOGIN");
            resultList1.add(map);
        }else{
            ActionUtilisateur actionUserToSave = new ActionUtilisateur();
            actionUserToSave.setStatusBscs(arg.getStatus());
            actionUserToSave.setOfferName(arg.getOfferName());
            actionUserToSave.setSerialNumber(arg.getSerialNumber());
            actionUserToSave.setContractId(String.valueOf(arg.getContractId()));
            actionUserToSave.setUser(user);
            actionUserToSave.setCreatedAt(Utilities.getCurrentDate());
            actionUserToSave.setUpdatedAt(Utilities.getCurrentDate());
            actionUserToSave.setFromBss(Boolean.TRUE);
            actionUserToSave.setTypeNumero(typeNumeroRepository.findByLibelle(StatusApiEnum.CORAIL, Boolean.FALSE));
            actionUserToSave.setProfil(user != null ? user.getProfil() : null);
            actionUserToSave.setCreatedBy(user != null ? user.getId() : null);
            actionUserToSave.setTmCode(String.valueOf(arg.getTariffModelCode()));
            actionUserToSave.setPortNumber(String.valueOf(arg.getPortNumber()));
            actionUserToSave.setActivationDate(arg.getActivationDate() != null ? Utilities.formatDateAnyFormat(arg.getActivationDate(), "yyyy-MM-dd HH:mm:ss") : null);
            actionUserToSave.setStatus(status);
            actionUserToSave.setNumero(AES.encrypt(arg.getMsisdn(), StatusApiEnum.SECRET_KEY));
            actionUserToSave.setIsMachine(arg.isFrozen());
            actionUserToSave.setIsDeleted(Boolean.FALSE);
            if (user.getCategory().getCode().equalsIgnoreCase(StatusApiEnum.ADMIN_CODE)){
                slf4jLogger.info("EST ADMIN");
                if (arg.getOfferName().equalsIgnoreCase("Orange money PDV") || arg.getOfferName().equalsIgnoreCase("ORANGE ZEBRA STK")) {
                    slf4jLogger.info("EST OMCI {} " + arg.getOfferName());
                    actionUserToSave.setCategory(objectUtilities.categoriePair().getCategorieOmci());
                    actionUserToSave.setEmpreinte(
                            Utilities.empreinte(objectUtilities.categoriePair().getCategorieOmci().getCode(), user != null ? user.getId() : null, Utilities.getCurrentDateTime(), arg.getMsisdn()));
                } else {
                    actionUserToSave.setEmpreinte(
                            Utilities.empreinte(objectUtilities.categoriePair().getCategorieTelco().getCode(), user != null ? user.getId() : null, Utilities.getCurrentDateTime(), arg.getMsisdn()));
                    slf4jLogger.info("EST TELCO {} " + arg.getOfferName(), objectUtilities.categoriePair().getCategorieTelco());
                    actionUserToSave.setCategory(objectUtilities.categoriePair().getCategorieTelco());
                }
            }else {
                actionUserToSave.setCategory(user.getCategory());
            }
            actionUtilisateurRepository.save(actionUserToSave);
            Result map = new Result();
            map.setReason(jsonNode.get("message") != null ? jsonNode.get("message").asText() : "Erreur lors du call api");
            map.setNumero(arg.getMsisdn());
            map.setOfferName(arg.getOfferName());
            map.setSerialNumber(arg.getSerialNumber());
            map.setContractId(String.valueOf(arg.getContractId()));
            map.setTariffModelCode(String.valueOf(arg.getTariffModelCode()));
            map.setPortNumber(String.valueOf(arg.getPortNumber()));
            map.setLockStatus(arg.getLockStatus());
            map.setStatus(arg.getStatus());
            map.setActivationDate(arg.getActivationDate());
            map.setFrozen(arg.isFrozen());
            map.setLogin(user != null ? user.getLogin() : "AUCUN LOGIN");
            resultList1.add(map);
        }
        if (Utilities.isNotEmpty(resultList1)){
            resultList.addAll(resultList1);
        }
    }

    private static String extractedNumbers(List<String> numbers) {
        String result = numbers.stream()
                .map(number -> "msisdns=" + number)
                .collect(Collectors.joining("&"));
        return result;
    }

    public LockUnLockFreezeDtos freezeNumber(List<String> strings, String requestId) {
        LockUnLockFreezeDtos lockUnLockFreezeDtos = new LockUnLockFreezeDtos();
        String jsonString = getString(strings);
        if (jsonString == null || !Utilities.isNotEmpty(strings)) {
            lockUnLockFreezeDtos.setCode("400");
            lockUnLockFreezeDtos.setMessage("aucun numero renseigné");
            lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
            return lockUnLockFreezeDtos;
        }
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonString);
            Request request = new Request.Builder()
                    .url(paramsUtils.getSimswapBaseUriv2() + ":msisdn/freeze?appId=" + paramsUtils.getAppId() + "&requestId=" + requestId)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseData = responseBody.string();
                    slf4jLogger.info("-----------------------------------STRING------------------------------------------ " + responseData);
                    JsonNode jsonNode = mapper.readTree(responseData);
                    slf4jLogger.info("---------------------------------JSONNODE-------------------------------------------- " + jsonNode);
                    if (jsonNode != null && jsonNode.get("status").asInt() == 200) {
                        lockUnLockFreezeDtos = mapper.treeToValue(jsonNode, LockUnLockFreezeDtos.class);
                        lockUnLockFreezeDtos.setHasError(Boolean.FALSE);
                        slf4jLogger.info("---------------------------------DESERIALISATION-------------------------------------------- " + lockUnLockFreezeDtos);
						lockUnLockFreezeDtos.setHasError(Boolean.FALSE);
						lockUnLockFreezeDtos.setStatus(jsonNode.get("status") != null ? jsonNode.get("status").asText() : "408 sur " + strings);
						lockUnLockFreezeDtos.setMessage(jsonNode.get("message") != null ? jsonNode.get("message").asText() : "Erreur lors du call api sur " + strings);
						lockUnLockFreezeDtos.setCode(jsonNode.get("code") != null ? jsonNode.get("code").asText() : "Erreur lors du call api");

						return lockUnLockFreezeDtos;
                    }
					lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
					lockUnLockFreezeDtos.setStatus(jsonNode.get("status") != null ? jsonNode.get("status").asText() : "408 sur " + strings);
					lockUnLockFreezeDtos.setMessage(jsonNode.get("message") != null ? jsonNode.get("message").asText() : "Erreur lors du call api sur " + strings);
					lockUnLockFreezeDtos.setCode(jsonNode.get("code") != null ? jsonNode.get("code").asText() : "Erreur lors du call api");
                    slf4jLogger.info("retour du formatage en cas d'erreur {}", lockUnLockFreezeDtos);
                }
            }
        } catch (IOException e) {
            slf4jLogger.info(e.getMessage());
            lockUnLockFreezeDtos.setCode("500");
            lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
            lockUnLockFreezeDtos.setStatus(e.getMessage() + " sur " + jsonString);
            lockUnLockFreezeDtos.setMessage("une erreur est survenue lors du traitement " + jsonString);
            return lockUnLockFreezeDtos;
        }

        return lockUnLockFreezeDtos;
    }

    public LockUnLockFreezeDtos lockNumber(List<String> strings, String requestId) {
        LockUnLockFreezeDtos lockUnLockFreezeDtos = new LockUnLockFreezeDtos();
        String jsonString = getString(strings);
        if (jsonString == null || !Utilities.isNotEmpty(strings)) {
            lockUnLockFreezeDtos.setCode("400");
            lockUnLockFreezeDtos.setMessage("aucun numero renseigné");
            return lockUnLockFreezeDtos;
        }
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonString);
            Request request = new Request.Builder()
                    .url(paramsUtils.getSimswapBaseUriv2() + ":msisdn/lock?appId=" + paramsUtils.getAppId() + "&requestId=" + requestId)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseData = responseBody.string();
                    slf4jLogger.info("-----------------------------------STRING------------------------------------------ " + responseData);
                    JsonNode jsonNode = mapper.readTree(responseData);
                    slf4jLogger.info("---------------------------------JSONNODE-------------------------------------------- " + jsonNode);
                    if (jsonNode != null && jsonNode.get("status").asInt() == 200) {
                        lockUnLockFreezeDtos = mapper.treeToValue(jsonNode, LockUnLockFreezeDtos.class);
                        slf4jLogger.info("---------------------------------DESERIALISATION-------------------------------------------- " + lockUnLockFreezeDtos);
                        lockUnLockFreezeDtos.setHasError(Boolean.FALSE);
                        lockUnLockFreezeDtos.setNumbers(strings);
                        return lockUnLockFreezeDtos;
                    }
					lockUnLockFreezeDtos.setStatus(jsonNode.get("status") != null ? jsonNode.get("status").asText() : "408 sur " + strings);
					lockUnLockFreezeDtos.setMessage(jsonNode.get("message") != null ? jsonNode.get("message").asText()  : "une erreur est survenue lors du traitement ");
					lockUnLockFreezeDtos.setCode(jsonNode.get("code") != null ? jsonNode.get("code").asText() : "Erreur lors du call api");
                    lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
                    lockUnLockFreezeDtos.setNumbers(strings);
                    slf4jLogger.info("retour du formatage en cas d'erreur {}", lockUnLockFreezeDtos);
                    return  lockUnLockFreezeDtos;
                }
            }
        } catch (IOException e) {
            slf4jLogger.info(e.getMessage());
            lockUnLockFreezeDtos.setCode("500");
            lockUnLockFreezeDtos.setStatus(e.getMessage());
            lockUnLockFreezeDtos.setMessage("une erreur "+ e.getMessage()+" est survenue lors du traitement ");
            lockUnLockFreezeDtos.setNumbers(strings);
            lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
            return lockUnLockFreezeDtos;
        }

        return lockUnLockFreezeDtos;
    }

    public LockUnLockFreezeDtos unlockNumber(List<String> strings, String requestId) {
		LockUnLockFreezeDtos lockUnLockFreezeDtos = new LockUnLockFreezeDtos();
        String jsonString = getString(strings);
        if (jsonString == null || !Utilities.isNotEmpty(strings)) {
            lockUnLockFreezeDtos.setCode("400");
            lockUnLockFreezeDtos.setMessage("aucun numero renseigné");
            return lockUnLockFreezeDtos;
        }
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonString);
            Request request = new Request.Builder()
                    .url(paramsUtils.getSimswapBaseUriv2() + ":msisdn/unlock?appId=" + paramsUtils.getAppId() + "&requestId=" + requestId)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseData = responseBody.string();
                    slf4jLogger.info("-----------------------------------STRING------------------------------------------ " + responseData);
                    JsonNode jsonNode = mapper.readTree(responseData);
                    slf4jLogger.info("---------------------------------JSONNODE-------------------------------------------- " + jsonNode);
                    if (jsonNode != null && jsonNode.get("status").asInt() == 200) {
                        lockUnLockFreezeDtos = mapper.treeToValue(jsonNode, LockUnLockFreezeDtos.class);
                        slf4jLogger.info("---------------------------------DESERIALISATION-------------------------------------------- " + lockUnLockFreezeDtos);
                        lockUnLockFreezeDtos.setHasError(Boolean.FALSE);
                        return lockUnLockFreezeDtos;
                    }
                    lockUnLockFreezeDtos.setStatus(jsonNode.get("status") != null ? jsonNode.get("status").asText(): "408 sur " + jsonString);
                    lockUnLockFreezeDtos.setMessage(jsonNode.get("message") != null ? jsonNode.get("message").asText() : "Erreur lors du call api sur " + jsonString);
                    lockUnLockFreezeDtos.setCode(jsonNode.get("code") != null ? jsonNode.get("code").asText() : "Erreur lors du call api");
                    lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
                    slf4jLogger.info("retour du formatage en cas d'erreur {}", lockUnLockFreezeDtos);
                    return  lockUnLockFreezeDtos;

                }
            }
        } catch (IOException e) {
            slf4jLogger.info(e.getMessage());
            lockUnLockFreezeDtos.setCode("500");
            lockUnLockFreezeDtos.setMessage("une erreur est survenue lors du traitement " + jsonString);
            lockUnLockFreezeDtos.setStatus(e.getMessage() + " " + jsonString);
            lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
            return lockUnLockFreezeDtos;
        } catch (IllegalArgumentException e) {
            slf4jLogger.info(e.getMessage());
            lockUnLockFreezeDtos.setCode("500");
            lockUnLockFreezeDtos.setMessage("une erreur est survenue lors du traitement " + jsonString);
            lockUnLockFreezeDtos.setStatus(e.getMessage() + " " + jsonString);
            lockUnLockFreezeDtos.setHasError(Boolean.TRUE);
            return lockUnLockFreezeDtos;
        }
        return lockUnLockFreezeDtos;
    }

    private static String getString(List<String> strings) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(strings);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    private void refreshNumberOnCorail(_AppRequestDto _AppRequestDto, SensitiveNumberData unLockFreezeDto) {
        slf4jLogger.info("prêt a raffraichir {}", 1);
        if (unLockFreezeDto.getData() != null) {
            slf4jLogger.info("prêt a raffraichir {}", 2);
            User user = userRepository.findOne(_AppRequestDto.getUser(), Boolean.FALSE);
            TypeNumero typeNumero = typeNumeroRepository.findByLibelle(StatusApiEnum.CORAIL, Boolean.FALSE);
            Status status = statusRepository.findByCode(unLockFreezeDto.getData().getLockStatus(), Boolean.FALSE);
            List<ActionUtilisateur> actionUtilisateurs = actionUtilisateurRepository.findByOneNumero(typeNumero.getId(), user.getCategory() != null ? user.getCategory().getId() : null, unLockFreezeDto.getData() != null ? AES.encrypt(unLockFreezeDto.getData().getMsisdn(), StatusApiEnum.SECRET_KEY) : null, Boolean.FALSE);
            if (Utilities.isNotEmpty(actionUtilisateurs)) {
                slf4jLogger.info("prêt a raffraichir {}", 3);
                ActionUtilisateur actionUtilisateur = actionUtilisateurs.get(0);
                actionUtilisateur.setContractId(String.valueOf(unLockFreezeDto.getData().getContractId()));
                actionUtilisateur.setActivationDate(Utilities.formatDateAnyFormat(unLockFreezeDto.getData().getActivationDate(), "M/d/yyyy h:mm:ss a"));
                actionUtilisateur.setPortNumber(String.valueOf(unLockFreezeDto.getData().getPortNumber()));
                actionUtilisateur.setSerialNumber(unLockFreezeDto.getData().getSerialNumber());
                actionUtilisateur.setStatusBscs(unLockFreezeDto.getData().getLockStatus());
                actionUtilisateur.setIsMachine(unLockFreezeDto.getData().isFrozen());
                actionUtilisateur.setUpdatedAt(Utilities.getCurrentDate());
                actionUtilisateur.setUpdatedBy(_AppRequestDto.getUser());
                actionUtilisateur.setStatus(status);
                slf4jLogger.info(" a raffraichir {}", 4);
                actionUtilisateurRepository.save(actionUtilisateur);
            }
        }

    }

    private void refresh(_AppRequestDto _AppRequestDto, SensitiveNumberData unLockFreezeDto, ActionUtilisateur actionUtilisateurnew) {
        slf4jLogger.info("prêt a raffraichir {}", 1);
        if (unLockFreezeDto.getData() != null) {
            slf4jLogger.info("prêt a raffraichir {}", 2);
            Status status = statusRepository.findByCode(unLockFreezeDto.getData().getLockStatus(), Boolean.FALSE);
            List<ActionUtilisateur> actionUtilisateurs = actionUtilisateurRepository.findByNumeroListe(unLockFreezeDto.getData() != null ? AES.encrypt(unLockFreezeDto.getData().getMsisdn(), StatusApiEnum.SECRET_KEY) : null, Boolean.FALSE);
            if (Utilities.isNotEmpty(actionUtilisateurs)) {
                slf4jLogger.info("prêt a raffraichir {}", 3);
                actionUtilisateurs.stream().forEach(actionUtilisateur -> {
                    actionUtilisateur.setContractId(String.valueOf(unLockFreezeDto.getData().getContractId()));
                    actionUtilisateur.setActivationDate(Utilities.formatDateAnyFormat(unLockFreezeDto.getData().getActivationDate(), "yyyy-MM-dd HH:mm:ss"));
                    actionUtilisateur.setPortNumber(String.valueOf(unLockFreezeDto.getData().getPortNumber()));
                    actionUtilisateur.setSerialNumber(unLockFreezeDto.getData().getSerialNumber());
                    actionUtilisateur.setStatusBscs(unLockFreezeDto.getData().getLockStatus());
                    actionUtilisateur.setIsMachine(unLockFreezeDto.getData().isFrozen());
                    actionUtilisateur.setUpdatedAt(Utilities.getCurrentDate());
                    actionUtilisateur.setStatus(status);
                    slf4jLogger.info("a raffraichir {}", unLockFreezeDto.getData().getMsisdn());
                    actionUtilisateurRepository.save(actionUtilisateur);
                });
            }
            if (actionUtilisateurnew != null) {
                actionUtilisateurnew.setContractId(String.valueOf(unLockFreezeDto.getData().getContractId()));
                actionUtilisateurnew.setActivationDate(Utilities.formatDateAnyFormat(unLockFreezeDto.getData().getActivationDate(), "yyyy-MM-dd HH:mm:ss"));
                actionUtilisateurnew.setPortNumber(String.valueOf(unLockFreezeDto.getData().getPortNumber()));
                actionUtilisateurnew.setSerialNumber(unLockFreezeDto.getData().getSerialNumber());
                actionUtilisateurnew.setStatusBscs(unLockFreezeDto.getData().getLockStatus());
                actionUtilisateurnew.setIsMachine(unLockFreezeDto.getData().isFrozen());
                actionUtilisateurnew.setUpdatedAt(Utilities.getCurrentDate());
                actionUtilisateurnew.setStatus(status);
                slf4jLogger.info("a raffraichir {}", unLockFreezeDto.getData().getMsisdn());
                actionUtilisateurRepository.save(actionUtilisateurnew);
            }
        }

    }

    public ApiResponse<SensitiveNumberPackDto> getContracts(_AppRequestDto _AppRequestDto) {
        slf4jLogger.info("start method contract");
        ApiResponse<SensitiveNumberPackDto> apiResponse = new ApiResponse<>();
        String requestId = UUID.randomUUID().toString();
        TokenDto map = myToken();
        String token = map.getAccess_token() != null ? map.getAccess_token() : null;
        SensitiveNumberPackDto unLockFreezeDto = null;
        TokenDto tokenDto = myToken();
        if (tokenDto != null) {
            OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
            myBuilder = ignoreCertificateSSL(myBuilder);
            OkHttpClient client = myBuilder.build();

            Request request = new Request.Builder()
                    .url("https://192.168.31.195/gwbooster2/sim-swap-backend/v1/sensitive-numbers/?appId=" + appId + "&requestId=" + requestId + "&pageNumber=" + _AppRequestDto.getPageNumber() + "&pageSize=" + _AppRequestDto.getPageSize() + "&sensitiveNumberPack=" + _AppRequestDto.getSensitiveNumberPack())
                    .method("GET", null).addHeader("Authorization", "Bearer " + token).build();
            try {
                Response response = client.newCall(request).execute();
                if (response != null) {
                    slf4jLogger.info("Response code: " + response.code());
                    slf4jLogger.info("Response body: " + response.body());
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String responseData = responseBody.string();
                        slf4jLogger.info("----------------------------------------------------------------------------- " + responseData);

                        JsonNode jsonNode = mapper.readTree(responseData);
                        unLockFreezeDto = mapper.treeToValue(jsonNode, SensitiveNumberPackDto.class);
                        apiResponse.setData(unLockFreezeDto);
                        apiResponse.setStatus(unLockFreezeDto.getStatus() != 0 ? unLockFreezeDto.getStatus() : 0);

                        slf4jLogger.info("Response data: " + unLockFreezeDto);
                        return apiResponse;
                    }
                }
            } catch (IOException e) {
                slf4jLogger.error("Error during API call: " + e.getMessage());
            }
            apiResponse.setData(unLockFreezeDto);

        }
        slf4jLogger.info("end method contract");
        return apiResponse;
    }

    public static OkHttpClient.Builder ignoreCertificateSSL(OkHttpClient.Builder builder) {
        // LOGGER.warn("Ignore Ssl Certificate");
        try {
            /* Create a trust manager that does not validate certificate chains */
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};
            /* Install the all-trusting trust manager */
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            /* Create an ssl socket factory with our all-trusting manager */
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            // LOGGER.warn("Exception while configuring IgnoreSslCertificate" + e, e);
        }

        return builder;
    }

    public void sendSms(String content, AtionToNotifiable ationToNotifiable){
        TypeParametrage typeParametrage = typeParametrageRepository.findByCode("NOTIFICATION ALERTE SIMSWAP",
                Boolean.FALSE);
        List<Parametrage> parametrageList = parametrageRepository.findByIdTypeParametrage(typeParametrage.getId(),
                Boolean.FALSE);
        List<ParametrageProfil> parametrageProfils = null;
        if (Utilities.isNotEmpty(parametrageList)) {
            parametrageProfils = parametrageProfilRepository
                    .findByIdParametrage(parametrageList.get(0).getId(), Boolean.FALSE);
        }
        if (Utilities.isNotEmpty(parametrageProfils)) {
            parametrageProfils.forEach(arg -> {
                System.out.println("numero " + arg.getNumero() + "----------------- " + " messgae " + content);
                if (Utilities.notBlank(content) && Utilities.notBlank(arg.getNumero())) {
                    _AppRequestDto _AppRequest = _AppRequestDto.builder().message(content).phoneNumber(arg.getNumero()).build();
                    try {
                        sendSMS(_AppRequest);
                    } catch (BadHttpRequest e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
            Locale locale = new Locale(languageID, "");
            List<Map<String, String>> emailNotificationElementList = sendEmailList(parametrageProfils);
            sendEmail(emailNotificationElementList, locale, content, ationToNotifiable);
        }
    }

    public void sendEmail(String content, AtionToNotifiable ationToNotifiable) throws IOException {
        TypeParametrage typeParametrage = typeParametrageRepository.findByCode("NOTIFICATION ALERTE SIMSWAP",
                Boolean.FALSE);
        List<Parametrage> parametrageList = parametrageRepository.findByIdTypeParametrage(typeParametrage.getId(),
                Boolean.FALSE);
        List<ParametrageProfil> parametrageProfils = null;
        if (Utilities.isNotEmpty(parametrageList)) {
            parametrageProfils = parametrageProfilRepository
                    .findByIdParametrage(parametrageList.get(0).getId(), Boolean.FALSE);
        }
        if (Utilities.isNotEmpty(parametrageProfils)) {
            String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
            Locale locale = new Locale(languageID, "");
            List<Map<String, String>> emailNotificationElementList = sendEmailList(parametrageProfils);
            sendEmail(emailNotificationElementList, locale, content, ationToNotifiable);
        }
    }

    public List<Map<String, String>> sendEmailList(List<ParametrageProfil> parametrageProfils) {
        List<Map<String, String>> toRecipients = parametrageProfils.stream().map(arg -> {
            Map<String, String> recipient;
            recipient = new HashMap<>();
            recipient.put("email", arg.getEmail());
            recipient.put("user", arg.getNomPrenom());
            return recipient;
        }).collect(Collectors.toList());
        return toRecipients;
    }

    @SneakyThrows
    public void sendEmail(List<Map<String, String>> toRecipients, Locale locale, String body, AtionToNotifiable ationToNotifiable) {

        Map<String, String> from = new HashMap<>();
        from.put("email", paramsUtils.getSmtpMailAdresse());
        from.put("user", paramsUtils.getSmtpLogin());
        String subject = "NOTIFICATION SIMSWAP!";
        context = new Context();
        context.setVariable("entete", ENTETE);
        context.setVariable("titre", TITRE);
        context.setVariable("login", paramsUtils.getSmtpMailAdresse());
        context.setVariable("content", body);
        context.setVariable("appLink", paramsUtils.getUriLoginSimswap());
        String template = "alerte_format";
        hostingUtils.sendEmail(from, toRecipients, subject, body, null, context,
                template, locale, ationToNotifiable);
    }

    public Category getCategory(String offre){
        if (offre.equalsIgnoreCase("Orange money PDV") || offre.equalsIgnoreCase("ORANGE ZEBRA STK")) {
            slf4jLogger.info("EST OMCI {} " + offre);
            return objectUtilities.categoriePair().getCategorieOmci();
        } else {
            slf4jLogger.info("EST TELCO {} " + offre);
            return objectUtilities.categoriePair().getCategorieTelco();
        }
    }

}
