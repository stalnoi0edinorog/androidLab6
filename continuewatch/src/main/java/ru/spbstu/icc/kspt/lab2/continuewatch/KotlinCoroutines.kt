package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.android.synthetic.main.activity_main.*

class KotlinCoroutines: AppCompatActivity() {
    var secondsElapsed: Int = 0
    private  lateinit var prefs: SharedPreferences
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var job: Job


    private fun update() = textSecondsElapsed.post {
        textSecondsElapsed.text = "Second elapsed: " + secondsElapsed++
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("Task", "Coroutines created")
        prefs = getSharedPreferences(SECONDS, Context.MODE_PRIVATE)
        }

    override fun onStart() {
        super.onStart()
        if (prefs.contains(SECONDS))
            secondsElapsed = prefs.getInt(SECONDS, 0)
        Log.i("Task", "Coroutines started")
    }

    override fun onResume() {
        super.onResume()
        job = scope.launch {
            while (true) {
                delay(1000)
                update()
            }
        }
        Log.i("Task", "Coroutines resumed")
    }

    override fun onPause() {
        prefs.edit().putInt(SECONDS, secondsElapsed).apply()
        job.cancel()
        super.onPause()
    }
}