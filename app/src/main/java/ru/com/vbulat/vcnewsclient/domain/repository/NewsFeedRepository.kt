package ru.com.vbulat.vcnewsclient.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.com.vbulat.vcnewsclient.domain.entety.AuthState
import ru.com.vbulat.vcnewsclient.domain.entety.FeedPost
import ru.com.vbulat.vcnewsclient.domain.entety.PostComment

interface NewsFeedRepository {

    fun getAuthStateFlow() : StateFlow<AuthState>

    fun getRecommendations() : StateFlow<List<FeedPost>>

    fun getComments(feedPost : FeedPost) : StateFlow<List<PostComment>>

    suspend fun checkAuthState()

    suspend fun loadNextData()

    suspend fun deletePost(
        feedPost : FeedPost
    )

    suspend fun addLike(
        feedPost : FeedPost
    )

    suspend fun deleteLike(
        feedPost : FeedPost
    )

}