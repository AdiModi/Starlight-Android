package com.example.aditya.starlight;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.hardware.camera2.CameraManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.hardware.Camera;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static SharedPreferences sharedPreferences;
    private boolean isFlashAvailable;
    private Camera camera;
    private Camera.Parameters cameraParams;
    private CameraManager cameraManager;
    private String cameraID;

    private TextView titleTextView_L, titleTextView_u, titleTextView_m, titleTextView_o, titleTextView_s;

    TextView lumosTextView;
    SwitchCompat lumosSwitchCompact;

    private static Random random;

    private Animation titleTextView_L_Animation, titleTextView_u_Animation, titleTextView_m_Animation, titleTextView_o_Animation, titleTextView_s_Animation;

    static {
        random = new Random();
    }

    private void initializeAndAnimateTitleView() {
        titleTextView_L = findViewById(R.id.titleTextView_L);
        titleTextView_L_Animation = AnimationUtils.loadAnimation(this, R.anim.main_title_animation);
        titleTextView_L_Animation.setDuration(500 + random.nextInt(10)*100);
        titleTextView_L.clearAnimation();
        titleTextView_L.startAnimation(titleTextView_L_Animation);

        titleTextView_u = findViewById(R.id.titleTextView_u);
        titleTextView_u_Animation = AnimationUtils.loadAnimation(this, R.anim.main_title_animation);
        titleTextView_u_Animation.setDuration(500 + random.nextInt(10)*100);
        titleTextView_u.clearAnimation();
        titleTextView_u.startAnimation(titleTextView_u_Animation);

        titleTextView_m = findViewById(R.id.titleTextView_m);
        titleTextView_m_Animation = AnimationUtils.loadAnimation(this, R.anim.main_title_animation);
        titleTextView_m_Animation.setDuration(500 + random.nextInt(10)*100);
        titleTextView_m.clearAnimation();
        titleTextView_m.startAnimation(titleTextView_m_Animation);

        titleTextView_o = findViewById(R.id.titleTextView_o);
        titleTextView_o_Animation = AnimationUtils.loadAnimation(this, R.anim.main_title_animation);
        titleTextView_o_Animation.setDuration(500 + random.nextInt(10)*100);
        titleTextView_o.clearAnimation();
        titleTextView_o.startAnimation(titleTextView_o_Animation);

        titleTextView_s = findViewById(R.id.titleTextView_s);
        titleTextView_s_Animation = AnimationUtils.loadAnimation(this, R.anim.main_title_animation);
        titleTextView_s_Animation.setDuration(500 + random.nextInt(10)*100);
        titleTextView_s.clearAnimation();
        titleTextView_s.startAnimation(titleTextView_s_Animation);
    }

    private void initializeViews() {
        initializeAndAnimateTitleView();

        lumosTextView = findViewById(R.id.lumosTextView);
        lumosSwitchCompact = findViewById(R.id.lumosSwitchCompact);
    }

    private void checkFlashAvailability() {
        isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            camera = android.hardware.Camera.open();
            cameraParams = camera.getParameters();
        } else {
            cameraManager = (CameraManager) getSystemService(this.CAMERA_SERVICE);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraID = cameraManager.getCameraIdList()[0];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        checkFlashAvailability();
        initializeViews();
    }
}
