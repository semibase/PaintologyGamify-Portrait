package com.paintology.lite.portrait.drawing.CameraPreview.ui;

import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatActivity;

import com.paintology.lite.portrait.drawing.CameraPreview.configuration.CameraConfiguration;
import com.paintology.lite.portrait.drawing.CameraPreview.configuration.ConfigurationProvider;
import com.paintology.lite.portrait.drawing.CameraPreview.controller.CameraController;
import com.paintology.lite.portrait.drawing.CameraPreview.controller.view.CameraView;
import com.paintology.lite.portrait.drawing.CameraPreview.ui.view.AspectFrameLayout;
import com.paintology.lite.portrait.drawing.CameraPreview.utils.Size;
import com.paintology.lite.portrait.drawing.CameraPreview.utils.Utils;
import com.paintology.lite.portrait.drawing.R;


/**
 * Main sandrioscamera activity handling all the transactions
 * Created by Arpit Gandhi on 12/1/16.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
abstract public class SandriosCameraActivity<CameraId> extends AppCompatActivity
        implements ConfigurationProvider, CameraView, SensorEventListener {

    protected AspectFrameLayout previewContainer;
    protected ViewGroup userContainer;
    @CameraConfiguration.SensorPosition
    protected int sensorPosition = CameraConfiguration.SENSOR_POSITION_UNSPECIFIED;
    @CameraConfiguration.DeviceDefaultOrientation
    protected int deviceDefaultOrientation;
    private SensorManager sensorManager = null;
    private CameraController<CameraId> cameraController;
    private int degrees = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraController = createCameraController(this, this);
        cameraController.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        int defaultOrientation = Utils.getDeviceDefaultOrientation(this);

        if (defaultOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            deviceDefaultOrientation = CameraConfiguration.ORIENTATION_LANDSCAPE;
        } else if (defaultOrientation == Configuration.ORIENTATION_PORTRAIT) {
            deviceDefaultOrientation = CameraConfiguration.ORIENTATION_PORTRAIT;
        }

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT > 15) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        setContentView(R.layout.generic_camera_layout);

        previewContainer = (AspectFrameLayout) findViewById(R.id.previewContainer);
        userContainer = (ViewGroup) findViewById(R.id.userContainer);

        onProcessBundle(savedInstanceState);
        setUserContent();
    }

    protected void onProcessBundle(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        cameraController.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            cameraController.onPause();
            sensorManager.unregisterListener(this);
        } catch (Exception e) {
            Log.e("TAG", "Exception onPause " + e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cameraController.onDestroy();
    }

    public final CameraController<CameraId> getCameraController() {
        return cameraController;
    }

    public abstract CameraController<CameraId> createCameraController(CameraView cameraView, ConfigurationProvider configurationProvider);

    private void setUserContent() {
        userContainer.removeAllViews();
        userContainer.addView(getUserContentView(LayoutInflater.from(this), userContainer));
    }

    public final void setCameraPreview(View preview, Size previewSize) {
        onCameraControllerReady();

        if (previewContainer == null || preview == null) return;
        previewContainer.removeAllViews();
        previewContainer.addView(preview);
        Log.e("TAGGG", "View Height Width Print 2> " + previewSize.getWidth() + " >H> " + previewSize.getHeight());
        previewContainer.setAspectRatio(previewSize.getHeight() / (double) previewSize.getWidth());
    }

    public final void clearCameraPreview() {
        if (previewContainer != null)
            previewContainer.removeAllViews();
    }

    abstract View getUserContentView(LayoutInflater layoutInflater, ViewGroup parent);

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if (sensorEvent.values[0] < 4 && sensorEvent.values[0] > -4) {
                    if (sensorEvent.values[1] > 0) {
                        // UP
                        sensorPosition = CameraConfiguration.SENSOR_POSITION_UP;
                        degrees = deviceDefaultOrientation == CameraConfiguration.ORIENTATION_PORTRAIT ? 0 : 90;
                    } else if (sensorEvent.values[1] < 0) {
                        // UP SIDE DOWN
                        sensorPosition = CameraConfiguration.SENSOR_POSITION_UP_SIDE_DOWN;
                        degrees = deviceDefaultOrientation == CameraConfiguration.ORIENTATION_PORTRAIT ? 180 : 270;
                    }
                } else if (sensorEvent.values[1] < 4 && sensorEvent.values[1] > -4) {
                    if (sensorEvent.values[0] > 0) {
                        // LEFT
                        sensorPosition = CameraConfiguration.SENSOR_POSITION_LEFT;
                        degrees = deviceDefaultOrientation == CameraConfiguration.ORIENTATION_PORTRAIT ? 90 : 180;
                    } else if (sensorEvent.values[0] < 0) {
                        // RIGHT
                        sensorPosition = CameraConfiguration.SENSOR_POSITION_RIGHT;
                        degrees = deviceDefaultOrientation == CameraConfiguration.ORIENTATION_PORTRAIT ? 270 : 0;
                    }
                }
                onScreenRotation(degrees);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public final int getSensorPosition() {
        return sensorPosition;
    }

    @Override
    public final int getDegrees() {
        return degrees;
    }

    protected abstract void onScreenRotation(int degrees);

    protected void onCameraControllerReady() {
    }
}
