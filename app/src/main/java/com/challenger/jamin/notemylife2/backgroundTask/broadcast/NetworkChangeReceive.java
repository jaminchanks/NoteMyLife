package com.challenger.jamin.notemylife2.backgroundTask.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by jamin on 8/8/15.
 */
public class NetworkChangeReceive extends BroadcastReceiver {
    public static boolean isNetworkAvailable = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            isNetworkAvailable = true;
        } else {
            Toast.makeText(context, "现在处于离线状态", Toast.LENGTH_SHORT).show();
            isNetworkAvailable = false;
        }
    }
}
