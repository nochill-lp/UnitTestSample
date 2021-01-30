package com.nochill_lp.unittestsample.ui.articlelist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nochill_lp.unittestsample.R
import com.nochill_lp.unittestsample.ui.articlelist.viewmodel.ArticleListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleListActivity : AppCompatActivity() {

    private val viewModel: ArticleListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)

        viewModel.loadArticles()
    }
}