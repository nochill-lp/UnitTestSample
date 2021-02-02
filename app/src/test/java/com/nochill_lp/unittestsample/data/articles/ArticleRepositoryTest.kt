package com.nochill_lp.unittestsample.data.articles

import com.nochill_lp.unittestsample.CoroutineBaseTest
import com.nochill_lp.unittestsample.domain.ResultState
import com.nochill_lp.unittestsample.domain.model.article.ArticleDataSource
import com.nochill_lp.unittestsample.domain.model.article.ArticlesNotFound
import com.nochill_lp.unittestsample.domain.model.article.article
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import junit.framework.TestCase
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ArticleRepositoryTest: CoroutineBaseTest(){

    /** TODO
     * Explain what is Mockk+
     * When is executed Before
     * When is executed After
     * What is a spy and a slot
     * How to mock Static classes or Kotlin object
     * Why is useful to unmockk all in tear down
     */

    @MockK
    private lateinit var articleService: ArticleService

    private lateinit var dataSource: ArticleDataSource

    @Before
    fun initialize(){
        MockKAnnotations.init(this)
        dataSource = ArticleRepository(articleService, coroutinesTestRule.testDispatcherProvider)
    }

    @Test
    fun `Given api response success, when repository get articles, should return ResultState Success`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        // Given
        val listArticleResponse = listOf(
            ArticleService.ArticleResponse(
                id = "id1",
                title = "title1",
                url = "url1",
                imageUrl = "imageUrl1",
                newsSite = "newsSite1",
                summary = "summary1",
                publishedAt = "2021-02-03T11:00:00.000Z"
            ),
            ArticleService.ArticleResponse(
                id = "id2",
                title = "title2",
                url = "url2",
                imageUrl = "imageUrl2",
                newsSite = "newsSite2",
                summary = "summary2",
                publishedAt = "2021-02-03T11:00:00.000Z"
            )
        )
        coEvery { articleService.getArticles() } returns Response.success(listArticleResponse)

        // When
        val result = dataSource.getArticle()

        // Then
        assertTrue(result is ResultState.Success)
        assertEquals(2, (result as ResultState.Success).data.size)
    }

    @Test
    fun `Given api response error, when repository get articles, should return ResultState Error`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        // Given
        coEvery { articleService.getArticles() } returns Response.error(
            401,
            ResponseBody.create(
            MediaType.get("application/json"),""
            )
        )

        // When
        val result = dataSource.getArticle()

        // Then
        assertTrue(result is ResultState.Error)
        assertTrue((result as ResultState.Error).exception is ArticlesNotFound)
    }

    @Test
    fun `Given api response success but empty list, when repository get articles, should return ResultState Success with empty articles`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        // Given
        val emptyArticleResponse = emptyList<ArticleService.ArticleResponse>()
        coEvery { articleService.getArticles() } returns Response.success(emptyArticleResponse)

        // When
        val result = dataSource.getArticle()

        // Then
        assertTrue(result is ResultState.Success)
        assertEquals(0, (result as ResultState.Success).data.size)
    }

    @After
    fun tearDown(){
        unmockkAll()
    }

}