package com.acme.kotlinintervals.intervals

import android.content.Context
import android.media.MediaPlayer

import android.util.Log

class AudioPlayer constructor (val audios: List<Int>, val context: Context){

    val loadedAudios = mutableMapOf<Int, MediaPlayer>()

    fun playAudio(audio: Int) {
        Log.i("playTAG", "play res: ${audios[audio]}")

        val player = findPlayer(audio)
        player.start()
    }

    fun findPlayer(audio: Int): MediaPlayer {
        return loadedAudios[audio] ?: createPlayer(audio)
    }

    private fun createPlayer(audio: Int): MediaPlayer{
        val player = MediaPlayer.create(context, audios[audio])
        loadedAudios.put(audio, player)
        return player
    }
}