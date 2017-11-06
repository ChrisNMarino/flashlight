package com.marinoland.flashlight;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private Switch lightSwitch;
    private AdView adView;
    private Flashlight flashlight;

    /**
     * Activity Lifecycle on create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            flashlight = new Flashlight(this);
            lightSwitch = (Switch) findViewById(R.id.lightSwitch);

            lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tryLightState(isChecked);
                }
            });
            tryLightState(lightSwitch.isChecked());

            // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
            MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
            adView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        } catch (Flashlight.FlashlightHardwareException e) {
            handleFatalFlashlightError(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tryLightState(false);
    }

    /**
     * @param e flashlight exception to kill the app with
     */
    private void handleFatalFlashlightError(final Flashlight.FlashlightHardwareException e) {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.setTitle("FAILURE!");
        dlg.setMessage(e.getMessage());
        dlg.setButton(AlertDialog.BUTTON_POSITIVE,
                "Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        dlg.show();
    }

    /**
     * @param isOn attempt to turn it on or off
     */
    private void tryLightState(final boolean isOn) {
        try {
            if (isOn) {
                flashlight.turnOn();
            } else {
                flashlight.turnOff();
            }
        } catch (Flashlight.FlashlightHardwareException e) {
            handleFatalFlashlightError(e);
        }
    }


}
