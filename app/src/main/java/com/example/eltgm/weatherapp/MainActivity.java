package com.example.eltgm.weatherapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    NotificationManager notificationManager;
    DbHelper dbHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    } //создание меню
    //TODO меню
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String url = getIntent().getExtras().getString("url");

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, //конфигурация сетевого запроса
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final WeatherDay[] days = getWeather(response);

                        dayTempSet(days);
                        insertToDb(days);
                        WeatherDay[] daysFour = Arrays.copyOfRange(days,1,days.length); // передаем только 4 дня,не включая сегодняшний
                        final RecyclerView rvMain = (RecyclerView) findViewById(R.id.rvMain);
                        // Create adapter passing in the sample user data

                        final WeatherAdapter adapter = new WeatherAdapter(MainActivity.this, daysFour);
                        // Attach the adapter to the recyclerview to populate items
                        rvMain.setAdapter(adapter);
                        // Set layout manager to position the items
                        rvMain.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 Toast.makeText(getApplicationContext(),
                        "Упс, что-то пошло не так", Toast.LENGTH_LONG).show();
            }
        });
        final RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest); //отправка сетевого запроса

    }

    public void insertToDb(WeatherDay[] days) {

        dbHelper = new DbHelper(getApplicationContext());

        String cityString = days[0].cityName;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query("cities", null, null, null, null, null, null);

        boolean hasCity = false;
        long cityId = -1;
        if(c.moveToFirst())
        {
            int nameColIndex = c.getColumnIndex("name");
            int idColIndex = c.getColumnIndex("_id");

                do {
                    if(c.getString(nameColIndex).equals(cityString))
                    {
                        hasCity = true;
                        cityId = c.getInt(idColIndex);
                        c.close();
                        break;
                    }

                } while (c.moveToNext());
            } else
                c.close();


        String ID = days[0].id;

        if(!hasCity) {
            int k = 1;
            JSONObject tempObj = new JSONObject();
            JSONObject humObj = new JSONObject();
            JSONObject presObj = new JSONObject();
            JSONObject windObj = new JSONObject();
            JSONObject descrObj = new JSONObject();

            ContentValues cityCv = new ContentValues();
            cityCv.put("name", cityString);
            cityId = db.insert("cities", null, cityCv);

            hasCity = true;
            ContentValues tempValues = new ContentValues();
            ContentValues windValues = new ContentValues();
            ContentValues presValues = new ContentValues();
            ContentValues humValues = new ContentValues();
            ContentValues descrValues = new ContentValues();

            for(int i=0; i < days.length; i++)
            {
                int count = 0;
                int[] temps = days[i].getTemp();
                double[] pres = days[i].getPressure();
                double[] wind = days[i].getWindSpeed();
                int[] hum = days[i].getHumidity();
                String[] descr = days[i].getDescription();
                long[] sec = days[i].getDaySec();

                for(int j = 0; j < temps.length;j++) {
                    SimpleDateFormat parseFormat = new SimpleDateFormat("H");
                    Date date = new Date((sec[j]-10800)*1000);
                    String newDate = parseFormat.format(date);

                    tempObj.put(newDate + "temp", temps[j]);
                    humObj.put(newDate + "hum", hum[j]);
                    presObj.put(newDate + "pres", pres[j]);
                    windObj.put(newDate + "wind", wind[j]);
                    descrObj.put(newDate + "descr", descr[j]);
                    // TODO Перевести массив в JSON строку!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }

                String jsonTemp = tempObj.toString();
                tempValues.put("temp" + (k),jsonTemp);

                String jsonWind = windObj.toString();
                windValues.put("wind" + (k),jsonWind);

                String jsonPres = presObj.toString();
                presValues.put("pres" + (k),jsonPres);

                String jsonHum = humObj.toString();
                humValues.put("hum" + (k),jsonHum);

                String jsonDescr = descrObj.toString();
                descrValues.put("descr" + (k),jsonDescr);
                k++;
            }

            tempValues.put("city_id",Long.valueOf(ID));
            humValues.put("city_id",Long.valueOf(ID));
            presValues.put("city_id",Long.valueOf(ID));
            windValues.put("city_id",Long.valueOf(ID));
            descrValues.put("city_id",Long.valueOf(ID));

            db.insert("temp", null, tempValues);
            db.insert("hum",null,humValues);
            db.insert("pres",null,presValues);
            db.insert("wind",null,windValues);
            db.insert("descr",null,descrValues);

        } else {

            int k = 1;
            JSONObject tempObj = new JSONObject();
            JSONObject humObj = new JSONObject();
            JSONObject presObj = new JSONObject();
            JSONObject windObj = new JSONObject();
            JSONObject descrObj = new JSONObject();


            ContentValues tempValues = new ContentValues();
            ContentValues windValues = new ContentValues();
            ContentValues presValues = new ContentValues();
            ContentValues humValues = new ContentValues();
            ContentValues descrValues = new ContentValues();

            for(int i=0; i < days.length; i++)
            {
                int count = 0;
                int[] temps = days[i].getTemp();
                double[] pres = days[i].getPressure();
                double[] wind = days[i].getWindSpeed();
                int[] hum = days[i].getHumidity();
                String[] descr = days[i].getDescription();
                long[] sec = days[i].getDaySec();

                for(int j = 0; j < temps.length;j++) {
                    SimpleDateFormat parseFormat = new SimpleDateFormat("H");
                    Date date = new Date((sec[j]-10800)*1000);
                    String newDate = parseFormat.format(date);

                    tempObj.put(newDate + "temp", temps[j]);
                    humObj.put(newDate + "hum", hum[j]);
                    presObj.put(newDate + "pres", pres[j]);
                    windObj.put(newDate + "wind", wind[j]);
                    descrObj.put(newDate + "descr", descr[j]);

                    // TODO Перевести массив в JSON строку!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }

                String jsonTemp = tempObj.toString();
                tempValues.put("temp" + (k),jsonTemp);

                String jsonWind = windObj.toString();
                windValues.put("wind" + (k),jsonWind);

                String jsonPres = presObj.toString();
                presValues.put("pres" + (k),jsonPres);

                String jsonHum = humObj.toString();
                humValues.put("hum" + (k),jsonHum);

                String jsonDescr = descrObj.toString();
                descrValues.put("descr" + (k),jsonDescr);
                k++;
            }

            tempValues.put("city_id",Long.valueOf(ID));
            humValues.put("city_id",Long.valueOf(ID));
            presValues.put("city_id",Long.valueOf(ID));
            windValues.put("city_id",Long.valueOf(ID));
            descrValues.put("city_id",Long.valueOf(ID));

            db.update("temp", tempValues, "_id = " + cityId,null);
            db.update("hum", humValues, "_id = " + cityId,null);
            db.update("pres", presValues, "_id = " + cityId,null);
            db.update("wind", windValues, "_id = " + cityId,null);
            db.update("descr", descrValues, "_id = " + cityId,null);

        }

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог

                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false -
                        // выходим из цикла
                    } while (c.moveToNext());
                }


    public WeatherDay[] getWeather(String response){

        JSONParser parser = new JSONParser();
        Object JSONobj = null;
        Weather[] tempDay;
        int start;

        try {
            JSONobj = parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject cityAll = (JSONObject) JSONobj;
        JSONObject cityName = (JSONObject) cityAll.get("city");
        JSONArray cityWeatherList = (JSONArray) cityAll.get("list");

        JSONObject osdkiObj =(JSONObject) cityWeatherList.get(0);
        JSONArray osadkiWeather = (JSONArray) osdkiObj.get("weather");
        JSONObject windObj = (JSONObject) osdkiObj.get("wind");
//        String wind = windObj.get("speed").toString();

        JSONObject osadkiWeatherArray = (JSONObject) osadkiWeather.get(0);
       // String osadki = osadkiWeatherArray.get("description").toString();

        Weather[] tempBuff = new Weather[cityWeatherList.size()];

        for(int i = 0; i < tempBuff.length; i++){
            JSONObject cityDayWeather = (JSONObject) cityWeatherList.get(i);
            JSONObject cityDayTempAll = (JSONObject) cityDayWeather.get("main");
            JSONArray descriptionArray = (JSONArray) cityDayWeather.get("weather");
            JSONObject descriptionObject = (JSONObject) descriptionArray.get(0);
            String nowTempK =  cityDayTempAll.get("temp").toString();
            JSONObject innerWind = (JSONObject) cityDayWeather.get("wind");

            Weather temp = new Weather(Double.valueOf(nowTempK) - 273.15,cityDayWeather.get("dt_txt").toString(),descriptionObject.get("description").toString(),
                    Double.valueOf(innerWind.get("speed").toString()),Integer.valueOf(cityDayTempAll.get("humidity").toString()),
                    Integer.valueOf(cityDayWeather.get("dt").toString()), Double.valueOf(cityDayTempAll.get("pressure").toString()),String.valueOf(((JSONObject)cityAll.get("city")).get("id"))
                    ,String.valueOf(cityName));
            tempBuff[i] = temp;
        }

        for(start = 0; start < tempBuff.length; start++){
            if(tempBuff[start].date.contains("00:00:00")){
                break;
            }
        }
        tempDay = Arrays.copyOfRange(tempBuff,0,tempBuff.length);

        int count = 0;
        int oldCount = count;
        WeatherDay[] weatherDays = new WeatherDay[5];

        for(int i = 0; i < 5; i++)
            for(; count <= tempDay.length; count++){
                if(count == tempDay.length){
                    Weather[] tempDays = Arrays.copyOfRange(tempDay, oldCount, count);
                    oldCount = count;
                    weatherDays[i] = new WeatherDay(tempDays);
                }
                else {
                    if (tempDay[count].date.contains("00:00:00") && count != oldCount) {
                        Weather[] tempDays = Arrays.copyOfRange(tempDay, oldCount, count);
                        oldCount = count;
                        weatherDays[i] = new WeatherDay(tempDays);
                        break;
                    }
                }
            }
        return weatherDays;
    }//создаем массив погод на 5 дня

    @SuppressLint("SimpleDateFormat")
    private void dayTempSet(WeatherDay[] days){
        final TextView tvDay = (TextView)findViewById(R.id.tvDay);
        final TextView tvDayTemp = (TextView)findViewById(R.id.tvDayTemp);
        final TextView tvWind = (TextView)findViewById(R.id.tvWind);
        final TextView tvOsadki = (TextView)findViewById(R.id.tvOsadki);
        final TextView tvCityName = (TextView)findViewById(R.id.tvEnter);

        final Date date = new Date();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd MMM yyyy H:mm");

        tvCityName.setText(days[0].cityName);
        tvDay.setText(dateFormat.format(date));
        tvDayTemp.setText(String.valueOf(days[0].getTemp()));
        tvOsadki.setText(days[0].getDescription()[0]);
        tvWind.setText("wind speed: " + days[0].getWindSpeed()[0] + "m/s");

        Context context = getApplicationContext();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(android.R.drawable.ic_media_next)
                .setContentTitle(days[0].cityName)
                .setContentText(String.valueOf(days[0].getTemp())); // Текст уведомления

        // Notification notification = builder.getNotification(); // до API 16
        Notification notification = builder.build();

        notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(100, notification);
    }//заполняем погоду для сегодняшнего дня

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(100);
    } //при уничтожении активности
}

