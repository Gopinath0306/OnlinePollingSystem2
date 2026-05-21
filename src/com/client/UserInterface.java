package com.client;

import com.exception.InvalidVoterException;
import com.management.DBConnectionManager;
import com.model.Nominee;
import com.model.Vote;
import com.model.Voter;
import com.service.NomineeService;
import com.service.VoterService;
import com.service.VoteService;
import com.util.ApplicationUtil;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class UserInterface {

   
    static  Scanner scanner = new Scanner(System.in);

    
    private static VoterService   voterService;
    private static NomineeService nomineeService;
    private static VoteService    voteService;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

   
    public static void main(String[] args) {
       
       
        try {
           
            voterService   = new VoterService();
            nomineeService =new NomineeService();
            voteService    = new VoteService();
            displayMainMenu();

        } catch (SQLException e) {
            ApplicationUtil.printError(e.getMessage());
            
        } finally {
           
            DBConnectionManager.closeConnection();
            System.out.println("\n  System shutdown complete. Goodbye!");
        }
    }

  
    private static void displayMainMenu() {
        int choice;
        do {
            System.out.println();
            
            ApplicationUtil.printSeparator();
            System.out.println("           ONLINE POLLING SYSTEM - MAIN MENU");
            ApplicationUtil.printSeparator();
            System.out.println("  1. Voter Module");
            System.out.println("  2. Nominee Module");
            System.out.println("  3. Vote Module");
            System.out.println("  4. Result Module");
            System.out.println("  5. Exit");
            ApplicationUtil.printSeparator();
            System.out.print("  Enter your choice: ");

           
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: displayVoterMenu();   
                        break;
                case 2: displayNomineeMenu(); 
                        break;
                case 3:displayVoteMenu();
                       break;
                case 4: displayResultMenu();  
                   break;
                case 5: System.out.println("\n  Returning to exit..."); 
                       break;
                default: ApplicationUtil.printError("Invalid option. Please enter 1–5.");
            }
        } while (choice != 5);
    }

    
    private static void displayVoterMenu() {
        int choice;
        do {
            System.out.println();
            ApplicationUtil.printSeparator();
            System.out.println("               VOTER MODULE");
            ApplicationUtil.printSeparator();
            System.out.println("  1. Add Voter");
            System.out.println("  2. Update Mobile Number");
            System.out.println("  3. Update Address");
            System.out.println("  4. Search Voter By ID");
            System.out.println("  5. View Voters By District");
            System.out.println("  6. Delete Voter");
            System.out.println("  7. Back to Main Menu");
            ApplicationUtil.printSeparator();
            System.out.print("  Enter your choice: ");

            
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: handleAddVoter();              
                        break;
                case 2: handleUpdateMobileNumber();    
                        break;
                case 3: handleUpdateAddress();          
                       break;
                case 4: handleSearchVoterById();        
                       break;
                case 5: handleViewVotersByDistrict();  
                      break;
                case 6: handleDeleteVoter();           
                      break;
                case 7: System.out.println("  Returning to main menu..."); 
                       break;
                default: ApplicationUtil.printError("Invalid option. Please enter 1–7.");
            }
        } while (choice != 7);
    }

   
    private static void handleAddVoter() {
        System.out.println();
        ApplicationUtil.printBanner("ADD VOTER");

        try {
            System.out.print("  Enter Voter Name       : ");
            String voterName = scanner.nextLine().trim();

            System.out.print("  Enter Date of Birth    : (dd-MM-yyyy) ");
            String dobStr = scanner.nextLine().trim();
            Date dob = parseDate(dobStr);
            if (dob == null) return;

            System.out.print("  Enter Login ID         : ");
            String loginId = scanner.nextLine().trim();

            System.out.print("  Enter Password         : ");
            String password = scanner.nextLine().trim();

            System.out.print("  Enter Address          : ");
            String address = scanner.nextLine().trim();

            System.out.print("  Enter District         : ");
            String district = scanner.nextLine().trim();

            System.out.print("  Enter Mobile Number    : ");
            long mobileNumber = scanner.nextLong();
            scanner.nextLine();

            
            Voter newVoter = voterService.addVoter(voterName, dob, loginId,
                                                    password, address, district, mobileNumber);

            ApplicationUtil.printSuccess("Voter registered successfully!");
            System.out.println("  Generated Voter ID: " + newVoter.getVoterId());
            System.out.println("  Voter Age        : " + newVoter.getAge());

        } catch (InvalidVoterException e) {
            ApplicationUtil.printError(e.getMessage());
        } catch (SQLException e) {
            ApplicationUtil.printError( e.getMessage());
        }
    }

   
    private static void handleUpdateMobileNumber() {
        System.out.println();
        ApplicationUtil.printBanner("UPDATE MOBILE NUMBER");

        System.out.print("  Enter Voter ID        : ");
        String voterId = scanner.nextLine().trim();

        System.out.print("  Enter New Mobile No.  : ");
        long mobile = scanner.nextLong();
        scanner.nextLine();

        try {
            voterService.updateMobileNumber(voterId, mobile);
            ApplicationUtil.printSuccess("Mobile number updated successfully for Voter ID: " + voterId);
        } catch (InvalidVoterException e) {
            ApplicationUtil.printError(e.getMessage());
        } catch (SQLException e) {
            ApplicationUtil.printError( e.getMessage());
        }
    }

    
    private static void handleUpdateAddress() {
        System.out.println();
        ApplicationUtil.printBanner("UPDATE ADDRESS");

        System.out.print("  Enter Voter ID   : ");
        String voterId = scanner.nextLine().trim();

        System.out.print("  Enter New Address: ");
        String address = scanner.nextLine().trim();

        try {
            voterService.updateAddress(voterId, address);
            ApplicationUtil.printSuccess("Address updated successfully for Voter ID: " + voterId);
        } catch (InvalidVoterException e) {
            ApplicationUtil.printError(e.getMessage());
        } catch (SQLException e) {
            ApplicationUtil.printError( e.getMessage());
        }
    }

    
    private static void handleSearchVoterById() {
        System.out.println();
        ApplicationUtil.printBanner("SEARCH VOTER BY ID");

        System.out.print("  Enter Voter ID: ");
        String voterId = scanner.nextLine().trim();

        try {
            Voter voter = voterService.searchVoterById(voterId);
            System.out.println();
            ApplicationUtil.printDivider();
            System.out.println("  VOTER DETAILS");
            ApplicationUtil.printDivider();
            System.out.println(voter);
            ApplicationUtil.printDivider();
        } catch (InvalidVoterException e) {
            ApplicationUtil.printError(e.getMessage());
        } catch (SQLException e) {
            ApplicationUtil.printError( e.getMessage());
        }
    }

    
    private static void handleViewVotersByDistrict() {
        System.out.println();
        ApplicationUtil.printBanner("VOTERS BY DISTRICT");

        System.out.print("  Enter District Name: ");
        String district = scanner.nextLine().trim();

        try {
            List<Voter> voters = voterService.getVotersByDistrict(district);
            if (voters.isEmpty()) {
                ApplicationUtil.printInfo("No voters found in district: " + district);
                return;
            }
            System.out.println("\n  Total Voters in '" + district + "': " + voters.size());
            ApplicationUtil.printDivider();
            for (int i = 0; i < voters.size(); i++) {
                System.out.println("  [" + (i + 1) + "]" + voters.get(i));
                ApplicationUtil.printDivider();
            }
        } catch (SQLException e) {
            ApplicationUtil.printError("Database error: " + e.getMessage());
        }
    }

   
    private static void handleDeleteVoter() {
        System.out.println();
        ApplicationUtil.printBanner("DELETE VOTER");

        System.out.print("  Enter Voter ID to Delete: ");
        String voterId = scanner.nextLine().trim();

        System.out.print("  Are you sure you want to delete Voter ID '" + voterId + "'? (yes/no): ");
        String confirm = scanner.nextLine().trim();

        if (!"yes".equalsIgnoreCase(confirm)) {
            ApplicationUtil.printInfo("Delete operation cancelled.");
            return;
        }

        try {
        	Voter voter = voterService.searchVoterById(voterId);
            voterService.deleteVoter(voterId);
            ApplicationUtil.printSuccess("Voter ID '" + voterId + "' deleted successfully.");
            System.out.println(voter);
        } catch (InvalidVoterException e) {
            ApplicationUtil.printError(e.getMessage());
        } catch (SQLException e) {
            ApplicationUtil.printError( e.getMessage());
        }
    }

    
    
