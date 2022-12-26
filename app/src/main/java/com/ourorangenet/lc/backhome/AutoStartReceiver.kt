package com.ourorangenet.lc.backhome

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat


class AutoStartReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "received", Toast.LENGTH_SHORT).show()

        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        if(networkInfo!!.typeName.equals("WIFI", true)) { //判断是否为WiFi
            val mWifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val mWifiInfo: WifiInfo = mWifiManager.connectionInfo

            val wifiSsid=mWifiInfo.ssid
            Toast.makeText(context, "SSID: " + wifiSsid, Toast.LENGTH_SHORT).show()
        }
    }
}