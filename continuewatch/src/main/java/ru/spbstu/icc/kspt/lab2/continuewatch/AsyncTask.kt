package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.spbstu.icc.kspt.lab2.continuewatch.databinding.ActivityMainBinding

class AsyncTask: AppCompatActivity() {
    var secondsElapsed: Int = 0
    private  lateinit var prefs: SharedPreferences
    private  lateinit var counter: Counter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("Task", "Activity created")
        prefs = getSharedPreferences(SECONDS, Context.MODE_PRIVATE)
    }

    override fun onStart() {
        super.onStart()
        if (prefs.contains(SECONDS))
            secondsElapsed = prefs.getInt(SECONDS, 0)
        Log.i("Task", "Activity started")
    }

    override fun onResume() {
        super.onResume()
        counter = Counter(binding)
        counter.execute()
        Log.i("Task", "Activity resumed")
    }

    override fun onPause() {
        prefs.edit().putInt(SECONDS, secondsElapsed).apply()
        counter.cancel(false)
        super.onPause()
    }

    inner class Counter(private val binding: ActivityMainBinding): android.os.AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            while (!isCancelled) {
                Thread.sleep(1000)
                publishProgress()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
            binding.textSecondsElapsed.text = "Second elapsed: " + secondsElapsed++
            Log.i("Task", "text: " + textSecondsElapsed.hashCode())
        }
    }
}