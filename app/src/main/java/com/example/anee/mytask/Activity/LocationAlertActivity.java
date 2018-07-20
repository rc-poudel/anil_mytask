package com.example.anee.mytask.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anee.mytask.LoginManager.SQLiteHandler;
import com.example.anee.mytask.R;
import com.example.anee.mytask.models.TodolistPojo;

public class LocationAlertActivity extends AppCompatActivity {

    TextView task, date, location, time;
    public  String task_name, task_date, task_loc, task_time,task_id;
    Button button;

    SQLiteHandler sqLiteHandler;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_alert);
        sqLiteHandler = new SQLiteHandler(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Location Alert");
        // task_id = getIntent().getStringExtra("taskid");
       // Toast.makeText(LocationAlertActivity.this, task_id+"hello", Toast.LENGTH_SHORT).show();
        task = findViewById(R.id.task_name);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        time = findViewById(R.id.time);
        button = findViewById(R.id.delte_btn);
        sharedPreferences = getSharedPreferences("taskid", 0);

        TodolistPojo pojo = (TodolistPojo) getIntent().getSerializableExtra("EXTRA");
        task_name = pojo.getName();
        task_date = pojo.getDate();
        task_loc = pojo.getLocation();
        task_time = pojo.getTime();
        task_id = pojo.getTask_Id();
        task.setText(task_name);
        date.setText(task_date);
        location.setText(task_loc);
        time.setText(task_id);
      //  Toast.makeText(LocationAlertActivity.this, task+"I love Coding", Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String id = sharedPreferences.getString("id", "");
               // Toast.makeText(LocationAlertActivity.this, id+"I am form sharp",Toast.LENGTH_SHORT).show();

                  ShowAlertDialog();

                }



        });
    }

    public void ShowAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task !!");
        builder.setMessage("Are You Sure !!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sqLiteHandler.deleteTask(task_id);

                Intent intent = new Intent(LocationAlertActivity.this,Navigation_HomePage.class);
                startActivity(intent);
                finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                // app icon in action bar clicked; goto parent activity.
                Intent intent = new Intent(this, Navigation_HomePage.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent intent = new Intent(this, Navigation_HomePage.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }




}
