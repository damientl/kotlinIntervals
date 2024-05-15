package com.acme.kotlinintervals

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.Handler
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
import android.view.WindowManager
import com.acme.kotlinintervals.databinding.ActivityMainBinding
import com.acme.kotlinintervals.service.ForePlayerService
import android.provider.Settings
import android.widget.Toast
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(),
    SecondFragment.OnProgramSelectedListener, SecondFragment.OnScreenToggle {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var foreService: ForePlayerService
    private lateinit var serviceIntent: Intent


    private val foreConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to service, cast the IBinder and get service instance
            val binder = service as ForePlayerService.LocalBinder
            foreService = binder.getService()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {}
    }

    override fun stopFore() {
        Log.i("playTAG", "stop fore")
        //Toast.makeText(this, "stopFore", Toast.LENGTH_SHORT).show()
        //foreService.stopService(serviceIntent)
        foreService.stopForeground(true)
        window.clearFlags (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setScreenBrightness(0.02f)
    }
    fun quit() {
        finishAndRemoveTask()
    }

    override fun playProgramSelected(program: Int) {

        window.addFlags (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setScreenBrightness(0.01f)
        foreService.playProgram(program)
    }

    override fun getTotalTime(program: Int) : Int {
        return foreService.getTotalTime(program)
    }

    override fun setScreen(bright: Boolean) {
        if(bright) {
            setScreenBrightness(0.3f)
        } else {
            setScreenBrightness(0.01f)
        }
    }

    private fun setScreenBrightness(value: Float) {
        val layoutpars = window.attributes
        layoutpars.screenBrightness = value
        window.attributes = layoutpars
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //start foreground service
        // An intent is an abstract description of an operation to be performed
        serviceIntent = Intent(this, ForePlayerService::class.java).also { intent ->
            Log.i("playTAG", "staart fore")
            this.applicationContext.startForegroundService(intent)
        }

        // also bind to foreground service
        /*
        Intent(this, ForePlayerService::class.java).also { intent ->
            bindService(intent, foreConnection, Context.BIND_AUTO_CREATE)
        }*/
        // todo: maybe intent?
        Executors.newSingleThreadScheduledExecutor().schedule({
            bindService(serviceIntent, foreConnection, Context.BIND_AUTO_CREATE)
        }, 1, TimeUnit.SECONDS)


        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        checkWritePermission()
    }

    fun checkWritePermission() {

        if (!Settings.System.canWrite(this)) {
            Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                data = Uri.parse("package:$packageName")
                startActivity(this)
            }
        }
        //Toast.makeText(this, "Permission already granted.", Toast.LENGTH_SHORT).show()
    }

    // called from activity livecycle
    override fun onStop() {
        super.onStop()
        foreService.stopForeground(true)
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