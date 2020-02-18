package edu.miracosta.cs134.ahernandez.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    // Task 1: Make constants for all database values
    // database name, version, table base, field names, primary key.
    public static final String DATABASE_NAME = "ToDo2Day" ;
    public static final String TABLE_NAME = "Tasks" ;
    public static final int VERSION = 1 ;

    public static final String KEY_FIELD_ID = "_id" ;
    public static final String FIELD_DESCRIPTION = "description" ;
    public static final String FIELD_IS_DONE = "is_done" ;


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSQL = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")" ;

        Log.i(DATABASE_NAME, createSQL) ;
        db.execSQL(createSQL) ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 1) Drop the old table, recreate the new

        if(newVersion > oldVersion)
        {
            // Delete the old version.
            String dropSQL = "DROP TABLE IF EXISTS " + TABLE_NAME ;
            Log.i(DATABASE_NAME, dropSQL) ;
            db.execSQL(dropSQL) ;

            // Create a new database
            onCreate(db) ;
        }
    }

    /**
     * Adds a new task to the database.
     * @param task The new task to be added.
     */
    public void addTask(Task task)
    {
        // Decide whether we're reading or writing to/from the database.
        // For adding tasks, we're writing to the database.

        // How to get the database without an explicit variable.
        SQLiteDatabase db = getWritableDatabase() ;

        // When we add to the database, use a data structure.
        // called contentValues (key, value) pairs
        ContentValues values = new ContentValues() ;

        // Set up our key/values pairs
        values.put(FIELD_DESCRIPTION, task.getDescription()) ;
        values.put(FIELD_IS_DONE, task.isDone() ? 1 : 0) ;

        db.insert(TABLE_NAME, null, values) ;
        // After we're done, close the connection to the database.
        db.close() ;
    }
}
