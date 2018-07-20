package com.example.anee.mytask.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.toolbox.Volley;
import com.example.anee.mytask.LoginManager.SQLiteHandler;
import com.example.anee.mytask.LoginManager.SessionManager;
import com.example.anee.mytask.R;
import com.example.anee.mytask.Service.TimeAlertService;
import com.example.anee.mytask.volley.AppConfig;
import com.example.anee.mytask.volley.AppController;

import static com.example.anee.mytask.volley.AppConfig.task_url;

public class LoginActivity extends Activity {
    private static final String TAG = SignupActivity.class.getSimpleName();
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private Button login;
    private TextView login_link;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_login);
        login = findViewById(R.id.btn_login);
        login_link = findViewById(R.id.link_signup);
        sharedPreferences = getSharedPreferences("task", 0);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);




        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());


        // Check if user is already logged in or not
        if (session.isLoggedIn()) {

            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, Navigation_HomePage.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();


                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);


                }


                else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the Email and Password!", Toast.LENGTH_LONG)
                            .show();
                }


            }

        });

        // Link to Register Screen
        login_link.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        SignupActivity.class);
                startActivity(i);
                finish();
            }
        });




    }
    private void loadTask() {


        StringRequest stringRequest = new StringRequest(Method.POST, task_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            String userids = sharedPreferences.getString("udi", "");


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting task object from json array

                                JSONObject task = array.getJSONObject(i);



                                String userid = task.getString("taskuid");

                                if (userids.equals(userid))
                                {
                                    String task_title = task.getString("task_title");
                                    String task_location = task.getString("task_location");
                                    String task_date = (task.getString("task_date"));
                                    String task_time = (task.getString("task_time"));
                                    String task_latitude = (task.getString("task_latitude"));
                                    String task_longitude = (task.getString("task_longitude"));
                                    String taskuid = task.getString("taskuid");
                                    String taskid = task.getString("taskid");
                                    String created_on = task.getString("task_created_on");
                                    Log.i("TaskName", task_title);
                                    Log.i("TaskLocation", task_location);

//                                    db.addTask(task_title, task_location, task_date, task_time,task_latitude,task_longitude,
//                                            taskuid,taskid,created_on);


                                }

                            }

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


    //  function to verify login details in mysql db

    private boolean checkLogin(final String email, final String password) {
            // Tag used to cancel the request
            String tag_string_req = "req_login";

            pDialog.setMessage("Logging in ...");
        showDialog();


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Login Response: " + response.toString());
                        hideDialog();

                        try {


                            JSONObject jObj = new JSONObject(response.toString());
                            boolean error = jObj.getBoolean("error");

                            // Check for error node in json
                            if (!error) {
                                // user successfully logged in
                                // Create login session
                                session.setLogin(true);

                                // Now store the user in SQLite

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String uid = jObj.getString("uid");


                                Log.i("userid", uid);
                                JSONObject user = jObj.getJSONObject("user");
                                String name = user.getString("name");

                                String email = user.getString("email");
                                String created_at = user.getString("created_at");

                                editor.putString("udi", uid);
                                editor.putString("username", name);
                                Log.i("username", name);
                                editor.putString("email", email);

                                editor.apply();

                                // Inserting row in users table
                                db.addUser(name, email, uid, created_at);

                                // Launch main activity



                                Intent intent = new Intent(LoginActivity.this,
                                        Navigation_HomePage.class);
                                Intent i = new Intent(LoginActivity.this,
                                        TimeAlertService.class);
                                startService(i);

                                Log.i("userid", uid);
                                intent.putExtra("userid", uid);
                                loadTask();
                                startActivity(intent);
                                finish();

                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(LoginActivity.this, "No internet connection!!", Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        return false;
    }












    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}