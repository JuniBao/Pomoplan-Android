package com.rooftrellen.pomoplan.db;

/**
 * PomoContract is a class for storing database contract of POMOPLAN.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public final class PomoContract {

    /**
     * The integer type.
     *
     * @since 1.0.0
     */
    private final static String INTEGER_TYPE = " INTEGER";

    /**
     * The text type.
     *
     * @since 1.0.0
     */
    private final static String TEXT_TYPE = " TEXT";

    /**
     * The primary key.
     *
     * @since 1.0.0
     */
    private final static String PRIMARY_KEY = " PRIMARY KEY";

    /**
     * The foreign key.
     *
     * @since 1.0.0
     */
    private final static String FOREIGN_KEY = "FOREIGN KEY";

    /**
     * The references.
     *
     * @since 1.0.0
     */
    private final static String REFERENCES = " REFERENCES ";

    /**
     * The comma separator.
     *
     * @since 1.0.0
     */
    private final static String COMMA_SEP = ",";

    /**
     * Prevents from instantiating.
     *
     * @since 1.0.0
     */
    private PomoContract() {}

    /**
     * PomoEntry is a class for describing the schema of pomodoro table.
     *
     * @since 1.0.0
     */
    public static abstract class PomoEntry {

        /**
         * The table name.
         *
         * @since 1.0.0
         */
        public final static String TABLE_NAME = "pomodoro";

        /**
         * The column name of ID.
         *
         * @since 1.0.0
         */
        public final static String COLUMN_NAME_ID = "id";

        /**
         * The column name of tag ID.
         *
         * @since 1.0.0
         */
        public final static String COLUMN_NAME_TAG_ID = "tag_id";

        /**
         * The column name of memo.
         *
         * @since 1.0.0
         */
        public final static String COLUMN_NAME_MEMO = "memo";

        /**
         * The column name of daily ID.
         *
         * @since 1.0.0
         */
        public final static String COLUMN_NAME_DAILY_ID = "daily_id";

        /**
         * The column name of time.
         *
         * @since 1.0.0
         */
        public final static String COLUMN_NAME_TIME = "time";

        /**
         * The column name of user ID.
         *
         * @since 1.0.0
         */
        public final static String COLUMN_NAME_USER_ID = "user_id";

        /**
         * The SQL for creating table.
         *
         * @since 1.0.0
         */
        public final static String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                        COLUMN_NAME_TAG_ID + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_MEMO + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_DAILY_ID + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_USER_ID + TEXT_TYPE + COMMA_SEP +
                        FOREIGN_KEY + "(" + COLUMN_NAME_TAG_ID + ")" + REFERENCES +
                        TagEntry.TABLE_NAME + "(" + TagEntry.COLUMN_NAME_ID + ")" + COMMA_SEP +
                        FOREIGN_KEY + "(" + COLUMN_NAME_DAILY_ID + ")" + REFERENCES +
                        DailyEntry.TABLE_NAME + "(" + DailyEntry.COLUMN_NAME_ID + ")" + COMMA_SEP +
                        FOREIGN_KEY + "(" + COLUMN_NAME_USER_ID + ")" + REFERENCES +
                        UserEntry.TABLE_NAME + "(" + UserEntry.COLUMN_NAME_ID + ")" +
                        " )";

        /**
         * The SQL for dropping table.
         *
         * @since 1.0.0
         */
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    /**
     * DailyEntry is a class for describing the schema of daily table.
     *
     * @since 1.0.0
     */
    public static abstract class DailyEntry {

        /**
         * The table name.
         *
         * @since 1.0.0
         */
        public static final String TABLE_NAME = "daily";

        /**
         * The column name of ID.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_ID = "id";

        /**
         * The column name of date.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_DATE = "date";

        /**
         * The column name of plan.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_PLAN = "plan";

        /**
         * The column name of user ID.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_USER_ID = "user_id";

        /**
         * The SQL for creating table.
         *
         * @since 1.0.0
         */
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                        COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_PLAN + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_USER_ID + TEXT_TYPE + COMMA_SEP +
                        FOREIGN_KEY + "(" + COLUMN_NAME_USER_ID + ")" + REFERENCES +
                        UserEntry.TABLE_NAME + "(" + UserEntry.COLUMN_NAME_ID + ")" +
                        " )";

        /**
         * The SQL for dropping table.
         *
         * @since 1.0.0
         */
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    /**
     * TagEntry is a class for describing the schema of tag table.
     *
     * @since 1.0.0
     */
    public static abstract class TagEntry {

        /**
         * The table name.
         *
         * @since 1.0.0
         */
        public static final String TABLE_NAME = "tag";

        /**
         * The column name of ID.
         *
         * @since 1.0.0
         */
        public final static String COLUMN_NAME_ID = "id";

        /**
         * The column name of name.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_NAME = "name";

        /**
         * The column name of plan.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_PLAN = "plan";

        /**
         * The column name of user ID.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_USER_ID = "user_id";

        /**
         * The SQL for creating table.
         *
         * @since 1.0.0
         */
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                        COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_PLAN + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_USER_ID + TEXT_TYPE + COMMA_SEP +
                        FOREIGN_KEY + "(" + COLUMN_NAME_USER_ID + ")" + REFERENCES +
                        UserEntry.TABLE_NAME + "(" + UserEntry.COLUMN_NAME_ID + ")" +
                        " )";

        /**
         * The SQL for dropping table.
         *
         * @since 1.0.0
         */
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    /**
     * UserEntry is a class for describing the schema of user table.
     *
     * @since 1.0.0
     */
    public static abstract class UserEntry {

        /**
         * The table name.
         *
         * @since 1.0.0
         */
        public static final String TABLE_NAME = "user";

        /**
         * The column name of ID.
         *
         * @since 1.0.0
         */
        public final static String COLUMN_NAME_ID = "id";

        /**
         * The column name of name.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_NAME = "name";

        /**
         * The column name of password.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_PASSWORD = "password";

        /**
         * The column name of WiFi option.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_WIFI_OFF = "wifi_off";

        /**
         * The column name of Bluetooth option.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_BLUETOOTH_OFF = "bluetooth_off";

        /**
         * The column name of Pomodoro duration.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_POMO_DURATION = "pomo_duration";

        /**
         * The column name of break duration.
         *
         * @since 1.0.0
         */
        public static final String COLUMN_NAME_BREAK_DURATION = "break_duration";

        /**
         * The SQL for creating table.
         *
         * @since 1.0.0
         */
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                        COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_WIFI_OFF + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_BLUETOOTH_OFF + INTEGER_TYPE + COMMA_SEP+
                        COLUMN_NAME_POMO_DURATION + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_BREAK_DURATION + INTEGER_TYPE +
                " )";

        /**
         * The SQL for dropping table.
         *
         * @since 1.0.0
         */
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
