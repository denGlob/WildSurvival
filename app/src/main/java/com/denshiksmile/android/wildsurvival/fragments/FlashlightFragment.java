package com.denshiksmile.android.wildsurvival.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.denshiksmile.android.wildsurvival.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Denys Smile on 4/6/2016.
 */
public class FlashlightFragment extends Fragment {

    private android.hardware.Camera camera;
    private android.hardware.Camera.Parameters parametersOnOff;
    private android.hardware.Camera.Parameters parametersSOS;

    private ImageButton flashLightButton;
    private ImageButton sosButton;

    private boolean isFlashLightOn = false;
    private boolean onSOS = false;

    public static Timer mTimer;
    private MyTimerTask mTimerTask;

    public FlashlightFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_flashlight, container, false);


        flashLightButton = (ImageButton) v.findViewById(R.id.flashlight_button);
        flashLightButton.setOnClickListener(new FlashOnOffListener());
        sosButton = (ImageButton) v.findViewById(R.id.sos_button);
        sosButton.setOnClickListener(new FlashSOSListener());

        if (isFlashSupported() && isCameraRealesed == false) {
            camera = Camera.open();
            parametersOnOff = camera.getParameters();
            parametersSOS = camera.getParameters();
        } else {
            showNoFlashAlert();
        }
        return v;
    }

    private class FlashOnOffListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (isFlashLightOn) {
                if(mTimer != null)
                    mTimer.cancel();
                flashLightButton.setImageResource(R.drawable.flashlight_off);
                sosButton.setImageResource(R.drawable.sos_button_off);
                setOffSingleFlash(parametersSOS);
                setOffSingleFlash(parametersOnOff);
                isFlashLightOn = false;
            } else {
                if(mTimer != null)
                    mTimer.cancel();
                flashLightButton.setImageResource(R.drawable.flashlight_on);
                sosButton.setImageResource(R.drawable.sos_button_off);
                setOffSingleFlash(parametersSOS);
                setOnSingleFlash(parametersOnOff);
                isFlashLightOn = true;
            }
        }

    }

    private class FlashSOSListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            flashLightButton.setImageResource(R.drawable.flashlight_off);
            if(onSOS == false) {
                if (mTimer != null) {
                    mTimer.cancel();
                }
                sosButton.setImageResource(R.drawable.sos_button_on);
                setOffSingleFlash(parametersOnOff);
                if(isCameraRealesed == false) {
                    mTimer = new Timer();
                    mTimerTask = new MyTimerTask();
                    mTimer.schedule(mTimerTask, 1000, 1500);
                }
                onSOS = true;
            }
            else {
                if (mTimer != null) {
                    mTimer.cancel();
                }
                sosButton.setImageResource(R.drawable.sos_button_off);
                setOffSingleFlash(parametersSOS);
                onSOS = false;
            }
        }
    }

    boolean isCameraRealesed = false;

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            try {
                if(isCameraRealesed == false) {
                    setOnSingleFlash(parametersSOS);
                    setOffSingleFlash(parametersSOS);
                }
            } catch (RuntimeException re) {
                isCameraRealesed = true;
            }

        }
    }

    public void setOnSingleFlash(android.hardware.Camera.Parameters parameters) {
        parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
    }

    public void setOffSingleFlash(android.hardware.Camera.Parameters parameters) {
        parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
    }

    private void showNoFlashAlert() {
        new AlertDialog.Builder(getActivity())
                .setMessage("Your device hardware does not support flashlight!")
                .setIcon(android.R.drawable.ic_dialog_alert).setTitle("Error")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,  int which) {
                        dialog.dismiss();
                        onStop();
                    }
                }).show();
    }

    private boolean isFlashSupported() {
        PackageManager pm = getActivity().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onDestroy() {
        if(camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (camera != null) {
            mTimerTask.cancel();
            camera.stopPreview();
            camera.release();
            isCameraRealesed = true;
            camera = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        camera = Camera.open();
        flashLightButton = (ImageButton) getActivity().findViewById(R.id.flashlight_button);
//        flashLightButton.setOnClickListener(new FlashOnOffListener());
        sosButton = (ImageButton) getActivity().findViewById(R.id.sos_button);
//        sosButton.setOnClickListener(new FlashSOSListener());
//        flashLightButton.setImageResource(R.drawable.flashlight_off);
//        sosButton.setImageResource(R.drawable.sos_button_off);
//        parametersOnOff = camera.getParameters();
//        parametersSOS = camera.getParameters();
//        setOffSingleFlash(parametersOnOff);
//        setOffSingleFlash(parametersSOS);
        isCameraRealesed = false;
    }


}
