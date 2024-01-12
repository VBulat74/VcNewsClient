package ru.com.vbulat.vcnewsclient.data.repository

import android.app.Application
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import ru.com.vbulat.vcnewsclient.data.mapper.NewsFeedMapper
import ru.com.vbulat.vcnewsclient.data.model.LikesCountResponseDto
import ru.com.vbulat.vcnewsclient.data.network.ApiFactory
import ru.com.vbulat.vcnewsclient.domain.FeedPost
import ru.com.vbulat.vcnewsclient.domain.StatisticItem
import ru.com.vbulat.vcnewsclient.domain.StatisticType

class NewsFeedRepository(application : Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)
    val loggedIn = (token != null) && (token.isValid)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts : List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom : String? = null

    suspend fun loadRecommendations():List<FeedPost>{

        val startFrom = nextFrom

        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts

        val response = if (startFrom == null) {
            apiService.loadRecommendation(getAccessToken())
        }else{
            apiService.loadRecommendation(getAccessToken(),startFrom)
        }
        nextFrom = response.newsFeedContent.nextFrom

        val posts =  mapper.mapResponseToPosts(response)

        _feedPosts.addAll(posts)

        return feedPosts
    }

    private fun getAccessToken() : String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    suspend fun deletePost(
        feedPost : FeedPost
    ){
        apiService.ignorePost(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )

        _feedPosts.remove(feedPost)
    }

    suspend fun addLike(
        feedPost : FeedPost
    ){
        val response = apiService.addLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id,
        )

        updateFeedPosts(response,feedPost)

    }

    suspend fun deleteLike(
        feedPost : FeedPost
    ){
        val response = apiService.deleteLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id,
        )

        updateFeedPosts(response,feedPost)
    }

    private fun updateFeedPosts(
        response : LikesCountResponseDto,
        feedPost : FeedPost
    ){
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type= StatisticType.LIKES, count = newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }
}