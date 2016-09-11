package task.shopify.www.shopifytask;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    private Context mContext = ItemDetailsActivity.this;

    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();
    private static final int ANIM_DURATION = 500;

    @BindView(R.id.iv_selected_image)ImageView mImageView;
    @BindView(R.id.parentLayout)RelativeLayout mParentLayout;
    @BindView(R.id.tv_title)TextView mTextViewTitle;

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

        mBackground = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
        mParentLayout.setBackground(mBackground);

        Bundle bundle = getIntent().getExtras();
        final int thumbnailTop = bundle.getInt(TOP_CO_ORDINATE);
        final int thumbnailLeft = bundle.getInt(LEFT_CO_ORDIANTE);
        final int thumbnailWidth = bundle.getInt(WIDTH);
        final int thumbnailHeight = bundle.getInt(HEIGHT);
        mOriginalOrientation = bundle.getInt(ORIENTATION);
        String title = bundle.getString(TITLE);
        mTextViewTitle.setText(title);

        byte[] byteArray = bundle.getByteArray(IMAGE);

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        mImageView.setImageDrawable(mBitmapDrawable);

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

        // We'll fade the text in later
        mTextViewTitle.setAlpha(0);

        // Animate scale and translation to go from thumbnail to full size
        mImageView.animate().setDuration(duration).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator).
                withEndAction(new Runnable() {
                    public void run() {
                        // Animate the description in after the image animation
                        // is done. Slide and fade the text in from underneath
                        // the picture.
                        mTextViewTitle.setTranslationY(-mTextViewTitle.getHeight());
                        mTextViewTitle.animate().setDuration(duration/2).
                                translationY(0).alpha(1).
                                setInterpolator(sDecelerator);
                    }
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
                withEndAction(new Runnable() {
                    public void run() {
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

                    }
                });



    }

    /**
     * Overriding this method allows us to run our exit animation first, then exiting
     * the activity when it is complete.
     */
    @Override
    public void onBackPressed() {
        runExitAnimation(new Runnable() {
            public void run() {
                // *Now* go ahead and exit the activity
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();

        // override transitions to skip the standard window animations
        overridePendingTransition(0, 0);
    }

}
