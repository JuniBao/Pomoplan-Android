package com.rooftrellen.pomoplan.activity.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * CustomViewPager is a ViewPager that can be enabled/disabled paging.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class CustomViewPager extends ViewPager {

    /**
     * The paging enable flag.
     *
     * @since 1.0.0
     */
    private boolean pagingEnabled;

    /**
     * Initializes with context.
     *
     * @param context the context.
     * @since 1.0.0
     */
    public CustomViewPager(Context context) {
        super(context);
        pagingEnabled = false;
    }

    /**
     * Initializes with context and attributes.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @since 1.0.0
     */
    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        pagingEnabled = false;
    }

    /**
     * Sets the paging enable flag.
     *
     * @param pagingEnabled the paging enable flag.
     * @since 1.0.0
     */
    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }

    /**
     * Handles touch screen motion event.
     *
     * @param event the touch screen motion event.
     * @return if success.
     * @since 1.0.0
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Control paging
        return this.pagingEnabled && super.onTouchEvent(event);
    }

    /**
     * Handles intercept touch screen motion event.
     *
     * @param event the intercept touch screen motion event.
     * @return if success.
     * @since 1.0.0
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Control paging
        return this.pagingEnabled && super.onInterceptTouchEvent(event);
    }

}
