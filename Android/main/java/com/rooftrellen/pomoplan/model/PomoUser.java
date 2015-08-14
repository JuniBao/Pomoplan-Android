package com.rooftrellen.pomoplan.model;

import java.io.Serializable;

import android.util.Log;

import com.rooftrellen.pomoplan.exception.PomoException;

/**
 * User is a class for storing user's information and settings.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class PomoUser implements Serializable {

    /**
     * User ID.
     *
     * @since 1.0.0
     */
    private String id;

    /**
     * User's name.
     *
     * @since 1.0.0
     */
    private String name;

    /**
     * User's password.
     *
     * @since 1.0.0
     */
    private String password;

    /**
     * WiFi's option. True is Off.
     *
     * @since 1.0.0
     */
    private boolean wifiOff;

    /**
     * Bluetooth's option. True is Off.
     *
     * @since 1.0.0
     */
    private boolean bluetoothOff;

    /**
     * Pomodoro's duration in minute.
     *
     * @since 1.0.0
     */
    private int pomoDuration;

    /**
     * Break's duration in minute.
     *
     * @since 1.0.0
     */
    private int breakDuration;

    /**
     * Default WiFi option.
     *
     * @since 1.0.0
     */
    public final static boolean DEFAULT_WIFI_OFF = false;

    /**
     * Default Bluetooth option.
     *
     * @since 1.0.0
     */
    public final static boolean DEFAULT_BLUETOOTH_OFF = false;

    /**
     * Default Pomodoro duration.
     *
     * @since 1.0.0
     */
    public final static int DEFAULT_POMO_DURATION = 1;

    /**
     * Default break duration.
     *
     * @since 1.0.0
     */
    public final static int DEFAULT_BREAK_DURATION = 1;

    /**
     * Initializes with user's information and settings.
     *
     * @param id the user ID.
     * @param name the user's name.
     * @param password the user's password.
     * @param wifiOff the user's WiFi option. True is Off.
     * @param bluetoothOff the user's Bluetooth option. True is Off.
     * @param pomoDuration the user's Pomodoro duration in minute.
     * @param breakDuration the user's break duration in minute.
     * @since 1.0.0
     */
    public PomoUser(String id, String name, String password, boolean wifiOff,
                    boolean bluetoothOff,int pomoDuration, int breakDuration) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.wifiOff = wifiOff;
        this.bluetoothOff = bluetoothOff;
        setPomoDuration(pomoDuration);
        setBreakDuration(breakDuration);
    }

    /**
     * Initializes with user's information and settings.
     *
     * @param name the user's name.
     * @param password the user's password.
     * @since 1.0.0
     */
    public PomoUser(String name, String password) {
        this.name = name;
        this.password = password;
        wifiOff = DEFAULT_WIFI_OFF;
        bluetoothOff = DEFAULT_BLUETOOTH_OFF;
        pomoDuration = DEFAULT_POMO_DURATION;
        breakDuration = DEFAULT_BREAK_DURATION;
    }

    /**
     * Initializes with default settings.
     *
     * @since 1.0.0
     */
    public PomoUser() {
        wifiOff = DEFAULT_WIFI_OFF;
        bluetoothOff = DEFAULT_BLUETOOTH_OFF;
        pomoDuration = DEFAULT_POMO_DURATION;
        breakDuration = DEFAULT_BREAK_DURATION;
    }

    /**
     * Gets user's ID.
     *
     * @return the user's ID.
     * @since 1.0.0
     */
    public String getId() {
        return id;
    }

    /**
     * Gets user's name.
     *
     * @return the user's name.
     * @since 1.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * Gets user's password.
     *
     * @return the user's password.
     * @since 1.0.0
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets user's WiFi option.
     *
     * @return the user's WiFi option. True is Off.
     * @since 1.0.0
     */
    public boolean getWifiOff() {
        return wifiOff;
    }

    /**
     * Gets user's Bluetootch option.
     *
     * @return the user's Bluetooth option. True is Off.
     * @since 1.0.0
     */
    public boolean getBluetoothOff() {
        return bluetoothOff;
    }

    /**
     * Gets user's Pomodoro duration.
     *
     * @return the user's Pomodoro duration in minute.
     * @since 1.0.0
     */
    public int getPomoDuration() {
        return pomoDuration;
    }

    /**
     * Gets user's break duration.
     *
     * @return the user's break duration in minute.
     * @since 1.0.0
     */
    public int getBreakDuration() {
        return breakDuration;
    }

    /**
     * Sets user's ID.
     *
     * @param id the user ID.
     * @since 1.0.0
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets user's WiFi option.
     *
     * @param wifiOff the user's WiFi option. True is Off.
     * @since 1.0.0
     */
    public void setWifiOff(boolean wifiOff) {
        this.wifiOff = wifiOff;
    }

    /**
     * Sets user's Bluetooth option.
     *
     * @param bluetoothOff the user's Bluetooth option. True is Off.
     * @since 1.0.0
     */
    public void setBluetoothOff(boolean bluetoothOff) {
        this.bluetoothOff = bluetoothOff;
    }

    /**
     * Sets user's Pomodoro duration.
     *
     * @param pomoDuration the user's Pomodoro duration in minute.
     * @since 1.0.0
     */
    public void setPomoDuration(int pomoDuration) {
        try {
            if (pomoDuration <= 0) {
                throw new PomoException(PomoException.ErrorCode.NEGATIVE_VALUE, "User's Pomodoro duration is non-positive.");
            } else {
                this.pomoDuration = pomoDuration;
            }
        } catch (PomoException e) {
            Log.e(e.getErrorTag(), e.toString());
            this.pomoDuration = DEFAULT_POMO_DURATION;
        }
    }

    /**
     * Sets user's break duration.
     *
     * @param breakDuration the user's break duration in minute.
     * @since 1.0.0
     */
    public void setBreakDuration(int breakDuration) {
        try {
            if (pomoDuration <= 0) {
                throw new PomoException(PomoException.ErrorCode.NEGATIVE_VALUE, "User's break duration is non-positive.");
            } else {
                this.breakDuration = breakDuration;
            }
        } catch (PomoException e) {
            Log.e(e.getErrorTag(), e.toString());
            this.breakDuration = DEFAULT_BREAK_DURATION;
        }
    }

    /**
     * Gets the String value.
     *
     * @return the String value of User.
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return "id=" + id + "&"
                + "name=" + name + "&"
                + "password=" + password + "&"
                + "wifioff=" + (wifiOff?1:0) + "&"
                + "bluetoothoff=" + (bluetoothOff?1:0) + "&"
                + "pomoduration=" + pomoDuration + "&"
                + "breakduration=" + breakDuration;
    }

}
