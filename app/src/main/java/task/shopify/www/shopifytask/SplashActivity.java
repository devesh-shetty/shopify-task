package task.shopify.www.shopifytask;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_animation)
    ImageView mImageViewAnimation;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mContext = SplashActivity.this;

        ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(mContext, R.animator.flip);
        objectAnimator.setTarget(mImageViewAnimation);
        objectAnimator.setInterpolator(new OvershootInterpolator());
        objectAnimator.setDuration(5000);
        objectAnimator.start();

        Handler handler = new Handler();
        handler.postDelayed( ()-> {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
        }, 5000 );


    }
}
