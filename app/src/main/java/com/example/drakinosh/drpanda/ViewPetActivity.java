package com.example.drakinosh.drpanda;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ViewPetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet);

        // get pid from intent
        final Integer pid = getIntent().getIntExtra("PET_ID", 1);

        String DBASE_NAME = "panda_db";
        //WARNING: allowed main thread queries
        //future me: see - https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread

        final AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DBASE_NAME).allowMainThreadQueries().build();
        final PetDao petDao = appDatabase.getPetDao();
        final PetDateDao petDateDao = appDatabase.getPetDateDao();

        //Get single profile
        Pet tempPet = petDao.getPetById(1);



        // Add new dates to database
        final EditText pdDate = findViewById(R.id.et_petdate_value);
        final EditText pdType = findViewById(R.id.et_petdate_type);
        Button pdBut = findViewById(R.id.but_petdate_add);

        pdBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String petdate_val = pdDate.getText().toString();
                String petdate_type = pdType.getText().toString();

                PetDate tempPd = new PetDate();
                tempPd.setPetDateValue(petdate_val);
                tempPd.setPetDateType(petdate_type);
                tempPd.setPetDatePid(pid);      // need to set this, or tempPd won't be stored
                //tempPet.setPid(1);

                petDateDao.insertPetDate(tempPd);
            }
        });



        LinearLayout lin_lay = findViewById(R.id.pet_lin_layout);

        // Display pet Id (add names and pics later)
        TextView petName = new TextView(this);
        petName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        petName.setText(tempPet.getPetName());
        lin_lay.addView(petName);


        // display list of dates
        List<PetDate> petDates = petDateDao.getPetDatesByPId(pid);

        for (PetDate pdate : petDates) {
            TextView petDateText = new TextView(this);
            LinearLayout.LayoutParams dim = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            petDateText.setLayoutParams(dim);
            petDateText.setText(pdate.getPetDateValue());
            lin_lay.addView(petDateText);
        }


    }
}
