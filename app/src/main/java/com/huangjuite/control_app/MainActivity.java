package com.huangjuite.control_app;

import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;
    private CustomViewPager mViewPager;

    private control_frag control_fragment;
    private bt_frag connection_fragment;
    private pid_frag pid_fragment;

    private Timer timer;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Log.d(TAG, "onCreate: Starting.");

        control_fragment = new control_frag();
        connection_fragment = new bt_frag();
        pid_fragment = new pid_frag();
        pid_fragment.setActivity(this);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        task = new TimerTask() {
            @Override
            public void run() {
                if(connection_fragment.getmBluetoothConnection()!=null && connection_fragment.getmBluetoothConnection().isConnected()){
                    String motor_r,motor_l;
                    int[] joyValue = control_fragment.getJoy_value();

                    motor_l = toMotorString(joyValue[0] - joyValue[1]);
                    motor_r = toMotorString(joyValue[0] + joyValue[1]);

                    btSentText(motor_l + motor_r);
                }
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 1000, 250);
        Log.d(TAG,"timer set");


        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        mViewPager.setPagingEnabled(false);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(CustomViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(control_fragment, "control");
        adapter.addFragment(connection_fragment, "connection");
        adapter.addFragment(pid_fragment, "pid");
        viewPager.setAdapter(adapter);

    }

    public void btSentText(String command) {
        connection_fragment.getmBluetoothConnection().write(command);
    }


    private String toMotorString(int value){
        String string = "";
        string += (value>0)? "1":"0";
        int v = Math.max(Math.min(Math.abs(value),99),0);
        string += String.format("%02d",v);
        return string;
    }


}
