package ru.com.vbulat.vcnewsclient.data.model

import com.google.gson.annotations.SerializedName

data class LikesDto (
    @SerializedName("count") val count : Int,
)