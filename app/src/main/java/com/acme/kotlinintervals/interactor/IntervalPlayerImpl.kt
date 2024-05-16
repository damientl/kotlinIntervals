package com.acme.kotlinintervals.interactor

import android.os.SystemClock
import android.util.Log
import com.acme.kotlinintervals.audio.AudioPlayer
import com.example.util.MyScheduler
import com.example.util.VariableIntervalExecutor
import java.util.*


class IntervalPlayerImpl constructor (private val intervalReader: IntervalReader,
                                      private val audioPlayer: AudioPlayer) : IntervalPlayer{

    // TODO: make interval Reader and audio player interfaces, to make this component more stable

    private val S_TO_MIN = 60L
    private val SEC_TO_MS = 1000L

    private lateinit var intervals: List<AudioInterval>
    private val variableIntervalExecutor = VariableIntervalExecutor(MyScheduler())

    override fun play(programResource: Int) {
        intervals = intervalReader.readIntervals(programResource)
        var delay = intervals.map { it.duration }.sum()

        //playChain( 0)

        variableIntervalExecutor.playIntervals(intervals,
            {
                audioPlayer.playAudio(it)
            })

        Log.i("playTAG", "total delay:${delay / S_TO_MIN}min")
        SystemClock.sleep(delay)
    }

    override fun getTotalMinutes(programResource: Int): Int {
        intervals = intervalReader.readIntervals(programResource)
        val delay = intervals.map { it.duration }.sum()

        return (delay / S_TO_MIN).toInt();
    }

    override fun stop(){
        variableIntervalExecutor.stop()
    }
}