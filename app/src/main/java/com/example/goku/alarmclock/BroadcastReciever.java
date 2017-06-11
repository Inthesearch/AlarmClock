package com.example.goku.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Goku on 11/06/2017.
 */

public class BroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("work","done");
        Toast.makeText(context,"alarm time",Toast.LENGTH_LONG).show();
    }
}
