package com.management;

import com.model.Voter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VoterManagement {

    // Active JDBC connection injected via constructor
    private final Connection connection;

    
    public VoterManagement(Connection connection) {
        this.connection = connection;
    }

   
    public void insertVoter(Voter voter) throws SQLException {
        String sql = "INSERT INTO voter "
                   + "(voter_id, voter_name, dob, age, login_id, password, address, district, mobile_number) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, voter.getVoterId());
            ps.setString(2, voter.getVoterName());
            // Convert java.util.Date → java.sql.Date for JDBC
            ps.setDate(3, new Date(voter.getDob().getTime()));
            ps.setInt(4, voter.getAge());
            ps.setString(5, voter.getLoginId());
            ps.setString(6, voter.getPassword());
            ps.setString(7, voter.getAddress());
            ps.setString(8, voter.getDistrict());
            ps.setLong(9, voter.getMobileNumber());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Insert voter failed – no rows affected.");
            }
        }
    }

  
    public void updateMobileNumber(String voterId, long mobileNumber) throws SQLException {
        String sql = "UPDATE voter SET mobile_number = ? WHERE voter_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, mobileNumber);
            ps.setString(2, voterId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Update failed – Voter ID '" + voterId + "' not found.");
            }
        }
    }

   
    public void updateAddress(String voterId, String address) throws SQLException {
        String sql = "UPDATE voter SET address = ? WHERE voter_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, address);
            ps.setString(2, voterId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Update failed – Voter ID '" + voterId + "' not found.");
            }
        }
    }

   
    public Voter getVoterById(String voterId) throws SQLException {
        String sql = "SELECT * FROM voter WHERE voter_id = ?";
        Voter voter = null;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, voterId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    voter = mapResultSetToVoter(rs);
                }
            }
        }
        return voter;
    }

   
    public Voter getVoterByLoginId(String loginId) throws SQLException {
        String sql = "SELECT * FROM voter WHERE login_id = ?";
        Voter voter = null;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, loginId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    voter = mapResultSetToVoter(rs);
                }
            }
        }
        return voter;
    }

   
    public List<Voter> getVotersByDistrict(String district) throws SQLException {
        String sql = "SELECT * FROM voter WHERE district = ? ORDER BY voter_name";
        List<Voter> voters = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, district);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    voters.add(mapResultSetToVoter(rs));
                }
            }
        }
        return voters;
    }

   
    public int getTotalVoterCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM voter";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        }
        return 0;
    }

    
   

   
    public boolean isLoginIdTaken(String loginId) throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM voter WHERE login_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, loginId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cnt") > 0;
                }
            }
        }
        return false;
    }

    
    public void deleteVoter(String voterId) throws SQLException {
        String sql = "DELETE FROM voter WHERE voter_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, voterId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Delete failed – Voter ID '" + voterId + "' not found.");
            }
        }
    }

    
    public int getVoterCountByDistrict(String district) throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM voter WHERE district = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, district);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cnt");
                }
            }
        }
        return 0;
    }
    
    private Voter mapResultSetToVoter(ResultSet rs) throws SQLException {
        Voter voter = new Voter();
        voter.setVoterId(rs.getString("voter_id"));
        voter.setVoterName(rs.getString("voter_name"));
        voter.setDob(rs.getDate("dob"));             
        voter.setAge(rs.getInt("age"));
        voter.setLoginId(rs.getString("login_id"));
        voter.setPassword(rs.getString("password"));
        voter.setAddress(rs.getString("address"));
        voter.setDistrict(rs.getString("district"));
        voter.setMobileNumber(rs.getLong("mobile_number"));
        return voter;
    }
}
