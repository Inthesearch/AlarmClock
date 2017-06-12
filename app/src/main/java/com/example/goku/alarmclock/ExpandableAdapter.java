package com.example.goku.alarmclock;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Goku on 10/06/2017.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {


    private Context ctx;
    private ArrayList<ParentBean> parentlist;
    private ArrayList<ChildBean> childlist;
    Map<ParentBean ,ArrayList<ChildBean>> map;
    Intent intent ;
    PendingIntent pendingIntent;
    AlarmManager manager;
    int hour1, minute2, year, day,month;
    Calendar calendar;
    TextView alarm_time;
    int gr;


    public ExpandableAdapter(Context ctx, ArrayList<ParentBean> parentlist, ArrayList<ChildBean> childlist, Map<ParentBean,ArrayList<ChildBean>> map){

        this.ctx = ctx;
        this.parentlist = parentlist;
        this.childlist = childlist;
         this.map = map;
        intent = new Intent(ctx,BroadcastReciever.class);
        manager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);

        //for(ParentBean pb : parentlist){
            ;

        //map.put(pb,childlist);
        Log.e("childliset",String.valueOf(childlist.size()));
    }
    @Override
    public int getGroupCount() {
        Log.d("problem","1");
        for(ParentBean b : parentlist){
            Log.e("parentlist",b.toString());
        }
//        Log.e("map3", String.valueOf(map.get(parentlist.get(0))));

        return parentlist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.e("problem", "30");
        Log.e("childlist",String.valueOf(map.get(parentlist.get(groupPosition)).size()));

        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        Log.e("problem2 ", parentlist.get(groupPosition).toString());
        Log.e("groupPosition",String.valueOf(groupPosition));
        Log.e("map", String.valueOf(map.get(parentlist.get(groupPosition)).get(0)));
        return parentlist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.e("problem3",childlist.get(childPosition).toString());
        return childlist.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        Log.e("problem4",String.valueOf(groupPosition));
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        Log.e("problem4",String.valueOf(childPosition));
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ParentBean header = (ParentBean) this.getGroup(groupPosition);
        hour1 = header.getHour();
        minute2 = header.getMinute();
        day = header.getDate();
        month = header.getMonth();
        year = header.getYear();

            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.parent_view,null);
            }
            Log.e("Mision2",header.toString());
            alarm_time = (TextView)convertView.findViewById(R.id.alarm_time);

        Switch onOff = (Switch)convertView.findViewById(R.id.onOff);
        onOff.setChecked(parentlist.get(groupPosition).status);
        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){

                    pendingIntent = PendingIntent.getBroadcast(ctx,parentlist.get(groupPosition).getRequest(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    manager.set(AlarmManager.RTC_WAKEUP,parentlist.get(groupPosition).getTime(),pendingIntent);
                    parentlist.get(groupPosition).setStatus(true);
                }if(isChecked==false){
                    pendingIntent = PendingIntent.getBroadcast(ctx,parentlist.get(groupPosition).getRequest(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    manager.cancel(pendingIntent);
                    Log.e("alarm","stops");
                    parentlist.get(groupPosition).setStatus(false);
                }
            }
        });
        alarm_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 gr= groupPosition;

               setTime();




            }
        });
        if(header.getHour()>=12){
            header.setHour(header.getHour()-12);
        }if(header.getHour()<10){
            alarm_time.setText("0"+header.getHour()+" : "+header.getMinute());
        }else{
        alarm_time.setText(header.getHour()+" : "+header.getMinute());}

        TextView date = (TextView)convertView.findViewById(R.id.date);
        if(header.getDate()<10){
            date.setText("0"+header.getMonth()+"-"+header.getDate()+"-"+header.getYear());
        }else {
            date.setText(header.getMonth() + "-" + header.getDate() + "-" + header.getYear());
        }



        Log.e("mission ", "possible");
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Log.e("mission","impossible");
        ChildBean footer = (ChildBean)this.getChild(groupPosition,childPosition);
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view,parent,false);

        }
        CheckBox vibrate = (CheckBox)convertView.findViewById(R.id.vibrate);
        vibrate.setChecked(childlist.get(groupPosition).getVibrate());
        vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    childlist.get(groupPosition).setVibrate(true);
                }else{
                    childlist.get(groupPosition).setVibrate(false);
                }
            }
        });

        ImageButton delete = (ImageButton) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("group",String.valueOf(groupPosition));


                Log.e("requset",String.valueOf(parentlist.get(groupPosition).getRequest()));
                 pendingIntent = PendingIntent.getBroadcast(ctx,parentlist.get(groupPosition).getRequest(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                manager.cancel(pendingIntent);
                childlist.remove(groupPosition);
                parentlist.remove(groupPosition);
                Log.e("one","time");
//                this.notify();
                notifyDataSetChanged();

            }
        });








        return convertView;
    }


    public void setTime(){

        TimePickerDialog timePickerDialog = new TimePickerDialog(ctx, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                hour1= hourOfDay;
                minute2 = minute;
                Toast.makeText(ctx, hour1+" "+minute2+"is selected",Toast.LENGTH_LONG).show();


                Log.d("timepicker",String.valueOf(hour1)+" "+String.valueOf(minute2));


                parentlist.get(gr).setHour(hour1);
                parentlist.get(gr).setMinute(minute2);
                calendar = Calendar.getInstance();
                calendar.set(year, month, day, hour1, minute2,00);
                parentlist.get(gr).setTime(calendar.getTimeInMillis());
                pendingIntent = PendingIntent.getBroadcast(ctx,parentlist.get(gr).getRequest(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                manager.cancel(pendingIntent);
                manager.set(AlarmManager.RTC_WAKEUP,parentlist.get(gr).getTime(),pendingIntent);
                Log.e("time","changes");

                if(parentlist.get(gr).getHour()>=12){
                    parentlist.get(gr).setHour(parentlist.get(gr).getHour()-12);
                }if(parentlist.get(gr).getHour()<10){
                    alarm_time.setText("0"+parentlist.get(gr).getHour()+" : "+parentlist.get(gr).getMinute());
                }else{
                    alarm_time.setText(parentlist.get(gr).getHour()+" : "+parentlist.get(gr).getMinute());}



                //Log.d("timepicker",String.valueOf(hour)+" "+String.valueOf(minute1));

                //set_date();



            }
        },hour1,minute2,false);
        timePickerDialog.show();
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
