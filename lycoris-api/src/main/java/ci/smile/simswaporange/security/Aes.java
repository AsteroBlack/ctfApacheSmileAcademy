/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.smile.simswaporange.security;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.codec.Base64;





/**
 *
 * @author FOSSOU
 */
public class Aes {

    public Aes() throws NoSuchAlgorithmException {

    } 

    public static String encrypt(byte[] textBytes, SecretKeySpec secretKey, Cipher aesCipher) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
//        //get the public key
//        PublicKey pk = pair.getPublic();

        //Initialize the cipher for encryption. Use the secret key.
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);

        //Perform the encryption using doFinal
        byte[] encByte = aesCipher.doFinal(textBytes);

        // converts to base64 for easier display.
        byte[] base64Cipher = Base64.encode(encByte);

        return new String(base64Cipher);
    }//end encrypt

    public static String decrypt(byte[] cipherBytes, SecretKeySpec secretKey, Cipher aesCipher) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
//        //get the public key
//        PrivateKey pvk = pair.getPrivate();

        //Create a Cipher object
        //Cipher rsaCipher = Cipher.getInstance("RSA/ECB/NoPadding");
        //Initialize the cipher for encryption. Use the public key.
        aesCipher.init(Cipher.DECRYPT_MODE, secretKey);

        //Perform the encryption using doFinal
        byte[] decByte = aesCipher.doFinal(cipherBytes);

        return new String(decByte);

    }

    public static AlgorithmParameterSpec makeIv(String codeIv) {
        try {
            return new IvParameterSpec(codeIv.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void generateKey() {
        try {
            String valeurCle = "";

            KeyGenerator keyGen = KeyGenerator.getInstance(CustomerCareUtils.ALGORITHM_AES);
            keyGen.init(Integer.parseInt(CustomerCareUtils.BITS));
            SecretKey cle = keyGen.generateKey();
            valeurCle = new String(Base64.encode(cle.getEncoded()));
            File KeyAes = new File("");
            System.out.println("continue");
            // Create files to store public and private key
            if (KeyAes.getParentFile() != null) {
                System.out.println("test");
                KeyAes.getParentFile().mkdirs();
                System.out.println("test2");
            }

            KeyAes.createNewFile();

            // Saving the Private key in a file
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                    new FileOutputStream(KeyAes));
            privateKeyOS.writeObject(valeurCle);
            privateKeyOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean areKeysPresent() {

        File symetricKey = new File("");
        if (symetricKey.exists()) {
            System.out.println("bon");
            return true;
        }
        System.out.println("pas bon");
        return false;
    }

    private void createNewKey() {
        if (!areKeysPresent()) {
            generateKey();
        }
    }

    public String getAesKey() {
        return CustomerCareUtils.KEY_AES;
    }

    public String getAesKeyFile() {
        String key = null;
        try {
            createNewKey();
            ObjectInputStream inputStream = null;
            // Encrypt the string using the public key
            inputStream = new ObjectInputStream(new FileInputStream(""));
            key = (String) inputStream.readObject();
            System.out.println("keyRead" + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public static String generateKeyAes() {
        String valeurCle = "";
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(CustomerCareUtils.ALGORITHM_AES);
            keyGen.init(Integer.parseInt(CustomerCareUtils.BITS));
            SecretKey cle = keyGen.generateKey();
            valeurCle = new String(Base64.encode(cle.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valeurCle;
    }
}
