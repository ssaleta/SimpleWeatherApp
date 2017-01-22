package com.example.sebastian.simpleweatherapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sebastian on 2017-01-07.
 */
public class Location {

    @SerializedName("sys")
    public Country country = new Country();

    @SerializedName("name")
    private String city;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public class Country{

        @SerializedName("country")
        private String country;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
