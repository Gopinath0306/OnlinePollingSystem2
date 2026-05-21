package com.service;

import com.exception.InvalidVoterException;
import com.management.DBConnectionManager;
import com.management.NomineeManagement;
import com.management.VoteManagement;
import com.model.Nominee;
import com.model.Vote;
import com.model.Voter;
import com.util.ApplicationUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


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

    public Vote castVote(Voter voter, int nomineeId)
            throws InvalidVoterException, SQLException {

        
        if (voteManagement.hasVoterAlreadyVoted(voter.getVoterId())) {
            throw new InvalidVoterException(
                "Voter '" + voter.getVoterName() + "' has already cast their vote. "
                + "Each voter may vote only once.");
        }

        
        Nominee nominee = nomineeManagement.getNomineeById(nomineeId);
        if (nominee == null) {
            throw new InvalidVoterException("Nominee ID " + nomineeId + " does not exist.");
        }

        
        int voteId = ApplicationUtil.generateVoteId(connection);

        
        Vote vote = new Vote(voteId, voter.getVoterId(), nomineeId,
                             new Date(System.currentTimeMillis()));

        
        voteManagement.insertVote(vote);
        nomineeManagement.incrementVoteCount(nomineeId);

        return vote;
    }
    
    public Map<String, Double> getDistrictVotingPercentage() throws SQLException {
        return voteManagement.getDistrictVotingPercentage();
    }
    
    public Map<String, int[]> getDistrictVotingDetails() throws SQLException {
        return voteManagement.getDistrictVotingDetails();
    }
    
    public int getTotalVotesCast() throws SQLException {
        return voteManagement.getTotalVotesCast();
    }

    
    public boolean hasVoterAlreadyVoted(String voterId) throws SQLException {
        return voteManagement.hasVoterAlreadyVoted(voterId);
    }
   
}
