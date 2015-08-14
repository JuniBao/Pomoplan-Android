package com.rooftrellen.pomoplan.activity.util;

import android.media.Ringtone;
import android.widget.TextView;

import com.rooftrellen.pomoplan.activity.main.PomoFragment;

/**
 * BreakTimer is a CustomTimer for break time.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class BreakTimer extends CustomTimer {

    /**
     * The parent fragment.
     *
     * @since 1.0.0
     */
    private PomoFragment pomoFrag;

    /**
     * Initializes with minute setting and related components.
     *
     * @param setMinute the minute setting.
     * @param pomoFrag the parent fragment.
     * @param clock the TextView for displaying clock.
     * @param ring the finish ringtone.
     * @since 1.0.0
     */
    public BreakTimer(long setMinute, PomoFragment pomoFrag, TextView clock, Ringtone ring) {
        super(setMinute, clock, ring);
        this.pomoFrag = pomoFrag;
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
        pomoFrag.breakTimerFinish();
    }

}
