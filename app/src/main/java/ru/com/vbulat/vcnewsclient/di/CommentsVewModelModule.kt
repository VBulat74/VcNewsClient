package ru.com.vbulat.vcnewsclient.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.com.vbulat.vcnewsclient.presentation.comments.CommentsVewModel

@Module
interface CommentsVewModelModule {

    @IntoMap
    @ViewModelKey(CommentsVewModel::class)
    @Binds
    fun bindCommentsViewModel(viewModel: CommentsVewModel) : ViewModel
}