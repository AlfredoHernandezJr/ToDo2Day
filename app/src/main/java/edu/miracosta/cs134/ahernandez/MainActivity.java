package edu.miracosta.cs134.ahernandez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import edu.miracosta.cs134.ahernandez.model.DBHelper;
import edu.miracosta.cs134.ahernandez.model.Task;

public class MainActivity extends AppCompatActivity {

    // Create a reference to the database.
    private DBHelper mDB ;
    private List<Task> mAllTasks ;

    private EditText descriptionEditText ;
    private ListView taskListView ;
    private TaskListAdapter mTaskListAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descriptionEditText = findViewById(R.id.taskEditText) ;
        taskListView = findViewById(R.id.taskListView) ;
        mDB = new DBHelper(this) ;

        mAllTasks = mDB.getAllTasks() ;

        // Instantiate the ListAdapter.
        mTaskListAdapter = new TaskListAdapter(this, R.layout.task_item, mAllTasks) ;

        // Connect the ListView with the ListAdapter.
        taskListView.setAdapter(mTaskListAdapter) ;
    }

    public void addTask(View v)
    {
        // let's extract the description from the EditText.
        String description = "" ;

        // id = -1, description = User input, isDone = false.
        if(descriptionEditText.getText().toString() != "")
        {
            description = descriptionEditText.getText().toString() ;
            Task newTask = new Task(description) ;
            long id = mDB.addTask(newTask) ;
            newTask.setId(id) ;
            // Add the new task to the list.
            mAllTasks.add(newTask) ;
        }

        mTaskListAdapter.notifyDataSetChanged() ;
    }

    public void clearAllTasks(View v)
    {
        mDB.clearAllTasks() ;
        mAllTasks.clear();
        mTaskListAdapter.notifyDataSetChanged() ;
    }

    // Ctrl + o
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDB.close() ;
    }
}