package task.shopify.www.shopifytask;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import task.shopify.www.shopifytask.adapter.ImageAdapter;
import task.shopify.www.shopifytask.config.Config;
import task.shopify.www.shopifytask.model.Image;
import task.shopify.www.shopifytask.model.Item;
import task.shopify.www.shopifytask.model.Product;
import task.shopify.www.shopifytask.model.Products;

/**
 *
 * @author Devesh Shetty
 */
public class MainActivity extends AppCompatActivity implements Callback<Products>{

    @BindView(R.id.tv_total_cost)TextView mTvTotalCost;
    @BindView(R.id.progress_bar)ProgressBar mProgressBar;
    @BindView(R.id.gridView_images)GridView mGridView;

    private ImageAdapter mImageAdapter;
    private Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_fetch_data)
    public void fetchData(View view){
        mProgressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //prepare the call
        ShopifyTaskAPI shopifyTaskAPI = retrofit.create(ShopifyTaskAPI.class);
        Call<Products> call = shopifyTaskAPI.loadData(3);
        //make an asynchronous call
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Products> call, Response<Products> response) {

        int code = response.code();
        switch (code){

            case Config.STATUS_CODE_OK:
                //HTTP request was successful
                ArrayList<Item> adapterItems = new ArrayList<>();

                Products products = response.body();
                List<Product> productList = products.getProducts();

                for(Product product : productList){

                    List<Image> imageList = product.getImages();
                    String productTitle = product.getTitle();

                    for(Image image : imageList){
                        Item item = new Item(productTitle, image.getSrc());
                        adapterItems.add(item);
                    }

                }

                mImageAdapter = new ImageAdapter(mContext, adapterItems);
                mGridView.setAdapter(mImageAdapter);

                mProgressBar.setVisibility(View.GONE);
                break;

            default:
                Toast.makeText(mContext, "Error code: "+code, Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onFailure(Call<Products> call, Throwable t) {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(mContext, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }
}
