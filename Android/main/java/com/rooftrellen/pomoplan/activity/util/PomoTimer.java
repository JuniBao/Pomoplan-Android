package com.rooftrellen.pomoplan.activity.util;

import android.media.Ringtone;
import android.widget.TextView;

import com.rooftrellen.pomoplan.activity.main.PomoFragment;

/**
 * PomoTimer is a CustomTimer for Pomodoro time.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class PomoTimer extends CustomTimer {

    /**
     * The parent fragment.
     *
     * @since 1.0.0
     */
    private PomoFragment pomoFrag;

    /**
     * The notification ringtone.
     *
     * @since 1.0.0
     */
    private Ringtone notification;

    /**
     * The prepare minute.
     *
     * @since 1.0.0
     */
    private long preMinute;

    /**
     * The summarize minute.
     *
     * @since 1.0.0
     */
    private long sumMinute;

    /**
     * Initializes with minute setting and related components.
     *
     * @param setMinute the minute setting.
     * @param pomoFrag the parent fragment.
     * @param clock the TextView for displaying clock.
     * @param ring the finish ringtone.
     * @param notification the notification ringtone.
     * @since 1.0.0
     */
    public PomoTimer(long setMinute, PomoFragment pomoFrag, TextView clock, Ringtone ring, Ringtone notification) {
        super(setMinute, clock, ring);
        this.pomoFrag = pomoFrag;
        this.notification = notification;
        preMinute = setMinute - 1;
        sumMinute = 1;
    }

    /**
     * Decrements time every tick and plays notification when necessary.
     *
     * @param millisUntilFinished the imprecise remaining time.
     * @since 1.0.0
     */
    @Override
    public void onTick(long millisUntilFinished) {
        super.onTick(millisUntilFinished);
        if ((curMinute == preMinute || curMinute == sumMinute) && curSecond == 0) {
            notification.play();
        }
    }

    /**
     * Calls back when finish.
     *
     * @since 1.0.0
     */
    @Override
    public void onFinish() {
        super.onFinish();
        // Call back
        pomoFrag.pomoTimerFinish();
    }

}
