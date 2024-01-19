package ru.com.vbulat.vcnewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import ru.com.vbulat.vcnewsclient.data.repository.NewsFeedRepositoryImpl
import ru.com.vbulat.vcnewsclient.domain.entety.FeedPost
import ru.com.vbulat.vcnewsclient.domain.usecases.GetCommentsUseCase
import javax.inject.Inject

class CommentsVewModel @Inject constructor(
    feedPost : FeedPost,
    application : Application
) : ViewModel() {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getCommentsUseCase = GetCommentsUseCase(repository)

    val screenState = getCommentsUseCase(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it,
            )
        }
}