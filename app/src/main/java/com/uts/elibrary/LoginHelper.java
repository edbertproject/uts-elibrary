package com.uts.elibrary;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginHelper extends SQLiteOpenHelper {
    //Database Name
    public static final String DATABASE_NAME = "logindb";
    //Database Version
    public static final int DATABASE_VERSION = 1;
    //Table User
    public static final String TABLE_USERS = "users";

    //Field in Table Users
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //Declare Variabel that contain Query for Create Table
    public static final String SQL_TABLE_USERS = "CREATE TABLE " + TABLE_USERS +
            " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USERNAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT "
            + " ) ";

    //Contructor
    public LoginHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Execute Query in Variable SQL_TABLE_USERS
        db.execSQL(SQL_TABLE_USERS);

        // add admin user every create table
        addUserWithContext(new UserModel(null,"admin","admin@mail.co","admin"),db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    public void addUserWithContext(UserModel user, SQLiteDatabase db) {
        //Put values to variable DB from UserModel
        ContentValues values = new ContentValues();

        values.put(KEY_USERNAME, user.username);
        values.put(KEY_EMAIL, user.email);
        values.put(KEY_PASSWORD, user.password);

        //Insert new Record to DB
        db.insert(TABLE_USERS, null, values);
    }

    public void addUser(UserModel user) {
        //Initiate to Write DB
        SQLiteDatabase db = this.getWritableDatabase();
        //Put values to variable DB from UserModel
        ContentValues values = new ContentValues();

        values.put(KEY_USERNAME, user.username);
        values.put(KEY_EMAIL, user.email);
        values.put(KEY_PASSWORD, user.password);

        //Insert new Record to DB
        db.insert(TABLE_USERS, null, values);
    }

    public UserModel Authenticate(UserModel user) {
        SQLiteDatabase db = this.getReadableDatabase();

        //Query to look for value match with variable username in DB
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID, KEY_USERNAME, KEY_EMAIL, KEY_PASSWORD},
                KEY_USERNAME + "=?",
                new String[]{user.username},
                null, null,null);

        //get values from UserModel
        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            UserModel user1 = new UserModel(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));

            //Checking is username from EditText is Same with on DB
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }
        return null;
    }

    //Method when email exists
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        //Query to look for value match with variable email in DB
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID, KEY_USERNAME, KEY_EMAIL, KEY_PASSWORD},
                KEY_EMAIL + "=?",
                new String[]{email},
                null, null,null);

        return cursor != null && cursor.moveToFirst() && cursor.getCount() > 0;
    }
}
