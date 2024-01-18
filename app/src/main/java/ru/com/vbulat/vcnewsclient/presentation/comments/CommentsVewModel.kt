package ru.com.vbulat.vcnewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import ru.com.vbulat.vcnewsclient.data.repository.NewsFeedRepository
import ru.com.vbulat.vcnewsclient.domain.FeedPost

class CommentsVewModel(
    feedPost : FeedPost,
    application : Application
) : ViewModel() {

    private val repository = NewsFeedRepository(application)

    val screenState = repository.getComments(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it,
            )
        }
}