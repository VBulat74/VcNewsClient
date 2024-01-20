package ru.com.vbulat.vcnewsclient.presentation

import android.app.Application
import ru.com.vbulat.vcnewsclient.di.ApplicationComponent
import ru.com.vbulat.vcnewsclient.di.DaggerApplicationComponent
import ru.com.vbulat.vcnewsclient.domain.entety.FeedPost

class NewsFeedApplication : Application() {

    val component : ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            this,
            FeedPost(0,0,"","","","","", listOf(),true)
        )
    }
}