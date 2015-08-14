package com.rooftrellen.pomoplan.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.provider.Settings.Secure;

import com.rooftrellen.pomoplan.backend.GetService;
import com.rooftrellen.pomoplan.exception.PomoException;
import com.rooftrellen.pomoplan.model.PomoDaily;
import com.rooftrellen.pomoplan.model.PomoTag;
import com.rooftrellen.pomoplan.model.PomoUser;
import com.rooftrellen.pomoplan.model.Pomodoro;

/**
 * PomoDbHelper is an SQLiteOpenHelper for connecting database.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class PomoDbHelper extends SQLiteOpenHelper {

    /**
     * The readable DB.
     *
     * @since 1.0.0
     */
    private SQLiteDatabase readDb;

    /**
     * The writable DB.
     *
     * @since 1.0.0
     */
    private SQLiteDatabase writeDb;

    /**
     * The database table for PomoUser.
     *
     * @since 1.0.0
     */
    private TableInterface<PomoUser> tableUser;

    /**
     * The database table for PomoDaily.
     *
     * @since 1.0.0
     */
    private TableInterface<PomoDaily> tableDaily;

    /**
     * The database table for PomoTag.
     *
     * @since 1.0.0
     */
    private TableInterface<PomoTag> tableTag;

    /**
     * The database table for Pomodoro.
     *
     * @since 1.0.0
     */
    private TableInterface<Pomodoro> tablePomo;

    /**
     * The singleton instance.
     *
     * @since 1.0.0
     */
    private static PomoDbHelper dbHelper = null;

    /**
     * The database version.
     *
     * @since 1.0.0
     */
    private static final int DB_VERSION = 1;

    /**
     * The database file name.
     *
     * @since 1.0.0
     */
    private static final String DB_NAME = "pomoplan.db";

    /**
     * Initializes with context.
     *
     * @param context the application context.
     * @since 1.0.0
     */
    private PomoDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // Create database
        try {
            readDb = getReadableDatabase();
            if (readDb == null) {
                throw new PomoException(PomoException.ErrorCode.INVALID_DB, "Readable database is null.");
            }
            writeDb = getWritableDatabase();
            if (writeDb == null) {
                throw new PomoException(PomoException.ErrorCode.INVALID_DB, "Writable database is null.");
            }
        } catch (PomoException e) {
            Log.e(e.getErrorTag(), e.toString());
        }
        // Create tables
        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        tableUser = new TableUser(androidId, context, readDb, writeDb);
        tableDaily = new TableDaily(androidId, context, readDb, writeDb);
        tableTag = new TableTag(androidId, context, readDb, writeDb);
        tablePomo = new TablePomo(androidId, context, readDb, writeDb);
    }

    /**
     * Gets the singleton instance.
     *
     * @param context the application context.
     * @return the singleton instance.
     * @since 1.0.0
     */
    public static PomoDbHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new PomoDbHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    /**
     * Reopens db when log out.
     *
     * @since 1.0.0
     */
    public void reopen() {
        onOpen(this.getWritableDatabase());
    }

    /**
     * Resets db when open.
     *
     * @param db the database.
     * @since 1.0.0
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL(PomoContract.PomoEntry.SQL_DELETE_ENTRIES);
        db.execSQL(PomoContract.DailyEntry.SQL_DELETE_ENTRIES);
        db.execSQL(PomoContract.TagEntry.SQL_DELETE_ENTRIES);
        db.execSQL(PomoContract.UserEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Creates the database if not exist.
     *
     * @param db the database.
     * @since 1.0.0
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PomoContract.PomoEntry.SQL_CREATE_ENTRIES);
        db.execSQL(PomoContract.DailyEntry.SQL_CREATE_ENTRIES);
        db.execSQL(PomoContract.TagEntry.SQL_CREATE_ENTRIES);
        db.execSQL(PomoContract.UserEntry.SQL_CREATE_ENTRIES);
    }

    /**
     * Updates the database if there is a new version.
     *
     * @param db the database.
     * @param oldVersion the old version.
     * @param newVersion the new version.
     * @since 1.0.0
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PomoContract.PomoEntry.SQL_DELETE_ENTRIES);
        db.execSQL(PomoContract.DailyEntry.SQL_DELETE_ENTRIES);
        db.execSQL(PomoContract.TagEntry.SQL_DELETE_ENTRIES);
        db.execSQL(PomoContract.UserEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Inserts a PomoUser.
     *
     * @param object the PomoUser.
     * @param backend the flag of backend.
     * @since 1.0.0
     */
    public void insertUser(PomoUser object, boolean backend) {
        tableUser.insert(object, backend);
    }

    /**
     * Inserts a PomoDaily.
     *
     * @param object the PomoDaily.
     * @param backend the flag of backend.
     * @since 1.0.0
     */
    public void insertDaily(PomoDaily object, boolean backend) {
        tableDaily.insert(object, backend);
    }

    /**
     * Inserts a PomoTag.
     *
     * @param object the PomoTag.
     * @param backend the flag of backend.
     * @since 1.0.0
     */
    public void insertTag(PomoTag object, boolean backend) {
        tableTag.insert(object, backend);
    }

    /**
     * Inserts a Pomodoro.
     *
     * @param object the Pomodoro.
     * @param backend the flag of backend.
     * @since 1.0.0
     */
    public void insertPomo(Pomodoro object, boolean backend) {
        tablePomo.insert(object, backend);
    }

    /**
     * Updates a PomoUser.
     *
     * @param object the PomoUser.
     * @since 1.0.0
     */
    public void updateUser(PomoUser object) {
        tableUser.update(object);
    }

    /**
     * Updates a PomoDaily.
     *
     * @param object the PomoDaily.
     * @since 1.0.0
     */
    public void updateDaily(PomoDaily object) {
        tableDaily.update(object);
    }

    /**
     * Updates a PomoTag.
     *
     * @param object the PomoTag.
     * @since 1.0.0
     */
    public void updateTag(PomoTag object) {
        tableTag.update(object);
    }

    /**
     * Selects PomoDaily by date.
     *
     * @param date the date.
     * @param userId the user ID.
     * @return the PomoDaily.
     * @since 1.0.0
     */
    public PomoDaily selectDailyByDate(String date, String userId) {
        PomoDaily daily = ((TableDaily) tableDaily).selectByDate(date, userId);
        if (daily != null) {
            daily.setCompleted(((TablePomo) tablePomo).selectByDaily(daily.getId()));
        }
        return daily;
    }

    /**
     * Selects daily date by daily ID.
     *
     * @param id the daily ID.
     * @return the date.
     * @since 1.0.0
     */
    public String selectDailyDateById(String id) {
        return ((TableDaily) tableDaily).selectById(id).getDate();
    }

    /**
     * Selects PomoTag by name.
     *
     * @param name the name.
     * @param userId the user ID.
     * @return the PomoTag.
     * @since 1.0.0
     */
    public PomoTag selectTagByName(String name, String userId) {
        PomoTag tag = ((TableTag) tableTag).selectByName(name, userId);
        if (tag != null) {
            tag.setCompleted(((TablePomo) tablePomo).selectByTag(tag.getId()));
        }
        return tag;
    }

    /**
     * Selects tag name by tag ID.
     *
     * @param id the tag ID.
     * @return the tag name.
     * @since 1.0.0
     */
    public String selectTagNameById(String id) {
        return ((TableTag) tableTag).selectById(id).getName();
    }

    /**
     * Selects all PomoTag by user ID.
     *
     * @param userId the user ID.
     * @return the list of PomoTag.
     * @since 1.0.0
     */
    public ArrayList<PomoTag> selectTagsByUserId(String userId) {
        ArrayList<PomoTag> tags = ((TableTag) tableTag).selectByUserId(userId);
        for (PomoTag tag : tags) {
            tag.setCompleted(((TablePomo) tablePomo).selectByTag(tag.getId()));
        }
        return tags;
    }

    /**
     * Gets the existence of PomoUser by name.
     *
     * @param name the user name.
     * @return the existence.
     * @since 1.0.0
     */
    public boolean selectUserByName(String name) {
        GetService service = new GetService();
        return service.getUser(name);
    }

    /**
     * Selects PomoUser by name and password.
     *
     * @param name the name.
     * @param password the password.
     * @return the PomoUser.
     * @since 1.0.0
     */
    public PomoUser selectUserByNamePassword(String name, String password) {
        GetService service = new GetService();
        PomoUser user = service.getUser(name, password);
        if (user != null) {
            insertUser(user, false);
            String userId = user.getId();
            ArrayList<PomoDaily> dailies = service.getDaily(userId);
            for (PomoDaily d : dailies) {
                insertDaily(d, false);
            }
            ArrayList<PomoTag> tags = service.getTag(userId);
            for (PomoTag t : tags) {
                insertTag(t, false);
            }
            ArrayList<Pomodoro> pomos = service.getPomo(userId);
            for (Pomodoro p : pomos) {
                insertPomo(p, false);
            }
        }
        return user;
    }

}
