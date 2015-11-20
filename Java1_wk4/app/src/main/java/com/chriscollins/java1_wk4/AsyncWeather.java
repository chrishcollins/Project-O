package com.chriscollins.java1_wk4;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by chriscollins on 11/19/15.
 */
public class AsyncWeather extends AsyncTask<URL, Integer, JSONObject>{

    WeatherArray<Weather> weather;

    @Override
    protected JSONObject doInBackground(URL... params) {
        return null;
    }
}
