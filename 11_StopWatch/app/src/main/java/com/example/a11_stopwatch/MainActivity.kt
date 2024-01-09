package com.example.a11_stopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a11_stopwatch.ui.theme._11_StopWatchTheme
import java.sql.Time
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MainViewModel>()
            val sec = viewModel.sec.value
            val milli = viewModel.milli.value
            val isRunning = viewModel.isRunning.value

            MainScreen(
                isRunning,
                sec,
                milli,
                onToggle = {
                    running ->
                    if(running) {
                        viewModel.onPause()
                    } else {
                        viewModel.onStart()
                    }
                }
            )
        }
    }
}

// 뷰모델 > 기능
class MainViewModel: ViewModel() {

    private var timerTask: Timer? = null
    private var time = 0

    private val _sec = mutableStateOf(0) // 초
    val sec: State<Int> = _sec

    private val _milli = mutableStateOf(0) // 밀리sec
    val milli: State<Int> = _milli

    private val _isRunning = mutableStateOf(false)
    var isRunning: State<Boolean> = _isRunning

    // 재생버튼
    fun onStart() {
        _isRunning.value = true
        timerTask = timer(period = 10) {
            time++
            _sec.value = time / 100
            _milli.value = time % 100
        }
    }

    fun onPause() {
        _isRunning.value = false
        timerTask?.cancel()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    isRunning: Boolean,
    sec: Int,
    milli: Int,
    onToggle: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "스탑워치")})
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(8.dp),

            ) {
                Text("$sec", fontSize = 100.sp)
                Text("$milli", fontSize = 30.sp)
            }
            FloatingActionButton(
                onClick = { onToggle(isRunning) },
                Modifier.background(color = Green)
            ) {
                Image(
                    painter = painterResource(
                        id = if(isRunning) {
                            R.drawable.baseline_pause_24
                        } else {
                            R.drawable.baseline_play_arrow_24
                        }
                    ),
                    contentDescription = "start/pause"
                )
            }
        }

    }
}
