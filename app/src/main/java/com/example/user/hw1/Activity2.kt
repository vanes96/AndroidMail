package com.example.user.hw1

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import java.util.*

class Activity2 : AppCompatActivity()
{
    private val secondsValueKey: String = "secondsValueKey"
    private val isTimerStartedKey: String = "isTimerStartedKey"
    private val timeToStart = 1000
    private val ten: Int = 10
    private val hundred: Int = 100
    private val second: Int = 1000
    private var secondsValue: Int = 0
    private var secondsText: TextView? = null
    private var button: Button? = null
    private var isTimerStarted: Boolean = false
    private var numbers: HashMap<Int, String> = hashMapOf(
            0 to "ноль", 1 to "один", 2 to "два", 3 to "три", 4 to "четыре", 5 to "пять",
            6 to "шесть", 7 to "семь", 8 to "восемь", 9 to "девять", 10 to "десять",
            11 to "одиннадцать", 12 to "двенадцать", 13 to "тринадцать", 14 to "четырнадцать", 15 to "пятнадцать",
            16 to "шестнадцать", 17 to "семнадцать", 18 to "восемнадцать", 19 to "девятнадцать", 20 to "двадцать",
            30 to "тридцать", 40 to "сорок", 50 to "пятьдесят", 60 to "шестьдесят", 70 to "семьдесят",
            80 to "восемьдесят", 90 to "девяносто", 100 to "сто", 200 to "двести",  300 to "триста",
            400 to "четыреста",  500 to "пятьсот",  600 to "шестьсот",  700 to "семьсот", 800 to "восемьсот",
            900 to "девятьсот",  1000 to "тысяча")

    lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)
        secondsText = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        button?.setOnClickListener {
            if (isTimerStarted)
                stopTimer()
            else
                startTimer(timeToStart)
        }

        if (savedInstanceState != null)
        {
            isTimerStarted = savedInstanceState.getBoolean(isTimerStartedKey)
            secondsValue = savedInstanceState.getInt(secondsValueKey)
            secondsText?.text = getNumberName(secondsValue)

            if (secondsValue == 1000)
                isTimerStarted = false

            if(isTimerStarted)
                startTimer(1000 - secondsValue)
        }
        else
        {
            timer = createTimer(timeToStart)
        }
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putBoolean(isTimerStartedKey, isTimerStarted)
        outState.putInt(secondsValueKey, secondsValue)
        super.onSaveInstanceState(outState)
    }

    private fun startTimer(time: Int)
    {
        //timer.cancel()
        timer = createTimer(time)
        timer.start()
        isTimerStarted = true
        button?.text = "Stop"
    }

    private fun stopTimer()
    {
        timer.onFinish()
    }

    private fun createTimer(time: Int): CountDownTimer
    {
        var timer = object : CountDownTimer(time * second.toLong(), second.toLong())
        {
            override fun onTick(ticks: Long)
            {
                secondsValue = second - ticks.toInt() / second
                secondsText?.text = getNumberName(secondsValue)
            }

            override fun onFinish() {
                isTimerStarted = false
                button?.text = "Start"
                cancel()
            }
        }
        return timer
    }

    fun getNumberName(time: Int): String
    {
        var remainder = time
        var numberName = ""

        if(remainder >= hundred)
        {
            numberName += numbers[remainder / hundred * hundred] + " "
            remainder %= hundred

        }

        if (remainder in 20..99)
        {
            numberName += numbers[remainder / ten * ten] + " "
            remainder %= ten
        }

        if(remainder in 0..19)
        {
            if (numberName == "" && remainder == 0 || remainder != 0)
                numberName += numbers[remainder] + " "
        }

        return numberName
    }

//    override fun onStop() {
//        var lastState = isTimerStarted
//        stopTimer()
//        isTimerStarted = lastState
//        super.onStop()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (isTimerStarted)
//            startTimer(1000 - secondsValue)
//    }
}

