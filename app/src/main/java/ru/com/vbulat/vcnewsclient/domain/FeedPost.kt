package ru.com.vbulat.vcnewsclient.domain

data class FeedPost (
    val id : String,
    val communityName : String,
    val publicationData: String,
    val communityImageUrl : String,
    val contentText : String,
    val contentImageUrl : String?,
    val statistics : List<StatisticItem>,
    )