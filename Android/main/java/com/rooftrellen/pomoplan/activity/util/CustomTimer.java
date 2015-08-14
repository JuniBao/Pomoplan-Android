package com.rooftrellen.pomoplan.activity.util;

import android.media.Ringtone;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * CustomTimer is a CountDownTimer displaying in a TextView and playing ringtone when finish.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public abstract class CustomTimer extends CountDownTimer {

    /**
     * The minute setting.
     *
     * @since 1.0.0
     */
    private long setMinute;

    /**
     * The TextView for displaying clock.
     *
     * @since 1.0.0
     */
    private TextView clock;

    /**
     * The finish ringtone.
     *
     * @since 1.0.0
     */
    private Ringtone ring;

    /**
     * The current minute.
     *
     * @since 1.0.0
     */
    protected long curMinute;

    /**
     * The current second.
     *
     * @since 1.0.0
     */
    protected long curSecond;

    /**
     * The tick interval in ms.
     *
     * @since 1.0.0
     */
    private final static long INTERVAL = 1000;

    /**
     * Ratio between minute and millisecond.
     *
     * @since 1.0.0
     */
    private final static long MINUTE_TO_MILLISECOND = 60000;

    /**
     * The display time format.
     *
     * @since 1.0.0
     */
    private final static String TIME_FORMAT = "%02d : %02d";

    /**
     * Initializes with minute setting and ringtone.
     *
     * @param setMinute the minute setting.
     * @param ring the finish ringtone.
     * @since 1.0.0
     */
    public CustomTimer(long setMinute, TextView clock, Ringtone ring) {
        super(setMinute * MINUTE_TO_MILLISECOND, INTERVAL);
        this.setMinute = setMinute;
        this.clock = clock;
        this.ring = ring;
        curMinute = setMinute;
        curSecond = 0;
    }

    /**
     * Decrements time every tick.
     *
     * @param millisUntilFinished the imprecise remaining time.
     * @since 1.0.0
     */
    @Override
    public void onTick(long millisUntilFinished) {
        if (curSecond == 0) {
            curSecond = 59;
            curMinute--;
        } else {
            curSecond--;
        }
        display();
    }

    /**
     * Refreshes the clock and plays the ringtone after finished.
     *
     * @since 1.0.0
     */
    @Override
    public void onFinish() {
        curMinute = 0;
        curSecond = 0;
        display();
        curMinute = setMinute;
        ring.play();
    }

    /**
     * Displays the time.
     *
     * @since 1.0.0
     */
    public void display() {
        clock.setText(String.format(TIME_FORMAT, curMinute, curSecond));
    }

}
