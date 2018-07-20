package com.example.anee.mytask.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.anee.mytask.LoginManager.SQLiteHandler;
import com.example.anee.mytask.LoginManager.SessionManager;
import com.example.anee.mytask.R;

public class Navigation_HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    Button add_task, view_task_list;
    public String username, useremail;
    private SQLiteHandler db;
    private SessionManager session;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_navigation__home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nameView);
        TextView navUseremail = (TextView) headerView.findViewById(R.id.emailView);

        sharedPreferences = getSharedPreferences("task", 0);

        username = sharedPreferences.getString("username", "");
        useremail = sharedPreferences.getString("email", "");



        navUsername.setText(username);
        navUseremail.setText(useremail);
        Log.i("users", username);



        add_task = findViewById(R.id.add_task);


        view_task_list = findViewById(R.id.view_task_list);
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Navigation_HomePage.this, AddTaskActivity.class);
                startActivity(intent);
                finish();
            }
        });
        view_task_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Navigation_HomePage.this, ToDoListActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   // @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navigation__home_page, menu);
//        return true;
//    }

  //  @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            //   logoutUser();
//            Intent intent = new Intent(Navigation_HomePage.this, UserDetails.class);
//            startActivity(intent);
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_home:
                Intent intent = new Intent(Navigation_HomePage.this, RemiderHomeActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_add_task:
                Intent i = new Intent(Navigation_HomePage.this, AddTaskActivity.class);
                startActivity(i);
                break;
            case R.id.nav_to_do_list:
                Intent g = new Intent(Navigation_HomePage.this, ToDoListActivity.class);
                startActivity(g);
                break;
            case R.id.nav_time_alert:
                Intent s = new Intent(Navigation_HomePage.this, TaskListActivity.class);
                startActivity(s);
            case R.id.nav_location_alert:
                Intent t = new Intent(Navigation_HomePage.this, LocationAlertActivity.class);
                startActivity(t);
                break;
            case R.id.nav_logout:
                Intent  l= new Intent(Navigation_HomePage.this, UserDetails.class);
                startActivity(l);
               break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//    private void logoutUser() {
//
//        session.setLogin(false);
//        db.deleteUsers();
//
//        // Launching the login activity
//        Intent intent = new Intent(Navigation_HomePage.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }
}
