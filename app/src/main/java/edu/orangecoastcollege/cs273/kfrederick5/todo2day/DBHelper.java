package edu.orangecoastcollege.cs273.kfrederick5.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    private static final String DATABASE_NAME = "ToDo2Day";
    static final String DATABASE_TABLE = "Tasks";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_IS_DONE = "is_done";


    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")";
        database.execSQL (table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    //Create a method to add a new task to the database
    public void addTask(Task newTask)
    {
        // 1) Create a reference to our database
        SQLiteDatabase taskDB = this.getWritableDatabase();

        // 2) Make a key-value pair for each value you want to insert
        ContentValues values = new ContentValues();
        //values.put(KEY_FIELD_ID, newTask.getId());
        values.put(FIELD_DESCRIPTION, newTask.getDescription());
        values.put(FIELD_IS_DONE, newTask.getIsDone());

        // 3) Insert the value into the taskDB
        taskDB.insert(DATABASE_TABLE, null, values);

        // 4) Close database
        taskDB.close();
    }

    //Create a method to get all the tasks
    public ArrayList<Task> getAllTasks()
    {
        // 1) Create a reference to our database
        SQLiteDatabase taskDB = this.getReadableDatabase();

        // 2) Make new empty ArrayList
        ArrayList<Task> allTasks = new ArrayList<>();

        // 3) Query the database for all records (all rows) and all fields (all columns)
        // The return type of query is Cursor
        Cursor results = taskDB.query(DATABASE_TABLE, null, null, null, null, null, null);

        // 4) Loop through the results, create Task objects, add to the ArrayList
        if (results.moveToFirst())
        {
            do {
                int id = results.getInt(0);
                String description = results.getString(1);
                int isDone = results.getInt(2);
                allTasks.add(new Task(id, description, isDone));
            }while(results.moveToNext());

        }

        taskDB.close();
        results.close();
        return allTasks;
    }

    public void updateTask(Task existingTask)
    {
        // 1) Create a reference to our database
        SQLiteDatabase taskDB = this.getWritableDatabase();

        // 2) Make a key-value pair for each value you want to insert
        ContentValues values = new ContentValues();
        //values.put(KEY_FIELD_ID, newTask.getId());
        values.put(FIELD_DESCRIPTION, existingTask.getDescription());
        values.put(FIELD_IS_DONE, existingTask.getIsDone());

        // 3) Insert the value into the taskDB
        taskDB.update(DATABASE_TABLE,
                values,
                KEY_FIELD_ID + "=?",
                new String[] {String.valueOf(existingTask.getId())});

        // 4) Close database
        taskDB.close();
    }

    public void deleteAllTasks()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, FIELD_IS_DONE + "=1", null);
        db.close();
    }



}
