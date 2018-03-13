package th.co.dtac.digitalservices.msgcenter.core;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import th.co.dtac.digitalservices.msgcenter.model.RespHistory;
import th.co.dtac.digitalservices.msgcenter.model.RespMessage;

/**
 * Created by Llvve on 10/7/15 AD.
 */
public interface ApiEndpointInterface {

    @FormUrlEncoded
    @POST("/api/msgcenter/register")
    Call<RespMessage> msgcenterRegister(@FieldMap Map<String, String> hRegister);

//    @FormUrlEncoded
//    @POST("/api/msgcenter/send")
//    Call<RespMessage> msgcenterSend(@FieldMap Map<String, String> hSend);

    @GET("/api/msgcenter/user_history_list")
    Call<RespHistory> getHistory(@Query("phone_number") String phone_number, @Query("page") int page, @Query("limit") int limit,
                                 @Query("nonce") String nonce, @Query("signature") String signature);

    @FormUrlEncoded
    @POST("/api/msgcenter/history/user/update")
    Call<RespMessage> msgcenterUpdate(@Query("ids") ArrayList<String> ids, @Query("visibility") boolean isVisibility, @Query("is_read") boolean isRead,
                                      @Query("nonce") String nonce, @Query("signature") String signature);

    @FormUrlEncoded
    @POST("/api/msgcenter/history/loadlist/update_action")
    Call<RespMessage> msgcenterUpdateAction(@Query("id") String id, @Query("action") String action, @Query("sub_action") String subAction,
                                            @Query("nonce") String nonce, @Query("signature") String signature);
}
