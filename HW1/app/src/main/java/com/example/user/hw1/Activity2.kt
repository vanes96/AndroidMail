package com.example.user.hw1

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import java.util.*

class Activity2 : AppCompatActivity()
{
    private val currentTimeKey: String = "currentTimeKey"
    private val totalTime = 1000
    private val startTime = 0
    private val second: Int = 1000
    private val hundred: Int = 100
    private val ten: Int = 10
    private var currentTime: Int = startTime
    private var numbers: HashMap<Int, String> = hashMapOf(
            0 to "ноль", 1 to "один", 2 to "два", 3 to "три", 4 to "четыре", 5 to "пять",
            6 to "шесть", 7 to "семь", 8 to "восемь", 9 to "девять", 10 to "десять",
            11 to "одиннадцать", 12 to "двенадцать", 13 to "тринадцать", 14 to "четырнадцать", 15 to "пятнадцать",
            16 to "шестнадцать", 17 to "семнадцать", 18 to "восемнадцать", 19 to "девятнадцать", 20 to "двадцать",
            30 to "тридцать", 40 to "сорок", 50 to "пятьдесят", 60 to "шестьдесят", 70 to "семьдесят",
            80 to "восемьдесят", 90 to "девяносто", 100 to "сто", 200 to "двести",  300 to "триста",
            400 to "четыреста",  500 to "пятьсот",  600 to "шестьсот",  700 to "семьсот", 800 to "восемьсот",
            900 to "девятьсот",  1000 to "тысяча")
    private var currentTimeText: TextView? = null
    private var button: Button? = null
    var timer: CountDownTimer? = null

    // ====================== Overrides ======================
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)
        currentTimeText = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        button?.setOnClickListener {
            if (isTimerStarted())
                stopTimer()
            else
                startTimer(startTime)
        }

        if (savedInstanceState != null)
        {
            currentTime = savedInstanceState.getInt(currentTimeKey)
            updateTimerText()

            if(isTimerStarted())
                startTimer(currentTime)
            else
                updateTimerText()
        }
        else
        {
            timer = createTimer(totalTime - startTime)
        }
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putInt(currentTimeKey, currentTime)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        var lastState = currentTime
        stopTimer()
        currentTime = lastState
        updateTimerText()
        if (isTimerStarted())
            setButtonText("Stop")
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        if (isTimerStarted()) {
            timer?.cancel()
            startTimer(currentTime)
        }
    }
    // =======================================================

    private fun isTimerStarted(): Boolean = currentTime in (startTime + 1)..(totalTime - 1)

    private fun createTimer(time: Int): CountDownTimer = object : CountDownTimer(time * second.toLong(), second.toLong())
    {
        override fun onTick(ticks: Long)
        {
            currentTime = totalTime - ticks.toInt() / second  // totalTime - секунд всего
            updateTimerText()
        }

        override fun onFinish() {
            setButtonText("Start")
            timer?.cancel()
        }
    }

    private fun startTimer(time: Int)
    {
        timer = createTimer(totalTime - time)
        timer?.start()
        setButtonText("Stop")
    }

    private fun stopTimer()
    {
        currentTime = startTime
        setButtonText("Start")
        updateTimerText()
        timer?.cancel()
    }

    private fun updateTimerText()
    {
        currentTimeText?.text = getNumberName(currentTime)
    }

    private fun setButtonText(text: String)
    {
        button?.text = text
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
}