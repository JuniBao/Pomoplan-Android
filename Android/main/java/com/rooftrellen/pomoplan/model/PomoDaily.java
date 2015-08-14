package com.rooftrellen.pomoplan.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

import com.rooftrellen.pomoplan.exception.PomoException;

/**
 * PomoDaily is a class for storing information about Pomodoro daily.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class PomoDaily implements Serializable {

    /**
     * Daily's ID.
     *
     * @since 1.0.0
     */
    private String id;

    /**
     * Daily's date.
     *
     * @since 1.0.0
     */
    private String date;

    /**
     * Daily's plan.
     *
     * @since 1.0.0
     */
    private int plan;

    /**
     * Daily's user ID.
     *
     * @since 1.0.0
     */
    private String userId;

    /**
     * Daily's completed Pomodoro.
     *
     * @since 1.0.0
     */
    private ArrayList<Pomodoro> completed;

    /**
     * Initializes with daily's information.
     *
     * @param id the daily's ID.
     * @param date the daily's date.
     * @param plan the daily's plan.
     * @param userId the daily's user ID.
     * @since 1.0.0
     */
    public PomoDaily(String id, String date, int plan, String userId) {
        this.id = id;
        this.date = date;
        this.plan = plan;
        this.userId = userId;
        completed = new ArrayList<>();
    }

    /**
     * Initializes with daily's information.
     *
     * @param date the daily's date.
     * @param userId the daily's user ID.
     * @since 1.0.0
     */
    public PomoDaily(String date, String userId) {
        this.date = date;
        plan = 0;
        this.userId = userId;
        completed = new ArrayList<>();
    }

    /**
     * Gets daily's ID.
     *
     * @return the daily's ID.
     * @since 1.0.0
     */
    public String getId() {
        return id;
    }

    /**
     * Gets daily's date.
     *
     * @return the daily's date.
     * @since 1.0.0
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets daily's plan.
     *
     * @return the daily's plan.
     * @since 1.0.0
     */
    public int getPlan() {
        return plan;
    }

    /**
     * Gets daily's user ID.
     *
     * @return the user ID.
     * @since 1.0.0
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets daily's completed Pomodoro.
     *
     * @return the daily's completed Pomodoro.
     * @since 1.0.0
     */
    public ArrayList<Pomodoro> getCompleted() {
        return completed;
    }

    /**
     * Sets daily's ID.
     *
     * @param id the daily's ID.
     * @since 1.0.0
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets daily's plan.
     *
     * @param plan the daily's plan.
     * @since 1.0.0
     */
    public void setPlan(int plan) {
        this.plan = plan;
    }

    /**
     * Sets daily's Pomodoro.
     *
     * @param completed the daily's Pomodoro.
     * @since 1.0.0
     */
    public void setCompleted(ArrayList<Pomodoro> completed) {
        try {
            if (completed == null) {
                throw new PomoException(PomoException.ErrorCode.NULL_OBJECT,
                        "Tag's completed list is null.");
            } else {
                this.completed = completed;
            }
        } catch (PomoException e) {
            Log.e(e.getErrorTag(), e.toString());
            this.completed = new ArrayList<>();
        }
    }

    /**
     * Gets the String value.
     *
     * @return the String value of PomoDaily.
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return "id=" + id + "&"
                + "date=" + date + "&"
                + "plan=" + plan + "&"
                + "userid=" + userId;
    }

}
