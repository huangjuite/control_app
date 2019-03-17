package com.huangjuite.control_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmedeisis.bugstick.Joystick;
import com.jmedeisis.bugstick.JoystickListener;

public class control_frag extends Fragment {
    private static final String TAG = "control fragment";

    TextView yTextView ;
    TextView xTextView ;

    String xNoneString;
    String xValueString;
    String yNoneString;
    String yValueString;

    Joystick joystick_r;
    Joystick joystick_l;

    private int joy_value[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.control_fragment,container,false);

        joy_value = new int[2];
        joy_value[0] = 0;
        joy_value[1] = 0;

        yTextView = (TextView) view.findViewById(R.id.y_value);
        xTextView = (TextView) view.findViewById(R.id.x_value);

        xNoneString = getString(R.string.x_value_none);
        xValueString = getString(R.string.x_value);
        yNoneString = getString(R.string.y_value_none);
        yValueString = getString(R.string.y_value);

        joystick_r = (Joystick) view.findViewById(R.id.joystick_r);
        joystick_l = (Joystick) view.findViewById(R.id.joystick_l);


        joystick_r.setJoystickListener(new JoystickListener() {
            @Override
            public void onDown() {

            }

            @Override
            public void onDrag(float degrees, float offset) {
                float value = (float) (offset * 100 * Math.cos(Math.toRadians(degrees)));
                xTextView.setText(String.format(xValueString, value));
                joy_value[1] = (int)value;
            }

            @Override
            public void onUp() {
                xTextView.setText(xNoneString);
                joy_value[1] = 0;
            }
        });


        joystick_l.setJoystickListener(new JoystickListener() {
            @Override
            public void onDown() {
            }

            @Override
            public void onDrag(float degrees, float offset) {
                float value = (float) (offset * 100 * Math.sin(Math.toRadians(degrees)));
                yTextView.setText(String.format(yValueString, value));
                joy_value[0] = (int)value;

            }

            @Override
            public void onUp() {
                yTextView.setText(yNoneString);
                joy_value[0] = 0;
            }
        });

        return view;
    }

    public int[] getJoy_value() {
        return joy_value;
    }
}
