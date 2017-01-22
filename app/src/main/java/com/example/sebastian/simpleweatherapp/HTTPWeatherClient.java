package com.example.sebastian.simpleweatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sebastian on 2017-01-07.
 */
public class HTTPWeatherClient {
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String OPEN_WEATHER_KEY = "&appid=4486e0f55bd59ade152282ecd02c8b30";
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    public String getWeatherData(String location) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection) new URL(BASE_URL + location + OPEN_WEATHER_KEY).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            //Read the response
            StringBuffer stringBuffer = new StringBuffer();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null)
            stringBuffer.append(line + "\r\n");
            inputStream.close();
            connection.disconnect();
            return stringBuffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { if(inputStream != null)
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {if(connection != null)
                connection.disconnect();
            }
            catch (Throwable t){

            }
        }
            return null;
        }

      /* public byte[] getImage(String code){
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            String string2= IMG_URL + code+".png";
            connection = (HttpURLConnection) new URL(IMG_URL + code+".png").openConnection();
            Log.d("HTTPWeatherClient", string2);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
          *//*  connection.setDoOutput(true);*//*
            connection.connect();

            //Read response
            inputStream = connection.getInputStream();
            FlushedInputStream flushedInputStream = new FlushedInputStream(inputStream);
            Drawable d = Drawable.createFromStream(inputStream,"icon");
            Log.d("HTTP", d.toString());
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            while (inputStream.read(buffer) != -1)
                byteArrayOutputStream.write(buffer);
            return byteArrayOutputStream.toByteArray();
        }
         catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{if(inputStream != null)
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{if(connection != null)
                connection.disconnect();
            } catch(Throwable t){

            }
        }
        return null;
    }*/
    }

