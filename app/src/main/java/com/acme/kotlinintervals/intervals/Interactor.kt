package com.acme.kotlinintervals.intervals

import android.content.Context
import android.content.res.Resources
import com.acme.kotlinintervals.R

class Interactor constructor (val resources: Resources, val appContext: Context){

    val audioResList = listOf(R.raw.piano, R.raw.piano2, R.raw.`in`, R.raw.out, R.raw.hold, R.raw.assobio_fim,
        R.raw.toco)
    val reader = IntervalReader(this.resources)
    val player = AudioPlayer(audioResList, this.appContext)
    var intervalPlayer = IntervalPlayer(reader, player)

    fun play(program: Int) {
        intervalPlayer.playIntervals(program)
    }
}