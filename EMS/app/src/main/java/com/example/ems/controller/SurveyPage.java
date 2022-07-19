package com.example.ems.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ems.R;
import com.example.ems.dao.EmployeeDao;
import com.example.ems.model.Employee;
import com.google.android.material.textfield.TextInputLayout;

public class SurveyPage extends AppCompatActivity {

    TextInputLayout question1, question2, question3, question4, question5;
    TextView tvEmployeeID, tvEmployeeName;
    Button submit;
    String employeeId;
    int empRating,employeeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page);
        initViews();
        Bundle bundle =getIntent().getExtras();
        String employeeName = bundle.getString("empName");
        employeeID = bundle.getInt(employeeId, -1);
        setPreFilledData(employeeName,employeeID);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (    validateData("question1") &&
                        validateData("question2") &&
                        validateData("question3") &&
                        validateData("question4") &&
                        validateData("question5")) {
                    empRating = (Integer.parseInt(question1.getEditText().getText().toString()))+(Integer.parseInt(question2.getEditText().getText().toString()))+(Integer.parseInt(question3.getEditText().getText().toString()))+(Integer.parseInt(question4.getEditText().getText().toString()))+(Integer.parseInt(question5.getEditText().getText().toString()));
                    EmployeeDao employeeDao=new EmployeeDao(SurveyPage.this);
                    Employee employee1 = setEmployee(employeeID,empRating);
                    try {
                        Boolean success=employeeDao.editEmployee(employee1);
                        if(success){
                            Intent intent = new Intent(SurveyPage.this, ShowEmployees.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(SurveyPage.this, "Failed to Edit", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(SurveyPage.this, "Exception"+ e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(SurveyPage.this, "Enter Correct DATA", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViews() {
        tvEmployeeName=findViewById(R.id.tvEmpName);
        tvEmployeeID=findViewById(R.id.tvEmpId);
        question1=findViewById(R.id.q1);
        question2=findViewById(R.id.q2);
        question3=findViewById(R.id.q3);
        question4=findViewById(R.id.q4);
        question5=findViewById(R.id.q5);
        submit=findViewById(R.id.btnSubmit);

    }

    private void setPreFilledData(String employeeName,int employeeID) {
        tvEmployeeName.setText(employeeName);
        tvEmployeeID.setText(""+employeeID);
    }

    public boolean validateData(String question) {
        switch (question) {
            case "question1":
                if ((question1.getEditText().getText().toString().trim()).equals("")) {
                    question1.setError("Enter non-empty value in range 1-5");
                    Toast.makeText(this, "NON EMPTY", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else if (!((Integer.parseInt(question1.getEditText().getText().toString())) <= 5 && (Integer.parseInt(question1.getEditText().getText().toString()) > 0))) {
                    question1.setError("Enter value in range 1-5");
                    Toast.makeText(this, "Not in range" + (Integer.parseInt(question1.getEditText().getText().toString())), Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    question1.setErrorEnabled(false);
                }
                return true;
            case "question2":
                if ((question2.getEditText().getText().toString().trim()).equals("")) {
                    question2.setError("Enter non-empty value in range 1-5");
                    return false;
                }
                else if (!((Integer.parseInt(question2.getEditText().getText().toString())) <= 5 && (Integer.parseInt(question2.getEditText().getText().toString()) > 0))) {
                    question2.setError("Enter value in range 1-5");
                    return false;
                } else {
                    question2.setErrorEnabled(false);
                }
                return true;
                case "question3":
                if ((question3.getEditText().getText().toString().trim()).equals("")) {
                    question3.setError("Enter non-empty value in range 1-5");
                    return false;
                }
                else if (!((Integer.parseInt(question3.getEditText().getText().toString())) <= 5 && (Integer.parseInt(question3.getEditText().getText().toString()) > 0))) {
                    question3.setError("Enter value in range 1-5");
                    return false;
                } else {
                    question3.setErrorEnabled(false);
                }
                return true;
                case "question4":
                if ((question4.getEditText().getText().toString().trim()).equals("")) {
                    question4.setError("Enter non-empty value in range 1-5");
                    return false;
                }
                else if (!((Integer.parseInt(question4.getEditText().getText().toString())) <= 5 && (Integer.parseInt(question4.getEditText().getText().toString()) > 0))) {
                    question4.setError("Enter value in range 1-5");
                    return false;
                } else {
                    question4.setErrorEnabled(false);
                }
                return true;
                case "question5":
                if ((question5.getEditText().getText().toString().trim()).equals("")) {
                    question5.setError("Enter non-empty value in range 1-5");
                    return false;
                }
                else if (!((Integer.parseInt(question5.getEditText().getText().toString())) <= 5 && (Integer.parseInt(question5.getEditText().getText().toString()) > 0))) {
                    question5.setError("Enter value in range 1-5");
                    return false;
                } else {
                    question5.setErrorEnabled(false);
                }
                return true;
            default:
                return false;
        }
    }

    public Employee setEmployee(int empID,int empRating){
        EmployeeDao employeeDao=new EmployeeDao(SurveyPage.this);
        Employee employee =employeeDao.getEmployeeById(employeeID);
        try {
            employee.setEmployeeId(empID);
            employee.setSurveyStatus("DONE");
            employee.setEmployeeRating(empRating);
            Toast.makeText(this, "Employee found" + employee.toString(), Toast.LENGTH_LONG).show();
        }
      catch (Exception e){
          Toast.makeText(this, "Exception" + e.toString(), Toast.LENGTH_SHORT).show();
      }
        return employee;
    }


}