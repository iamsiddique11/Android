package com.example.ems.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ems.R;
import com.example.ems.dao.EmployeeDao;
import com.example.ems.model.Employee;

public class EmployeeProfile extends AppCompatActivity {

    TextView tvEPName,tvEPGender,tvEPMobile,tvEPDept,tvEPSurveyStatus,tvEPEmpID,tvEPEmpEmail,tvEPScore,tvEPComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        initViews();
        Intent intent=getIntent();
        int empID= intent.getIntExtra("employeeId",-1);
        try {
            if(empID!=-1) {
                EmployeeDao employeeDao=new EmployeeDao(EmployeeProfile.this);
                Employee employeeData=employeeDao.getEmployeeById(empID);
                setPreFilledData(employeeData);
            }
            else
                Toast.makeText(this, "Error viewing Result", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "From exception"+ e.toString(), Toast.LENGTH_SHORT).show();
        }
       }

    public void initViews(){
        tvEPName = findViewById(R.id.tvEPName);
        tvEPGender = findViewById(R.id.tvEPGender);
        tvEPMobile = findViewById(R.id.tvEPMobile);
        tvEPDept = findViewById(R.id.tvEPDept);
        tvEPSurveyStatus = findViewById(R.id.tvEPSurveyStatus);
        tvEPEmpID =findViewById(R.id.tvEPEmpId);
        tvEPEmpEmail = findViewById(R.id.tvEPEmail);
        tvEPScore = findViewById(R.id.tvEPScore);
        tvEPComment = findViewById(R.id.tvEPResult);
    }
    private void setPreFilledData(Employee employeeData) {

        Toast.makeText(this, "Set Result EMP" + employeeData.toString(), Toast.LENGTH_SHORT).show();
        tvEPName.setText(employeeData.getFirstName() +" "+ employeeData.getLastName());
        tvEPEmpEmail.setText(employeeData.getEmail().trim());
        tvEPEmpID.setText(""+employeeData.getEmployeeId());
        tvEPGender.setText(employeeData.getGender());
        tvEPDept.setText(employeeData.getDepartment());
        tvEPScore.setText(employeeData.getEmployeeRating()+"/25");
        tvEPMobile.setText(employeeData.getPhoneNumber());
        tvEPSurveyStatus.setText(employeeData.getSurveyStatus());
        tvEPComment.setText(getComment(employeeData.getEmployeeRating()));

    }
    public String getComment(int empRating){
        if(empRating>5 && empRating <=10){
            return "Poor Performance!\n" +
                    "Need to Improve!";
        }
        if(empRating>10 && empRating <=20){
            return "Good Performance!Keep up.\n"+
                    " Eligible for increment!";
        }if(empRating>20 && empRating <=25){
            return "Excellent Performance!\n"+
                    "Promotion Time!";
        }
        return "Take Survey first!!!";
    }
}