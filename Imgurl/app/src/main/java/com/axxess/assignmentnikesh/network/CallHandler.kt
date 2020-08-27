package com.axxess.assignmentnikesh.network

import com.axxess.assignmentnikesh.network.model.BaseResponse

interface CallHandler {

    fun onSuccess(response: BaseResponse?)
    fun onFailure(message: String?, response: BaseResponse?)
}