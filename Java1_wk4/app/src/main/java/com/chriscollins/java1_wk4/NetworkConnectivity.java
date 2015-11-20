package com.chriscollins.java1_wk4;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by chriscollins on 11/19/15.
 */
public class NetworkConnectivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static boolean checkNetwork(Context context) {
        ConnectivityManager connection = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connection != null) {
            NetworkInfo networkInfo = connection.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        } else {
            Log.i(TAG, "No connection");
        }
        return false;
    }
}
