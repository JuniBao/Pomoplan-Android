package com.rooftrellen.pomoplan.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

import com.rooftrellen.pomoplan.exception.PomoException;

/**
 * PomoTag is a class for storing information about Pomodoro tag.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class PomoTag implements Serializable {

    /**
     * Tag's ID.
     *
     * @since 1.0.0
     */
    private String id;

    /**
     * Tag's name.
     *
     * @since 1.0.0
     */
    private String name;

    /**
     * Tag's plan.
     *
     * @since 1.0.0
     */
    private int plan;

    /**
     * Tag's user ID.
     *
     * @since 1.0.0
     */
    private String userId;

    /**
     * Tag's completed Pomodoro.
     *
     * @since 1.0.0
     */
    private ArrayList<Pomodoro> completed;

    /**
     * Initializes with tag's information.
     *
     * @param id the tag's ID.
     * @param name the tag's name.
     * @param plan the tag's plan.
     * @param userId the tag's user ID.
     * @since 1.0.0
     */
    public PomoTag(String id, String name, int plan, String userId) {
        this.id = id;
        this.name = name;
        this.plan = plan;
        this.userId = userId;
        completed = new ArrayList<>();
    }

    /**
     * Initializes with tag's information.
     *
     * @param name the tag's name.
     * @param userId the tag's user ID.
     * @since 1.0.0
     */
    public PomoTag(String name, String userId) {
        this.name = name;
        plan = 0;
        this.userId = userId;
        completed = new ArrayList<>();
    }

    /**
     * Gets tag's ID.
     *
     * @return the tag's ID.
     * @since 1.0.0
     */
    public String getId() {
        return id;
    }

    /**
     * Gets tag's name.
     *
     * @return the tag's name.
     * @since 1.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * Gets tag's plan.
     *
     * @return the tag's plan.
     * @since 1.0.0
     */
    public int getPlan() {
        return plan;
    }

    /**
     * Gets tag's user ID.
     *
     * @return the user ID.
     * @since 1.0.0
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets tag's completed Pomodoro.
     *
     * @return the tag's completed Pomodoro.
     * @since 1.0.0
     */
    public ArrayList<Pomodoro> getCompleted() {
        return completed;
    }

    /**
     * Sets tag's ID.
     *
     * @param id the tag's ID.
     * @since 1.0.0
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets tag's name.
     *
     * @param name the tag's name.
     * @since 1.0.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets tag's plan.
     *
     * @param plan the tag's plan.
     * @since 1.0.0
     */
    public void setPlan(int plan) {
        this.plan = plan;
    }

    /**
     * Sets tag's completed Pomodoro.
     *
     * @param completed the tag's completed Pomodoro.
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
     * @return the String value of PomoTag.
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return "id=" + id + "&"
                + "name=" + name.replaceAll(" ", "+") + "&"
                + "plan=" + plan + "&"
                + "userid=" + userId;
    }

}
