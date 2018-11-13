package com.example.user.hw1

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity()
{
    val timeToStart = 10000
    var isTimerStarted = false
    private val timeKey = "timeKey"
    private val isTimerStartedKey = "isTimerStartedKey"
    private var time : Long = 0
    lateinit var timer : CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null)
        {
            isTimerStarted = savedInstanceState.getBoolean(isTimerStartedKey)
            timer = createTimer(timeToStart - savedInstanceState.getLong(timeKey))
            if (!isTimerStarted)
            {
                timer.start()
                isTimerStarted = true
            }
        }
        else
        {
            timer = createTimer(timeToStart.toLong())
            if (!isTimerStarted)
            {
                timer.start()
                isTimerStarted = true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        isTimerStarted = false
        outState.putLong(timeKey, time)
        outState.putBoolean(isTimerStartedKey, isTimerStarted)
        timer.cancel()
        super.onSaveInstanceState(outState)
    }

    private fun createTimer(seconds:Long): CountDownTimer
    {
        val timer = object : CountDownTimer(seconds, 1000)
        {
            override fun onTick(ticks: Long)
            {
                time = timeToStart - ticks
            }

            override fun onFinish()
            {
                timer.cancel()
                isTimerStarted = false
                val intent =Intent(this@MainActivity, Activity2::class.java)
                startActivity(intent)
                this@MainActivity.finish()
            }
        }
        return timer
    }


}
