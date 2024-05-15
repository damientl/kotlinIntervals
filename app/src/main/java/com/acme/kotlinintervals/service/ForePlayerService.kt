package com.acme.kotlinintervals.service

import android.app.*
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.acme.kotlinintervals.MainActivity
import com.acme.kotlinintervals.R
import com.acme.kotlinintervals.interactor.IntervalAssets
import com.acme.kotlinintervals.interactor.PlayerFactory

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

    private lateinit var playerFactory: PlayerFactory

    private fun setup() {
        Log.i("playTAG", "setup fore")
        playerFactory = PlayerFactory(this.resources, this.applicationContext)
    }

    fun playProgram(program: Int) {
        Log.i("playTAG", "play fore")
        Toast.makeText(this, "playProgram", Toast.LENGTH_SHORT).show()
        val programResource = IntervalAssets.PROGRAM_RESOURCES[program] ?: IntervalAssets.DEFAULT_PROGRAM
        playerFactory.getPlayer().play(programResource)
    }

    fun getTotalTime(program:Int): Int{
        val programResource = IntervalAssets.PROGRAM_RESOURCES[program] ?: IntervalAssets.DEFAULT_PROGRAM
        return playerFactory.getPlayer().getTotalMinutes(programResource)
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

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show()
        playerFactory.getPlayer().stop()
    }

}