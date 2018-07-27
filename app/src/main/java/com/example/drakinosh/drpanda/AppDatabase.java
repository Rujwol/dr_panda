package com.example.drakinosh.drpanda;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Pet.class, PetDate.class, PetMed.class}, version=5)
public abstract class AppDatabase extends RoomDatabase{
    public abstract PetDao getPetDao();
    public abstract PetDateDao getPetDateDao();
    public abstract PetMedDao getPetMedDao();

}
