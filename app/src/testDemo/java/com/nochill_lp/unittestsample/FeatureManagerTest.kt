package com.nochill_lp.unittestsample

import com.nochill_lp.unittestsample.domain.model.NumberOfResources
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FeatureManagerTest{

    @Test
    fun isExpandArticleEnabledTest(){
        assertEquals(false, FeatureManager.isExpandArticleEnabled())
    }

    @Test
    fun numberOfArticleAvailableTest(){
        assertEquals(FeatureManager.numberOfArticlesAvailable()::class, NumberOfResources.Limited::class)
        assertEquals(2, (FeatureManager.numberOfArticlesAvailable() as NumberOfResources.Limited).limit)
    }
}