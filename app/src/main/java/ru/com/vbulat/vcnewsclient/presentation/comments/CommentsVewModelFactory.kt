package ru.com.vbulat.vcnewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.com.vbulat.vcnewsclient.domain.FeedPost

class CommentsVewModelFactory(
    private val feedPost: FeedPost,
    private val application : Application,
) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsVewModel(feedPost, application) as T
    }
}