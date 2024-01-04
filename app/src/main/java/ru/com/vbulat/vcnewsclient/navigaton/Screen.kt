package ru.com.vbulat.vcnewsclient.navigaton

import ru.com.vbulat.vcnewsclient.domain.FeedPost

sealed class Screen (
    val route : String,
) {
    object NewsFeed : Screen(route = ROUTE_NEWS_FEED)
    object Favorite : Screen(route = ROUTE_FAVORITE)
    object Profile : Screen(route = ROUTE_PROFILE)
    object Home : Screen(route = ROUTE_HOME)
    object Comments : Screen(route = ROUTE_COMMENTS){

        private const val ROUTE_FOR_ARGS = "comments"
        fun getRouteWithArgs(feedPost: FeedPost) : String {
            return "$ROUTE_FOR_ARGS/${feedPost.id}/${feedPost.contentText}"
        }
    }

    companion object {

        const val KEY_FEED_POST_ID = "feed_post_id"
        const val KEY_CONTENT_TEXT = "content_text"

        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVORITE = "favorite"
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST_ID}/{$KEY_CONTENT_TEXT}"
        const val ROUTE_HOME = "home"
    }
}