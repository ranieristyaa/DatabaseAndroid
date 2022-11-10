package com.example.dataruang;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "fsmdb.sqlite";
    public static final String TABLE_NAME = "ruangan";
    public static final String COLUMN_ID  = "id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_KAPASITAS = "kapasitas";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table ruangan " +
                        "(id integer primary key autoincrement, nama text,kapasitas integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ruangan");
        onCreate(db);
    }
    public boolean addRuangan(String nama, String kapasitas){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama",nama);
        contentValues.put("kapasitas",Integer.parseInt(kapasitas));
        db.insert("ruangan", null, contentValues);
        db.close();
        return true;
    }

    public boolean updateRuangan(Integer id,String nama, String kapasitas)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama",nama);
        contentValues.put("kapasitas",Integer.parseInt(kapasitas));
        db.update("ruangan", contentValues, "id = ?", new String[]{Integer.toString(id)});
        db.close();
        return true;
    }
    public Integer deleteRuangan(Integer id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("ruangan","id = ?",new String[]{Integer.toString(id)});
    }
    public Cursor getData(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from ruangan where id = " + id + "", null);
        return res;
    }

    @SuppressLint("Range")
    public ArrayList<String> getAll(){
        ArrayList<String> arraylist= new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from ruangan",null);

        if (cursor.moveToFirst()) {
            do {
                arraylist.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAMA)));
            } while (cursor.moveToNext());
        }
        return arraylist;
    }
}

