package th.co.dtac.digitalservices.newmsgcenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.co.dtac.digitalservices.newmsgcenter.response.TestResponseModel;

public class MessageCenterManager {

    public interface MessageListener {

        void onCallSuccess(TestResponseModel model);
        
        void onError();
    }

    public static void registerToken(String token, final MessageListener listener) {
        listener.onError();
        Call<TestResponseModel> call = ApiClient.getInstance().getEndpointInterface().registerToken("abcdef");
        call.enqueue(new Callback<TestResponseModel>() {
            @Override
            public void onResponse(Call<TestResponseModel> call, Response<TestResponseModel> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        TestResponseModel data = response.body();
                        listener.onCallSuccess(data);
                    }
                }
            }

            @Override
            public void onFailure(Call<TestResponseModel> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
