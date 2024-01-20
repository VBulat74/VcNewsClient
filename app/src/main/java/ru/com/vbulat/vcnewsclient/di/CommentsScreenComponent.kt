package ru.com.vbulat.vcnewsclient.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.com.vbulat.vcnewsclient.domain.entety.FeedPost
import ru.com.vbulat.vcnewsclient.presentation.ViewModelFactory

@Subcomponent(
    modules = [CommentsVewModelModule::class]
)
interface CommentsScreenComponent {

    fun getVewModelFactory() : ViewModelFactory

    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance feedPost: FeedPost,
        ) : CommentsScreenComponent
    }
}