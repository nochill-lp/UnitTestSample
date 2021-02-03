package com.nochill_lp.unittestsample.domain.model.article

import com.nochill_lp.unittestsample.domain.ResultState
import java.lang.Exception

/*
* NTT Data Italia S.p.A.
*
* @author Luca Pollastri - luca.pollastri@nttdata.com
* @version 1.0
*
*/

interface ArticleDataSource {

    suspend fun getArticle(): ResultState<List<Article>>

    // Here for test examples
    fun getArticle(success: Boolean, dataSourceResponse: ArticleDataSourceResponse<List<Article>>)

    // Here for test example
    fun getArticle(apiLibrary: ApiLibrary<List<Article>>, dataSourceResponse: ArticleDataSourceResponse<List<Article>>)

    interface ApiLibrary<T>{

        fun runApi(callback: Callback<T>)

        interface Callback<T>{
            fun onApiSuccess(data: T)
            fun onApiError(exception: Exception)
        }
    }

    interface ArticleDataSourceResponse<T>{
        fun onSuccess(data: T)
        fun onError(exception: Exception)
    }
}
