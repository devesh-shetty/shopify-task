package task.shopify.www.shopifytask;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import task.shopify.www.shopifytask.config.Config;

public class ItemDetailsActivity extends AppCompatActivity {

    public static final String ORIENTATION = "ORIENTATION";
    public static final String LEFT_CO_ORDIANTE = "LEFT";
    public static final String TOP_CO_ORDINATE = "TOP";
    public static final String WIDTH = "WIDTH";
    public static final String HEIGHT = "HEIGHT";
    public static final String TITLE = "TITLE";
    public static final String IMAGE = "IMAGE";
    public static final String VARIANTS = "VARIANTS";

    private Context mContext = ItemDetailsActivity.this;

    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();
    private static final int ANIM_DURATION = 500;

    @BindView(R.id.iv_selected_image)ImageView mImageView;
    @BindView(R.id.parentLayout)RelativeLayout mParentLayout;
    @BindView(R.id.tv_title)TextView mTextViewTitle;
    @BindView(R.id.spinner_variants)Spinner mSpinnerVariants;
    @BindView(R.id.tv_variant_cost)TextView mTextViewVariantCost;

    private int mOriginalOrientation;
    private BitmapDrawable mBitmapDrawable;
    int mLeftDelta;
    int mTopDelta;
    float mWidthScale;
    float mHeightScale;
    ColorDrawable mBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        ButterKnife.bind(this);

        int color = ContextCompat.getColor(mContext, R.color.colorPrimaryDark);
        mBackground = new ColorDrawable(color);
        mParentLayout.setBackground(mBackground);

        Bundle bundle = getIntent().getExtras();
        final int thumbnailTop = bundle.getInt(TOP_CO_ORDINATE);
        final int thumbnailLeft = bundle.getInt(LEFT_CO_ORDIANTE);
        final int thumbnailWidth = bundle.getInt(WIDTH);
        final int thumbnailHeight = bundle.getInt(HEIGHT);
        mOriginalOrientation = bundle.getInt(ORIENTATION);
        String title = bundle.getString(TITLE);
        mTextViewTitle.setText(title);

        String imgSrc = bundle.getString(IMAGE);
        //load the imageView with the bitmap
        Picasso.with(mContext)
                .load(imgSrc)
                .placeholder(R.drawable.progress_animation)
                .into(mImageView);

        //keys of the map consist of the variant's title
        HashMap<String, String> mapVariants = (HashMap<String, String>) bundle.getSerializable(VARIANTS);
        Set<String> variantSet =  mapVariants.keySet();
        ArrayList list = new ArrayList();
        list.addAll(variantSet);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerVariants.setAdapter(arrayAdapter);
        mSpinnerVariants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get the variant
                String key = (String) mSpinnerVariants.getItemAtPosition(position);
                //get the price
                String price = mapVariants.get(key);
                NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
                String res = numberFormat.format(new BigDecimal(price));
                mTextViewVariantCost.setText(res);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Only run the animation if we're coming from the parent activity, not if
        // we're recreated automatically by the window manager (e.g., device rotation)
        if (savedInstanceState == null) {
            ViewTreeObserver observer = mImageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    mImageView.getViewTreeObserver().removeOnPreDrawListener(this);

                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    mImageView.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / mImageView.getWidth();
                    mHeightScale = (float) thumbnailHeight / mImageView.getHeight();

                    runEnterAnimation();

                    return true;
                }
            });
        }

    }


    /**
     * The enter animation scales the picture in from its previous thumbnail
     * size/location In parallel, the background of the
     * activity is fading in.When the picture is in place, the text description
     * drops down.
     */
    public void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION * Config.ANIMATOR_SCALE);

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up
        mImageView.setPivotX(0);
        mImageView.setPivotY(0);
        mImageView.setScaleX(mWidthScale);
        mImageView.setScaleY(mHeightScale);
        mImageView.setTranslationX(mLeftDelta);
        mImageView.setTranslationY(mTopDelta);

        // We'll fade them in later
        mTextViewTitle.setAlpha(0);
        mSpinnerVariants.setAlpha(0);
        mTextViewVariantCost.setAlpha(0);

        // Animate scale and translation to go from thumbnail to full size
        mImageView.animate().setDuration(duration).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator).
                withEndAction(() -> {
                    // Animate the description in after the image animation
                    // is done. Slide and fade the text in from underneath
                    // the picture.
                    mTextViewTitle.setTranslationY(-mTextViewTitle.getHeight());
                    mTextViewTitle.animate().setDuration(duration/2).
                            translationY(0).alpha(1).
                            setInterpolator(sDecelerator);

                    mSpinnerVariants.setTranslationX( mSpinnerVariants.getWidth());
                    mSpinnerVariants.animate().setDuration(duration/2)
                            .translationX(0).alpha(1)
                            .setInterpolator(sDecelerator);

                    mTextViewVariantCost.setTranslationY( mTextViewVariantCost.getHeight());
                    mTextViewVariantCost.animate().setDuration(duration/2)
                            .translationY(0).alpha(1)
                            .setInterpolator(sDecelerator);


                });;

        // Fade in the  background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
        bgAnim.setDuration(duration);
        bgAnim.start();


    }

    /**
     * The exit animation is basically a reverse of the enter animation, except that if
     * the orientation has changed we simply scale the picture back into the center of
     * the screen.
     *
     * @param endAction This action gets run after the animation completes (this is
     * when we actually switch activities)
     */
    public void runExitAnimation(final Runnable endAction) {
        final long duration = (long) (ANIM_DURATION * Config.ANIMATOR_SCALE);

        // No need to set initial values for the reverse animation; the image is at the
        // starting size/location that we want to start from. Just animate to the
        // thumbnail size/location that we retrieved earlier

        // Caveat: configuration change invalidates thumbnail positions; just animate
        // the scale around the center. Also, fade it out since it won't match up with
        // whatever's actually in the center
        final boolean fadeOut;
        if (getResources().getConfiguration().orientation != mOriginalOrientation) {
            mImageView.setPivotX(mImageView.getWidth() / 2);
            mImageView.setPivotY(mImageView.getHeight() / 2);
            mLeftDelta = 0;
            mTopDelta = 0;
            fadeOut = true;
        } else {
            fadeOut = false;
        }

        // First, slide/fade text out of the way
        mTextViewTitle.animate().translationY(-mTextViewTitle.getHeight()).alpha(0).
                setDuration(duration/2).setInterpolator(sAccelerator).
                withEndAction(() -> {
                    // Animate image back to thumbnail size/location
                    mImageView.animate().setDuration(duration).
                            scaleX(mWidthScale).scaleY(mHeightScale).
                            translationX(mLeftDelta).translationY(mTopDelta).
                            withEndAction(endAction);
                    if (fadeOut) {
                        mImageView.animate().alpha(0);
                    }
                    // Fade out background
                    ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0);
                    bgAnim.setDuration(duration);
                    bgAnim.start();

                    mTextViewVariantCost.animate().translationY( -mTextViewVariantCost.getHeight()).alpha(0)
                                                    .setDuration(duration/2).setInterpolator(sAccelerator);
                    mSpinnerVariants.animate().translationX(-mSpinnerVariants.getWidth()).alpha(0)
                            .setDuration(duration/2).setInterpolator(sAccelerator);


                });



    }

    /**
     * Overriding this method allows us to run our exit animation first, then exiting
     * the activity when it is complete.
     */
    @Override
    public void onBackPressed() {
        runExitAnimation(() -> finish());
    }

    @Override
    public void finish() {
        super.finish();

        // override transitions to skip the standard window animations
        overridePendingTransition(0, 0);
    }

}
