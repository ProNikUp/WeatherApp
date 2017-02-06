package com.example.eltgm.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

/**
 * Created by eltgm on 17.01.17.
 */

public class Launcher extends AppCompatActivity {

    private String url;
    private EditText editText;
    Context mContext;
    //"http://api.openweathermap.org/data/2.5/forecast?q=Moscow&appid=295f7bee433d8cf26fd64d9ab085726b"
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_layout);

        editText = (EditText)findViewById(R.id.editText);
        editText.setVisibility(View.INVISIBLE);
        mContext = getApplicationContext();
        //Toast.makeText(getApplicationContext(),
             //   "Упс, что-то пошло не так", Toast.LENGTH_LONG).show();
    }

    public void enterWeather(View view) {
        url = " ";
        Intent intent = new Intent(Launcher.this,MainActivity.class);
        url = "http://api.openweathermap.org/data/2.5/forecast?q=" + editText.getText()
                + "&appid=295f7bee433d8cf26fd64d9ab085726b";
        intent.putExtra("url", url);
        startActivity(intent);
    }

    public void findPosition(View view) {
        SmartLocation.with(this).location().oneFix().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                //url = " ";
                Intent intent = new Intent(Launcher.this,MainActivity.class);
                url = "http://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLatitude() +"&lon="+ location.getLongitude() +
                        "&appid=295f7bee433d8cf26fd64d9ab085726b";
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        if(!SmartLocation.with(this).location().state().isAnyProviderAvailable()) {
            //Toast.makeText(getApplicationContext(),"Geolocation is unavailable",Toast.LENGTH_LONG).show();
            editText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * ДЛЯ ПОИСКА ПОГОДЫ ПО ГЕОЛОКАЦИИ
     */
   //api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=295f7bee433d8cf26fd64d9ab085726b

}
