package com.example.eltgm.weatherapp;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by eltgm on 17.01.17.
 */

public class Launcher extends AppCompatActivity {

    private LocationManager locationManager;

    double lng;
    double lat;
    boolean flag;
    private String url;
    private EditText editText;
    //"http://api.openweathermap.org/data/2.5/forecast?q=Moscow&appid=295f7bee433d8cf26fd64d9ab085726b"
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_layout);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        editText = (EditText)findViewById(R.id.editText);
        editText.setVisibility(View.VISIBLE);
    }

    public void enterWeather(View view) {
        url = " ";
        Intent intent = new Intent(Launcher.this,MainActivity.class);
        url = "http://api.openweathermap.org/data/2.5/forecast?q=" + editText.getText()
                + "&appid=295f7bee433d8cf26fd64d9ab085726b";
        intent.putExtra("url", url);
        startActivity(intent);
    }

    /**
     * ДЛЯ ПОИСКА ПОГОДЫ ПО ГЕОЛОКАЦИИ
     */
/*    //api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=295f7bee433d8cf26fd64d9ab085726b
    public void getLocation(View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                10,
                10,
                locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                10,
                10,
                locationListener);

        url = " ";
        Intent intent = new Intent(Launcher.this, MainActivity.class);
        url = "api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lng + "&appid=295f7bee433d8cf26fd64d9ab085726b";
        intent.putExtra("url",url);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                10,
                10,
                locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                10,
                10,
                locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lng = location.getLongitude();
            lat = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast toast = Toast.makeText(Launcher.this,"Не возможно получить геоданные",Toast.LENGTH_LONG);
            toast.show();
        }
    };*/
}
