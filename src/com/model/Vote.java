package com.model;

import java.sql.Date;


public class Vote {

    
    private int    voteId;     
    private String voterId;    
    private int    nomineeId;  
    private Date   votedDate;  

       public Vote() {
    }

   
    public Vote(int voteId, String voterId, int nomineeId, Date votedDate) {
        this.voteId    = voteId;
        this.voterId   = voterId;
        this.nomineeId = nomineeId;
        this.votedDate = votedDate;
    }

    
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

    @Override
    public String toString() {
        return "\n  Vote ID       : " + voteId
             + "\n  Voter ID      : " + voterId
             + "\n  Nominee ID    : " + nomineeId
             + "\n  Voted Date    : " + votedDate;
    }
}
