package com.nochill_lp.unittestsample.data.articles

import com.nochill_lp.unittestsample.domain.model.article.article
import com.nochill_lp.unittestsample.extensions.tryParseDate
import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.http.GET

/*
*
* @author Luca Pollastri
* @version 1.0
*
*/

interface ArticleService {

    @GET("/api/v2/articles")
    suspend fun getArticles(): Response<List<ArticleResponse>>

    data class ArticleResponse(
        @field:Json(name="id")
        val id: String?,
        @field:Json(name="title")
        val title: String?,
        @field:Json(name="url")
        val url: String?,
        @field:Json(name="imageUrl")
        val imageUrl: String?,
        @field:Json(name="newsSite")
        val newsSite: String?,
        @field:Json(name="summary")
        val summary: String?,
        @field:Json(name="publishedAt")
        val publishedAt: String?
    ) {


        fun toArticle() = this.let{ response ->
            article {
                id = response.id ?: ""
                title = response.title ?: ""
                imageUrl = response.imageUrl ?: ""
                newsSite = response.newsSite ?: ""
                summary = response.summary ?: ""
                publishedAt = response.publishedAt?.tryParseDate("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
            }
        }

    }
}