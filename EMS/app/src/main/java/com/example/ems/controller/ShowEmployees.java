package com.example.ems.controller;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ems.R;
import com.example.ems.dao.EmployeeDao;
import com.example.ems.model.Employee;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ShowEmployees extends AppCompatActivity implements View.OnClickListener {
    TableLayout tableLayout;
    String employeeId;
    Button buttonAddEmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_employees);
        buttonAddEmp = findViewById(R.id.buttonAddEmp);
        initViews();
        Intent intent = getIntent();
        int managerID =intent.getIntExtra("managerId",-1);

        buttonAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ShowEmployees.this, EmployeeAddActivity.class);
                intent.putExtra("managerId",managerID);
                intent.setAction("add");
                startActivity(intent);
//                startActivity(new Intent(ShowEmployees.this, EmployeeAddActivity.class));
                finish();
            }
        });
    }

    public void initViews() {

        tableLayout=(TableLayout) findViewById(R.id.tbl_layout);
        addHeaders();
        showEmployees();
        //Toast.makeText(ShowEmployees.this, employeeList.toString() , Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "SIZEEEE" +employeeList.size() , Toast.LENGTH_SHORT).show();
    }

    public void addHeaders() {
        TableLayout tl = findViewById(R.id.tbl_layout);
        TableRow tr = new TableRow(ShowEmployees.this);
        tr.setLayoutParams(getLayoutParams());
        //  tr.addView(getTextView(0, "Auditor id", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "ID", Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "FIRST NAME",  Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "LAST NAME", Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "GENDER", Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "DEPT", Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "RATING", Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "MANAGER ID", Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "HIRE DATE", Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "EMAIL", Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "PHONE NUMBER", Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "SURVEY STATUS", Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "", Color.GREEN, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "", Color.GREEN, Typeface.BOLD, R.drawable.ic_cell_shape));
        tr.addView(getTextView(0, "", Color.GREEN, Typeface.BOLD, R.drawable.ic_cell_shape));

        tl.addView(tr, getTblLayoutParams());

    }

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(ShowEmployees.this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }

    private TextView getEditTextView(int id, String title, int color, int typeface, int bgColor , Employee employeeData) {
        TextView tv = new TextView(ShowEmployees.this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setClickable(true);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableLayout.invalidate();
                tableLayout.refreshDrawableState();
                //Toast.makeText(ShowEmployees.this, "Clicked EDIT btn", Toast.LENGTH_SHORT).show();
                editEmployee(employeeData);
            }
        });
        return tv;
    }

    private TextView getDeleteTextView(int id, String title, int color, int typeface, int bgColor , Employee employeeData) {
        TextView tv = new TextView(ShowEmployees.this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setClickable(true);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableLayout.invalidate();
                tableLayout.refreshDrawableState();
               // Toast.makeText(ShowEmployees.this, "Clicked DELETE btn", Toast.LENGTH_SHORT).show();
                deleteEmployee(employeeData);
            }
        });
        return tv;
    }

    private TextView getSurveyPendingTextView(int id, String title, int color, int typeface, int bgColor , Employee employeeData) {
        TextView tv = new TextView(ShowEmployees.this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setClickable(true);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableLayout.invalidate();
                tableLayout.refreshDrawableState();
               // Toast.makeText(ShowEmployees.this, "Clicked TakeSurvey btn", Toast.LENGTH_SHORT).show();
                surveyEmployee(employeeData);
            }
        });
        return tv;
    }

    private TextView getViewResultTextView(int id, String title, int color, int typeface, int bgColor , Employee employeeData) {
        TextView tv = new TextView(ShowEmployees.this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setClickable(true);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableLayout.invalidate();
                tableLayout.refreshDrawableState();
                Toast.makeText(ShowEmployees.this, "Clicked View Result btn", Toast.LENGTH_SHORT).show();
                 viewEmployeeResult(employeeData);
            }
        });
        return tv;
    }


    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(1, 1, 1, 1);
        params.weight = 1;
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }

    public void addRows(Employee employeeData){
            Log.d(TAG, " Inside addRows: " );
            TableRow tr = new TableRow(ShowEmployees.this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getRowsTextView(0, String.valueOf(employeeData.getEmployeeId()), Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
            tr.addView(getRowsTextView(0, employeeData.getFirstName(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
            tr.addView(getRowsTextView(0, employeeData.getLastName(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
            tr.addView(getRowsTextView(0, employeeData.getGender(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
            tr.addView(getRowsTextView(0, employeeData.getDepartment(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
            tr.addView(getRowsTextView(0, String.valueOf(employeeData.getEmployeeRating()), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
            tr.addView(getRowsTextView(0, String.valueOf(employeeData.getManagerId()), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
            tr.addView(getRowsTextView(0, employeeData.getHireDate(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
            tr.addView(getRowsTextView(0, employeeData.getEmail(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
            tr.addView(getRowsTextView(0, employeeData.getPhoneNumber(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
            tr.addView(getRowsTextView(0, employeeData.getSurveyStatus(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
            tr.addView(getEditTextView(0,"EDIT" , Color.GREEN, Typeface.BOLD ,R.drawable.ic_cell_shape,employeeData));
            tr.addView(getDeleteTextView(0,"DELETE" , Color.RED, Typeface.BOLD ,R.drawable.ic_cell_shape,employeeData));
            //Toast.makeText(this, ""+employeeData.getSurveyStatus().toLowerCase(Locale.ROOT), Toast.LENGTH_SHORT).show();
            if (employeeData.getSurveyStatus().toLowerCase(Locale.ROOT).equals("pending")) {
                tr.addView(getSurveyPendingTextView(0, "TAKE SURVEY", Color.BLUE, Typeface.BOLD, R.drawable.ic_cell_shape, employeeData));
            }
            else{
                tr.addView(getViewResultTextView(0,"VIEW RESULT" , Color.GRAY, Typeface.BOLD ,R.drawable.ic_cell_shape,employeeData));
            }
        tableLayout.addView(tr, getTblLayoutParams());
        }

    private TextView getRowsTextView(int id, String title, int color, int typeface,int bgColor) {
        TextView tv = new TextView(ShowEmployees.this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }

    @Override
    public void onClick(View view) {

    }

    public void showEmployees(){

        EmployeeDao employeeDao=new EmployeeDao(ShowEmployees.this);
        List<Employee> employeeList=employeeDao.getAllEmployee();
        try {
            for (Iterator<Employee> iterator=employeeList.iterator(); iterator.hasNext(); ) {
                Employee employeeData=iterator.next();
                addRows(employeeData);
            }
        }
        catch(Exception e){
            Log.d(TAG, "addRows: returned exception"+e);
        }
    }
    public void editEmployee(Employee employeeData){

        Intent intent =new Intent(ShowEmployees.this, EmployeeAddActivity.class);
          intent.putExtra(employeeId,employeeData.getEmployeeId());
          intent.setAction("edit");
        startActivity(intent);
        finish();
    }
    public void surveyEmployee(Employee employeeData){

        Intent intent =new Intent(ShowEmployees.this, SurveyPage.class);
        Bundle bundle = new Bundle();

        bundle.putString( "empName", employeeData.getFirstName()+ " " +employeeData.getLastName());
        bundle.putInt(employeeId,employeeData.getEmployeeId());
        Toast.makeText(this, "From INTENT SENDING" + employeeData.getEmployeeId(), Toast.LENGTH_SHORT).show();
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    public void viewEmployeeResult(Employee employeeData){

        Intent intent =new Intent(ShowEmployees.this, EmployeeProfile.class);
        intent.putExtra("employeeId",employeeData.getEmployeeId());
        Toast.makeText(this, "From view Result SENDING" + employeeData.getEmployeeId(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
    public void deleteEmployee(Employee employeeData){
        EmployeeDao employeeDao=new EmployeeDao(ShowEmployees.this);
        try{
        boolean success=employeeDao.deleteEmployee(employeeData.getEmployeeId());
         if (success){
             Toast.makeText(ShowEmployees.this, "Employee deleted successfully"+employeeData.toString(), Toast.LENGTH_SHORT).show();
             startActivity(new Intent(this, ShowEmployees.class));         }
         else{
             Toast.makeText(this, "Error DELETING EMP", Toast.LENGTH_SHORT).show();
         }
        } catch (Exception e) {
        Toast.makeText(ShowEmployees.this, "exception"+e, Toast.LENGTH_SHORT).show();
        }

    }

}
/**
 * public void addRows(Employee employeeData){
 *             Log.d(TAG, " Inside addRows: " );
 *             //Toast.makeText(this, "DATA FROM ADDROWS" + employeeData.toString(), Toast.LENGTH_SHORT).show();
 *             //Toast.makeText(this, "DATA FROM ITERATOR" + employeeData.toString(), Toast.LENGTH_SHORT).show();
 *             TableRow tr = new TableRow(ShowEmployees.this);
 *             tr.setLayoutParams(getLayoutParams());
 *             //
 *             Random random = new Random();
 *             int tr_Index =random.nextInt(10-50)+10;
 *             tr.addView(getRowsTextView(tr_Index, String.valueOf(employeeData.getEmployeeId()), Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
 *             tr.addView(getRowsTextView(tr_Index, employeeData.getFirstName(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
 *             tr.addView(getRowsTextView(tr_Index, employeeData.getLastName(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
 *             tr.addView(getRowsTextView(tr_Index, employeeData.getGender(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
 *             tr.addView(getRowsTextView(tr_Index, employeeData.getDepartment(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
 *             tr.addView(getRowsTextView(tr_Index, String.valueOf(employeeData.getEmployeeRating()), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
 *             tr.addView(getRowsTextView(tr_Index, String.valueOf(employeeData.getManagerId()), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
 *             tr.addView(getRowsTextView(tr_Index, employeeData.getHireDate(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
 *             tr.addView(getRowsTextView(tr_Index, employeeData.getEmail(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
 *             tr.addView(getRowsTextView(tr_Index, employeeData.getPhoneNumber(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
 *           //  if (employeeData.getSurveyStatus().toLowerCase(Locale.ROOT) == "pending"){
 *             tr.addView(getRowsTextView(tr_Index, employeeData.getSurveyStatus(), Color.BLACK, Typeface.BOLD ,R.drawable.ic_cell_shape ));
 *             tr.addView(getEditTextView(tr_Index,"EDIT" , Color.GREEN, Typeface.BOLD ,R.drawable.ic_cell_shape, tr));
 *         //   }
 *          //   else {
 *          //       tr.addView(getRowsTextView(0, employeeData.getSurveyStatus(), Color.BLACK, Typeface.BOLD, R.drawable.ic_cell_shape));
 *          //   }
 *             tableLayout.addView(tr, getTblLayoutParams());
 *         Toast.makeText(this, "ADD ROWs" + tableLayout.getChildAt(tr_Index), Toast.LENGTH_SHORT).show();
 *         }
 *
 *
 *      switch (employeeData.getSurveyStatus().toLowerCase(Locale.ROOT)){
 *                 case ("pending"):
 *                     tr.addView(getSurveyPendingTextView(0,"TAKE SURVEY" , Color.BLUE, Typeface.BOLD ,R.drawable.ic_cell_shape,employeeData));
 *                 case ("done"):
 *                     tr.addView(getViewResultTextView(0,"VIEW RESULT" , Color.GRAY, Typeface.BOLD ,R.drawable.ic_cell_shape,employeeData));
 *             }
 *                 try {
 *             tableLayout.invalidate();
 *             tableLayout.refreshDrawableState();
 *         }catch (Exception e){
 *             Log.d(TAG, "Table Layout Error: " + e.toString());
 *         }
 */