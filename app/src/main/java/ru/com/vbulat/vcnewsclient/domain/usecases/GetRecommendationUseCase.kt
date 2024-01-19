package ru.com.vbulat.vcnewsclient.domain.usecases

import kotlinx.coroutines.flow.StateFlow
import ru.com.vbulat.vcnewsclient.domain.entety.FeedPost
import ru.com.vbulat.vcnewsclient.domain.repository.NewsFeedRepository

class GetRecommendationUseCase (
    private val repository : NewsFeedRepository
) {
    operator fun invoke() : StateFlow<List<FeedPost>>{
        return repository.getRecommendations()
    }
}