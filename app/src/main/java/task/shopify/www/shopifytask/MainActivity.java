package task.shopify.www.shopifytask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import task.shopify.www.shopifytask.proto.HackDaysActivity;
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
        mGridView.setOnItemClickListener(mOnItemClickListener);
        fetchData(mCurrentPageNo);
    }

    /**
     * This method makes an asynchronous call with the supplied parameter(i.e., pageNo )
     * @param pageNo the page no from which the data has to be fetched
     */
    private void fetchData(int pageNo){
        mProgressBar.setVisibility(View.VISIBLE);
        Log.d(DEBUG_TAG,"Fetch request made for Page No: "+pageNo);
        //Build a Retrofit with the url and use the GsonConvertor
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
                        HashMap<String, String> variantMap = new HashMap<>();
                        for(Variant variant: variantList){
                            //check if the variant is available or not
                            if(variant.isAvailable()){
                                String cost = variant.getPrice();
                                String title = variant.getTitle();
                                Log.d(DEBUG_TAG, "Adding "+cost+" of "+ title+  " to "+mTotalCost);
                                BigDecimal price = new BigDecimal(cost);
                                mTotalCost = mTotalCost.add(price);
                                Log.d(DEBUG_TAG, "Got "+mTotalCost);

                                variantMap.put(title, cost);

                            }

                        }

                        List<Image> imageList = product.getImages();
                        String productTitle = product.getTitle();

                        for(Image image : imageList){
                            Item item = new Item(productTitle, image.getSrc(), variantMap);
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
        //Log.d(DEBUG_TAG, t.getMessage());
        Toast.makeText(mContext, "Please, check your internet connection.", Toast.LENGTH_LONG).show();
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        /**
         * Pass the required info for animation to the next activity
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Item item = (Item) mGridView.getItemAtPosition(position);
            int[] screenLocation = new int[2];
            //get the co-ordinates of the view on the screen
            view.getLocationOnScreen(screenLocation);
            Intent intent = new Intent(mContext, ItemDetailsActivity.class);
            int orientation = getResources().getConfiguration().orientation;

            intent.putExtra(ItemDetailsActivity.ORIENTATION, orientation)
                    .putExtra(ItemDetailsActivity.LEFT_CO_ORDIANTE, screenLocation[0])
                    .putExtra(ItemDetailsActivity.TOP_CO_ORDINATE, screenLocation[1])
                    .putExtra(ItemDetailsActivity.WIDTH, view.getWidth())
                    .putExtra(ItemDetailsActivity.HEIGHT, view.getHeight())
                    .putExtra(ItemDetailsActivity.IMAGE, item.getProductImageSrc())
                    .putExtra(ItemDetailsActivity.TITLE, item.getProductTitle())
                    .putExtra(ItemDetailsActivity.VARIANTS, item.getVariantMap());

            mContext.startActivity(intent);

            // Override transitions: we don't want the normal window animation in addition
            // to our custom one
            ((Activity)mContext).overridePendingTransition(0, 0);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id){
            case R.id.hack_days:
                intent = new Intent(mContext, HackDaysActivity.class);
                startActivity(intent);
                break;
            case R.id.about_me:
                intent = new Intent(mContext, AboutMeActivity.class);
                startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
