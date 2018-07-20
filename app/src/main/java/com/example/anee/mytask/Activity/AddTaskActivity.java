package com.example.anee.mytask.Activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anee.mytask.LoginManager.SQLiteHandler;
import com.example.anee.mytask.R;
import com.example.anee.mytask.Service.Receiver;
import com.example.anee.mytask.volley.MySingleton;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.anee.mytask.LoginManager.SQLiteHandler.KEY_CREATED_ON;
import static com.example.anee.mytask.LoginManager.SQLiteHandler.TABLE_TASK;
import static com.example.anee.mytask.LoginManager.SQLiteHandler.TASKUID;
import static com.example.anee.mytask.LoginManager.SQLiteHandler.TASK_DATE;
import static com.example.anee.mytask.LoginManager.SQLiteHandler.TASK_ID;
import static com.example.anee.mytask.LoginManager.SQLiteHandler.TASK_LAT;
import static com.example.anee.mytask.LoginManager.SQLiteHandler.TASK_LNG;
import static com.example.anee.mytask.LoginManager.SQLiteHandler.TASK_LOCATION;
import static com.example.anee.mytask.LoginManager.SQLiteHandler.TASK_NAME;
import static com.example.anee.mytask.LoginManager.SQLiteHandler.TASK_TIME;


public class AddTaskActivity extends AppCompatActivity {
    public String tasklocation, userid, taskname;

    private static final String TAG = "AddTaskActivity";


    public static double tasklatitude, tasklongitude;
    SQLiteDatabase db;
    SQLiteHandler sqLiteHandler;
    SharedPreferences sharedPreferences;


    public TextView task_location, task_name;
    public Calendar calendar;
    DatePicker pickerDate;
    TimePicker pickerTime;


    static ArrayList<String> places;
    public static String value;
    AlertDialog.Builder builder;

    private String UploadUrl = "https://jatadigital.com/api/insertdata.php";


