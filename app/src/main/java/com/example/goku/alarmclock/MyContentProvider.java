package com.example.goku.alarmclock;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }
    DBhelper dBhelper ;
    SQLiteDatabase sqLiteDatabase;


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

       String tab=  uri.getLastPathSegment();
        int i  = sqLiteDatabase.delete(tab,null,null);
        return i;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String tab = uri.getLastPathSegment();
        long l = sqLiteDatabase.insert(tab,null,values);
        Uri dummy = Uri.parse("alarm inserted"+l);

        return dummy;
    }

    @Override
    public boolean onCreate() {
        dBhelper = new DBhelper(getContext(),Util.DBname,null,Util.DBVersion);
        sqLiteDatabase = dBhelper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String tab = uri.getLastPathSegment();
        Cursor cursor = sqLiteDatabase.query(tab,projection,null,null,null,null,null);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String tab = uri.getLastPathSegment();
        int i = sqLiteDatabase.update(tab,values,selection,null);
        return i;
           }

    class DBhelper extends SQLiteOpenHelper {

        public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Util.CREATE_Parent);
            db.execSQL(Util.CREATE_Child);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
