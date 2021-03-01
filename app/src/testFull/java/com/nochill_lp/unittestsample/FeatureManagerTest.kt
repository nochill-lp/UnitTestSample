package com.nochill_lp.unittestsample

import com.nochill_lp.unittestsample.domain.model.NumberOfResources
import org.junit.Assert.assertEquals
import org.junit.Test

class FeatureManagerTest{

    @Test
    fun isExpandArticleEnabledTest(){
        assertEquals(true, FeatureManager.isExpandArticleEnabled())
    }


    @Test
    fun numberOfArticleAvailableTest(){
        assertEquals(FeatureManager.numberOfArticlesAvailable()::class, NumberOfResources.Unlimited::class)
    }
}