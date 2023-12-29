package ru.com.vbulat.vcnewsclient.domain

import ru.com.vbulat.vcnewsclient.R

data class FeedPost (
    val id : Int = 0,
    val communityName : String = "/dev/null",
    val publicationData: String = "14:00",
    val avatarResId : Int = R.drawable.post_comunity_thumbnail,
    val contentText : String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi egestas, tortor ac ultricies auctor, odio nunc scelerisque risus, et feugiat est dui non orci. Vivamus consequat id enim efficitur pulvinar.",
    val contentImmageResId : Int = R.drawable.post_content_image,
    val statistics : List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = 966),
        StatisticItem(type = StatisticType.SHARES, count = 7),
        StatisticItem(type = StatisticType.COMMENTS, count = 8),
        StatisticItem(type = StatisticType.LIKES, count = 27),
    )
 )