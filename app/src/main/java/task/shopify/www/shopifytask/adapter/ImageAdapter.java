package task.shopify.www.shopifytask.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
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

     class ViewHolder {

        @BindView(R.id.iv_item_image)ImageView ivProductImage;
        @BindView(R.id.tv_item_title)TextView tvProductTitle;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
//            ivProductImage.setOnClickListener(this);
        }


     }



}
