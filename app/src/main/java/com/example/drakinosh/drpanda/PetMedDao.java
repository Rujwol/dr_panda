package com.example.drakinosh.drpanda;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;

import java.util.List;


@Dao
public interface PetMedDao {


    // associated pids
    @Query("SELECT * FROM petmed WHERE pet_med_pid = :petId")
    List<PetMed> getPetMedsByPId(int  petId);

    @Query("SELECT * FROM petmed WHERE mid = :petMedId")
    PetMed getPetMedById(int petMedId);

    @Insert
    void insertAll(PetMed... petMeds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPetMed(PetMed petMed);

    @Delete
    void delete(PetMed petMed);

}
