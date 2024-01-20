package ru.com.vbulat.vcnewsclient.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import ru.com.vbulat.vcnewsclient.domain.entety.AuthState
import ru.com.vbulat.vcnewsclient.presentation.getApplicationComponent
import ru.com.vbulat.vcnewsclient.ui.theme.VcNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val component = getApplicationComponent()
            val viewModel : MainViewModel = viewModel(factory = component.getViewModelFactory())
            val authState = viewModel.authState.collectAsState(AuthState.Initial)

            val launcher = rememberLauncherForActivityResult(
                contract = VK.getVKAuthActivityResultContract(),
            ) {
                viewModel.performAuthResult()
            }

            VcNewsClientTheme {

                when (authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen()
                    }

                    AuthState.NotAuthorized -> {
                        LoginScreen {
                            launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                    }

                    AuthState.Initial -> {}
                }

            }
        }
    }
}