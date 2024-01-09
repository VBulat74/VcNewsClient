package ru.com.vbulat.vcnewsclient.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.com.vbulat.vcnewsclient.data.model.NewsFeedResponseDto

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadRecommendation(
        @Query("access_token") token : String,
    ) : NewsFeedResponseDto
}