package th.co.dtac.digitalservices.msgcenter.utils;

import android.content.Context;
import android.util.Base64;

import com.google.gson.JsonParser;

import org.apache.commons.codec.binary.Hex;

import java.io.InputStream;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import th.co.dtac.digitalservices.msgcenter.model.NSHash;

/**
 * Created by Llvve on 3/21/2017 AD.
 */

public class Utils {

    private static String getSecret(Context context) {
        try {
            if (Shared.getSecretKey(context).equals("")) {
                InputStream inputStream = context.getAssets().open("secret.txt");
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                inputStream.close();
                Shared.commitSecretKey(context, new String(bytes));
            }
            return new JsonParser().parse(new String(Base64.decode(Shared.getSecretKey(context), Base64.DEFAULT))).getAsJsonObject().get("secret").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String createSignature(Context context, String data, String nonce) {
        char[] passwordChar = getSecret(context).toCharArray();
        for (int i = 0; i < passwordChar.length; i++) {
            nonce = nonce.substring(0, (i + 1) * 2) + passwordChar[passwordChar.length - (i + 1)] + nonce.substring((i + 1) * 2);
        }

        try {
            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");

            SecretKeySpec secretKeySpec = new SecretKeySpec(nonce.getBytes("UTF-8"), "HmacSHA256");
            hmacSHA256.init(secretKeySpec);

            return new String(Hex.encodeHex(hmacSHA256.doFinal(data.getBytes("UTF-8"))));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String createNonce() {
        String nonce = String.valueOf(System.currentTimeMillis());
        String text = "";
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for(int i = 0; i < nonce.length(); i++ ) {
            text += possible.charAt((int)Math.floor(Math.random() * possible.length()));
            text += nonce.charAt((int)Math.floor(Math.random() * nonce.length()));
        }
        return text;
    }

    public static NSHash createNSHash(Context context, String data) {
        String nonce = Utils.createNonce();
        String signature = Utils.createSignature(context, data, nonce);
        return new NSHash(nonce, signature);
    }

}
