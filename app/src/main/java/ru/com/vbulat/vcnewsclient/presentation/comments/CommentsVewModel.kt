package ru.com.vbulat.vcnewsclient.presentation.comments

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import ru.com.vbulat.vcnewsclient.domain.entety.FeedPost
import ru.com.vbulat.vcnewsclient.domain.usecases.GetCommentsUseCase
import javax.inject.Inject

class CommentsVewModel @Inject constructor(
    private val feedPost : FeedPost,
    private val getCommentsUseCase : GetCommentsUseCase,
) : ViewModel() {

    val screenState = getCommentsUseCase(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it,
            )
        }
}