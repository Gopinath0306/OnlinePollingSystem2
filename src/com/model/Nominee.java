package com.model;

/**
 * Model class representing a Nominee entity.
 * Maps to the 'nominee' table in the database.
 *
 * @author Senior Java Developer
 * @version 1.0
 */
public class Nominee {

    // -------------------------------------------------------
    // Fields (mapped to DB columns)
    // -------------------------------------------------------
    private int    nomineeId;    // nominee_id   INT PK
    private String nomineeName;  // nominee_name VARCHAR(25)
    private String constitution; // constitution VARCHAR(30)
    private String district;     // district     VARCHAR(50)
    private String symbol;       // symbol       VARCHAR(50)  (party symbol)
    private String address;      // address      VARCHAR(100)
    private int    voteCount;    // vote_count   INT

    // -------------------------------------------------------
    // Default Constructor
    // -------------------------------------------------------
    public Nominee() {
    }

    // -------------------------------------------------------
    // Parameterized Constructor
    // -------------------------------------------------------
    public Nominee(int nomineeId, String nomineeName, String constitution,
                   String district, String symbol, String address, int voteCount) {
        this.nomineeId    = nomineeId;
        this.nomineeName  = nomineeName;
        this.constitution = constitution;
        this.district     = district;
        this.symbol       = symbol;
        this.address      = address;
        this.voteCount    = voteCount;
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------
    public int getNomineeId() {
        return nomineeId;
    }

    public void setNomineeId(int nomineeId) {
        this.nomineeId = nomineeId;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    // -------------------------------------------------------
    // toString – for display purposes
    // -------------------------------------------------------
    @Override
    public String toString() {
        return "\n  Nominee ID    : " + nomineeId
             + "\n  Name          : " + nomineeName
             + "\n  Constitution  : " + constitution
             + "\n  District      : " + district
             + "\n  Party Symbol  : " + symbol
             + "\n  Address       : " + address
             + "\n  Vote Count    : " + voteCount;
    }
}
