package ru.com.vbulat.vcnewsclient.data.model

import com.google.gson.annotations.SerializedName

data class LikesCountResponseDto(
    @SerializedName("response") val likes : LikesCountDto,
)
