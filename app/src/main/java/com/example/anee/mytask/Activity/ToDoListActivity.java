package com.example.anee.mytask.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anee.mytask.DatabaseHelper.UserTaskInfo;
import com.example.anee.mytask.LoginManager.SQLiteHandler;
import com.example.anee.mytask.R;
import com.example.anee.mytask.models.RecyclerAdapter;
import com.example.anee.mytask.models.ToDoListInfo;
import com.example.anee.mytask.models.TodolistPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClicklistener {
    private static final String task_url = "https://jatadigital.com/api/select.php";
    SQLiteHandler sqLiteHandler;
    RelativeLayout container;
    List<TodolistPojo> taskList;
    RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ProgressDialog pDialog;
    RecyclerAdapter adapter;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_do_list);
        recyclerView = findViewById(R.id.recyclerView);
        container = findViewById(R.id.linear_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("My To Do List");
        sharedPreferences = getSharedPreferences("taskid",0);
        sqLiteHandler = new SQLiteHandler(this);
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        taskList = new ArrayList<>();
     //   loadTask();
        selectUserTask();

    }
//    public void onResume(){
//        super.onResume();
//        selectUserTask();
//    }

    public void selectUserTask() {



        recyclerView.removeAllViews();

        ArrayList<UserTaskInfo> taskinfo = sqLiteHandler.getUserTaskList();


        for (int i = 0; i < taskinfo.size(); i++) {

            UserTaskInfo taskInfo = taskinfo.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.raw_item,null);
            String task_id, task_name, task_location,task_date,task_time;
            task_id = (taskInfo.getTaskid());

//            SharedPreferences.Editor editor = sharedPreferences.edit();
            task_name = (taskInfo.getTask_title());
            task_location = (taskInfo.getTask_location());
            task_date = (taskInfo.getTask_date());
            task_time = (taskInfo.getTask_time());




            taskList.add(new TodolistPojo(task_id, task_name, task_location, task_date, task_time) );

            Intent intent = new Intent(ToDoListActivity.this,RecyclerAdapter.class);

        //  startActivity(intent);
          //  intent.putExtra("taskid",view.getTag().toString());
//            container.addView(view);
        }

        adapter = new RecyclerAdapter(ToDoListActivity.this, taskList);
        adapter.setOnItemClickListener(ToDoListActivity.this);
        recyclerView.setAdapter(adapter);

    }





//    private void loadTask() {
//
//
//
//
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, task_url,
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//
//                            //converting the string to json array object
//                            JSONArray array = new JSONArray(response);
//
//                           String userids = sharedPreferences.getString("udi","");
//
//
//                                //traversing through all the object
//                            for (int i = 0; i < array.length(); i++) {
//
//                                //getting task object from json array
//
//                                JSONObject task = array.getJSONObject(i);
//
//                                String userid = task.getString("taskuid");
//                                if (userids.equals(userid)) {
//                                    Log.i("useridddddddddd", userid);
//
//
//                                    //adding the task to task list
//                                    taskList.add(new ToDoListInfo(
//
//                                            task.getString("task_title"),
//                                            task.getString("task_location"),
//                                            task.getString("task_time"),
//                                            task.getString("task_date")
//                                    ));
//                                }
//
//                            }
//
//
//
//
//                            //creating adapter object and setting it to recyclerview
//                            RecyclerAdapter adapter = new RecyclerAdapter(ToDoListActivity.this, taskList);
//                            recyclerView.setAdapter(adapter);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });

//        //adding our stringrequest to queue
//        Volley.newRequestQueue(this).add(stringRequest);
//    }


















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
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onItemclick(TodolistPojo pojo) {

        Intent intent = new Intent(ToDoListActivity.this, LocationAlertActivity.class);
        intent.putExtra("EXTRA", pojo);

        startActivity(intent);

    }
}


