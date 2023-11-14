package com.example.tictactoe

import android.app.Activity
import android.content.Context
import android.os.Vibrator
import android.util.Log

class Device {
    companion object {
        fun vibrate(context: Activity) {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            // Check if the device supports vibration
            if (vibrator.hasVibrator()) {
                // Vibrate for 1000 milliseconds (1 second)
                vibrator.vibrate(1000)
            }
            Log.v("VIBRATION", "vibrated")
        }
    }
}