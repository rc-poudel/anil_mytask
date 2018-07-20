package com.example.anee.mytask.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anee.mytask.Activity.ToDoListActivity;
import com.example.anee.mytask.models.RecyclerAdapter;
import com.example.anee.mytask.models.ToDoListInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.anee.mytask.volley.AppConfig.task_url;

public class TimeAlertService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();



    }



    private void loadTask() {






        StringRequest stringRequest = new StringRequest(Request.Method.GET, task_url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);



                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting task object from json array

                                JSONObject task = array.getJSONObject(i);

                                String userid = task.getString("taskuid");
                             //   if (userids.equals(userid))
                                {
                                    Log.i("useridddddddddd", userid);


                                    //adding the task to task list
//                                    task.add(new ToDoListInfo(
//
//                                            task.getString("task_title"),
//                                            task.getString("task_location"),
//                                            task.getString("task_time"),
//                                            task.getString("task_date")
//                                    ));
                                }

                            }




                            //creating adapter object and setting it to recyclerview
//                            RecyclerAdapter adapter = new RecyclerAdapter(ToDoListActivity.this, taskList);
//                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }






}
