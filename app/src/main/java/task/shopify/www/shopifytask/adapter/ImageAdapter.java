package task.shopify.www.shopifytask.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import task.shopify.www.shopifytask.ItemDetailsActivity;
import task.shopify.www.shopifytask.R;
import task.shopify.www.shopifytask.config.Config;
import task.shopify.www.shopifytask.model.Item;

/**
 * The ImageAdapter that will supply the data source to the gridView
 * @author Devesh Shetty
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<Item> mDataSource;
    private LayoutInflater mLayoutInflater;

    public ImageAdapter(Context mContext, List<Item> mDataSource) {
        this.mContext = mContext;
        this.mDataSource = mDataSource;
        mLayoutInflater = ((Activity)mContext).getLayoutInflater();

    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Item getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.image_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Item item = getItem(position);

        if(Config.DEBUGGING_MODE){
            Picasso.with(mContext).setIndicatorsEnabled(true);
            Picasso.with(mContext).setLoggingEnabled(true);
        }

        //load the imageView with the bitmap
        Picasso.with(mContext)
                .load(item.getProductImageSrc())
                .placeholder(R.drawable.progress_animation)
                .into(viewHolder.ivProductImage);


        viewHolder.tvProductTitle.setText(item.getProductTitle());
        return convertView;
    }

     class ViewHolder implements View.OnClickListener{

        @BindView(R.id.iv_item_image)ImageView ivProductImage;
        @BindView(R.id.tv_item_title)TextView tvProductTitle;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
            ivProductImage.setOnClickListener(this);
        }

         /**
          * Pass the required info for animation to the next activity
          */
         @Override
         public void onClick(View view) {

             view.buildDrawingCache();
             Bitmap bitmap = view.getDrawingCache();
             ByteArrayOutputStream stream = new ByteArrayOutputStream();
             bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
             byte[] byteArray = stream.toByteArray();
             //cleanup the cache
             view.destroyDrawingCache();

             int[] screenLocation = new int[2];
             view.getLocationOnScreen(screenLocation);
             Intent intent = new Intent(mContext, ItemDetailsActivity.class);
             int orientation = mContext.getResources().getConfiguration().orientation;

             intent.putExtra(ItemDetailsActivity.ORIENTATION, orientation)
                     .putExtra(ItemDetailsActivity.LEFT_CO_ORDIANTE, screenLocation[0])
                     .putExtra(ItemDetailsActivity.TOP_CO_ORDINATE, screenLocation[1])
                     .putExtra(ItemDetailsActivity.WIDTH, view.getWidth())
                     .putExtra(ItemDetailsActivity.HEIGHT, view.getHeight())
                     .putExtra(ItemDetailsActivity.IMAGE, byteArray)
                     .putExtra(ItemDetailsActivity.TITLE, tvProductTitle.getText()+"");

             mContext.startActivity(intent);

             // Override transitions: we don't want the normal window animation in addition
             // to our custom one
             ((Activity)mContext).overridePendingTransition(0, 0);
         }
     }



}
