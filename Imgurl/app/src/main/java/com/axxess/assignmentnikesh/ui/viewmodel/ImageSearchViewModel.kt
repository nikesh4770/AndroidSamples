package com.axxess.assignmentnikesh.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.axxess.assignmentnikesh.network.ApiInterface
import com.axxess.assignmentnikesh.network.CallHandler
import com.axxess.assignmentnikesh.network.model.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageSearchViewModel(val apiInterface: ApiInterface) : ViewModel() {

    class Factory constructor(
        var apiInterface: ApiInterface
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ImageSearchViewModel(apiInterface) as T
        }
    }

    internal var baseResponseLiveData: MutableLiveData<BaseResponse> = MutableLiveData()

    fun getSearchResponse(query: String, cb: CallHandler): MutableLiveData<BaseResponse> {
        apiInterface.searchImages(query).enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                t.printStackTrace()
                cb.onFailure(t.message, null)
                baseResponseLiveData = MutableLiveData()
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        baseResponseLiveData.postValue(it)
                    }

                    cb.onSuccess(response = response.body())
                } else {
                    cb.onFailure("Error occurred! Please try again", response = response.body())
                    baseResponseLiveData = MutableLiveData()
                }
            }
        })
        return baseResponseLiveData
    }
}