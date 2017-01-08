package com.example.sebastian.simpleweatherapp;


import android.util.Log;

import com.example.sebastian.simpleweatherapp.Model.Location;
import com.example.sebastian.simpleweatherapp.Model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sebastian on 2017-01-07.
 */
public class JSONParser {

    public static Weather getWeather(String data) throws JSONException {
        Weather weather = new Weather();
        Log.e("SimleWeatherApp", "w geetWeather");

        JSONObject jsonObject = new JSONObject(data);
        Location location = new Location();
        JSONObject coordObj = getObject("coord", jsonObject);
        location.setLatitude(getFloat("lat", coordObj));
        location.setLongitude(getFloat("lon", coordObj));

        JSONObject sysObj = getObject("sys", jsonObject);
        location.setCountry(getString("country", sysObj));
        location.setCity(getString("name", jsonObject));
        location.setSunrise(getInt("sunrise", sysObj));
        location.setSunset(getInt("sunset", sysObj));
        weather.location = location;

        JSONArray jsonArray = jsonObject.getJSONArray("weather");
        JSONObject jsonWeather = jsonArray.getJSONObject(0);
        weather.currentCondition.setWeatherId(getInt("id", jsonWeather));
        weather.currentCondition.setCondition(getString("main", jsonWeather));
        weather.currentCondition.setDescr(getString("description", jsonWeather));
        weather.currentCondition.setIcon(getString("icon", jsonWeather));

        JSONObject mainObj = getObject("main", jsonObject);
        weather.currentCondition.setHumidity(getInt("humidity", mainObj));
        weather.currentCondition.setPressure(getInt("pressure", mainObj));
        weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
        weather.temperature.setTemp(getFloat("temp", mainObj));

        // Wind
        JSONObject windObj = getObject("wind", jsonObject);
        weather.wind.setSpeed(getFloat("speed", windObj));
        weather.wind.setDeg(getFloat("deg", windObj));

        // Clouds
        JSONObject cloudsObj = getObject("clouds", jsonObject);
        weather.clouds.setPerc(getInt("all", cloudsObj));

        return weather;
    }


    private static JSONObject getObject(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject(tagName);
    }

    private static String getString(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString(tagName);
    }

    private static Float getFloat(String tagName, JSONObject jsonObject) throws JSONException {
        return (float) jsonObject.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt(tagName);
    }
}
