package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // 네비게이션을 사용하려면 dependency 추가해야 함
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState()}

            NavHost(
                navController = navController,
                startDestination = "first" // 시작 라우트
            ) {
                composable("first") {
                    FirstScreen(navController = navController, scope, snackbarHostState)
                }
                composable("second/{value}") { backStackEntry ->
                    SecondScreen(
                        navController = navController,
                        value = backStackEntry.arguments?.getString("value") ?: ""
                    )
                }
                composable("third/{value}") { backStackEntry ->
                    ThirdScreen(
                        navController = navController,
                        value = backStackEntry.arguments?.getString("value") ?: ""
                    )

                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FirstScreen(navController: NavController, scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
    val (value, setValue) = remember { // 상태 기억 옵저버
        mutableStateOf("")
    }

    Scaffold(
        snackbarHost = { SnackbarHost (snackbarHostState)}
    ) {
        Column( // 큰 틀
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {// 안의 구성요소
            Text(text="메인화면")
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = value, onValueChange = setValue)
            Button(onClick = { // 버튼 클릭 시 네비게이트
                if (value.isNotEmpty()) {
                    navController.navigate("second/$value") // 두 번째 화면 이동
                } else { // 텍스트 없을 시 스낵바
                    scope.launch {
                        snackbarHostState.showSnackbar("텍스트를 입력해주세요.")
                    }
                }

            }) {
                Text("화면2/텍스트길이 재기") // 버튼의 텍스트
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                navController.navigate("third/$value") // 세 번째 화면 이동

            }) {
                Text(text = "화면3/텍스트 놀이")
            }
    }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SecondScreen(navController: NavController, value: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "화면2/텍스트길이 재기", fontSize = 30.sp)
        Spacer(modifier = Modifier.width(10.dp))
        var valLenth = value.length
        Text(text = "텍스트 길이는 $valLenth 입니다.")

        Button(onClick = { navController.navigateUp() }) {
            Text(text = "뒤로가기")
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ThirdScreen(navController: NavController, value: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "화면3/텍스트 놀이", fontSize = 30.sp)
        Spacer(modifier = Modifier.width(10.dp))

        Text(text = "$value", fontSize = 50.sp, color = Color.Blue)
        Text(text = "$value", fontSize = 30.sp, color = Color.Green, fontWeight = FontWeight.Bold)
        Text(text = "$value", fontSize = 20.sp, color = Color.Magenta, fontWeight = FontWeight.ExtraLight)

        Button(onClick = { navController.navigateUp() }) {
            Text(text = "뒤로가기")
        }

    }
}