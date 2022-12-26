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


class ConnectivityChangeReceiver
    //: BroadcastReceiver()
{
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: MyNetworkCallback

    fun Register(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val request = NetworkRequest.Builder().build()
            networkCallback = MyNetworkCallback(context)
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }

    fun Unregister() {
        if(networkCallback != null && connectivityManager != null){
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    //override fun onReceive(context: Context, intent: Intent) {
    //    Toast.makeText(context, "received", Toast.LENGTH_SHORT).show()
    //
    //    val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //    val networkInfo = connMgr.activeNetworkInfo
    //    if(networkInfo!!.typeName.equals("WIFI", true)) { //判断是否为WiFi
    //        val mWifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    //        val mWifiInfo: WifiInfo = mWifiManager.connectionInfo
    //
    //        val wifiSsid=mWifiInfo.ssid
    //        Toast.makeText(context, "SSID: " + wifiSsid, Toast.LENGTH_SHORT).show()
    //    }
    //}

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    class MyNetworkCallback constructor(context: Context) : NetworkCallback() {
        private lateinit var context: Context

        init {
            this.context = context
        }

        override fun onAvailable(network: Network) {
            println("onAvailable(network: Network)")
            super.onAvailable(network)

                var wifiSsid: String
            //if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                val mWifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val mWifiInfo: WifiInfo = mWifiManager.connectionInfo
                wifiSsid = mWifiInfo.ssid

                val networkId = mWifiInfo.networkId

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    println("缺少权限：定位")
                    Toast.makeText(context, "缺少权限：定位", Toast.LENGTH_SHORT).show()
                    return
                }
                val configuredNetworks = mWifiManager.configuredNetworks

                for (wifiConfiguration in configuredNetworks){
                    if (wifiConfiguration.networkId==networkId){
                        wifiSsid=wifiConfiguration.SSID;
                        wifiSsid=wifiSsid.substring(1, wifiSsid.length - 1)
                        break;
                    }
                }
            //}else{
            //    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            //    val networkInfo = connectivityManager.activeNetworkInfo
            //    wifiSsid = networkInfo!!.extraInfo
            //}

            println("SSID: " + wifiSsid)
            Toast.makeText(context, "SSID: " + wifiSsid, Toast.LENGTH_SHORT).show()
            if(wifiSsid=="AndroidWifi"){
                println("Detected home wifi!")
                Toast.makeText(context, "Detected home wifi!", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onLost(network: Network) {
            println("onLost(network: Network)")
            super.onLost(network)
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            println("onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities)")
            super.onCapabilitiesChanged(network, networkCapabilities)
            //网络变化时，这个方法会回调多次
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    println("TRANSPORT_WIFI")
                } else {
                    println("TRANSPORT_CELLULAR")
                }
            }
        }
    }
}