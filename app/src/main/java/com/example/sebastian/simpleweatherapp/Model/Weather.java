package com.example.sebastian.simpleweatherapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sebastian on 2017-01-07.
 */
public class Weather {

    @SerializedName("main")
    public CurrentCondition currentCondition = new CurrentCondition();
    @SerializedName("wind")
    public Wind wind = new Wind();
    @SerializedName("weather")
    public List<WeatherDescription> weatherDescription;

    public byte[] iconData;

    public class CurrentCondition {

        @SerializedName("pressure")
        private float pressure;
        @SerializedName("humidity")
        private float humidity;
        @SerializedName("temp")
        private float temperature;

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public float getPressure() {
            return pressure;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public float getHumidity() {
            return humidity;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }

    }

    public class WeatherDescription {

        private List<WeatherDescription> weatherDescription;
        @SerializedName("description")
        private String description;
        @SerializedName("icon")
        private String icon;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<WeatherDescription> getWeatherDescritpion() {
            return weatherDescription;
        }

        public void setWeatherDescription(List<WeatherDescription> weatherDescription) {
            this.weatherDescription = weatherDescription;
        }

    }

    public class Wind {
        @SerializedName("speed")
        private float speed;

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

    }


}