package com.nochill_lp.unittestsample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class CoroutineBaseTest {

    // This bypasses the main thread check, and immediately runs any tasks on your test thread
    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()
}