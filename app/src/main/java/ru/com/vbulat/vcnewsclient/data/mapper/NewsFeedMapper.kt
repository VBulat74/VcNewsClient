package ru.com.vbulat.vcnewsclient.data.mapper

import ru.com.vbulat.vcnewsclient.data.model.NewsFeedResponseDto
import ru.com.vbulat.vcnewsclient.domain.FeedPost
import ru.com.vbulat.vcnewsclient.domain.StatisticItem
import ru.com.vbulat.vcnewsclient.domain.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToPosts(
        responseDto : NewsFeedResponseDto
    ) : List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        val posts = responseDto.newsFeedContent.posts
        val groups = responseDto.newsFeedContent.groups

        for (post in posts) {
            val group = groups.find {
                it.id == post.communityId.absoluteValue
            } ?: continue
            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                communityName = group.name,
                publicationData = mapTimeStampToDate(post.date * 1000),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments.count),
                    StatisticItem(type = StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticType.SHARES, post.reposts.count),
                ),
                isLiked = post.likes.userLikes > 0,
            )
            result.add(feedPost)
        }
        return result
    }

    private fun mapTimeStampToDate(timeStamp : Long) : String {
        val date = Date(timeStamp)
        return SimpleDateFormat("dd MMMM yyyy, hh:mm:ss", Locale.getDefault()).format(date)
    }
}