package com.acme.kotlinintervals.intervals

import android.content.res.Resources

class IntervalReader constructor (private val resources: Resources){
    fun readIntervals(program: Int): List<AudioInterval> {
        return resources.openRawResource(program).reader().readLines().map{ mapToInterval(it) }
    }

    private fun mapToInterval(line: String): AudioInterval {
        val lineData = line.split(',');
        return AudioInterval(lineData[0].toLong() ,lineData[1].toInt())
    }
}