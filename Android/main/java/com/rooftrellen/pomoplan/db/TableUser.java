package com.rooftrellen.pomoplan.db;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.backend.PostService;
import com.rooftrellen.pomoplan.model.PomoUser;

/**
 * TableUser is a class for controlling the CRUD of PomoUser table.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class TableUser implements TableInterface<PomoUser> {

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
    public TableUser(String androidId, Context context, SQLiteDatabase readDb,
                     SQLiteDatabase writeDb) {
        this.androidId = androidId;
        this.context = context;
        this.readDb = readDb;
        this.writeDb = writeDb;
    }

    /**
     * Inserts a PomoUser.
     *
     * @param object the PomoUser.
     * @param backend the flag of backend.
     * @since 1.0.0
     */
    @Override
    public void insert(PomoUser object, boolean backend) {
        ContentValues values = new ContentValues();
        String id;
        if (backend) {
            id = androidId + Calendar.getInstance().getTimeInMillis();
        } else {
            id = object.getId();
        }
        values.put(PomoContract.UserEntry.COLUMN_NAME_ID, id);
        values.put(PomoContract.UserEntry.COLUMN_NAME_NAME, object.getName());
        values.put(PomoContract.UserEntry.COLUMN_NAME_PASSWORD, object.getPassword());
        values.put(PomoContract.UserEntry.COLUMN_NAME_WIFI_OFF, object.getWifiOff());
        values.put(PomoContract.UserEntry.COLUMN_NAME_BLUETOOTH_OFF, object.getBluetoothOff());
        values.put(PomoContract.UserEntry.COLUMN_NAME_POMO_DURATION, object.getPomoDuration());
        values.put(PomoContract.UserEntry.COLUMN_NAME_BREAK_DURATION, object.getBreakDuration());
        writeDb.insert(PomoContract.UserEntry.TABLE_NAME, null, values);
        if (backend) {
            object.setId(id);
            // Insert in backend
            Intent intent = new Intent(context, PostService.class);
            intent.putExtra(ExtraName.SERVICE_TYPE, PostService.INSERT_USER);
            intent.putExtra(ExtraName.SERVICE_OBJECT, object);
            context.startService(intent);
        }
    }

    /**
     * Updates a PomoUser.
     *
     * @param object the PomoUser.
     * @since 1.0.0
     */
    @Override
    public void update(PomoUser object) {
        ContentValues values = new ContentValues();
        values.put(PomoContract.UserEntry.COLUMN_NAME_NAME, object.getName());
        values.put(PomoContract.UserEntry.COLUMN_NAME_PASSWORD, object.getPassword());
        values.put(PomoContract.UserEntry.COLUMN_NAME_WIFI_OFF, object.getWifiOff());
        values.put(PomoContract.UserEntry.COLUMN_NAME_BLUETOOTH_OFF, object.getBluetoothOff());
        values.put(PomoContract.UserEntry.COLUMN_NAME_POMO_DURATION, object.getPomoDuration());
        values.put(PomoContract.UserEntry.COLUMN_NAME_BREAK_DURATION, object.getBreakDuration());
        writeDb.update(
            PomoContract.UserEntry.TABLE_NAME,
            values,
            PomoContract.UserEntry.COLUMN_NAME_ID + "=?",
            new String[] { object.getId() });
        // Update in backend
        Intent intent = new Intent(context, PostService.class);
        intent.putExtra(ExtraName.SERVICE_TYPE, PostService.UPDATE_USER);
        intent.putExtra(ExtraName.SERVICE_OBJECT, object);
        context.startService(intent);
    }

    /**
     * Deletes a PomoUser.
     *
     * @param object the PomoUser.
     * @since 1.0.0
     */
    @Override
    public void delete(PomoUser object) {
        writeDb.delete(
                PomoContract.UserEntry.TABLE_NAME,
                PomoContract.UserEntry.COLUMN_NAME_ID + "=?",
                new String[] { object.getId() });
        // Delete in backend
        Intent intent = new Intent(context, PostService.class);
        intent.putExtra(ExtraName.SERVICE_TYPE, PostService.DELETE_USER);
        intent.putExtra(ExtraName.SERVICE_OBJECT, object);
        context.startService(intent);
    }

}
