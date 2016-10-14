package edu.orangecoastcollege.cs273.kfrederick5.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper database;
    private List<Task> taskList;
    private TaskListAdapter taskListAdapter;

    private EditText taskEditText;
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TEMP, delete old database then create new
        //this.deleteDatabase(DBHelper.DATABASE_TABLE);

        //Make a DBHelper reference
        database = new DBHelper(this);


        //database.addTask(new Task("Die", 1));

        // Fill the list with tasks from the database
        taskList = database.getAllTasks();

        // Create our custom task list adapter
        // (We want to associate with adapter with context, layout, and list
        taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskList);

        //Connect list view with layout
        taskListView = (ListView) findViewById(R.id.taskListView);

        //Associate adapter with the list view
        taskListView.setAdapter(taskListAdapter);

        //Connect edit text with our layout
        taskEditText = (EditText) findViewById(R.id.taskEditText);

        // Make a new task and add to the database



        /* Get all tasks from database and print with Log.i()
        ArrayList<Task> allTasks = taskDB.getAllTasks();
        // Loop through each task, print to Log.i
        for(Task singleTask : allTasks)
            Log.i("DATABASE TASK", singleTask.toString());
            */

    }

    public void addTask(View v)
    {
        String description = taskEditText.getText().toString();

        if(description.isEmpty())
        {
            Toast.makeText(this, "Task description cannot be empty, doofus.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Make new task
            Task newTask = new Task(description, 0);

            // Add task to the list adapter
            taskListAdapter.add(newTask);

            // Add task to database
            database.addTask(newTask);

            taskEditText.setText("");
        }
    }

    public void changeTaskStatus(View v)
    {
        if(v instanceof CheckBox) {
            CheckBox selectedCheck = (CheckBox) v;
            Task selectedTask = (Task) selectedCheck.getTag();

            selectedTask.setIsDone(selectedCheck.isChecked()? 1 : 0);

            //Update it in the database
            database.updateTask(selectedTask);
        }
    }

    public void clearAllTasks(View v)
    {
        // Clear completed
        for(int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getIsDone() == 1)
            {
            taskList.remove(i);
            i--;}
        }
        //taskList.clear();

        // Delete all the records (Tasks) in the database
        database.deleteAllTasks();


        //Tell taskListAdapter to update itself
        taskListAdapter.notifyDataSetChanged();
    }
}
