package com.nochill_lp.unittestsample.data.articles

import com.nochill_lp.unittestsample.CoroutineBaseTest
import com.nochill_lp.unittestsample.domain.ResultState
import com.nochill_lp.unittestsample.domain.model.article.Article
import com.nochill_lp.unittestsample.domain.model.article.ArticleDataSource
import com.nochill_lp.unittestsample.domain.model.article.ArticlesNotFound
import com.nochill_lp.unittestsample.domain.model.article.article
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import retrofit2.Response

class ArticleRepositoryTest: CoroutineBaseTest(){

    /**
     *
     * Explain what is Mockk+ --> https://mockk.io/ An object under test may have dependencies on other (complex) objects.
     * To isolate the behavior of the object you want to replace the other
     * objects by mocks that simulate the behavior of the real objects
     *
     * When is executed Before --> method annotate with before are executed before each test. Useful when we want to execute some common code before each test
     * When is executed After --> method annotate with after are executed after each test. Useful to clear data after each test
     * What is a spy --> Spy allows mocking only a particular part of some class.
     * ArgumentCaptor --> when we need to capture a parameter passed in a method we use a captured slot
     * val slot = slot<String>() object.runMethod(capture(slot)) assertEquals("expected", slot.captured)
     *
     * returns --> specify that the matched call returns a specified value
     * answer --> specify that the matched call answers with a code block scoped with answer scope
     *
     * Verify --> Used to verify that a method of a mock object is or is not called
     * How to mock Static classes or Kotlin object --> MockkObject and MockkStatic
     * Why is useful to unmockk all in tear down --> clear up all mocked objectes
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
        coVerify(exactly = 1) { articleService.getArticles() }
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
        coVerify(exactly = 1) { articleService.getArticles() }
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
        coVerify(exactly = 1) { articleService.getArticles() }
        assertTrue(result is ResultState.Success)
        assertEquals(0, (result as ResultState.Success).data.size)
    }


    // Here as a test example
    // Verify --> Used to verify that a method of a mock object is or is not called (Also use verifyOrder or verifySequence)
    // For example in MVP architecture is useful to verify when is called a view method and to test exactly the parameters
    @Test
    fun `Given success response, when get articles with callback, should run on success method`(){

        // Given
        val articleCallback = mockk<ArticleDataSource.ArticleDataSourceResponse<List<Article>>>(relaxUnitFun = true)

        // When
        dataSource.getArticle(true, articleCallback)

        // Should
        verify { articleCallback.onSuccess(any()) }
    }

    // Here as a test example
    // ArgumentCaptor --> when we need to capture a parameter passed in a method we use a captured slot
    // * val slot = slot<String>() object.runMethod(capture(slot)) assertEquals("expected", slot.captured)
    @Test
    fun `Given a success response from a service, when get articles, should run on success method`(){

        //Given
        val articleCallback = mockk<ArticleDataSource.ArticleDataSourceResponse<List<Article>>>(relaxUnitFun = true)
        val apiLibrary = mockk<ArticleDataSource.ApiLibrary<List<Article>>>(relaxUnitFun = true)
        val slot = slot<ArticleDataSource.ApiLibrary.Callback<List<Article>>>()
        every { apiLibrary.runApi(capture(slot)) } answers {
            slot.captured.onApiSuccess(emptyList())
        }

        // When
        dataSource.getArticle(apiLibrary, articleCallback)

        // Should
        verify { articleCallback.onSuccess(any()) }
    }

    @After
    fun tearDown(){
        unmockkAll()
    }

}