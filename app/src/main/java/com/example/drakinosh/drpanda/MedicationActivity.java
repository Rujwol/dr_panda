package com.example.drakinosh.drpanda;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MedicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        // get pid from intent
        final Integer pid = getIntent().getIntExtra("PET_ID", 1);

        String DBASE_NAME = "panda_db";
        //WARNING: allowed main thread queries
        //future me: see - https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread

        final AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DBASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        final PetDao petDao = appDatabase.getPetDao();
        final PetMedDao petMedDao = appDatabase.getPetMedDao();

        Button addMedBut = findViewById(R.id.add_medication_but);

        addMedBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MedicationActivity.this, AddMedication.class);
                myIntent.putExtra("PET_ID", pid);
                startActivity(myIntent);
            }
        });

        List<PetMed> petMeds = petMedDao.getPetMedsByPId(pid);
        LinearLayout myLinlay = findViewById(R.id.medi_lin_layout);

        for (PetMed petMed : petMeds) {
            TextView petMedText = new TextView(this);
            LinearLayout.LayoutParams dim = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            petMedText.setLayoutParams(dim);
            petMedText.setText("Medicine " + petMed.getPetMedName() + " for " + petMed.getPetMedWeeks()
                                + " weeks, " + petMed.getPetMedDosage() + " dosage every " + petMed.getPetMedDays()
                                + " days.");
            myLinlay.addView(petMedText);
        }


    }
}