package com.service;

import com.management.DBConnectionManager;
import com.management.NomineeManagement;
import com.model.Nominee;
import com.util.ApplicationUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;



public class NomineeService {

    
    private final NomineeManagement nomineeManagement;

    
    private final Connection connection;

    
    public NomineeService() throws SQLException {
        this.connection        = DBConnectionManager.getConnection();
        this.nomineeManagement = new NomineeManagement(connection);
    }

    
    public Nominee addNominee(String nomineeName, String constitution,
                              String district, String symbol, String address)
            throws IllegalArgumentException, SQLException {

        
        if (ApplicationUtil.isNullOrBlank(nomineeName)) {
            throw new IllegalArgumentException("Nominee name cannot be empty.");
        }
        if (ApplicationUtil.isNullOrBlank(constitution)) {
            throw new IllegalArgumentException("Constitution cannot be empty.");
        }
        if (ApplicationUtil.isNullOrBlank(district)) {
            throw new IllegalArgumentException("District cannot be empty.");
        }
        if (ApplicationUtil.isNullOrBlank(symbol)) {
            throw new IllegalArgumentException("Party symbol cannot be empty.");
        }

        
        int nomineeId = ApplicationUtil.generateNomineeId(connection);

        
        Nominee nominee = new Nominee(nomineeId, nomineeName, constitution,
                                      district, symbol, address, 0);

        nomineeManagement.insertNominee(nominee);
        return nominee;
    }

   
    public void updateNomineeAddress(int nomineeId, String address)
            throws IllegalArgumentException, SQLException {

        if (ApplicationUtil.isNullOrBlank(address)) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        
        Nominee existing = nomineeManagement.getNomineeById(nomineeId);
        if (existing == null) {
            throw new IllegalArgumentException("Nominee ID " + nomineeId + " not found.");
        }
        nomineeManagement.updateNomineeAddress(nomineeId, address);
    }

   
    public Nominee searchNomineeById(int nomineeId)
            throws IllegalArgumentException, SQLException {

        Nominee nominee = nomineeManagement.getNomineeById(nomineeId);
        if (nominee == null) {
            throw new IllegalArgumentException("Nominee ID " + nomineeId + " not found in the system.");
        }
        return nominee;
    }


   
    public List<Nominee> getAllNominees() throws SQLException {
        return nomineeManagement.getAllNominees();
    }

   
    public void deleteNominee(int nomineeId)
            throws IllegalArgumentException, SQLException {

        Nominee existing = nomineeManagement.getNomineeById(nomineeId);
        if (existing == null) {
            throw new IllegalArgumentException("Nominee ID " + nomineeId + " not found.");
        }
        nomineeManagement.deleteNominee(nomineeId);
    }

 
}
