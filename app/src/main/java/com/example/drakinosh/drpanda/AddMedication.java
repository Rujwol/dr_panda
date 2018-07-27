package com.example.drakinosh.drpanda;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddMedication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medication);

        // get pid from intent
        final Integer pid = getIntent().getIntExtra("PET_ID", 1);

        String DBASE_NAME = "panda_db";
        //WARNING: allowed main thread queries
        //future me: see - https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread

        final AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DBASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        final PetMedDao petMedDao = appDatabase.getPetMedDao();

        final Calendar myCalendar = Calendar.getInstance();
        final DateFormat datetime_form = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);


        final EditText etMedName = findViewById(R.id.et_med_name);
        final EditText etMedDosage = findViewById(R.id.et_med_dosage);
        final EditText etMedStartDate = findViewById(R.id.et_med_startdate);
        final EditText etMedWeeks = findViewById(R.id.et_med_weeks);
        final EditText etMedTime = findViewById(R.id.et_med_time);
        final EditText etMedDays = findViewById(R.id.et_med_days);
        Button addMed = findViewById(R.id.add_medication_but);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(etMedStartDate, myCalendar);
            }
        };

        etMedStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMedication.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // first, convert the given data into a notification
                String s = etMedStartDate.getText().toString() + " " + etMedTime.getText().toString();
                try {
                    Date date = datetime_form.parse(s); //ignore ParseException


                    //Date current = new Date(System.currentTimeMillis());

                    // insert new PetMed into database (table petmed)
                    PetMed petMed = new PetMed();
                    petMed.setPetMedPid(pid);
                    petMed.setPetMedName(etMedName.getText().toString());
                    petMed.setPetMedDosage(etMedDosage.getText().toString());
                    petMed.setPetMedWeeks(Integer.parseInt(etMedWeeks.getText().toString()));
                    petMed.setPetMedDays(Integer.parseInt(etMedDays.getText().toString()));
                    petMed.setPetMedStartDate(etMedStartDate.getText().toString());

                    petMedDao.insertPetMed(petMed);

                    // add a little alrt
                    AlertDialog alertDialog = new AlertDialog.Builder(AddMedication.this).create();
                    alertDialog.setTitle("alert");
                    alertDialog.setMessage("Medication added");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.show();
                    //end

                    //long diff = date.getTime() - current.getTime();

                    int REQUEST_CODE = NotificationId.getRequestCode();

                    //move onto next activity with message

                    // create notification
                    Notification.Builder builder = new Notification.Builder(AddMedication.this);
                    builder.setContentTitle("DrPanda: Scheduled Medical Notification");
                    builder.setContentText("");
                    builder.setSmallIcon(R.drawable.ic_launcher_background);

                    // set vibration
                    builder.setVibrate(new long[]{ 1000, 1000, 1000 ,1000});
                    Notification mNoti = builder.build();


                    // set it
                    // spelling mistake bhayechha.
                    Intent yaruki = new Intent(AddMedication.this, NotiReciever.class);
                    yaruki.putExtra(NotiReciever.NOTIFICATION_ID, NotificationId.getId())
                    ;
                    yaruki.putExtra(NotiReciever.NOTIFICATION, mNoti);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AddMedication.this,
                            REQUEST_CODE, yaruki, PendingIntent.FLAG_UPDATE_CURRENT);

                    //long futureInMillis = SystemClock.elapsedRealtime() + diff;
                    int days = Integer.parseInt(etMedDays.toString());
                    AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    am.setRepeating(AlarmManager.RTC, date.getTime(), days * 1000 * 60 * 60 * 24, pendingIntent);
                    // note: need to cancel after weeks are up




                } catch (Exception e) {
                    ;
                }

            }
        });




    }
    private void updateLabel(EditText startDate, Calendar myCalendar) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        startDate.setText(sdf.format(myCalendar.getTime()));

    }
}
