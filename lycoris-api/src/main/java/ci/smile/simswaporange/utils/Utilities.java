
/*
 * Created on 2022-06-09 ( Time 18:20:00 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
//import javax.xml.XMLConstants;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;

import ci.smile.simswaporange.dao.entity.Category;
import ci.smile.simswaporange.dao.repository.CategoryRepository;
import ci.smile.simswaporange.proxy.customizeclass.CategoriesPair;
import ci.smile.simswaporange.utils.dto.customize._AppRequestDto;
import javassist.tools.web.BadHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Utilities
 *
 * @author Geo
 */

@Slf4j
public class Utilities {

    public static SecretKey key;
    public static int KEY_SIZE = 128;
    public static int T_LEN = 128;
    public static Cipher encryptionCipher;
    static final String alphabet = "#abcdefghijklmnopqrstuvwxyz";

    public static Date getCurrentDate() {
        return new Date();
    }

    public static boolean isValidID(Integer id) {
        return id != null && id > 0;
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat dateTimesFormats = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = dateTimesFormats.format(new Date());
        return date;
    }

    public static boolean areEquals(Object obj1, Object obj2) {
        return (Objects.equals(obj1, obj2));
    }

    public static <T extends Comparable<T>, Object> boolean areEquals(T obj1, T obj2) {
        return (obj1 == null ? obj2 == null : obj1.equals(obj2));
    }

    public static String getDateToString(Date date){
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(date);

        return todayAsString;
    }

    public static boolean areNotEquals(Object obj1, Object obj2) {
        return !areEquals(obj1, obj2);
    }

    public static <T extends Comparable<T>, Object> boolean areNotEquals(T obj1, T obj2) {
        return !(areEquals(obj1, obj2));
    }

    public static Date parseDate(String inputDate) throws ParseException {
        // Définir le format d'entrée
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Parser la date d'entrée
        Date date = inputFormat.parse(inputDate);
        return date;
    }

    private static final String[] IP_HEADER_CANDIDATES = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    /**
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    private static List<String> listeBase = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    public static String combinaisonString() {
        String lettres = "";
        try {
            Random random;
            for (int i = 0; i < 10; i++) {
                random = new Random();
                int rn = random.nextInt(35 - 0 + 1) + 0;
                lettres += listeBase.get(rn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lettres;
    }
    public static String getFileFromTomcat(ParamsUtils paramsUtils){
        LocalDate dateAujourdhui = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateFormatee = dateAujourdhui.format(formatter);
        String resultatCsv = paramsUtils.getBaseString() + dateFormatee + ".csv";
        Path cheminFichierCsv = FileSystems.getDefault().getPath(paramsUtils.getExtractData(), resultatCsv);

        return cheminFichierCsv.toString();

    }

    public static String formatDate(String date, String initDateFormat, String endDateFormat) throws ParseException {
        if (!notBlank(date)) {
            return "";
        }
        Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
        String parsedDate = formatter.format(initDate);

        return parsedDate;
    }

    @SneakyThrows
    public static Date formatDateAnyFormat(String date, String endDateFormat) {
        try {
            Date initDate = new SimpleDateFormat(endDateFormat).parse(date);
            return initDate;
        } catch (Exception e) {
            Date initDate = new SimpleDateFormat("M/d/yyyy").parse(date);
            return initDate;
        }
    }

    public static String onList(List<Integer> liste) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < liste.size(); i++) {
            sb.append(liste.get(i));
            if (i < liste.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        String resultat = sb.toString();
        System.out.println(resultat);
        return resultat;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static int duration(Date startDate, Date endDate) {
        long duration = ChronoUnit.DAYS.between(asLocalDate(startDate), asLocalDate(endDate));
        return Integer.parseInt(String.valueOf(duration + 1));
    }

    public static int duration(LocalDate startLocalDate, LocalDate endLocalDate) {
        long duration = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
        return Integer.parseInt(String.valueOf(duration + 1));
    }

    /**
     * Check if a String given is an Integer.
     *
     * @param s
     * @return isValidInteger
     */
    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try {
            Integer.parseInt(s);

            // s is a valid integer
            isValidInteger = true;
        } catch (NumberFormatException ex) {
            // s is not an integer
        }

