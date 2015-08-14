package com.rooftrellen.pomoplan.db;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.backend.PostService;
import com.rooftrellen.pomoplan.model.PomoDaily;

/**
 * TableDaily is a class for controlling the CRUD of PomoDaily table.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class TableDaily implements TableInterface<PomoDaily> {

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
    public TableDaily(String androidId, Context context, SQLiteDatabase readDb, SQLiteDatabase writeDb) {
        this.androidId = androidId;
        this.context = context;
        this.readDb = readDb;
        this.writeDb = writeDb;
    }

    /**
     * Inserts a PomoDaily.
     *
     * @param object the PomoDaily.
     * @param backend the flag of backend.
     * @since 1.0.0
     */
    @Override
    public void insert(PomoDaily object, boolean backend) {
        ContentValues values = new ContentValues();
        String id;
        if (backend) {
            id = androidId + Calendar.getInstance().getTimeInMillis();
        } else {
            id = object.getId();
        }
        values.put(PomoContract.DailyEntry.COLUMN_NAME_ID, id);
        values.put(PomoContract.DailyEntry.COLUMN_NAME_DATE, object.getDate());
        values.put(PomoContract.DailyEntry.COLUMN_NAME_PLAN, object.getPlan());
        values.put(PomoContract.DailyEntry.COLUMN_NAME_USER_ID, object.getUserId());
        writeDb.insert(PomoContract.DailyEntry.TABLE_NAME, null, values);
        if (backend) {
            object.setId(id);
            // Insert in backend
            Intent intent = new Intent(context, PostService.class);
            intent.putExtra(ExtraName.SERVICE_TYPE, PostService.INSERT_DAILY);
            intent.putExtra(ExtraName.SERVICE_OBJECT, object);
            context.startService(intent);
        }
    }

    /**
     * Updates a PomoDaily.
     *
     * @param object the PomoDaily.
     * @since 1.0.0
     */
    @Override
    public void update(PomoDaily object) {
        ContentValues values = new ContentValues();
        values.put(PomoContract.DailyEntry.COLUMN_NAME_DATE, object.getDate());
        values.put(PomoContract.DailyEntry.COLUMN_NAME_PLAN, object.getPlan());
        values.put(PomoContract.DailyEntry.COLUMN_NAME_USER_ID, object.getUserId());
        writeDb.update(
                PomoContract.DailyEntry.TABLE_NAME,
                values,
                PomoContract.DailyEntry.COLUMN_NAME_ID + "=?",
                new String[] { object.getId() });
        // Update in backend
        Intent intent = new Intent(context, PostService.class);
        intent.putExtra(ExtraName.SERVICE_TYPE, PostService.UPDATE_DAILY);
        intent.putExtra(ExtraName.SERVICE_OBJECT, object);
        context.startService(intent);
    }

    /**
     * Deletes a PomoDaily.
     *
     * @param object the PomoDaily.
     * @since 1.0.0
     */
    @Override
    public void delete(PomoDaily object) {
        writeDb.delete(
                PomoContract.DailyEntry.TABLE_NAME,
                PomoContract.DailyEntry.COLUMN_NAME_ID + "=?",
                new String[] { object.getId() });
        // Delete in backend
        Intent intent = new Intent(context, PostService.class);
        intent.putExtra(ExtraName.SERVICE_TYPE, PostService.DELETE_DAILY);
        intent.putExtra(ExtraName.SERVICE_OBJECT, object);
        context.startService(intent);
    }

    /**
     * Selects a PomoDaily by date and user ID.
     *
     * @param date the date.
     * @param userId the user ID.
     * @return the PomoDaily.
     * @since 1.0.0
     */
    public PomoDaily selectByDate(String date, String userId) {
        PomoDaily daily = null;
        Cursor cursor = readDb.query(
                PomoContract.DailyEntry.TABLE_NAME,
                new String[] {
                        PomoContract.DailyEntry.COLUMN_NAME_ID,
                        PomoContract.DailyEntry.COLUMN_NAME_PLAN
                },
                PomoContract.DailyEntry.COLUMN_NAME_DATE + "=? AND "
                        + PomoContract.DailyEntry.COLUMN_NAME_USER_ID + "=?",
                new String[] { date, userId },
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            daily = new PomoDaily(cursor.getString(cursor.getColumnIndexOrThrow(
                    PomoContract.DailyEntry.COLUMN_NAME_ID)),
                    date,
                    cursor.getInt(cursor.getColumnIndexOrThrow(
                            PomoContract.DailyEntry.COLUMN_NAME_PLAN)),
                    userId
                    );
        }
        cursor.close();
        return daily;
    }

    /**
     * Selects a PomoDaily by ID.
     *
     * @param id the ID.
     * @return the PomoDaily.
     * @since 1.0.0
     */
    public PomoDaily selectById(String id) {
        PomoDaily daily = null;
        Cursor cursor = readDb.query(
                PomoContract.DailyEntry.TABLE_NAME,
                new String[] {
                        PomoContract.DailyEntry.COLUMN_NAME_DATE,
                        PomoContract.DailyEntry.COLUMN_NAME_PLAN,
                        PomoContract.DailyEntry.COLUMN_NAME_USER_ID
                },
                PomoContract.DailyEntry.COLUMN_NAME_ID + "=?",
                new String[] { id },
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            daily = new PomoDaily(id,
                    cursor.getString(cursor.getColumnIndexOrThrow(
                            PomoContract.DailyEntry.COLUMN_NAME_DATE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(
                            PomoContract.DailyEntry.COLUMN_NAME_PLAN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(
                            PomoContract.DailyEntry.COLUMN_NAME_USER_ID))
            );
        }
        cursor.close();
        return daily;
    }

}
