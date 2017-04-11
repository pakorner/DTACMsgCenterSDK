package th.co.dtac.digitalservices.msgcenter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public abstract class Shared {

    private static final String DTAC_MSGCENTER_PREF = "DTAC_MSGCENTER";
    private static final String DTAC_MSGCENTER_PREF_FCM_TOKEN = "DTAC_MSGCENTER_FCM_TOKEN";
    private static final String DTAC_MSGCENTER_PREF_LANG = "DTAC_MSGCENTER_LANG";
    private static final String DTAC_MSGCENTER_PREF_MOCK_UDID = "DTAC_MSGCENTER_MOCK_UDID";
    private static final String DTAC_MSGCENTER_PREF_SECRET_KEY = "DTAC_MSGCENTER_SECRET_KEY";

    public static void commitFcmToken(Context context, String token) {
        SharedPreferences preference = context.getSharedPreferences(DTAC_MSGCENTER_PREF, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putString(DTAC_MSGCENTER_PREF_FCM_TOKEN, token);
        editor.apply();
    }

    public static String getFcmToken(Context context) {
        SharedPreferences preference = context.getSharedPreferences(DTAC_MSGCENTER_PREF, Context.MODE_PRIVATE);
        return preference.getString(DTAC_MSGCENTER_PREF_FCM_TOKEN, "");
    }

    public static void commitLang(Context context, String lang) {
        SharedPreferences preference = context.getSharedPreferences(DTAC_MSGCENTER_PREF, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putString(DTAC_MSGCENTER_PREF_LANG, lang);
        editor.apply();
    }

    public static String getLang(Context context) {
        SharedPreferences preference = context.getSharedPreferences(DTAC_MSGCENTER_PREF, Context.MODE_PRIVATE);
        return preference.getString(DTAC_MSGCENTER_PREF_LANG, "en");
    }

    public static void commitMockUDID(Context context, String mockUdid) {
        SharedPreferences preference = context.getSharedPreferences(DTAC_MSGCENTER_PREF, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putString(DTAC_MSGCENTER_PREF_MOCK_UDID, mockUdid);
        editor.apply();
    }

    public static String getMockUDID(Context context) {
        SharedPreferences preference = context.getSharedPreferences(DTAC_MSGCENTER_PREF, Context.MODE_PRIVATE);
        return preference.getString(DTAC_MSGCENTER_PREF_MOCK_UDID, "android" + String.valueOf(System.currentTimeMillis()));
    }

    public static void commitSecretKey(Context context, String secretKey) {
        SharedPreferences preference = context.getSharedPreferences(DTAC_MSGCENTER_PREF, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putString(DTAC_MSGCENTER_PREF_SECRET_KEY, secretKey);
        editor.apply();
    }

    public static String getSecretKey(Context context) {
        SharedPreferences preference = context.getSharedPreferences(DTAC_MSGCENTER_PREF, Context.MODE_PRIVATE);
        return preference.getString(DTAC_MSGCENTER_PREF_SECRET_KEY, "");
    }

}