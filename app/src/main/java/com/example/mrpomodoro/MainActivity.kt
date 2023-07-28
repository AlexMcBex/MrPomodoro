package com.example.mrpomodoro

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mrpomodoro.AdviceSlipApiService
import com.example.mrpomodoro.MyCountDownTimer
import android.util.Log
import kotlinx.serialization.*
import kotlinx.serialization.json.*


@Serializable
data class AdviceSlip(val id: Int, val advice: String)

@Serializable
data class AdviceResponse(val slip: AdviceSlip)

val jsonString = "{\"slip\": {\"id\": 199, \"advice\": \"Be brave. Even if you're not, pretend to be. No one can tell the difference.\"}}"
val adviceResponse = Json.decodeFromString<AdviceResponse>(jsonString)
val advice = adviceResponse.slip.advice





class MainActivity : AppCompatActivity() {

    private lateinit var workTimer: MyCountDownTimer
    private lateinit var restTimer: MyCountDownTimer
    private lateinit var adviceService: AdviceSlipApiService
    private lateinit var button: Button
    private lateinit var timerText: TextView
    private lateinit var adviceText: TextView
    private var isResting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)  // Replace with your actual button ID
        timerText = findViewById(R.id.timerText)  // Replace with your actual timer TextView ID
        adviceText = findViewById(R.id.adviceText)  // Replace with your actual advice TextView ID

        workTimer = MyCountDownTimer(25 * 60 * 1000, 1000, timerText)
        restTimer = MyCountDownTimer(5 * 60 * 1000, 1000, timerText)

        adviceService = AdviceSlipApiService { advice ->
            Log.d("MainActivity", "New advice: $advice") // Log when new advice is posted
            val stringBuilder = StringBuilder()
            for (c in advice) {
                if (c.toInt() in 32..126) { // ASCII range
                    stringBuilder.append(c)
                }
            }
            val cleanAdvice = stringBuilder.toString()
            runOnUiThread {
                adviceText.text = cleanAdvice
            }
        }



        val params = button.layoutParams
        params.width = 400
        params.height = 200

        button.layoutParams = params
        button.setOnClickListener {
            when (button.text) {
                "START" -> {
                    button.text = "STOP"
                    button.setBackgroundColor(Color.RED)
                    workTimer.start()
                    adviceService.start()
                }
                "STOP" -> {
                    button.text = "START"
                    button.setBackgroundColor(Color.BLUE)
                    workTimer.reset()
                    adviceService.stop()
                }
                "REST" -> {
                    button.text = "STOP"
                    button.setBackgroundColor(Color.GREEN)
                    restTimer.start()
                    isResting = true
                }
                "WORK" -> {
                    button.text = "STOP"
                    button.setBackgroundColor(Color.RED)
                    workTimer.start()
                    isResting = false
                }
            }
        }

        workTimer.onFinish = {
            button.text = "REST"
            button.setBackgroundColor(Color.GREEN)
            vibrate()
        }

        restTimer.onFinish = {
            button.text = "WORK"
            button.setBackgroundColor(Color.BLUE)
            vibrate()
        }
    }

    private fun vibrate() {
        // Implement your vibration logic here
    }
}
