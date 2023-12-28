package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: MyViewModel = viewModel<MyViewModel>()

            // 보여줄 기본 텍스트
            var text: MutableState<String> = remember {
                mutableStateOf("Hello world")
            }

            // 방법1. viewModel 사용 안할 때
            var text1: MutableState<String> = remember {
                mutableStateOf("")
            }

            // 방법2. by, viewModel 사용 할 때
            var text2: String by remember {
                mutableStateOf("")
            }

            // 방법3.
            var (text3, setText) = remember {
                mutableStateOf("")
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Text(text = viewModel.value.value)

                // 방법1.
//                Text(text = text.value)
//                TextField(value = text1.value, onValueChange = { newText -> text1.value = newText })

                // 방법2.
//                Text(text = viewModel.value.value)
//                TextField(value = text2, onValueChange = { newText -> text2 = newText })

                // 방법3.
                Text(text = viewModel.value.value)
                TextField(value = text3, onValueChange = setText)

                Button(onClick = {
                    // 방법1.
//                    text.value = text1.value
                    // 방법2.
//                    viewModel.changeValue(text2)
                    // 방법3.
                    viewModel.changeValue(text3)
                }) {
                    Text(text = "클릭")
                }
            }
            }
        }
    }

class MyViewModel: ViewModel() {

    private val _value: MutableState<String> = mutableStateOf("Hello World")
    var value: State<String> = _value

    fun changeValue(newValue: String) {
        Log.d("변경된 텍스트", "$newValue")
        _value.value = newValue
    }

}
