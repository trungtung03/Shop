package com.example.appchuadatten.checkConnect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnect {
    public static boolean checkConnection(Context context) {
//        boolean haveInternet = false;
//        boolean haveConnectMobile = false;
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
//        for (NetworkInfo ni : networkInfo) {
//            if(ni.getTypeName().equalsIgnoreCase("WIFI")){
//                if(ni.isConnected()){
//                    haveInternet = true;
//                }
//            }if (ni.getTypeName().equalsIgnoreCase("MOBILE")){
//                if(ni.isConnected()){
//                    haveConnectMobile = true;
//                }
//            }
//        }
//        return haveInternet || haveConnectMobile;

        boolean haveConnectInternet = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            haveConnectInternet = true;
        }
        return haveConnectInternet;
    }
}
