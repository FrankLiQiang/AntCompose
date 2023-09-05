package com.frank.antcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.frank.antcompose.ui.theme.AntComposeTheme
import java.util.Timer
import java.util.TimerTask
import kotlin.system.exitProcess

var thisTimer: Timer = Timer()
var thisTask: TimerTask? = null
var currentX = 0
var currentY = 0
var direction = 1
var current_direction = 0 //0: West 1:North, 2: East, 3:South
var a = 0
var b = 0
var gridWidth = 20.0f
var x0: ArrayList<Int> = arrayListOf()
var y0: ArrayList<Int> = arrayListOf()
var step by mutableStateOf(0)
var isInitOK = false

const val rows = 122
const val cols = 222

val arr = Array(rows) { IntArray(cols) }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thisTask = object : TimerTask() {
            override fun run() {
                try {
                    doTask()
                } catch (_: Exception) {
                }
            }
        }
        thisTimer.scheduleAtFixedRate(thisTask, 100, 40)
        setContent {
            AntComposeTheme {
                Init()
                DrawRect()
            }
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        stopDrawTimer()
        exitProcess(0)
    }


}

fun doTask() {

    if (!isInitOK) return

    step++
    //若蚂蚁在黑格，右转90度，将该格改为白格，向前移一步；
    //若蚂蚁在白格，左转90度，将该格改为黑格，向前移一步。
    if (arr[currentX][currentY] == 0) {
        direction = 1
        x0.add(currentX)
        y0.add(currentY)
        arr[currentX][currentY] = 1
    } else {
        arr[currentX][currentY] = 0
        for (i in 0 until x0.size) {
            if (x0[i] == currentX && y0[i] == currentY) {
                x0.removeAt(i)
                y0.removeAt(i)
                break
            }
        }

        direction = -1
    }
    getNextPos()
}

fun getNextPos() {
    current_direction += direction
    if (current_direction < 0) {
        current_direction = 3
    }
    if (current_direction > 3) {
        current_direction = 0
    }
    when (current_direction) {
        0 -> currentX--
        1 -> currentY--
        2 -> currentX++
        else -> currentY++
    }
    if (currentX < 0 || currentX >= a) {
        isInitOK = false
        stopDrawTimer()
    }
    if (currentY < 0 || currentY >= b) {
        isInitOK = false
        stopDrawTimer()
    }
}

fun stopDrawTimer() {
    if (thisTask != null) {
        thisTask!!.cancel()
        thisTask = null
    }
    thisTimer.cancel()
    thisTimer.purge()
}