package com.management;

import com.model.Vote;
import com.model.Voter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



public class VoteManagement {

    private final Connection connection;

    
    public VoteManagement(Connection connection) {
        this.connection = connection;
    }

    
    public void insertVote(Vote vote) throws SQLException {
        String sql = "INSERT INTO vote(vote_id, voter_id, nominee_id, vote_date) VALUES (?, ?, ?, ?)";

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

    public boolean hasVoterAlreadyVoted(String voterId) throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM vote WHERE voter_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, voterId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cnt") > 0;
                }
            }
        }
        return false;
    }
    
    public int getTotalVotesCast() throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM vote";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        }
        return 0;
    }
    
    public Map<String, Double> getDistrictVotingPercentage() throws SQLException {
        String sql =
            "SELECT v.district, COUNT(DISTINCT v.voter_id)  AS total_voters, COUNT(DISTINCT vt.vote_id)  AS votes_cast, "
          + "       CASE "
          + "           WHEN COUNT(DISTINCT v.voter_id) = 0 THEN 0.0 "
          + "           ELSE ROUND((COUNT(DISTINCT vt.vote_id) * 100.0) / COUNT(DISTINCT v.voter_id), 2) "
          + "       END AS percentage "
          + "FROM voter v LEFT JOIN vote vt ON v.voter_id = vt.voter_id "
          + "GROUP BY v.district "
          + "ORDER BY percentage DESC";

        Map<String, Double> percentageMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String district    = rs.getString("district");
                double percentage  = rs.getDouble("percentage");
                percentageMap.put(district, percentage);
            }
        }
        return percentageMap;
    }

    public Map<String, int[]> getDistrictVotingDetails() throws SQLException {
        String sql =
            "SELECT v.district,  COUNT(DISTINCT v.voter_id) AS total_voters,  COUNT(DISTINCT vt.vote_id) AS votes_cast "
          + "FROM voter v LEFT JOIN vote vt ON v.voter_id = vt.voter_id "
          + "GROUP BY v.district "
          + "ORDER BY v.district";

        Map<String, int[]> detailsMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String district   = rs.getString("district");
                int totalVoters   = rs.getInt("total_voters");
                int votesCast     = rs.getInt("votes_cast");
                detailsMap.put(district, new int[]{totalVoters, votesCast});
            }
        }
        return detailsMap;
    }
    

   
}
