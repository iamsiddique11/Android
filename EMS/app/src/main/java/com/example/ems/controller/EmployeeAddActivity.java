package com.example.ems.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ems.dao.EmployeeDao;
import com.example.ems.dao.LoginCredentialsDao;
import com.example.ems.model.LoginCredentials;
import com.example.ems.R;
import com.example.ems.model.Employee;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Iterator;
import java.util.List;

public class EmployeeAddActivity extends AppCompatActivity {

    TextInputLayout empFirstName,empLastName,empEmail,empId,empDept,empHireDate,empPNum;
    TextView textViewHeading;
    RadioGroup rgGender;
    RadioButton rbMale,rbFemale,rbOthers;
    Button addEmpButton,viewAllEmpButton;
    String employeeFirstName,employeeLastName,employeeEmail,employeeGender,employeeDepartment,employeeHireDate,employeePhoneNumber,employeeId,employeeSurveyStatus;
    int employeeID,employeeRating,employeeMgrId;
    //long employeePhoneNumber; //TODO numberformat exception giving 0
   // List<Employee> employeeList=new ArrayList<>();
    Employee employee;
    //boolean edit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add);
        initViews();
        Intent intent = getIntent();
        intent.getAction();

        //Bundle bundle =getIntent().getExtras();
       // actionType = bundle.getString("actionType");
       // employeeID = bundle.getInt(employeeId, -1);
        //Toast.makeText(this, "INTENT Action is " + intent.getAction(), Toast.LENGTH_LONG).show();

        if (intent.getAction().equals("edit")) {
                int employeeID=intent.getIntExtra(employeeId, -1);
                EmployeeDao employeeDao=new EmployeeDao(EmployeeAddActivity.this);
                List<Employee> allEmployee=employeeDao.getAllEmployee();
                for (Iterator<Employee> iterator=allEmployee.iterator(); iterator.hasNext(); ) {
                    Employee employeeData=iterator.next();
                    if (employeeID == (employeeData.getEmployeeId())) {
                        setPreFilledData(employeeData);
                    }
                }
            }
        if (intent.getAction().equals("add"))
        {
            int managerID=intent.getIntExtra("managerId", -1);
            setMgrId(managerID);
        }

        addEmpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     if (validateData("empFirstName") & validateData("empLastName") & validateData("empEmail") &validateData("empId")&validateData("empDept")&validateData("empHireDate") &validateData("empPNum")) {
                         setData();
                         //employee=new Employee(employeeID, employeeFirstName, employeeLastName, employeeGender, employeeDepartment, employeeRating, employeeHireDate, employeeEmail, employeePhoneNumber, employeeSurveyStatus, employeeMgrId);
                         if(intent.getAction().equals("edit")){
                             try {
                                 employee=new Employee(employeeID, employeeFirstName, employeeLastName, employeeGender, employeeDepartment, employeeRating, employeeHireDate, employeeEmail, employeePhoneNumber, employeeSurveyStatus, employeeMgrId);
                                 EmployeeDao employeeDao=new EmployeeDao(EmployeeAddActivity.this);
                                 boolean success=employeeDao.editEmployee(employee);
                                 if(success){
                                     Toast.makeText(EmployeeAddActivity.this, "Employee edited successfully"+employee.toString(), Toast.LENGTH_SHORT).show();
                                 }else
                                     Toast.makeText(EmployeeAddActivity.this, "Editing employee returned error", Toast.LENGTH_SHORT).show();
                             } catch (Exception e) {
                                 Toast.makeText(EmployeeAddActivity.this, "exception"+e, Toast.LENGTH_SHORT).show();
                             }
                         }
                         else {
                             try {//TODO default values for Rating n surveyStatus is still not perfect and Generating mgr id is pending
                                 employee=new Employee(employeeID, employeeFirstName, employeeLastName, employeeGender, employeeDepartment, employeeRating, employeeHireDate, employeeEmail, employeePhoneNumber, "pending", getMgrId());
                                 EmployeeDao employeeDao=new EmployeeDao(EmployeeAddActivity.this);
                                 boolean success=employeeDao.addEmployee(employee);
                                 if(success){//TODO continue to add logind ata onec new emo is created and check further process
                                     LoginCredentials loginModel = new LoginCredentials(employeeEmail,"employee","employee",getMgrId());
                                     LoginCredentialsDao loginCredentialsDao = new LoginCredentialsDao(EmployeeAddActivity.this);
                                     loginCredentialsDao.addLoginCredentials(loginModel);
                                     Toast.makeText(EmployeeAddActivity.this, "Employee added successfully"+employee.toString(), Toast.LENGTH_SHORT).show();
                                 }
                                 else Toast.makeText(EmployeeAddActivity.this, "Error adding emp" , Toast.LENGTH_SHORT).show();
                             } catch (Exception e) {
                                 Toast.makeText(EmployeeAddActivity.this, "exception"+e, Toast.LENGTH_SHORT).show();
                             }
                         }
                         Intent showEmployee=new Intent(EmployeeAddActivity.this, ShowEmployees.class);
                         startActivity(showEmployee);
                         finish();
                     }
                }
            });
        viewAllEmpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showEmployee=new Intent(EmployeeAddActivity.this, ShowEmployees.class);
                startActivity(showEmployee);
                finish();
            }
        });
}

    private void setPreFilledData(Employee employeeData) {
        Toast.makeText(this, "Editing EMP" + employeeData.toString(), Toast.LENGTH_SHORT).show();
        textViewHeading.setText("Edit Employee Details");
        empFirstName.getEditText().setText(employeeData.getFirstName());
        empLastName.getEditText().setText(employeeData.getLastName());
        empEmail.getEditText().setText(employeeData.getEmail());
        empId.getEditText().setText(""+employeeData.getEmployeeId());
        //Toast.makeText(this, "FROM SET DATA"+ employeeData.getGender(), Toast.LENGTH_SHORT).show();
        setGender(employeeData.getGender());//TODO change is not reflecting previous change is shown
        empDept.getEditText().setText(employeeData.getDepartment());
        empHireDate.getEditText().setText(employeeData.getHireDate());
        empPNum.getEditText().setText(employeeData.getPhoneNumber());

        employeeMgrId=employeeData.getManagerId();
        employeeRating=employeeData.getEmployeeRating();
        employeeSurveyStatus=employeeData.getSurveyStatus();
        viewAllEmpButton.setEnabled(false);
    }

    private void setGender(String gender) {
        //rgGender.clearCheck();
        switch (gender){
            case ("MALE"):
                rbFemale.setChecked(false);
                rbOthers.setChecked(false);
                rbMale.setChecked(true);
            case ("FEMALE"):
                rbMale.setChecked(false);
                rbFemale.setChecked(true);
                rbOthers.setChecked(false);
        }
    }

    public void initViews(){
        textViewHeading = findViewById(R.id.tvHeading);
        empFirstName = findViewById(R.id.empFirstName);
        empLastName = findViewById(R.id.empLastName);
        empEmail = findViewById(R.id.empEmail);
        empId = findViewById(R.id.empId);
        rgGender =findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        rbOthers = findViewById(R.id.rbOthers);
        empDept = findViewById(R.id.empDept);
        empHireDate = findViewById(R.id.empHireDate);
        empPNum = findViewById(R.id.empPNum);

        addEmpButton = findViewById(R.id.addEmpButton);
        viewAllEmpButton = findViewById(R.id.viewAllEmpButton);

    }

    public String getGender() {
        switch (rgGender.getCheckedRadioButtonId()) {
            case (R.id.rbMale):
                employeeGender="MALE";
                break;
            case (R.id.rbFemale):
                employeeGender="FEMALE";
                break;
            default:
                employeeGender="OTHERS";
                break;
        }
        return employeeGender;

    }

    public boolean validateData(String textInputLayout){
        switch (textInputLayout){
            case "empFirstName":
                if ((empFirstName.getEditText().getText().toString().trim()).equals("")) {
                    empFirstName.setError("Cannot be empty");
                    return false;
                } else {
                    empFirstName.setErrorEnabled(false);
                } return true;

            case "empLastName":
                if ((empLastName.getEditText().getText().toString().trim()).equals("")) {
                    empLastName.setError("Cannot be empty");
                    return false;
                } else {
                    empLastName.setErrorEnabled(false);
                } return true;
            case "empEmail" :
                if ((empEmail.getEditText().getText().toString().trim()).equals("")) {
                    empEmail.setError("Cannot be empty");
                    return false;
                } else {
                    empEmail.setErrorEnabled(false);
                } return true;
            case "empId" :
                if ((empId.getEditText().getText().toString().trim()).equals("")) {
                    empId.setError("Cannot be empty");
                    return false;
                } else {
                    empId.setErrorEnabled(false);
                } return true;
            case "empDept":
                if ((empDept.getEditText().getText().toString().trim()).equals("")) {
                    empDept.setError("Cannot be empty");
                    return false;
                } else {
                    empDept.setErrorEnabled(false);
                } return true;
            case "empHireDate":
                if ((empHireDate.getEditText().getText().toString().trim()).equals("")) {
                    empHireDate.setError("Cannot be empty");
                    return false;
                } else {
                    empHireDate.setErrorEnabled(false);
                } return true;
            case "empPNum":
                if ((empPNum.getEditText().getText().toString().trim()).equals("")) {
                    empPNum.setError("Cannot be empty");
                    return false;
                } else {
                    empPNum.setErrorEnabled(false);
                } return true;
            default:
              return false;
        }
    }

    public void setData(){
        try {
            employeeFirstName = empFirstName.getEditText().getText().toString();
            employeeLastName = empLastName.getEditText().getText().toString();
            employeeEmail = empEmail.getEditText().getText().toString();
            employeeID = Integer.parseInt(empId.getEditText().getText().toString());
            employeeGender = getGender();
            //employeeSurveyStatus = "DONE";
            employeeMgrId = getMgrId();
            employeeDepartment = empDept.getEditText().getText().toString();
            employeeHireDate = empHireDate.getEditText().getText().toString(); //TODO datepicker if have time
            employeePhoneNumber =  empPNum.getEditText().getText().toString().trim();  //TODO converting STR TO LONG NOR WORKING
        }catch (Exception e){
            Toast.makeText(this, "Exception  :" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void setMgrId(int MgrId){
        employeeMgrId = MgrId;
    }
    public int getMgrId(){
        return employeeMgrId;
    }
}
/** NOTES FOR REF
 *     try {
 *                         age = Integer.parseInt(empFirstName.getEditText().getText().toString());
 *                         Toast.makeText(EmployeeAddActivity.this, "Num   " + age , Toast.LENGTH_SHORT).show();
 *                     }catch (Exception e){
 *
 *                     }
 *
 *                     .replaceFirst("^+91","")
 *
 *                     //
 * //    private void clearGender() {
 * //           int selectedId = rgGender.getCheckedRadioButtonId();
 * //            if (selectedId > 0) {
 * //                rgGender.clearCheck();
 * //            }
 * //        }
 * //        private void activateButtons() {
 * //            rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
 * //                public void onCheckedChanged(RadioGroup group, int checkedId) {
 * //                    rgGender.setOnCheckedChangeListener(null);
 * //                }
 * //            });
 * //            clearGender();
 * //        }
 */