package com.aslansari.lotr.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val accessToken: AccessToken
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header(
                "Authorization",
                String.format("%s %s", accessToken.type, accessToken.token)
            )
            .build()
        return chain.proceed(modifiedRequest)
    }
}