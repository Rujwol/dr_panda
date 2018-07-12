package com.example.drakinosh.drpanda;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import com.example.drakinosh.drpanda.AppDatabase;


public class CreateFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        String DBASE_NAME = "panda_db";

        //WARNING: allowed main thread queries
        //future me: see - https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread


        final AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DBASE_NAME).allowMainThreadQueries().build();
        final PetDao petDao = appDatabase.getPetDao();

        Button etCreateBut = findViewById(R.id.et_create_but);
        final EditText etPetName = findViewById(R.id.et_pet_name);
        final EditText etPetBreed = findViewById(R.id.et_pet_breed);

        etCreateBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String pet_name = etPetName.getText().toString();
                String pet_breed = etPetBreed.getText().toString();

                Pet tempPet = new Pet();
                tempPet.setPetBreed(pet_breed);
                tempPet.setPetName(pet_name);
                //tempPet.setPid(1);

                petDao.insertPet(tempPet);
            }
        });

    }
}