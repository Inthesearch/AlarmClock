package com.example.goku.alarmclock;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Goku on 10/06/2017.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {


    private Context ctx;
    private ArrayList<ParentBean> parentlist;
    private ArrayList<ChildBean> childlist;
    HashMap<ParentBean ,ArrayList<ChildBean>> map;


    public ExpandableAdapter(Context ctx, ArrayList<ParentBean> parentlist, ArrayList<ChildBean> childlist){

        this.ctx = ctx;
        this.parentlist = parentlist;
        this.childlist = childlist;
         map = new HashMap<>();
        for(ParentBean pb : parentlist){
            ;

        map.put(pb,childlist);
        Log.e("childliset",String.valueOf(childlist.size()));
    }}
    @Override
    public int getGroupCount() {
        Log.d("problem","1");
        for(ParentBean b : parentlist){
            Log.e("parentlist",b.toString());
        }

        return parentlist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.e("problem", "30");
        Log.e("childlist",String.valueOf(childlist.size()));

        return map.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        Log.e("problem2 ", parentlist.get(groupPosition).toString());
        Log.e("groupPosition",String.valueOf(groupPosition));
        return parentlist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.e("problem3",childlist.get(childPosition).toString());
        return childlist.get(childPosition);
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

            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.parent_view,null);
            }
            Log.e("Mision2",header.toString());
            TextView alarm_time = (TextView)convertView.findViewById(R.id.alarm_time);
        Switch onOff = (Switch)convertView.findViewById(R.id.onOff);
        onOff.setChecked(parentlist.get(groupPosition).status);
        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                Toast.makeText(ctx,"alarm on ",Toast.LENGTH_SHORT).show();
                    parentlist.get(groupPosition).setStatus(true);
                }if(isChecked==false){
                    Toast.makeText(ctx,"alarm off",Toast.LENGTH_LONG).show();
                    parentlist.get(groupPosition).setStatus(false);
                }
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
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Log.e("mission","impossible");
        ChildBean footer = (ChildBean)this.getChild(groupPosition,childPosition);
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view,parent,false);

        }







        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
