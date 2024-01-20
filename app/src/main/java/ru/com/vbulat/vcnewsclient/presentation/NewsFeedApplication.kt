package ru.com.vbulat.vcnewsclient.presentation

import android.app.Application
import ru.com.vbulat.vcnewsclient.di.ApplicationComponent
import ru.com.vbulat.vcnewsclient.di.DaggerApplicationComponent

class NewsFeedApplication : Application() {

    val component : ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            this,
        )
    }
}