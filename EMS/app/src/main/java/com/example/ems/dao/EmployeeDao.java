package com.example.ems.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.ems.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDao extends SQLiteOpenHelper {

    public final static String EMPLOYEE_TABLE_NAME="EMPLOYEE_TABLE";
    public final static String EMPLOYEE_ID="ID";
    public final static String EMPLOYEE_FIRSTNAME="FIRSTNAME";
    public final static String EMPLOYEE_LASTNAME="LASTNAME";
    public final static String EMPLOYEE_GENDER="GENDER";
    public final static String EMPLOYEE_DEPARTMENT="DEPARTMENT";
    public final static String EMPLOYEE_RATING="RATING";
    public final static String EMPLOYEE_HIREDATE="HIREDATE";
    public final static String EMPLOYEE_EMAIL="EMAIL";
    public final static String EMPLOYEE_PHONE_NUMBER="PHONE_NUMBER";
    public final static String EMPLOYEE_SURVEY_STATUS="SURVEY_STATUS";
    public final static String EMPLOYEE_MANAGER_ID="MANAGER_ID";
    Employee employee;


    public EmployeeDao(@Nullable Context context) {
        super(context, "EmployeeTable.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE "+EMPLOYEE_TABLE_NAME+"("+
                EMPLOYEE_ID+" INT PRIMARY KEY, "+
                EMPLOYEE_FIRSTNAME+" TEXT NOT NULL, "+
                EMPLOYEE_LASTNAME+" TEXT NOT NULL, "+
                EMPLOYEE_GENDER+" TEXT NOT NULL, "+
                EMPLOYEE_DEPARTMENT+" TEXT NOT NULL, "+
                EMPLOYEE_RATING+" INT NOT NULL, "+
                EMPLOYEE_HIREDATE+" TEXT NOT NULL, "+
                EMPLOYEE_EMAIL+" TEXT NOT NULL, "+
                EMPLOYEE_PHONE_NUMBER+" TEXT NOT NULL, "+
                EMPLOYEE_SURVEY_STATUS+" TEXT NOT NULL, "+
                EMPLOYEE_MANAGER_ID+" INT NOT NULL "+
                ");");
//        sqLiteDatabase.execSQL(" DROP TABLE " + EMPLOYEE_TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addEmployee(Employee employee) {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(EMPLOYEE_ID, employee.getEmployeeId());
        contentValues.put(EMPLOYEE_FIRSTNAME, employee.getFirstName());
        contentValues.put(EMPLOYEE_LASTNAME, employee.getLastName());
        contentValues.put(EMPLOYEE_GENDER, employee.getGender());
        contentValues.put(EMPLOYEE_DEPARTMENT, employee.getDepartment());
        contentValues.put(EMPLOYEE_RATING, employee.getEmployeeRating());
        contentValues.put(EMPLOYEE_HIREDATE, employee.getHireDate());
        contentValues.put(EMPLOYEE_EMAIL, employee.getEmail());
        contentValues.put(EMPLOYEE_PHONE_NUMBER, employee.getPhoneNumber());
        contentValues.put(EMPLOYEE_SURVEY_STATUS, employee.getSurveyStatus());
        contentValues.put(EMPLOYEE_MANAGER_ID, employee.getManagerId());

        long insert=sqLiteDatabase.insert(EMPLOYEE_TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        if (insert == -1) {
            Log.d(TAG, "addEmployee() returned: "+false);
            return false;
        } else {
            Log.d(TAG, "addEmployee() returned: "+true);
            return true;
        }
    }

    public List<Employee> getAllEmployee() {

        List<Employee> returnList=new ArrayList<>();
        String query="SELECT * FROM "+EMPLOYEE_TABLE_NAME;

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(query, null);
        //to make sure its not empty if loop
        if (cursor.moveToFirst()) {
            do {
                Integer EMPLOYEE_ID=Integer.parseInt(cursor.getString(0));
                String EMPLOYEE_FIRSTNAME=cursor.getString(1);
                String EMPLOYEE_LASTNAME=cursor.getString(2);
                String EMPLOYEE_GENDER=cursor.getString(3);
                String EMPLOYEE_DEPARTMENT=cursor.getString(4);
                Integer EMPLOYEE_RATING=Integer.parseInt(cursor.getString(5));
                String EMPLOYEE_HIREDATE=cursor.getString(6);
                String EMPLOYEE_EMAIL=cursor.getString(7);
                String EMPLOYEE_PHONE_NUMBER=cursor.getString(8);
                String EMPLOYEE_SURVEY_STATUS=cursor.getString(9);
                Integer EMPLOYEE_MANAGER_ID=Integer.parseInt(cursor.getString(10));
                Employee employee=new Employee(EMPLOYEE_ID, EMPLOYEE_FIRSTNAME, EMPLOYEE_LASTNAME, EMPLOYEE_GENDER, EMPLOYEE_DEPARTMENT, EMPLOYEE_RATING, EMPLOYEE_HIREDATE, EMPLOYEE_EMAIL, EMPLOYEE_PHONE_NUMBER, EMPLOYEE_SURVEY_STATUS, EMPLOYEE_MANAGER_ID);
                returnList.add(employee);

            } while (cursor.moveToNext());
        } else {
            //Failed
            Log.d(TAG, "getAllEmployee() returned: "+"Error");
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public Employee getEmployeeById(int employeeID) {
        Log.wtf(TAG, "getEmployee returned: "+employeeID);
        String query="SELECT * FROM "+EMPLOYEE_TABLE_NAME+" WHERE "+EMPLOYEE_ID+" = "+employeeID;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            //Cursor cursor = db.rawQuery("Select * from "+ EMPLOYEE_TABLE_NAME + " where "+ EMPLOYEE_ID   + employeeId + "", null);
            Integer EMPLOYEE_ID=Integer.parseInt(cursor.getString(0));
            String EMPLOYEE_FIRSTNAME=cursor.getString(1);
            String EMPLOYEE_LASTNAME=cursor.getString(2);
            String EMPLOYEE_GENDER=cursor.getString(3);
            String EMPLOYEE_DEPARTMENT=cursor.getString(4);
            Integer EMPLOYEE_RATING=Integer.parseInt(cursor.getString(5));
            String EMPLOYEE_HIREDATE=cursor.getString(6);
            String EMPLOYEE_EMAIL=cursor.getString(7);
            String EMPLOYEE_PHONE_NUMBER=cursor.getString(8);
            String EMPLOYEE_SURVEY_STATUS=cursor.getString(9);
            Integer EMPLOYEE_MANAGER_ID=Integer.parseInt(cursor.getString(10));
            Employee employee=new Employee(EMPLOYEE_ID, EMPLOYEE_FIRSTNAME, EMPLOYEE_LASTNAME, EMPLOYEE_GENDER, EMPLOYEE_DEPARTMENT, EMPLOYEE_RATING, EMPLOYEE_HIREDATE, EMPLOYEE_EMAIL, EMPLOYEE_PHONE_NUMBER, EMPLOYEE_SURVEY_STATUS, EMPLOYEE_MANAGER_ID);
            db.close();
            cursor.close();
            return (employee);
        }else
            return employee;
    }
    public boolean editEmployee(Employee employeeData) {

         SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
       // contentValues.put(EMPLOYEE_ID,employeeData.getEmployeeId());
        contentValues.put(EMPLOYEE_FIRSTNAME,employeeData.getFirstName());
        contentValues.put(EMPLOYEE_LASTNAME,employeeData.getLastName());
        contentValues.put(EMPLOYEE_GENDER,employeeData.getGender());
        contentValues.put(EMPLOYEE_DEPARTMENT,employeeData.getDepartment());
        contentValues.put(EMPLOYEE_RATING,employeeData.getEmployeeRating());
        contentValues.put(EMPLOYEE_HIREDATE,employeeData.getHireDate());
        contentValues.put(EMPLOYEE_EMAIL,employeeData.getEmail());
        contentValues.put(EMPLOYEE_PHONE_NUMBER,employeeData.getPhoneNumber());
        contentValues.put(EMPLOYEE_SURVEY_STATUS,employeeData.getSurveyStatus());
        contentValues.put(EMPLOYEE_MANAGER_ID,employeeData.getManagerId());
        //long insert = sqLiteDatabase.update(EMPLOYEE_TABLE_NAME,contentValues, "where ID = ?" ,new String[] {String.valueOf(employeeData.getEmployeeId())});
        long insert = sqLiteDatabase. update(EMPLOYEE_TABLE_NAME, contentValues, EMPLOYEE_ID + "=" + employeeData.getEmployeeId(), null);
        sqLiteDatabase.close();
        if(insert == -1){
            Log.d(TAG, "editEmployee() returned: " + false);
            return false;
        }else {
            Log.d(TAG, "editEmployee() returned: " + true);
            return true;
        }
    }
    public boolean editEmployeeSurvey(Employee employeeData) {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPLOYEE_RATING,employeeData.getEmployeeRating());
        contentValues.put(EMPLOYEE_SURVEY_STATUS,employeeData.getSurveyStatus());
        long insert = sqLiteDatabase. update(EMPLOYEE_TABLE_NAME, contentValues, EMPLOYEE_ID + "=" + employeeData.getEmployeeId(), null);
        sqLiteDatabase.close();
        if(insert == -1){
            Log.d(TAG, "editEmployee() returned: " + false);
            return false;
        }else {
            Log.d(TAG, "editEmployee() returned: " + true);
            return true;
        }
    }

    public boolean deleteEmployee(int employeeId) {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        long insert = sqLiteDatabase.delete(EMPLOYEE_TABLE_NAME,EMPLOYEE_ID + "=" + employeeId, null);
        sqLiteDatabase.close();
        if(insert == -1){
            Log.d(TAG, "DeleteEmployee() returned: " + false);
            return false;
        }else {
            Log.d(TAG, "Deleted() returned: " + true);
            return true;
        }
    }

}
