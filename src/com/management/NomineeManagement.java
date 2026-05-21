package com.management;

import com.model.Nominee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NomineeManagement {

    
    private Connection connection;

    public NomineeManagement(Connection connection) {
        this.connection = connection;
    }

    public void insertNominee(Nominee nominee) throws SQLException {
        String sql = "INSERT INTO nominee "
                   + "(nominee_id, nominee_name, constitution, district, symbol, address, vote_count) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, nominee.getNomineeId());
            ps.setString(2, nominee.getNomineeName());
            ps.setString(3, nominee.getConstitution());
            ps.setString(4, nominee.getDistrict());
            ps.setString(5, nominee.getSymbol());
            ps.setString(6, nominee.getAddress());
            ps.setInt(7, 0);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Insert nominee failed – no rows affected.");
            }
        }
    }
    
    

   
    public void updateNomineeAddress(int nomineeId, String address) throws SQLException {
        String sql = "UPDATE nominee SET address = ? WHERE nominee_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, address);
            ps.setInt(2, nomineeId);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Update failed – Nominee ID " + nomineeId + " not found.");
            }
        }
    }

    
  
   
    public Nominee getNomineeById(int nomineeId) throws SQLException {
        String sql = "SELECT * FROM nominee WHERE nominee_id = ?";
        Nominee nominee = null;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, nomineeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nominee = mapResultSetToNominee(rs);
                }
            }
        }
        return nominee;
    }
  
    public void deleteNominee(int nomineeId) throws SQLException {
        String sql = "DELETE FROM nominee WHERE nominee_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, nomineeId);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Delete failed – Nominee ID " + nomineeId + " not found.");
            }
        }
    }

  
    
    public List<Nominee> getAllNominees() throws SQLException {
        String sql = "SELECT * FROM nominee ORDER BY district, vote_count DESC";
        List<Nominee> nominees = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                nominees.add(mapResultSetToNominee(rs));
            }
        }
        return nominees;
    }
    
    private Nominee mapResultSetToNominee(ResultSet rs) throws SQLException {
        Nominee nominee = new Nominee();
        nominee.setNomineeId(rs.getInt("nominee_id"));
        nominee.setNomineeName(rs.getString("nominee_name"));
        nominee.setConstitution(rs.getString("constitution"));
        nominee.setDistrict(rs.getString("district"));
        nominee.setSymbol(rs.getString("symbol"));
        nominee.setAddress(rs.getString("address"));
        nominee.setVoteCount(rs.getInt("vote_count"));
        return nominee;
    }
    
    public List<Nominee> getNomineesByDistrict(String district) throws SQLException {
        String sql = "SELECT * FROM nominee WHERE district = ? ORDER BY vote_count DESC, nominee_name";
        List<Nominee> nominees = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, district);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    nominees.add(mapResultSetToNominee(rs));
                }
            }
        }
        return nominees;
    }
    
    public Map<String, Integer> getPartyWiseVoteCount() throws SQLException {
        String sql = "SELECT symbol, SUM(vote_count) AS total_votes "
                   + "FROM nominee GROUP BY symbol ORDER BY total_votes DESC";
        Map<String, Integer> partyVotes = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                partyVotes.put(rs.getString("symbol"), rs.getInt("total_votes"));
            }
        }
        return partyVotes;
    }
    
    
    
    
    public void incrementVoteCount(int nomineeId) throws SQLException {
        String sql = "UPDATE nominee SET vote_count = vote_count + 1 WHERE nominee_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, nomineeId);
            ps.executeUpdate();
        }
    }

}
