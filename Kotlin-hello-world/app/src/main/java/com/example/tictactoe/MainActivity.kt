package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var increment = 0
        var bt_increment = findViewById<Button>(R.id.bt_increment)
        var tx_result = findViewById<TextView>(R.id.tx_numberClicks)
        bt_increment.setOnClickListener {
            increment += 1
            tx_result.setText(""+increment)
            Log.v("INCREMENT VALUE: ", "" + increment)
        }
    }
}