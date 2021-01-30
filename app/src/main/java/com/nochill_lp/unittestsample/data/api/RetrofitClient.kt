package com.nochill_lp.unittestsample.data.api

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nochill_lp.unittestsample.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

/*
*
* @author Luca Pollastri
* @version 1.0
*
*/

class RetrofitClient(
    val context: Context
) {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val baseOkHttpClient by lazy {
        OkHttpClient()
    }

    private val clientBuilder by lazy {
        val builder = baseOkHttpClient
            .newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }

        builder
    }

    private val retrofitClient by lazy {
        Retrofit.Builder().baseUrl("https://www.google.com")
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(clientBuilder.build())
            .build()
    }

    /**
     * Creates an instance of [apiClass]
     * using the [retrofitClient].
     */
    fun <T : Any> createServiceAPI(apiClass: KClass<T>): T {
        return retrofitClient.create(
            apiClass.java
        )
    }


}