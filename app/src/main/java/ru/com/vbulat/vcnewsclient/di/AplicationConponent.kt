package ru.com.vbulat.vcnewsclient.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.com.vbulat.vcnewsclient.presentation.ViewModelFactory

@ApplicationScope
@Component (
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory() : ViewModelFactory

    fun getCommentScreenComponentFactory() : CommentsScreenComponent.Factory


    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance context : Context
        ) : ApplicationComponent
    }
}