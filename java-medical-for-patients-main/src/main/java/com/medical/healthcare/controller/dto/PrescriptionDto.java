package com.medical.healthcare.controller.dto;

import java.sql.Time;
import java.util.Date;

public class PrescriptionDto {

    private Date date;

    private String drugName;

    private String identificationNumber;

    public PrescriptionDto() {
    }

    public PrescriptionDto(Date date, String drugName, String identificationNumber) {
        this.date = date;
        this.drugName = drugName;
        this.identificationNumber = identificationNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }
}
