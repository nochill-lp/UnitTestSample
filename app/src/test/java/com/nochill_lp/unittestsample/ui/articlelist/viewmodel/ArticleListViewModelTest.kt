package com.nochill_lp.unittestsample.ui.articlelist.viewmodel

import android.content.Context
import com.nochill_lp.unittestsample.CoroutineBaseTest
import com.nochill_lp.unittestsample.FeatureManager
import com.nochill_lp.unittestsample.domain.ResultState
import com.nochill_lp.unittestsample.domain.model.article.ArticleDataSource
import com.nochill_lp.unittestsample.domain.model.article.ArticlesNotFound
import com.nochill_lp.unittestsample.domain.model.article.article
import com.nochill_lp.unittestsample.extensions.getUserConnectivityType
import com.nochill_lp.unittestsample.ui.UIState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class ArticleListViewModelTest: CoroutineBaseTest(){

    @MockK
    private lateinit var articleDataSource: ArticleDataSource
    @MockK
    private lateinit var context: Context

    private lateinit var viewModel: ArticleListViewModel

    @Before
    fun initialize(){
        MockKAnnotations.init(this)
        mockkStatic("com.nochill_lp.unittestsample.extensions.ContextExtensionsKt")
        every { context.getUserConnectivityType() } returns "wifi"
        viewModel = ArticleListViewModel(context, articleDataSource)
    }

    @Test
    fun `Given repository success response, when viewmodel load articles, should set value to UIState Success`() = coroutinesTestRule.testDispatcher.runBlockingTest{

        // Given
        val repositoryResponse = ResultState.Success(
            listOf(
                article{
                    id = "id1"
                    title = "title1"
                    imageUrl = "imageUrl1"
                    summary = "summary1"
                },
                article{
                    id = "id2"
                    title = "title2"
                    imageUrl = "imageUrl2"
                    summary = "summary2"
                }
            )
        )
        coEvery { articleDataSource.getArticle() } returns repositoryResponse

        // When
        viewModel.loadArticles()

        //Should
        assertTrue(viewModel.articleState.value is UIState.Success)
    }


    @Test
    fun `Given repository error response, when viewmodel load articles, should set value to UIState Error`() = coroutinesTestRule.testDispatcher.runBlockingTest{

        // Given
        val repositoryResponse = ResultState.Error(ArticlesNotFound())
        coEvery { articleDataSource.getArticle() } returns repositoryResponse

        // When
        viewModel.loadArticles()

        //Should
        assertTrue(viewModel.articleState.value is UIState.Error)
    }

    @Test
    fun `Given analytics enabled, when viewmodel load articles, should log analytics`() = coroutinesTestRule.testDispatcher.runBlockingTest{

        // Given
        val repositoryResponse = ResultState.Success(
                listOf(
                        article{
                            id = "id1"
                            title = "title1"
                            imageUrl = "imageUrl1"
                            summary = "summary1"
                        },
                        article{
                            id = "id2"
                            title = "title2"
                            imageUrl = "imageUrl2"
                            summary = "summary2"
                        }
                )
        )
        coEvery { articleDataSource.getArticle() } returns repositoryResponse
        mockkObject(FeatureManager)
        every { FeatureManager getProperty "analyticsEnabled" } returns true

        // When
        viewModel.loadArticles()

        //Should
        assertTrue(viewModel.articleState.value is UIState.Success)
        verify { context.getUserConnectivityType() }
    }

    @Test
    fun `Given analytics disabled, when viewmodel load articles, should not log analytics`() = coroutinesTestRule.testDispatcher.runBlockingTest{

        // Given
        val repositoryResponse = ResultState.Success(
                listOf(
                        article{
                            id = "id1"
                            title = "title1"
                            imageUrl = "imageUrl1"
                            summary = "summary1"
                        },
                        article{
                            id = "id2"
                            title = "title2"
                            imageUrl = "imageUrl2"
                            summary = "summary2"
                        }
                )
        )
        coEvery { articleDataSource.getArticle() } returns repositoryResponse
        mockkObject(FeatureManager)
        every { FeatureManager getProperty "analyticsEnabled" } returns false

        // When
        viewModel.loadArticles()

        //Should
        assertTrue(viewModel.articleState.value is UIState.Success)
        verify(exactly = 0) { context.getUserConnectivityType() }
    }

    @After
    fun tearDown(){
        unmockkAll()
    }

}