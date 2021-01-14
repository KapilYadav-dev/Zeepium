package in.kay.zeepium.Api;

import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    String BASE_URL = "https://zeepium-mrkaydev.herokuapp.com/";
    @GET("search")
    Call<ResponseBody> search(@Query("q") String query);
}
