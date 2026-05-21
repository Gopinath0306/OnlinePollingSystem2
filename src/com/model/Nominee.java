package com.model;


public class Nominee {

   
    private int    nomineeId;    
    private String nomineeName;  
    private String constitution; 
    private String district;     
    private String symbol;       
    private String address;      
    private int    voteCount;    

   
    public Nominee() {
    }

    
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
