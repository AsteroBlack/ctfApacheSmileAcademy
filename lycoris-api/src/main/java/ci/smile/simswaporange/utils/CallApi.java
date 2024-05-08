package ci.smile.simswaporange.utils;
import ci.smile.simswaporange.utils.dto.SensitiveNumbersDto;
import okhttp3.ResponseBody;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.ActionUtilisateurDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.ToString;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Data
@ToString
@Component
public class CallApi {

    private SecretKey key;
    private final int KEY_SIZE = 128;
    private final int T_LEN = 128;
    private Cipher encryptionCipher;

    public Response<Map<String, Object>> ApiGetNumber(String restUrl, Request<ActionUtilisateurDto> req) throws IOException {
        OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
        OkHttpClient client = myBuilder.build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader("isEncrypte", "false")
                .url(restUrl)
                .build();
//        Call call = client.newCall(request);
//        okhttp3.Response response = call.execute();

        Response<Map<String, Object>> apiResponse = new Response<>();
        apiResponse.setHasError(true);
//        if (!response.isSuccessful() || response.body() == null) {
//            Status status = new Status();
//            status.setCode(response.code() + "");
//            status.setMessage(response.body() == null ? "L'appel de l'api a échoué." : response.message());
//            apiResponse.setHasError(true);
//            apiResponse.setStatus(status);
//        }
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        ResponseBody responseBody = response.body();
        File  getNumbersTest = new File(getClass().getClassLoader().getResource("getNumbersTest.json").getFile());
        Map<String, Object> results = objectMapper.readValue(getNumbersTest, Map.class);
        System.out.println(results);

        if (results != null) {

                Status status = new Status();
                String code = results.get("status").toString();
                String message =  results.get("message").toString();
                status.setCode(code);
                status.setMessage(message);
                apiResponse.setStatus(status);
                System.out.println("1111111111111111111111111111");
                System.out.println(status);

                Map<String, Object> data = (Map<String, Object>) results.get("data");
                Integer totalPages = (Integer) data.get("totalPages");
                Integer totalElements = (Integer) data.get("totalElements");
                apiResponse.setCount((long) (totalElements * totalPages));
//                SensitiveNumbersDto numbersDto = new SensitiveNumbersDto();

                List<Map<String, Object>> sensitiveNumbers = (List<Map<String, Object>>) data.get("sensitiveNumbers");
               /* Map<String,Object> sensitiveOb = new HashMap<>();
                for (Map<String, Object> numbersDto : sensitiveNumbers){
                     sensitiveOb.put("msisdn",numbersDto.get("msisdn"));
                     sensitiveOb.put("lockStatus",numbersDto.get("lockStatus"));
                     sensitiveOb.put("isFreezed",numbersDto.get("isFreezed"));
                     sensitiveOb.put("contractId",numbersDto.get("contractId"));
                     sensitiveOb.put("portNumber",numbersDto.get("portNumber"));
                     sensitiveOb.put("serialNumber",numbersDto.get("serialNumber"));
                     sensitiveOb.put("tariffModelCode",numbersDto.get("tariffModelCode"));
                     sensitiveOb.put("offerName",numbersDto.get("offerName"));
                     sensitiveOb.put("activationDate",numbersDto.get("activationDate"));
                     sensitiveOb.put("status",numbersDto.get("status"));
                }*/

                apiResponse.setItems(sensitiveNumbers);
//                apiResponse.setSensitiveNumbers(sensitiveNumbers);
                apiResponse.setHasError(false);

        } else {
            apiResponse.setHasError(true);
        }

//        response.close();
        return apiResponse;
    }

    public Response ApiGetUser(String login, String password) throws IOException {

//		System.out.println("Requesst reçu " + requestBody);
        Response res = null;
        String token = null;

//		OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
////        myBuilder = ignoreCertificateSSL(myBuilder);
//		OkHttpClient client = myBuilder.build();
////		okhttp3.RequestBody body = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), JsonBody);
//		okhttp3.Request request = new okhttp3.Request.Builder()
//				.addHeader("Content-Type", "application/json")
//				.addHeader("X-AUTH-TOKEN", token)
//				.url("https://"+ParamsUtils.simSwapServer+":"+ParamsUtils.simSwapPort+"restUrl").build();
//		okhttp3.Response response = client.newCall(request).execute();
        // .......................
//		if(response!=null) {
//			ResponseBody resp=response.body();
//			if(resp!=null) {
//				res=resp.string();
//			}
//		}
//		response.close();
        return res;
    }

    public Response ApiBlockNumber(String restUrl, int req) throws IOException {
        OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json;  charset=utf-8");
        OkHttpClient client = myBuilder.build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .header("X-Username", String.valueOf(req))
                .addHeader("Content-Type", "application/json")
                .addHeader("isEncrypte", "false")
                .post(RequestBody.create(mediaType, ""))
                .url(ParamsUtils.simSwapAddress + restUrl)
                .build();
//        Call call = client.newCall(request);
//        okhttp3.Response response = call.execute();
        Response<Map<String, Object>> apiResponse = new Response<>();
        apiResponse.setHasError(true);
//        if (!response.isSuccessful() || response.body() == null) {
//            Status status = new Status();
//            status.setCode(response.code() + "");
//            status.setMessage(response.body() == null ? "L'appel de l'api a échoué." : response.message());
//            apiResponse.setHasError(true);
//            apiResponse.setStatus(status);

            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            okhttp3.ResponseBody responseBody = response.body();
        File  actionNumberFile = new File(getClass().getClassLoader().getResource("actionNumber.json").getFile());
            Map<String, Object> results = objectMapper.readValue( actionNumberFile, Map.class);
            if (results != null) {
                if (results.containsKey("status") && results.containsValue(200)) {
                    Status status = new Status();
                    status.setCode(results.get("status").toString());
                    status.setMessage(results.get("message").toString());
                    apiResponse.setStatus(status);

                    Map<String, Object> data = (Map<String, Object>) results.get("data");
                    if (data.containsKey("state") && data.containsValue("Completed")) {
                        apiResponse.setState(data.get("state").toString());
                        apiResponse.setHasError(false);
                    }
                }else {
                    apiResponse.setHasError(true);
                }
            }


//        response.close();
        return apiResponse;

    }

