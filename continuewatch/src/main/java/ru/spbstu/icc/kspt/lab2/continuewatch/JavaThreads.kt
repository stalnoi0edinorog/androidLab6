package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val SECONDS = "seconds"

class JavaThreads : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var backgroundThread: Thread? = null
    private  lateinit var prefs: SharedPreferences

    private fun update() = textSecondsElapsed.post {
        textSecondsElapsed.text = "Second elapsed: " + secondsElapsed++
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("Task", "JavaThread created")
        prefs = getSharedPreferences(SECONDS, Context.MODE_PRIVATE)
    }

    override fun onStart() {
        super.onStart()
        if (prefs.contains(SECONDS))
            secondsElapsed = prefs.getInt(SECONDS, 0)
    }

    override fun onResume() {
            backgroundThread = Thread {
                try {
                    while (!Thread.currentThread().isInterrupted) {
                        Thread.sleep(1000)
                        update()
                    }
                } catch (e: InterruptedException) {
                }
            }.also {
                it.start()
            }
        super.onResume()
    }

    override fun onPause() {
        backgroundThread?.interrupt()
        prefs.edit().putInt(SECONDS, secondsElapsed).apply()
        super.onPause()
    }
}