    RequestQueue requestQueue;
    private Button saveBtn;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("task", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(" Add Task");
        actionBar.setDisplayHomeAsUpEnabled(true);
        sqLiteHandler = new SQLiteHandler(this);
        db = sqLiteHandler.getWritableDatabase();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        task_location = findViewById(R.id.add_task_location);
        pickerDate = findViewById(R.id.date_Picker);
        pickerTime = findViewById(R.id.time_Picker);
        task_name = findViewById(R.id.add_task_name);
        builder = new AlertDialog.Builder(AddTaskActivity.this);
        saveBtn = findViewById(R.id.uploadBtn);
        requestQueue = Volley.newRequestQueue(AddTaskActivity.this);
        calendar = Calendar.getInstance();
        pDialog = new ProgressDialog(AddTaskActivity.this);
        //   getDate();
//        getTime();
//        getMapUpdate();


        final String lng = new Double(tasklongitude).toString();
        final String lat = new Double(tasklatitude).toString();


        tasklocation = getIntent().getStringExtra("location");
        taskname = task_name.getText().toString();


        if (tasklocation == null)
            tasklocation = "Search in map";


        task_location.setText(tasklocation);


        task_location.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                pickPointOnMap();


                Intent intent = new Intent(AddTaskActivity.this, MapsActivity.class);
                startActivity(intent);


            }


        });


        saveBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                saveTask();
                // Closing database connection
                Intent openMainScreen = new Intent(AddTaskActivity.this, Receiver.class);
                openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startService(openMainScreen);

                if (taskname == null) {
                    task_name.setError(Html.fromHtml("<font color = 'red'>" +
                            " Please Enter the Task and Location First</font>"));


                } else {

//                    final String servertaskname, servertaskdate, servertasktime;
//                    servertaskname = task_name.getText().toString();
//                    servertaskdate = pickerTime.getText().toString();
//                    servertasktime = time_Picker_text.getText().toString();
//                    pDialog.setMessage("Task is saving ...");
//                    showDialog();


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UploadUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {

                                    hideDialog();
                                    finish();
                                    Intent intent = new Intent(AddTaskActivity.this, ToDoListActivity.class);
                                    startActivity(intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                    // Hiding the progress dialog after all task complete.
                                    hideDialog();
                                    // Showing error message if something goes wrong.
                                    Toast.makeText(AddTaskActivity.this, "Server Error .........", Toast.LENGTH_LONG).show();

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            userid = sharedPreferences.getString("udi", "");

                            // Creating Map String Params.
//                        final String lng;
                            Map<String, String> params = new HashMap<String, String>();

                            //    Adding All values to Params.
//                            params.put("task_title", servertaskname);
//                        //    params.put("task_location", tasklocation);
//                            params.put("task_date", servertaskdate);
//                            params.put("task_time", servertasktime);
                            params.put("task_latitude", lat);
                            params.put("task_longitude", lng);
                            Log.i("useridddddddddd", userid);
                            params.put("taskuid", userid);
                            return params;
                        }


                    };
                    // Toast.makeText(AddTaskActivity.this, AddTaskActivity.tasklatitude + "", Toast.LENGTH_LONG).show();
                    Toast.makeText(AddTaskActivity.this, userid + "", Toast.LENGTH_SHORT);
                    MySingleton.getInstance(AddTaskActivity.this).addRequestQue(stringRequest);

                }
            }

        });


    }


    static final int PICK_MAP_POINT_REQUEST = 999;  // The request code

    private void pickPointOnMap() {
        Intent pickPointIntent = new Intent(this, MapsActivity.class);
        startActivityForResult(pickPointIntent, PICK_MAP_POINT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_MAP_POINT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                LatLng latLng = (LatLng) data.getParcelableExtra("picked_point");
                // Toast.makeText(this, "Point Chosen: " + latLng.latitude + " " + latLng.longitude, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void saveTask() {
        final String lng = new Double(tasklongitude).toString();
        final String lat = new Double(tasklatitude).toString();
        String taskname = task_name.getText().toString();
        String tasklocation = task_location.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put("task_title", taskname);
        cv.put("task_location", tasklocation);
        cv.put("task_latitude", lat);
        cv.put("task_longitude", lng);
        cv.put("taskuid", userid);
        Calendar calender = Calendar.getInstance();
        calender.clear();
        calender.set(Calendar.MONTH, pickerDate.getMonth());
        calender.set(Calendar.DAY_OF_MONTH, pickerDate.getDayOfMonth());
        calender.set(Calendar.YEAR, pickerDate.getYear());
        calender.set(Calendar.HOUR, pickerTime.getCurrentHour());
        calender.set(Calendar.MINUTE, pickerTime.getCurrentMinute());
        calender.set(Calendar.SECOND, 00);

        SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.hour_minutes));
        String timeString = formatter.format(new Date(calender.getTimeInMillis()));
        SimpleDateFormat dateformatter = new SimpleDateFormat(getString(R.string.dateformate));
        String dateString = dateformatter.format(new Date(calender.getTimeInMillis()));

        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Receiver.class);

        String alertTitle = task_name.getText().toString();
        intent.putExtra(getString(R.string.alert_title), alertTitle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);

        cv.put(sqLiteHandler.TASK_TIME, timeString);
        cv.put(sqLiteHandler.TASK_DATE, dateString);
        sqLiteHandler.insertTask(cv);
        db.close();


    }

    public void saveTaskIntoServer() {

        String taskname = task_name.getText().toString();
        String tasklocation = task_location.getText().toString();


        //    String taskdate = date_picker_text.getText().toString();
        //    String tasktime = time_Picker_text.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put("task_title", taskname);
        cv.put("task_location", tasklocation);
        // cv.put("task_latitude",lat);
        //cv.put("task_longitude",lng);
        cv.put("taskuid", userid);

        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AddTaskActivity.this, Receiver.class);
        String alertTitle = task_name.getText().toString();
        intent.putExtra(getString(R.string.alarmtext), alertTitle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddTaskActivity.this, 0, intent, 0);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        //  cv.put("task_time",taskdate);
        // cv.put("task_date",tasktime);

        sqLiteHandler.insertTask(cv);
        db.close();

        finish();


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                item.setTitle("Add Task");

                Intent intent = new Intent(this, Navigation_HomePage.class);
                startActivity(intent);

                // app icon in action bar clicked; goto parent activity.
                this.finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    public void getDate() {
//
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//        month = calendar.get(Calendar.MONTH);
//        year = calendar.get(Calendar.YEAR);
//
//       // date_picker_text.setText(year + "/" + month + "/" + day);
//        date_picker_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//             //           date_picker_text.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
//
//
//                    }
//                }, year, month, day);
//                datePickerDialog.show();
//            }
//        });
//
//    }

//    public void getTime() {
//
//        hour = calendar.get(Calendar.HOUR_OF_DAY);
//        minute = calendar.get(Calendar.MINUTE);
//        SelectedTimeFormat(hour);
//        time_Picker_text.setText(hour + " : " + minute + " " + format);
//
//        time_Picker_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        SelectedTimeFormat(hourOfDay);
//                        time_Picker_text.setText(hourOfDay + " : " + minute + " " + format);
//
//                    }
//                }, hour, minute, true);
//                timePickerDialog.show();
//            }
//        });
//
//
//    }


//    public void SelectedTimeFormat(int hour) {
//
//        if (hour == 0) {
//            hour += 12;
//            format = "AM";
//
//        } else if (hour == 12) {
//            format = "PM";
//
//
//        } else if (hour > 12) {
//            hour -= 12;
//            format = "PM";
//        } else {
//
//            format = "AM";
//        }
//    }
//
//    public void getMapUpdate() {
//
//    }
//
//
////    public void addTask(String task_title, String task_location, String task_date, String task_time, String task_latitude
////            , String task_longitude, String taskuid, String taskid, String created_on) {
////
////
//        ContentValues values = new ContentValues();
//        values.put(TASK_ID, taskid);
//        values.put(TASK_NAME, task_title);
//        values.put(TASK_LOCATION, task_location);
//        values.put(TASK_DATE, task_date);
//        values.put(TASK_TIME, task_time);
//        values.put(TASK_LAT, task_latitude);
//        values.put(TASK_LNG, task_longitude);
//        values.put(TASKUID, taskuid);
//        values.put(KEY_CREATED_ON, created_on);
//
//
//        // Inserting Row
//        long id = db.insert(TABLE_TASK, null, values);
//        db.close(); // Closing database connection
//
//        Log.d(TAG, "New Task inserted into sqlite: " + id);
//        Intent intent = new Intent(this, ToDoListActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//
//
//    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();

        task_name.setText(taskname);

    }


}



