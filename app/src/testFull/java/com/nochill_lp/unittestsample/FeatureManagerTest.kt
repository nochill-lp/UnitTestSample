package com.nochill_lp.unittestsample

import org.junit.Assert.assertEquals
import org.junit.Test

class FeatureManagerTest{

    @Test
    fun isExpandArticleEnabledTest(){
        assertEquals(true, FeatureManager.isExpandArticleEnabled())
    }


    @Test
    fun getFeedSourcesTest(){
        assertEquals(4, FeatureManager.getFeedSources().size)
    }
}