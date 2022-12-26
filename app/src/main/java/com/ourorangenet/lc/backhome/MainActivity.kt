package com.ourorangenet.lc.backhome

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.getSystemService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ourorangenet.lc.backhome.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var connectivityChangeReceiver: ConnectivityChangeReceiver
    //private lateinit var localBroadcastManager: LocalBroadcastManager
    //private lateinit var mReceiver: ConnectivityChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        println("onCreate(savedInstanceState: Bundle?)")

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //mReceiver = ConnectivityChangeReceiver()
        //val intentFilter = IntentFilter()
        //intentFilter.addAction(Intent.ACTION_MANAGE_NETWORK_USAGE)
        //localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)
        //localBroadcastManager.registerReceiver(mReceiver, intentFilter)
        connectivityChangeReceiver = ConnectivityChangeReceiver()
        connectivityChangeReceiver.Register(applicationContext)
    }

    override fun onDestroy() {
        println("onDestroy()")

        super.onDestroy()

        //if(mReceiver != null){
        //    localBroadcastManager.unregisterReceiver(mReceiver)
        //}
        if(connectivityChangeReceiver != null) {
            connectivityChangeReceiver.Unregister()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}