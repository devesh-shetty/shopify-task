package task.shopify.www.shopifytask;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import task.shopify.www.shopifytask.config.Config;
import task.shopify.www.shopifytask.model.Products;

public class MainActivity extends AppCompatActivity implements Callback<Products>{

    @BindView(R.id.tv_total_cost)TextView mTvTotalCost;
    @BindView(R.id.progress_bar)ProgressBar mProgressBar;

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

        ShopifyTaskAPI shopifyTaskAPI = retrofit.create(ShopifyTaskAPI.class);
        Call<Products> call = shopifyTaskAPI.loadData(3);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Products> call, Response<Products> response) {
        mProgressBar.setVisibility(View.GONE);
        Log.d("Result", "Code: "+response.code()+" \n"+response.body().toString());

    }

    @Override
    public void onFailure(Call<Products> call, Throwable t) {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(mContext, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }
}
