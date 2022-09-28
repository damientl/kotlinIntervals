package com.acme.kotlinintervals.service

import android.app.*
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.acme.kotlinintervals.MainActivity
import com.acme.kotlinintervals.R
import com.acme.kotlinintervals.intervals.Interactor
import kotlin.concurrent.thread

class ForePlayerService: Service () {

    // Binder given to clients
    private val binder = LocalBinder()
    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): ForePlayerService = this@ForePlayerService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private var interactor: Interactor? = null

    fun setup() {
        Log.i("playTAG", "setup fore")
        interactor = Interactor(this.resources, this.applicationContext)
    }

    private fun startPlayer(programResource: Int) {
        Log.i("playTAG", "startPlayer")

            interactor!!.play(programResource)

    }

    fun playProgram(program: Int) {

        Log.i("playTAG", "play fore")

        val progResources = mapOf(
            10 to R.raw.prog10s,
            5 to R.raw.prog5s
        )
        startPlayer(progResources[program]!!)

    }


    // for foreground service
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i("playTAG", "onstart command fore")

        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE)
            }

        // Create the NotificationChannel
        val name = "channel default"
        val descriptionText = "desc"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val CHANNEL_ID = "232"
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        mChannel.description = descriptionText
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)

        val notification: Notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(getText(R.string.notification_title))
            .setContentText("blabla")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setTicker("ticker")
            .build()

        //val input = intent?.getStringExtra(Constants.inputExtra)

        // Notification ID cannot be 0.
        val ONGOING_NOTIFICATION_ID = 1
        startForeground(ONGOING_NOTIFICATION_ID, notification)

        setup()

        return Service.START_STICKY
    }

}