package th.co.dtac.digitalservices.newmsgcenter;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import th.co.dtac.digitalservices.msgcenter.BuildConfig;
import th.co.dtac.digitalservices.msgcenter.core.GsonStringConverterFactory;
import th.co.dtac.digitalservices.msgcenter.model.NSHash;
import th.co.dtac.digitalservices.msgcenter.utils.Utils;
import th.co.dtac.digitalservices.newmsgcenter.request.SendStatisticRequestModel;
import th.co.dtac.digitalservices.newmsgcenter.response.RespMessage;

import static th.co.dtac.digitalservices.newmsgcenter.ServiceProperties.BASE_URL;
import static th.co.dtac.digitalservices.newmsgcenter.ServiceProperties.BASE_URL_DEV;

public class ApiClient {

    private static final ApiClient ourInstance = new ApiClient();

    public static ApiClient getInstance() {
        return ourInstance;
    }

    private ApiClient() {
    }

    private EndpointInterface endpointInterface = null;

    private Retrofit getRetrofit(boolean isTest) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        return new Retrofit.Builder()
                .baseUrl(isTest ? BASE_URL_DEV : BASE_URL)
                .client(client)
                .addConverterFactory(new GsonStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void register(Boolean isTest, Context context, String phoneEncrypt, String device, String telType,
                         String osVersion, String model, String appName, String appVersionName,
                         String appVersionCode, String sdkVersionCode, String udid,
                         String dtacRegisterDate, String lang, String token, String dtacid,
                         String lat, String lng, Boolean isAcceptPush,
                         Callback<RespMessage> callback) {
        Retrofit retrofit = getRetrofit(isTest);
        EndpointInterface endpointInterface = retrofit.create(EndpointInterface.class);

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
        hMap.put("lat", lat);
        hMap.put("long", lng);

        Call<RespMessage> call = endpointInterface.msgcenterRegister(hMap, isAcceptPush);
        call.enqueue(callback);
    }

    private String getCredentials(boolean isTest) {
        if (isTest) {
            return Credentials.basic("noti", "notitestpassword");
        } else {
            return Credentials.basic("noti", "N0t!MsgCenter");
        }
    }

    public void updateConsent(Boolean isTest, Context context, String phoneEncrypt, String device, String telType,
                              String osVersion, String model, String appName, String appVersionName,
                              String appVersionCode, String sdkVersionCode, String udid,
                              String dtacRegisterDate, String lang, String token, String dtacid,
                              String lat, String lng, Boolean isAcceptConsent, Boolean isAcceptPush,
                              Callback<RespMessage> callback) {
        Retrofit retrofit = getRetrofit(isTest);
        EndpointInterface endpointInterface = retrofit.create(EndpointInterface.class);

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
        hMap.put("lat", lat);
        hMap.put("long", lng);

        Call<RespMessage> call = endpointInterface.msgcenterUpdateConsent(hMap, isAcceptConsent, isAcceptPush);
        call.enqueue(callback);
    }

    public void sendStatisticNotificationAsync(boolean isTest, String messageId, String dtacId, String status, Callback<RespMessage> callback) {
        Retrofit retrofit = getRetrofit(isTest);
        EndpointInterface endpointInterface = retrofit.create(EndpointInterface.class);

        SendStatisticRequestModel requestModel = new SendStatisticRequestModel();
        requestModel.setMessageId(messageId)
                .setDtacId(dtacId)
                .setStatus(status);

        Call<RespMessage> call = endpointInterface.sendStatisticNotification(getCredentials(isTest), requestModel);
        call.enqueue(callback);
    }

    public void sendStatisticNotificationSync(boolean isTest, String messageId, String dtacId, String status) {
        Retrofit retrofit = getRetrofit(isTest);
        EndpointInterface endpointInterface = retrofit.create(EndpointInterface.class);

        SendStatisticRequestModel requestModel = new SendStatisticRequestModel();
        requestModel.setMessageId(messageId)
                .setDtacId(dtacId)
                .setStatus(status);

        Call<RespMessage> call = endpointInterface.sendStatisticNotification(getCredentials(isTest), requestModel);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getVersion() {
        return BuildConfig.VERSION_NAME;
    }
}
