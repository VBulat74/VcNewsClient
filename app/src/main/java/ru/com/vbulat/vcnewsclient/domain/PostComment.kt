package ru.com.vbulat.vcnewsclient.domain

import ru.com.vbulat.vcnewsclient.R

data class PostComment(
    val id: Int,
    val authorName: String = "Jon Smith",
    val authorAvatarId : Int = R.drawable.ic_comment_author_avatar,
    val commentText: String = "Long comment text",
    val publicationDate: String = "14:00"
)
