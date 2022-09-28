package com.acme.kotlinintervals.intervals

import android.os.SystemClock
import android.os.SystemClock.elapsedRealtime
import android.util.Log
import com.acme.kotlinintervals.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime


class IntervalPlayer constructor (private val intervalReader: IntervalReader, private val audioPlayer: AudioPlayer){

    private val SEC_TO_MS = 1000L

    private lateinit var intervals: List<AudioInterval>

    fun playIntervals (program: Int) {
        play(program)
    }

    private fun play(program: Int) {
        intervals = intervalReader.readIntervals(program)
        var delay = intervals.map { it.duration }.sum()

        playChain( 0)

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