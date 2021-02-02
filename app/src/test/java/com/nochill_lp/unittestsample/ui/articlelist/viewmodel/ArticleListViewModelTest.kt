package com.nochill_lp.unittestsample.ui.articlelist.viewmodel

import com.nochill_lp.unittestsample.CoroutineBaseTest
import com.nochill_lp.unittestsample.domain.ResultState
import com.nochill_lp.unittestsample.domain.model.article.ArticleDataSource
import com.nochill_lp.unittestsample.domain.model.article.ArticlesNotFound
import com.nochill_lp.unittestsample.domain.model.article.article
import com.nochill_lp.unittestsample.ui.UIState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class ArticleListViewModelTest: CoroutineBaseTest(){

    @MockK
    private lateinit var articleDataSource: ArticleDataSource

    private lateinit var viewModel: ArticleListViewModel

    @Before
    fun initialize(){
        MockKAnnotations.init(this)
        viewModel = ArticleListViewModel(articleDataSource)
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

    @After
    fun tearDown(){
        unmockkAll()
    }

}