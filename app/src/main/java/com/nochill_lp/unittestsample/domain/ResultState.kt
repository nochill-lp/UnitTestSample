package com.nochill_lp.unittestsample.domain

/*
* NTT Data Italia S.p.A.
*
* @author Luca Pollastri - luca.pollastri@nttdata.com
* @version 1.0
*
*/

sealed class ResultState<out T>{
    data class Success<out T>(val data: T): ResultState<T>()
    data class Error(val exception: Exception): ResultState<Nothing>()
}