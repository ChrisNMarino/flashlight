package com.marinoland.flashlight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private Switch lightSwitch;
    private AdView adView;

    /**
     * Activity Lifecycle on create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lightSwitch = (Switch)findViewById(R.id.lightSwitch);

        lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tryLightState(isChecked);
            }
        });
        tryLightState(lightSwitch.isChecked());

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    /**
     * @param isOn attempt to turn it on or off
     */
    private void tryLightState(final boolean isOn) {
        Toast.makeText(getApplicationContext(), "Attempting " + isOn, Toast.LENGTH_SHORT).show();
    }


}
