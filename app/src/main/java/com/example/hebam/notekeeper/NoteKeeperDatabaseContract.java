package com.example.hebam.notekeeper;

import android.provider.BaseColumns;

public final class NoteKeeperDatabaseContract { //final -> make it non-inheritable

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private NoteKeeperDatabaseContract(){} //make non-creatable

    /**
     * Inner class that defines constant values for the course database table.
     * Each entry in the table represents a single course.
     */
    public static final class CourseInfoEntry implements BaseColumns { //convention in naming these classes, that represent a table, is to add "Entry" at the end of the name
        //The class implements BaseColumns interface so we could use _ID

        //constant for the table's name
        public static final String TABLE_NAME = "course_info";
        //constants for the columns' names
        public static final String COLUMN_COURSE_ID = "course_id";
        public static final String COLUMN_COURSE_TITLE = "course_title";

        //CREATE TABLE course_info (course_id TEXT UNIQUE NOT NULL, course_title TEXT NOT NULL)
        public static final String SQL_CREATE_TABLE =
        "CREATE TABLE " + TABLE_NAME + " ("+
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_COURSE_ID + " TEXT UNIQUE NOT NULL, " +
                COLUMN_COURSE_TITLE + " TEXT NOT NULL)";
    }

    /**
     * Inner class that defines constant values for the note database table.
     * Each entry in the table represents a single note.
     */
    public static final class NoteInfoEntry implements BaseColumns{
        //constant for the table's name
        public static final String TABLE_NAME = "note_info";
        //constants for the columns' names
        public static final String COLUMN_NOTE_TITLE = "note_title";
        public static final String COLUMN_NOTE_TEXT = "note_text";
        public static final String COLUMN_COURSE_ID = "course_id";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("+
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NOTE_TITLE + " TEXT NOT NULL, " +
                        COLUMN_NOTE_TEXT + " TEXT, " +
                        COLUMN_COURSE_ID + " TEXT NOT NULL)";
    }
}
