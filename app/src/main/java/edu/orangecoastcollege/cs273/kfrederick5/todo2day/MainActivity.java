package edu.orangecoastcollege.cs273.kfrederick5.todo2day;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TEMP, delete old database then create new
        this.deleteDatabase(DBHelper.DATABASE_TABLE);

        //Make a DBHelper reference
        DBHelper taskDB = new DBHelper(this);

        // Make a new task and add to the database
        taskDB.addTask(new Task(1, "Study for CS273 Midterm", 0));
        taskDB.addTask(new Task(2, "Study for CS200 Test", 0));
        taskDB.addTask(new Task(3, "Study for Physics 185 Test", 0));
        taskDB.addTask(new Task(4, "Study for Math 280 Test", 0));
        taskDB.addTask(new Task(5, "Die", 0));

        // Get all tasks from database and print with Log.i()
        ArrayList<Task> allTasks = taskDB.getAllTasks();
        // Loop through each task, print to Log.i
        for(Task singleTask : allTasks)
            Log.i("DATABASE TASK", singleTask.toString());

    }
}
