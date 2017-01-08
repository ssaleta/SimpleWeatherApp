package com.example.sebastian.simpleweatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sebastian.simpleweatherapp.Model.Weather;

import org.json.JSONException;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String city = "Wroclaw,PL";
        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});
        checkWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = setLocation.getText().toString();
                Log.d("button", city);
                JSONWeatherTask task = new JSONWeatherTask();
                task.execute(new String[]{city});
            }
        });

    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = new HTTPWeatherClient().getWeatherData(params[0]);


            try {
                weather = JSONParser.getWeather(data);
               /* weather.iconData = new HTTPWeatherClient().getImage(weather.currentCondition.getIcon());*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
           /* if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap image = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                weatherImg.setImageBitmap(image);*/
            cityname.setText(weather.location.getCity() + "," + weather.location.getCountry());
            condition.setText(weather.currentCondition.getCondition());
            windSpeed.setText(Float.toString(weather.wind.getSpeed()) + " m/s");
            DecimalFormat oneDecimalPlace = new DecimalFormat("##.#");
            //Kelvin to Celsjus
            temperature.setText(oneDecimalPlace.format(weather.temperature.getTemp() - 272.15) + " °C");
            humidity.setText(Float.toString(weather.currentCondition.getHumidity()) + " %");
            pressure.setText(Float.toString(weather.currentCondition.getPressure()) + " hPa");
        }
    }

}
