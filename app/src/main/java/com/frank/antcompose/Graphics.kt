package com.frank.antcompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import java.util.Timer
import java.util.TimerTask

@Composable
fun Init() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        a = (size.width / gridWidth).toInt()
        b = (size.height / gridWidth).toInt()
        currentX = a / 2
        currentY = b / 2
        isInitOK = true
    }
}

@Composable
fun DrawRect() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (thisTask == null) {
                    currentX = a / 2
                    currentY = b / 2
                    isInitOK = true
                    step = 0
                    direction = 1
                    current_direction = 0
                    for (r in 0 until rows) {
                        for (c in 0 until cols) {
                            arr[r][c] = 0
                        }
                    }
                    thisTask = object : TimerTask() {
                        override fun run() {
                            try {
                                doTask()
                            } catch (_: Exception) {
                            }
                        }
                    }
                    thisTimer = Timer()
                    thisTimer.scheduleAtFixedRate(thisTask, 100, 30)

                } else {
                    stopDrawTimer()
                    doTask()
                }
            }
            .background(Color.Black)
    ) {
        fun drawMyRect(xx: Int, yy: Int) {
            drawRect(
                color = Color.LightGray,
                topLeft = Offset(x = gridWidth * xx, y = gridWidth * yy),
                size = Size(width = gridWidth, height = gridWidth),
            )
        }
        if (step > 0) {
            for (r in 0 until rows) {
                for (c in 0 until cols) {
                    if (arr[r][c] == 1) {
                        drawMyRect(r, c)
                    }
                }
            }
        }
    }
}
