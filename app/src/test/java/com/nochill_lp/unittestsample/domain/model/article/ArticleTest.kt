package com.nochill_lp.unittestsample.domain.model.article

import org.junit.Assert.*
import org.junit.Test


class ArticleTest{

    @Test
    fun `Article creation without date test`(){

        val article = article {
            id = "id"
            title = "titolo"
            newsSite = "newsSite"
            imageUrl = "imageUrl"
            summary = "summary"
        }

        assertEquals("id", article.id)
        assertEquals("titolo", article.title)
        assertEquals("newsSite", article.newsSite)
        assertEquals("imageUrl", article.imageUrl)
        assertEquals("summary", article.summary)
        assertNull(article.publishedAt)
    }

    @Test
    fun `Empty article creation test`(){
        val article = article {}

        assertEquals("", article.id)
        assertEquals("", article.title)
        assertEquals("", article.newsSite)
        assertEquals("", article.imageUrl)
        assertEquals("", article.summary)
        assertNull(article.publishedAt)
    }
}