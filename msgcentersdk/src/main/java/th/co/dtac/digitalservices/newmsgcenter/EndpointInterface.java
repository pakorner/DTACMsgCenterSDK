package th.co.dtac.digitalservices.newmsgcenter;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import th.co.dtac.digitalservices.newmsgcenter.response.RespMessage;
import th.co.dtac.digitalservices.newmsgcenter.response.TestResponseModel;

public interface EndpointInterface {

    @GET("path")
    Call<TestResponseModel> registerToken(@Query("token") String token);

//    @FormUrlEncoded
//    @POST("path")
//    @Headers({"Example: value", "Example value"})
//    void registerTokenPost(@Field("token") String token, @Header("Authorization") String bearerToken);

    @FormUrlEncoded
    @POST("register")
    Call<RespMessage> msgcenterRegister(@FieldMap Map<String, String> hRegister);
}
