package com.example.goku.alarmclock;

import android.net.Uri;

/**
 * Created by Goku on 13/06/2017.
 */

public class Util {


    public final static Uri PARENT_URI= Uri.parse("content://alarm1/parent1");
    public final static Uri CHILD_URI = Uri.parse("content://alarm1/child1");
    public final static String parentid = "_ID";
    public final static String parenttime = "TIME";
    public final static String parentrequest = "REQUEST";
    public final static String parenthour = "HOUR";
    public final static String parentminute = "MINUTE";
    public final static String parentstatus = "STATUS";

    public final static String childsong = "SONG";
    public final static String childid = "_ID";
    public final static String childvibrate = "VIBRATE";

    public final static int DBVersion = 1;
    public final static String DBname = "alarmclock.db";
    public final static String CREATE_Parent = "CREATE table parent1(_ID integer primary key autoincrement," +
            "TIME varchar(2500)," +
            "REQUEST integer," +
            "HOUR integer," +
            "MINUTE integer," +
            "STATUS varchar(256))";
    public final static String CREATE_Child = "CREATE table child1(_ID integer primary key autoincrement," +
            "SONG varchar(256)," +
            "VIBRATE varchar(256))";
}
