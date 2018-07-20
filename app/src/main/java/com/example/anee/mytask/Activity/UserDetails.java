package com.example.anee.mytask.Activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.anee.mytask.LoginManager.SQLiteHandler;
import com.example.anee.mytask.LoginManager.SessionManager;
import com.example.anee.mytask.R;

public class UserDetails extends Activity {

    private TextView txtName;
    private TextView txtEmail,txttaskname,txttaskid;
    private Button btnLogout;
//    public static String userid;
    private SQLiteHandler db;
    private SessionManager session;
    public AddTaskActivity addTaskActivity = new AddTaskActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        txtName =  findViewById(R.id.name);
        txtEmail =  findViewById(R.id.email);
        txttaskid =  findViewById(R.id.taskid);
        txttaskname =  findViewById(R.id.taskname);
        btnLogout =  findViewById(R.id.btnLogout);
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
               HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
        String  uid = user.get("uid");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);
        // addTaskActivity.userid =uid;

        ContentValues cv = new ContentValues();
        cv.get("task_latitude");
        


               // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

    }



    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();
        db.deleteUsersTask();
        // Launching the login activity
        Intent intent = new Intent(UserDetails.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}