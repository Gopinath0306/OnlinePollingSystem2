package com.service;

import com.exception.InvalidVoterException;
import com.management.DBConnectionManager;
import com.management.VoterManagement;
import com.model.Voter;
import com.util.ApplicationUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VoterService {

    
    private final VoterManagement voterManagement;

    
    private final Connection connection;
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

   
    public VoterService() throws SQLException {
        this.connection      = DBConnectionManager.getConnection();
        this.voterManagement = new VoterManagement(connection);
    }

    public Voter addVoter(String record)  throws InvalidVoterException, SQLException{
    	String[] data=record.split(":");
    	
    	String pattern="[A-Za-z ]+";
    	
    	String voterName=data[0];
    	Date dob=parseDate(data[1]);
    	String loginId=data[2];
    	String password=data[3];
    	String address=data[4];
    	String district=data[5];
    	long mobileNumber=Long.parseLong(data[6]);
    	
    	
    	if (ApplicationUtil.isNullOrBlank(voterName)) {
           throw new InvalidVoterException("Voter name cannot be empty.");
      }
      
      if(!voterName.matches(pattern)) {
     	throw new InvalidVoterException("voter name only contains alphabets.Please Try again");
      }
      if (dob == null) {
          throw new InvalidVoterException("Date of birth cannot be null.");
      }
  
      int age = ApplicationUtil.calculateAge(dob);
      if (age < 18 && age>99) {
          throw new InvalidVoterException(
              "Voter is underage (age = " + age + "). Must be at least 18 years old.");
      }
      if (ApplicationUtil.isNullOrBlank(loginId)) {
          throw new InvalidVoterException("Login ID cannot be empty.");
      }
      if (voterManagement.isLoginIdTaken(loginId)) {
          throw new InvalidVoterException("Login ID '" + loginId + "' is already registered.");
      }
      if (ApplicationUtil.isNullOrBlank(password)) {
          throw new InvalidVoterException("Password cannot be empty.");
      }
      if (!ApplicationUtil.isValidMobileNumber(mobileNumber)) {
          throw new InvalidVoterException("Mobile number must be exactly 10 digits.");
     }

      
     String voterId = ApplicationUtil.generateVoterId(connection);
      
     Voter voter = new Voter(voterId, voterName, dob, age,
                             loginId, password, address, district, mobileNumber);

      voterManagement.insertVoter(voter);
     return voter;
    	
    }
    
    
    private static Date parseDate(String dateStr) {
        DATE_FORMAT.setLenient(false);
        try {
            return DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            ApplicationUtil.printError("Invalid date format. Please use dd-MM-yyyy (e.g., 15-08-1990).");
            return null;
        }
    }
   
//    public Voter addVoter(String voterName, Date dob, String loginId,
//                          String password, String address, String district,
//                          long mobileNumber)
//            throws InvalidVoterException, SQLException {
//
//    	String pattern="[A-Za-z ]+";
//       
//        if (ApplicationUtil.isNullOrBlank(voterName)) {
//            throw new InvalidVoterException("Voter name cannot be empty.");
//        }
//        
//        if(!voterName.matches(pattern)) {
//        	throw new InvalidVoterException("voter name only contains alphabets.Please Try again");
//        }
//        if (dob == null) {
//            throw new InvalidVoterException("Date of birth cannot be null.");
//        }
    
//        int age = ApplicationUtil.calculateAge(dob);
//        if (age < 18 && age>99) {
//            throw new InvalidVoterException(
//                "Voter is underage (age = " + age + "). Must be at least 18 years old.");
//        }
//        if (ApplicationUtil.isNullOrBlank(loginId)) {
//            throw new InvalidVoterException("Login ID cannot be empty.");
//        }
//        if (voterManagement.isLoginIdTaken(loginId)) {
//            throw new InvalidVoterException("Login ID '" + loginId + "' is already registered.");
//        }
//        if (ApplicationUtil.isNullOrBlank(password)) {
//            throw new InvalidVoterException("Password cannot be empty.");
//        }
//        if (!ApplicationUtil.isValidMobileNumber(mobileNumber)) {
//            throw new InvalidVoterException("Mobile number must be exactly 10 digits.");
//        }
//
//        
//        String voterId = ApplicationUtil.generateVoterId(connection);
//
//        
//        Voter voter = new Voter(voterId, voterName, dob, age,
//                                loginId, password, address, district, mobileNumber);
//
//        voterManagement.insertVoter(voter);
//        return voter;
//    }
    
    

   
    public void updateMobileNumber(String voterId, long mobileNumber)
            throws InvalidVoterException, SQLException {

        if (!ApplicationUtil.isValidMobileNumber(mobileNumber)) {
            throw new InvalidVoterException("Mobile number must be exactly 10 digits.");
        }
        Voter existing = voterManagement.getVoterById(voterId);
        if (existing == null) {
            throw new InvalidVoterException("Voter ID '" + voterId + "' not found.");
        }
        voterManagement.updateMobileNumber(voterId, mobileNumber);
    }

    
    public void updateAddress(String voterId, String address)
            throws InvalidVoterException, SQLException {

        if (ApplicationUtil.isNullOrBlank(address)) {
            throw new InvalidVoterException("Address cannot be empty.");
        }
        Voter existing = voterManagement.getVoterById(voterId);
        if (existing == null) {
            throw new InvalidVoterException("Voter ID '" + voterId + "' not found.");
        }
        voterManagement.updateAddress(voterId, address);
    }

  
    public Voter searchVoterById(String voterId)
            throws InvalidVoterException, SQLException {

        Voter voter = voterManagement.getVoterById(voterId);
        if (voter == null) {
            throw new InvalidVoterException("Voter ID '" + voterId + "' not found in the system.");
        }
        return voter;
    }

   
    public List<Voter> getVotersByDistrict(String district) throws SQLException {
        return voterManagement.getVotersByDistrict(district);
    }

  
    public void deleteVoter(String voterId)
            throws InvalidVoterException, SQLException {

        Voter existing = voterManagement.getVoterById(voterId);
        if (existing == null) {
            throw new InvalidVoterException("Voter ID '" + voterId + "' not found.");
        }
        voterManagement.deleteVoter(voterId);
    }
}
