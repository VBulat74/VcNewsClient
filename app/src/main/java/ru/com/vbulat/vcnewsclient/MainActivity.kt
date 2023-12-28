package ru.com.vbulat.vcnewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import ru.com.vbulat.vcnewsclient.ui.theme.MainScreen
import ru.com.vbulat.vcnewsclient.ui.theme.VcNewsClientTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VcNewsClientTheme {
                MainScreen(viewModel)
            }
        }
    }
}