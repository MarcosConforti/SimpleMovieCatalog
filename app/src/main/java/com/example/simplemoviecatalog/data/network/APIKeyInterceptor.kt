package com.example.simplemoviecatalog.data.network

import com.example.simplemoviecatalog.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class APIKeyInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val interceptorBuilder = request.url().newBuilder().addQueryParameter(
            "api_key", Constants.API_KEY
        ).build()

        val modifierRequest = request.newBuilder().url(interceptorBuilder).build()

        return chain.proceed(modifierRequest)
    }
}