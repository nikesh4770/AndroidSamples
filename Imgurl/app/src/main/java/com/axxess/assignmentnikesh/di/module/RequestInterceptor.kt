package com.axxess.assignmentnikesh.di.module

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

internal class RequestInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val builder: Request.Builder = original.newBuilder()
            .header("Accept-Language", "en")
            .method(original.method(), original.body())
        val newRequest: Request = builder.build()
        return chain.proceed(newRequest)
    }
}
