package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;


public class ApplicationUtil {

    
    private static final String VOTER_ID_PREFIX = "VTR";

    

    public static String generateVoterId(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM voter";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("cnt");
            }
            // Format: VTR00001, VTR00002 ...
            return VOTER_ID_PREFIX + String.format("%05d", count + 1);
        }
    }

   
    public static int generateVoteId(Connection connection) throws SQLException {
        String sql = "SELECT COALESCE(MAX(vote_id), 0) + 1 AS next_id FROM vote";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("next_id");
            }
            return 1;
        }
    }

    public static int generateNomineeId(Connection connection) throws SQLException {
        String sql = "SELECT COALESCE(MAX(nominee_id), 1000) + 1 AS next_id FROM nominee";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("next_id");
            }
            return 1001;
        }
    }

    
    public static int calculateAge(Date dob) {
        if (dob == null) {
            return 0;
        }
        
        LocalDate birthDate   = dob.toInstant()
                                   .atZone(ZoneId.systemDefault())
                                   .toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

   
    public static boolean isValidMobileNumber(long mobileNumber) {
        String mobile = String.valueOf(mobileNumber);
        return mobile.length() == 10;
    }

   
    public static boolean isNullOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static void printSeparator() {
        System.out.println("============================================================");
    }

    public static void printDivider() {
        System.out.println("------------------------------------------------------------");
    }

   
    public static void printBanner(String title) {
        printSeparator();
        String paddedTitle = "\t\t\t\t" + title;
        System.out.println(paddedTitle);
        printSeparator();
    }

    
    public static void printSuccess(String message) {
        System.out.println("\n  SUCCESS: " + message);
    }

   
    public static void printError(String message) {
        System.out.println("\n  ERROR: " + message);
    }

    
    public static void printInfo(String message) {
        System.out.println("  ℹ  " + message);
    }
}
