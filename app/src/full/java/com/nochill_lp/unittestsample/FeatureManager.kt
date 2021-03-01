package com.nochill_lp.unittestsample

import com.nochill_lp.unittestsample.domain.model.NumberOfResources

object FeatureManager {

    fun isExpandArticleEnabled(): Boolean {
        return true
    }

    fun numberOfArticlesAvailable(): NumberOfResources{
        return NumberOfResources.Unlimited
    }
}