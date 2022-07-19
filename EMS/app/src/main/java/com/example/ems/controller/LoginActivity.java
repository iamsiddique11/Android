package com.example.ems.controller;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.ems.dao.LoginCredentialsDao;
import com.example.ems.model.LoginCredentials;
import com.example.ems.R;
import com.example.ems.dao.EmployeeDao;
import com.example.ems.model.Employee;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Iterator;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout editEmail,editPass;
    private RadioGroup radioGroupDesignation;
    private CheckBox rememberMeCheckBox;  //Non functional just for Frontend
    private Button loginButton,buttonAddLogin,buttonViewAllLogin;
    private String designation = "";
    public static final int MANAGER_ID = 2452698;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setVisibilities();

        loginButton.setOnClickListener(new View.OnClickListener() {
            LoginCredentialsDao loginCredentialsDao = new LoginCredentialsDao(LoginActivity.this) ;
            List<LoginCredentials> allCredentials= loginCredentialsDao.getAllCredentials();
            EmployeeDao employeeDao = new EmployeeDao(LoginActivity.this);
            List<Employee> employeeList=employeeDao.getAllEmployee();

            @Override
            public void onClick(View view) {
                if(validateData("email") & validateData("password")){
                LoginCredentials loginCredentials = new LoginCredentials(editEmail.getEditText().getText().toString(),editPass.getEditText().getText().toString(),checkDesignation(),0);
                    for (Iterator<LoginCredentials> iterator=allCredentials.iterator(); iterator.hasNext(); ) {
                        LoginCredentials loginData=iterator.next();
                        if (loginCredentials.getEmailId().equals(loginData.getEmailId()) & loginCredentials.getPassword().equals(loginData.getPassword()) & loginCredentials.getDesignation().equals(loginData.getDesignation())) {
                            Toast.makeText(LoginActivity.this, "Login Successful!"+loginCredentials.toString(), Toast.LENGTH_SHORT).show();
                            //TODO conditon for emp or mgr check and then intent. if mgr send mgr Id as extra.
                            if (loginCredentials.getDesignation() == "employee") {
//                                case "employee":
                                for (Iterator<Employee> iterator1=employeeList.iterator(); iterator1.hasNext(); ) {
                                    Employee empData=iterator1.next();
                                    if (loginCredentials.getEmailId().equals(empData.getEmail())) {
                                        Intent intent=new Intent(LoginActivity.this, EmployeeProfile.class);
                                        intent.putExtra("employeeId", empData.getEmployeeId());
                                        startActivity(intent);
                                        finish();
                                    } else continue;
                                }Toast.makeText(LoginActivity.this, "No employee records in DATABASE", Toast.LENGTH_SHORT).show();
                            }
                            if (loginCredentials.getDesignation() == "manager") {
//                                case "manager" :
                                    Intent intent=new Intent(LoginActivity.this, ShowEmployees.class);
                                    intent.setAction("add");
                                    intent.putExtra("managerId",MANAGER_ID);
                                    startActivity(intent);
                                    finish();
//                                default:
//                                    Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
//                                    startActivity(intent);
                            }
                        }
                    } Toast.makeText(LoginActivity.this, "Login Not Successful !"+"/t"+"Check your credentials Again", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(LoginActivity.this, "Login Credentials validation failed !" + "/t" + "Check your DATA Again", Toast.LENGTH_SHORT).show();
            }
        });

        buttonAddLogin.setOnClickListener(new View.OnClickListener() {
            LoginCredentials loginCredentials;

            @Override
            public void onClick(View view) {
                if(validateData("email") & validateData("password")){
                    loginCredentials = new LoginCredentials(editEmail.getEditText().getText().toString(),editPass.getEditText().getText().toString(),checkDesignation(),MANAGER_ID);
                    Toast.makeText(LoginActivity.this, loginCredentials.toString(), Toast.LENGTH_SHORT).show();
            try {
                LoginCredentialsDao loginCredentialsDao = new LoginCredentialsDao(LoginActivity.this) ;
                boolean success = loginCredentialsDao.addLoginCredentials(loginCredentials);
                Toast.makeText(LoginActivity.this, "added" + success, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(LoginActivity.this, "exception" + e, Toast.LENGTH_SHORT).show();
               }
            }}
        });
        buttonViewAllLogin.setOnClickListener(new View.OnClickListener() {
            LoginCredentials loginCredentials;
            @Override
            public void onClick(View view) {
                try {
                    LoginCredentialsDao loginCredentialsDao = new LoginCredentialsDao(LoginActivity.this) ;
                    List<LoginCredentials> allCredentials= loginCredentialsDao.getAllCredentials();
                    Toast.makeText(LoginActivity.this, allCredentials.toString() , Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(LoginActivity.this, "exception" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         *    List<LoginCredentials> allCredentials= loginCredentialsDao.getAllCredentials();
         *    Toast.makeText(LoginActivity.this, allCredentials.toString() , Toast.LENGTH_SHORT).show();
         */
    }
    private void initView(){

        loginButton = findViewById(R.id.buttonLogin);
        buttonAddLogin = findViewById(R.id.buttonAddLogin);
        buttonViewAllLogin=findViewById(R.id.buttonViewAllLogin);

        editEmail = findViewById(R.id.etEmail);
        editPass = findViewById(R.id.etPassword);
        rememberMeCheckBox = findViewById(R.id.checkBoxRemember);

        radioGroupDesignation= findViewById(R.id.radioDesignation);
    }
private String checkDesignation() {
    switch (radioGroupDesignation.getCheckedRadioButtonId()) {
        case (R.id.radioButtonEmployee):
            designation="employee";
            break;
        case (R.id.radioButtonManager):
            designation="manager";
            break;
        default:
            designation="unknown";
            break;
    }
    return designation;
}
private void setVisibilities() {
   // buttonAddLogin.setEnabled(true);
    buttonViewAllLogin.setEnabled(true);
}
private boolean validateData(String textInputLayout) {
        Log.d(TAG, "validateData : started");
    switch (textInputLayout){
     case "email":
            if (editEmail.getEditText().getText().toString().equals("")) {
                editEmail.setErrorEnabled(true);
                editEmail.setError("Cannot be Empty");
                return false;
            }else {
                editEmail.setErrorEnabled(false);
                return true;
            }
     case "password":
            if (editPass.getEditText().getText().toString().equals("")) {
                editPass.setError("Enter a valid password");
                return false;
            }else {
                editPass.setErrorEnabled(false);
                return true;
            }
            default:return false;
        }
}
}