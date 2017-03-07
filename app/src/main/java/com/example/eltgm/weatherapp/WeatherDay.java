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
        Object JSONobj2 = null;
        Object JSONobj3 = null;
        Object JSONobj4 = null;
        Object JSONobj5 = null;

        try {
            JSONobj = parser.parse(tempJSON);
            JSONobj2 = parser.parse(humJSON);
            JSONobj3 = parser.parse(windJSON);
            JSONobj4 = parser.parse(presJSON);
            JSONobj5 = parser.parse(descrJSON);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject tempObj = (JSONObject) JSONobj;
        JSONObject humObj = (JSONObject) JSONobj2;
        JSONObject windObj = (JSONObject) JSONobj3;
        JSONObject presObj = (JSONObject) JSONobj4;
        JSONObject descrObj = (JSONObject) JSONobj5;
        for(int i = 0; i < tempObj.size(); i++){
            int tmp = (int) tempObj.get(count+"temp");
            int hum = (int) humObj.get(count+"hum");
            double wind = (double) windObj.get(count+"wind");
            double pres = (double) presObj.get(count+"pres");
            String descr = String.valueOf(descrObj.get(count+"descr"));
            if(Integer.valueOf(tmp) != null) {
                day[i].setTmp(tmp);
                day[i].setHumidity(hum);
                day[i].setWindSpeed(wind);
                day[i].setPressure(pres);
                day[i].setDescription(descr);
                count+=3;
            }else{
                while (Integer.valueOf(tmp) == null){
                    count+=3;
                    tmp = (int) tempObj.get(count+"temp");
                    hum = (int) humObj.get(count+"hum");
                    wind = (double) windObj.get(count+"wind");
                    pres = (double) presObj.get(count+"pres");
                    descr = String.valueOf(descrObj.get(count+"descr"));
                }
            }
        }

    }

}//класс,хранящий погоду на день
