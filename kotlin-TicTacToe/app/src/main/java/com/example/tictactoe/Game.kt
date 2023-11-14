package com.example.tictactoe

import android.app.Activity
import android.util.Log
import android.widget.TextView

class Game(val context: Activity) {

    companion object {
        var possible_wins= arrayOf(
            arrayOf(R.id.tx_a1,R.id.tx_a2,R.id.tx_a3),
            arrayOf(R.id.tx_b1,R.id.tx_b2,R.id.tx_b3),
            arrayOf(R.id.tx_c1,R.id.tx_c2,R.id.tx_c3),

            arrayOf(R.id.tx_a1,R.id.tx_b2,R.id.tx_c3),
            arrayOf(R.id.tx_c1,R.id.tx_b2,R.id.tx_a3),

            arrayOf(R.id.tx_a1,R.id.tx_b1,R.id.tx_c1),
            arrayOf(R.id.tx_a2,R.id.tx_b2,R.id.tx_c2),
            arrayOf(R.id.tx_a3,R.id.tx_b3,R.id.tx_c3))

        var cells= arrayOf(R.id.tx_a1,R.id.tx_a2,R.id.tx_a3,R.id.tx_b1,R.id.tx_b2,R.id.tx_b3,R.id.tx_c1,R.id.tx_c2,R.id.tx_c3)

    }

    var gameover = false
    var turn: Int = 1

    fun changeTurn(){
        turn = if (turn == 1) 2 else 1
        context.findViewById<TextView>(R.id.tx_turn).setText("Turn of Player: $turn")
    }

    fun checkTie() : Boolean {
        for (cell in cells) {
            if (!Box(this, cell).isClicked()) return false
        }
        return true
    }
    fun end() {
        for (cell in cells) {
           Box(this, cell).close()
            gameover=true
        }
    }
    fun endWin() {
        context.findViewById<TextView>(R.id.tx_turn).setText("Congrats to Player $turn")


        end()
    }
    fun checkWin(): Boolean {
        for (possible in possible_wins){
            var seqStr=""
            for (cellId in possible){
                val existingValue:String=Box(this, cellId).getContent()
                if (existingValue.isEmpty()) break
                seqStr += existingValue
            }
            if (seqStr=="OOO" || seqStr=="XXX") {
                for (cellId in possible) {
                    Box(this, cellId).setBg()
                }
                return true
            }
        }
        return false
    }



    fun initGame() {
        // The Operation here is to get element by Id
        for (cell in cells) {
            val item = Box(this, cell)
            item.init()
        }
    }



}