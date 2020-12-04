package com.example.lab6

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.lab6.databinding.ActivityMainBinding
import java.io.InputStream
import java.net.URL

const val URL = "https://i.ibb.co/4j5sZsJ/cat.jpg"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.button.setOnClickListener {
            DownloadImageTask(binding).execute(URL)
        }
    }

    inner class DownloadImageTask(private val binding: ActivityMainBinding)
        : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg params: String?): Bitmap? {
            val urlDisplay = params.first()
            var image: Bitmap? = null

            try {
                val inputStream: InputStream = URL(urlDisplay).openStream()
                image = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
                e.printStackTrace()
            }
            return image
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            binding.image.setImageBitmap(result)
            binding.button.visibility = INVISIBLE
        }
    }
}