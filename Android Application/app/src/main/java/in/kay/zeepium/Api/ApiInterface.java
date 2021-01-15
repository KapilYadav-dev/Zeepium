package in.kay.zeepium.Api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    String BASE_URL = "http://192.168.1.7:8080/";
    @GET("search")
    Call<ResponseBody> search(@Query("q") String query);
}
