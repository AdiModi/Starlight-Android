package com.example.aditya.starlight;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private boolean isFlashAvailable;
    private Camera camera;
    private Camera.Parameters cameraParams;
    private CameraManager cameraManager;
    private String cameraID;

    private TextView titleTextView_L, titleTextView_u, titleTextView_m, titleTextView_o, titleTextView_s;

    private TextView lumosTextView, lumosAternetaTextView;
    private SwitchCompat lumosSwitchCompact, lumosAlternetaSwitchCompact;
    private ImageButton musicImageButton, soundImageButton;
    private boolean isFlashOn = false, isLumosOn = false, isLumosAlternetaOn = false;
    private Thread lumosAlternetaThread = null;

    private MediaPlayer musicMediaPlayer, soundMediaPlayer;

    private static class Settings {
        static boolean music = false;
        static boolean sound = false;
    }

    private static Random random;

    private Animation titleTextView_L_Animation, titleTextView_u_Animation, titleTextView_m_Animation, titleTextView_o_Animation, titleTextView_s_Animation;

    static {
        random = new Random();
    }

    private void turnOnFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraID, true);
            } else {
                cameraParams.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(cameraParams);
                camera.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isFlashOn = true;
    }

    private void turnOffFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraID, false);
            } else {
                cameraParams.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(cameraParams);
                camera.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isFlashOn = false;
    }

    private void initializeAndAnimateTitleView() {
        titleTextView_L = findViewById(R.id.titleTextView_L);
        titleTextView_L_Animation = AnimationUtils.loadAnimation(this, R.anim.main_title_animation);
        titleTextView_L_Animation.setDuration(500 + random.nextInt(10) * 100);
        titleTextView_L.clearAnimation();
        titleTextView_L.startAnimation(titleTextView_L_Animation);

        titleTextView_u = findViewById(R.id.titleTextView_u);
        titleTextView_u_Animation = AnimationUtils.loadAnimation(this, R.anim.main_title_animation);
        titleTextView_u_Animation.setDuration(500 + random.nextInt(10) * 100);
        titleTextView_u.clearAnimation();
        titleTextView_u.startAnimation(titleTextView_u_Animation);

        titleTextView_m = findViewById(R.id.titleTextView_m);
        titleTextView_m_Animation = AnimationUtils.loadAnimation(this, R.anim.main_title_animation);
        titleTextView_m_Animation.setDuration(500 + random.nextInt(10) * 100);
        titleTextView_m.clearAnimation();
        titleTextView_m.startAnimation(titleTextView_m_Animation);

        titleTextView_o = findViewById(R.id.titleTextView_o);
        titleTextView_o_Animation = AnimationUtils.loadAnimation(this, R.anim.main_title_animation);
        titleTextView_o_Animation.setDuration(500 + random.nextInt(10) * 100);
        titleTextView_o.clearAnimation();
        titleTextView_o.startAnimation(titleTextView_o_Animation);

        titleTextView_s = findViewById(R.id.titleTextView_s);
        titleTextView_s_Animation = AnimationUtils.loadAnimation(this, R.anim.main_title_animation);
        titleTextView_s_Animation.setDuration(500 + random.nextInt(10) * 100);
        titleTextView_s.clearAnimation();
        titleTextView_s.startAnimation(titleTextView_s_Animation);
    }

    private void lumos(boolean state){
        if (isLumosAlternetaOn){
            lumosAlterneta(false);
            lumosAlternetaSwitchCompact.setChecked(false);
        }

        if (state) {
            turnOnFlashLight();
            if (Settings.sound){
                soundMediaPlayer.start();
            }

            lumosTextView.setText("Nox");
            lumosTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
            isLumosOn = true;
        } else {
            turnOffFlashLight();

            lumosTextView.setText("Lumos");
            lumosTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.white));
            isLumosOn = false;
        }
    }

    private void lumosAlterneta(boolean state){
        if (isLumosOn){
            lumos(false);
            lumosSwitchCompact.setChecked(false);
        }

        if (state) {
            lumosAlternetaThread = new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            turnOnFlashLight();
                            sleep(500);
                            turnOffFlashLight();
                            sleep(500);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            lumosAlternetaThread.start();
            if (Settings.sound){
                soundMediaPlayer.start();
            }

            lumosAternetaTextView.setText("Nox");
            lumosAternetaTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
            isLumosAlternetaOn = true;
        } else {
            if(lumosAlternetaThread != null){
                lumosAlternetaThread.interrupt();
            }
            turnOffFlashLight();
            lumosAlternetaThread = null;
            lumosAternetaTextView.setText("Lumos Alterneta");
            lumosAternetaTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.white));
            isLumosAlternetaOn = false;
        }
    }

    private void initializeViews() {
        initializeAndAnimateTitleView();

        lumosTextView = findViewById(R.id.lumosTextView);
        lumosSwitchCompact = findViewById(R.id.lumosSwitchCompact);
        lumosSwitchCompact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lumos(isChecked);
            }
        });

        lumosAternetaTextView = findViewById(R.id.lumosAlternetaTextView);
        lumosAlternetaSwitchCompact = findViewById(R.id.lumosAlternetaSwitchCompact);
        lumosAlternetaSwitchCompact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lumosAlterneta(isChecked);
            }
        });

        musicImageButton = findViewById(R.id.musicImageButton);
        musicMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.harry_potter_theme);
        musicMediaPlayer.setLooping(true);
        musicMediaPlayer.setVolume(100, 100);
        if(Settings.music){
            musicImageButton.setImageResource(R.drawable.music_on);
            musicMediaPlayer.start();
        }
        musicImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Settings.music) {
                    Settings.music = false;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("music", Settings.music);
                    editor.commit();

                    musicMediaPlayer.pause();

                    Toast.makeText(MainActivity.this, "Music Turned Off!", Toast.LENGTH_SHORT).show();

                    musicImageButton.setImageResource(R.drawable.music_off);
                } else {
                    Settings.music = true;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("music", Settings.music);
                    editor.commit();

                    musicMediaPlayer.start();

                    Toast.makeText(MainActivity.this, "Music Turned On!", Toast.LENGTH_SHORT).show();

                    musicImageButton.setImageResource(R.drawable.music_on);
                }
            }
        });
        soundImageButton = findViewById(R.id.soundImageButton);
        soundMediaPlayer= MediaPlayer.create(MainActivity.this, R.raw.lumos_theme);
        soundMediaPlayer.setVolume(100, 100);
        if(Settings.sound){
            soundImageButton.setImageResource(R.drawable.sound_on);
        }
        soundImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Settings.sound) {
                    Settings.sound = false;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("sound", Settings.sound);
                    editor.commit();

                    Toast.makeText(MainActivity.this, "Sound Turned Off!", Toast.LENGTH_SHORT).show();

                    soundImageButton.setImageResource(R.drawable.sound_off);
                } else {
                    Settings.sound = true;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("sound", Settings.sound);
                    editor.commit();

                    Toast.makeText(MainActivity.this, "Sound Turned On!", Toast.LENGTH_SHORT).show();

                    soundImageButton.setImageResource(R.drawable.sound_on);
                }
            }
        });
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

        if (!isFlashAvailable) {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            alert.setTitle("No Flash");
            alert.setMessage("Your wand cannot incantate LUMOS!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "CLOSE THE APP", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
    }

    private void loadSettings() {
        sharedPreferences = this.getSharedPreferences("settings", MODE_PRIVATE);
        Settings.music = sharedPreferences.getBoolean("music", false);
        Settings.sound = sharedPreferences.getBoolean("sound", false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loadSettings();

        setContentView(R.layout.activity_main);

        checkFlashAvailability();
        initializeViews();
    }
}
