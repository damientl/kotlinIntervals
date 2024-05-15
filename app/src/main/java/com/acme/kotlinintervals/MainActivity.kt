package com.acme.kotlinintervals

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.acme.kotlinintervals.databinding.ActivityMainBinding
import com.acme.kotlinintervals.service.ForePlayerService
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(),
    SecondFragment.OnProgramSelectedListener, SecondFragment.OnScreenToggle {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var foreService:ForePlayerService



    override fun stopFore() {
        Log.i("playTAG", "stop fore")
        //Toast.makeText(this, "stopFore", Toast.LENGTH_SHORT).show()
        foreService.onDestroy()

        window.clearFlags (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setScreenBrightness(0.02f)
        quit()
    }
    fun quit() {
        finishAndRemoveTask()
    }

    override fun playProgramSelected(program: Int) {

        window.addFlags (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setScreenBrightness(0.01f)
        thread{

            foreService.playProgram(program)
        }


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

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        checkWritePermission()

        foreService = ForePlayerService(resources, applicationContext)

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
        foreService.onDestroy()
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