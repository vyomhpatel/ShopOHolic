package b12app.vyom.com.shopoholic.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

import b12app.vyom.com.shopoholic.R;

public class StartUpScreen extends AppCompatActivity {

    private LottieAnimationView animationView;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartUpScreen.this, Login.class));
                finish();
            }
        },2700);
    }
}
