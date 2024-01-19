package ru.com.vbulat.vcnewsclient.domain.usecases

import ru.com.vbulat.vcnewsclient.domain.entety.FeedPost
import ru.com.vbulat.vcnewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repository : NewsFeedRepository
) {
    suspend operator fun  invoke(feedPost : FeedPost) {
        return repository.deletePost(feedPost)
    }
}