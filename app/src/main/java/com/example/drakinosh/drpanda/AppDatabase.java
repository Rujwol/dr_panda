package com.example.drakinosh.drpanda;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Pet.class}, version=1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract PetDao getPetDao();
}
