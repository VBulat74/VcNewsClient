package ru.com.vbulat.vcnewsclient.presentation.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.com.vbulat.vcnewsclient.domain.FeedPost

class CommentsVewModelFactory(
    private val feedPost: FeedPost
) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsVewModel(feedPost) as T
    }
}