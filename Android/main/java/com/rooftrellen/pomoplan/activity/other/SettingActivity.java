package com.rooftrellen.pomoplan.activity.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.model.PomoUser;

/**
 * SettingActivity is an activity for showing user settings.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class SettingActivity extends Activity {

    /**
     * The switch for WiFi.
     *
     * @since 1.0.0
     */
    private Switch switchWifi;

    /**
     * The switch for bluetooth.
     *
     * @since 1.0.0
     */
    private Switch switchBluetooth;

    /**
     * The TextView for showing Pomodoro duration.
     *
     * @since 1.0.0
     */
    private TextView textPomoDura;

    /**
     * The TextView for showing break duration.
     *
     * @since 1.0.0
     */
    private TextView textBreakDura;

    /**
     * The user.
     *
     * @since 1.0.0
     */
    private PomoUser user;

    /**
     * The minimum Pomodoro duration.
     *
     * @since 1.0.0
     */
    private final static int MIN_POMO_DURATION = 1;

    /**
     * The maximum Pomodoro duration.
     *
     * @since 1.0.0
     */
    private final static int MAX_POMO_DURATION = 50;

    /**
     * The minimum break duration.
     *
     * @since 1.0.0
     */
    private final static int MIN_BREAK_DURATION = 1;

    /**
     * The maximum break duration.
     *
     * @since 1.0.0
     */
    private final static int MAX_BREAK_DURATION = 10;

    /**
     * Initializes user settings and views.
     *
     * @param savedInstanceState the saved instance state.
     * @since 1.0.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        // Get user setting
        Intent intent = getIntent();
        user = (PomoUser) intent.getSerializableExtra(ExtraName.POMO_USER);
        // Setup view
        switchWifi = (Switch) findViewById(R.id.switchWifi);
        switchWifi.setChecked(user.getWifiOff());
        switchBluetooth = (Switch) findViewById(R.id.switchBluetooth);
        switchBluetooth.setChecked(user.getBluetoothOff());
        textPomoDura = (TextView) findViewById(R.id.textPomoDura);
        textPomoDura.setText(String.valueOf(user.getPomoDuration()));
        textBreakDura = (TextView) findViewById(R.id.textBreakDura);
        textBreakDura.setText(String.valueOf(user.getBreakDuration()));
    }

    /**
     * Decreases Pomodoro duration when click.
     *
     * @param view the decrease button.
     * @since 1.0.0
     */
    public void decPomoDura(View view) {
        textPomoDura.setText(String.valueOf(Math.max(MIN_POMO_DURATION,
                Integer.parseInt(textPomoDura.getText().toString()) - 1)));
    }

    /**
     * Increases Pomodoro duration when click.
     *
     * @param view the increase button.
     * @since 1.0.0
     */
    public void incPomoDura(View view) {
        textPomoDura.setText(String.valueOf(Math.min(MAX_POMO_DURATION,
                Integer.parseInt(textPomoDura.getText().toString()) + 1)));
    }

    /**
     * Decreases break duration when click.
     *
     * @param view the decrease button.
     * @since 1.0.0
     */
    public void decBreakDura(View view) {
        textBreakDura.setText(String.valueOf(Math.max(MIN_BREAK_DURATION,
                Integer.parseInt(textBreakDura.getText().toString()) - 1)));
    }

    /**
     * Increases break duration when click.
     *
     * @param view the increase button.
     * @since 1.0.0
     */
    public void incBreakDura(View view) {
        textBreakDura.setText(String.valueOf(Math.min(MAX_BREAK_DURATION,
                Integer.parseInt(textBreakDura.getText().toString()) + 1)));
    }

    /**
     * Sends back update when back.
     *
     * @since 1.0.0
     */
    @Override
    public void onBackPressed() {
        // Update data
        user.setWifiOff(switchWifi.isChecked());
        user.setBluetoothOff(switchBluetooth.isChecked());
        user.setPomoDuration(Integer.parseInt(textPomoDura.getText().toString()));
        user.setBreakDuration(Integer.parseInt(textBreakDura.getText().toString()));
        Intent i = new Intent();
        i.putExtra(ExtraName.POMO_USER, user);
        setResult(Activity.RESULT_OK, i);
        super.onBackPressed();
    }

}
