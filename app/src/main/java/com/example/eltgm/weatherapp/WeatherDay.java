package com.example.eltgm.weatherapp;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by eltgm on 19.01.17.
 */

class WeatherDay {
    Weather[] day;


    WeatherDay(Weather[] days) {
        this.day = days;
    }

    public Weather[] getDay() {
        return day;
    }

    WeatherDay(String tempJSON, String humJSON, String windJSON, String presJSON, String descrJSON){
        int count = 0;
        JSONParser parser = new JSONParser();
        Object JSONobj = null;
        try {
            JSONobj = parser.parse(tempJSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject tempObj = (JSONObject) JSONobj;
        for(int i = 0; i < tempObj.size(); i++){
            int tmp = (int) tempObj.get(count+"temp");
            if(tmp != 0) {
                day[i].setTmp(tmp);
                count += 3;
            }else{
                while (tmp == 0){
                    count+=3;
                    tmp = (int) tempObj.get(count+"temp");
                }
            }
        }

    }

}//класс,хранящий погоду на день
