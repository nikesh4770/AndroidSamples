package com.axxess.assignmentnikesh.network

import com.axxess.assignmentnikesh.network.model.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    /**
     * [Search Image by title, album etc..](https://api.imgur.com/3/gallery/search/1?q=vanilla)
     *
     * Get the list of images by title name, album name, image name etc...
     *
     * @param [page] Specify the page of results to query.
     *
     * @return [BaseResponse] response
     */
    @Headers("Authorization: Client-ID 137cda6b5008a7c; charset=UTF-8")
    @GET("/3/gallery/search/1")
    fun searchImages(@Query("q") query: String): Call<BaseResponse>
}