package com.nochill_lp.unittestsample.ui.articlelist.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nochill_lp.unittestsample.data.api.RetrofitClient
import com.nochill_lp.unittestsample.data.articles.ArticleRepository
import com.nochill_lp.unittestsample.data.articles.ArticleService
import com.nochill_lp.unittestsample.domain.ResultState
import com.nochill_lp.unittestsample.domain.model.article.ArticleDataSource
import kotlinx.coroutines.launch
import retrofit2.Retrofit

/*
* NTT Data Italia S.p.A.
*
* @author Luca Pollastri - luca.pollastri@nttdata.com
* @version 1.0
*
*/

class ArticleListViewModel(
    context: Context
): ViewModel() {

    private val articleDataSource = ArticleRepository(
        RetrofitClient(context).createServiceAPI(ArticleService::class)
    )

    fun loadArticles(){
        viewModelScope.launch {

            val result = articleDataSource.getArticle()

            when(result){
                is ResultState.Success -> {
                    result.data.forEach {
                        Log.d("ARTICLES", "article: $it")
                    }
                }
                else -> Log.d("ARTICLE", "Error loading articles")
            }


        }
    }
}