package th.co.dtac.digitalservices.newmsgcenter;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import th.co.dtac.digitalservices.newmsgcenter.response.TestResponseModel;

public interface EndpointInterface {

    @GET("path")
    Call<TestResponseModel> registerToken(@Query("token") String token);

    @FormUrlEncoded
    @POST("path")
    void registerTokenPost(@Field("token") String token);
}
