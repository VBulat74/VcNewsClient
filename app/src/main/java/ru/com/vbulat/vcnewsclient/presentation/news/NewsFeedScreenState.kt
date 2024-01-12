package ru.com.vbulat.vcnewsclient.presentation.news

import ru.com.vbulat.vcnewsclient.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    object Loading : NewsFeedScreenState()
    data class Posts(
        val posts : List<FeedPost>,
        val nextDataIsLoading : Boolean = false
    ) : NewsFeedScreenState()

}