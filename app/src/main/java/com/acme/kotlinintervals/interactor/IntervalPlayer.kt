package com.acme.kotlinintervals.interactor

interface IntervalPlayer {
    fun play(programResource: Int)
    fun getTotalMinutes(programResource: Int): Int
}