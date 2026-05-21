package com.model;

import java.util.Date;


public class Voter {

  
    private String voterId;       // voter_id   VARCHAR(25) PK
    private String voterName;     // voter_name VARCHAR(25)
    private Date   dob;           // dob        DATE
    private int    age;           // age        INT
    private String loginId;       // login_id   VARCHAR(25)
    private String password;      // password   VARCHAR(25)
    private String address;       // address    VARCHAR(50)
    private String district;      // district   VARCHAR(25)
    private long   mobileNumber;  // mobile_number BIGINT

   
    public Voter() {
    }

    
    public Voter(String voterId, String voterName, Date dob, int age,
                 String loginId, String password, String address,
                 String district, long mobileNumber) {
        this.voterId      = voterId;
        this.voterName    = voterName;
        this.dob          = dob;
        this.age          = age;
        this.loginId      = loginId;
        this.password     = password;
        this.address      = address;
        this.district     = district;
        this.mobileNumber = mobileNumber;
    }

   
    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getVoterName() {
        return voterName;
    }

    public void setVoterName(String voterName) {
        this.voterName = voterName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    @Override
    public String toString() {
        return "\n  Voter ID      : " + voterId
             + "\n  Name          : " + voterName
             + "\n  Date of Birth : " + dob
             + "\n  Age           : " + age
             + "\n  Login ID      : " + loginId
             + "\n  Address       : " + address
             + "\n  District      : " + district
             + "\n  Mobile        : " + mobileNumber;
    }
}
