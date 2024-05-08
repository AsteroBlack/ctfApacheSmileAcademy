package ci.smile.simswaporange.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerCareUtils {
	private Logger slf4jLogger;

	public static final String ALGORITHM_AES = "AES";
	public static final String ALGORITHM_RSA = "RSA";
	public static final String ALGORITHM_AES_ECB_PKCS7 = "AES/ECB/PKCS7Padding";
	// public static final String ALGORITHM_AES_ECB_PKCS7 =
	// "AES/ECB/PKCS5Padding";
	public static final String BIT = "4096";
	public static final String BITS = "128";
	public static final String KEY_AES = "khqWPuvI+431q/0Ev1wVhzG3Po8Z0UIBlvM/fm6uGW0=";

	public static List<String> URI_TO_NOT_LOG = Arrays.asList("logs/exportHistoriqueConnexion",
			"logs/exportDistributionArchitectureLegacy", "logs/exportTransportArchitectureLegacy",
			"logs/exportTransportDistributionArchitectureDisruptive", "logRefonteSi/exportAction", "otv/exportLocation",
			"logRefonteSi/getConnected", "logRefonteSi/getActions");

	public static List<String> URI_AS_IGNORE = Arrays.asList("logs/exportHistoriqueConnexion","logs/ipBng/upload",
			"portail-captif/uploadFileToDir","portail-captif/campagneBaseClientImport","portail-captif/testDeploiement",
			"logs/exportDistributionArchitectureLegacy", "logs/exportTransportArchitectureLegacy",
			"logs/exportTransportDistributionArchitectureDisruptive", "logRefonteSi/exportAction", "otv/exportLocation","otv/exportContenusRegarder",
			"utilisateur/validateToken", "utilisateur/generateToken", "utilisateur/endInscription",
			"utilisateur/changePassword", "logRefonteSi/getConnected", "logRefonteSi/getActions", "user/getUserCreate",
			"user/getActiveSession", "otv/maFibreOrange/getInfo", "otv/getListesComptesMobiles", "user/uploadDatasUser",
			"relOltTransport/extraction", "relPdDistribution/exportationRelOltDistribution",
			"awaleTransport/extraction", "awaleDistribution/exportationAwaleDistribution",
			"disruptiveDistribution/exportationDisruptive", "relOltTransport/upload", "relPdDistribution/upload","logs/disruptiveDistributionV2/upload",
			"disruptiveDistribution/upload", "awaleTransport/upload", "awaleDistribution/upload", "logs/getTokenn","user/uploadUser", "logs/logement/upload", "logement/upload", "/logs/exportPrepaidCustomerOperationHistory");

	public static List<String> URI_AS_NOT_TOKEN = Arrays.asList("api/", "utilisateur/emailForgotPwd",
			"utilisateur/validateToken2", "utilisateur/validateToken", "utilisateur/generateToken",
			"utilisateur/endInscription", "utilisateur/changePassword", "logRefonteSi/getConnected",
			"logRefonteSi/getActions");
	public static List<String> PROFILES_TO_IGNORE = Arrays.asList("unencrypt_prod");
	public static final String VAR = "svg_";

	public static LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static String[] getIndex(String startDate, String endDate) throws ParseException {
		List<LocalDate> totalDates = new ArrayList<>();
		Date start = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
		Date end = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
		LocalDate begin = convertToLocalDateViaMilisecond(start);
		LocalDate en = convertToLocalDateViaMilisecond(end);
		while (begin.isBefore(en)) {
			totalDates.add(begin);
			begin = begin.plusDays(1);
		}
		if (totalDates != null && !totalDates.isEmpty()) {
			List<String> result = new ArrayList<>();
			totalDates.forEach(t -> {
				result.add(t.toString());
			});
			return (String[]) result.toArray();
		}
		return null;
	}

	// for token
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	public static SimpleDateFormat[] findDateFormat(String date) {
		SimpleDateFormat simpleDateFormat = null;
		SimpleDateFormat simpleDateFormat2 = null;
		String regex_dd_MM_yyyy = "\\A0?(?:3[01]|[12][0-9]|[1-9])[/.-]0?(?:1[0-2]|[1-9])[/.-][0-9]{4}\\z";
		String regex_dd_MM_yyyy_hh = "\\A0?(?:3[01]|[12][0-9]|[1-9])[/.-]0?(?:1[0-2]|[1-9])[/.-][0-9]{4}\\h+0?(?:2[0-3]|1?[0-9])\\z";
		String regex_dd_MM_yyyy_hh_mm = "\\A0?(?:3[01]|[12][0-9]|[1-9])[/.-]0?(?:1[0-2]|[1-9])[/.-][0-9]{4}\\h+0?(?:2[0-3]|1?[0-9])[:.]0?[1-5]?[0-9]\\z";
		String regex_dd_MM_yyyy_hh_mm_ss = "\\A0?(?:3[01]|[12][0-9]|[1-9])[/.-]0?(?:1[0-2]|[1-9])[/.-][0-9]{4}\\h+0?(?:2[0-3]|1?[0-9])[:.]0?[1-5]?[0-9][:.]0?[1-5]?[0-9]\\z";

		if (date != null && !date.isEmpty())
			try {
				String pattern = "";
				String pattern2 = "";
				if (date.matches(regex_dd_MM_yyyy)) {
					pattern = "dd-MM-yyyy";
					pattern2 = "yyyy-MM-dd";
				} else if (date.matches(regex_dd_MM_yyyy_hh)) {
					pattern = "dd-MM-yyyy HH";
					pattern2 = "yyyy-MM-dd HH";
				} else if (date.matches(regex_dd_MM_yyyy_hh_mm)) {
					pattern = "dd-MM-yyyy HH:mm";
					pattern2 = "yyyy-MM-dd HH:mm";
				} else if (date.matches(regex_dd_MM_yyyy_hh_mm_ss)) {
					pattern = "dd-MM-yyyy HH:mm:ss";
					pattern2 = "yyyy-MM-dd HH:mm:ss";
				}

				if (pattern != null && !pattern.isEmpty()) {
					if (date.contains("/"))
						pattern = pattern.replaceAll("-", "/");
					if (date.contains("."))
						pattern = pattern.replaceAll("-", ".");

					System.out.println(pattern);
					simpleDateFormat = new SimpleDateFormat(pattern);
					simpleDateFormat2 = new SimpleDateFormat(pattern2);
					simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
					simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("UTC"));
					return new SimpleDateFormat[] { simpleDateFormat, simpleDateFormat2 };
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return new SimpleDateFormat[] { null, null };
			}
		return new SimpleDateFormat[] { null, null };
	}

	public static boolean isValidateIvorianPhoneNumber(String phoneNumber) {
		String regex = "^(\\(\\+225\\)|\\+225|\\(00225\\)|00225)?(\\s)?[0-9]{2}([ .-]?[0-9]{2}){3}$";
		return phoneNumber.matches(regex);
	}

	public static String ivorianPhoneNumberToStandardFormat(String phoneNumber) {
		String beginRegex = "^(\\(\\+225\\)|\\+225|\\(00225\\)|00225)?";
		String specialCharRegex = "[ .-]?";
		String simplePhoneNumber;

		simplePhoneNumber = phoneNumber.replaceAll(beginRegex, "");
		return simplePhoneNumber.replaceAll(specialCharRegex, "");
	}

	public BufferedReader getFileAsBuffer(String fileName) throws UnsupportedEncodingException {
		InputStream input = CustomerCareUtils.class.getClassLoader().getResourceAsStream(fileName);
		return new BufferedReader(new InputStreamReader(input, "UTF-8"));
	}

	public FileReader getResource(String resourceName) throws FileNotFoundException {
		return new FileReader(getClass().getResource(resourceName).getFile());
		// return new
		// FileReader(ServletContext.class.getResource(resourceName).getFile());
	}

	/**
	 * Constructeur privé
	 */
	private CustomerCareUtils() {
		slf4jLogger = LoggerFactory.getLogger(getClass());
	}

	private static CustomerCareUtils instance;

	/**
	 * Point d'accès pour l'instance unique du singleton
	 */
	public static synchronized CustomerCareUtils getInstance() {
		if (instance == null) {
			instance = new CustomerCareUtils();
		}

		return instance;
	}

	public static JSONObject objectToJSONObject(Object object) {
		Object json = null;
		JSONObject jsonObject = null;
		try {
			json = new JSONTokener(object.toString()).nextValue();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (json instanceof JSONObject) {
			jsonObject = (JSONObject) json;
		}
		return jsonObject;
	}

	public static JSONArray objectToJSONArray(Object object) {
		Object json = null;
		JSONArray jsonArray = null;
		try {
			json = new JSONTokener(object.toString()).nextValue();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (json instanceof JSONArray) {
			jsonArray = (JSONArray) json;
		}
		return jsonArray;
	}

	public static boolean anObjectFieldsMapAllFieldsToVerify(List<Object> objets, Map<String, Object> fieldsToVerify) {
		for (Object objet : objets) {
			boolean oneObjectMapAllFields = true;
			JSONObject jsonObject = objectToJSONObject(objet);
			for (Entry<String, Object> entry : fieldsToVerify.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				try {
					if (!jsonObject.get(key).equals(value)) {
						oneObjectMapAllFields = false;
						break;
					}
				} catch (Exception e) {
					oneObjectMapAllFields = false;
					break;
				}
			}
			if (oneObjectMapAllFields)
				return true;
		}

		return false;
	}

	public static String encrypt(String str) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		byte[] hashedBytes = digest.digest(str.getBytes("UTF-8"));

		return convertByteArrayToHexString(hashedBytes);
	}

	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}

	public static boolean isValidEmail(String email) {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

//    public static String convertFileToBase64(String pathFichier) {
//
//        if (!Utilities.existFile(pathFichier)) {
//            return null;
//        }
//
//        File   originalFile  = new File(pathFichier);
//        String encodedBase64 = null;
//        try {
//            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
//            byte[]          bytes                 = new byte[(int) originalFile.length()];
//            fileInputStreamReader.read(bytes);
//            encodedBase64 = new String(java.util.Base64.getEncoder().encodeToString((bytes)));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return encodedBase64;
//    }

	public static String convertToBase64(String pathFichier) {
		File file = new File(Thread.currentThread().getContextClassLoader().getResource(pathFichier).getFile());
		return getStringImage(file);
	}

//    public static String getQRCodeBase64(String infos) {
//        if (infos == null) {
//            return null;
//        }
//        File file = new File(QRCode.from(infos).withSize(500, 500).withErrorCorrection(ErrorCorrectionLevel.H).to(ImageType.JPG).file().getAbsolutePath());
//        return getStringImage(file);
//    }
//
//    public static String getBarCodeBase64(String infos) throws IOException {
//        if (infos == null) {
//            return null;
//        }
//        final int  dpi         = 160;
//        String     barCodePath = "/tmp/";
//        Code39Bean bean39      = new Code39Bean();
//        String     filePath    = barCodePath + infos + ".JPG";
//        // Configure the barcode generator
//        bean39.setModuleWidth(UnitConv.in2mm(2.8f / dpi));
//        // Open output file
//        File             outputFile = new File(filePath);
//        FileOutputStream out        = new FileOutputStream(outputFile);
//        // Set up the canvas provider for monochrome PNG output
//        BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
//        // Generate the barcode
//        bean39.generateBarcode(canvas, infos);
//        // Signal end of generation
//        canvas.finish();
//        File file = new File(filePath);
//        return getStringImage(file);
//    }

	private static String getStringImage(File file) {
		try {
			FileInputStream fin = new FileInputStream(file);
			byte[] imageBytes = new byte[(int) file.length()];
			fin.read(imageBytes, 0, imageBytes.length);
			fin.close();
			return Base64.encodeBase64String(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//    public static boolean canEditAllField(HashMap<String, Object> fieldToEdit, Object originalObject, Integer maxFieldEditable, String editionKey) {
//        Integer                 _numberOfFieldToEdit = 0;
//        JSONObject              originalJsonObject   = new JSONObject(originalObject);
//        HashMap<String, Object> fieldToEditClone     = (HashMap<String, Object>) fieldToEdit.clone();
//        for (Entry<String, Object> entry : fieldToEditClone.entrySet()) {
//            String fieldName          = entry.getKey();
//            Object fieldValue         = entry.getValue();
//            Object originalFieldValue = (originalJsonObject.isNull(fieldName)) ? null : originalJsonObject.get(fieldName);
//            if (Utilities.areNotEquals(originalFieldValue, fieldValue)) {
//                _numberOfFieldToEdit++;
//                if (!fieldToEdit.containsKey(editionKey))
//                    fieldToEdit.put(editionKey, true);
//            }
//        }
//        return (_numberOfFieldToEdit > maxFieldEditable) ? false : true;
//    }

	public static boolean notBlank(String str) {
		return str != null && !str.isEmpty() && !str.equals("\n");
	}

//    public static List<List<Log>> organizeLogsByRequestIdentifier(List<Log> logs) {
//        if (logs == null || logs.isEmpty())
//            return null;
//
//        List<List<Log>> result                = new ArrayList<List<Log>>();
//        List<Log>       completeRequest       = new ArrayList<Log>();
//        List<String>    requestIdentifierList = new ArrayList<String>();
//        for (Log log : logs)
//            if (!requestIdentifierList.contains(log.getRequestIdentifier()))
//                requestIdentifierList.add(log.getRequestIdentifier());
//
//        for (String requestIdentifier : requestIdentifierList) {
//            completeRequest = new ArrayList<Log>();
//            for (Log log : logs) {
//                if (log.getRequestIdentifier().equals(requestIdentifier)) {
//                    completeRequest.add(log);
//                    // if (completeRequest.size() == 2)
//                    // break;
//                }
//            }
//            System.out.println(requestIdentifier + " : nombre de conf trouvées = " + completeRequest.size());
//            result.add(completeRequest);
//        }
//        System.out.println("nombre de transaction trouvées = " + result.size());
//        return result;
//    }

	public static int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static String generateAlphanumericCode(Integer nbreCaractere) {
		String formatted = null;
		formatted = RandomStringUtils.randomAlphanumeric(nbreCaractere).toLowerCase();
		return formatted;
	}

	public static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

}
