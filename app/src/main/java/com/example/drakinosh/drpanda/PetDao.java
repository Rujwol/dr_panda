package com.example.drakinosh.drpanda;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;


@Dao
public interface PetDao {

    @Query("SELECT * FROM pet")
    List<Pet> getAll();

    @Query("SELECT * FROM pet WHERE pid in (:petIds)")
    List<Pet> loadAllByIds(int[] petIds);

    @Insert
    void insertAll(Pet... pets);

    @Delete
    void delete(Pet pet);

}
