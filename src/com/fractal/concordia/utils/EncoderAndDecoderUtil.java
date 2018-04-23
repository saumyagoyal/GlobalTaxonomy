package com.fractal.concordia.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncoderAndDecoderUtil {

	private final static String secretKey = "fms is damn awesome";
	private static Cipher dcipher = null;
	private static Cipher ecipher = null;
	private static final Logger log = Logger.getLogger(EncoderAndDecoderUtil.class.getName());
	// 8-byte Salt
	private final static byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35,
			(byte) 0xE3, (byte) 0x03 };
	// Iteration count
	private final static int iterationCount = 19;
	private static Key key;
	private static KeyGenerator generator;

	/**
	 * 1. Base64 encoding
	 *
	 * 2.PBEWithMD5AndDES
	 *
	 * 3.DES/ECB/PKCS5Padding
	 *
	 * */
	public static String encode(String string, String encodingName) {
		String encodedString = "";
		if (encodingName.equalsIgnoreCase("base64")) {
			byte[] byteArray = Base64.encodeBase64(string.getBytes());
			encodedString = new String(byteArray);

		} else if (encodingName.equalsIgnoreCase("PBEWithMD5AndDES")) {
			// Key generation for enc and desc
			KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
			SecretKey key = null;
			String charSet = "UTF-8";
			byte[] in = null;
			byte[] out = null;
			try {
				key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
				AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
				ecipher = Cipher.getInstance(key.getAlgorithm());
				ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
				in = string.getBytes(charSet);
				out = ecipher.doFinal(in);
			} catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
					| InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException
					| BadPaddingException e) {
				log.error("encode :" + e.getMessage());
			}
			encodedString = new Base64().encodeAsString(out);
			if (encodedString.indexOf("/", 0) > -1) {
				encodedString = encodedString.replace('/', '(');
			}
		} else if (encodingName.equalsIgnoreCase("DES/ECB/PKCS5Padding")) {
			try {
				// Get a cipher object.
				Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, generateKey());

				// Gets the raw bytes to encrypt, UTF8 is needed for
				// having a standard character set
				byte[] stringBytes = string.getBytes("UTF8");

				// encrypt using the cypher
				byte[] raw = cipher.doFinal(stringBytes);

				// converts to base64 for easier display.
				BASE64Encoder encoder = new BASE64Encoder();
				encodedString = encoder.encode(raw);
			} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
					| UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
				log.error("encode :" + e.getMessage());
			}

		}
		return encodedString;
	}

	/**
	 * 1. Base64 encoding
	 *
	 * 2.PBEWithMD5AndDES
	 *
	 * 3.DES/ECB/PKCS5Padding
	 *
	 * */
	public static String decode(String decode, String decodingName) {
		String decoder = "";
		if (decodingName.equalsIgnoreCase("base64")) {
			byte[] decoded = Base64.decodeBase64(decode);
			try {
				decoder = new String(decoded, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error("decode :" + e.getMessage());
			}
		} else if (decodingName.equalsIgnoreCase("PBEWithMD5AndDES")) {
			// Key generation for enc and desc
			KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
			SecretKey key;
			try {
				key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
				// Prepare the parameter to the ciphers
				AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
				// Decryption process; same key will be used for decr
				dcipher = Cipher.getInstance(key.getAlgorithm());
				dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
				if (decode.indexOf("(", 0) > -1) {
					decode = decode.replace('(', '/');
				}
				byte[] enc = Base64.decodeBase64(decode);
				byte[] utf8 = dcipher.doFinal(enc);
				String charSet = "UTF-8";
				String plainStr = new String(utf8, charSet);
				return plainStr;
			} catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
					| IOException e) {
				log.error("decode :" + e.getMessage());
			}
		} else if (decodingName.equalsIgnoreCase("DES/ECB/PKCS5Padding")) {
			// Get a cipher object.
			Cipher cipher;
			try {
				cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, generateKey());
				// decode the BASE64 coded message
				BASE64Decoder decoder1 = new BASE64Decoder();
				byte[] raw = decoder1.decodeBuffer(decode);
				// decode the message
				byte[] stringBytes = cipher.doFinal(raw);
				// converts the decoded message to a String
				decoder = new String(stringBytes, "UTF8");
			} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException
					| IllegalBlockSizeException | BadPaddingException e) {
				log.error("decode :" + e.getMessage());
			}

		}
		return decoder;
	}

	/**
	 * Generates the encryption key. using "des" algorithm
	 *
	 * @throws NoSuchAlgorithmException
	 */
	private static Key generateKey() throws NoSuchAlgorithmException {
		if (key == null) {
			generator = KeyGenerator.getInstance("DES");
			generator.init(new SecureRandom());
			key = generator.generateKey();
		}
		return key;
	}
}
