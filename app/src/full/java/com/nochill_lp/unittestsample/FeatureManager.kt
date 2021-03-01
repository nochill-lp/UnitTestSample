package com.nochill_lp.unittestsample

object FeatureManager {

    fun isExpandArticleEnabled(): Boolean {
        return true
    }

    fun getFeedSources(): List<String>{
        return listOf(
            "Washington Post",
            "New York Times",
            "Los Angeles Post",
            "Miami Herald"
        )
    }
}