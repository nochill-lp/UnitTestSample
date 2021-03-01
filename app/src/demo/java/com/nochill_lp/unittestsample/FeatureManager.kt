package com.nochill_lp.unittestsample

object FeatureManager {

    fun isExpandArticleEnabled(): Boolean {
        return false
    }

    fun getFeedSources(): List<String>{
        return listOf(
            "Washington Post",
            "New York Times",
        )
    }
}