package ru.com.vbulat.vcnewsclient.data.repository

import android.app.Application
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.com.vbulat.vcnewsclient.data.mapper.NewsFeedMapper
import ru.com.vbulat.vcnewsclient.data.model.LikesCountResponseDto
import ru.com.vbulat.vcnewsclient.data.network.ApiFactory
import ru.com.vbulat.vcnewsclient.domain.FeedPost
import ru.com.vbulat.vcnewsclient.domain.PostComment
import ru.com.vbulat.vcnewsclient.domain.StatisticItem
import ru.com.vbulat.vcnewsclient.domain.StatisticType
import ru.com.vbulat.vcnewsclient.extensions.mergeWith

class NewsFeedRepository(application : Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextDataNeededEvent = MutableSharedFlow<Unit> (replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()
    private val loadedListFlow = flow {
        nextDataNeededEvent.emit(Unit)
        nextDataNeededEvent.collect{
            val startFrom = nextFrom

            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.loadRecommendation(getAccessToken())
            }else{
                apiService.loadRecommendation(getAccessToken(),startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom

            val posts =  mapper.mapResponseToPosts(response)

            _feedPosts.addAll(posts)

            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }.catch {

    }

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts : List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom : String? = null

    val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn (
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = feedPosts,
    )

    suspend fun loadNextData(){
        nextDataNeededEvent.emit(Unit)
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
        refreshedListFlow.emit(feedPosts)
    }

    fun getComments (feedPost : FeedPost) : Flow<List<PostComment>> = flow {
        val comments = apiService.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id,
        )
        emit (mapper.mapResponseToComments(comments))
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
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

    private suspend fun updateFeedPosts(
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
        refreshedListFlow.emit(feedPosts)
    }

    companion object{
        private const val RETRY_TIMEOUT_MILLIS = 5_000L
    }

}