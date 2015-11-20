package com.chriscollins.Java1Wk_4;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.InjectView;


/**
 * Created by chriscollins on 11/17/15.
 */
public class NetworkCheck {
    private CurrentWeather mCurrentWeather;
    private AlertDialogFragment alertDialogFrag;
    private MainActivity mainActiv;
    @InjectView(R.id.currentDegree) TextView mCurrentDegree;
    @InjectView(R.id.chanceRain) TextView mChanceRain;
    @InjectView(R.id.tomorrowsWeather) TextView mTomorrowsWeather;
    @InjectView(R.id.refresh_btn) Button mrefresh_btn;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;
    @InjectView(R.id.Latitude)
    EditText mLat;
    @InjectView(R.id.Longitude) EditText mLong;
    public static final String TAG = MainActivity.class.getSimpleName();

    public void checkNetwork() {

          /*  String apiKey = "24d605f0eaa4a369f58485ec6b6f3501";
            String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + mLat + "," + mLong;



        if (isNetworkAvailable()) {


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
            Toast.makeText(this, getString(R.string.no_network),
                    Toast.LENGTH_LONG).show();
        }*/
    }


   /* private boolean isNetworkAvailable() {
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
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    } */



}

