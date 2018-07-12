package com.example.drakinosh.drpanda;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Pet.class, PetDate.class}, version=2)
public abstract class AppDatabase extends RoomDatabase{
    public abstract PetDao getPetDao();
    public abstract PetDateDao getPetDateDao();
}
