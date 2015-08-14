package com.rooftrellen.pomoplan.db;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.backend.PostService;
import com.rooftrellen.pomoplan.model.Pomodoro;

/**
 * TablePomo is a class for controlling the CRUD of Pomodoro table.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class TablePomo implements TableInterface<Pomodoro> {

    /**
     * The Android ID.
     *
     * @since 1.0.0
     */
    private String androidId;

    /**
     * The application context.
     *
     * @since 1.0.0
     */
    private Context context;

    /**
     * The readable database.
     *
     * @since 1.0.0
     */
    private SQLiteDatabase readDb;

    /**
     * The writable database.
     *
     * @since 1.0.0
     */
    private SQLiteDatabase writeDb;

    /**
     * Initializes with Android ID, context and databases.
     *
     * @param androidId the Android ID.
     * @param context the context.
     * @param readDb the readable database.
     * @param writeDb the writable database.
     */
    public TablePomo(String androidId, Context context, SQLiteDatabase readDb, SQLiteDatabase writeDb) {
        this.androidId = androidId;
        this.context = context;
        this.readDb = readDb;
        this.writeDb = writeDb;
    }

    /**
     * Inserts a Pomodoro.
     *
     * @param object the Pomodoro.
     * @param backend the flag of backend.
     * @since 1.0.0
     */
    @Override
    public void insert(Pomodoro object, boolean backend) {
        ContentValues values = new ContentValues();
        String id;
        if (backend) {
            id = androidId + Calendar.getInstance().getTimeInMillis();
        } else {
            id = object.getId();
        }
        values.put(PomoContract.PomoEntry.COLUMN_NAME_ID, id);
        values.put(PomoContract.PomoEntry.COLUMN_NAME_TAG_ID, object.getTagId());
        values.put(PomoContract.PomoEntry.COLUMN_NAME_MEMO, object.getMemo());
        values.put(PomoContract.PomoEntry.COLUMN_NAME_DAILY_ID, object.getDailyId());
        values.put(PomoContract.PomoEntry.COLUMN_NAME_TIME, object.getTime());
        values.put(PomoContract.PomoEntry.COLUMN_NAME_USER_ID, object.getUserId());
        writeDb.insert(PomoContract.PomoEntry.TABLE_NAME, null, values);
        if (backend) {
            object.setId(id);
            // Insert in backend
            Intent intent = new Intent(context, PostService.class);
            intent.putExtra(ExtraName.SERVICE_TYPE, PostService.INSERT_POMO);
            intent.putExtra(ExtraName.SERVICE_OBJECT, object);
            context.startService(intent);
        }
    }

    /**
     * Updates a Pomodoro.
     *
     * @param object the Pomodoro.
     * @since 1.0.0
     */
    @Override
    public void update(Pomodoro object) {
        ContentValues values = new ContentValues();
        values.put(PomoContract.PomoEntry.COLUMN_NAME_TAG_ID, object.getTagId());
        values.put(PomoContract.PomoEntry.COLUMN_NAME_MEMO, object.getMemo());
        values.put(PomoContract.PomoEntry.COLUMN_NAME_DAILY_ID, object.getDailyId());
        values.put(PomoContract.PomoEntry.COLUMN_NAME_TIME, object.getTime());
        values.put(PomoContract.PomoEntry.COLUMN_NAME_USER_ID, object.getUserId());
        writeDb.update(
                PomoContract.PomoEntry.TABLE_NAME,
                values,
                PomoContract.PomoEntry.COLUMN_NAME_ID + "=?",
                new String[] { object.getId() });
        // Update in backend
        Intent intent = new Intent(context, PostService.class);
        intent.putExtra(ExtraName.SERVICE_TYPE, PostService.UPDATE_POMO);
        intent.putExtra(ExtraName.SERVICE_OBJECT, object);
        context.startService(intent);
    }

    /**
     * Deletes a Pomodoro.
     *
     * @param object the Pomodoro.
     * @since 1.0.0
     */
    @Override
    public void delete(Pomodoro object) {
        writeDb.delete(
                PomoContract.PomoEntry.TABLE_NAME,
                PomoContract.PomoEntry.COLUMN_NAME_ID + "=?",
                new String[] { object.getId() });
        // Delete in backend
        Intent intent = new Intent(context, PostService.class);
        intent.putExtra(ExtraName.SERVICE_TYPE, PostService.DELETE_POMO);
        intent.putExtra(ExtraName.SERVICE_OBJECT, object);
        context.startService(intent);
    }

    /**
     * Selects a list of Pomodoro by daily ID.
     *
     * @param dailyId the daily ID.
     * @return the list of Pomodoro.
     * @since 1.0.0
     */
    public ArrayList<Pomodoro> selectByDaily(String dailyId) {
        ArrayList<Pomodoro> completed = new ArrayList<>();
        Cursor cursor = readDb.query(
                PomoContract.PomoEntry.TABLE_NAME,
                new String[] {
                        PomoContract.PomoEntry.COLUMN_NAME_ID,
                        PomoContract.PomoEntry.COLUMN_NAME_TAG_ID,
                        PomoContract.PomoEntry.COLUMN_NAME_MEMO,
                        PomoContract.PomoEntry.COLUMN_NAME_TIME,
                        PomoContract.PomoEntry.COLUMN_NAME_USER_ID
                },
                PomoContract.PomoEntry.COLUMN_NAME_DAILY_ID + "=?",
                new String[] { dailyId },
                null,
                null,
                PomoContract.PomoEntry.COLUMN_NAME_ID + " ASC");
        if (cursor.moveToFirst()) {
            do {
                completed.add(new Pomodoro(
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                PomoContract.PomoEntry.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                PomoContract.PomoEntry.COLUMN_NAME_TAG_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                PomoContract.PomoEntry.COLUMN_NAME_MEMO)),
                        dailyId,
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                PomoContract.PomoEntry.COLUMN_NAME_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                PomoContract.PomoEntry.COLUMN_NAME_USER_ID))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return completed;
    }

    /**
     * Selects a list of Pomodoro by tag ID.
     *
     * @param tagId the tag ID.
     * @return the list of Pomodoro.
     * @since 1.0.0
     */
    public ArrayList<Pomodoro> selectByTag(String tagId) {
        ArrayList<Pomodoro> completed = new ArrayList<>();
        Cursor cursor = readDb.query(
                PomoContract.PomoEntry.TABLE_NAME,
                new String[] {
                        PomoContract.PomoEntry.COLUMN_NAME_ID,
                        PomoContract.PomoEntry.COLUMN_NAME_MEMO,
                        PomoContract.PomoEntry.COLUMN_NAME_DAILY_ID,
                        PomoContract.PomoEntry.COLUMN_NAME_TIME,
                        PomoContract.PomoEntry.COLUMN_NAME_USER_ID
                },
                PomoContract.PomoEntry.COLUMN_NAME_TAG_ID + "=?",
                new String[] { tagId },
                null,
                null,
                PomoContract.PomoEntry.COLUMN_NAME_ID + " ASC");
        if (cursor.moveToFirst()) {
            do {
                completed.add(new Pomodoro(
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                PomoContract.PomoEntry.COLUMN_NAME_ID)),
                        tagId,
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                PomoContract.PomoEntry.COLUMN_NAME_MEMO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                PomoContract.PomoEntry.COLUMN_NAME_DAILY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                PomoContract.PomoEntry.COLUMN_NAME_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                PomoContract.PomoEntry.COLUMN_NAME_USER_ID))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return completed;
    }

}
