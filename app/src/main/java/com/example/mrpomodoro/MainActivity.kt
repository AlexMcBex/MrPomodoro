package com.example.mrpomodoro

import android.graphics.Color
import android.os.Bundle
import android.os.Vibrator
import android.os.VibrationEffect
import android.os.Build
import android.content.Context
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mrpomodoro.AdviceSlipApiService
import com.example.mrpomodoro.MyCountDownTimer

class MainActivity : AppCompatActivity() {

    private lateinit var workTimer: MyCountDownTimer
    private lateinit var restTimer: MyCountDownTimer
    private lateinit var adviceService: AdviceSlipApiService
    private lateinit var button: Button
    private lateinit var timerText: TextView
    private lateinit var adviceText: TextView
    private lateinit var vibrator: Vibrator
    private var isResting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        timerText = findViewById(R.id.timerText)
        adviceText = findViewById(R.id.adviceText)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        workTimer = MyCountDownTimer(25 * 60 * 1000, 1000, timerText)
        restTimer = MyCountDownTimer(5 * 60 * 1000, 1000, timerText)

        adviceService = AdviceSlipApiService { advice ->
            val cleanAdvice = advice.filter { it.isLetterOrDigit() || it.isWhitespace() }
            runOnUiThread {
                adviceText.text = cleanAdvice
            }
        }

        button.setOnClickListener {
            vibrator.cancel()
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
        // Check if device has a vibrator
        if (vibrator.hasVibrator()) {
            // Vibrate with a pattern. Sleep for 500, vibrate for 500, sleep for 500 and so on
            val pattern = longArrayOf(500, 500, 500, 500)
            // The "-1" here means to repeat indefinitely
            // "0" would play the pattern once, then stop
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0))
            } else {
                vibrator.vibrate(pattern, 0)  // The "0" means to repeat the pattern starting at the beginning
            }
        }
    }
}
