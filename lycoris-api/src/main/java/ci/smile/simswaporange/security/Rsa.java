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
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.security.crypto.codec.Base64;

/**
 *
 * @author FOSSOU
 */
public class Rsa {

	public Rsa() {
		System.out.println("on est ici");
		if (!areKeysPresent()) {
			// Method generates a pair of keys using the RSA algorithm and
			// stores it
			// in their respective files
//			 generateKey();
		}
	}

	public static String encrypt(byte[] textBytes, PublicKey publicKey, Cipher rsaCipher)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {
		// //get the public key
		// PublicKey pk = pair.getPublic();
		// Initialize the cipher for encryption. Use the public key.
		rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// Perform the encryption using doFinal
		byte[] encByte = rsaCipher.doFinal(textBytes);
		// converts to base64 for easier display.
		byte[] base64Cipher = Base64.encode(encByte);

		return new String(base64Cipher);
	}// end encrypt

	public static String decrypt(byte[] cipherBytes, PrivateKey privateKey, Cipher rsaCipher)
			throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		// //get the public key
		// PrivateKey pvk = pair.getPrivate();

		// Create a Cipher object
		// Cipher rsaCipher = Cipher.getInstance("RSA/ECB/NoPadding");
		// Initialize the cipher for encryption. Use the public key.
		rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);

		// Perform the encryption using doFinal
		byte[] decByte = rsaCipher.doFinal(cipherBytes);

		return new String(decByte);

	}// end decrypt

	public static void generateKey() {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(CustomerCareUtils.ALGORITHM_RSA);
			keyGen.initialize(Integer.parseInt(CustomerCareUtils.BIT));
			final KeyPair key = keyGen.generateKeyPair();
			// System.out.println("key"+key);
//			File privateKeyFile = new File(Rsa.class.getClassLoader().getResource("private.key").getFile());
//			File publicKeyFile = new File(Rsa.class.getClassLoader().getResource("public.key").getFile());
			File privateKeyFile = new File("/home/lenovo/Documents/Formation/Keys/private.key");
			File publicKeyFile = new File("/home/lenovo/Documents/Formation/Keys/public.key");
			System.out.println("continue");
			// Create files to store public and private key
			if (privateKeyFile.getParentFile() != null) {
				System.out.println("test");
				privateKeyFile.getParentFile().mkdirs();
				System.out.println("test2");
			}

			privateKeyFile.createNewFile();

			if (publicKeyFile.getParentFile() != null) {
				publicKeyFile.getParentFile().mkdirs();
			}
			publicKeyFile.createNewFile();

			// Saving the Public key in a file
			ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
			publicKeyOS.writeObject(key.getPublic());
			System.out.println("key.getPublic()" + key.getPublic());
			publicKeyOS.close();

			// Saving the Private key in a file
			ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
			privateKeyOS.writeObject(key.getPrivate());
			System.out.println("key.getPrivate()" + key.getPrivate());
			privateKeyOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean areKeysPresent() {
		File privateKey = new File("/home/lenovo/Documents/Formation/Keys/private.key");
		File publicKey = new File("/home/lenovo/Documents/Formation/Keys/public.key");

		if (privateKey.exists() && publicKey.exists()) {
			return true;
		}
		return false;
	}

	public PublicKey getPublicKey() {
		PublicKey publicKey = null;
		try {
			ObjectInputStream inputStream = null;
			// Encrypt the string using the public key
			inputStream = new ObjectInputStream(new FileInputStream(
					Thread.currentThread().getContextClassLoader().getResource("public.key").getFile()));
			// inputStream = new ObjectInputStream(new maos 
			// FileInputStream(getClass().getClassLoader().getResource("public.key").getFile()));
			publicKey = (PublicKey) inputStream.readObject();
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		PrivateKey privateKey = null;
		try {
			ObjectInputStream inputStream = null;
			// Encrypt the string using the public key
			inputStream = new ObjectInputStream(
					new FileInputStream(getClass().getClassLoader().getResource("private.key").getFile()));
			privateKey = (PrivateKey) inputStream.readObject();
			if (inputStream != null) {
				inputStream.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return privateKey;
	}

}
