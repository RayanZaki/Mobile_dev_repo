package com.example.tictactoe

import android.graphics.Color
import android.widget.TextView

class Box(val game: Game, id: Int) {
    val item: TextView

    init {
        this.item = game.context.findViewById(id)
    }
    fun getContent() : String{
        return item.text.toString()
    }

    fun setBg(){
        item.setBackgroundColor(Color.parseColor("#0000FF"))
    }
    fun init() {
        item.setOnClickListener { handleClick() }

    }

    private fun getValue(): String {
        return item.text.toString()
    }

    fun isClicked(): Boolean {
        return item.text.toString().isNotEmpty()
    }

    private fun setMarker() {
        if (game.turn==1) {
            item.setText("X")
            item.setTextColor(Color.parseColor("#ff0000"))

        }else {
            item.setText("O")
            item.setTextColor(Color.parseColor("#00ff00"))
        }
    }

    private fun handleClick(){
        val existingValue:String= getValue()

        if (existingValue.isNotEmpty()){
            Device.vibrate(game.context)
            return
        }

        setMarker()

        if (game.checkWin()){
            Device.vibrate(game.context)
            game.endWin()
            return
        }
        else if (game.checkTie()) {
            game.end()
            Device.vibrate(game.context)
            return

        }
        game.changeTurn()
    }

    fun close() {

        item.setOnClickListener(null)
    }

}