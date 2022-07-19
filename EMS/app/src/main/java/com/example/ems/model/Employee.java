package com.example.ems.model;


public class Employee {

    private int employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private String department;
    private int employeeRating;
    public int managerId;

    private String hireDate;
    private String email;
    private String phoneNumber;

    private String surveyStatus;

    public Employee(int employeeId, String firstName, String lastName, String gender, String department, int employeeRating, String hireDate, String email, String phoneNumber, String surveyStatus,int managerId) {
        this.employeeId=employeeId;
        this.firstName=firstName;
        this.lastName=lastName;
        this.gender=gender;
        this.department=department;
        this.employeeRating=employeeRating;
        this.hireDate=hireDate;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.surveyStatus=surveyStatus;
        this.managerId=managerId;
    }
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId=employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender=gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department=department;
    }

    public int getEmployeeRating() {
        return employeeRating;
    }

    public void setEmployeeRating(int employeeRating) {
        this.employeeRating=employeeRating;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate=hireDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber=phoneNumber;
    }

    public String getSurveyStatus() {
        return surveyStatus;
    }

    public void setSurveyStatus(String surveyStatus) {
        this.surveyStatus=surveyStatus;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId=managerId;
    }

    @Override
    public String toString() {
        return "Employee{"+
                "employeeId="+employeeId+
                ", firstName='"+firstName+'\''+
                ", lastName='"+lastName+'\''+
                ", gender='"+gender+'\''+
                ", department='"+department+'\''+
                ", employeeRating="+employeeRating+
                ", managerId="+managerId+
                ", hireDate="+hireDate+
                ", email='"+email+'\''+
                ", phoneNumber="+phoneNumber+
                ", surveyStatus='"+surveyStatus+'\''+
                '}';
    }
}
