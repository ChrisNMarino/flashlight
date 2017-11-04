package com.marinoland.flashlight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Switch lightSwitch;

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
    }

    /**
     * @param isOn attempt to turn it on or off
     */
    private void tryLightState(final boolean isOn) {
        Toast.makeText(getApplicationContext(), "Attempting " + isOn, Toast.LENGTH_SHORT).show();
    }


}
