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
import com.rooftrellen.pomoplan.model.PomoTag;

/**
 * TableTag is a class for controlling the CRUD of PomoTag table.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class TableTag implements TableInterface<PomoTag> {

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
    public TableTag(String androidId, Context context, SQLiteDatabase readDb, SQLiteDatabase writeDb) {
        this.androidId = androidId;
        this.context = context;
        this.readDb = readDb;
        this.writeDb = writeDb;
    }

    /**
     * Inserts a PomoTag.
     *
     * @param object the PomoTag.
     * @param backend the flag of backend.
     * @since 1.0.0
     */
    @Override
    public void insert(PomoTag object, boolean backend) {
        ContentValues values = new ContentValues();
        String id;
        if (backend) {
            id = androidId + Calendar.getInstance().getTimeInMillis();
        } else {
            id = object.getId();
        }
        values.put(PomoContract.TagEntry.COLUMN_NAME_ID, id);
        values.put(PomoContract.TagEntry.COLUMN_NAME_NAME, object.getName());
        values.put(PomoContract.TagEntry.COLUMN_NAME_PLAN, object.getPlan());
        values.put(PomoContract.TagEntry.COLUMN_NAME_USER_ID, object.getUserId());
        writeDb.insert(PomoContract.TagEntry.TABLE_NAME, null, values);
        if (backend) {
            object.setId(id);
            // Insert in backend
            Intent intent = new Intent(context, PostService.class);
            intent.putExtra(ExtraName.SERVICE_TYPE, PostService.INSERT_TAG);
            intent.putExtra(ExtraName.SERVICE_OBJECT, object);
            context.startService(intent);
        }
    }

    /**
     * Updates a PomoTag.
     *
     * @param object the PomoTag.
     * @since 1.0.0
     */
    @Override
    public void update(PomoTag object) {
        ContentValues values = new ContentValues();
        values.put(PomoContract.TagEntry.COLUMN_NAME_NAME, object.getName());
        values.put(PomoContract.TagEntry.COLUMN_NAME_PLAN, object.getPlan());
        values.put(PomoContract.TagEntry.COLUMN_NAME_USER_ID, object.getUserId());
        writeDb.update(
                PomoContract.TagEntry.TABLE_NAME,
                values,
                PomoContract.TagEntry.COLUMN_NAME_ID + "=?",
                new String[]{object.getId()});
        // Update in backend
        Intent intent = new Intent(context, PostService.class);
        intent.putExtra(ExtraName.SERVICE_TYPE, PostService.UPDATE_TAG);
        intent.putExtra(ExtraName.SERVICE_OBJECT, object);
        context.startService(intent);
    }

    /**
     * Deletes a PomoTag.
     *
     * @param object the PomoTag.
     * @since 1.0.0
     */
    @Override
    public void delete(PomoTag object) {
        writeDb.delete(
                PomoContract.TagEntry.TABLE_NAME,
                PomoContract.TagEntry.COLUMN_NAME_ID + "=?",
                new String[] { object.getId() });
        // Delete in backend
        Intent intent = new Intent(context, PostService.class);
        intent.putExtra(ExtraName.SERVICE_TYPE, PostService.DELETE_TAG);
        intent.putExtra(ExtraName.SERVICE_OBJECT, object);
        context.startService(intent);
    }

    /**
     * Selects a PomoTag by name and user ID.
     *
     * @param name the name.
     * @param userId the user ID.
     * @return the PomoTag.
     * @since 1.0.0
     */
    public PomoTag selectByName(String name, String userId) {
        PomoTag tag = null;
        Cursor cursor = readDb.query(
                PomoContract.TagEntry.TABLE_NAME,
                new String[] {
                        PomoContract.TagEntry.COLUMN_NAME_ID,
                        PomoContract.TagEntry.COLUMN_NAME_PLAN
                },
                PomoContract.TagEntry.COLUMN_NAME_NAME + "=? AND "
                        + PomoContract.TagEntry.COLUMN_NAME_USER_ID + "=?",
                new String[] { name, userId },
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            tag = new PomoTag(cursor.getString(cursor.getColumnIndexOrThrow(
                    PomoContract.TagEntry.COLUMN_NAME_ID)),
                    name,
                    cursor.getInt(cursor.getColumnIndexOrThrow(
                            PomoContract.TagEntry.COLUMN_NAME_PLAN)),
                    userId);
        }
        cursor.close();
        return tag;
    }

    /**
     * Selects a PomoTag by ID.
     *
     * @param id the ID.
     * @return the PomoTag.
     * @since 1.0.0
     */
    public PomoTag selectById(String id) {
        PomoTag tag = null;
        Cursor cursor = readDb.query(
                PomoContract.TagEntry.TABLE_NAME,
                new String[] {
                        PomoContract.TagEntry.COLUMN_NAME_NAME,
                        PomoContract.TagEntry.COLUMN_NAME_PLAN,
                        PomoContract.TagEntry.COLUMN_NAME_USER_ID
                },
                PomoContract.TagEntry.COLUMN_NAME_ID + "=?",
                new String[] { id },
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            tag = new PomoTag(id,
                    cursor.getString(cursor.getColumnIndexOrThrow(
                            PomoContract.TagEntry.COLUMN_NAME_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(
                            PomoContract.TagEntry.COLUMN_NAME_PLAN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(
                            PomoContract.TagEntry.COLUMN_NAME_USER_ID))
            );
        }
        cursor.close();
        return tag;
    }

    /**
     * Selects all PomoTag by user ID.
     *
     * @param userId the user ID.
     * @return the list of PomoTag.
     * @since 1.0.0
     */
    public ArrayList<PomoTag> selectByUserId(String userId) {
        ArrayList<PomoTag> tags = new ArrayList<>();
        Cursor cursor = readDb.query(
                PomoContract.TagEntry.TABLE_NAME,
                new String[] {
                        PomoContract.TagEntry.COLUMN_NAME_ID,
                        PomoContract.TagEntry.COLUMN_NAME_NAME,
                        PomoContract.TagEntry.COLUMN_NAME_PLAN
                },
                PomoContract.TagEntry.COLUMN_NAME_USER_ID + "=?",
                new String[] { userId },
                null,
                null,
                PomoContract.TagEntry.COLUMN_NAME_ID + " ASC");
        if (cursor.moveToFirst()) {
            do {
                tags.add(new PomoTag(
                        cursor.getString(cursor.getColumnIndexOrThrow(PomoContract.TagEntry.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(PomoContract.TagEntry.COLUMN_NAME_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PomoContract.TagEntry.COLUMN_NAME_PLAN)),
                        userId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tags;
    }

}
