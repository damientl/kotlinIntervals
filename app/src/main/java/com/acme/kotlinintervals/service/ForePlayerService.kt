package com.acme.kotlinintervals.service

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import com.acme.kotlinintervals.interactor.IntervalAssets
import com.acme.kotlinintervals.interactor.PlayerFactory

class ForePlayerService constructor(val resources: Resources, private val appContext: Context) {


    private val playerFactory = PlayerFactory(this.resources, this.appContext)

    fun playProgram(program: Int) {
        Log.i("playTAG", "play fore")
        //Toast.makeText(appContext, "playProgram", Toast.LENGTH_SHORT).show()
        val programResource =
            IntervalAssets.PROGRAM_RESOURCES[program] ?: IntervalAssets.DEFAULT_PROGRAM
        playerFactory.getPlayer().play(programResource)
    }

    fun getTotalTime(program: Int): Int {
        val programResource =
            IntervalAssets.PROGRAM_RESOURCES[program] ?: IntervalAssets.DEFAULT_PROGRAM
        return playerFactory.getPlayer().getTotalMinutes(programResource)
    }

    fun onDestroy() {
        Toast.makeText(appContext, "Destroy", Toast.LENGTH_SHORT).show()
        playerFactory.getPlayer().stop()
    }

}