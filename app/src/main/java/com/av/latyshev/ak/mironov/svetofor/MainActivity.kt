package com.av.latyshev.ak.mironov.svetofor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainLayout()
        }
    }
}

//Разделение на строки
@Composable
fun MainLayout() {
    // Состояние для цветов кругов
    val circleColors = remember { mutableStateListOf(Color.Red, Color.LightGray, Color.LightGray) }
    // Номер светофора
    var circle by remember { mutableIntStateOf(1) }
    var direction by remember { mutableIntStateOf(0) }
    // Включение таймера
    var isTimerRunning by remember { mutableStateOf(false) }
    // Текст кнопки "Режим авто"
    // var autoModeText by remember { mutableStateOf("Режим авто: Выкл") }

    LaunchedEffect(isTimerRunning) {
        while (isTimerRunning) {
            delay(1000L)
            changeColor(circle, circleColors)
            if (direction == 0)
            {
                circle++
                if (circle == 2) direction = 1
            }
            else
            {
                circle--
                if (circle == 0) direction = 0
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        circleColors.forEach { color ->
            CircleShape(color)
        }
        ButtonChange(
            onChangeColors = {
                changeColor(circle, circleColors)
                if (direction == 0)
                {
                    circle++
                    if (circle == 2) direction = 1
                }
                else
                {
                    circle--
                    if (circle == 0) direction = 0
                }
            },
            isEnabled = !isTimerRunning
        )

        ButtonAuto(isTimerRunning) {
            isTimerRunning = !isTimerRunning
        }
    }
}

fun changeColor(circle: Int, circleColors: MutableList<Color>) {
    circleColors[0] = Color.LightGray
    circleColors[1] = Color.LightGray
    circleColors[2] = Color.LightGray
    when(circle) {
        0 -> {
            circleColors[0] = Color.Red
        }
        1 -> {
            circleColors [1] = Color.Yellow
        }
        2 -> {
            circleColors [2] = Color.Green
        }
    }
}

@Composable
fun CircleShape(color: Color) {
    Canvas(Modifier.size(230.dp)) {
        drawCircle(
            color = color,
            center = center,
            radius = 110.dp.toPx()
        )
    }
}

@Composable
fun ButtonChange(onChangeColors: () -> Unit, isEnabled: Boolean = true) {
    Button(
        onClick = onChangeColors,
        enabled = isEnabled
    ) {
        Text("Сменить цвет", fontSize = 25.sp)
    }
}

@Composable
fun ButtonAuto(isTimerRunning: Boolean, onToggleAuto: () -> Unit) {
    Button(
        onClick = onToggleAuto
    ) {
        Text("Режим авто: ${ if (isTimerRunning) "Вкл" else "Выключен"}", fontSize = 25.sp)
    }
}
