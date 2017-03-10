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
            while (!tempObj.containsKey(count+"temp"))
                count+=3;
/*            if(!tempObj.containsKey(count+"temp")) {

            }else{*/
            while (tempObj.containsKey(count+"temp")){
                long tmp = Long.parseLong(tempObj.get(count+"temp").toString());
                long hum =  Long.parseLong(humObj.get(count+"hum").toString());
                double wind = Double.parseDouble(windObj.get(count+"wind").toString());
                double pres = Double.parseDouble(presObj.get(count+"pres").toString());
                String descr = String.valueOf(descrObj.get(count+"descr"));
                day[i].setTmp(tmp);
                day[i].setHumidity(hum);
                day[i].setWindSpeed(wind);
                day[i].setPressure(pres);
                day[i].setDescription(descr);
                count+=3;
            }
        }

    }

}//класс,хранящий погоду на день
