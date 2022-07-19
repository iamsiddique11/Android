package com.example.ems.model;
//TODO add mgr_id a foriegn key here coz even mgrz have mgrz.
public class LoginCredentials {
    private String emailId;
    private String password;
    private String designation;
    private int managerOnlyId;

    public LoginCredentials(String emailId, String password, String designation , int managerOnlyId) {
        this.emailId = emailId;
        this.password = password;
        this.designation = designation;
        this.managerOnlyId = managerOnlyId;
    }

    public LoginCredentials() {
    }

    public int getManagerOnlyId() {
        return managerOnlyId;
    }

    public void setManagerOnlyId(int managerOnlyId) {
        this.managerOnlyId=managerOnlyId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "LoginCredentials{"+
                "emailId='"+emailId+'\''+
                ", password='"+password+'\''+
                ", designation='"+designation+'\''+
                ", managerOnlyId="+managerOnlyId+
                '}';
    }
}