        return isValidInteger;
    }

    public static String generateCodeOld() {
        String formatted = null;
        formatted = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        return formatted;
    }

    public static String generateCode() {
        String formatted = null;
        SecureRandom secureRandom = new SecureRandom();
        int num = secureRandom.nextInt(100000000);
        formatted = String.format("%05d", num);
        return formatted;
    }

    public static boolean isTrue(Boolean b) {
        return b != null && b;
    }

    public static boolean isFalse(Boolean b) {
        return !isTrue(b);
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Check if a Integer given is an String.
     *
     * @param i
     * @return isValidString
     */
    public static boolean isString(Integer i) {
        boolean isValidString = true;
        try {
            Integer.parseInt(i + "");

            // i is a valid integer

            isValidString = false;
        } catch (NumberFormatException ex) {
            // i is not an integer
        }

        return isValidString;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static String encrypt(String str) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hashedBytes = digest.digest(str.getBytes("UTF-8"));

        return convertByteArrayToHexString(hashedBytes);
    }

    public static boolean isDateValid(String date) {
        try {
            String simpleDateFormat = "dd/MM/yyyy";

            if (date.contains("-")) simpleDateFormat = "dd-MM-yyyy";
            else if (date.contains("/")) simpleDateFormat = "dd/MM/yyyy";
            else return false;

            DateFormat df = new SimpleDateFormat(simpleDateFormat);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String GenerateValueKey(String code) {
        String result = null;
        // String prefix = prefixe;
        String suffix = null;
        String middle = null;
        String separator = "-";
        final String defaut = "000001";
        try {

            SimpleDateFormat dt = new SimpleDateFormat("yy-MM-dd-ss");
            String _date = dt.format(new Date());
            String[] spltter = _date.split(separator);
            middle = spltter[0] + spltter[1] + spltter[2] + spltter[3];
            if (code != null) {
                // Splitter le code pour recuperer les parties
                // String[] parts = code(separator);
                String part = code.substring(1);
                System.out.println("part" + part);

                if (part != null) {
                    int cpt = new Integer(part);
                    cpt++;

                    String _nn = String.valueOf(cpt);

                    switch (_nn.length()) {
                        case 1:
                            suffix = "00000" + _nn;
                            break;
                        case 2:
                            suffix = "0000" + _nn;
                            break;
                        case 3:
                            suffix = "000" + _nn;
                            break;
                        case 4:
                            suffix = "00" + _nn;
                            break;
                        case 5:
                            suffix = "0" + _nn;
                            break;
                        default:
                            suffix = _nn;
                            break;
                    }
                    // result = prefix + separator + middle + separator +
                    // suffix;
                    result = middle + separator + suffix;
                }
            } else {
                // result = prefix + separator + middle + separator + defaut;
                result = middle + separator + defaut;
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static Integer getAge(Date dateNaissance) throws ParseException, Exception {
        Integer annee = 0;

        if (dateNaissance == null) {
            annee = 0;
        }
        Calendar birth = new GregorianCalendar();
        birth.setTime(dateNaissance);
        Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        int adjust = 0;
        if (now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR) < 0) {
            adjust = -1;
        }
        annee = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + adjust;
        return annee;
    }

    public static Boolean AvailableCode(String code) {
        if (code == null || code.isEmpty()) {
            return false;
        }
        Locale local = new Locale(code, "");
        return LocaleUtils.isAvailableLocale(local);

    }

    public static String normalizeFileName(String fileName) {
        String fileNormalize = null;
        fileNormalize = fileName.trim().replaceAll("\\s+", "_");
        fileNormalize = fileNormalize.replace("'", "");
        fileNormalize = Normalizer.normalize(fileNormalize, Normalizer.Form.NFD);
        fileNormalize = fileNormalize.replaceAll("[^\\p{ASCII}]", "");

        return fileNormalize;
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    public static SimpleDateFormat findDateFormat(String date) {
        SimpleDateFormat simpleDateFormat = null;
        String regex_dd_MM_yyyy = "\\A0?(?:3[01]|[12][0-9]|[1-9])[/.-]0?(?:1[0-2]|[1-9])[/.-][0-9]{4}\\z";

        if (date.matches(regex_dd_MM_yyyy))
            if (date.contains("-")) simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            else if (date.contains("/")) simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return simpleDateFormat;
    }

    /**
     * @return Permet de retourner la date courante du système
     */
    public static String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * @param l liste de vérification de doublons
     * @return retourne le nombre de doublon trouvé
     */
    public static int getDupCount(List<String> l) {
        int cnt = 0;
        HashSet<String> h = new HashSet<>(l);

        for (String token : h) {
            if (Collections.frequency(l, token) > 1) cnt++;
        }

        return cnt;
    }

    public static boolean saveImage(String base64String, String nomCompletImage, String extension) throws Exception {

        BufferedImage image = decodeToImage(base64String);

        if (image == null) {

            return false;

        }

        File f = new File(nomCompletImage);

        // write the image

        ImageIO.write(image, extension, f);

        return true;

    }

    public static boolean saveVideo(String base64String, String nomCompletVideo) throws Exception {

        try {

            byte[] decodedBytes = Base64.getDecoder().decode(base64String);
            File file2 = new File(nomCompletVideo);
            FileOutputStream os = new FileOutputStream(file2, true);
            os.write(decodedBytes);
            os.close();

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

        return true;

    }

    public static BufferedImage decodeToImage(String imageString) throws Exception {

        BufferedImage image = null;

        byte[] imageByte;

        imageByte = Base64.getDecoder().decode(imageString);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {

            image = ImageIO.read(bis);

        }

        return image;

    }

    public static String encodeToString(BufferedImage image, String type) {

        String imageString = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {

            ImageIO.write(image, type, bos);

            byte[] imageBytes = bos.toByteArray();

            imageString = new String(Base64.getEncoder().encode(imageBytes));

            bos.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return imageString;

    }

    public static String convertFileToBase64(String pathFichier) {
        File originalFile = new File(pathFichier);
        String encodedBase64 = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
            byte[] bytes = new byte[(int) originalFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.getEncoder().encodeToString((bytes)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedBase64;
    }

    public static String getImageExtension(String str) {
        String extension = "";
        int i = str.lastIndexOf('.');
        if (i >= 0) {
            extension = str.substring(i + 1);
            return extension;
        }
        return null;
    }

    public static boolean fileIsImage(String image) {

        String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp|jpeg))$)";
        Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = pattern.matcher(image);

        return matcher.matches();

    }

    public static boolean fileIsVideo(String video) {

        String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(mp4|avi|camv|dvx|mpeg|mpg|wmv|3gp|mkv))$)";
        Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = pattern.matcher(video);

        return matcher.matches();

    }

    public static void createDirectory(String chemin) {
        File file = new File(chemin);
        if (!file.exists()) {
            try {
                FileUtils.forceMkdir(file);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void deleteFolder(String chemin) {
        File file = new File(chemin);
        try {
            if (file.exists() && file.isDirectory()) {
                FileUtils.forceDelete(new File(chemin));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String chemin) {
        File file = new File(chemin);
        try {
            if (file.exists() && file.getName() != null && !file.getName().isEmpty()) {
                FileUtils.forceDelete(new File(chemin));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean notBlank(String str) {
        return str != null && !str.isEmpty() && !str.equals("\n");
    }

    public static boolean notEmpty(List<String> lst) {
        return lst != null && !lst.isEmpty() && lst.stream().noneMatch(s -> s.equals("\n")) && lst.stream().noneMatch(s -> s.equals(null));
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return (list != null && !list.isEmpty());
    }

    public static <T> void validateList(List<T> list) {
        if ((list == null && list.isEmpty())){
            throw new CustomEntityNotFoundException("902","La liste est vide", Boolean.TRUE);
        }
    }

    static public String GetCode(String Value, Map<String, String> Table) {

        for (Entry<String, String> entry : Table.entrySet()) {
            if (entry.getValue().equals(Value)) {
                return entry.getKey();
            }
        }
        return Value;
    }



    public static boolean anObjectFieldsMapAllFieldsToVerify(List<Object> objets, Map<String, Object> fieldsToVerify) {
        for (Object objet : objets) {
            boolean oneObjectMapAllFields = true;
            JSONObject jsonObject = new JSONObject(objet);
            for (Map.Entry<String, Object> entry : fieldsToVerify.entrySet()) {
                // slf4jLogger.info("jsonObject " +jsonObject);
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
            if (oneObjectMapAllFields) return true;
        }

        return false;
    }

    public static String generateAlphanumericCode(Integer nbreCaractere) {
        String formatted = null;
        formatted = RandomStringUtils.randomAlphanumeric(nbreCaractere).toUpperCase();
        return formatted;
    }

    public static Boolean verifierEmail(String email) {
        Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

    public static String empreinte(String code, Integer idUser, String date, String number) {
        String encryptData = null;

        try {
            encryptData = encrypt(code + "" + idUser + "" + date + "" + number);
            System.out.println("........................." + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("......................" + encryptData);
        return encryptData;
    }

    public void init() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();
    }

    public static String encryptAES(String message) throws Exception {
        byte[] messageInBytes = message.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public static String decryptAES(String encryptedMessage) throws Exception {
        byte[] messageInBytes = decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static <T> boolean isEmpty(List<T> list) {
        return (list == null || list.isEmpty());
    }

    public static <T> List<T> paginner(List<T> allItems, Integer index, Integer size) {
        if (isEmpty(allItems)) {
            return null;
        }

        List<T> items = new ArrayList<T>();
        // si une pagination est pécisée, ne prendre que les éléments demandés
        if (index != null && size != null) {
            Integer fromIndex = index * size;
            if (fromIndex < allItems.size()) {
                Integer toIndex = fromIndex + size;
                if (toIndex > allItems.size()) toIndex = allItems.size();
                items.addAll(allItems.subList(fromIndex, toIndex));
            }
        } else {
            items.addAll(allItems);
        }

        return items;
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        Set<T> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    public static String getSuitableFileDirectory(String fileExtension, ParamsUtils paramsUtils) {
        String suitableFileDirectory = null;
        System.out.println("fileExtension :.......... " + fileExtension);
        if (fileIsImage("file." + fileExtension)) {
            System.out.println("fileExtension 1:.......... " + fileExtension);
            suitableFileDirectory = paramsUtils.getImageDirectory();
        } else {
            if (fileIsTexteDocument("file." + fileExtension)) {
                System.out.println("fileExtension 2:.......... " + fileExtension);
                suitableFileDirectory = paramsUtils.getTextfileDirectory();
                log.info("suitableFileDirectory {}", suitableFileDirectory);
                return String.format("%s%s", paramsUtils.getRootFilesPath(), "");
            } else {
                if (fileIsVideo("file." + fileExtension)) {
                    System.out.println("fileExtension 3:.......... " + fileExtension);
                    suitableFileDirectory = paramsUtils.getVideoDirectory();
                }
            }
        }
        if (suitableFileDirectory == null) {
            suitableFileDirectory = paramsUtils.getOtherfileDirectory();
        }
        return String.format("%s%s", paramsUtils.getRootFilesPath(), suitableFileDirectory);
    }

    public static Integer getColumnIndex(String column) {
        column = new StringBuffer(column.toLowerCase()).reverse().toString();
        Double columnPosition = 0d;
        for (int i = 0; i < column.length(); i++) {
            columnPosition += alphabet.indexOf(column.charAt(i)) * Math.pow(26, i);
        }
        return columnPosition.intValue() - 1;
    }

    public static boolean fileIsTexteDocument(String textDocument) {

        String TEXT_DOCUMENT_PATTERN = "([^\\s]+(\\.(?i)(doc|docx|txt|odt|ods|pdf|xls|xlsx))$)";
        Pattern pattern = Pattern.compile(TEXT_DOCUMENT_PATTERN);
        Matcher matcher = pattern.matcher(textDocument);
        return matcher.matches();
    }

    public static String saveFile(String fileName, String extension, XSSFWorkbook workbook, ParamsUtils paramsUtils) throws IOException, Exception {
        String filePath = null;
        String fileDirectory = null;
        if (fileName.contains("/")) {
            String[] fileNames = fileName.split("/");
            fileName = fileNames[fileNames.length - 1];
        }
        fileName = normalizeFileName(fileName) + "." + extension;
        // S'assurer que l'extension est bonne
        if (!fileIsImage(fileName) && !fileIsTexteDocument(fileName) && !fileIsVideo(fileName)) {
            System.out.println("\n\nL'extension '" + extension + "' n'est pas prise en compte !\n\n");
            return null;
        }
        String filesDirectory = getSuitableFileDirectory(extension, paramsUtils);
        log.info("getSuitableFileDirectory {}", filesDirectory);

        File directory = new File(filesDirectory);
        log.info("directory {}", directory);

        if(!directory.exists()){
            log.info("le dossier existe pas va le créer {}", directory);
            createDirectory(filesDirectory);
        }
        if (!filesDirectory.endsWith("/")) {
            filesDirectory += "/";
        }
        fileDirectory = filesDirectory + fileName;
        FileOutputStream outFile = new FileOutputStream(fileDirectory);
        workbook.write(outFile);
        outFile.close();
        filePath = fileDirectory;

        return fileName;
        // return filePath;
    }

    public static String saveFileHssWork(String fileName, String extension, HSSFWorkbook workbook, ParamsUtils paramsUtils) throws IOException, Exception {
        String filePath = null;
        String fileDirectory = null;
        if (fileName.contains("/")) {
            String[] fileNames = fileName.split("/");
            fileName = fileNames[fileNames.length - 1];
        }
        fileName = normalizeFileName(fileName) + "." + extension;
        // S'assurer que l'extension est bonne
        if (!fileIsImage(fileName) && !fileIsTexteDocument(fileName) && !fileIsVideo(fileName)) {
            System.out.println("\n\nL'extension '" + extension + "' n'est pas prise en compte !\n\n");
            return null;
        }
        String filesDirectory = getSuitableFileDirectory(extension, paramsUtils);
        createDirectory(filesDirectory);
        if (!filesDirectory.endsWith("/")) {
            filesDirectory += "/";
        }
        fileDirectory = filesDirectory + fileName;

        FileOutputStream outFile = new FileOutputStream(new File(fileDirectory));
        workbook.write(outFile);
        outFile.close();
        filePath = fileDirectory;

        return fileName;
        // return filePath;
    }

    public static String getFileUrlLink(String fileName, ParamsUtils paramsUtils) {
        String suitableFileDirectory = null;
        String file[] = fileName.split("\\.");
        int i = 0, j = 1;
        if (file.length > 0 && j < file.length) {
            String fileExtension = file[j];
            if (fileIsImage("file." + fileExtension) || fileIsTexteDocument("file." + fileExtension)) {
                suitableFileDirectory = paramsUtils.getUrlImageLink();
            } else {
                suitableFileDirectory = "extra_data/files/";
            }
        }
        return String.format("%s%s%s", paramsUtils.getUrlRoot(), suitableFileDirectory, fileName);
    }

    public static Response<Map<String, Object>> ApiFromBscs(Map<String, Object> data, String url) throws IOException {
        Response<Map<String, Object>> map = new Response<>();
        OkHttpClient client = new OkHttpClient();
        try {
//				System.out.println("Token Ici ----------> / " + generateTokenLite.get("access_token").toString());
            String queryString = new Gson().toJson(data);
            okhttp3.RequestBody body = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), queryString);
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("POST", body)
//						.addHeader("Authorization", "Bearer " + generateTokenLite.get("access_token").toString())
                    .addHeader("Content-Type", "application/json").build();
            okhttp3.Response resp = client.newCall(request).execute();
            String outputString = resp.body().string();
            Map<String, Object> maps = new Gson().fromJson(outputString, new TypeToken<Map<String, Object>>() {
            }.getType());

            if (maps != null) {
                System.out.println("RETOUR -----------" + maps);
                map.setHasError(Boolean.FALSE);
                map.setItem(maps);
                System.out.println(map);
                return map;
            }
        } catch (Exception e) {
            map.setHasError(Boolean.TRUE);
            e.printStackTrace();
        }
        return map;
//		}
//		return map;

    }

    public static Cell getCell(Row row, Integer index) {
        return (row.getCell(index) == null) ? row.createCell(index) : row.getCell(index);
    }

    public static String alerteConnexion(String nom, String login, String profil, String categorie) {
        LocalDateTime now = LocalDateTime.now();
        String alertMessage = persMessagelogin(now, nom, login, profil, categorie);
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
            LocalTime startTime = LocalTime.of(8, 0);
            LocalTime endTime = LocalTime.of(17, 0);
            if (now.toLocalTime().isBefore(startTime) || now.toLocalTime().isAfter(endTime)) {
                return alertMessage;
            }
        }else {
            return alertMessage;
        }
        return null;
    }

    public static String persMessagelogin(LocalDateTime localDateTime, String nom, String login, String profil, String categorie) {
        String messageAlerte = String.format("ALERT: Connexion en dehors des heures ouvrées\n\n" + "\n Date et heure de la connexion: %s\n" + "\n L'utilisateur %s a tenté de se connecter en dehors des heures ouvrées autorisées. La Tentative a eu lieu le %s. Veuillez vérifier cette activité.\n" + "\n LOGIN : %s\n" + " NOM: %s\n" + " PROFIL : %s\n" + "\n CATEGORY: %s", getDay(localDateTime), nom, localDateTime, login, nom, profil, categorie);
        return messageAlerte;
    }
    public static String generateSimswapAlert(LocalDateTime localDateTime, String nom, String numeroTelephone, String statusCode, String login, String statut) {
        String messageAlerte = String.format("ALERT: Changement de SIM initié par l'utilisateur %s \n\n"
                        + "Initié le : %s\n"
                        + "\nL'utilisateur %s a initié un changement d'Etat SIM \n"
                        + "\nNOM: %s\n"
                        + "\nStatusCode: %s\n"
                        + "\nLogin: %s\n"
                        + "\nNuméro ayant fait l’objet de %s: \n"
                        + "\nSTATUT: %s\n",
                nom,getDay(localDateTime), nom,statusCode,login,statusCode, numeroTelephone, statut);
        return messageAlerte;
    }

    public static String demandeGenerateSimswapAlert(LocalDateTime localDateTime, String nomDemandeur, String numeroTelephone, String statusCode, String statut) {
        String messageAlerte = String.format("ALERT: Demande de Changement de SIM initiée par l'utilisateur %s\n\n"
                        + "Date et heure de la demande : %s\n"
                        + "%s a soumis une demande de changement d'État SIM\n"
                        + "\nNom du Demandeur: %s\n"
                        + "\nCode de la demande: %s\n"
                        + "\nNuméro concerné par la demande : %s\n"
                        + "\nStatut de la demande: %s\n",
                nomDemandeur,nomDemandeur, getDay(localDateTime), nomDemandeur, statusCode, numeroTelephone, statut);
        return messageAlerte;
    }

    public static String validateGenerateDemandeSimswapAlert(LocalDateTime localDateTime, String nom, String numeroTelephone, String statusCode, String login, String nomPrenomValidateur, String loginValidateur, String statut) {
        String messageAlerte = String.format("ALERT: Valider un Changement de SIM initié par l'utilisateur %s \n\n"
                        + "Initié le : %s\n"
                        + "\nL'utilisateur %s a initié un changement d'Etat SIM \n"
                        + "\nNOM: %s\n"
                        + "\nStatusCode: %s\n"
                        + "\nLogin: %s\n"
                        + "\nNuméro ayant fait l’objet de %s: \n"
                        + "\nVALIDER PAR: %s\n"
                        + "\nLOGIN VALIDATEUR: %s\n"
                        + "\nSTATUT: %s\n",
                nom,getDay(localDateTime), nom,statusCode,login,statusCode, numeroTelephone,nomPrenomValidateur, loginValidateur, statut);
        return messageAlerte;
    }

    public static String messageCreationUtilisateur(LocalDateTime localDateTime, String nom, String login, String profil, String categorie, String createurNom, String createurLogin, String createurProfil, String createurCategorie) {
        String messageAlerte = String.format("ALERTE : CRÉATION D'UN NOUVEL UTILISATEUR\n\n" + "\nDate et heure de la création : %s\n" + "\n Un nouveau utilisateur a été créé avec les détails suivants :\n" + "LOGIN : %s\n" + "NOM : %s\n" + "PROFIL : %s\n" + "CATÉGORIE : %s\n\n" + "Créé par :\n" + "\n Nom du créateur : %s\n" + "\n Login du créateur : %s\n" + "\n Profil du créateur : %s\n" + "\n Catégorie du créateur : %s", getDay(localDateTime), login, nom, profil, categorie, createurNom, createurLogin, createurProfil, createurCategorie);
        return messageAlerte;
    }

    public static String messageVerrouillageUtilisateur(LocalDateTime localDateTime, String nomUtilisateur, String loginUtilisateur, String profilUtilisateur, String categorieUtilisateur, String verrouilleParNom, String verrouilleParLogin, String verrouilleParProfil, String verrouilleParCategorie) {
        String messageAlerte = String.format("ALERTE : UTILISATEUR VERROUILLÉ\n\n" + "Date et heure du verrouillage : %s\n" + "L'utilisateur suivant a été verrouillé :\n" + "LOGIN : %s\n" + "NOM : %s\n" + "PROFIL : %s\n" + "CATÉGORIE : %s\n\n" + "\n Verrouillé par :\n" + "Nom  personne ayant verrouillé : %s\n" + "\n Login  personne ayant verrouillé : %s\n" + "\n Profil  personne ayant verrouillé : %s\n" + "\n Catégorie  personne ayant verrouillé : %s", getDay(localDateTime), loginUtilisateur, nomUtilisateur, profilUtilisateur, categorieUtilisateur, verrouilleParNom, verrouilleParLogin, verrouilleParProfil, verrouilleParCategorie);
        return messageAlerte;
    }

	public static String messageTransfertCategorie(LocalDateTime localDateTime, String transfereParNom, String transfereParLogin, String transfereParProfil, String numero, String categoryInitial, String categoryDestination) {
		String messageAlerte = String.format("ALERTE : TRANSFERT DANS %s\n\n" + "\n Le : %s\n" + "\n Ce numero %s a été transféré par :\n" + "\n LOGIN : %s\n" + "\n NOM : %s\n" + "PROFIL : %s\n" + "\n CATÉGORIE INITIAL : %s\n" + "\n CATÉGORIE DE DESTINATION : %s\n\n", categoryDestination, getDay(localDateTime), numero, transfereParLogin, transfereParNom, transfereParProfil, categoryInitial, categoryDestination);
		return messageAlerte;
	}
    public static String messageValidationTransfertCategorie(LocalDateTime localDateTime, String validateurNom, String validateurLogin,String transfereParProfil, String numero, String categoryInitial, String categoryDestination) {
        String messageAlerte = String.format("ALERTE : VALIDATION DU TRANSFERT DANS %s\n\n"
                        + "Le : %s\n"
                        + "\nLa demande de transfert pour le numéro %s a été validée par :\n"
                        + "\nVALIDATEUR LOGIN : %s\n"
                        + "\nVALIDATEUR NOM : %s\n"
                        + "\nPROFIL : %s\n"
                        + "\nCATÉGORIE INITIAL : %s\n"
                        + "\nCATÉGORIE DE DESTINATION : %s\n\n",
                categoryDestination, getDay(localDateTime), numero, validateurLogin, validateurNom,transfereParProfil, categoryInitial, categoryDestination);
        return messageAlerte;
    }

	public static String messageModificationUtilisateur(LocalDateTime localDateTime, String nomUtilisateurModifie, String loginUtilisateurModifie, String profilUtilisateurModifie, String categorieUtilisateurModifie, String modifieParNom, String modifieParLogin, String modifieParProfil, String modifieParCategorie) {
        String messageAlerte = String.format("ALERTE : MODIFICATION D'UTILISATEUR\n\n" + "Date et heure de la modification : %s\n" + "Les détails de cet utilisateur ont été modifiés :\n" + "\n LOGIN : %s\n" + "\n NOM : %s\n" + "PROFIL : %s\n" + "\n CATÉGORIE : %s\n\n" + "\n Modifié par :\n" + " \n Nom personne ayant modifié : %s\n" + "Login  personne ayant modifié : %s\n" + "\n Profil  personne modifié : %s\n" + "\n Catégorie  personne ayant modifié : %s", getDay(localDateTime), loginUtilisateurModifie, nomUtilisateurModifie, profilUtilisateurModifie, categorieUtilisateurModifie, modifieParNom, modifieParLogin, modifieParProfil, modifieParCategorie);
        return messageAlerte;
    }

    public static String messageImportationFichier(LocalDateTime localDateTime, String nomFichierImporte, String cheminFichierImporte, String typeFichierImporte, String importeParNom, String importeParLogin, String importeParProfil, String importeParCategorie) {
        String messageAlerte = String.format("ALERTE : IMPORTATION DE FICHIER\n\n" +
                        "Date et heure de l'importation : %s\n" +
                        "Les détails du fichier importé sont les suivants :\n\n" +
                        "Nom du fichier : %s\n" +
                        "Chemin du fichier : %s\n" +
                        "Type de fichier : %s\n\n" +
                        "Importé par :\n\n" +
                        "Nom de la personne ayant importé : %s\n" +
                        "Login de la personne ayant importé : %s\n" +
                        "Profil de la personne ayant importé : %s\n" +
                        "Catégorie de la personne ayant importé : %s",
                getDay(localDateTime), nomFichierImporte, cheminFichierImporte, typeFichierImporte, importeParNom, importeParLogin, importeParProfil, importeParCategorie);
        return messageAlerte;
    }

    public static String operationEnMasse(String libelleOperation,LocalDateTime localDateTime, String nomFichierImporte, String cheminFichierImporte, String typeFichierImporte, String importeParNom, String importeParLogin, String importeParProfil, String importeParCategorie) {
        String messageAlerte = String.format("ALERTE : OPÉRATION EN MASSE DE : %s \n\n" +
                        "Date et heure de l'importation : %s\n" +
                        "Les détails du fichier importé sont les suivants :\n\n" +
                        "Fichier D'origine : %s\n" +
                        "Fichier Resultat: %s\n" +
                        "Type de fichier : %s\n\n" +
                        "Opération effectué par : %s\n" +
                        "Login  : %s\n" +
                        "Profil : %s\n" +
                        "Catégorie  : %s",
                libelleOperation,getDay(localDateTime), nomFichierImporte, cheminFichierImporte, typeFichierImporte, importeParNom, importeParLogin, importeParProfil, importeParCategorie);
        return messageAlerte;
    }

    public static String messageDeverrouillageUtilisateur(LocalDateTime localDateTime, String nomUtilisateurDeverrouille, String loginUtilisateurDeverrouille, String profilUtilisateurDeverrouille, String categorieUtilisateurDeverrouille, String deverrouilleParNom, String deverrouilleParLogin, String deverrouilleParProfil, String deverrouilleParCategorie) {
        String messageAlerte = String.format("ALERTE : UTILISATEUR DÉVERROUILLÉ\n\n" + " \n Date et heure du déverrouillage : %s\n" + " \n L'utilisateur suivant a été déverrouillé :\n" + "LOGIN : %s\n" + "NOM : %s\n" + "PROFIL : %s\n" + "CATÉGORIE : %s\n\n" + "Déverrouillé par :\n" + "Nom personne ayant déverrouillé : %s\n" + "Login personne ayant déverrouillé : %s\n" + "Profil  personne ayant déverrouillé : %s\n" + "Catégorie personne ayant déverrouillé : %s", localDateTime, loginUtilisateurDeverrouille, nomUtilisateurDeverrouille, profilUtilisateurDeverrouille, categorieUtilisateurDeverrouille, deverrouilleParNom, deverrouilleParLogin, deverrouilleParProfil, deverrouilleParCategorie);
        return messageAlerte;
    }


    private static String getDay(LocalDateTime currentDateTime) {
        // Formatez la date et l'heure en utilisant un DateTimeFormatter personnalisé
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy HH 'heures' mm 'minutes' ss 'secondes'", Locale.FRENCH);
        String formattedDateTime = currentDateTime.format(formatter);
        // Mettez la première lettre du jour en majuscule
        formattedDateTime = formattedDateTime.substring(0, 1).toUpperCase() + formattedDateTime.substring(1);
        // Affichez la date et l'heure formatées
        return formattedDateTime;
    }
}
