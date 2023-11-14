package dev.startsoftware.snakegamesimplesteps

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text
import java.util.Vector

class MainActivity : AppCompatActivity() {
    var boardSize=40
    var board_data= arrayOfNulls<Array<TextView?>>(boardSize)
    var direction="right"
    var is_running=false
    var paused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildGrid(boardSize)
        initSnake()
        createThreadMoveSnake()
        generateFood()

        var bt_start=findViewById<Button>(R.id.bt_start)
        bt_start.setOnClickListener {
            if (!is_running) {
                if(!paused) initSnake()
                paused = false
                createThreadMoveSnake()
                bt_start.setText("Pause the Game")
                is_running=true
            }else{
                paused =true
                bt_start.setText("Start")
                is_running=false
            }
        }


        var bt_right=findViewById<Button>(R.id.bt_right)
        bt_right.setOnClickListener {
            if(direction == "left") return@setOnClickListener
            direction="right"
        }

        var bt_down=findViewById<Button>(R.id.bt_down)
        bt_down.setOnClickListener {
            if(direction == "up") return@setOnClickListener
            direction="down"
        }
        var bt_up=findViewById<Button>(R.id.bt_up)
        bt_up.setOnClickListener {
            if(direction == "down") return@setOnClickListener
            direction="up"
        }
        var bt_left=findViewById<Button>(R.id.bt_left)
        bt_left.setOnClickListener {
            if(direction == "right") return@setOnClickListener
            direction="left"
        }
    }
    var food_row:Int=0
    var food_col:Int=0

    var bonus_row =0
    var bonus_col =0

    var bonus_generated=false

    var food_generated=0


    private fun setBonus(row: Int, col: Int){
        var cell= board_data[row - 1]?.get(col - 1) as TextView
        cell.setBackgroundColor(Color.parseColor("#00ffff"))
        cell= board_data[row - 1]?.get(col) as TextView
        cell.setBackgroundColor(Color.parseColor("#00ffff"))
        cell= board_data[row]?.get(col) as TextView
        cell.setBackgroundColor(Color.parseColor("#00ffff"))
        cell= board_data[row]?.get(col - 1) as TextView
        cell.setBackgroundColor(Color.parseColor("#00ffff"))
    }

    private fun restoreBonus(row:Int,col:Int){
      restoreCellOfGrid(row - 1,col - 1)
        restoreCellOfGrid(row - 1,col)
        restoreCellOfGrid(row,col)
        restoreCellOfGrid(row, col -1)
    }
    private fun generateFood(){
        food_generated += 1
        delay = if (delay - increment < 50) 50 else delay - increment

        if (food_generated % 1 == 0 && !bonus_generated) {
            bonus_col=(1..boardSize-1).random()
            bonus_row=(1..boardSize-1).random()
            bonus_generated=true
            setBonus(bonus_row, bonus_col)

        }

        food_row=(0..boardSize-1).random()
        food_col=(0..boardSize-1).random()

        var cell= board_data[food_row]?.get(food_col) as TextView
        cell.setBackgroundColor(Color.parseColor("#0000ff"))



    }
    var snakeCells= Vector<String>()

    private fun initSnake(){
        delay = 300
        food_generated = 0
        for (elem in snakeCells) {
            var vals=elem.split("x")
            var row=vals[0].toString().toInt()
            var col=vals[1].toString().toInt()
            restoreCellOfGrid(row,col)
        }
        snakeCells.removeAllElements()
        snakeCells.add("10x10")
        snakeCells.add("10x11")
        snakeCells.add("10x12")

        var cell= board_data[10]?.get(10) as TextView
        cell.setBackgroundColor(Color.parseColor("#ff0000"))

        cell= board_data[10]?.get(11) as TextView
        cell.setBackgroundColor(Color.parseColor("#ff0000"))

        cell= board_data[10]?.get(12) as TextView
        cell.setBackgroundColor(Color.parseColor("#ff0000"))
    }
    var delay:Long=300
    val increment = 50
    private fun createThreadMoveSnake(){
        var handler = Handler(Looper.getMainLooper())
        val runnable =object : Runnable{
            override fun run() {
                if (!is_running) return
                moveSnake()
                handler.postDelayed(this, delay)
            }
        }
        handler.postDelayed(runnable, delay)
    }

    private fun createThreadRemoveBonus(){
        var handler = Handler(Looper.getMainLooper())
        val runnable =object : Runnable{
            override fun run() {
                if (!is_running) return
                moveSnake()
                handler.postDelayed(this, delay)
            }
        }
        handler.postDelayed(runnable, delay)
    }
    fun moveSnake(){
        var headVals=snakeCells.get(snakeCells.size-1).split("x")
        var tailVals=snakeCells.get(0).split("x")
        var head_row=headVals[0].toString().toInt()
        var head_col=headVals[1].toString().toInt()
        var tail_row=tailVals[0].toString().toInt()
        var tail_col=tailVals[1].toString().toInt()



        println("Direction is "+direction)
        if (direction=="right") {
            head_col = head_col + 1
        }
        if (direction=="left") {
            head_col = head_col - 1
        }
        if (direction=="down") {
            head_row = head_row + 1
        }
        if (direction=="up") {
            head_row = head_row - 1
        }
        if ( head_col<0 || tail_col <0 || head_row <0 || tail_row<0 ||
                head_col>=boardSize || tail_col>=boardSize || head_row>=boardSize || tail_row>=boardSize || snakeCells.size != snakeCells.distinct().size) {
            setGameOver()
            return
        }

        snakeCells.add(""+head_row+"x"+head_col)

        var cell= board_data[head_row]?.get(head_col) as TextView
        cell.setBackgroundColor(Color.parseColor("#ff0000"))
//        println("new head > "+head_row+"x"+head_col+" :  "+cell)

        // remove tail

        if ((head_row == bonus_row && head_col == bonus_col) || (head_row == bonus_row - 1 && head_col == bonus_col - 1) || (head_row == bonus_row - 1 && head_col == bonus_col)  || (head_row == bonus_row && head_col == bonus_col - 1)) {
            restoreBonus(bonus_row, bonus_col)
            bonus_generated = false
        }
        if(food_row == head_row && food_col == head_col) {
            generateFood()
        } else {
            restoreCellOfGrid(tail_row,tail_col)
            snakeCells.removeElementAt(0)
        }

        println()
        println("Snake Cells:")
        snakeCells.forEach { value -> println(value) }
        println()



    }


    fun setGameOver(){
        findViewById<Button>(R.id.bt_start).setText("Start Again")
        findViewById<TextView>(R.id.tx_over).visibility= View.VISIBLE
        is_running=false
    }



    private fun restoreCellOfGrid(row:Int,col:Int){
        var cell= board_data[row]?.get(col) as TextView
        if (row % 2 == col %2) {
            cell.setBackgroundColor(Color.parseColor("#ECE5B6"))
        } else {
            cell.setBackgroundColor(Color.parseColor("#E0FFFF"))
        }
    }
    private fun buildGrid(number: Int){
        var lt_board=findViewById<LinearLayout>(R.id.lt_board)
        lt_board.removeAllViews()
        for (row in 0..number-1) {
            var lt_row = layoutInflater.inflate(R.layout.rowofcells, null) as LinearLayout
            var row_data= arrayOfNulls<TextView>(number)
            for (col in 0..number-1) {
                var tx_cell: TextView?
                if (row % 2 == col %2) {
                    tx_cell = layoutInflater.inflate(R.layout.cellone, null) as TextView
                } else {
                    tx_cell = layoutInflater.inflate(R.layout.celltwo, null) as TextView
                }
                lt_row.addView(tx_cell)
                row_data[col]=tx_cell
            }
            lt_board.addView(lt_row)
            board_data[row]= row_data
        }
    }

}