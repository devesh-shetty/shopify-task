package task.shopify.www.shopifytask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
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
import task.shopify.www.shopifytask.model.Variant;
import task.shopify.www.shopifytask.util.Util;

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
    private ArrayList<Item> mAdapterItems;
    private BigDecimal mTotalCost = BigDecimal.ZERO;
    private int mCurrentPageNo = 1;

    private final String DEBUG_TAG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapterItems = new ArrayList<>();
        mImageAdapter = new ImageAdapter(mContext, mAdapterItems);
        mGridView.setAdapter(mImageAdapter);

        //Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_anim);
        //GridLayoutAnimationController animationController = new GridLayoutAnimationController(animation, 0.4f, 0.4f);
        //mGridView.setLayoutAnimation(animationController);

        fetchData(mCurrentPageNo);
    }

    /**
     * This method makes an asynchronous call with the supplied parameter(i.e., pageNo )
     * @param pageNo the page no from which the data has to be fetched
     */
    private void fetchData(int pageNo){
        mProgressBar.setVisibility(View.VISIBLE);
        Log.d(DEBUG_TAG,"Fetch request made for Page No: "+pageNo);
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(Config.BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

        //prepare the call
        ShopifyTaskAPI shopifyTaskAPI = retrofit.create(ShopifyTaskAPI.class);
        Call<Products> call = shopifyTaskAPI.loadData(pageNo);
        //make an asynchronous call
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Products> call, Response<Products> response) {

        int code = response.code();

        switch (code){

            case Config.STATUS_CODE_OK:
                //HTTP request was successful

                Products products = response.body();
                List<Product> productList = products.getProducts();

                if(productList.isEmpty()){
                    //productList is empty that indicates pages from the currentPage onwards have no data
                    //we have retrieved all the information from all the pages so stop the calls
                    mProgressBar.setVisibility(View.GONE);
                    //get the numberFormat object for Canadian currency
                    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
                    String res = numberFormat.format(mTotalCost);
                    String clock = Util.getEmojiByUnicode(Config.UNICODE_CLOCK);
                    String watch = Util.getEmojiByUnicode(Config.UNICODE_WATCH);
                    mTvTotalCost.setText("Total cost "+clock+watch+": "+res);
                    return;
                }

                for(Product product : productList){

                    String productType = product.getProductType();
                    if(productType != null && ( productType.equals(Config.PRODUCT_TYPE_CLOCK)
                                            || productType.equals(Config.PRODUCT_TYPE_WATCH) ) ){
                       //add items to the list only if they are of type watch or clock

                        List<Variant> variantList = product.getVariants();
                        for(Variant variant: variantList){
                            //check if the variant is available or not
                            if(variant.isAvailable()){
                                BigDecimal price = new BigDecimal(variant.getPrice());
                                mTotalCost = mTotalCost.add(price);
                            }

                        }

                        List<Image> imageList = product.getImages();
                        String productTitle = product.getTitle();

                        for(Image image : imageList){
                            Item item = new Item(productTitle, image.getSrc());
                            mAdapterItems.add(item);
                        }

                    }

                }

                mImageAdapter.notifyDataSetChanged();

                mCurrentPageNo++;
                fetchData(mCurrentPageNo);

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
