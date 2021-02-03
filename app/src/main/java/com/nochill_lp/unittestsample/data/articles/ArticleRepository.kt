package com.nochill_lp.unittestsample.data.articles

import com.nochill_lp.unittestsample.data.api.NetworkResource
import com.nochill_lp.unittestsample.data.api.safeApiCall
import com.nochill_lp.unittestsample.domain.ResultState
import com.nochill_lp.unittestsample.domain.model.article.Article
import com.nochill_lp.unittestsample.domain.model.article.ArticleDataSource
import com.nochill_lp.unittestsample.domain.model.article.ArticlesNotFound
import com.nochill_lp.unittestsample.utils.DefaultDispatcherProvider
import com.nochill_lp.unittestsample.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import java.lang.Exception

/*
* NTT Data Italia S.p.A.
*
* @author Luca Pollastri - luca.pollastri@nttdata.com
* @version 1.0
*
*/

class ArticleRepository(
  private val articleService: ArticleService,
  private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ArticleDataSource {

    override suspend fun getArticle(): ResultState<List<Article>> {
        return withContext(dispatcherProvider.io()){
            val result = safeApiCall{
                articleService.getArticles()
            }

            when(result){
                is NetworkResource.Success -> ResultState.Success(result.data?.map { it.toArticle() } ?: emptyList())
                is NetworkResource.Error -> ResultState.Error(ArticlesNotFound())
            }
        }
    }

    // Here for testing example
    override fun getArticle(success: Boolean, dataSourceResponse: ArticleDataSource.ArticleDataSourceResponse<List<Article>>) {
        if(success){
            dataSourceResponse.onSuccess(emptyList())
        } else{
            dataSourceResponse.onError(ArticlesNotFound())
        }

    }

    override fun getArticle(
            apiLibrary: ArticleDataSource.ApiLibrary<List<Article>>,
            dataSourceResponse: ArticleDataSource.ArticleDataSourceResponse<List<Article>>
    ) {

        // Api library call with callback, which returns data in another callback
        apiLibrary.runApi(object : ArticleDataSource.ApiLibrary.Callback<List<Article>>{
            override fun onApiSuccess(data: List<Article>) {
                dataSourceResponse.onSuccess(data)
            }

            override fun onApiError(exception: Exception) {
                dataSourceResponse.onError(exception)
            }
        })
    }

}