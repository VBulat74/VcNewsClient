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
import ru.com.vbulat.vcnewsclient.presentation.NewsFeedApplication
import ru.com.vbulat.vcnewsclient.presentation.ViewModelFactory
import ru.com.vbulat.vcnewsclient.ui.theme.VcNewsClientTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var vewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as NewsFeedApplication).component
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        setContent {

            VcNewsClientTheme {
                val viewModel : MainViewModel = viewModel(factory = vewModelFactory)
                val authState = viewModel.authState.collectAsState(AuthState.Initial)

                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract(),
                ) {
                    viewModel.performAuthResult()
                }

                when (authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen(viewModelFactory = vewModelFactory)
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