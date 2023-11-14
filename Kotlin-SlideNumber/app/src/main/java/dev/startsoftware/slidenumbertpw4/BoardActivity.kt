package dev.startsoftware.slidenumbertpw4

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.util.Vector
import kotlin.properties.Delegates

class BoardActivity : AppCompatActivity() {

    var boardSize by Delegates.notNull<Int>();
    lateinit var gridData: Array<Array<TextView?>?>
    var empty_row by Delegates.notNull<Int>()
    var empty_col by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init_data()

        var bt_back = findViewById<Button>(R.id.bt_back)
        bt_back.setOnClickListener {
            val intent = Intent()
            this.setResult(RESULT_OK, intent)
            this.finish()
        }


        var bt_shuffle=findViewById<Button>(R.id.bt_shuffle)
        bt_shuffle.setOnClickListener {
             shuffle()
        }

        for (row in 0..boardSize-1) {
            for (col in 0..boardSize - 1) {
                gridData[row]?.get(col)?.setOnClickListener {
                    moveSquare(row,col)
                }
            }

        }
    }
    fun moveSquare(row:Int , col:Int){
        if (row+1==empty_row && col==empty_col ||
            row-1==empty_row  && col==empty_col  ||
            col+1==empty_col  && row==empty_row  ||
            col-1==empty_col  && row==empty_row
            )
            swapWithEmpty(row,col)
    }

    fun swapWithEmpty(row:Int, col:Int){
        var value= gridData[row]?.get(col)?.text
        gridData[row]?.get(col)?.text=""
        gridData[empty_row]?.get(empty_col)?.text=value
        empty_row=row
        empty_col=col
        checkForWin()
    }
    fun checkForWin(){
        //to be completed for students..
    }
    fun shuffle(){
        for (i in 0..100)shuffleOneTime()
    }
    fun shuffleOneTime(){
        var tmp_set=Vector<String>()

        if (empty_row+1<boardSize) tmp_set.add(""+(empty_row+1)+"x"+empty_col)

        if (empty_row-1>=0) tmp_set.add(""+(empty_row-1)+"x"+empty_col)

        if (empty_col+1<boardSize) tmp_set.add(""+(empty_row)+"x"+(empty_col+1))

        if (empty_col-1>=0) tmp_set.add(""+(empty_row)+"x"+(empty_col-1))

        var rand= (0..tmp_set.size-1).random()
        var nextCell=tmp_set.get(rand)

        var row=nextCell[0].toString().toInt()
        var col=nextCell[2].toString().toInt()

        var value= gridData[row]?.get(col)?.text
        gridData[row]?.get(col)?.text=""
        gridData[empty_row]?.get(empty_col)?.text=value

        empty_row=row
        empty_col=col

    }


    fun init_data(){

        boardSize = intent.getStringExtra("boardSize")?.toInt()!!

        empty_row = boardSize - 1
        empty_col = boardSize - 1

        gridData = arrayOfNulls<Array<TextView?>>(boardSize)

        buildGrid()

    }


    fun buildGrid() {
        var boardLayout = findViewById<LinearLayout>(R.id.lv_board)

        boardLayout.removeAllViews()

        var counter = 1

        for (row in 0 until boardSize) {
            var rowLayout = layoutInflater.inflate(R.layout.board_row, null) as LinearLayout

            var rowData = arrayOfNulls<TextView>(boardSize)

            for (col in 0 until boardSize) {
                val txCell = layoutInflater.inflate(R.layout.cell, null) as TextView


                txCell.text = counter.toString()

                if(col == empty_col && row == empty_row)
                    txCell.text = "  "

                counter++

                rowLayout.addView(txCell)
                rowData[col] = txCell
            }

            boardLayout.addView(rowLayout)
            gridData[row] = rowData

        }
    }

}