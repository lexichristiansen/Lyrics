package me.lexichristiansen.lyrics.database;

import android.provider.BaseColumns;

/**
 * Database
 */
public final class DatabaseContract {

    // Should not be instantiated
    private DatabaseContract() {}

    public class Lyrics implements BaseColumns {
        public static final String TABLE_NAME = "lyrics";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_LYRICS_ENGLISH = "lyrics_english";
        public static final String COLUMN_NAME_LYRICS_JAPANESE = "lyrics_japanese";

        public static final String SQL_CREATE_TABLE_LYRICS = "CREATE TABLE " + Lyrics.TABLE_NAME + " (" +
                Lyrics._ID + " INTEGER PRIMARY KEY," +
                Lyrics.COLUMN_NAME_TITLE + " TEXT," +
                Lyrics.COLUMN_NAME_LYRICS_ENGLISH + " TEXT," +
                Lyrics.COLUMN_NAME_LYRICS_JAPANESE + " TEXT)";

        public static final String SQL_DELETE_TABLE_LYRICS = "DROP TABLE IF EXISTS " + Lyrics.TABLE_NAME;
    }
}
