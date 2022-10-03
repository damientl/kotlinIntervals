package com.acme.kotlinintervals.interactor

import android.content.res.Resources

class IntervalReader constructor (private val resources: Resources){
    fun readIntervals(programResource: Int): List<AudioInterval> {
        return resources.openRawResource(programResource).reader().readLines().map{ mapToInterval(it) }
    }

    private fun mapToInterval(line: String): AudioInterval {
        val lineData = line.split(',');
        return AudioInterval(lineData[0].toLong() ,lineData[1].toInt())
    }
}