package th.co.dtac.digitalservices.msgcenter.utils;

import android.util.Base64;

import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class SubrNumbEncrypter {
	private static final String CIPHER_METHOD = "DESede/CBC/PKCS5Padding";
	private KeySpec keySpec;
	private SecretKey key;
	private IvParameterSpec iv;

	public SubrNumbEncrypter() {
		init("62v01fVsCWHfRcW");
	}

	public SubrNumbEncrypter(String keyString) {
		init(keyString);
	}

	private void init(String keyString) {
		try {
			final byte[] keyBytes = Arrays.copyOf(keyString.getBytes("utf-8"), 24);
			keySpec = new DESedeKeySpec(keyBytes);
			key = SecretKeyFactory.getInstance("DESede").generateSecret(keySpec);
			iv = new IvParameterSpec(new byte[8]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String encrypt(String value) {
		try {
			Cipher ecipher = Cipher.getInstance(CIPHER_METHOD);
			ecipher.init(Cipher.ENCRYPT_MODE, key, iv);

			if (value == null)
				return null;

			// Encode the string into bytes using utf-8
			byte[] utf8 = value.getBytes("UTF8");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);
			// Encode bytes to base64 to get a string
//			return java.net.URLEncoder.encode(Base64.encodeToString(enc, Base64.DEFAULT).replaceAll("\\+", "-").replaceAll("/", "_").replaceAll("\n",""),"UTF-8");
            return Base64.encodeToString(enc, Base64.DEFAULT).replaceAll("\\+", "-").replaceAll("/", "_").replaceAll("\n","");
//			return new String(Base64Coder.encode(enc).replaceAll("\\+", "-").replaceAll("/", "_");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}