package com.example.mrpomodoro

import android.os.CountDownTimer
import android.widget.TextView

class MyCountDownTimer(
    private val millisInFuture: Long,
    countDownInterval: Long,
    private val textView: TextView
) : CountDownTimer(millisInFuture, countDownInterval) {
    var onFinish: (() -> Unit)? = null

    override fun onFinish() {
        onFinish?.invoke()
    }

    override fun onTick(millisUntilFinished: Long) {
        val minutes = millisUntilFinished / 1000 / 60
        val seconds = millisUntilFinished / 1000 % 60
        textView.text = String.format("%02d:%02d", minutes, seconds)
    }

    fun reset() {
        cancel()
        onTick(millisInFuture)
    }
}
