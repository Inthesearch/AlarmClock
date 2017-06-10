package com.example.goku.alarmclock;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class add_Alarm extends AppCompatActivity {
    int date, month, year, hour, minute1;

    ArrayList<ParentBean> parentlist;
    ArrayList<ChildBean> childlist;
    String song;
    boolean vibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        get_time_and_date();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_alarm();
                ParentBean pb = new ParentBean(hour, minute1, date, month, year, true);
                 ChildBean cb = new ChildBean(song, vibrate );
                pb.toString();
                cb.toString();
            }
        });
    }

    public void get_time_and_date(){
        Calendar calendar = Calendar.getInstance();
         date = calendar.get(Calendar.DAY_OF_MONTH);
         month = calendar.get(Calendar.MONTH);
         year = calendar.get(Calendar.YEAR);
         hour = calendar.get(Calendar.HOUR_OF_DAY);
         minute1 = calendar.get(Calendar.MINUTE);
    }

    public void add_alarm(){


        TimePickerDialog timePickerDialog = new TimePickerDialog(add_Alarm.this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(add_Alarm.this, hourOfDay+" "+minute+"is selected",Toast.LENGTH_LONG).show();
                hour = hourOfDay;
                minute1 = minute;
                set_date();
            }
        },hour,minute1,false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();


    }

    public void set_date(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(add_Alarm.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {

                year = year1;
                month = month1;
                date = dayOfMonth1;
            }
        },year, month,date);

        datePickerDialog.setTitle("Select date");
        datePickerDialog.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add__alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
