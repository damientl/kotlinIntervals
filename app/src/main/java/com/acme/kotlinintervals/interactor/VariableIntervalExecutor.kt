package com.example.util

import com.acme.kotlinintervals.interactor.AudioInterval
import kotlinx.coroutines.runBlocking

class VariableIntervalExecutor constructor(scheduler: MyScheduler) {
    private var stop = false
    fun playIntervals(intervals: List<AudioInterval>, taskExecutor: (Int) -> Unit){
        runBlocking {
            var i = 0
            while (i < intervals.size && !stop) {
                val elem = intervals[i]
                val audioIndex = elem.audio

                MyScheduler().schedule({
                    println("play audio: $audioIndex, sleep: ${elem.duration}")
                    taskExecutor(audioIndex)
                                       },
                    elem.duration)
                i++
            }
        }
    }
    fun stop(){
        stop = true;
    }
}