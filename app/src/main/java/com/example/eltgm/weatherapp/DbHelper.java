package com.example.eltgm.weatherapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eltgm on 14.02.17.
 */

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context,"myDb",null,1);
    }//создание базы данных

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cities (" +
                "_id integer primary key autoincrement," +
                "name text);");
        db.execSQL("create table temp (" +
                "_id integer primary key autoincrement," +
                "city_id integer," +
                "temp1 text," +
                "temp2 text," +
                "temp3 text," +
                "temp4 text," +
                "temp5 text);");
        db.execSQL("create table hum (" +
                "_id integer primary key autoincrement," +
                "city_id integer," +
                "hum1 text," +
                "hum2 text," +
                "hum3 text," +
                "hum4 text," +
                "hum5 text);");
        db.execSQL("create table pres (" +
                "_id integer primary key autoincrement," +
                "city_id integer," +
                "pres1 text," +
                "pres2 text," +
                "pres3 text," +
                "pres4 text," +
                "pres5 text);");
        db.execSQL("create table wind (" +
                "_id integer primary key autoincrement," +
                "city_id integer," +
                "wind1 text," +
                "wind2 text," +
                "wind3 text," +
                "wind4 text," +
                "wind5 text);");
        db.execSQL("create table descr (" +
                "_id integer primary key autoincrement," +
                "city_id integer," +
                "descr1 text," +
                "descr2 text," +
                "descr3 text," +
                "descr4 text," +
                "descr5 text);");
    }//создание таблиц

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }//обновление таблиц
}// класс для работы с бд
