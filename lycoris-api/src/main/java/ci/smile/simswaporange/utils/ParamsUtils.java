package ci.smile.simswaporange.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;


@Data
@Component
public class ParamsUtils {
    //	public static String simSwapServer;
//	@Value("${url.simswap.server}")
//	public void setSimServer(String server) {
//		simSwapServer = server;
//	}
//	public static String simSwapPort;
//	@Value("${url.simswap.port}")
//	public void setSimPort(String port) {
//		simSwapPort = port; 
//	}

	@Value("${smtp.mail.host}")
	private String smtpHost;

	@Value("${simswap.api.uri.v2}")
	private String simswapBaseUriv2;
	
	@Value("${secret.key}")
	private String aesSecretKey;
	
	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.host.serveur2}")
	private String redisHostServeur2;

	@Value("${spring.redis.host.serveur3}")
	private String redisHostServeur3;
	
	@Value("${spring.redis.port}")
	private Integer redisPort;
	
	@Value("${spring.redis.password}")
	private String redisPassword;
	
	@Value("${simswap.api.uri}")
	private String simswapUri;
	
	@Value("${category.om}")
	private String[] categorieOmci;
	@Value("${category.telco}")
	private String[] categoriTelco;
	
//	Paramètre authentification LDAP, email, envoie sms;
	@Value("${smtp.oci.mail.address}")
	private String ociServeurMailAddress;
	@Value("${app-id}")
	private String appId;
	@Value("${smtp.oci.mail.port}")
	private Integer ociPortServeurMail;
	@Value("${send.sms.message}")
	private String urlSendSms;
	@Value("${url.authentication.test}")
	private String urlAuthenticationLdapTest;
	
	@Value("${smtp.mail.username}")
	private String smtpUsername;
	
	@Value("${url.authentication.production}")
	private String urlAuthenticationLdapProduction;
	
	@Value("${file.path-doc}")
    private String filePathDoc;
	
	@Value("${duree.blocage}")
	private Long dureeBlocage;

	@Value("${smtp.mail.port}")
	private Integer smtpPort;
	
	@Value("${smtp.mail.adresse}")
	private String smtpMailAdresse;
	
	@Value("${smtp.mail.password}")
	private String smtpPassword;

	@Value("${uri.login.simswap}")
	private String uriLoginSimswap;

    @Value("${image.directory}")
    private String imageDirectory;

    @Value("${textfile.directory}")
    private String textfileDirectory;

    @Value("${video.directory}")
    private String videoDirectory;

    @Value("${otherfile.directory}")
    private String otherfileDirectory;

    @Value("${url.root}")
    private String urlRoot;

    @Value("${path.root}")
    private String rootFilesPath;
    
    @Value("${path.root.files}")
    private String rootFilesPathTomcat;
    
    @Value("${smtp.mail.adresse}")
	private String smtpLogin;

    // Files location
    @Value("${url.image.link}")
    private String urlImageLink;

    @Value("${parametre.dossier}")
    private String pathDossier;
    
    
    @Value("${extract.data}")
    private String extractData;
    
    @Value("${cron.execute.00h.byDay}")
    private String pathDossierJob;
    
    @Value("${cron.string.base}")
    private String baseString;
    


    @Value("${url.admin}")
    private String urlAdmin;
    
    @Value("${basic.auth}")
    private String basicAuth;

    @Value("${spring.datasource.driverClassName}")
    private String userDataSource;

    @Value("${spring.datasource.url}")
    private String urlDb;

    @Value("${spring.datasource.username}")
    private String userDb;

    @Value("${spring.datasource.password}")
    private String passDb;

    public static String simSwapAddress;

    @Value("${url.simswap.address}")
    public void setSimPort(String address) {
        simSwapAddress = address;
    }
    
    public static String simSwapAddressMsisdn;
    @Value("${url.simswap.address.msisdn}")
    public void setSimPortMsisdn(String adressmsisdn) {
    	simSwapAddressMsisdn = adressmsisdn;
    }
    
    public static String simSwapAppId;

    @Value("${url.simswap.appId}")
    public void setAppId(String appId) {
        ParamsUtils.simSwapAppId = appId;
    }


    public static String sensitiveNumberPack;

    @Value("${url.simswap.sensitiveNumberPack}")
    public void sensitiveNumber(String sensitiveNumber) {
        ParamsUtils.sensitiveNumberPack = sensitiveNumber;
    }
    
    //redis
    
	@Value("#{'${redis.nodes}'.split(',')}") 
	private List<String> nodes;
	@Value("${redis.clusterName}")
	private String clusterName;
	@Value("${redis.password}")
	private String password;
	

	@Value("${redis.database}")
	private Integer database;
	
	public List<String> getNodes() {
		return nodes;
	}
	public String getClusterName() {
		return clusterName;
	}
	public String getPassword() {
		return password;
	}
	public Integer getDatabase() {
		return database;
	}
}
