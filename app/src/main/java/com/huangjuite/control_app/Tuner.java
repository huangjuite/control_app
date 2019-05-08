package com.huangjuite.control_app;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Tuner {
    Button up, down;
    SeekBar seekBar;
    TextView textView;
    MainActivity activity;

    public Tuner(Button up, Button down, SeekBar seekBar, TextView textView, MainActivity activity) {
        this.up = up;
        this.down = down;
        this.seekBar = seekBar;
        this.textView = textView;
        this.activity = activity;

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


}
