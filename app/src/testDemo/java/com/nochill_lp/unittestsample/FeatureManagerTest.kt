package com.nochill_lp.unittestsample

import org.junit.Assert.assertEquals
import org.junit.Test

class FeatureManagerTest{

    @Test
    fun isExpandArticleEnabledTest(){
        assertEquals(false, FeatureManager.isExpandArticleEnabled())
    }

    @Test
    fun getFeedSourcesTest(){
        assertEquals(2, FeatureManager.getFeedSources().size)
    }
}