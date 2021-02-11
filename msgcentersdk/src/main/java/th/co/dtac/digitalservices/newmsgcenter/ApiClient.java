package th.co.dtac.digitalservices.newmsgcenter;

import android.content.Context;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import th.co.dtac.digitalservices.msgcenter.core.GsonStringConverterFactory;
import th.co.dtac.digitalservices.msgcenter.model.NSHash;
import th.co.dtac.digitalservices.msgcenter.utils.Utils;
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

//    public EndpointInterface getEndpointInterface( ) {
//        if (endpointInterface == null) {
//            endpointInterface = createEndpointInterface();
//        }
//        return endpointInterface;
//    }

//    private HttpLoggingInterceptor getHttpLogginInterceptor() {
//        HttpLoggingInterceptor logginInterceptor = new HttpLoggingInterceptor();
//        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        return logginInterceptor;
//    }

    private OkHttpClient createOkhttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(getHttpLogginInterceptor())
                .build();
        return client;
    }

//    private EndpointInterface createEndpointInterface() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
////                .client(createOkhttpClient())
//                .build();
//        return retrofit.create(EndpointInterface.class);
//    }

    public void register(Boolean isTest, Context context, String phoneEncrypt, String device, String telType,
                         String osVersion, String model, String appName, String appVersionName,
                         String appVersionCode, String sdkVersionCode, String udid,
                         String dtacRegisterDate, String lang, String token, String dtacid,
                         String lat, String lng, Boolean isAcceptConsent, Boolean isAcceptPush,
                         Callback<RespMessage> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(isTest ? BASE_URL_DEV : BASE_URL)
                .addConverterFactory(new GsonStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
        hMap.put("lng", lng);

        Call<RespMessage> call = endpointInterface.msgcenterRegister(hMap, isAcceptConsent, isAcceptPush);
        call.enqueue(callback);
    }
}
