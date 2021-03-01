package com.nochill_lp.unittestsample.domain.model

sealed class NumberOfResources {
    data class Limited(val limit: Int): NumberOfResources()
    object Unlimited: NumberOfResources()
}