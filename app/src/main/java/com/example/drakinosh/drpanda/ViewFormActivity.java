package com.example.drakinosh.drpanda;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);

        String DBASE_NAME = "panda_db";
        //WARNING: allowed main thread queries
        //future me: see - https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread

        final AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DBASE_NAME).allowMainThreadQueries().build();
        final PetDao petDao = appDatabase.getPetDao();

        //Get single profile
        Pet tempPet = petDao.getPetById(1);

        //Display one profile
        LinearLayout lin_lay = findViewById(R.id.view_lin_layout);

        //single, dynamically generated textview for now
        TextView petText = new TextView(this);
        LinearLayout.LayoutParams dim = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        petText.setLayoutParams(dim);
        petText.setText(tempPet.getPetName());
        lin_lay.addView(petText);


    }
}
