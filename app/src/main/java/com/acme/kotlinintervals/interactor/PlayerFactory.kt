package com.acme.kotlinintervals.interactor

import android.content.Context
import android.content.res.Resources
import com.acme.kotlinintervals.audio.AudioPlayer

class PlayerFactory constructor (val resources: Resources, private val appContext: Context){

    private val reader = IntervalReader(this.resources)
    private val player = AudioPlayer(IntervalAssets.AUDIO_RESOURCES, this.appContext)
    var intervalPlayerImpl = IntervalPlayerImpl(reader, player)

    fun getPlayer(): IntervalPlayer {
        return intervalPlayerImpl
    }
}