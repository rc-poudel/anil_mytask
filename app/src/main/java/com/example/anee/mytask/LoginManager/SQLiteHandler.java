package com.example.anee.mytask.LoginManager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.anee.mytask.Activity.TaskListActivity;
import com.example.anee.mytask.Activity.ToDoListActivity;
import com.example.anee.mytask.DatabaseHelper.UserTaskInfo;

import java.util.ArrayList;
import java.util.HashMap;

import static android.os.Build.ID;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mytaskRemider";

    // Login table name
    public static final String TABLE_USER = "task_user";
    public static final String TABLE_TASK = "task_table";


    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";


    //Task table
    public static final String TASK_ID = "taskid";
    public static final String TASK_NAME = "task_title";
    public static final String TASK_LOCATION = "task_location";
    public static final String TASK_LAT = "task_latitude";
    public static final String TASK_LNG = "task_longitude";
    public static final String TASK_TIME = "task_time";
    public static final String TASK_DATE = "task_date";
    public static final String TASKUID = "taskuid";
    public static final String KEY_CREATED_ON = "created_at";


    String CreateTaskTableSql = "CREATE TABLE if not exists 'user_task_list' (\n" +

            "    taskid       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    task_title     VARCHAR (50),\n" +
            "    task_location     VARCHAR (50),\n" +
            "   task_latitude     VARCHAR (50),\n" +
            "  task_longitude     VARCHAR (50),\n" +
            "  task_time     VARCHAR (50),\n" +
            "  task_date     VARCHAR (50),\n" +
            "  taskuid     VARCHAR (50),\n" +
            "  task_created_on     VARCHAR (50)\n"+
            ");";

//    String createtable = "CREATE TABLE if not exists user (\n" +
//            "    Id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//            "    Name     VARCHAR (50),\n" +
//            "    Price    VARCHAR (5),\n" +
//            "    Quantity VARCHAR (3),\n" +
//            "    Total    VARCHAR (5),\n" +
//            "    Image    BLOB\n" +
//            ");";


//            "\t`taskid`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//            "\t`task_title`\tTEXT,\n" +
//            "\t`task_location`\tTEXT,\n" +
//            "\t`task_latitude`\tTEXT,\n" +
//            "\t`task_longitude`\tTEXT,\n" +
//            "\t`task_time`\tTEXT,\n" +
//            "\t`task_date`\tTEXT,\n" +
//            "\t`taskuid`\tTEXT,\n" +
//            "\t`task_created_on`\tTEXT,\n" +
//            ");";
//
//
//    String createtable = "CREATE TABLE if not exists user (\n" +
//            "    Id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//            "    Name     VARCHAR (50),\n" +
//            "    Price    VARCHAR (5),\n" +
//            "    Quantity VARCHAR (3),\n" +
//            "    Total    VARCHAR (5),\n" +
//            "    Image    BLOB\n" +
//            ");";


//    private static final String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
//            + TASK_ID + " INTEGER PRIMARY KEY," + TASK_NAME + " TEXT,"
//            + TASK_LOCATION + " TEXT," + TASK_LAT + " INTEGER,"
//            + TASK_LNG + " TEXT," + TASK_TIME + " INTEGER,"
//            + TASK_DATE + " INTEGER," + TASKUID + " INTEGER,"
//            + KEY_CREATED_AT + " INTEGER" + ")";

    private static final String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
            + KEY_CREATED_AT + " TEXT" + ")";


//    public void onCreateTask(SQLiteDatabase db) {
//        String CREATE_TASK_TABLE = "CREATE TABLE " +  TABLE_TASK + "("
//                + TASK_ID + " INTEGER PRIMARY KEY," + TASK_NAME + " TEXT,"
//                + TASK_LOCATION + " TEXT," + TASK_LAT + " INTEGER,"
//                + TASK_LNG + " TEXT," + TASK_TIME + " INTEGER,"
//                + TASK_DATE + " INTEGER," + TASKUID + " INTEGER,"
//                + KEY_CREATED_AT + " INTEGER" + ")";
//
//        db.execSQL(CREATE_TASK_TABLE);
//
//        Log.d(TAG, "Database task table created");
//
//    }
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
//                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
//                + KEY_CREATED_AT + " TEXT" + ")";
//        db.execSQL(CREATE_LOGIN_TABLE);
//
//        Log.d(TAG, "Database tables created");
//    }

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getWritableDatabase().execSQL(CreateTaskTableSql);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_LOGIN_TABLE);
       // db.execSQL(CREATE_TASK_TABLE);

    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);

    }

    /**
     * Storing user details in database
     */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);

    }


