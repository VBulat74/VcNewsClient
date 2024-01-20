package ru.com.vbulat.vcnewsclient.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.com.vbulat.vcnewsclient.di.ApplicationComponent
import ru.com.vbulat.vcnewsclient.di.DaggerApplicationComponent

class NewsFeedApplication : Application() {

    val component : ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            this,
        )
    }
}

@Composable
fun getApplicationComponent() : ApplicationComponent{
    Log.d("Recomposition", "getApplicationComponent")
    return (LocalContext.current.applicationContext as NewsFeedApplication).component
}