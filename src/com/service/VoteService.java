package com.service;

import com.exception.InvalidVoterException;
import com.management.DBConnectionManager;
import com.management.NomineeManagement;
import com.management.VoteManagement;
import com.model.Nominee;
import com.model.Voter;
import com.util.ApplicationUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class VoteService {

    
    private final VoteManagement    voteManagement;
    private final NomineeManagement nomineeManagement;

    
    private final Connection connection;

    
    public VoteService() throws SQLException {
        this.connection        = DBConnectionManager.getConnection();
        this.voteManagement    = new VoteManagement(connection);
        this.nomineeManagement = new NomineeManagement(connection);
    }

   
    public Voter authenticateVoter(String loginId, String password)
            throws InvalidVoterException, SQLException {

        if (ApplicationUtil.isNullOrBlank(loginId) || ApplicationUtil.isNullOrBlank(password)) {
            throw new InvalidVoterException("Login ID and Password cannot be empty.");
        }

        Voter voter = voteManagement.authenticateVoter(loginId, password);
        if (voter == null) {
            throw new InvalidVoterException("Invalid Login ID or Password. Authentication failed.");
        }
        return voter;
    }

    public List<Nominee> getAllNomineesForReport() throws SQLException {
        return nomineeManagement.getAllNominees();
    }

   
}
