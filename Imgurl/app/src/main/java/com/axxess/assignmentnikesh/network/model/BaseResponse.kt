package com.axxess.assignmentnikesh.network.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class BaseResponse(
    @SerializedName("data")
    var data: ArrayList<Data>? = ArrayList(),

    @Nullable
    @SerializedName("success")
    var success: Boolean? = null,

    @SerializedName("status")
    var status: Int? = null
)