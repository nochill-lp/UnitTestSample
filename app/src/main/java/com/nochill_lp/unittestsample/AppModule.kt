package com.nochill_lp.unittestsample

import com.nochill_lp.unittestsample.data.api.RetrofitClient
import com.nochill_lp.unittestsample.data.articles.ArticleRepository
import com.nochill_lp.unittestsample.data.articles.ArticleService
import com.nochill_lp.unittestsample.domain.model.article.ArticleDataSource
import com.nochill_lp.unittestsample.ui.articlelist.viewmodel.ArticleListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/*
*
* @author Luca Pollastri
* @version 1.0
*
*/


val appModule = module {


    single<ArticleService> {
        RetrofitClient(androidApplication()).createServiceAPI(ArticleService::class)
    }

    viewModel { ArticleListViewModel(get()) }

    single<ArticleDataSource> { ArticleRepository(get()) }
}