

/*
 * Java controller for entity table user 
 * Created on 2022-10-04 ( Time 11:23:37 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package ci.smile.simswaporange.rest.api;

import lombok.extern.java.Log;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Locale;
import java.util.Map;

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.enums.FunctionalityEnum;
import ci.smile.simswaporange.business.*;
import ci.smile.simswaporange.rest.fact.ControllerFactory;

/**
Controller for table "user"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/user")
public class UserController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<UserDto> controllerFactory;
	@Autowired
	private UserBusiness userBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UserDto> create(@RequestBody Request<UserDto> request) {
    	log.info("start method /user/create");
        Response<UserDto> response = controllerFactory.create(userBusiness, request, FunctionalityEnum.CREATE_USER);
		log.info("end method /user/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UserDto> update(@RequestBody Request<UserDto> request) {
    	log.info("start method /user/update");
        Response<UserDto> response = controllerFactory.update(userBusiness, request, FunctionalityEnum.UPDATE_USER);
		log.info("end method /user/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UserDto> delete(@RequestBody Request<UserDto> request) {
    	log.info("start method /user/delete");
        Response<UserDto> response = controllerFactory.delete(userBusiness, request, FunctionalityEnum.DELETE_USER);
		log.info("end method /user/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UserDto> getByCriteria(@RequestBody Request<UserDto> request) {
    	log.info("start method /user/getByCriteria");
        Response<UserDto> response = controllerFactory.getByCriteria(userBusiness, request, FunctionalityEnum.VIEW_USER);
		log.info("end method /user/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<UserDto> custom(@RequestBody Request<UserDto> request) {
	  log.info("UserDto method /$/custom");
	  Response<UserDto> response = new Response<UserDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = userBusiness.custom(request, locale);
	   } else {
	    slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
	      response.getStatus().getMessage());
	    return response;
	   }
	   if (!response.isHasError()) {
	    slf4jLogger.info("end method custom");
	    slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
	   } else {
	    slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
	      response.getStatus().getMessage());
	   }
	  } catch (CannotCreateTransactionException e) {
	   exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
	  } catch (TransactionSystemException e) {
	   exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
	  } catch (RuntimeException e) {
	   exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
	  } catch (Exception e) {
	   exceptionUtils.EXCEPTION(response, locale, e);
	  }
	  slf4jLogger.info("end method /UserDto/custom");
	  return response;
	 }
	@RequestMapping(value = "/connexionLdap", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<UserDto> connexion(@RequestBody Request<UserDto> request) {
		log.info("UserDto method /connexion/connexion");
		Response<UserDto> response = new Response<UserDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try { 
			response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = userBusiness.connexion(request, locale);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
				return response;
			}
			if (!response.isHasError()) {
				slf4jLogger.info("end method connexion");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
			}
		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /UserDto/connexion");
		return response;
	}

	@RequestMapping(value = "/validateInscription", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<UserDto> validateInscription(@RequestBody Request<UserDto> request) {
		log.info("UserDto method /connexion/validateInscription");
		Response<UserDto> response = new Response<UserDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = userBusiness.validateInscription(request, locale);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
				return response;
			}
			if (!response.isHasError()) {
				slf4jLogger.info("end method validateInscription");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
			}
		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /UserDto/connexion");
		return response;
	}

	@RequestMapping(value = "/blockUser", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<UserDto> lock(@RequestBody Request<UserDto> request) {
		log.info("UserDto method /connexion/connexion");
		Response<UserDto> response = new Response<UserDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = userBusiness.lock(request, locale);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
				return response;
			}
			if (!response.isHasError()) {
				slf4jLogger.info("end method connexion");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
			}
		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /UserDto/connexion");
		return response;
	}

	@RequestMapping(value = "/unBlockUser", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<UserDto> unlock(@RequestBody Request<UserDto> request) {
		log.info("UserDto method /connexion/connexion");
		Response<UserDto> response = new Response<UserDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = userBusiness.unlock(request, locale);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
				return response;
			}
			if (!response.isHasError()) {
				slf4jLogger.info("end method connexion");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
			}
		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /UserDto/connexion");
		return response;
	}
	@RequestMapping(value = "/connexion", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<UserDto> connexionLdap(@RequestBody Request<UserDto> request) {
		log.info("UserDto method /connexion/connexion");
		Response<UserDto> response = new Response<>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = userBusiness.connexionLdap(request, locale);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
				return response;
			}
			if (!response.isHasError()) {
				slf4jLogger.info("end method connexion");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
			}
		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /UserDto/connexion");
		return response;
	}
	
	@RequestMapping(value = "/getUserSessionTTL", method = RequestMethod.POST, consumes = {"application/json"}, produces = {
	"application/json"})
	public Response<UserDto> getUserSessionTTL(@RequestBody Request<UserDto> request) throws Exception {
		log.info("start method /user/getUserSessionTTL");
		Response<UserDto> response   = new Response<UserDto>();
		String            languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale            locale     = new Locale(languageID, "");
		// response = Validate.validateObject(request, response, functionalError, locale);

		try {
			//response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = userBusiness.getUserSessionTTL(request, locale);
			} else {
				log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage()));
				return response;
			}
			if (!response.isHasError()) {
				log.info(String.format("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS));
			} else {
				log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage()));
			}
		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		log.info("end method getUserSessionTTL");
		return response;
	}
	
	@RequestMapping(value="/deleteKeys",method = RequestMethod.POST, consumes = {
	"application/json" },produces={"application/json"})
	public Response<Map<String, Object>> deleteKeys(@RequestBody Request<UserDto> request) {
		slf4jLogger.info("start method /user/deleteKeys");
		Response<Map<String, Object>> response = new Response<>();

		String languageID = (String)requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");

		try {
			response = userBusiness.deleteKeys(locale);

			if(!response.isHasError()){
				slf4jLogger.info("end method deleteKeys");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);  
			}else{
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
			}

		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /user/deleteKeys");
		return response;
	}
	
	@RequestMapping(value = "/getSessionUser", method = RequestMethod.POST, consumes = {
	"application/json" }, produces = { "application/json" })
	public Response<UserDto> getSessionUser(@RequestBody Request<UserDto> request) {
		log.info("start method /user/getSessionUser");
		Response<UserDto> response = new Response<UserDto>();

		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = userBusiness.getSessionUser(request, locale);
			} else {
				return response;
			}
		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		log.info("end method /user/getSessionUser");
		return response;
	}
	
	@RequestMapping(value="/getActiveSession",method=RequestMethod.GET,produces={"application/json"})
	public Response<UserDto> getActiveSession() {
		slf4jLogger.info("start method/user/getActiveSession");
		Response<UserDto> response = new Response<UserDto>();
		String languageID = (String)requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			if(!response.isHasError()){
				response = userBusiness.getActiveSession(locale);
			}else{
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
				return response;
			}
			if(!response.isHasError()){
				slf4jLogger.info("end method getActiveSession");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);  
			}else{
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
			}

		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /user/getActiveSession");
		return response;
	}
	
	@RequestMapping(value="/getPublicKey",method=RequestMethod.GET,produces={"application/json"})
	public Response<UserDto> getPublicKey() {
		slf4jLogger.info("start method /user/getPublicKey");
		Response<UserDto> response = new Response<UserDto>();

		String languageID = (String)requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");

		try {
			if(!response.isHasError()){
				response = userBusiness.getPublicKey(locale);
			}else{
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
				return response;
			}
			if(!response.isHasError()){
				slf4jLogger.info("end method getPublicKey");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);  
			}else{
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
			}

		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /user/getPublicKey");
		return response;
	}

	@RequestMapping(value = "/callAPI", method = RequestMethod.GET, produces = { "application/json" })
	public String callAPI() {
		slf4jLogger.info("start method callAPI");

		String rep = "KO";

		OkHttpClient.Builder myBuilder = new OkHttpClient.Builder();
		myBuilder = ignoreCertificateSSL(myBuilder);
		okhttp3.OkHttpClient client = myBuilder.build();

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "grant_type=client_credentials");
		okhttp3.Request request = new okhttp3.Request.Builder().url("https://192.168.31.195/gwbooster2/token").method("POST", body)
				.addHeader("Authorization",
						"Basic SFBpOHZzeUFWM3JsaDg5Q1Bha0loNzFETzB3YTo5V19ncnI0NjN6Y3Z5RWk2WTNQSUxTRWQyZWth")
				.addHeader("Content-Type", "application/x-www-form-urlencoded").build();
		try {
			okhttp3.Response response = client.newCall(request).execute();
			if (response != null) {
				ResponseBody db = response.body();
				if (db != null) {
					rep = db.string();
					slf4jLogger.info("rep : " + rep);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			slf4jLogger.info("message : " + e.getMessage());
			slf4jLogger.info("cause : " + e.getCause());
			e.printStackTrace();
		}

		slf4jLogger.info("end method callAPI");
		return rep;
	}

	public static OkHttpClient.Builder ignoreCertificateSSL(OkHttpClient.Builder builder) {
		// LOGGER.warn("Ignore Ssl Certificate");
		try {

			/* Create a trust manager that does not validate certificate chains */
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[] {};
				}
			} };

			/* Install the all-trusting trust manager */
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
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
}
