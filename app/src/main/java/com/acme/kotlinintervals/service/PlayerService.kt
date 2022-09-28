package com.acme.kotlinintervals.service

import android.app.*
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.acme.kotlinintervals.MainActivity
import com.acme.kotlinintervals.R
import com.acme.kotlinintervals.intervals.Interactor
import kotlin.concurrent.thread

class PlayerService : Service() {


    // Binder given to clients
    private val binder = LocalBinder()
    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): PlayerService = this@PlayerService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private var interactor: Interactor? = null

    fun setup() {
        interactor = Interactor(this.resources, this.applicationContext)
    }

    private fun startPlayer(programResource: Int) {
        Log.i("playTAG", "startPlayer")
        interactor!!.play(programResource)

    }

    fun playProgram(program: Int) {
        val progResources = mapOf(
            10 to R.raw.prog10s,
            5 to R.raw.prog5s
        )
        startPlayer(progResources[program]!!)

        stopSelf()
    }

}