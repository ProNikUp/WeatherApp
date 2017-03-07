package com.example.eltgm.weatherapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Date;

/**
 * Created by eltgm on 14.02.17.
 */

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context,"myDb",null,1);
    }//создание базы данных


    public WeatherDay getCityWeather(String cityId, DbHelper helper)
    {
        String temp1Json;
        String hum1Json;
        String pres1Json;
        String wind1Json;
        String descr1Json;
        String dayJson;

        SQLiteDatabase db = helper.getReadableDatabase();

        // SELECT * FROM temp WHERE

        Cursor c = db.query("cities", null, null, null, null, null, null);

        if(c.moveToFirst())
        {
            int cityIdColIndex = c.getColumnIndex("city_id");
            int idColIndex = c.getColumnIndex("_id");

            if(cityId == null)
            {
                Cursor temps = db.query("temp",null, "city_id='"+c.getInt(c.getColumnIndex("city_id"))+"'",null, null, null,null);
                Cursor hum = db.query("hum",null, "city_id='"+c.getInt(cityIdColIndex)+"'",null, null, null,null);
                Cursor pres = db.query("pres",null, "city_id='"+c.getInt(cityIdColIndex)+"'",null, null, null,null);
                Cursor wind = db.query("wind",null, "city_id='"+c.getInt(cityIdColIndex)+"'",null, null, null,null);
                Cursor descr = db.query("descr",null, "city_id='"+c.getInt(cityIdColIndex)+"'",null, null, null,null);

               if(temps.moveToFirst() && hum.moveToFirst() && pres.moveToFirst() && wind.moveToFirst() && descr.moveToFirst()){
                   dayJson = temps.getString(temps.getColumnIndex("temp1"));
                   JSONParser parser = new JSONParser();
                   Object JSONobj = null;

                   try {
                       JSONobj = parser.parse(dayJson);
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
                   JSONObject tempObj = (JSONObject) JSONobj;

                   int dayValue = (int)tempObj.get("day");
                   if(dayValue == new Date().getSeconds()) { // int past = (new Date().getSeconds() - dayValue)/3600= кол-во прошедших дней
                       // "temp" + past + 1
                       temp1Json = temps.getString(temps.getColumnIndex("temp" + 1));
                       hum1Json = hum.getString(hum.getColumnIndex("hum" + 1));
                       pres1Json = pres.getString(pres.getColumnIndex("pres" + 1));
                       wind1Json = wind.getString(wind.getColumnIndex("wind" + 1));
                       descr1Json = descr.getString(descr.getColumnIndex("descr" + 1));
                       return new WeatherDay(temp1Json, hum1Json, pres1Json, wind1Json, descr1Json);
                   }else{
                       int past = (new Date().getSeconds() - dayValue)/3600 + 1;
                       temp1Json = temps.getString(temps.getColumnIndex("temp" + past));
                       hum1Json = hum.getString(temps.getColumnIndex("hum" + past));
                       pres1Json = pres.getString(temps.getColumnIndex("pres" + past));
                       wind1Json = wind.getString(temps.getColumnIndex("wind" + past));
                       descr1Json = descr.getString(temps.getColumnIndex("descr" + past));
                       return new WeatherDay(temp1Json, hum1Json, pres1Json, wind1Json, descr1Json);
                   }
               }
            }

        } else
            c.close();
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cities (" +
                "_id integer primary key autoincrement," +
                "city_id integer,"+
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
