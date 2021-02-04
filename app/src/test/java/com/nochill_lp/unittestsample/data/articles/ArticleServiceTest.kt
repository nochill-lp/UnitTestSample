package com.nochill_lp.unittestsample.data.articles

import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Test

class ArticleServiceTest{

    @Test
    fun `ArticleReponse mapper to Article`(){

        val response = ArticleService.ArticleResponse(
                id = "id",
                title = "title",
                summary = "summary",
                newsSite = "newsSite",
                imageUrl = "imageUrl",
                url = "url",
                publishedAt = "2021-02-04T12:33:50.000Z"
        )

        val article = response.toArticle()

        assertEquals("id", article.id)
        assertEquals("title", article.title)
        assertEquals("summary", article.summary)
        assertEquals("newsSite", article.newsSite)
        assertEquals("imageUrl", article.imageUrl)
        assertNotNull(article.publishedAt)
    }

    @Test
    fun `ArticleReponse mapper to Article with unparsable date`(){

        val response = ArticleService.ArticleResponse(
                id = "id",
                title = "title",
                summary = "summary",
                newsSite = "newsSite",
                imageUrl = "imageUrl",
                url = "url",
                publishedAt = "unparsableDate"
        )

        val article = response.toArticle()

        assertEquals("id", article.id)
        assertEquals("title", article.title)
        assertEquals("summary", article.summary)
        assertEquals("newsSite", article.newsSite)
        assertEquals("imageUrl", article.imageUrl)
        assertNull(article.publishedAt)
    }

}