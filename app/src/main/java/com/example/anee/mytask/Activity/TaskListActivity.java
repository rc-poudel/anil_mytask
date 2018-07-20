package com.example.anee.mytask.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anee.mytask.DatabaseHelper.UserTaskInfo;
import com.example.anee.mytask.LoginManager.SQLiteHandler;
import com.example.anee.mytask.R;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {
    SQLiteHandler sqLiteHandler;
    LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        sqLiteHandler = new SQLiteHandler(this);
        container = findViewById(R.id.activity_task);
//        selectUserTask();

    }

//    public void selectUserTask() {
//
//
//        ArrayList<UserTaskInfo> taskinfo = sqLiteHandler.getUserTaskList();
//
//
//        for (int i = 0; i < taskinfo.size(); i++) {
//            UserTaskInfo taskInfo = taskinfo.get(i);
////            TextView taskView = new TextView(this);
////            taskView.setText(taskInfo.getTask_title() + "\n " + taskInfo.getTaskuid());
//            View view = LayoutInflater.from(this).inflate(R.layout.raw_item,null);
//            TextView taskname,tasklocation;
//            taskname = view.findViewById(R.id.name);
//            tasklocation =view.findViewById(R.id.location);
//            taskname.setText(taskInfo.getTask_title());
//            tasklocation.setText(taskInfo.getTask_location());
//
//            container.addView(view);
//
//
//        }
//
//
//    }
}
