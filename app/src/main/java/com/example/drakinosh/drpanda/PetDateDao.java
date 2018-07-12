package com.example.drakinosh.drpanda;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;


@Dao
public interface PetDateDao {


    // associated pids
    @Query("SELECT * FROM petdate WHERE pet_date_pid = :petId")
    List<PetDate> getPetDatesByPId(int  petId);

    @Query("SELECT * FROM petdate WHERE did = :petDateId")
    PetDate getPetDateById(int petDateId);

    /*
    @Query("SELECT * FROM petdate WHERE pet_date_pid in (:petIds)")
    List<Pet> loadAllByIds(int[] petIds);
    */

    @Insert
    void insertAll(PetDate... petDates);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPetDate(PetDate petDate);

    @Delete
    void delete(PetDate petDate);

}
