package th.co.dtac.digitalservices.newmsgcenter;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static th.co.dtac.digitalservices.newmsgcenter.ServiceProperties.BASE_URL;

public class ApiClient {

    private static final ApiClient ourInstance = new ApiClient();

    public static ApiClient getInstance() {
        return ourInstance;
    }

    private ApiClient() {
    }

    private EndpointInterface endpointInterface = null;

    public EndpointInterface getEndpointInterface() {
        if (endpointInterface == null) {
            endpointInterface = createEndpointInterface();
        }
        return endpointInterface;
    }

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

    private EndpointInterface createEndpointInterface() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(createOkhttpClient())
                .build();
        return retrofit.create(EndpointInterface.class);
    }
}
