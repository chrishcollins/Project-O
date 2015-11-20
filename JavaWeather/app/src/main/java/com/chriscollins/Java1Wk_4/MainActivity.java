package com.chriscollins.Java1Wk_4;
/**
 * Created by chriscollins on 11/17/15.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private CurrentWeather mCurrentWeather;
    private NetworkCheck mNetworkCheck;


    @InjectView(R.id.currentDegree) TextView mCurrentDegree;
    @InjectView(R.id.chanceRain) TextView mChanceRain;
    @InjectView(R.id.tomorrowsWeather) TextView mTomorrowsWeather;
    @InjectView(R.id.refresh_btn) Button mrefresh_btn;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;
    @InjectView(R.id.Latitude) EditText mLat;
    @InjectView(R.id.Longitude) EditText mLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        String apiKey = "24d605f0eaa4a369f58485ec6b6f3501";
        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                "/" + mLat + "," + mLong;

        mProgressBar.setVisibility(View.INVISIBLE);

        final EditText latitude = mLat;
        final EditText longitude = mLong;

        mrefresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView latView =  (TextView) findViewById(R.id.Latitude);
                TextView longView =  (TextView) findViewById(R.id.Longitude);
                String mLat = latView.getText().toString();
                String mLong = longView.getText().toString();
                getForecast(latitude, longitude);
                //mNetworkCheck.checkNetwork();
                //NetworkCheck.checkNetwork(this);
                Toast.makeText(MainActivity.this, R.string.no_network, Toast.LENGTH_LONG);
                String apiKey = "24d605f0eaa4a369f58485ec6b6f3501";
                String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                        "/" + mLat + "," + mLong;
                if (isNetworkAvailable()) {
                    toggleRefresh();

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(forecastUrl)
                            .build();

                    Call call = client.newCall(request);
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
                                    mCurrentWeather = getCurrentDetails(jsonData);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            updateDisplay();
                                        }
                                    });
                                } else {
                                    alertUserAboutError();
                                }
                            } catch (IOException | JSONException e) {
                                Log.e(TAG, "Exception caught: ", e);
                            }
                        }
                    });
                }
                else {

                }
            }
        });

        getForecast(latitude, longitude);

        Log.d(TAG, "Main UI code is running!");
    }

    public void getForecast(EditText latitude, EditText longitude) {
        String apiKey = "24d605f0eaa4a369f58485ec6b6f3501";
        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                "/" + latitude + "," + longitude;


    }



    private void updateDisplay() {
        mCurrentDegree.setText(mCurrentWeather.getTemperature() + "");
        mChanceRain.setText(mCurrentWeather.getPrecipChance() + "%");
        mTomorrowsWeather.setText(mCurrentWeather.getSummary());


    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setTimeZone(timezone);

        Log.d(TAG, currentWeather.getFormattedTime());

        return currentWeather;
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


    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
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
}