//    public void addTask(String task_title, String task_location, String task_date, String task_time, String task_latitude
//            , String task_longitude, String taskuid, String taskid, String created_on) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
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
//
//
//    }


    public void insertTask(ContentValues cv) {

        getWritableDatabase().insert("user_task_list", "", cv);
    }


    public ArrayList<UserTaskInfo> getUserTaskList() {


        ArrayList<UserTaskInfo> Tasklist = new ArrayList();

        String sql = "select * from user_task_list ORDER BY\n" +
                " taskid ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = getWritableDatabase().rawQuery(sql, null);

        while (cursor.moveToNext()) {

            UserTaskInfo taskInfo = new UserTaskInfo();
            taskInfo.setTask_title(cursor.getString(cursor.getColumnIndex("task_title")));
            taskInfo.setTask_location(cursor.getString(cursor.getColumnIndex("task_location")));
            taskInfo.setTask_time(cursor.getString(cursor.getColumnIndex("task_time")));
            taskInfo.setTask_date(cursor.getString(cursor.getColumnIndex("task_date")));
            taskInfo.setTask_latitude(cursor.getString(cursor.getColumnIndex("task_latitude")));
            taskInfo.setTask_longitude(cursor.getString(cursor.getColumnIndex("task_longitude")));
            taskInfo.setTask_created_on(cursor.getString(cursor.getColumnIndex("task_created_on")));
            taskInfo.setTaskid(cursor.getString(cursor.getColumnIndex("taskid")));
            taskInfo.setTaskuid(cursor.getString(cursor.getColumnIndex("taskuid")));
            Tasklist.add(taskInfo);


        }

        return Tasklist;
    }





    /**
     * Getting user data from database
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }


    /**
     * Getting user data from database
     */
//    public HashMap<String, String> getUserTask() {
//        HashMap<String, String> usertask = new HashMap<String, String>();
//        String selectQuery = "SELECT  * FROM " + TABLE_TASK;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // Move to first row
//        cursor.moveToFirst();
//        if (cursor.getCount() > 0) {
//            usertask.put("taskid", cursor.getString(1));
//            usertask.put("task_title", cursor.getString(2));
//            usertask.put("task_location", cursor.getString(4));
//            usertask.put("task_latitude", cursor.getString(5));
//            usertask.put("task_longitude", cursor.getString(6));
//            usertask.put("task_time", cursor.getString(7));
//            usertask.put("task_date", cursor.getString(8));
//           // usertask.put("created_at", cursor.getString(9));
//        }
//        cursor.close();
//        db.close();
//        // return user
//        Log.d(TAG, "Fetching usertask from Sqlite: " + usertask.toString());
//
//        return usertask;
//    }


    /**
     * Re crate database Delete all tables and create them again
     * @param //id
     */

//    public void deleteTask(String id) {
//
//        SQLiteDatabase dba = this.getWritableDatabase();
//        dba.delete("user_task_list", "taskid" + " = ?",
//                new String[]{String.valueOf(id)});
//
//        dba.close();
//
//
//    }
//    public void deleteTask(String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
////        getWritableDatabase().delete("user_task_list","taskid= ?",new String[]{id} );
////        db.delete(PlaceEntry.TABLE_NAME, PlaceEntry.COLUMN_PLACE_ID + "=\"" + placeId+"\"", null) ;
//         getWritableDatabase().delete("user_task_list", "taskid=" +id, null);
//
//    }

    public void deleteTask(String taskid){
        getWritableDatabase().delete("user_task_list","taskid="+taskid,null);
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();
        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void deleteUsersTask() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_TASK, null, null);
        db.close();
        Log.d(TAG, "Deleted all Task  from sqlite");

    }
}

