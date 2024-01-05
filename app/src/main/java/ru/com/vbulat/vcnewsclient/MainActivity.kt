package ru.com.vbulat.vcnewsclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import ru.com.vbulat.vcnewsclient.ui.theme.MainScreen
import ru.com.vbulat.vcnewsclient.ui.theme.VcNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VcNewsClientTheme {
                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract(),
                ){
                    when (it) {
                        is VKAuthenticationResult.Success -> {
                            Log.d("aaa", "Success auth")
                        }
                        is VKAuthenticationResult.Failed -> {
                            Log.d("aaa", "Failed auth")
                        }
                    }
                }
                launcher.launch(listOf(VKScope.WALL))

                MainScreen()


            }
        }
    }
}