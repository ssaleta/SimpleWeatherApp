package com.example.sebastian.simpleweatherapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.sebastian.simpleweatherapp.Model.Location;
import com.example.sebastian.simpleweatherapp.Model.Weather;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String OPEN_WEATHER_KEY = "&appid=4486e0f55bd59ade152282ecd02c8b30";
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    @BindView(R.id.weatherImg)
    ImageView weatherImg;
    @BindView(R.id.city_name)
    TextView cityname;
    @BindView(R.id.condition)
    TextView condition;
    @BindView(R.id.pressure)
    TextView pressure;
    @BindView(R.id.wind_speed)
    TextView windSpeed;
    @BindView(R.id.temperature)
    TextView temperature;
    @BindView(R.id.humidity)
    TextView humidity;
    @BindView(R.id.location)
    EditText setLocation;
    @BindView(R.id.checkWeatherBtn)
    Button checkWeatherBtn;

    private HTTPRequestHandler httpRequestHandler;
    private Weather weather;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        city = "Czestochowa,PL";
        httpRequestHandler = HTTPRequestHandler.getInstance();
        httpRequestHandler.init(getApplicationContext());
        httpRequestHandler.sendGetRequest(BASE_URL + city + OPEN_WEATHER_KEY, getResponseListener(), getErrorListener());

        setLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(setLocation.getApplicationWindowToken(), inputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
        checkWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setLocation.getText().toString() != null) {
                    city = setLocation.getText().toString();
                }
                httpRequestHandler.sendGetRequest(BASE_URL + city + OPEN_WEATHER_KEY, getResponseListener(), getErrorListener());
            }
        });

    }

    private Response.Listener getResponseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        };
    }

    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT);
            }
        };
    }

    public void showJSON(String json) {
        Gson gson = new Gson();
        Weather weather = gson.fromJson(json, Weather.class);
        Location location = gson.fromJson(json, Location.class);
        initializeView(weather, location);

    }

    public void initializeView(Weather weather, Location location) {

        Picasso.with(this)
                .load(IMG_URL + weather.weatherDescription.get(0).getIcon() + ".png")
                .into(weatherImg);
        DecimalFormat oneDecimalPlace = new DecimalFormat("##.#");
        cityname.setText(location.getCity() + "," + location.country.getCountry());
        condition.setText(weather.weatherDescription.get(0).getDescription());
        windSpeed.setText(Float.toString(weather.wind.getSpeed()) + " m/s");
        humidity.setText(Float.toString(weather.currentCondition.getHumidity()) + " %");
        pressure.setText(Float.toString(weather.currentCondition.getPressure()) + " hPa");
        temperature.setText(oneDecimalPlace.format(weather.currentCondition.getTemperature() - 272.15) + " °C");
    }
}


