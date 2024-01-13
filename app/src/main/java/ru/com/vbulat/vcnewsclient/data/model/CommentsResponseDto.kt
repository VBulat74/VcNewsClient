package ru.com.vbulat.vcnewsclient.data.model

import com.google.gson.annotations.SerializedName

data class CommentsResponseDto(
    @SerializedName("response") val content : CommentsContentDto,

)
