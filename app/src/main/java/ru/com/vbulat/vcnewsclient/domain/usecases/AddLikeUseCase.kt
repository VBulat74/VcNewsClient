package ru.com.vbulat.vcnewsclient.domain.usecases

import ru.com.vbulat.vcnewsclient.domain.entety.FeedPost
import ru.com.vbulat.vcnewsclient.domain.repository.NewsFeedRepository

class AddLikeUseCase(
    private val repository : NewsFeedRepository
) {
    suspend operator fun  invoke(feedPost : FeedPost) {
        return repository.addLike(feedPost)
    }
}