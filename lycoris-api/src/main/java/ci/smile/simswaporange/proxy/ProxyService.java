//package ci.smile.simswaporange.proxy;
//
//import java.util.Map;
//
//import org.springframework.http.ResponseEntity;
//
//import ci.smile.simswaporange.proxy.response.ApiResponse;
//import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
//import ci.smile.simswaporange.proxy.response.SensitiveNumberDto;
//import ci.smile.simswaporange.proxy.response.SensitiveNumberPackDto;
//import ci.smile.simswaporange.proxy.response.TokenDto;
//import feign.Headers;
//import feign.Param;
//import feign.RequestLine; 
//
//public interface ProxyService {
//	@RequestLine("POST /gwbooster2/token")
//	@Headers({ "Accept: application/json", "Content-Type: application/x-www-form-urlencoded",
//			"Authorization: Basic SFBpOHZzeUFWM3JsaHg5Q1Bha0loNzFETzB3YTo5V19ncr0Nj3zY3Z5RWk2YT3QUxTRWQyZWth" })
//	ResponseEntity<?> getToken(@Param("grant_type") String grantType);
//	
//	@RequestLine("POST /gwbooster2/sim-swap-backend/v1/sensitive-numbers/{phoneNumber}/lock?appId={appId}&requestId={requestId}")
//    @Headers("Authorization: Bearer {token}")
//	ApiResponse<LockUnLockFreezeDto> lockPhoneNumber(@Param("phoneNumber") String phoneNumber, @Param("appId") String appId, @Param("requestId") String requestId, @Param("token") String token);
//
//	@RequestLine("POST /gwbooster2/sim-swap-backend/v1/sensitive-numbers/{phoneNumber}/unlock?appId={appId}&requestId={requestId}")
//	@Headers("Authorization: Bearer {token}")
//	ApiResponse<LockUnLockFreezeDto>  unlockPhoneNumber(@Param("phoneNumber") String phoneNumber, @Param("appId") String appId,@Param("requestId") String requestId, @Param("token") String token);
//
//	@RequestLine("POST /gwbooster2/sim-swap-backend/v1/sensitive-numbers/{phoneNumber}/freeze?appId={appId}&requestId={requestId}")
//    @Headers("Authorization: Bearer {token}")
//	ApiResponse<LockUnLockFreezeDto> freezePhoneNumber(@Param("phoneNumber") String phoneNumber, @Param("appId") String appId, @Param("requestId") String requestId, @Param("token") String token);
//
//	@RequestLine("GET /gwbooster2/sim-swap-backend/v1/sensitive-numbers/?appId={appId}&requestId={requestId}&pageNumber={pageNumber}&pageSize={pageSize}&sensitiveNumberPack={sensitiveNumberPack}")
//    @Headers("Authorization: Bearer {token}")
//	ApiResponse<SensitiveNumberPackDto> getContracts(@Param("appId") String appId, @Param("requestId") String requestId, @Param("pageNumber") int pageNumber, @Param("pageSize") int pageSize, @Param("sensitiveNumberPack") String sensitiveNumberPack, @Param("token") String token);
//
//	@RequestLine("GET /gwbooster2/sim-swap-backend/v1/sensitive-numbers/find-by-msisdn?appId={appId}&requestId={requestId}&msisdn={msisdn}")
//    @Headers("Authorization: Bearer {token}")
//	ApiResponse<SensitiveNumberDto>  searchContract(@Param("appId") String appId, @Param("requestId") String requestId, @Param("msisdn") String msisdn, @Param("token") String token);
//
//	@RequestLine("POST /routeur_kannel/interface.php/application_orange?SOA=Orange&DA={phoneNumber}&UserName=application_orange&Password=orange&Content={message}&Flags=1&SenderAppId={appId}")
//	void  sendSMS(@Param("phoneNumber") String phoneNumber, @Param("message") String message, @Param("appId") String appId);
//
//}
