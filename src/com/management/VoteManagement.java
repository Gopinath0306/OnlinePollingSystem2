package com.management;

import com.model.Vote;
import com.model.Voter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class VoteManagement {

    private final Connection connection;

    
    public VoteManagement(Connection connection) {
        this.connection = connection;
    }

    
    public void insertVote(Vote vote) throws SQLException {
        String sql = "INSERT INTO vote (vote_id, voter_id, nominee_id, voted_date) "
                   + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, vote.getVoteId());
            ps.setString(2, vote.getVoterId());
            ps.setInt(3, vote.getNomineeId());
            ps.setDate(4, new Date(System.currentTimeMillis())); // Today's date

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Insert vote failed – no rows affected.");
            }
        }
    }

    
    public Voter authenticateVoter(String loginId, String password) throws SQLException {
        String sql = "SELECT * FROM voter WHERE login_id = ? AND password = ?";
        Voter voter = null;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, loginId);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    voter = new Voter();
                    voter.setVoterId(rs.getString("voter_id"));
                    voter.setVoterName(rs.getString("voter_name"));
                    voter.setDob(rs.getDate("dob"));
                    voter.setAge(rs.getInt("age"));
                    voter.setLoginId(rs.getString("login_id"));
                    voter.setPassword(rs.getString("password"));
                    voter.setAddress(rs.getString("address"));
                    voter.setDistrict(rs.getString("district"));
                    voter.setMobileNumber(rs.getLong("mobile_number"));
                }
            }
        }
        return voter;
    }

   
    

   
}
