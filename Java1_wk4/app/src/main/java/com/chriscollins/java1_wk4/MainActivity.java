package com.chriscollins.java1_wk4;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Weather mWeather;

    @InjectView(R.id.CurrentDegree) TextView mCurrentDegree;
    @InjectView(R.id.PrecipPercent) TextView mChanceRain;
    @InjectView(R.id.CurrentWeather) TextView mCurrentWeather;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;
    @InjectView(R.id.Latitude) EditText mlat;
    @InjectView(R.id.Longitude) EditText mLong;
    @InjectView(R.id.refresh_btn) ImageView mrefresh_btn;



    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        final double latitude = 36.9781;
        final double longitude = -82.5769;
        // Get the connectivity manager as a system service.
        // The Context class provides several string constants for
        // accessing various system services.

        Button enterButton = (Button) findViewById(R.id.refresh_btn);
        enterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView latView =  (TextView) findViewById(R.id.Latitude);
                TextView longView =  (TextView) findViewById(R.id.Longitude);
                String lat = latView.getText().toString();
                String lon = longView.getText().toString();
                try {
                    String apiKey = "24d605f0eaa4a369f58485ec6b6f3501";
                    String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                            "/" + lat + "," + lon;
                    String qs = URLEncoder.encode(forecastUrl, "UTF-8");
                    URL queryURL = new URL(forecastUrl + "?q=" + qs + "&format=json");
                            new PullWTask().execute(queryURL);
                } catch (Exception e) {
                    Log.e(TAG, "Invalid api: " + latView + " " + longView);
                }
            }
        });

        mProgressBar.setVisibility(View.INVISIBLE);
        mrefresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longitude);
            }
        });
        getForecast(latitude, longitude);
        //ConnectivityManager mgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            Log.i(TAG, "Main code is running");
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "24d605f0eaa4a369f58485ec6b6f3501";
        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                "/" + latitude + "," + longitude;

        if (isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();

            com.squareup.okhttp.Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mWeather = getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException | JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
        else {
            Toast.makeText(this, getString(R.string.network_unavailable_message),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mrefresh_btn.setVisibility(View.INVISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mrefresh_btn.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        mCurrentDegree.setText(mWeather.getTemperature() + "");
        mChanceRain.setText(mWeather.getPrecipChance() + "%");
        mCurrentWeather.setText(mWeather.getSummary());

       /* Drawable drawable = getResources().getDrawable(mCurrentWeather.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }*/
    }

    private Weather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        Weather weather = new Weather();
        weather.setHumidity(currently.getDouble("humidity"));
        weather.setTime(currently.getLong("time"));
        weather.setIcon(currently.getString("icon"));
        weather.setPrecipChance(currently.getDouble("precipProbability"));
        weather.setSummary(currently.getString("summary"));
        weather.setTemperature(currently.getDouble("temperature"));
        weather.setTimeZone(timezone);

        Log.d(TAG, weather.getFormattedTime());

        return weather;
    }


private void updateDisplay(Weather weather) {
    ((TextView) findViewById(R.id.CurrentDegree)).setText(weather.getTemperature());
    ((TextView) findViewById(R.id.CurrentWeather)).setText(weather.getSummary());
    ((TextView) findViewById(R.id.PrecipPercent)).setText(weather.getPrecipChance());


}


    private class PullWTask extends AsyncTask<URL, Integer, JSONObject> {

       @Override
        protected  JSONObject doInBackground(URL...urls) {

           String jsonString = "";
            for(URL queryURL : urls) {
                try{
                    URLConnection connection = queryURL.openConnection();
                    //jsonString = IOUtils.toString(connection.getInputStream());
                    break;
                } catch (Exception e) {
                    Log.e(TAG, "Could not establish a URL connection" + queryURL.toString())
                }
            }

           Log.i(TAG, "Received Data; " + jsonString);

           JSONObject apiData;

           try{
           apiData = new JSONObject(jsonString);
           } catch (Exception e) {
               Log.e(TAG, "Cannot convert API response to JSON");
               apiData = null;
           }

           try{
               apiData = (apiData != null) ? apiData.getJSONObject("query").getJSONObject("results").getJSONObject("row") : null;
               Log.i(TAG, "API JSON data received: " + apiData.toString());
           } catch (Exception e) {
               Log.e(TAG, "Could not parse data record from response: " + apiData.toString());
               apiData = null;
           }
           return null;
       }
        protected void onPostExecute(JSONObject apiData) {
           // Weather result = new Weather(apiData);
            //updateDisplay(result);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialog dialog = new AlertDialog();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}







