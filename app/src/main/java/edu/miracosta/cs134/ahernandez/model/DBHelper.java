package edu.miracosta.cs134.ahernandez.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
     * Adds a task to the database.
     * @param task to be added.
     * @return id of the task.
     */
    public long addTask(Task task)
    {
        long id ;

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

        id = db.insert(TABLE_NAME, null, values) ;
        // After we're done, close the connection to the database.
        db.close() ;

        return id ;
    }

    /**
     * Gets add the tasks in the database.
     * @return all tasks.
     */
    public List<Task> getAllTasks()
    {
        List<Task> allTasks = new ArrayList<>() ;

        // Get the tasks from database.
        SQLiteDatabase db = getReadableDatabase() ;

        // Query the database to retrieve all records.
        // Store them in a data structure known as a Cursor.

        /** Name of table to pull from, String array that holds the parameters desired.*/
        Cursor cursor = db.query(TABLE_NAME,
                new String[] {KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_IS_DONE },
                null,
                null,
                null,
                null,
                null) ;

        // Loop through the cursor results, one at a time.
        // 1) Instantiate a new Task object.
        // 2) Add the new Task to the List

        // Checks if the cursor has anything in it.
        if(cursor.moveToFirst())
        {
            do{
                // Asks for the array's index.
                long id = cursor.getLong(0) ;
                String description = cursor.getString(1) ;
                // Compares what comes back from the database to 1.
                boolean isDone =  cursor.getInt(2) == 1 ;

                // Creates and adds the new task to the list.
                Task newTask = new Task(id, description, isDone) ;
                allTasks.add(newTask) ;

            } while(cursor.moveToNext()) ;
        }

        // Close the cursor.
        cursor.close() ;

        // Close the connection to the database.
        db.close() ;

        return allTasks ;
    }

    /**
     * Clears the records without deleting the table itself.
     */
    public void clearAllTasks()
    {
        SQLiteDatabase db = getWritableDatabase() ;
        db.delete(TABLE_NAME, null, null) ;

        db.close() ;
    }
}