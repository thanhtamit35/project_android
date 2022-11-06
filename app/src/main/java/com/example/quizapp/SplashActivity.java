package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizapp.ui_user.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private int currentProgress = 0;
    TextView twLogoText;
    ImageView imgLogo;
    ProgressBar progressBar;
    Animation fadeIn, slideIn;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getSupportActionBar().hide();

        mapping();
        loadAnimation();
        loadProgressbar();
        openLoginActivity();

//        while (currentProgress <= 100) {
//            progressBar.setProgress(currentProgress);
//            currentProgress ++;
//        }
//
//        progressBar.setMax(100);
    }

    public void mapping() {
        twLogoText = findViewById(R.id.tw_name_app);
        imgLogo = findViewById(R.id.img_logo_app);
        progressBar = findViewById(R.id.progress_bar);
    }

    public void loadAnimation() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        slideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
        twLogoText.setAnimation(slideIn);
        imgLogo.setAnimation(fadeIn);
    }

    public void loadProgressbar() {
        currentProgress = progressBar.getProgress();

        new Thread(() -> {
            while (currentProgress < 100) {
                currentProgress += 1;
                // Update the progress bar and display the current value in text view
                handler.post(() -> progressBar.setProgress(currentProgress));

                try {
                    // Sleep for 100 milliseconds to show the progress slowly.
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void openLoginActivity() {
        new Handler().postDelayed((Runnable) () -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }, 5200);
    }
}