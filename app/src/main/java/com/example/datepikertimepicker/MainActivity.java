package com.example.datepikertimepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView txtDateTime;
    Button btnTime, btnDate;
    EditText edtSeconds;
    Button btnStartTimer;
    Calendar dateTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtDateTime = findViewById(R.id.txtDateTime);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        edtSeconds = findViewById(R.id.edtSeconds);
        btnStartTimer = findViewById(R.id.btnStartTimer);

        btnStartTimer.setOnClickListener(view -> {
            int seconds = Integer.parseInt(edtSeconds.getText().toString());

            Intent intent = new Intent(MainActivity.this, Alarm.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + seconds * 1000L,
                    PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0));
        });

        //Установка начальной даты и времени
        txtDateTime.setText(DateUtils.formatDateTime(this,
                dateTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));

        btnDate.setOnClickListener(view -> new DatePickerDialog(MainActivity.this, d,
                dateTime.get(Calendar.YEAR),
                dateTime.get(Calendar.MONTH),
                dateTime.get(Calendar.DAY_OF_MONTH))
                .show());

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(MainActivity.this, t,
                        dateTime.get(Calendar.HOUR_OF_DAY),
                        dateTime.get(Calendar.MINUTE), true)
                        .show();
            }
        });
    }


    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourofday, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourofday);
            dateTime.set(Calendar.MINUTE, minute);
            txtDateTime.setText(DateUtils.formatDateTime(getApplicationContext(),
                    dateTime.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                            | DateUtils.FORMAT_SHOW_TIME));
        }
    };

    //установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthofyear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthofyear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            txtDateTime.setText(DateUtils.formatDateTime(getApplicationContext(),
                    dateTime.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                            | DateUtils.FORMAT_SHOW_TIME));
        }
    };
}





