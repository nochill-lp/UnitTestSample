package com.nochill_lp.unittestsample.domain.model.article

import com.squareup.moshi.Json
import java.lang.Exception
import java.util.*

/*
* NTT Data Italia S.p.A.
*
* @author Luca Pollastri - luca.pollastri@nttdata.com
* @version 1.0
*
*/

data class Article(
    val id: String,
    val title: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: Date?
)

// Article DSL
fun article(block: ArticleBuilder.() -> Unit) =
    ArticleBuilder().apply(block).build()

class ArticleBuilder{
    var id = ""
    var title = ""
    var imageUrl = ""
    var newsSite = ""
    var summary = ""
    var publishedAt: Date? = null

    fun build() =
        Article(id, title, imageUrl, newsSite, summary, publishedAt)
}

class ArticlesNotFound: Exception()
