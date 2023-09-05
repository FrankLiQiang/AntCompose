package com.frank.antcompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

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
            .background(Color.Black)
    ) {
        fun drawMyRect(xx:Int, yy: Int) {
            drawRect(
                color = Color.LightGray,
                topLeft = Offset(x = gridWidth * xx, y = gridWidth * yy),
                size = Size(width = gridWidth, height = gridWidth),
            )
        }
        if (step > 0) {
            for (i in 0..x0.size - 1) {
                drawMyRect(x0[i], y0[i])
            }
        }
    }
}
