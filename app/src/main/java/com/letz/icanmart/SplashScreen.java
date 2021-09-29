package com.letz.icanmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity
{
    TextView logo_letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo_letter = findViewById(R.id.textView_SplashScreen);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ani_splash);

        logo_letter.startAnimation(animation);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Intent igoMain = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(igoMain) ;
                finish();
            }
        },1000);
    }
}