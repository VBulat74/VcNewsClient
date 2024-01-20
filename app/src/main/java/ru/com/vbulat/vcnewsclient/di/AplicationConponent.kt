package ru.com.vbulat.vcnewsclient.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.com.vbulat.vcnewsclient.presentation.main.MainActivity

@ApplicationScope
@Component (
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun getCommentScreenComponentFactory() : CommentsScreenComponent.Factory


    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance context : Context
        ) : ApplicationComponent
    }
}