/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ci.smile.simswaporange.security;

import java.nio.charset.Charset;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

/**
 *
 * @author FOSSOU
 */
public class ManageAes {

    public ManageAes() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());   
    }

    public String encryptAes(String data_a_encoded, String secretCode) {
        String data_encoded = "";
        try {
            Cipher cipherAes = Cipher.getInstance(CustomerCareUtils.ALGORITHM_AES_ECB_PKCS7, "BC");
            byte[] decodedKey = Base64.decode(secretCode.getBytes(Charset.forName("UTF-8")));
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, CustomerCareUtils.ALGORITHM_AES);
            data_encoded = Aes.encrypt(data_a_encoded.getBytes(Charset.forName("UTF-8")), secretKey, cipherAes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data_encoded;
    }
    
    public String decryptAes(String data_a_encoded, String secretCode) {
        String data_decoded = "";
        try {
            Cipher cipherAes = Cipher.getInstance(CustomerCareUtils.ALGORITHM_AES_ECB_PKCS7, "BC");
            byte[] decodedKey = Base64.decode(secretCode.getBytes(Charset.forName("UTF-8")));
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, CustomerCareUtils.ALGORITHM_AES);
            data_decoded = Aes.decrypt(Base64.decode(data_a_encoded.getBytes(Charset.forName("UTF-8"))), secretKey, cipherAes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data_decoded;
    }
}
