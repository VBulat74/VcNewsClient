package ru.com.vbulat.vcnewsclient.domain.usecases

import kotlinx.coroutines.flow.StateFlow
import ru.com.vbulat.vcnewsclient.domain.entety.FeedPost
import ru.com.vbulat.vcnewsclient.domain.entety.PostComment
import ru.com.vbulat.vcnewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository : NewsFeedRepository
) {
    operator fun invoke(feedPost : FeedPost) : StateFlow<List<PostComment>> {
        return repository.getComments(feedPost = feedPost)
    }
}