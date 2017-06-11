package com.example.goku.alarmclock;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.Switch;
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
    AlarmManager manager ;
    Intent intent;
    PendingIntent pendingIntent;
    Calendar calendar;
    int request;

    ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(add_Alarm.this,BroadcastReciever.class);
         expandableListView = (ExpandableListView)findViewById(R.id.expandablelist );
        parentlist = new ArrayList<>();
        childlist = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_time_and_date();
                add_alarm();
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

    }

    public void get_time_and_date(){
        calendar = Calendar.getInstance();
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

                 hour = hourOfDay;
                minute1 = minute;
                Toast.makeText(add_Alarm.this, hour+" "+minute1+"is selected",Toast.LENGTH_LONG).show();


                Log.d("timepicker",String.valueOf(hour)+" "+String.valueOf(minute1));


                Log.d("timepicker",String.valueOf(hour)+" "+String.valueOf(minute1));

                set_date();



            }
        },hour,minute1,false);
        timePickerDialog.show();




    }

    public void set_date(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(add_Alarm.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {

                year = year1;
                month = month1;
                date = dayOfMonth1;
                calendar.set(year,month,date,hour,minute1,0);

                Log.e("tim2",String.valueOf(minute1));
                Log.e("pending",String.valueOf(calendar.getTimeInMillis()));
                pendingIntent = PendingIntent.getBroadcast(add_Alarm.this,parentlist.size(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                set_objects();
              /*  ParentBean pb = new ParentBean(hour, minute1, date, month, year, true);
                ChildBean cb = new ChildBean(song, vibrate );
                pb.toString();
                cb.toString();*/
            }
        },year, month,date);

        datePickerDialog.setTitle("Select date");
        datePickerDialog.show();

    }
    public void set_objects(){
        Log.e("date set",String.valueOf(year));
        Log.e("main","started"); ParentBean pb = new ParentBean(hour, minute1, date, month, year, true);
        ChildBean cb = new ChildBean("ok", true );

        Log.d("pb" , pb.toString());
        parentlist.add(pb);
        for(ParentBean pb1 : parentlist){
            Log.e("p1" , pb1.toString());
        }
        Log.d("cb",cb.toString());
        childlist.add(cb);

        ExpandableAdapter adapter = new ExpandableAdapter(this, parentlist, childlist);
        Log.e("Expandable","ok");
        adapter.notifyDataSetChanged();

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.e("group","work");

                return false;
            }
        });


        expandableListView.setAdapter(adapter);


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
