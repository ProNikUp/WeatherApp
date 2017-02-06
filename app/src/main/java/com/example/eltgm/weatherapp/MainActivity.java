package com.example.eltgm.weatherapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    JSONObject cityName;

    private Weather[] tempBuff;
    private String osadki;
    private String wind;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String url = getIntent().getExtras().getString("url");

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final WeatherDay[] days = getWeather(response);

                        dayTempSet();

                        final RecyclerView rvMain = (RecyclerView) findViewById(R.id.rvMain);
                        // Create adapter passing in the sample user data
                        final WeatherAdapter adapter = new WeatherAdapter(MainActivity.this, days);
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
        queue.add(stringRequest);


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
        cityName = (JSONObject) cityAll.get("city");
        JSONArray cityWeatherList = (JSONArray) cityAll.get("list");

        JSONObject osdkiObj =(JSONObject) cityWeatherList.get(0);
        JSONArray osadkiWeather = (JSONArray) osdkiObj.get("weather");
        JSONObject windObj = (JSONObject) osdkiObj.get("wind");
        wind = windObj.get("speed").toString();

        JSONObject osadkiWeatherArray = (JSONObject) osadkiWeather.get(0);
        osadki = osadkiWeatherArray.get("description").toString();

        tempBuff = new Weather[cityWeatherList.size()];

        for(int i = 0; i < tempBuff.length; i++){
            JSONObject cityDayWeather = (JSONObject) cityWeatherList.get(i);
            JSONObject cityDayTempAll = (JSONObject) cityDayWeather.get("main");
            JSONArray descriptionArray = (JSONArray) cityDayWeather.get("weather");
            JSONObject descriptionObject = (JSONObject) descriptionArray.get(0);
            String nowTempK =  cityDayTempAll.get("temp").toString();
            JSONObject innerWind = (JSONObject) cityDayWeather.get("wind");

            Weather temp = new Weather(Double.valueOf(nowTempK) - 273.15,cityDayWeather.get("dt_txt").toString(),descriptionObject.get("description").toString(),
                    Double.valueOf(innerWind.get("speed").toString()),Integer.valueOf(cityDayTempAll.get("humidity").toString()),
                    Integer.valueOf(cityDayWeather.get("dt").toString()), Double.valueOf(cityDayTempAll.get("pressure").toString()));
            tempBuff[i] = temp;
        }

        for(start = 0; start < tempBuff.length; start++){
            if(tempBuff[start].date.contains("00:00:00")){
                break;
            }
        }
        tempDay = Arrays.copyOfRange(tempBuff,start,tempBuff.length);

        int count = 0;
        int oldCount = count;
        WeatherDay[] weatherDays = new WeatherDay[4];

        for(int i = 0; i < 4; i++)
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
    }

    @SuppressLint("SimpleDateFormat")
    private void dayTempSet(){
        final TextView tvDay = (TextView)findViewById(R.id.tvDay);
        final TextView tvDayTemp = (TextView)findViewById(R.id.tvDayTemp);
        final TextView tvWind = (TextView)findViewById(R.id.tvWind);
        final TextView tvOsadki = (TextView)findViewById(R.id.tvOsadki);
        final TextView tvCityName = (TextView)findViewById(R.id.tvEnter);

        final Date date = new Date();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd MMM yyyy H:mm");

        tvCityName.setText(cityName.get("name").toString());
        tvDay.setText(dateFormat.format(date));
        tvDayTemp.setText(String.valueOf(tempBuff[0].getTemp()));
        tvOsadki.setText(osadki);
        tvWind.setText("wind speed: " + wind + "m/s");

        Context context = getApplicationContext();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(android.R.drawable.ic_media_next)
                .setContentTitle(cityName.get("name").toString())
                .setContentText(String.valueOf(tempBuff[0].getTemp())); // Текст уведомления

        // Notification notification = builder.getNotification(); // до API 16
        Notification notification = builder.build();

        notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(100, notification);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(100);
    }
}

