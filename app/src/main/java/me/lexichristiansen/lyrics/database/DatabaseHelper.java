package me.lexichristiansen.lyrics.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Creates and upgrades the database.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LyricsApp.db";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, null);
        this.context = context;
    }

    private static String SHARED_PREFS = "DatabaseHelper";
    private static String NEW_ID = "NewId";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.Lyrics.SQL_CREATE_TABLE_LYRICS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Lyrics.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    private int getNewId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(NEW_ID)) {
            int returnMe = sharedPreferences.getInt(NEW_ID, 0);
            sharedPreferences.edit().putInt(NEW_ID, returnMe + 1).apply();
            return returnMe;
        } else {
            sharedPreferences.edit().putInt(NEW_ID, 0).apply();
            return 0;
        }
    }

    public void addLyric(Lyric lyric) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Lyrics._ID,  getNewId());
        values.put(DatabaseContract.Lyrics.COLUMN_NAME_TITLE, lyric.getTitle());
        values.put(DatabaseContract.Lyrics.COLUMN_NAME_LYRICS_ENGLISH, lyric.getLyricsEnglish());
        values.put(DatabaseContract.Lyrics.COLUMN_NAME_LYRICS_JAPANESE, lyric.getLyricsJapanese());
        db.insert(DatabaseContract.Lyrics.TABLE_NAME, null, values);
    }

    public Lyric getLyric(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] stringArray = new String[] {
                DatabaseContract.Lyrics._ID,
                DatabaseContract.Lyrics.COLUMN_NAME_TITLE,
                DatabaseContract.Lyrics.COLUMN_NAME_LYRICS_ENGLISH,
                DatabaseContract.Lyrics.COLUMN_NAME_LYRICS_JAPANESE
        };

        String[] stringArray2 = new String[] {
                String.valueOf(id)
        };

        Cursor cursor = db.query(DatabaseContract.Lyrics.TABLE_NAME, stringArray,
                DatabaseContract.Lyrics._ID + "=?", stringArray2, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Lyric lyric = new Lyric(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        return lyric;
    }

    public Lyric[] getAllLyrics() {
        // Select All query
        String selectQuery = "SELECT * FROM " + DatabaseContract.Lyrics.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        Lyric[] lyricList = new Lyric[getLyricsCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Lyric lyric = new Lyric();
                lyric.setID(Integer.parseInt(cursor.getString(0)));
                lyric.setTitle(cursor.getString(1));
                lyric.setLyricsEnglish(cursor.getString(2));
                lyric.setLyricsJapanese(cursor.getString(3));
                lyricList[i] = lyric;
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lyricList;
    }

    public int getLyricsCount() {
        String countQuery = "SELECT  * FROM " + DatabaseContract.Lyrics.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateLyric(Lyric lyric) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Lyrics.COLUMN_NAME_TITLE, lyric.getTitle());
        values.put(DatabaseContract.Lyrics.COLUMN_NAME_LYRICS_ENGLISH, lyric.getLyricsEnglish());
        values.put(DatabaseContract.Lyrics.COLUMN_NAME_LYRICS_JAPANESE, lyric.getLyricsJapanese());

        return db.update(DatabaseContract.Lyrics.TABLE_NAME, values, DatabaseContract.Lyrics._ID + " = ?",
                new String[] { String.valueOf(lyric.getID()) });
    }

    public void deleteLyric(Lyric contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseContract.Lyrics.TABLE_NAME, DatabaseContract.Lyrics._ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

}
