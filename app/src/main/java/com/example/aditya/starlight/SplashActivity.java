package com.example.aditya.starlight;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    private final int ACTIVITY_KILL_DELAY = 5000;
    private static Random random;

    private RelativeLayout relativeLayout;
    private TextView titleTextView;

    private Animation relativeLayoutAnimation;
    private Animation titleTextViewAnimation;

    static {
        random = new Random();
    }

    private void initializeAndAnimateTitleView() {
        titleTextView = findViewById(R.id.titleTextView);
        titleTextViewAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_title_animation);
        titleTextViewAnimation.setStartTime(1000);
        titleTextViewAnimation.setDuration(ACTIVITY_KILL_DELAY - 1000);
        titleTextView.startAnimation(titleTextViewAnimation);

    }

    private void initializeAndAnimateBackground() {
        relativeLayout = findViewById(R.id.splashBackground);
        relativeLayoutAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_background_animation);
        relativeLayoutAnimation.setStartTime(1000);
        relativeLayoutAnimation.setDuration(ACTIVITY_KILL_DELAY - 1000);
        relativeLayout.startAnimation(relativeLayoutAnimation);
    }

    private void initializeViews() {
        initializeAndAnimateBackground();
        initializeAndAnimateTitleView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initializeViews();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, ACTIVITY_KILL_DELAY);
    }
}
