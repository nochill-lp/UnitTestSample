package com.nochill_lp.unittestsample.domain.model.article

import com.nochill_lp.unittestsample.domain.ResultState

/*
* NTT Data Italia S.p.A.
*
* @author Luca Pollastri - luca.pollastri@nttdata.com
* @version 1.0
*
*/

interface ArticleDataSource {

    suspend fun getArticle(): ResultState<List<Article>>
}