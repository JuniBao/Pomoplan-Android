package com.rooftrellen.pomoplan.model;

import java.io.Serializable;

/**
 * Pomodoro is a class for storing information of Pomodoro.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class Pomodoro implements Serializable {

    /**
     * Pomodoro's ID.
     *
     * @since 1.0.0
     */
    private String id;

    /**
     * Pomodoro's tag ID.
     *
     * @since 1.0.0
     */
    private String tagId;

    /**
     * Pomodoro's memo.
     *
     * @since 1.0.0
     */
    private String memo;

    /**
     * Pomodoro's daily ID.
     *
     * @since 1.0.0
     */
    private String dailyId;

    /**
     * Pomodoro's time.
     *
     * @since 1.0.0
     */
    private String time;

    /**
     * Pomodoro's user ID.
     *
     * @since 1.0.0
     */
    private String userId;

    /**
     * Initializes with information of Pomodoro.
     *
     * @param id the Pomodoro's ID.
     * @param tagId the Pomodoro's tag ID.
     * @param memo the Pomodoro's memo.
     * @param dailyId the Pomodoro's daily ID.
     * @param time the Pomodoro's time.
     * @param userId the Pomodoro's user ID.
     * @since 1.0.0
     */
    public Pomodoro(String id, String tagId, String memo, String dailyId, String time,
                    String userId) {
        this.id = id;
        this.tagId = tagId;
        this.memo = memo;
        this.dailyId = dailyId;
        this.time = time;
        this.userId = userId;
    }

    /**
     * Initializes with information of Pomodoro.
     *
     * @param tagId the Pomodoro's tag ID.
     * @param memo the Pomodoro's memo.
     * @param dailyId the Pomodoro's daily ID.
     * @param time the Pomodoro's time.
     * @param userId the Pomodoro's user ID.
     * @since 1.0.0
     */
    public Pomodoro(String tagId, String memo, String dailyId, String time, String userId) {
        this.tagId = tagId;
        this.memo = memo;
        this.dailyId = dailyId;
        this.time = time;
        this.userId = userId;
    }

    /**
     * Gets the ID.
     *
     * @return the ID.
     * @since 1.0.0
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the tag ID.
     *
     * @return the tag ID.
     * @since 1.0.0
     */
    public String getTagId() {
        return tagId;
    }

    /**
     * Gets the memo.
     *
     * @return the memo.
     * @since 1.0.0
     */
    public String getMemo() {
        return memo;
    }

    /**
     * Gets the daily ID.
     *
     * @return the daily ID.
     * @since 1.0.0
     */
    public String getDailyId() {
        return dailyId;
    }

    /**
     * Gets the time.
     *
     * @return the time.
     * @since 1.0.0
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID.
     * @since 1.0.0
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the ID.
     *
     * @param id the ID.
     * @since 1.0.0
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the String value.
     *
     * @return the String value of Pomodoro.
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return "id=" + id + "&"
                + "tagid=" + tagId + "&"
                + "memo=" + memo.replaceAll(" ", "+") + "&"
                + "dailyid=" + dailyId + "&"
                + "time=" + time + "&"
                + "userid=" + userId;
    }

}
