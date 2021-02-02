package com.nochill_lp.unittestsample.ui.articlelist.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nochill_lp.unittestsample.data.api.RetrofitClient
import com.nochill_lp.unittestsample.data.articles.ArticleRepository
import com.nochill_lp.unittestsample.data.articles.ArticleService
import com.nochill_lp.unittestsample.domain.ResultState
import com.nochill_lp.unittestsample.domain.model.article.Article
import com.nochill_lp.unittestsample.domain.model.article.ArticleDataSource
import com.nochill_lp.unittestsample.ui.UIState
import com.nochill_lp.unittestsample.ui.toUIState
import kotlinx.coroutines.delay
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
    private val articleDataSource: ArticleDataSource
): ViewModel() {

    private val _articlesState = MutableLiveData<UIState<List<Article>>>()
    val articleState: LiveData<UIState<List<Article>>> = _articlesState

    fun loadArticles(){
        viewModelScope.launch {
            _articlesState.value = UIState.Loading
            _articlesState.value = articleDataSource.getArticle().toUIState()
        }
    }
}