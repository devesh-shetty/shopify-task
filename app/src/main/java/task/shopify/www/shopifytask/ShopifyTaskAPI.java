package task.shopify.www.shopifytask;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import task.shopify.www.shopifytask.config.Config;
import task.shopify.www.shopifytask.model.Products;

/**
 * Created by deveshshetty on 10/09/16.
 */
public interface ShopifyTaskAPI {

    @GET(Config.RELATIVE_URL)
    Call<Products> loadData(@Query("page") int pageNo);
}
