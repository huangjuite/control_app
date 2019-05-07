package com.huangjuite.control_app;

import android.support.v7.app.AppCompatActivity;
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


    }


}
