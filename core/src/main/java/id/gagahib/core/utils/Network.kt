package id.gagahib.core.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import androidx.annotation.RequiresApi

class Network {

    companion object Utils {

        fun isConnected(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                isConnectedNewApi(context)
            } else{
                isConnectedOld(context)
            }
        }

        @Suppress("DEPRECATION")
        fun isConnectedOld(context: Context): Boolean {
            val connManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connManager.activeNetworkInfo
            return networkInfo.isConnected
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun isConnectedNewApi(context: Context): Boolean {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            return capabilities?.hasCapability(NET_CAPABILITY_INTERNET) == true
        }
    }
}

