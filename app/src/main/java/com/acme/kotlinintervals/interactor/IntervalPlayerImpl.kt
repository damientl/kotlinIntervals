package com.acme.kotlinintervals.interactor

import android.os.SystemClock
import android.util.Log
import com.acme.kotlinintervals.audio.AudioPlayer
import java.util.*


class IntervalPlayerImpl constructor (private val intervalReader: IntervalReader,
                                      private val audioPlayer: AudioPlayer) : IntervalPlayer{

    // TODO: make interval Reader and audio player interfaces, to make this component more stable

    private val S_TO_MIN = 60L
    private val SEC_TO_MS = 1000L

    private lateinit var intervals: List<AudioInterval>

    override fun play(programResource: Int) {
        intervals = intervalReader.readIntervals(programResource)
        var delay = intervals.map { it.duration }.sum()

        playChain( 0)

        Log.i("playTAG", "total delay:${delay / S_TO_MIN}min")
        SystemClock.sleep(delay)
    }

    private fun playChain(current: Int) {
        if(current >= intervals.size) {
            return
        }
        intervals[current].also {
            Log.i("playTAG", "wait:$it ")
            sleepExactly(it.duration * SEC_TO_MS, it.audio, current)
        }
    }

    private fun sleepExactly(delay: Long, audio: Int, currentIndex: Int) {

        val timerTask = object : TimerTask() {
            override fun run() {
                audioPlayer.playAudio(audio)
                playChain( currentIndex + 1)
            }
        }

        Timer().schedule(timerTask, delay)
    }
}