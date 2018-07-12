package com.example.drakinosh.drpanda;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;


@Entity
public class Pet {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    @ColumnInfo(name = "pet_name")
    private String petName;

    @ColumnInfo(name = "pet_breed")
    private String petBreed;

    @ColumnInfo(name = "pet_dob")
    private String petDOB;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetDOB(String petDOB) { this.petDOB = petDOB;}

    public String getPetDOB() { return petDOB; }

}
