package com.satyamltd.braintest.mathquiz;


import android.app.ActivityManager;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.satyamltd.braintest.mathquiz.utils.CheckBackground;


public class OptionsActivity extends FragmentActivity {
    LinearLayout adLayout;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    Switch soundMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView back = findViewById(R.id.img_back);
        // preferences
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed = sp.edit();
//        TextView ttitle =  findViewById(R.id.title);
        TextView sound = findViewById(R.id.sound);
        soundMode = (Switch) findViewById(R.id.sound_mode);


        boolean backgroundMusic = sp.getBoolean("play", false);
        if (backgroundMusic) {
            soundMode.setChecked(true);
        }
        // mode switch listener
        (soundMode).setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ed.putBoolean("play", true);
                    Intent svc = new Intent(OptionsActivity.this, BackgroundSoundService.class);
                    startService(svc);
                    soundMode.setChecked(true);
                } else {
                    ed.putBoolean("play", false);
                    stopService(new Intent(OptionsActivity.this, BackgroundSoundService.class));
                }
                ed.commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                finish();
            }
        });

    }//oncreate


    @Override
    public void onStart() {
        super.onStart();
        if (!isMyServiceRunning(BackgroundSoundService.class)) {
            boolean backgroundMusic = sp.getBoolean("play", false);
            if (backgroundMusic) {
                Intent svc = new Intent(this, BackgroundSoundService.class);
                startService(svc);
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (CheckBackground.appInBackground(this)) {
            stopService(new Intent(getApplicationContext(), BackgroundSoundService.class));
        } else {
            PowerManager kgMgr = (PowerManager) getSystemService(Context.POWER_SERVICE);
            boolean showing = kgMgr.isScreenOn();
            if (!showing) {
                stopService(new Intent(getApplicationContext(), BackgroundSoundService.class));
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (CheckBackground.appInBackground(this)) {
            stopService(new Intent(getApplicationContext(), BackgroundSoundService.class));
        } else {
            PowerManager kgMgr = (PowerManager) getSystemService(Context.POWER_SERVICE);
            boolean showing = kgMgr.isScreenOn();
            if (!showing) {
                stopService(new Intent(getApplicationContext(), BackgroundSoundService.class));
            }
        }

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}
