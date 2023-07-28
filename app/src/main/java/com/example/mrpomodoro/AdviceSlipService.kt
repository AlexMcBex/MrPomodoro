package com.example.mrpomodoro

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import android.util.Log
import com.google.gson.Gson



class AdviceSlipApiService(private val onNewAdvice: (String) -> Unit) {

    data class AdviceResponse(val slip: AdviceSlip)
    data class AdviceSlip(val id: Int, val advice: String)

    private val adviceUrl = "https://api.adviceslip.com/advice"
    private var job: Job? = null
    private val handler = Handler(Looper.getMainLooper())

    fun start() {
        job = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                withContext(Dispatchers.Default) {
                    try {
                        val url = URL(adviceUrl)
                        val httpURLConnection = url.openConnection() as HttpURLConnection
                        httpURLConnection.requestMethod = "GET"
                        val statusCode = httpURLConnection.responseCode
                        Log.d("AdviceService", "HTTP response code: $statusCode")
                        val inputStream = httpURLConnection.inputStream
                        val advice = parseAdvice(inputStream.bufferedReader())
                        advice?.let {
                            Log.d("AdviceService", "Advice: $it") // Log the advice if it's not null
                            handler.post { onNewAdvice(it) }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                delay(30 * 1000L)  // Delay for 30 seconds
            }
        }
    }


    fun stop() {
        job?.cancel()
    }

    private fun parseAdvice(reader: BufferedReader): String? {
        return try {
            val gson = Gson()
            val response = gson.fromJson(reader, AdviceResponse::class.java)
            response.slip.advice
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}


