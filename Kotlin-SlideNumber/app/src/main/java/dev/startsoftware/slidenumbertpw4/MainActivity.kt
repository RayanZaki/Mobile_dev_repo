package dev.startsoftware.slidenumbertpw4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_size)

        val genBtn = findViewById<Button>(R.id.generatePuzzleBtn)
        val sizeInput = findViewById<EditText>(R.id.boardSizeInp)

        genBtn.setOnClickListener{
            val intent = Intent(this, BoardActivity::class.java)

            val size = sizeInput.text.toString()

            if(size.isEmpty() || size.toInt() < 2) return@setOnClickListener

            Log.v("SIZE", size)
            intent.putExtra("boardSize", size)
            launchActivityPuzzle.launch(intent)
        }




    }

    private val launchActivityPuzzle = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->
        if(result.resultCode == Activity.RESULT_OK) {
            println("came back")
        }
    }

}