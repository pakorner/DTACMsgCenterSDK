package th.co.dtac.digitalservices.msgcenter.utils;

import android.util.Base64;

import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class SubrNumbDecrypter {
	private static final String CIPHER_METHOD = "DESede/CBC/PKCS5Padding";
	private KeySpec keySpec;
	private SecretKey key;
	private IvParameterSpec iv;

	public SubrNumbDecrypter() {
		init("62v01fVsCWHfRcW");
	}

	public SubrNumbDecrypter(String keyString) {
		init(keyString);
	}

	private void init(String keyString) {
		try {
			final byte[] keyBytes = Arrays.copyOf(keyString.getBytes("utf-8"), 24);
			keySpec = new DESedeKeySpec(keyBytes);
			key = SecretKeyFactory.getInstance("DESede").generateSecret(keySpec);
			iv = new IvParameterSpec(new byte[8]);
		} catch (Exception e) {
			// TODO log
		}
	}

	public String decrypt(String encryptedSnumb) throws Exception {
		Cipher dcipher = Cipher.getInstance(CIPHER_METHOD);
		dcipher.init(Cipher.DECRYPT_MODE, key, iv);

		if (encryptedSnumb == null)
			return null;

		// Decode base64 to get bytes
		byte[] dec = Base64.decode(encryptedSnumb.replaceAll("-", "+").replaceAll("_", "/"), Base64.DEFAULT);

		// Decrypt
		byte[] utf8 = dcipher.doFinal(dec);

		// Decode using utf-8
		return new String(utf8, "UTF8");
	}
}