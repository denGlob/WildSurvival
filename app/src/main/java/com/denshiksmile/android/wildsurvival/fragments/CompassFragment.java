package com.denshiksmile.android.wildsurvival.fragments;

import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.denshiksmile.android.wildsurvival.R;

/**
 * Created by Denys Smile on 4/6/2016.
 */
public class CompassFragment extends Fragment implements SensorListener {

    private ImageView compass;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;
    static final int sensor = SensorManager.SENSOR_ORIENTATION;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_compass, container, false);
        compass = (ImageView) v.findViewById(R.id.compass_view);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, sensor);

    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor != CompassFragment.sensor)
            return;
        float degree = Math.round(values[0]);
        RotateAnimation rotateAnimation = new RotateAnimation(
                currentDegree,
                - degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(100);
        rotateAnimation.setFillAfter(true);

        compass.startAnimation(rotateAnimation);
        currentDegree = - degree;
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
