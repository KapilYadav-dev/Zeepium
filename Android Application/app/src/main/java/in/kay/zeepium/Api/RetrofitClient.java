package in.kay.zeepium.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public Retrofit retrofit;
    public static in.kay.zeepium.Api.RetrofitClient mInstance;
    public RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized in.kay.zeepium.Api.RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new in.kay.zeepium.Api.RetrofitClient();
        }
        return mInstance;
    }

    public ApiInterface getApi() {
        return retrofit.create(ApiInterface.class);
    }
}
