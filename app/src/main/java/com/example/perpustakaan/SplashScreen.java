package com.example.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    ImageView img;
    TextView TV,TV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        img = findViewById(R.id.imgSplash);
        TV  = findViewById(R.id.idTV);
        TV2  = findViewById(R.id.idTV2);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.down_from_top);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.up_from_buttom);
        img.startAnimation(anim);
        TV.startAnimation(anim2);
        TV2.startAnimation(anim2);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), OnBoarding.class));            }
        }, 4000L); //5000 L = 5 detik
    }
}
