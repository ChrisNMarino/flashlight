package com.marinoland.flashlight;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

public class Flashlight {
    private boolean supportsFlash;

    private Context context;
    private CameraManager camMan;
    private String cameraId;

    public static class FlashlightHardwareException extends Exception {
        public FlashlightHardwareException(final String msg) {
            super(msg);
        }
    }

    /**
     * constructor
     *
     * @param context required
     */
    public Flashlight(final Context context) throws FlashlightHardwareException {
        this.context = context;
        supportsFlash = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH);
        if (!supportsFlash) {
            throw new FlashlightHardwareException("Unable to detect LED on this device");
        }
        camMan = (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
    }

    /**
     * @throws FlashlightHardwareException
     */
    public void turnOn() throws FlashlightHardwareException {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                camMan.setTorchMode(getCameraId(), true);
            }
        } catch (CameraAccessException e) {
            throw new FlashlightHardwareException(
                    "Exception while turning on LED");
        }
    }

    /**
     * @throws FlashlightHardwareException
     */
    public void turnOff() throws FlashlightHardwareException {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                camMan.setTorchMode(getCameraId(), false);
            }
        } catch (CameraAccessException e) {
            throw new FlashlightHardwareException(
                    "Exception while turning on LED");
        }
    }

    /**
     * @return camera id... but only one ever
     */
    private String getCameraId() throws FlashlightHardwareException {
        if (cameraId == null) {
            try {
                cameraId = camMan.getCameraIdList()[0];
            } catch(CameraAccessException e) {
                ((Activity)context).finish();
                throw new FlashlightHardwareException(
                        "Unable to locate camera hardware for flashlight");
            }
        }
        return cameraId;
    }

}
