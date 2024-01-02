package ru.com.vbulat.vcnewsclient.ui.theme

import ru.com.vbulat.vcnewsclient.domain.FeedPost
import ru.com.vbulat.vcnewsclient.domain.PostComment

sealed class HomeScreenState {

    object Initial : HomeScreenState()
    data class Posts(val posts : List<FeedPost>) : HomeScreenState()
    data class Comments(val feedPost: FeedPost, val comments : List<PostComment>) : HomeScreenState()

}