package com.ais.eduworld.model;

public class CalendarModel {
    private String Ntitle;
    private String NNAME;
    private String ASSIGNMENT;
    private String STARTDATE;

    public String getNtitle() {
        return Ntitle;
    }

    public void setNtitle(String ntitle) {
        Ntitle = ntitle;
    }

    public String getNNAME() {
        return NNAME;
    }

    public void setNNAME(String NNAME) {
        this.NNAME = NNAME;
    }

    public String getASSIGNMENT() {
        return ASSIGNMENT;
    }

    public void setASSIGNMENT(String ASSIGNMENT) {
        this.ASSIGNMENT = ASSIGNMENT;
    }

    public String getSTARTDATE() {
        return STARTDATE;
    }

    public void setSTARTDATE(String STARTDATE) {
        this.STARTDATE = STARTDATE;
    }
}
