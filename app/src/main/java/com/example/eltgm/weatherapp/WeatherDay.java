package com.example.eltgm.weatherapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eltgm on 19.01.17.
 */

class WeatherDay implements Parcelable{
    int[] temp;
    double[] pressure;
    int[] humidity;
    double[] windSpeed;
    String[] description;
    int day;

    WeatherDay(Weather[] weathers){
        temp = new int[weathers.length];
        pressure = new double[weathers.length];
        humidity = new int[weathers.length];
        windSpeed = new double[weathers.length];
        description = new String[weathers.length];

        for(int i = 0; i < weathers.length; i++){
            temp[i] = weathers[i].getTemp();
            pressure[i] = weathers[i].getPressure();
            humidity[i] = weathers[i].getHumidity();
            windSpeed[i] = weathers[i].getWindSpeed();
            description[i] = weathers[i].getDescription();
        }
        day = weathers[0].getDay();
    }


    protected WeatherDay(Parcel in) {
        this.temp = in.createIntArray();
        this.pressure = in.createDoubleArray();
        this.humidity = in.createIntArray();
        this.windSpeed = in.createDoubleArray();
        this.description = in.createStringArray();
        this.day = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(temp);
        dest.writeDoubleArray(pressure);
        dest.writeIntArray(humidity);
        dest.writeDoubleArray(windSpeed);
        dest.writeStringArray(description);
        dest.writeInt(day);
    }

    public static final Creator<WeatherDay> CREATOR = new Creator<WeatherDay>() {
        @Override
        public WeatherDay createFromParcel(Parcel in) {
            return new WeatherDay(in);
        }

        @Override
        public WeatherDay[] newArray(int size) {
            return new WeatherDay[size];
        }
    };

    double[] getPressure() {
        return pressure;
    }

    double[] getWindSpeed() {
        return windSpeed;
    }

    String getDay() {
        SimpleDateFormat parseFormat = new SimpleDateFormat("E");
        Date date = new Date((this.day + 86400)*1000);

        return parseFormat.format(date);
    }

    int[] getTemp() {
        return temp;
    }

    int[] getHumidity() {
        return humidity;
    }

    String[] getDescription() {
        return description;
    }
}//класс,хранящий погоду на день
