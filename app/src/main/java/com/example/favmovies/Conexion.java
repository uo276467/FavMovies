package com.example.favmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class Conexion {

    private Context mContext;

    public Conexion(Context mContext){
        mContext = mContext;
    }

    public boolean compruebaConexion(){
        boolean conectado = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        conectado = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return conectado;
    }

}
