package retrofit;

import java.util.List;

import model.DataShop;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("datashop")
    Call<List<DataShop>> getAllData();
}