    public Response DeblocageNumber(String restUrl, int req) throws IOException {
        OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json;  charset=utf-8");
        OkHttpClient client = myBuilder.build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .header("X-Username", String.valueOf(req))
                .addHeader("Content-Type", "application/json")
                .addHeader("isEncrypte", "false")
                .post(RequestBody.create(mediaType, ""))
                .url(ParamsUtils.simSwapAddress + restUrl)
                .build();
//        Call call = client.newCall(request);
//        okhttp3.Response response = call.execute();
        Response<Map<String, Object>> apiResponse = new Response<>();
        apiResponse.setHasError(true);
//        if (!response.isSuccessful() || response.body() == null) {
//            Status status = new Status();
//            status.setCode(response.code() + "");
//            status.setMessage(response.body() == null ? "L'appel de l'api a échoué." : response.message());
//            apiResponse.setHasError(true);
//            apiResponse.setStatus(status);
//        } else {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            okhttp3.ResponseBody responseBody = response.body();
            File  actionNumberFile = new File(getClass().getClassLoader().getResource("actionNumber.json").getFile());
            Map<String, Object> results = objectMapper.readValue(actionNumberFile, Map.class);
            if (results != null) {
                if (results.containsKey("status") && results.containsValue(200)) {
                    Status status = new Status();
                    status.setCode( results.get("status").toString());
                    status.setMessage( results.get("message").toString());
                    apiResponse.setStatus(status);

                    Map<String, Object> data = (Map<String, Object>) results.get("data");
                    if (data.containsKey("state") && data.containsValue("Completed")) {
                        apiResponse.setHasError(false);
                    }
                }
            }

//        response.close();
        return apiResponse;

    }

    public Response apiMiseEnMachine(String restUrl, int req) throws IOException {
        OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json;  charset=utf-8");
        OkHttpClient client = myBuilder.build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .header("X-Username", String.valueOf(req))
                .addHeader("Content-Type", "application/json")
                .addHeader("isEncrypte", "false")
                .post(RequestBody.create(mediaType, ""))
                .url(ParamsUtils.simSwapAddress + restUrl)
                .build();
//        Call call = client.newCall(request);
//        okhttp3.Response response = call.execute();
        Response<Map<String, Object>> apiResponse = new Response<>();
        apiResponse.setHasError(true);
//        if (!response.isSuccessful() || response.body() == null) {
//            Status status = new Status();
//            status.setCode(response.code() + "");
//            status.setMessage(response.body() == null ? "L'appel de l'api a échoué." : response.message());
//            apiResponse.setHasError(true);
//            apiResponse.setStatus(status);
//        } else {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            okhttp3.ResponseBody responseBody = response.body();
        File  actionNumberFile = new File(getClass().getClassLoader().getResource("actionNumber.json").getFile());
            Map<String, Object> results = objectMapper.readValue(actionNumberFile, Map.class);
            if (results != null) {
                if (results.containsKey("status") && results.containsValue(200)) {
                    Status status = new Status();
                    status.setCode( results.get("status").toString());
                    status.setMessage( results.get("message").toString());
                    apiResponse.setStatus(status);

                    Map<String, Object> data = (Map<String, Object>) results.get("data");
                    if (data.containsKey("state") && data.containsValue("Completed")) {
                        apiResponse.setHasError(false);
                    }
                }
            }

//        response.close();
        return apiResponse;


    }

    public String apiSuppressionNumero(List<String> number) throws IOException {
        System.out.println("Requesst reçu " + number);
        String res = "error Call API";
        String token = null;

//		String JsonBody=new Gson().toJson(number);
//		// avoid creating several instances, should be singleon
//		OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
////        myBuilder = ignoreCertificateSSL(myBuilder);
//		OkHttpClient client = myBuilder.build();
//		okhttp3.RequestBody body = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), JsonBody);
//		okhttp3.Request request = new okhttp3.Request.Builder()
//				.addHeader("Content-Type", "application/json")
//				.addHeader("X-AUTH-TOKEN", token)
//				.url("https://"+ParamsUtils.simSwapServer+":"+ParamsUtils.simSwapPort+"restUrl").post(body).build();
//		okhttp3.Response response = client.newCall(request).execute();
        // .......................
//		if(response!=null) {
//			ResponseBody resp=response.body();
//			if(resp!=null) {
//				res=resp.string();
//			}
//		}
//		response.close();
        return res;
    }


    public void init() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();
    }

    public String encryptAES(String message) throws Exception {
        byte[] messageInBytes = message.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public String decryptAES(String encryptedMessage) throws Exception {
        byte[] messageInBytes = decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public Map<String, Object> CallAPi(Map<String, Object> data, String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String queryString = new Gson().toJson(data);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), queryString);
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("POST", body).addHeader("Content-Type", "application/json").build();
        okhttp3.Response resp = client.newCall(request).execute();
        String outputString = resp.body().string();
        //slf4jLogger.info(outputString);
        Map<String, Object> map = new Gson().fromJson(outputString, new TypeToken<Map<String, Object>>() {
        }.getType());
        resp.close();
        return map;
    }

}
