package com.nochill_lp.unittestsample.data.api

import android.content.Context
import com.nochill_lp.unittestsample.extensions.defaultMoshi
import com.nochill_lp.unittestsample.extensions.fromJson
import kotlinx.coroutines.delay
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

/*
* NTT Data Italia S.p.A.
*
* @author Luca Pollastri - luca.pollastri@nttdata.com
* @version 1.0
*
*/

class SafeApiCall {

    suspend inline fun <T, reified E: Any> safeApiCall(
        block: () -> Response<T>
    ): NetworkResource<T, E> {

        val result = runCatching(block)

        return if(result.isSuccess){
            val response = result.getOrNull()
            return response?.let {
                if(it.isSuccessful){
                    NetworkResource.Success(it.body())
                } else {
                    val errorBody = it.errorBody()
                    if(errorBody != null){
                        NetworkResource.Error(NetworkError.HttpError(response.code(), defaultMoshi.fromJson<E>(errorBody.source())))
                    } else {
                        NetworkResource.Error(NetworkError.HttpError(response.code(), null))
                    }
                }
            } ?: NetworkResource.Error(NetworkError.Unknown())
        } else{
            when(result.exceptionOrNull()){
                is UnknownHostException -> NetworkResource.Error(NetworkError.NoInternetConnectionError())
                else -> NetworkResource.Error(NetworkError.Unknown())
            }
        }
    }
}