//    Nominee module
    private static void displayNomineeMenu() {
        int choice;
        do {
            System.out.println();
            ApplicationUtil.printSeparator();
            System.out.println("               NOMINEE MODULE");
            ApplicationUtil.printSeparator();
            System.out.println("  1. Add Nominee");
            System.out.println("  2. Update Nominee Address");
            System.out.println("  3. Search Nominee By ID");
            System.out.println("  4.View nominees by district");
            System.out.println("  5. Delete Nominee");
            System.out.println("  6. Back to Main Menu");
            ApplicationUtil.printSeparator();
            System.out.print("  Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: handleAddNominee();             
                       break;
                case 2: handleUpdateNomineeAddress();   
                       break;
                case 3: handleSearchNomineeById();      
                      break;
                case 4:
                     handleViewNomineesByDistrict(); 
                     break;
                case 5: handleDeleteNominee();          
                      break;
                case 6: System.out.println("  Returning to main menu...");
                     break;
                default: ApplicationUtil.printError("Invalid option. Please enter 1–6.");
            }
        } while (choice != 6);
    }

   
    private static void handleAddNominee() {
        System.out.println();
        ApplicationUtil.printBanner("ADD NOMINEE");

        try {
            System.out.print("  Enter Nominee Name    : ");
            String name = scanner.nextLine().trim();

            System.out.print("  Enter Constitution    : ");
            String constitution = scanner.nextLine().trim();

            System.out.print("  Enter District        : ");
            String district = scanner.nextLine().trim();

            System.out.print("  Enter Party Symbol    : ");
            String symbol = scanner.nextLine().trim();

            System.out.print("  Enter Address         : ");
            String address = scanner.nextLine().trim();

            Nominee newNominee = nomineeService.addNominee(name, constitution, district, symbol, address);

            ApplicationUtil.printSuccess("Nominee registered successfully!");
            System.out.println("  Generated Nominee ID: " + newNominee.getNomineeId());

        } catch (IllegalArgumentException e) {
            ApplicationUtil.printError(e.getMessage());
        } catch (SQLException e) {
            ApplicationUtil.printError("Database error: " + e.getMessage());
        }
    }

   
    private static void handleUpdateNomineeAddress() {
        System.out.println();
        ApplicationUtil.printBanner("UPDATE NOMINEE ADDRESS");

        System.out.print("  Enter Nominee ID     : ");
        int nomineeId =scanner.nextInt();
        scanner.nextLine();

        System.out.print("  Enter New Address    : ");
        String address = scanner.nextLine().trim();

        try {
            nomineeService.updateNomineeAddress(nomineeId, address);
            ApplicationUtil.printSuccess("Address updated for Nominee ID: " + nomineeId);
        } catch (IllegalArgumentException e) {
            ApplicationUtil.printError(e.getMessage());
        } catch (SQLException e) {
            ApplicationUtil.printError("Database error: " + e.getMessage());
        }
    }

   
    private static void handleSearchNomineeById() {
        System.out.println();
        ApplicationUtil.printBanner("SEARCH NOMINEE BY ID");

        System.out.print("  Enter Nominee ID: ");
        int nomineeId = scanner.nextInt();
        scanner.nextLine();

        try {
            Nominee nominee = nomineeService.searchNomineeById(nomineeId);
            System.out.println();
            ApplicationUtil.printDivider();
            System.out.println("  NOMINEE DETAILS");
            ApplicationUtil.printDivider();
            System.out.println(nominee);
            ApplicationUtil.printDivider();
        } catch (IllegalArgumentException e) {
            ApplicationUtil.printError(e.getMessage());
        } catch (SQLException e) {
            ApplicationUtil.printError("Database error: " + e.getMessage());
        }
    }

    
    private static void handleDeleteNominee() {
        System.out.println();
        ApplicationUtil.printBanner("DELETE NOMINEE");

        System.out.print("  Enter Nominee ID to Delete: ");
        int nomineeId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("  Are you sure you want to delete Nominee ID " + nomineeId + "? (yes/no): ");
        String confirm = scanner.nextLine().trim();

        if (!"yes".equalsIgnoreCase(confirm)) {
            ApplicationUtil.printInfo("Delete operation cancelled.");
            return;
        }

        try {
            nomineeService.deleteNominee(nomineeId);
            ApplicationUtil.printSuccess("Nominee ID " + nomineeId + " deleted successfully.");
        } catch (IllegalArgumentException e) {
            ApplicationUtil.printError(e.getMessage());
        } catch (SQLException e) {
            ApplicationUtil.printError("Database error: " + e.getMessage());
        }
    }
    
    private static void handleViewNomineesByDistrict() {
        System.out.println();
        ApplicationUtil.printBanner("NOMINEES BY DISTRICT");

        System.out.print("  Enter District Name: ");
        String district = scanner.nextLine().trim();

        try {
            List<Nominee> nominees = nomineeService.getNomineesByDistrict(district);
            if (nominees.isEmpty()) {
                ApplicationUtil.printInfo("No nominees found in district: " + district);
                return;
            }
            System.out.println("\n  Total Nominees in '" + district + "': " + nominees.size());
            ApplicationUtil.printDivider();
            for (int i = 0; i < nominees.size(); i++) {
                System.out.println("  [" + (i + 1) + "]" + nominees.get(i));
                ApplicationUtil.printDivider();
            }
        } catch (SQLException e) {
            ApplicationUtil.printError("Database error: " + e.getMessage());
        }
    }
    
    
    // vote module
    private static void displayVoteMenu() {
        int choice;
        do {
            System.out.println();
            ApplicationUtil.printSeparator();
            System.out.println("               VOTE MODULE");
            ApplicationUtil.printSeparator();
            System.out.println("  1. Login and Cast Vote");
            System.out.println("  2. Back to Main Menu");
            ApplicationUtil.printSeparator();
            System.out.print("  Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: handleLoginAndCastVote(); break;
                case 2: System.out.println("  Returning to main menu..."); break;
                default: ApplicationUtil.printError("Invalid option. Please enter 1 or 2.");
            }
        } while (choice != 2);
    }
    
    private static void handleLoginAndCastVote() {
        System.out.println();
        ApplicationUtil.printBanner("VOTER LOGIN");

        System.out.print("  Enter Login ID  : ");
        String loginId = scanner.nextLine().trim();

        System.out.print("  Enter Password  : ");
        String password = scanner.nextLine().trim();

        Voter voter;
        try {
            voter = voteService.authenticateVoter(loginId, password);
        } catch (InvalidVoterException e) {
            ApplicationUtil.printError(e.getMessage());
            return;
        } catch (SQLException e) {
            ApplicationUtil.printError("Database error during login: " + e.getMessage());
            return;
        }

       
        ApplicationUtil.printSuccess("Login successful! Welcome, " + voter.getVoterName() + ".");
        System.out.println("  Voter ID : " + voter.getVoterId());
        System.out.println("  District : " + voter.getDistrict());

       
        try {
            if (voteService.hasVoterAlreadyVoted(voter.getVoterId())) {
                ApplicationUtil.printError("You have already cast your vote. Each voter may vote only once.");
                return;
            }
        } catch (SQLException e) {
            ApplicationUtil.printError("Error checking vote status: " + e.getMessage());
            return;
        }

       
        System.out.println();
        ApplicationUtil.printBanner("NOMINEES IN YOUR DISTRICT: " + voter.getDistrict());

        List<Nominee> nominees;
        try {
            nominees = nomineeService.getNomineesByDistrict(voter.getDistrict());
        } catch (SQLException e) {
            ApplicationUtil.printError("Error loading nominees: " + e.getMessage());
            return;
        }

        if (nominees.isEmpty()) {
            ApplicationUtil.printInfo("No nominees are registered for district: " + voter.getDistrict());
            return;
        }

       
        System.out.println();
        System.out.printf("  %-5s %-10s %-20s %-15s %-15s%n",
                          "No.", "ID", "Name", "Constitution", "Symbol");
        ApplicationUtil.printDivider();
        for (int i = 0; i < nominees.size(); i++) {
            Nominee n = nominees.get(i);
            System.out.printf("  %-5d %-10d %-20s %-15s %-15s%n",
                              (i + 1), n.getNomineeId(), n.getNomineeName(),
                              n.getConstitution(), n.getSymbol());
        }
        ApplicationUtil.printDivider();

    
        System.out.print("  Enter Nominee ID to cast your vote: ");
        int nomineeId = scanner.nextInt();
        scanner.nextLine();

        
        System.out.print("  Confirm your vote for Nominee ID " + nomineeId + "? (yes/no): ");
        String confirm = scanner.nextLine().trim();

        if (!"yes".equalsIgnoreCase(confirm)) {
            ApplicationUtil.printInfo("Vote cancelled. You may try again.");
            return;
        }

        
        try {
            Vote vote = voteService.castVote(voter, nomineeId);
            System.out.println();
            ApplicationUtil.printSuccess("Vote cast successfully!");
            ApplicationUtil.printDivider();
            System.out.println("  Vote ID      : " + vote.getVoteId());
            System.out.println("  Voter        : " + voter.getVoterName() + " (" + voter.getVoterId() + ")");
            System.out.println("  Nominee ID   : " + vote.getNomineeId());
            System.out.println("  Date         : " + vote.getVotedDate());
            ApplicationUtil.printDivider();
            ApplicationUtil.printInfo("Thank you for exercising your democratic right!");
        } catch (InvalidVoterException e) {
            ApplicationUtil.printError(e.getMessage());
        } catch (SQLException e) {
            ApplicationUtil.printError("Database error while casting vote: " + e.getMessage());
        }
    }
    
    
    //Result module
    private static void displayResultMenu() {
        int choice;
        do {
            System.out.println();
            ApplicationUtil.printSeparator();
            System.out.println("               RESULT MODULE");
            ApplicationUtil.printSeparator();
            System.out.println("  1. Voting Percentage By District");
            System.out.println("  2. Party Wise Vote Count");
            System.out.println("  3. District Wise Nominee List");
            System.out.println("  4. Back to Main Menu");
            ApplicationUtil.printSeparator();
            System.out.print("  Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: handleDistrictVotingPercentage(); break;
                case 2: handlePartyWiseVoteCount();       break;
                case 3: handleDistrictWiseNomineeList();  break;
                case 4: System.out.println("  Returning to main menu..."); break;
                default: ApplicationUtil.printError("Invalid option. Please enter 1–4.");
            }
        } while (choice != 4);
    }
   
    private static void handleDistrictVotingPercentage() {
        System.out.println();
        ApplicationUtil.printBanner("VOTING PERCENTAGE BY DISTRICT");

        try {
            Map<String, int[]> details     = voteService.getDistrictVotingDetails();
            Map<String, Double> percentage = voteService.getDistrictVotingPercentage();

            if (details.isEmpty()) {
                ApplicationUtil.printInfo("No voting data available yet.");
                return;
            }

            System.out.printf("%n  %-20s %-15s %-15s %-15s%n",
                              "District", "Total Voters", "Votes Cast", "Percentage");
            ApplicationUtil.printDivider();

            for (Map.Entry<String, int[]> entry : details.entrySet()) {
                String district  = entry.getKey();
                int totalVoters  = entry.getValue()[0];
                int votesCast    = entry.getValue()[1];
                double pct       = percentage.getOrDefault(district, 0.0);

                System.out.printf("  %-20s %-15d %-15d %-15.2f%%%n",
                                  district, totalVoters, votesCast, pct);
            }
            ApplicationUtil.printDivider();
            System.out.println("  Total Votes Cast in System: " + voteService.getTotalVotesCast());

        } catch (SQLException e) {
            ApplicationUtil.printError("Database error: " + e.getMessage());
        }
    }
    
    private static void handlePartyWiseVoteCount() {
        System.out.println();
        ApplicationUtil.printBanner("PARTY WISE VOTE COUNT");

        try {
            Map<String, Integer> partyVotes = nomineeService.getPartyWiseVoteCount();

            if (partyVotes.isEmpty()) {
                ApplicationUtil.printInfo("No vote data available.");
                return;
            }

            System.out.printf("%n  %-25s %-15s%n", "Party Symbol", "Total Votes");
            ApplicationUtil.printDivider();

            
            partyVotes.entrySet()
                      .stream()
                      .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                      .forEach(entry ->
                          System.out.printf("  %-25s %-15d%n",
                                            entry.getKey(), entry.getValue()));

            ApplicationUtil.printDivider();

        } catch (SQLException e) {
            ApplicationUtil.printError("Database error: " + e.getMessage());
        }
    }
    
    private static void handleDistrictWiseNomineeList() {
        System.out.println();
        ApplicationUtil.printBanner("DISTRICT WISE NOMINEE LIST");

        try {
            List<Nominee> nominees = nomineeService.getAllNominees();

            if (nominees.isEmpty()) {
                ApplicationUtil.printInfo("No nominees registered in the system.");
                return;
            }

            String currentDistrict = "";
            for (Nominee n : nominees) {
                // Print district header when it changes
                if (!n.getDistrict().equals(currentDistrict)) {
                    currentDistrict = n.getDistrict();
                    System.out.println();
                    System.out.println("  District: " + currentDistrict.toUpperCase());
                    ApplicationUtil.printDivider();
                    System.out.printf("  %-10s %-20s %-15s %-12s %-10s%n",
                                     "ID", "Name", "Constitution", "Symbol", "Votes");
                    ApplicationUtil.printDivider();
                }
                System.out.printf("  %-10d %-20s %-15s %-12s %-10d%n",
                                  n.getNomineeId(), n.getNomineeName(),
                                  n.getConstitution(), n.getSymbol(), n.getVoteCount());
            }
            ApplicationUtil.printDivider();

        } catch (SQLException e) {
            ApplicationUtil.printError("Database error: " + e.getMessage());
        }
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
}
