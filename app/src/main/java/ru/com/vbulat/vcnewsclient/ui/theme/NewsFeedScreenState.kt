package ru.com.vbulat.vcnewsclient.ui.theme

import ru.com.vbulat.vcnewsclient.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()
    data class Posts(val posts : List<FeedPost>) : NewsFeedScreenState()

}