package com.model;

import java.sql.Date;

/**
 * Model class representing a Vote entity.
 * Maps to the 'vote' table in the database.
 *
 * @author Senior Java Developer
 * @version 1.0
 */
public class Vote {

    // -------------------------------------------------------
    // Fields (mapped to DB columns)
    // -------------------------------------------------------
    private int    voteId;     // vote_id    INT PK
    private String voterId;    // voter_id   VARCHAR(25) FK → voter
    private int    nomineeId;  // nominee_id INT FK → nominee
    private Date   votedDate;  // voted_date DATE

    // -------------------------------------------------------
    // Default Constructor
    // -------------------------------------------------------
    public Vote() {
    }

    // -------------------------------------------------------
    // Parameterized Constructor
    // -------------------------------------------------------
    public Vote(int voteId, String voterId, int nomineeId, Date votedDate) {
        this.voteId    = voteId;
        this.voterId   = voterId;
        this.nomineeId = nomineeId;
        this.votedDate = votedDate;
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------
    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public int getNomineeId() {
        return nomineeId;
    }

    public void setNomineeId(int nomineeId) {
        this.nomineeId = nomineeId;
    }

    public Date getVotedDate() {
        return votedDate;
    }

    public void setVotedDate(Date votedDate) {
        this.votedDate = votedDate;
    }

    // -------------------------------------------------------
    // toString
    // -------------------------------------------------------
    @Override
    public String toString() {
        return "\n  Vote ID       : " + voteId
             + "\n  Voter ID      : " + voterId
             + "\n  Nominee ID    : " + nomineeId
             + "\n  Voted Date    : " + votedDate;
    }
}
