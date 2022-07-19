package com.example.ems.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.ems.model.LoginCredentials;

import java.util.ArrayList;
import java.util.List;

public class LoginCredentialsDao extends SQLiteOpenHelper {

    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String DESIGNATION = "DESIGNATION";
    public static final String MANAGER_ID = "MANAGER_ID";
    public static final String LOGIN_CREDENTIALS = "LOGINCREDENTIALS";
   // private LoginCredentials loginCredentials;

    public LoginCredentialsDao(@Nullable Context context) {
        super(context, "LoginCredentials.db" , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(" CREATE TABLE " + LOGIN_CREDENTIALS + "(" +
                EMAIL + " TEXT PRIMARY KEY, " +
                PASSWORD + " TEXT NOT NULL, " +
                DESIGNATION + " TEXT NOT NULL, " +
                MANAGER_ID + " INT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+LOGIN_CREDENTIALS);
    }

    public boolean addLoginCredentials (LoginCredentials loginCredentials){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL, loginCredentials.getEmailId());
        contentValues.put(PASSWORD, loginCredentials.getPassword());
        contentValues.put(DESIGNATION, loginCredentials.getDesignation());
        contentValues.put(MANAGER_ID, loginCredentials.getManagerOnlyId());

        long insert = sqLiteDatabase.insert(LOGIN_CREDENTIALS,null,contentValues);

        if(insert == -1){
            Log.d(TAG, "addLoginCredentials() returned: " + false);
            return false;
        }else {
            Log.d(TAG, "addLoginCredentials() returned: " + true);
            return true;
        }
    }

    public List<LoginCredentials> getAllCredentials(){

        List<LoginCredentials> returnList = new ArrayList<>();
        String query="SELECT * FROM "+ LOGIN_CREDENTIALS;

        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor=db.rawQuery(query,null);
        //to make sure its not empty if loop
        if(cursor.moveToFirst()){

            do{
                String email= cursor.getString(0);
                String password = cursor.getString(1);
                String designation = cursor.getString(2);
                int managerOnlyId = cursor.getInt(3);

                LoginCredentials newLoginCredentials= new LoginCredentials(email,password,designation,managerOnlyId);
                returnList.add(newLoginCredentials);

            }while (cursor.moveToNext());
        }
        else
        {
            return returnList;
        }
        cursor.close();
        db.close();
        return returnList;
    }
}
