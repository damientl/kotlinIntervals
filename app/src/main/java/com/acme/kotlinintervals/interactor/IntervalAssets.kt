package com.acme.kotlinintervals.interactor

import com.acme.kotlinintervals.R

object IntervalAssets {

    val AUDIO_RESOURCES = listOf(
        R.raw.piano, R.raw.piano2, R.raw.`in`, R.raw.out, R.raw.hold, R.raw.assobio_fim,
        R.raw.toco)

    val PROGRAM_RESOURCES = mapOf(
        10 to R.raw.prog10s,
        5 to R.raw.prog5s
    )
    val DEFAULT_PROGRAM = R.raw.prog10s
}