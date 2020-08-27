package com.axxess.assignmentnikesh.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class AdConfig(
    @SerializedName("safeFlags")
    @Expose
    var safeFlags: List<String> = ArrayList(),

    @SerializedName("highRiskFlags")
    @Expose
    var highRiskFlags: List<Any> = ArrayList(),

    @SerializedName("unsafeFlags")
    @Expose
    var unsafeFlags: List<String> = ArrayList(),

    @SerializedName("wallUnsafeFlags")
    @Expose
    var wallUnsafeFlags: List<Any> = ArrayList(),

    @SerializedName("showsAds")
    @Expose
    var showsAds: Boolean? = null
)