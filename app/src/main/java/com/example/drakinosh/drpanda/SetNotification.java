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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SetNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_notification);

        final Integer pid = getIntent().getIntExtra("PET_ID", 1);

        String DBASE_NAME = "panda_db";

        //WARNING: allowed main thread queries
        //future me: see - https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread


        final AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DBASE_NAME).allowMainThreadQueries().build();
        final PetDao petDao = appDatabase.getPetDao();

        final DateFormat datetime_form = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        // Create datepicker
        final Calendar myCalendar = Calendar.getInstance();


        final EditText dateVal = findViewById(R.id.set_noti_date_value);
        final EditText dateType = findViewById(R.id.set_noti_date_type);
        final EditText time = findViewById(R.id.set_noti_time);
        Button addNotification  = findViewById(R.id.but_noti_add);

        //date picker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(dateVal, myCalendar);
            }
        };

        dateVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SetNotification.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        // notifications
        addNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // first, convert the given data into a notification
                String s = dateVal.getText().toString() + " " + time.getText().toString();
                try {
                    Date date = datetime_form.parse(s); //ignore ParseException
                    Date current = new Date(System.currentTimeMillis());
                    Pet myPet = petDao.getPetById(pid);
                    String petName = myPet.getPetName();
                    String checkType = dateType.getText().toString();

                    long diff = date.getTime() - current.getTime();

                    int REQUEST_CODE = 0;       // need to make this unqiue; Will do later;

                    //move onto next activity with message

                    // create notification
                    Notification.Builder builder = new Notification.Builder(SetNotification.this);
                    builder.setContentTitle("DrPanda: Scheduled Notification");
                    builder.setContentText("Scheduled " + checkType + " for " + petName );
                    builder.setSmallIcon(R.drawable.ic_launcher_background);

                    // set vibration
                    builder.setVibrate(new long[]{ 1000, 1000, 1000 ,1000});
                    Notification mNoti = builder.build();


                    // set it
                    // spelling mistake bhayechha.
                    Intent yaruki = new Intent(SetNotification.this, NotiReciever.class);
                    yaruki.putExtra(NotiReciever.NOTIFICATION_ID, 1);
                    yaruki.putExtra(NotiReciever.NOTIFICATION, mNoti);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(SetNotification.this,
                                        REQUEST_CODE, yaruki, PendingIntent.FLAG_UPDATE_CURRENT);

                    long futureInMillis = SystemClock.elapsedRealtime() + diff;
                    AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);


                    // add a little alrt
                    AlertDialog alertDialog = new AlertDialog.Builder(SetNotification.this).create();
                    alertDialog.setTitle("alert");
                    alertDialog.setMessage("Notification added for " + diff + " millisecs");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.show();


                } catch (Exception e) {
                    ;
                }

            }
        });

    }

    private void updateLabel(EditText dateVal, Calendar myCalendar) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        dateVal.setText(sdf.format(myCalendar.getTime()));

    }

}