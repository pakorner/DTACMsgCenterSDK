package th.co.dtac.digitalservices.msgcenter.core;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import th.co.dtac.digitalservices.msgcenter.model.NSHash;
import th.co.dtac.digitalservices.msgcenter.model.RespHistory;
import th.co.dtac.digitalservices.msgcenter.model.RespMessage;
import th.co.dtac.digitalservices.msgcenter.utils.Utils;

/**
 * Created by Llvve on 3/21/2017 AD.
 */

public class APIWrapper {

    public static final String BASE_URL = "http://ddos.dtac.co.th:3000/";

    public static final String SUB_ACTIONP_NOTIFICATION = "notification";
    public static final String SUB_ACTIONP_INBOX = "inbox";
    public static final String ACTIONP_OPEN_BY = "open_by";

    private static APIWrapper instance = null;

    private APIWrapper() {
        // Exists only to defeat instantiation.
    }

    public static APIWrapper getInstance() {
        if(instance == null) {
            instance = new APIWrapper();
        }
        return instance;
    }

    public void getHistory(Context context, String phoneEncrypt, int page, int limit, Callback<RespHistory> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(new GsonStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpointInterface apiInterface = retrofit.create(ApiEndpointInterface.class);

        NSHash nsHash = Utils.createNSHash(context, "limitnoncepagephonenumbersignature");

        Call<RespHistory> call = apiInterface.getHistory(phoneEncrypt, page, limit, nsHash.getNonce(), nsHash.getSignature());
        call.enqueue(callback);

    }

    public void register(Context context, String phoneEncrypt, String device, String telType,
                         String osVersion, String model, String appName, String appVersionName,
                         String appVersionCode, String sdkVersionCode, String udid,
                         String dtacRegisterDate, String lang, String token, String dtacid,
                         Callback<RespMessage> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(new GsonStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpointInterface apiInterface = retrofit.create(ApiEndpointInterface.class);

        NSHash nsHash = Utils.createNSHash(context,
        "appnameappversioncodeappversionnamedevicedtaciddtacregisterdatelangmodelnonceosversionphonenumbersdkversioncodesignatureteltypetokenudid");

        HashMap<String, String> hMap = new HashMap<>();
        hMap.put("phone_number", phoneEncrypt);
        hMap.put("device", device);
        hMap.put("dtac_id", dtacid);
        hMap.put("tel_type", telType);
        hMap.put("os_version", osVersion);
        hMap.put("model", model);
        hMap.put("app_name", appName);
        hMap.put("app_version_name", appVersionName);
        hMap.put("app_version_code", appVersionCode);
        hMap.put("sdk_version_code", sdkVersionCode);
        hMap.put("udid", udid);
        hMap.put("dtac_register_date", dtacRegisterDate);
        hMap.put("lang", lang);
        hMap.put("token", token);
        hMap.put("nonce", nsHash.getNonce());
        hMap.put("signature", nsHash.getSignature());

        Call<RespMessage> call = apiInterface.msgcenterRegister(hMap);
        call.enqueue(callback);
    }

    public void getUpdateHistory(Context context, ArrayList<String> ids , boolean isRead, boolean isVisibility, Callback<RespMessage> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(new GsonStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpointInterface apiInterface = retrofit.create(ApiEndpointInterface.class);

        NSHash nsHash = Utils.createNSHash(context, "limitnoncepagephonenumbersignature");

        Call<RespMessage> call = apiInterface.msgcenterUpdate(ids, isVisibility, isRead, nsHash.getNonce(), nsHash.getSignature());
        call.enqueue(callback);
    }

    public void getUpdateAction(Context context, String id , String action, String subAction, Callback<RespMessage> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(new GsonStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpointInterface apiInterface = retrofit.create(ApiEndpointInterface.class);

        NSHash nsHash = Utils.createNSHash(context, "limitnoncepagephonenumbersignature");

        Call<RespMessage> call = apiInterface.msgcenterUpdateAction(id, action, subAction, nsHash.getNonce(), nsHash.getSignature());
        call.enqueue(callback);
    }

}
