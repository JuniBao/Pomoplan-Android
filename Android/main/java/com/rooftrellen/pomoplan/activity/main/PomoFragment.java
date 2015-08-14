package com.rooftrellen.pomoplan.activity.main;

import java.util.Calendar;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rooftrellen.pomoplan.activity.util.BreakTimer;
import com.rooftrellen.pomoplan.activity.util.PomoTimer;
import com.rooftrellen.pomoplan.db.PomoDbHelper;
import com.rooftrellen.pomoplan.model.PomoDaily;
import com.rooftrellen.pomoplan.model.PomoTag;
import com.rooftrellen.pomoplan.model.PomoUser;
import com.rooftrellen.pomoplan.model.Pomodoro;
import com.rooftrellen.pomoplan.R;

/**
 * PomoFragment is a Fragment for showing the Pomodoro components.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class PomoFragment extends Fragment {

    /**
     * The parent Activity.
     *
     * @since 1.0.0
     */
    private MainActivity mainActivity;

    /**
     * The database connection.
     *
     * @since 1.0.0
     */
    private PomoDbHelper dbHelper;

    /**
     * The Pomodoro timer.
     *
     * @since 1.0.0
     */
    private PomoTimer pomoTimer;

    /**
     * The break timer.
     *
     * @since 1.0.0
     */
    private BreakTimer breakTimer;

    /**
     * The Pomodoro clock.
     *
     * @since 1.0.0
     */
    private TextView clock;

    /**
     * The WiFi manager.
     *
     * @since 1.0.0
     */
    private WifiManager wifiManager;

    /**
     * The Bluetooth adapter.
     *
     * @since 1.0.0
     */
    private BluetoothAdapter bluetoothAdapter;

    /**
     * The finish ringtone.
     *
     * @since 1.0.0
     */
    private Ringtone ring;

    /**
     * The notification ringtone.
     *
     * @since 1.0.0
     */
    private Ringtone notification;

    /**
     * The tag input.
     *
     * @since 1.0.0
     */
    private EditText editTag;

    /**
     * The memo input.
     *
     * @since 1.0.0
     */
    private EditText editMemo;

    /**
     * The tag label.
     *
     * @since 1.0.0
     */
    private TextView textTag;

    /**
     * The memo label.
     *
     * @since 1.0.0
     */
    private TextView textMemo;

    /**
     * The information label.
     *
     * @since 1.0.0
     */
    private TextView textInfo;

    /**
     * The display date format.
     *
     * @since 1.0.0
     */
    private final static String DATE_FORMAT = "%d/%d/%d";

    /**
     * The display time format.
     *
     * @since 1.0.0
     */
    private final static String TIME_FORMAT = "%02d:%02d";

    /**
     * The default tag.
     *
     * @since 1.0.0
     */
    private final static String DEFAULT_TAG = "Default";

    /**
     * Required empty constructor.
     *
     * @since 1.0.0
     */
    public PomoFragment() {}

    /**
     * Creates a new instance of PomoFragment.
     *
     * @return the new instance.
     * @since 1.0.0
     */
    public static PomoFragment newInstance() {
        return new PomoFragment();
    }

    /**
     * Stores the parent activity and database connection and calendar.
     *
     * @param savedInstanceState the saved instance state.
     * @since 1.0.0
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        dbHelper = mainActivity.getDbHelper();
    }

    /**
     * Initializes view components.
     *
     * @param inflater the inflater.
     * @param container the container.
     * @param savedInstanceState the saved instance state.
     * @return the root view.
     * @since 1.0.0
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pomo, container, false);
        Context context = root.getContext();
        // Set up WiFi
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // Set up Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // Set up ring tones.
        ring = RingtoneManager.getRingtone(context,
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        notification = RingtoneManager.getRingtone(context,
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        // Get view components
        clock = (TextView) root.findViewById(R.id.clockDigits);
        editTag = (EditText) root.findViewById(R.id.editTag);
        editMemo = (EditText) root.findViewById(R.id.editMemo);
        textTag = (TextView) root.findViewById(R.id.textTag);
        textMemo = (TextView) root.findViewById(R.id.textMemo);
        textInfo = (TextView) root.findViewById(R.id.textInfo);
        // Set up click event
        clock.setOnClickListener(new View.OnClickListener() {
            /**
             * Starts timer when click.
             *
             * @param view the view.
             * @since 1.0.0
             */
            @Override
            public void onClick(View view) {
                view.setClickable(false);
                // Hide menu
                mainActivity.setTimerOn(true);
                mainActivity.invalidateOptionsMenu();
                // Set up connections
                if (mainActivity.getWifiOff()) {
                    wifiManager.setWifiEnabled(false);
                }
                if (mainActivity.getBluetoothOff()) {
                    bluetoothAdapter.disable();
                }
                // Start timer
                pomoTimer.start();
                if (mainActivity.getUser() == null) {
                    textInfo.setText(R.string.info_work);
                }
            }
        });
        // Reset timer
        resetTimer();
        // Update user view
        if (mainActivity.getUser() == null) {
            logoutUpdate();
        } else {
            loginUpdate();
        }
        return root;
    }

    /**
     * Updates user view after login.
     *
     * @since 1.0.0
     */
    public void loginUpdate() {
        editTag.setVisibility(View.VISIBLE);
        editMemo.setVisibility(View.VISIBLE);
        textTag.setVisibility(View.VISIBLE);
        textMemo.setVisibility(View.VISIBLE);
        textInfo.setText("");
    }

    /**
     * Updates user view after logout.
     *
     * @since 1.0.0
     */
    public void logoutUpdate() {
        editTag.setVisibility(View.INVISIBLE);
        editMemo.setVisibility(View.INVISIBLE);
        textTag.setVisibility(View.INVISIBLE);
        textMemo.setVisibility(View.INVISIBLE);
        textInfo.setText(R.string.info_wait);
    }

    /**
     * Resets PomoTimer and BreakTimer and displays PomoTimer.
     *
     * @since 1.0.0
     */
    public void resetTimer() {
        pomoTimer = new PomoTimer(mainActivity.getPomoDuration(), this, clock, ring, notification);
        breakTimer = new BreakTimer(mainActivity.getBreakDuration(), this, clock, ring);
        pomoTimer.display();
    }

    /**
     * Calls back when Pomodoro timer finish.
     *
     * @since 1.0.0
     */
    public void pomoTimerFinish() {
        // Restore connections
        if (mainActivity.getWifiOff()) {
            wifiManager.setWifiEnabled(true);
        }
        if (mainActivity.getBluetoothOff()) {
            bluetoothAdapter.enable();
        }
        // Update user information
        updateUser();
        // Change timer
        breakTimer.display();
        breakTimer.start();
        if (mainActivity.getUser() == null) {
            textInfo.setText(R.string.info_break);
        }
    }

    /**
     * Calls back when break timer finish.
     *
     * @since 1.0.0
     */
    public void breakTimerFinish() {
        clock.setClickable(true);
        // Show menu
        mainActivity.setTimerOn(false);
        mainActivity.invalidateOptionsMenu();
        // Enable user input
        editTag.setEnabled(true);
        editMemo.setEnabled(true);
        // Change timer
        pomoTimer.display();
        if (mainActivity.getUser() == null) {
            textInfo.setText(R.string.info_wait);
        }
    }

    /**
     * Updates user's information.
     *
     * @since 1.0.0
     */
    private void updateUser() {
        PomoUser user = mainActivity.getUser();
        if (user != null) {
            // Get user ID
            String userId = user.getId();
            // Get tag
            String tagName = editTag.getText().toString();
            if(tagName.length() == 0) {
                tagName = DEFAULT_TAG;
            }
            PomoTag tag = dbHelper.selectTagByName(tagName, userId);
            if (tag == null) {
                tag = new PomoTag(tagName, userId);
                dbHelper.insertTag(tag, true);
                mainActivity.getTagFragment().addTag(tag);
            }
            // Get memo
            String memo = editMemo.getText().toString();
            // Get date and time
            Calendar calendar = mainActivity.getTime();
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int year = calendar.get(Calendar.YEAR);
            String date = String.format(DATE_FORMAT, month + 1, day, year);
            PomoDaily daily = dbHelper.selectDailyByDate(date, userId);
            String time = String.format(TIME_FORMAT, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE));
            // Update daily
            Pomodoro pomo = new Pomodoro(tag.getId(), memo, daily.getId(), time, userId);
            dbHelper.insertPomo(pomo, true);
            mainActivity.getDailyFragment().addPomo(pomo);
            // Update tag
            mainActivity.getTagFragment().addPomo(pomo);
            // Reset user input
            editTag.setText("");
            editMemo.setText("");
            editTag.setEnabled(false);
            editMemo.setEnabled(false);
        }
    }

}
