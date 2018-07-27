package com.example.drakinosh.drpanda;

import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
//import com.example.drakinosh.drpanda.AppDatabase;


public class CreateFormActivity extends AppCompatActivity {

    private static int RESULT_LOADIMG = 1;
    public static String petImagePath = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        String DBASE_NAME = "panda_db";

        String pathName = "";

        //WARNING: allowed main thread queries
        //future me: see - https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread


        // fall back to destructive migration
        final AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DBASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        final PetDao petDao = appDatabase.getPetDao();

        // Datepicker calender
        final Calendar myCalendar = Calendar.getInstance();


        Button etCreateBut = findViewById(R.id.et_create_but);
        Button etImageAdd = findViewById(R.id.et_imageadd_but);
        final EditText etPetName = findViewById(R.id.et_pet_name);
        final EditText etPetBreed = findViewById(R.id.et_pet_breed);
        final EditText etPetGender = findViewById(R.id.et_pet_gender);
        final EditText etPetDOB = findViewById(R.id.et_pet_dob);


        //date picker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(etPetDOB, myCalendar);
            }
        };


        //date picker on click, dob
        etPetDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateFormActivity.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        //select image from gallery
        etImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(mIntent, RESULT_LOADIMG);
            }
        });

        etCreateBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String pet_name = etPetName.getText().toString();
                String pet_breed = etPetBreed.getText().toString();
                String pet_gender = etPetGender.getText().toString();
                String pet_dob = etPetDOB.getText().toString();

                Pet tempPet = new Pet();
                tempPet.setPetBreed(pet_breed);
                tempPet.setPetName(pet_name);
                tempPet.setPetGender(pet_gender);
                tempPet.setPetDOB(pet_dob);
                tempPet.setImagePath(petImagePath);
                //tempPet.setPid(1);

                petDao.insertPet(tempPet);

                //go back
                finish();
            }
        });





    }

    private void updateLabel(EditText dateVal, Calendar myCalendar) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        dateVal.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dIntent) {
        super.onActivityResult(requestCode, resultCode, dIntent);
        if (requestCode == RESULT_LOADIMG && resultCode == RESULT_OK && null != dIntent) {
            Uri petImage = dIntent.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(petImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int colIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picPath = cursor.getString(colIndex);
            petImagePath = picPath;
            cursor.close();

        }
    }
}