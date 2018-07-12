package com.example.drakinosh.drpanda;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;


@Entity
public class PetDate {

    @PrimaryKey(autoGenerate = true)
    private int did;

    @ColumnInfo(name = "pet_date_pid")
    private int petDatePid;

    @ColumnInfo(name = "pet_date_value")
    private String petDateValue; // convert on storing

    @ColumnInfo(name = "pet_date_type")
    private String petDateType; // Check or Vacc

    public void setPetDatePid(int petDatePid) {
        this.petDatePid = petDatePid;
    }

    public int getPetDatePid() {
        return petDatePid;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getDid() {
        return did;
    }

    public void setPetDateValue(String petDateValue) {
        this.petDateValue = petDateValue;
    }

    public String getPetDateValue() {
        return petDateValue;
    }

    public void setPetDateType(String petDateType) {
        this.petDateType = petDateType;
    }

    public String getPetDateType() {
        return petDateType;
    }


}
