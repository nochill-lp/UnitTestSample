package com.nochill_lp.unittestsample.ui.articlelist.view.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nochill_lp.unittestsample.domain.ResultState
import com.nochill_lp.unittestsample.domain.model.article.Article
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.imageloading.ImageLoadState


@Composable
fun ArticleListScreen(articleState: ResultState<List<Article>>){
    MaterialTheme {
        when(articleState){
            is ResultState.Loading -> LoadingUI()
            is ResultState.Success -> ArticleList(articleState.data)
            is ResultState.Error -> ErrorUI()
        }
    }
}

@Composable
fun LoadingUI(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorUI(){
    // TODO
}

val padding = 16.dp

@Composable
fun ArticleList(articles: List<Article>){
    Surface(
        Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        LazyColumnFor(items = articles) {
            ArticleCard(
                article = it,
                typography = MaterialTheme.typography
            )
        }
    }
}

@Composable
fun ArticleCard(
    typography: Typography,
    article: Article
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 4.dp
    ) {
        Column(
            Modifier.padding(padding)
        ) {
            Text(
                article.title,
                style = typography.h5
            )
            Text(
                article.newsSite,
                style = typography.body2
            )
            Spacer(Modifier.preferredSize(padding))
            CoilImage(
                data = article.imageUrl,
            ){
                when(it){
                    is ImageLoadState.Success -> Image(
                        painter = it.painter,
                        contentDescription = "article image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(MaterialTheme.shapes.medium)
                    )
                    is ImageLoadState.Loading -> Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(200.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator ()
                    }
                }
            }
            Spacer(Modifier.preferredSize(padding))
            Text(
                article.summary,
                style = typography.body1
            )
        }
    }
}