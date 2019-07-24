package com.example.hebam.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
/**
 * Database helper for NoteKeeper app. Manages database creation and version management.
 */
public class NoteKeeperOpenHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = NoteKeeperOpenHelper.class.getSimpleName();

    //the name of the file containing the database
    public static final String DATABASE_NAME = "NoteKeeper.db";
    //the version of the database. If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link NoteKeeperOpenHelper}.
     *
     * @param context of the app
     */
    public NoteKeeperOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteKeeperDatabaseContract.CourseInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(NoteKeeperDatabaseContract.NoteInfoEntry.SQL_CREATE_TABLE);

        //Insert default values in the tables
        DatabaseDataWorker worker = new DatabaseDataWorker(db);
        worker.insertCourses();
        worker.insertSampleNotes();

        int i = 5;
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
