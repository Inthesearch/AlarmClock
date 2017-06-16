package com.example.goku.alarmclock;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class add_Alarm extends AppCompatActivity implements Serializable {
    int date, month, year, hour, minute1;
    ContentResolver contentResolver;

    ArrayList<ParentBean> parentlist;
    ArrayList<ChildBean> childlist;
    Map<ParentBean,ChildBean> map;

   // ExpandableListView expandableListView;
    ExpandableAdapter adapter;
    AlarmManager manager ;
    Intent intent;
    PendingIntent pendingIntent;
    Calendar calendar;
     int request,groupposition;

    ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        expandableListView = (ExpandableListView)findViewById(R.id.expandablelist );
        contentResolver = getContentResolver();
        String[] parent = {Util.parentid,Util.parenttime,Util.parentrequest,Util.parenthour,Util.parentminute,Util.parentstatus};

        String[] child = {Util.childid,Util.childsong,Util.childvibrate};
        parentlist = new ArrayList<>();
        childlist = new ArrayList<>();
       // Log.e("raghav","goyal");
        //contentResolver.delete(Util.PARENT_URI,null,null);
      // int d = contentResolver.delete(Util.CHILD_URI,null,null);
        Cursor cursor = contentResolver.query(Util.PARENT_URI,parent,null,null,null);
        Cursor childcursor = contentResolver.query(Util.CHILD_URI,child,null,null,null);

       // Toast.makeText(this,String.valueOf(d),Toast.LENGTH_SHORT).show();
        Log.e("cursor", String.valueOf(childcursor));
        if(cursor!=null){
            int i,r,h,m;
            String t;
            boolean s;
            while(cursor.moveToNext()){
                i = cursor.getInt(cursor.getColumnIndex(Util.parentid));
                    t = String.valueOf(cursor.getInt(cursor.getColumnIndex(Util.parenttime)));
                r = cursor.getInt(cursor.getColumnIndex(Util.parentrequest));
                h = cursor.getInt(cursor.getColumnIndex(Util.parenthour));
                m = cursor.getInt(cursor.getColumnIndex(Util.parentminute));
                Log.e("mi", String.valueOf(m));

                s= Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(Util.parentstatus)));
              Log.e("milli", String.valueOf(t));

                ParentBean pb = new ParentBean(h,m,s);
                pb.setRequest(r);
                //pb.setTime(t);
                pb.setId(i);
            parentlist.add(pb);

            }}
            if (childcursor!=null){
                String so;
                boolean v;
                int id ;
                Log.e("raghav","goyal");

                while(childcursor.moveToNext()){
                    so = childcursor.getString(childcursor.getColumnIndex(Util.childsong));
                    Log.e("childcursor",so);
                    v = Boolean.parseBoolean(childcursor.getString(childcursor.getColumnIndex(Util.childvibrate)));
                     id = childcursor.getInt(childcursor.getColumnIndex(Util.childid));
                    ChildBean cb = new ChildBean(so, v);
                   Log.e("string", cb.toString());
                    cb.setId(id);

//                    Log.e("childlist",childlist.get(0).toString());
                    childlist.add(cb);
                    for(ChildBean c :childlist){
                        Log.e("childlist2",c.toString());
                    }

                }
            }





        map= new HashMap<>();

        for(int j=0;j<parentlist.size();j++)

    {
        map.put(parentlist.get(j), childlist.get(j));
    }
    adapter = new ExpandableAdapter(this, parentlist, childlist,map);
        if(childlist.size()!=0){
            Log.e("childlist3", adapter.childlist.get(0).toString());}
        expandableListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(add_Alarm.this,BroadcastReciever.class);






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ;

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
                calendar.set(hour,minute1,0);

                set_objects();



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


                Log.e("tim2",String.valueOf(minute1));
                Log.e("pending",String.valueOf(calendar.getTimeInMillis()));


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
        Log.e("main","started"); ParentBean pb = new ParentBean(hour, minute1, true);

       // if(parentlist.size()==0){
         //   pb.setRequest(parentlist.size());
        pb.setRequest(parentlist.size());

        for(int j =0 ; j<parentlist.size();j++){
            if(pb.getRequest()==parentlist.get(j).getRequest()){
                pb.setRequest(pb.getRequest()+1);
            }
        }

        ChildBean cb = new ChildBean("ok", false );

        pb.setTime(calendar.getTimeInMillis());
        parentlist.add(pb);
        for(ParentBean pb1 : parentlist){
            Log.e("p1" , pb1.toString());
        }
        Log.d("cb",cb.toString());
        childlist.add(cb);

        ContentValues values = new ContentValues();
        values.put("TIME",pb.getTime());
        Log.e("time", String.valueOf(pb.getTime()));
        values.put("REQUEST",pb.getRequest());
        values.put("HOUR",pb.getHour());
        values.put("MINUTE",pb.getMinute());
        values.put("STATUS",pb.isStatus());
        Uri i = contentResolver.insert(Util.PARENT_URI,values);

        ContentValues childvalues = new ContentValues();
        childvalues.put("SONG",cb.getSong());
        childvalues.put("VIBRATE",cb.getVibrate());
        Uri f = contentResolver.insert(Util.CHILD_URI,childvalues);
        Toast.makeText(this,"inserted succesfully"+f,Toast.LENGTH_SHORT).show();




        pendingIntent = PendingIntent.getBroadcast(add_Alarm.this,pb.getRequest(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
        manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);




        Log.e("Expandable","ok");

        adapter.notifyDataSetChanged();
        expandableListView.setAdapter(adapter);






        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.e("group","work");


                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                groupposition = groupPosition;
                return false;
            }
        });


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
