package com.huangjuite.control_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class pid_frag extends Fragment {

    TextView textKp, textKi, textKd, textReference,textAngle;
    SeekBar seekBarKp, seekBarKi, seekBarKd, seekBarReference;
    Button kpUp, kpDown, kiUp, kiDown, kdUp, kdDown, referenceUp, referenceDown;
    Tuner tunerKp, tunerKi, tunerKd, tunerReference;
    float kp, li, kd, reference;
    MainActivity activity;

    public void setActivity(MainActivity acticity) {
        this.activity = acticity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pid_fragment,container,false);

        textKp = view.findViewById(R.id.textKp);
        textKi = view.findViewById(R.id.textKi);
        textKd = view.findViewById(R.id.textKd);
        textReference = view.findViewById(R.id.textReference);
        textAngle = view.findViewById(R.id.textViewAngle);

        seekBarKp = view.findViewById(R.id.seekBarKp);
        seekBarKi = view.findViewById(R.id.seekBarKi);
        seekBarKd = view.findViewById(R.id.seekBarKd);
        seekBarReference = view.findViewById(R.id.seekBarReference);

        kpUp = view.findViewById(R.id.kp_up);
        kpDown = view.findViewById(R.id.kp_down);

        kiUp = view.findViewById(R.id.ki_up);
        kiDown = view.findViewById(R.id.ki_down);

        kdUp = view.findViewById(R.id.kd_up);
        kdDown = view.findViewById(R.id.kd_down);

        referenceUp = view.findViewById(R.id.reference_up);
        referenceDown = view.findViewById(R.id.reference_down);

        tunerKp = new Tuner(kpUp, kpDown, seekBarKp, textKp, activity, 24, 50, 0, "p");
        tunerKi = new Tuner(kiUp, kiDown, seekBarKi, textKi, activity, 1.8, 50, 0, "i");
        tunerKd = new Tuner(kdUp, kdDown, seekBarKd, textKd, activity,30,50,0,"d");
        tunerReference = new Tuner(referenceUp, referenceDown, seekBarReference, textReference, activity,-2,5,-5,"r");



        return view;
    }

    public void updateInfo(double angle){
        textAngle.setText("Angle: "+ angle);
    }

}
