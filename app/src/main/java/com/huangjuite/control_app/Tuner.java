package com.huangjuite.control_app;

import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Tuner {
    Button up, down;
    SeekBar seekBar;
    TextView textView;
    MainActivity activity;
    int value;
    float upper_bound, lower_bound;
    String command_prefix;


    public Tuner(Button _up, Button _down, SeekBar _seekBar, final TextView _textView, MainActivity _activity, double _initvalue, double _upperbound, double _lowerbound, String _command_prifix) {
        this.up = _up;
        this.down = _down;
        this.seekBar = _seekBar;
        this.textView = _textView;
        this.activity = _activity;
        this.value  = (int)_initvalue*100;
        this.upper_bound = (float)_upperbound;
        this.lower_bound = (float)_lowerbound;
        this.seekBar.setMax(1000);
        this.command_prefix = _command_prifix;

        setSeekBar(value);
        textView.setText(valuetoString(value));

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = value + 1;
                value = limits(value);
                setSeekBar(value);
                textView.setText(valuetoString(value));
                activity.btSentText(command_prefix + valuetoString(value));
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = value - 1;
                value = limits(value);
                setSeekBar(value);
                textView.setText(valuetoString(value));
                activity.btSentText(command_prefix + valuetoString(value));
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = (int)((seekBar.getProgress()/1000.*(upper_bound-lower_bound)+lower_bound)*100);
                value = limits(value);
                textView.setText(valuetoString(value));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                activity.btSentText(command_prefix + valuetoString(value));
            }
        });

    }

    int limits(int v){
        return (int)Math.max(lower_bound*100,Math.min(upper_bound*100,v));

    }

    String valuetoString(int v){
        return String.format("%.2f",v/100.);
    }

    void setSeekBar(int v){
        float currentValue = 1000*((v-lower_bound*100)/(upper_bound-lower_bound)/100);
        seekBar.setProgress((int)currentValue);
    }


}
