package ru.com.vbulat.vcnewsclient.di

import android.content.Context
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.com.vbulat.vcnewsclient.data.network.ApiFactory
import ru.com.vbulat.vcnewsclient.data.network.ApiService
import ru.com.vbulat.vcnewsclient.data.repository.NewsFeedRepositoryImpl
import ru.com.vbulat.vcnewsclient.domain.repository.NewsFeedRepository

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository (impl : NewsFeedRepositoryImpl) : NewsFeedRepository

    companion object{
        @ApplicationScope
        @Provides
        fun provideApiService() : ApiService{
            return ApiFactory.apiService
        }

        @ApplicationScope
        @Provides
        fun provideVKStorage (
            context : Context
        ) : VKPreferencesKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }
    }


}