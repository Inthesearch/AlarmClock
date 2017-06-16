package com.example.goku.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.goku.alarmclock.R.id.vibrate;

/**
 * Created by Goku on 11/06/2017.
 */

public class BroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("work","done");
        Toast.makeText(context,"alarm time",Toast.LENGTH_LONG).show();

        ArrayList<ChildBean> childlist= (ArrayList<ChildBean>) intent.getSerializableExtra("childlist");
        //Log.e("on",childlist.get(0).toString());
             Log.e("grouppo",String.valueOf(intent.hasExtra("childlist")));

            if(intent.hasExtra("childlist")){
                Vibrator vibrate = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrate.vibrate(10000);

            }

        }




    }

