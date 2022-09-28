package com.acme.kotlinintervals

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.acme.kotlinintervals.databinding.ActivityMainBinding
import com.acme.kotlinintervals.service.ForePlayerService
import com.acme.kotlinintervals.service.PlayerService

class MainActivity : AppCompatActivity(), FirstFragment.OnProgramSelectedListener,
    SecondFragment.OnProgramSelectedForeListener{

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var mService: PlayerService
    private lateinit var foreService: ForePlayerService

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to service, cast the IBinder and get service instance
            val binder = service as PlayerService.LocalBinder
            mService = binder.getService()
            mService.setup()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {}
    }

    private val foreConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to service, cast the IBinder and get service instance
            val binder = service as ForePlayerService.LocalBinder
            foreService = binder.getService()
            //setup?
        }

        override fun onServiceDisconnected(arg0: ComponentName) {}
    }

    override fun onProgramSelected(item: Int) {
        Log.i("playTAG", "selected $item")
        mService.playProgram(item)
    }

    override fun stopFore() {
        Log.i("playTAG", "stop fore")
        foreService.stopForeground(true)
    }

    override fun playForeProgramSelected(program: Int) {
        foreService.playProgram(program)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Bind to LocalService
        Intent(this, PlayerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        //start foreground service
        Intent(this, ForePlayerService::class.java).also { intent ->
            Log.i("playTAG", "staart fore")


            this.applicationContext.startForegroundService(intent)
        }
        //TODO:  stopForeground()

        // also bind to foreground service
        Intent(this, ForePlayerService::class.java).also { intent ->
            bindService(intent, foreConnection, Context.BIND_AUTO_CREATE)
        }


        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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