package com.nochill_lp.unittestsample.data.api

/*
* NTT Data Italia S.p.A.
*
* @author Luca Pollastri - luca.pollastri@nttdata.com
* @version 1.0
*
*/

sealed class NetworkResource<T, E>(
    val data: T? = null,
    val error: NetworkError<E>? = null
) {
    class Success<T, E>(data: T?): NetworkResource<T, E>(data = data)
    class Error<T,E>(error: NetworkError<E>?): NetworkResource<T, E>(error = error)
}

sealed class NetworkError<E>{
    class HttpError<E>(val code: Int, val data : E?): NetworkError<E>()
    class NoInternetConnectionError<E>: NetworkError<E>()
    class Unknown<E>: NetworkError<E>()
}