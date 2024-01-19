package ru.com.vbulat.vcnewsclient.domain.entety

data class FeedPost (
    val id : Long,
    val communityId : Long,
    val communityName : String,
    val publicationData: String,
    val communityImageUrl : String,
    val contentText : String,
    val contentImageUrl : String?,
    val statistics : List<StatisticItem>,
    val isLiked : Boolean,
    )