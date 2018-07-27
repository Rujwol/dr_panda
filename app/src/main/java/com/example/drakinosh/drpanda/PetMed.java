package com.example.drakinosh.drpanda;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;


@Entity
public class PetMed {

    @PrimaryKey(autoGenerate = true)
    private int mid;

    @ColumnInfo(name = "pet_med_pid")
    private int petMedPid;

    @ColumnInfo(name = "pet_med_name")
    private String petMedName;

    @ColumnInfo(name = "pet_med_dosage")
    private String petMedDosage;

    @ColumnInfo(name = "pet_med_weeks")
    private int petMedWeeks;

    @ColumnInfo(name = "pet_med_days")
    private int petMedDays;

    @ColumnInfo(name = "pet_med_start_date")
    private String petMedStartDate;

    //@ColumnInfo(name = "pet_med_stop_date")
    //private String petMedStopDate;


    public void setMid(int mid) { this.mid = mid; }
    public int getMid() { return this.mid; }

    public void setPetMedPid(int petMedPid) { this.petMedPid = petMedPid; }
    public int getPetMedPid() { return petMedPid; }

    public void setPetMedName(String petMedName) { this.petMedName = petMedName; }
    public String getPetMedName() { return petMedName; }

    public void setPetMedDosage(String petMedDosage) { this.petMedDosage = petMedDosage; }
    public String getPetMedDosage() { return petMedDosage; }

    public void setPetMedWeeks(int petMedWeeks) { this.petMedWeeks = petMedWeeks; }
    public int getPetMedWeeks(){ return petMedWeeks; }

    public void setPetMedDays(int petMedDays) { this.petMedDays = petMedDays; }
    public int getPetMedDays() { return petMedDays; }

    public void setPetMedStartDate(String petMedStartDate) { this.petMedStartDate = petMedStartDate; }
    public String getPetMedStartDate() { return petMedStartDate; }

    /*
    public void setPetMedStopDate(String petMedStopDate) { this.petMedStopDate = petMedStopDate; }
    public String getPetMedStopDate(){ return petMedStopDate; }
    */

}
