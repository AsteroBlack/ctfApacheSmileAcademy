//package ci.smile.simswaporange.rest;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.Locale;
//import java.util.Map;
//import java.util.UUID;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponseWrapper;
//
//import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.MDC;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.env.Environment;
//import org.springframework.http.MediaType;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import ci.smile.simswaporange.dao.config.service.RedisService;
//import ci.smile.simswaporange.security.CopyPrintWriter;
//import ci.smile.simswaporange.security.CustomerCareUtils;
//import ci.smile.simswaporange.security.ManageRsa;
//import ci.smile.simswaporange.security.Rsa;
//import ci.smile.simswaporange.security.ManageAes;
//import ci.smile.simswaporange.utils.FunctionalError;
//import ci.smile.simswaporange.utils.HostingUtils;
//import ci.smile.simswaporange.utils.ParamsUtils;
//import ci.smile.simswaporange.utils.Status;
//import ci.smile.simswaporange.utils.Utilities;
//import ci.smile.simswaporange.utils.contract.Request;
//import ci.smile.simswaporange.utils.contract.Response;
//import ci.smile.simswaporange.utils.dto.UserDto;
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class EncryptFilter implements Filter {
//
//	private Logger slf4jLogger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	private FunctionalError functionalError;
//
//	@Autowired
//	private RedisService cacheUtils;
//
//	@Autowired
//	private ParamsUtils paramsUtils;
//
//	@Autowired
//	private Environment environment;
//
//	@Autowired
//	private HostingUtils hostingUtils;
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException, JSONException {
//		MDC.clear();
//		String key = "";
//		String requestValue = "";
//		String responseValue = "";
//		String resp = "";
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		boolean ignore = false;
//		String uri = req.getRequestURI().toString();
//		String sessionReq = Math.abs(UUID.randomUUID().getMostSignificantBits()) + "" + System.currentTimeMillis();
//		CurrentRequestId.set(sessionReq);
//		res.setHeader("Access-Control-Allow-Origin", "*");
//		res.setHeader("Access-Control-Allow-Credentials", "true");
//		res.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS");
//		res.setHeader("Access-Control-Allow-Headers",
//				"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,sessionUser,lang,user,isEncrypte,serviceLibelle,ndCode,customerCode");
//		String type = req.getHeader("Content-Type");
//		res.getHeader("Content-Type");
//		boolean isMultipart = type != null && type.startsWith("multipart/form-data");
//		// TODO Auto-generated method stub
//		if (environment.getActiveProfiles() != null && environment.getActiveProfiles().length > 0) {
//			String activeProfiles = "";
//			for (int i = 0; i < environment.getActiveProfiles().length; i++) {
//				activeProfiles += environment.getActiveProfiles()[i];
//			}
//			// Check if Active profiles contains profile to ignore
//			if (uri.contains("user/getPublicKey")) {
//				HttpServletRequestWritableWrapper requestWrapper = new HttpServletRequestWritableWrapper(req,
//						requestValue.getBytes());
//				HttpServletResponseReadableWrapper responseWrapper = new HttpServletResponseReadableWrapper(res);
//				chain.doFilter(requestWrapper, responseWrapper);
//				responseValue = responseWrapper.getResponseData();
//				resp = responseValue;
//			} else if (uri.contains("user/deconnexion")) {
//				resp = ExecuteRequestDeconnect(requestValue, responseValue, req, res, key, "", chain, "");
//			} else if (!req.getMethod().equals("OPTIONS")) {
//				Boolean isEncrypte = req.getHeader("isEncrypte") != null && !req.getHeader("isEncrypte").isEmpty()
//						? Boolean.valueOf(req.getHeader("isEncrypte"))
//						: null;
//				if (CustomerCareUtils.URI_AS_IGNORE.stream().anyMatch(s -> uri.contains(s))) {
//					chain.doFilter(request, response);
//					ignore = true;
//				} else if (isMultipart) {
//					resp = ExecuteRequestDisableSessionAndFilter(requestValue, responseValue, req, res, chain,
//							isMultipart);
//				} else {
//					String body = "";
//					body = ExtractRequest(request);
//					key = ExtractKey(request, body);
//					if (Utilities.notBlank(key) && paramsUtils.getAesSecretKey().equalsIgnoreCase(key) && body != null
//							&& !body.isEmpty()) {
////						String data = ExtractDataFromRsa(body);
////						if (uri.contains("user/connexion") || uri.contains("user/connexionLdap")) {
//							HttpServletRequestWritableWrapper requestWrapper = new HttpServletRequestWritableWrapper(
//									req, body.getBytes());
//							HttpServletResponseReadableWrapper responseWrapper = new HttpServletResponseReadableWrapper(
//									res);
//							chain.doFilter(requestWrapper, responseWrapper);
//							responseValue = responseWrapper.getResponseData();
//							resp = CompactData(responseValue, key);
////						}
//					} else {
//						resp = ReturnAccesDenied(req, res);
//					}
//				}
//			} else {
//				chain.doFilter(request, response);
//				ignore = true;
//			}
//			if (!ignore) {
//				res.setHeader("x-item", resp);
//				res.getWriter().write(resp);
//				res.getWriter().flush();
//			}
//		}
//	}
//
//	@Override
//	public void init(FilterConfig arg0) throws ServletException {
//		// TODO Auto-generated method stub
//
//	}
//
//	public String ExtractRequest(ServletRequest request) {
//		String requestBody = "";
//		try {
//			StringBuilder builder = new StringBuilder();
//			String aux = "";
//			while ((aux = request.getReader().readLine()) != null) {
//				builder.append(aux);
//			}
//			requestBody = builder.toString();
//		} catch (Exception e) {
//			slf4jLogger.warn(e.getMessage());
//		}
//		return requestBody;
//	}
//
//	public String ExtractData(ServletRequest request, String requestBody) {
//		String data = "";
//		try {
//			// String reqBody=ExtractRequest(request);
//			if (Utilities.notBlank(requestBody)) {
//				JSONObject jsonObject = new JSONObject(requestBody);
//				Object object = jsonObject.get("data");
//				if (object instanceof String) {
//					data = jsonObject.getString("data");
//				} else {
//					data = ManageRsa.encryptRsa(String.valueOf(jsonObject.get("data")));
//				}
//			}
//		} catch (Exception e) {
//			slf4jLogger.warn(e.getMessage());
//		}
//		return data;
//	}
//	
//	
//
//	public String ExtractKey(ServletRequest request, String requestBody) {
//		String data = "";
//		try {
//			// String reqBody=ExtractRequest(request);
//			if (Utilities.notBlank(requestBody)) {
//				JSONObject jsonObject = new JSONObject(requestBody);
//				Object object = jsonObject.get("key");
//				if (object instanceof String) {
//					data = jsonObject.getString("key");
//				}
//			}
//		} catch (Exception e) {
//			slf4jLogger.warn(e.getMessage());
//		}
//		return data;
//	}
//
//	public String ExtractDataFromRsa(String data) {
//		String decryptedRSA = "";
//		try {
//			decryptedRSA = ManageRsa.decryptRsa(data);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return decryptedRSA;
//	}
//
//	public String ExtractDataFromAes(String data, String key) {
//		String decryptedAes = "";
//		try {
//			ManageAes manageAes = new ManageAes();
//			decryptedAes = manageAes.decryptAes(data + "", key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return decryptedAes;
//	}
//
//	public String CompactData(String responseValue, String key) {
//		String data = "";
//		try {
//			ManageAes manageAes = new ManageAes();
//			String encrypt = manageAes.encryptAes(responseValue + "", key);
//			data = "{\"item\":\"" + encrypt + "\"}";
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return data;
//	}
//
//	public String ReturnAccesDenied(HttpServletRequest req, HttpServletResponse res) {
//		String responseValue = "";
//		try {
//			Response respObj = new Response();
//			respObj.setHasError(Boolean.TRUE);
//			String lang = req.getHeader("lang");
//			Locale locale = null;
//			if (lang != null) {
//				locale = new Locale(lang, "");
//			} else {
//				locale = new Locale("en", "");
//			}
//			respObj.setStatus(functionalError.ACCESS_DENIED("", locale));
//			responseValue = new Gson().toJson(respObj, Response.class);
//			res.setContentType(MediaType.APPLICATION_JSON_VALUE);
//			res.setHeader("plainData", Boolean.TRUE + "");
//			res.setHeader("Access-Control-Expose-Headers", "plainData");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return responseValue;
//	}
//
//	public String returnSessionExpired(HttpServletRequest req, HttpServletResponse res) {
//		String responseValue = "";
//		try {
//			Response respObj = new Response();
//			respObj.setHasError(Boolean.TRUE);
//			String lang = req.getHeader("lang");
//			Locale locale = null;
//			if (lang != null) {
//				locale = new Locale(lang, "");
//			} else {
//				locale = new Locale("en", "");
//			}
//			respObj.setStatus(functionalError.SESSION_EXPIRED("", locale));
//			responseValue = new Gson().toJson(respObj, Response.class);
//			res.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return responseValue;
//	}
//
//	public String ReturnDeconnectSession(HttpServletRequest req, HttpServletResponse res) {
//		String responseValue = "";
//		try {
//			Response respObj = new Response();
//			respObj.setHasError(Boolean.FALSE);
//			responseValue = new Gson().toJson(respObj, Response.class);
//			res.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return responseValue;
//	}
//
//	@SuppressWarnings("unused")
//	public String ExecuteRequest(String requestValue, String responseValue, HttpServletRequest req,
//			HttpServletResponse res, String key, String data, FilterChain chain, String body) {
//		String resp = "";
//		String serviceLibelle = "";
//		String ndCode = "";
//		String customerCode = "";
//		try {
//			String sessionUser = req.getHeader("sessionUser");
//			String externe = req.getHeader("externe");
//			if (sessionUser != null) {
//				UserDto peopleDto = null;
//				peopleDto = cacheUtils.getUser(sessionUser);
//				if (peopleDto != null) {
//					key = peopleDto.getKey();
//					if (externe != null && externe.equalsIgnoreCase("EXTERNE")) {
//					} else {
//						cacheUtils.saveUser(sessionUser, peopleDto, true);
//					}
//					if (data != null && !data.isEmpty()) {
//						requestValue = ExtractDataFromAes(data, key);
//						JSONObject json = new JSONObject(requestValue);
//						serviceLibelle = json.has("serviceLibelle") ? json.get("serviceLibelle").toString() : "";
//						ndCode = json.has("ndCode") ? json.get("ndCode").toString() : "";
//						customerCode = json.has("customerCode") ? json.get("customerCode").toString() : "";
//					} else {
//						ndCode = req.getHeader("ndCode");
//						serviceLibelle = req.getHeader("serviceLibelle");
//						customerCode = req.getHeader("customerCode");
//
//					}
//					HttpServletRequestWritableWrapper requestWrapper = new HttpServletRequestWritableWrapper(req,
//							requestValue.getBytes());
//
//					HttpServletResponseReadableWrapper responseWrapper = new HttpServletResponseReadableWrapper(res);
//
//					chain.doFilter(requestWrapper, responseWrapper);
//
//					responseValue = responseWrapper.getResponseData();
//
//					JSONObject jsonObject = new JSONObject(responseValue);
//					JSONObject status = jsonObject.has("status") ? (JSONObject) jsonObject.get("status")
//							: new JSONObject(new Status());
//					String code = "";
//					String message = "";
//					if (status != null && status.length() != 0) {
//						setMessageCode(status, code, message);
//					}
//					hostingUtils.LogResponseTransaction(CurrentRequestId.get(),
//							Utilities.getClientIp(req) /* req.getRemoteAddr() */,
//							req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//							req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//							req.getProtocol() != null ? req.getProtocol() : "", sessionUser, key,
//							req.getHeader("user") != null ? req.getHeader("user") + "" : "", peopleDto.getNom(),
//							peopleDto.getPrenom(), req.getRequestURI().toString(),
//							jsonObject.has("hasError") ? jsonObject.get("hasError").toString() : "", code, message,
//							requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//
//					resp = CompactData(responseValue, key);
//				} else {
//					String svg_session = CustomerCareUtils.VAR + sessionUser;
//					if (peopleDto != null) {
//						key = peopleDto.getKey();
//						if (data != null && !data.isEmpty()) {
//							requestValue = ExtractDataFromAes(data, key);
//							JSONObject json = new JSONObject(requestValue);
//							serviceLibelle = json.has("serviceLibelle") ? json.get("serviceLibelle").toString() : "";
//							ndCode = json.has("ndCode") ? json.get("ndCode").toString() : "";
//							customerCode = json.has("customerCode") ? json.get("customerCode").toString() : "";
//						}
//						responseValue = returnSessionExpired(req, res);
//						cacheUtils.deleteUser(svg_session);
//
//						JSONObject jsonObject = new JSONObject(responseValue);
//						JSONObject status = jsonObject.has("status") ? (JSONObject) jsonObject.get("status") : null;
//						String code = "";
//						String message = "";
//
//						if (status != null && status.length() != 0) {
//
//							setMessageCode(status, code, message);
//						}
//
//						hostingUtils.LogResponseTransaction(CurrentRequestId.get(),
//								Utilities.getClientIp(req) /* req.getRemoteAddr() */,
//								req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//								req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//								req.getProtocol() != null ? req.getProtocol() : "", sessionUser, key,
//								req.getHeader("user") != null ? req.getHeader("user") + "" : "", peopleDto.getNom(),
//								peopleDto.getPrenom(), req.getRequestURI().toString(),
//								jsonObject.has("hasError") ? jsonObject.get("hasError").toString() : "", code, message,
//								requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//						resp = CompactData(responseValue, key);
//
//					} else {
//						requestValue = body;
//						JSONObject json = new JSONObject(requestValue);
//						serviceLibelle = json.has("serviceLibelle") ? json.get("serviceLibelle").toString() : "";
//						ndCode = json.has("ndCode") ? json.get("ndCode").toString() : "";
//						customerCode = json.has("customerCode") ? json.get("customerCode").toString() : "";
//						responseValue = ReturnAccesDenied(req, res);
//						JSONObject jsonObject = new JSONObject(responseValue);
//						JSONObject status = jsonObject.has("status") ? (JSONObject) jsonObject.get("status") : null;
//						String code = "";
//						String message = "";
//
//						if (status != null && status.length() != 0) {
//							setMessageCode(status, code, message);
//						}
//
//						hostingUtils.LogResponseTransaction(CurrentRequestId.get(),
//								Utilities.getClientIp(req) /* req.getRemoteAddr() */,
//								req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//								req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//								req.getProtocol() != null ? req.getProtocol() : "", sessionUser, key,
//								req.getHeader("user") != null ? req.getHeader("user") + "" : "", "", "",
//								req.getRequestURI().toString(),
//								jsonObject.has("hasError") ? jsonObject.get("hasError").toString() : "", code, message,
//								requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//
//						resp = responseValue;
//					}
//				}
//			} else {
//				requestValue = body;
//				if (requestValue != null && !requestValue.isEmpty()) {
//					JSONObject json = new JSONObject(requestValue);
//					serviceLibelle = json.has("serviceLibelle") ? json.get("serviceLibelle").toString() : "";
//					ndCode = json.has("ndCode") ? json.get("ndCode").toString() : "";
//					customerCode = json.has("customerCode") ? json.get("customerCode").toString() : "";
//				} else {
//					ndCode = req.getHeader("ndCode");
//					serviceLibelle = req.getHeader("serviceLibelle");
//					customerCode = req.getHeader("customerCode");
//				}
//				responseValue = ReturnAccesDenied(req, res);
//				JSONObject jsonObject = new JSONObject(responseValue);
//				JSONObject status = (JSONObject) jsonObject.get("status");
//				String code = "";
//				String message = "";
//
//				if (status != null && status.length() != 0) {
//					setMessageCode(status, code, message);
//				}
//
//				hostingUtils.LogResponseTransaction(CurrentRequestId.get(),
//						Utilities.getClientIp(req) /* req.getRemoteAddr() */,
//						req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//						req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//						req.getProtocol() != null ? req.getProtocol() : "", sessionUser, key,
//						req.getHeader("user") != null ? req.getHeader("user") + "" : "", "", "",
//						req.getRequestURI().toString(),
//						jsonObject.has("hasError") ? jsonObject.get("hasError").toString() : "", code, message,
//						requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//
//				resp = responseValue;
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			slf4jLogger.warn(e.getMessage());
//		}
//		return resp;
//	}
//
//	@SuppressWarnings("unused")
//	public String ExecuteRequestDisable(String requestValue, String responseValue, HttpServletRequest req,
//			HttpServletResponse res, String key, String data, FilterChain chain, String body) {
//		String resp = "";
//		String serviceLibelle = "";
//		String ndCode = "";
//		String customerCode = "";
//		try {
//			if (data != null && !data.isEmpty()) {
//				key = "khqWPuvI+431q/0Ev1wVhzG3Po8Z0UIBlvM/fm6uGW0=";
//				requestValue = ExtractDataFromAes(data, key);
//				JSONObject json = new JSONObject(requestValue);
//				serviceLibelle = json.has("serviceLibelle") ? json.get("serviceLibelle").toString() : "";
//				ndCode = json.has("ndCode") ? json.get("ndCode").toString() : "";
//				customerCode = json.has("customerCode") ? json.get("customerCode").toString() : "";
//				HttpServletRequestWritableWrapper requestWrapper = new HttpServletRequestWritableWrapper(req,
//						requestValue.getBytes());
//
//				HttpServletResponseReadableWrapper responseWrapper = new HttpServletResponseReadableWrapper(res);
//
//				chain.doFilter(requestWrapper, responseWrapper);
//
//				responseValue = responseWrapper.getResponseData();
//
//				JSONObject jsonObject = new JSONObject(responseValue);
//				JSONObject status = jsonObject.has("status") ? (JSONObject) jsonObject.get("status") : null;
//				String code = "";
//				String message = "";
//
//				if (status != null && status.length() != 0) {
//
//					setMessageCode(status, code, message);
//				}
//
//				hostingUtils.LogResponseTransaction(CurrentRequestId.get(),
//						Utilities.getClientIp(req) /* req.getRemoteAddr() */,
//						req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//						req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//						req.getProtocol() != null ? req.getProtocol() : "", null, key,
//						req.getHeader("user") != null ? req.getHeader("user") + "" : "", null, null,
//						req.getRequestURI().toString(),
//						jsonObject.has("hasError") ? jsonObject.get("hasError").toString() : "", code, message,
//						requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//
//				resp = responseValue;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			slf4jLogger.warn(e.getMessage());
//		}
//		return resp;
//	}
//
//	@SuppressWarnings("unused")
//	public String ExecuteRequestDeconnect(String requestValue, String responseValue, HttpServletRequest req,
//			HttpServletResponse res, String key, String data, FilterChain chain, String body) {
//		String resp = "";
//		String serviceLibelle = "";
//		String ndCode = "";
//		String customerCode = "";
//		JSONObject json = new JSONObject(requestValue);
//		serviceLibelle = json.get("serviceLibelle").toString();
//		ndCode = json.get("ndCode").toString();
//		customerCode = json.get("customerCode").toString();
//		try {
//			String sessionUser = req.getHeader("sessionUser");
//			if (sessionUser != null) {
//				UserDto peopleDto = null;
//				peopleDto = cacheUtils.getUser(sessionUser);
//				if (peopleDto != null) {
//					key = peopleDto.getKey();
//					LogResponseTransaction(CurrentRequestId.get(), Utilities.getClientIp(req) /* req.getRemoteAddr() */,
//							req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//							req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//							req.getProtocol() != null ? req.getProtocol() : "", sessionUser, key,
//							req.getHeader("user") != null ? req.getHeader("user") + "" : "", peopleDto.getNom(),
//							peopleDto.getPrenom(), req.getRequestURI().toString(), "", "", "", requestValue,
//							responseValue, serviceLibelle, ndCode, customerCode);
//					peopleDto = null;
//					String svg_session = CustomerCareUtils.VAR + sessionUser;
//					if (peopleDto != null) {
//						cacheUtils.deleteUser(svg_session);
//					}
//					cacheUtils.saveUser(sessionUser, peopleDto, true);
//
//					responseValue = ReturnDeconnectSession(req, res);
//
//					JSONObject jsonObject = new JSONObject(responseValue);
//					JSONObject status = (JSONObject) jsonObject.get("status");
//					String code = "";
//					String message = "";
//
//					if (status != null && status.length() != 0) {
//						setMessageCode(status, code, message);
//					}
//
//					LogResponseTransaction(CurrentRequestId.get(), Utilities.getClientIp(req) /* req.getRemoteAddr() */,
//							req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//							req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//							req.getProtocol() != null ? req.getProtocol() : "", sessionUser, key,
//							req.getHeader("user") != null ? req.getHeader("user") + "" : "", "", "",
//							req.getRequestURI().toString(),
//							jsonObject.get("hasError").toString() != null ? jsonObject.get("hasError").toString() : "",
//							code, message, requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//
//					resp = responseValue;
//				} else {
//					requestValue = body;
//
//					LogResponseTransaction(CurrentRequestId.get(), Utilities.getClientIp(req) /* req.getRemoteAddr() */,
//							req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//							req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//							req.getProtocol() != null ? req.getProtocol() : "", sessionUser, key,
//							req.getHeader("user") != null ? req.getHeader("user") + "" : "", "", "",
//							req.getRequestURI().toString(), "", "", "", requestValue, responseValue, serviceLibelle,
//							ndCode, customerCode);
//
//					responseValue = ReturnAccesDenied(req, res);
//
//					JSONObject jsonObject = new JSONObject(responseValue);
//					JSONObject status = (JSONObject) jsonObject.get("status");
//					String code = "";
//					String message = "";
//
//					if (status != null && status.length() != 0) {
//						code = status.getString("code");
//						message = status.getString("message");
//					}
//
//					LogResponseTransaction(CurrentRequestId.get(), Utilities.getClientIp(req) /* req.getRemoteAddr() */,
//							req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//							req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//							req.getProtocol() != null ? req.getProtocol() : "", sessionUser, key,
//							req.getHeader("user") != null ? req.getHeader("user") + "" : "", "", "",
//							req.getRequestURI().toString(),
//							jsonObject.get("hasError").toString() != null ? jsonObject.get("hasError").toString() : "",
//							code, message, requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//
//					resp = responseValue;
//
//				}
//			} else {
//				requestValue = body;
//
//				LogResponseTransaction(CurrentRequestId.get(), Utilities.getClientIp(req) /* req.getRemoteAddr() */,
//						req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//						req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//						req.getProtocol() != null ? req.getProtocol() : "", sessionUser, key,
//						req.getHeader("user") != null ? req.getHeader("user") + "" : "", "", "",
//						req.getRequestURI().toString(), "", "", "", requestValue, responseValue, serviceLibelle, ndCode,
//						customerCode);
//
//				responseValue = ReturnAccesDenied(req, res);
//
//				JSONObject jsonObject = new JSONObject(responseValue);
//				JSONObject status = (JSONObject) jsonObject.get("status");
//				String code = "";
//				String message = "";
//
//				if (status != null && status.length() != 0) {
//					code = status.getString("code");
//					message = status.getString("message");
//				}
//
//				LogResponseTransaction(CurrentRequestId.get(), Utilities.getClientIp(req) /* req.getRemoteAddr() */,
//						req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//						req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//						req.getProtocol() != null ? req.getProtocol() : "", sessionUser, key,
//						req.getHeader("user") != null ? req.getHeader("user") + "" : "", "", "",
//						req.getRequestURI().toString(),
//						jsonObject.get("hasError").toString() != null ? jsonObject.get("hasError").toString() : "",
//						code, message, requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//
//				resp = responseValue;
//
//			}
//		} catch (Exception e) {
//			slf4jLogger.warn(e.getMessage());
//		}
//		return resp;
//	}
//
//	@SuppressWarnings("unused")
//	public String ExecuteRequestDisableFilter(String requestValue, String responseValue, HttpServletRequest req,
//			HttpServletResponse res, FilterChain chain, boolean isMultipart) {
//		String resp = "";
//		try {
//			String sessionUser = req.getHeader("sessionUser");
//
//			String serviceLibelle = "";
//			String ndCode = "";
//			String customerCode = "";
//			JSONObject jsonObject = null;
//
//			final boolean ignoreSessionCheching = true;
//			if (ignoreSessionCheching || Utilities.notBlank(sessionUser)) {
//				UserDto userDto = null;
//				if (Utilities.notBlank(sessionUser)) {
//					userDto = cacheUtils.getUser(sessionUser);
//				}
//				if (ignoreSessionCheching || userDto != null) {
//					// doit etre enlevé lorsque le front intègre
//					if (userDto != null) {
//						// requestValue = addUserInRequest(requestValue, userDto);
//						cacheUtils.saveUser(userDto.getKey(), userDto, true);
//					}
//					HttpServletResponseReadableWrapper responseWrapper = new HttpServletResponseReadableWrapper(res);
//
//					if (isMultipart) {
//						chain.doFilter(req, responseWrapper);
//					} else {
//						jsonObject = new JSONObject(requestValue);
//						HttpServletRequestWritableWrapper requestWrapper = new HttpServletRequestWritableWrapper(req,
//								requestValue.getBytes());
//						chain.doFilter(requestWrapper, responseWrapper);
//					}
//
//					responseValue = responseWrapper.getResponseData();
//					if (Utilities.notBlank(responseValue) && responseValue.startsWith("{")) {
//						jsonObject = new JSONObject(responseValue);
//						// JSONObject status = (JSONObject) jsonObject.get("status");
//						JSONObject status = jsonObject.has("status") ? (JSONObject) jsonObject.get("status")
//								: new JSONObject(new Status());
//
//						String code = "";
//						String message = "";
//
//						if (status != null && status.length() != 0) {
//							setMessageCode(status, code, message);
//
//						}
//						hostingUtils.LogResponseTransaction(CurrentRequestId.get(), Utilities.getClientIp(req),
//								req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//								req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//								req.getProtocol() != null ? req.getProtocol() : "", sessionUser, "",
//								req.getHeader("tenantID") != null ? req.getHeader("tenantID") + "" : "",
//								userDto != null && Utilities.notBlank(userDto.getNom()) ? userDto.getNom() : "",
//								userDto != null && Utilities.notBlank(userDto.getPrenom()) ? userDto.getPrenom() : "",
//								req.getRequestURI().toString(),
//								jsonObject.get("hasError").toString() != null ? jsonObject.get("hasError").toString()
//										: "",
//								code, message, requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//					}
//					resp = responseValue;
//				} else {
//					String svg_session = CustomerCareUtils.VAR + sessionUser;
//					userDto = cacheUtils.getUser(svg_session);
//
//					if (userDto != null) {
//						responseValue = returnSessionExpired(req, res);
//						cacheUtils.deleteUser(svg_session);
//
//						jsonObject = new JSONObject(responseValue);
//						JSONObject status = (JSONObject) jsonObject.get("status");
//						String code = "";
//						String message = "";
//
//						if (status != null && status.length() != 0) {
//							code = status.getString("code");
//							message = status.getString("message");
//						}
//						hostingUtils.LogResponseTransaction(CurrentRequestId.get(), Utilities.getClientIp(req),
//								req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//								req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//								req.getProtocol() != null ? req.getProtocol() : "", sessionUser, "",
//								req.getHeader("tenantID") != null ? req.getHeader("tenantID") + "" : "",
//								userDto != null && Utilities.notBlank(userDto.getNom()) ? userDto.getNom() : "",
//								userDto != null && Utilities.notBlank(userDto.getPrenom()) ? userDto.getPrenom() : "",
//								req.getRequestURI().toString(),
//								jsonObject.get("hasError").toString() != null ? jsonObject.get("hasError").toString()
//										: "",
//								code, message, requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//
//						resp = responseValue;
//					} else {
//						responseValue = ReturnAccesDenied(req, res);
//
//						jsonObject = new JSONObject(responseValue);
//						JSONObject status = (JSONObject) jsonObject.get("status");
//						String code = "";
//						String message = "";
//
//						if (status != null && status.length() != 0) {
//							// code = status.getString("code");
//							// message = status.getString("message");
//							setMessageCode(status, code, message);
//						}
//
//						hostingUtils.LogResponseTransaction(CurrentRequestId.get(), Utilities.getClientIp(req),
//								req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//								req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//								req.getProtocol() != null ? req.getProtocol() : "", sessionUser, "",
//								req.getHeader("tenantID") != null ? req.getHeader("tenantID") + "" : "",
//								userDto != null && Utilities.notBlank(userDto.getNom()) ? userDto.getNom() : "",
//								userDto != null && Utilities.notBlank(userDto.getPrenom()) ? userDto.getPrenom() : "",
//								req.getRequestURI().toString(),
//								jsonObject.get("hasError").toString() != null ? jsonObject.get("hasError").toString()
//										: "",
//								code, message, requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//
//						resp = responseValue;
//					}
//				}
//			} else {
//				responseValue = ReturnAccesDenied(req, res);
//
//				jsonObject = new JSONObject(responseValue);
//				JSONObject status = (JSONObject) jsonObject.get("status");
//				String code = "";
//				String message = "";
//
//				if (status != null && status.length() != 0) {
//					// code = status.getString("code");
//					// message = status.getString("message");
//					setMessageCode(status, code, message);
//				}
//
//				hostingUtils.LogResponseTransaction(CurrentRequestId.get(), Utilities.getClientIp(req),
//						req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//						req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//						req.getProtocol() != null ? req.getProtocol() : "", sessionUser, "",
//						req.getHeader("tenantID") != null ? req.getHeader("tenantID") + "" : "", null, null,
//						req.getRequestURI().toString(),
//						jsonObject.get("hasError").toString() != null ? jsonObject.get("hasError").toString() : "",
//						code, message, requestValue, responseValue, null, null, null);
//
//				resp = responseValue;
//			}
//		} catch (Exception e) {
//			slf4jLogger.error(e.getMessage());
//			e.printStackTrace();
//		}
//		return resp;
//	}
//
//	public String ExecuteRequestDisableSessionAndFilter(String requestValue, String responseValue,
//			HttpServletRequest req, HttpServletResponse res, FilterChain chain, boolean isMultipart) {
//		String resp = "";
//		try {
//			String sessionUser = req.getHeader("sessionUser");
//			String serviceLibelle = "";
//			String ndCode = "";
//			String customerCode = "";
//			JSONObject jsonObject = null;
//			HttpServletResponseReadableWrapper responseWrapper = new HttpServletResponseReadableWrapper(res);
//			if (isMultipart) {
//				chain.doFilter(req, responseWrapper);
//			} else {
//				jsonObject = new JSONObject(requestValue);
//				HttpServletRequestWritableWrapper requestWrapper = new HttpServletRequestWritableWrapper(req,
//						requestValue.getBytes());
//				chain.doFilter(requestWrapper, responseWrapper);
//			}
//			responseValue = responseWrapper.getResponseData();
//			System.out.println("responseValue ===>" + responseValue);
//
//			if (Utilities.notBlank(responseValue) && responseValue.startsWith("{")) {
//				jsonObject = new JSONObject(responseValue);
//				// JSONObject status = (JSONObject) jsonObject.get("status");
//				JSONObject status = jsonObject.has("status") ? (JSONObject) jsonObject.get("status")
//						: new JSONObject(new Status());
//				String code = "";
//				String message = "";
//
//				if (status != null && status.length() != 0) {
//					// code = status.getString("code").toString();
//					// message = status.getString("message");
//					setMessageCode(status, code, message);
//
//				}
//				hostingUtils.LogResponseTransaction(CurrentRequestId.get(), Utilities.getClientIp(req),
//						req.getUserPrincipal() != null ? req.getUserPrincipal() + "" : "",
//						req.getHeader("User-Agent") != null ? req.getHeader("User-Agent") : "",
//						req.getProtocol() != null ? req.getProtocol() : "", sessionUser, "",
//						req.getHeader("tenantID") != null ? req.getHeader("tenantID") + "" : "", "", "",
//						req.getRequestURI().toString(),
//						jsonObject.get("hasError").toString() != null ? jsonObject.get("hasError").toString() : "",
//						code, message, requestValue, responseValue, serviceLibelle, ndCode, customerCode);
//
//				slf4jLogger.info(
//						" ------------------------  l'execution de la methode principale    ----------------------------");
//
//				resp = responseValue;
//			}
//
//		} catch (Exception e) {
//			slf4jLogger.error(e.getMessage());
//			e.printStackTrace();
//		}
//		return resp;
//	}
//
//	@Async
//	public void LogResponseTransaction(String RequestIdentifier, String RemoteAddr, String UserPrincipal,
//			String UserAgent, String Protocol, String SessionUser, String KeyAES, String CompanyID, String nom,
//			String prenom, String Uri, String HasError, String StatusCode, String StatusMessage, String RequestValue,
//			String ResponseValue, String Action, String ND, String Customer) throws Exception {
//		slf4jLogger.info(
//				" ------------------------  l'execution de la methode asynchrone    ----------------------------");
//		String connectedUserLogin = "";
//		String connectedUserFullName = "";
//		if (SessionUser != null) {
//			UserDto userDto = cacheUtils.getUser(SessionUser);
//			if (userDto != null) {
//				connectedUserLogin = userDto.getLogin();
//				connectedUserFullName = userDto.getPrenom() + " " + userDto.getNom();
//			}
//		}
//
//		slf4jLogger.info(
//				"Request:: RequestIdentifier:{},RemoteAddr:{} ,UserPrincipal:{},UserAgent:{},Protocol:{},SessionUser:{},KeyAES:{},CompanyID:{},FirstName:{},LastName:{},Uri:{},HasError:{},StatusCode:{},StatusMessage:{},RequestValue:{},ResponseValue:{},Action:{}",
//				RequestIdentifier, RemoteAddr, UserPrincipal, UserAgent, Protocol, SessionUser, KeyAES, CompanyID, nom,
//				prenom, Uri, HasError, StatusCode, StatusMessage, RequestValue, ResponseValue, Action);
//	}
//	// permettre de recuperer tous les logs sans les logs systems
//
//	private void setMessageCode(JSONObject status, String code, String message) {
//		if (status != null && status.length() != 0) {
//			code = status.has("code") ? status.get("code").toString() : "";
//			message = status.has("message") ? status.get("message").toString() : "";
//		}
//	}
//
//}
