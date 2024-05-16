package com.example.util

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MyScheduler {

    private val POOLING_INTERVAL = 100L
    suspend fun schedule(task: () -> Unit, delay: Long) = coroutineScope {
        launch  {
            waitDelay(delay * 1000)
        }
        task.invoke()
    }
    private suspend fun waitDelay(delay: Long){
        val start = System.currentTimeMillis()
        var now = System.currentTimeMillis()

        while (now < start + delay) {
            delay(POOLING_INTERVAL)
            now = System.currentTimeMillis()
        }
    }
}